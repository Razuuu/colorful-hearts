package terrails.colorfulhearts.forge.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRegistryEvent;

public class EidolonRepraisedCompat {

    private static final ResourceLocation CHILLED_OVERLAY = new ResourceLocation("eidolon", "hearts");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation("eidolon", "textures/gui/icons.png");

    public EidolonRepraisedCompat(final IEventBus bus) {
        bus.addListener(this::registerEffectHeart);
        MinecraftForge.EVENT_BUS.addListener(this::cancelOverlay);
        // TODO: Handle Ethereal Health (how to render those extra hearts?)
    }

    private void cancelOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id().equals(CHILLED_OVERLAY)) {
            event.setCanceled(true);
        }
    }

    // TODO: Gotta handle effect hearts that are transparent and should still have the original icons below them...
    public void registerEffectHeart(final ForgeHeartRegistryEvent event) {
        ForgeRegistries.MOB_EFFECTS.getHolder(new ResourceLocation("eidolon", "chilled")).ifPresent(effectHolder -> {
            CColorfulHearts.LOGGER.info("Registering custom hearts for chilled from mod eidolon");

            final ResourceLocation heartId = new ResourceLocation(CColorfulHearts.MOD_ID, "chilled_vanilla");
            HeartDrawing vanilla = new HeartDrawing() {

                @Override
                public void draw(GuiGraphics guiGraphics, int x, int y, boolean half, boolean hardcore, boolean highlight) {
                    RenderSystem.enableBlend();
                    guiGraphics.blit(ICONS_TEXTURE, x, y, half ? 9 : 0, 0, 9, 9);
                    RenderSystem.disableBlend();
                }

                @Override
                public ResourceLocation getId() {
                    return heartId;
                }
            };

            event.registerOverlayHeart(OverlayHeart.build(new ResourceLocation(CColorfulHearts.MOD_ID, "chilled"), player -> player.hasEffect(effectHolder.get()))
                    .addHealth(vanilla, 0.4f, 0.4f, 0.45f)
                    .addAbsorption(
                            HeartDrawing.colorBlend(vanilla, heartId.withSuffix("_absorption"), 1.0f, 1.0f, 1.0f, 0.15f, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA),
                            HeartDrawing.colorBlend(vanilla, heartId.withSuffix("_absorption_2"), 0.85f, 0.85f, 1.0f, 0.5f, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA)
                    ).finish()
            );
            CColorfulHearts.LOGGER.debug("Registered custom hearts for chilled from mod eidolon");
        });
    }
}
