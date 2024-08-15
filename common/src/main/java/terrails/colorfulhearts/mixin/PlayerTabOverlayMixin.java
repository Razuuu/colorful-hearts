package terrails.colorfulhearts.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terrails.colorfulhearts.render.TabHeartRenderer;

import java.util.UUID;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {

    @Inject(method = "renderTablistHearts", cancellable = true,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    opcode = 0)
    )
    private void colorfulhearts_renderTablistHearts(
            int y, int x, int offset, UUID playerUuid, GuiGraphics guiGraphics, int scoreValue,
            CallbackInfo ci, @Local PlayerTabOverlay.HealthState healthState
    ) {
        // this handles just 2 rows and then uses vanilla NNhp format (default behaviour)
        TabHeartRenderer.INSTANCE.renderPlayerListHud(y, x, offset, guiGraphics, scoreValue, healthState);
        ci.cancel();
    }
}
