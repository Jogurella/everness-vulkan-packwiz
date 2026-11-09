package com.viris.mixin.compat.everness;

import com.viris.Viris;
import com.viris.compat.everness.NetherFogPolicy;
import com.viris.pack.VitrailBridge;
import me.cortex.voxy.client.config.VoxyConfig;
import me.cortex.voxy.client.core.IVoxyRenderSystemHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AtmosphericFogEnvironment.class)
public abstract class NetherAtmosphereMixin {
    private static boolean everness$logged;
    @Inject(method="setupFog",at=@At("RETURN"))
    private void everness$netherDistance(FogData fog,Camera camera,ClientLevel level,float viewDistance,DeltaTracker delta,CallbackInfo ci) {
        if(!Viris.isVulkan()||!level.dimension().equals(Level.NETHER)||!VoxyConfig.CONFIG.isRenderingEnabled()||IVoxyRenderSystemHolder.getNullable()==null||VitrailBridge.pack()!=null)return;
        FogType fluid=camera.getFluidInCamera();
        if(fluid==FogType.LAVA||fluid==FogType.WATER||fluid==FogType.POWDER_SNOW)return;
        Minecraft mc=Minecraft.getInstance();
        if(mc.player==null||mc.player.hasEffect(MobEffects.BLINDNESS)||mc.player.hasEffect(MobEffects.DARKNESS)||mc.gui.hud.getBossOverlay().shouldCreateWorldFog())return;
        var range=NetherFogPolicy.extend(fog.environmentalStart,fog.environmentalEnd,mc.options.renderDistance().get(),VoxyConfig.CONFIG.sectionRenderDistance);
        if(range.end()!=fog.environmentalEnd){
            fog.environmentalStart=range.start();fog.environmentalEnd=range.end();
            if(!everness$logged){everness$logged=true;Viris.LOGGER.info("VIRIS: Nether atmospheric fog follows the active LOD view, {} blocks, without changing shader/fluid/status-effect fog",range.end());}
        }
    }
}
