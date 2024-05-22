package terrails.colorfulhearts.forge.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import org.lwjgl.opengl.GL11;
import quek.undergarden.Undergarden;
import quek.undergarden.registry.UGEffects;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;
import terrails.colorfulhearts.forge.api.event.ForgeHeartRegistryEvent;

public class UndergardenCompat {

    private static final ResourceLocation VIRULENCE_HEARTS = new ResourceLocation("undergarden", "textures/gui/virulence_hearts.png");

    public UndergardenCompat(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(this::cancelOverlay);
        bus.addListener(this::registerEffectHeart);
    }

    private void cancelOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id().equals(new ResourceLocation(Undergarden.MODID, "virulence_hearts"))) {
            event.setCanceled(true);
        }
    }

    public void registerEffectHeart(final ForgeHeartRegistryEvent event) {
        CColorfulHearts.LOGGER.info("Registering custom hearts for virulence from mod undergarden");
        final ResourceLocation id = new ResourceLocation(CColorfulHearts.MOD_ID, "virulence_vanilla");
        HeartDrawing vanilla = new HeartDrawing() {

            @Override
            public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                Player player = Minecraft.getInstance().player;
                assert player != null;
                Gui.HeartType heartType = Gui.HeartType.forPlayer(Minecraft.getInstance().player);
                guiGraphics.blit(VIRULENCE_HEARTS, x, y, heartType.getX(half, highlight), hardcore ? 45 : 0, 9, 9);
            }

            @Override
            public ResourceLocation getId() {
                return id;
            }
        };

        event.registerStatusEffectHeart(StatusEffectHeart.build(new ResourceLocation(CColorfulHearts.MOD_ID, "virulence"), player -> player.hasEffect(UGEffects.VIRULENCE.get()))
                .addHealth(vanilla, 0.45f, 0.4f, 0.4f)
                .addAbsorption(
                        HeartDrawing.colorBlend(vanilla, id.withSuffix("_absorption"), 1.0f, 1.0f, 1.0f, 0.15f, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA),
                        HeartDrawing.colorBlend(vanilla, id.withSuffix("_absorption_2"), 1.0f, 0.85f, 0.85f, 0.5f, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA)
                ).finish()
        );
        CColorfulHearts.LOGGER.debug("Registered custom hearts for virulence from mod undergarden");
    }
}