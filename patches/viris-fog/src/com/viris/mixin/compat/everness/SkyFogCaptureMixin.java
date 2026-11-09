package com.viris.mixin.compat.everness;
import com.viris.compat.everness.SkyFogUniform;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(FogRenderer.class)
public abstract class SkyFogCaptureMixin {
    @Inject(method="updateBuffer(Lnet/minecraft/client/renderer/fog/FogData;)V",at=@At("HEAD"),require=1)
    private void everness$capture(FogData data,CallbackInfo ci){SkyFogUniform.capture(data);}
}
