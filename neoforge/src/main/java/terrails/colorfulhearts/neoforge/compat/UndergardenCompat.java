package terrails.colorfulhearts.neoforge.compat;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import quek.undergarden.Undergarden;
import quek.undergarden.registry.UGEffects;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.api.heart.drawing.SpriteHeartDrawing;
import terrails.colorfulhearts.api.heart.drawing.StatusEffectHeart;
import terrails.colorfulhearts.neoforge.api.event.NeoHeartRegistryEvent;

public class UndergardenCompat {

    private static final SpriteHeartDrawing VIRULENCE_SPRITE_DRAWING = SpriteHeartDrawing.build(new ResourceLocation(Undergarden.MODID, "virulence_hearts")).finish(
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/normal"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/normal_blinking"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/half"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/half_blinking"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/hardcore"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/hardcore_blinking"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/hardcore_half"),
            new ResourceLocation(Undergarden.MODID, "virulence_hearts/hardcore_half_blinking")
    );

    public UndergardenCompat(IEventBus bus) {
        NeoForge.EVENT_BUS.addListener(this::cancelOverlay);
        bus.addListener(this::registerEffectHeart);
    }

    private void cancelOverlay(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(new ResourceLocation(Undergarden.MODID, "virulence_hearts"))) {
            event.setCanceled(true);
        }
    }

    public void registerEffectHeart(final NeoHeartRegistryEvent event) {
        CColorfulHearts.LOGGER.info("Registering custom hearts for virulence from mod undergarden");
        event.registerStatusEffectHeart(StatusEffectHeart.build(new ResourceLocation(CColorfulHearts.MOD_ID, "virulence"), player -> player.hasEffect(UGEffects.VIRULENCE))
                .addHealth(VIRULENCE_SPRITE_DRAWING, 0.6f, 0.6f, 0.6f).addAbsorption(VIRULENCE_SPRITE_DRAWING, 0.6f, 0.6f, 0.6f).finish()
        );
        CColorfulHearts.LOGGER.debug("Registered custom hearts for virulence from mod undergarden");
    }
}
