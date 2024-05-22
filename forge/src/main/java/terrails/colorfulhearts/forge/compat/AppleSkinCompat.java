package terrails.colorfulhearts.forge.compat;

import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import squeek.appleskin.ModConfig;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.event.HUDOverlayEvent;
import squeek.appleskin.api.food.FoodValues;
import squeek.appleskin.client.HUDOverlayHandler;
import squeek.appleskin.helpers.FoodHelper;
import terrails.colorfulhearts.compat.AppleSkinCommonCompat;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;
import terrails.colorfulhearts.forge.api.event.ForgeHeartUpdateEvent;
import terrails.colorfulhearts.forge.api.event.ForgeHeartRenderEvent;
import terrails.colorfulhearts.forge.mixin.compat.appleskin.HUDOverlayHandlerAccessor;

public class AppleSkinCompat extends AppleSkinCommonCompat {

    public AppleSkinCompat() {
        MinecraftForge.EVENT_BUS.addListener(this::onDefaultRender);
        MinecraftForge.EVENT_BUS.addListener(this::onPostRender);
        MinecraftForge.EVENT_BUS.addListener(this::heartChanged);
    }

    /**
     * event called when default overlay renderer gets called.
     * Issue with it is that it should never get called due to RenderGuiOverlayEvent.Post
     * never being called due to RenderGuiOverlayEvent.Pre being cancelled
     * Due to that, the value of modifiedHealth cannot be attained here and has
     * to be calculated manually.
     * In weird case the event gets called, it should be cancelled
     */
    private void onDefaultRender(HUDOverlayEvent.HealthRestored event) {
        event.setCanceled(true);
    }

    private void onPostRender(ForgeHeartRenderEvent.Post event) {
        Player player = client.player;
        assert player != null;

        if (!shouldDrawOverlay(event.getEffectHeart().orElse(null), player)) {
            return;
        }

        /* copied from HUDOverlayHandler */

        // try to get the item stack in the player hand
        ItemStack heldItem = player.getMainHandItem();
        if (ModConfig.SHOW_FOOD_VALUES_OVERLAY_WHEN_OFFHAND.get() && !FoodHelper.canConsume(heldItem, player))
            heldItem = player.getOffhandItem();

        boolean shouldRenderHeldItemValues = !heldItem.isEmpty() && FoodHelper.canConsume(heldItem, player);
        if (!shouldRenderHeldItemValues) {
            HUDOverlayHandler.resetFlash();
            return;
        }
        int health = Mth.ceil(player.getHealth());

        FoodValues modifiedFoodValues = FoodHelper.getModifiedFoodValues(heldItem, player);
        FoodValuesEvent foodValuesEvent = new FoodValuesEvent(player, heldItem, FoodHelper.getDefaultFoodValues(heldItem, player), modifiedFoodValues);
        MinecraftForge.EVENT_BUS.post(foodValuesEvent);
        modifiedFoodValues = foodValuesEvent.modifiedFoodValues;

        float foodHealthIncrement = FoodHelper.getEstimatedHealthIncrement(heldItem, modifiedFoodValues, player);
        int modifiedHealth = Mth.ceil(Math.min(health + foodHealthIncrement, player.getMaxHealth()));

        if (modifiedHealth <= health) {
            return;
        }

        // this value never reaches 1.0, so the health colors will always be somewhat mixed
        // I'll leave this behaviour as is at it makes the differentiation easier
        float alpha = HUDOverlayHandlerAccessor.getFlashAlpha();

        drawHealthOverlay(event.getGuiGraphics(), event.getX(), event.getY(), Mth.ceil(player.getAbsorptionAmount()), health, modifiedHealth, alpha, event.isHardcore());
    }

    private void heartChanged(ForgeHeartUpdateEvent event) {
        this.lastHealth = 0;
    }

    public boolean shouldDrawOverlay(StatusEffectHeart effectHeart, Player player) {
        if (effectHeart != null) {
            return false; // AppleSkin usually checks the effect, but we'll do it this way
        }

        /* copied from HUDOverlayHandler */
        if (!ModConfig.SHOW_FOOD_HEALTH_HUD_OVERLAY.get()) {
            return false;
        }

        // in the `PEACEFUL` mode, health will restore faster
        if (player.level().getDifficulty() == Difficulty.PEACEFUL)
            return false;

        FoodData stats = player.getFoodData();

        // when player has any changes health amount by any case can't show estimated health
        // because player will confused how much of restored/damaged healths
        if (stats.getFoodLevel() >= 18)
            return false;

        if (player.hasEffect(MobEffects.REGENERATION))
            return false;

        return true;
    }
}