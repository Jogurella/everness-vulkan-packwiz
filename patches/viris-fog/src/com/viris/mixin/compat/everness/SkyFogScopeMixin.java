package com.viris.mixin.compat.everness;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.viris.compat.everness.SkyFogUniform;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(SkyRenderer.class)
public abstract class SkyFogScopeMixin {
    @Unique private final SkyFogUniform everness$skyFog=new SkyFogUniform();
    @ModifyVariable(method="render",at=@At("HEAD"),argsOnly=true,ordinal=0,require=1)
    private GpuBufferSlice everness$skyOnly(GpuBufferSlice original){return everness$skyFog.forSky(original);}
    @Inject(method="render",at=@At("RETURN"),require=1)
    private void everness$end(CallbackInfo ci){everness$skyFog.endSky();}
    @Inject(method="close",at=@At("HEAD"),require=1)
    private void everness$close(CallbackInfo ci){everness$skyFog.close();}
}
