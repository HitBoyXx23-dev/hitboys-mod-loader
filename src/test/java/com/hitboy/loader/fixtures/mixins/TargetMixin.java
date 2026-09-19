package com.hitboy.loader.fixtures.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.hitboy.loader.fixtures.MixinTarget", remap = false)
public class TargetMixin {
    @Inject(method = "value", at = @At("HEAD"), cancellable = true, remap = false)
    private void replaceValue(CallbackInfoReturnable<String> callback) {
        callback.setReturnValue("HitBoy Mixin applied");
    }
}
