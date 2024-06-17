package terrails.colorfulhearts.forge.compat;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.api.forge.event.ForgeHeartRegistryEvent;

public class EidolonRepraisedCompat {

    private static final ResourceLocation EIDOLON_HEARTS = new ResourceLocation("eidolon", "hearts");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation("eidolon", "textures/gui/icons.png");

    public EidolonRepraisedCompat(final IEventBus bus) {
        bus.addListener(this::registerEffectHeart);
        MinecraftForge.EVENT_BUS.addListener(this::cancelOverlay);
        // TODO: Handle Ethereal Health (how to render those extra hearts?)
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
}
