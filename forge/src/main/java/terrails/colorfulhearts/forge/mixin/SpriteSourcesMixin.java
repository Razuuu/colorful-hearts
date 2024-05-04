package terrails.colorfulhearts.forge.mixin;

import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terrails.colorfulhearts.CColorfulHearts;
import terrails.colorfulhearts.render.atlas.sources.ColoredHearts;

@Mixin(SpriteSources.class)
public class SpriteSourcesMixin {

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void colorfulhearts_spriteRegistration(CallbackInfo info) {
        ColoredHearts.COLORED_HEARTS = SpriteSources.register(new ResourceLocation(CColorfulHearts.MOD_ID, "colored_hearts").toString(), ColoredHearts.CODEC);
    }
}
