package terrails.colorfulhearts.neoforge.compat;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.opengl.GL11;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.SpriteHeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;
import terrails.colorfulhearts.neoforge.api.event.NeoHeartRegistryEvent;

public class UndergardenCompat {

    private static final ResourceLocation VIRULENCE_OVERLAY = new ResourceLocation("undergarden", "virulence_hearts");

    public UndergardenCompat(IEventBus bus) {
        NeoForge.EVENT_BUS.addListener(this::cancelOverlay);
        bus.addListener(this::registerEffectHeart);
    }

    private void cancelOverlay(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(VIRULENCE_OVERLAY)) {
            event.setCanceled(true);
        }
    }

    public void registerEffectHeart(final NeoHeartRegistryEvent event) {
        BuiltInRegistries.MOB_EFFECT.getHolder(new ResourceLocation("undergarden", "virulence")).ifPresent(effectHolder -> {
            CColorfulHearts.LOGGER.info("Registering custom hearts for virulence from mod undergarden");

            final ResourceLocation heartId = new ResourceLocation(CColorfulHearts.MOD_ID, "virulence_vanilla");
            HeartDrawing vanilla = SpriteHeartDrawing.build(new ResourceLocation("undergarden", "virulence_hearts")).finish(
                    new ResourceLocation("undergarden", "virulence_hearts/normal"),
                    new ResourceLocation("undergarden", "virulence_hearts/normal_blinking"),
                    new ResourceLocation("undergarden", "virulence_hearts/half"),
                    new ResourceLocation("undergarden", "virulence_hearts/half_blinking"),
                    new ResourceLocation("undergarden", "virulence_hearts/hardcore"),
                    new ResourceLocation("undergarden", "virulence_hearts/hardcore_blinking"),
                    new ResourceLocation("undergarden", "virulence_hearts/hardcore_half"),
                    new ResourceLocation("undergarden", "virulence_hearts/hardcore_half_blinking")
            );

            event.registerStatusEffectHeart(OverlayHeart.build(new ResourceLocation(CColorfulHearts.MOD_ID, "virulence"), player -> player.hasEffect(effectHolder))
                    .addHealth(vanilla, 0.45f, 0.4f, 0.4f)
                    .addAbsorption(
                            HeartDrawing.colorBlend(vanilla, heartId.withSuffix("_absorption"), 1.0f, 1.0f, 1.0f, 0.15f, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA),
                            HeartDrawing.colorBlend(vanilla, heartId.withSuffix("_absorption_2"), 1.0f, 0.85f, 0.85f, 0.5f, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA)
                    ).finish()
            );
            CColorfulHearts.LOGGER.debug("Registered custom hearts for virulence from mod undergarden");
        });
    }
}
