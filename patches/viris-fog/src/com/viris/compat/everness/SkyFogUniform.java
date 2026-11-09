package com.viris.compat.everness;

import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.viris.Viris;
import com.viris.pack.VitrailBridge;
import me.cortex.voxy.client.config.VoxyConfig;
import me.cortex.voxy.client.core.IVoxyRenderSystemHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.Level;
import org.joml.Vector4f;

/** A sky-only uniform; the disabled terrain-distance fog must not enter the sky disc pass. */
public final class SkyFogUniform implements AutoCloseable {
    private static final Vector4f color=new Vector4f();
    private static float skyEnd,cloudEnd;
    private static boolean valid,logged;
    private MappableRingBuffer buffer;
    private boolean used;

    public static void capture(FogData data){
        valid=Float.isFinite(data.skyEnd)&&data.skyEnd>0;
        if(valid){color.set(data.color);skyEnd=data.skyEnd;cloudEnd=data.cloudEnd;}
    }

    public GpuBufferSlice forSky(GpuBufferSlice original){
        var m=Minecraft.getInstance();
        if(!valid||!Viris.isVulkan()||m.level==null||m.level.dimension()!=Level.OVERWORLD
            ||VitrailBridge.pack()!=null||!VoxyConfig.CONFIG.isRenderingEnabled()
            ||IVoxyRenderSystemHolder.getNullable()==null)return original;
        if(buffer==null)buffer=new MappableRingBuffer(()->"Everness default sky fog",130,FogRenderer.FOG_UBO_SIZE);
        try(var map=buffer.currentBuffer().map(false,true)){
            // Identical to the sky shader's own apply_fog arguments. No terrain or LOD uniform is changed.
            Std140Builder.intoBuffer(map.data()).putVec4(color).putFloat(0).putFloat(skyEnd)
                .putFloat(skyEnd).putFloat(skyEnd).putFloat(skyEnd).putFloat(cloudEnd);
        }
        used=true;
        if(!logged){logged=true;Viris.LOGGER.info("Everness: default sky uses its own fog range; terrain/LOD fog stays separate");}
        return buffer.currentBuffer().slice(0,FogRenderer.FOG_UBO_SIZE);
    }

    public void endSky(){if(used){buffer.rotate();used=false;}}
    @Override public void close(){if(buffer!=null){buffer.close();buffer=null;}used=false;}
}
