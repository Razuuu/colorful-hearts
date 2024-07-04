package terrails.colorfulhearts.forge.compat;

import elucent.eidolon.capability.ISoul;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRenderEvent;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRegistryEvent;

import java.util.Optional;

public class EidolonRepraisedCompat {

    private static final ResourceLocation EIDOLON_HEARTS = new ResourceLocation("eidolon", "hearts");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation("eidolon", "textures/gui/icons.png");

    public EidolonRepraisedCompat(final IEventBus bus) {
        bus.addListener(this::registerEffectHeart);
        MinecraftForge.EVENT_BUS.addListener(this::cancelOverlay);
        MinecraftForge.EVENT_BUS.addListener(this::renderEtherealHearts);
    }

    private void cancelOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id().equals(EIDOLON_HEARTS)) {
            event.setCanceled(true);
        }
    }

    public void registerEffectHeart(final ForgeHeartRegistryEvent event) {
        final ResourceLocation chilledId = new ResourceLocation("eidolon", "chilled");
        ForgeRegistries.MOB_EFFECTS.getHolder(chilledId).ifPresent(effectHolder -> {
            CColorfulHearts.LOGGER.info("Registering custom hearts for chilled from mod eidolon");

            HeartDrawing drawing = new HeartDrawing() {

                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    guiGraphics.blit(ICONS_TEXTURE, x, y, half ? 9 : 0, 0, 9, 9);
                }

                @Override
                public ResourceLocation getId() {
                    return chilledId;
                }
            };
            event.registerOverlayHeart(OverlayHeart.build(chilledId, player -> player.hasEffect(effectHolder.get()))
                    .addHealth(drawing)
                    .transparent().finish());

            CColorfulHearts.LOGGER.debug("Registered custom hearts for chilled from mod eidolon");
        });
    }

    private static final Minecraft client = Minecraft.getInstance();

    public void renderEtherealHearts(final ForgeHeartRenderEvent.Post event) {
        Player player = client.player;
        if (player == null) return;

        Optional<ISoul> capability = player.getCapability(ISoul.INSTANCE).resolve();
        if (capability.isEmpty()) return;
        ISoul soul = capability.get();

        float maxEtherealHealth = soul.getMaxEtherealHealth();
        if (maxEtherealHealth == 0) return;

        float etherealHealth = soul.getEtherealHealth();
        int maxEtherealHearts = Mth.ceil(maxEtherealHealth / 2.0f);

        int maxHealth = Mth.ceil(Math.max(player.getMaxHealth(), player.getHealth()));
        int absorption = Mth.ceil(player.getAbsorptionAmount());

        int healthHearts = Math.min(10, Mth.ceil(maxHealth / 2.0f));

        // There can be more than 10 absorption hearts when maxHealth < 19
        int maxAbsorbing = maxHealth >= 19 ? 20 : 40 - maxHealth - (maxHealth % 2);
        int absorptionHearts = Mth.ceil(Math.min(maxAbsorbing, absorption) / 2.0f);

        final ForgeGui gui = (ForgeGui) client.gui;
        int vanillaHearts = healthHearts + absorptionHearts;
        int top = vanillaHearts > 10 ? 20 : 10;
        if (vanillaHearts + maxEtherealHearts > top) {
            int overflown = (top - vanillaHearts) + maxEtherealHearts;
            if (overflown > 0) {
                gui.leftHeight += Mth.ceil(maxEtherealHearts / 10.0f) * 10;
            }
        }

        etherealHealth += Math.min(40, maxHealth + absorption);

        int xPos = event.getX();
        int yPos = event.getY();
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int blinkingOffset = event.isBlinking() ? 9 : 0;

        for (int i = healthHearts + absorptionHearts + maxEtherealHearts - 1; i >= healthHearts + absorptionHearts; i--) {
            int x = xPos + (i % 10) * 8;
            int y = yPos - (i / 10) * 10;
            event.getGuiGraphics().blit(ICONS_TEXTURE, x, y, blinkingOffset, 18, 9, 9);

            if (i * 2 + 1 < etherealHealth) {
                guiGraphics.blit(ICONS_TEXTURE, x, y, 0, 9, 9, 9);
            } else if (i * 2 + 1 == etherealHealth) {
                guiGraphics.blit(ICONS_TEXTURE, x, y, 9, 9, 9, 9);
            }
        }
    }
}
