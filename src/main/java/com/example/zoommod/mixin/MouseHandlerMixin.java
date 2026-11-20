package com.example.zoommod.mixin;

import com.example.zoommod.ZoomHandler;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void zoommod$scrollToZoom(long window, double xOffset, double yOffset, CallbackInfo ci) {
        if (ZoomHandler.isZooming() && yOffset != 0.0) {
            ZoomHandler.onScroll(yOffset);
            ci.cancel(); // stops the hotbar from scrolling while zooming
        }
    }
}
