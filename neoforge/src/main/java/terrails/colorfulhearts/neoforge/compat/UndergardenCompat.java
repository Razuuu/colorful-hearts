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
import terrails.colorfulhearts.api.neoforge.event.NeoHeartRegistryEvent;

public class UndergardenCompat {

    private static final ResourceLocation VIRULENCE_OVERLAY = ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts");

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
        BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.fromNamespaceAndPath("undergarden", "virulence")).ifPresent(effectHolder -> {
            CColorfulHearts.LOGGER.info("Registering custom hearts for virulence from mod undergarden");

            final ResourceLocation heartId = CColorfulHearts.location("virulence_vanilla");
            HeartDrawing vanilla = SpriteHeartDrawing.build(ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts")).finish(
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/normal"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/normal_blinking"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/half"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/half_blinking"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/hardcore"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/hardcore_blinking"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/hardcore_half"),
                    ResourceLocation.fromNamespaceAndPath("undergarden", "virulence_hearts/hardcore_half_blinking")
            );

            event.registerOverlayHeart(OverlayHeart.build(CColorfulHearts.location("virulence"), player -> player.hasEffect(effectHolder))
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
