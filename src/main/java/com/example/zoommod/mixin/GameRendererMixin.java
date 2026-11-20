package com.example.zoommod.mixin;

import com.example.zoommod.ZoomHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void zoommod$applyZoom(Camera camera, float partialTick, boolean useFovSetting,
                                   CallbackInfoReturnable<Float> cir) {
        // useFovSetting is false for the hand, so only the world view gets zoomed.
        if (!useFovSetting) {
            return;
        }
        double divisor = ZoomHandler.updateAndGetDivisor();
        if (divisor != 1.0) {
            cir.setReturnValue(cir.getReturnValue() / (float) divisor);
        }
    }
}
