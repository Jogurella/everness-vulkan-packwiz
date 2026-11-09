package com.viris.compat.everness;

/** Distance policy only; the caller excludes fluids, status effects, bosses and shader packs. */
public final class NetherFogPolicy {
    private NetherFogPolicy() {}
    public record Range(float start,float end) {}
    public static Range extend(float start,float end,int vanillaChunks,float voxySections) {
        if(!Float.isFinite(start)||!Float.isFinite(end)||end<=0||start>=end||vanillaChunks<=0||!Float.isFinite(voxySections)||voxySections<=0)return new Range(start,end);
        float requested=Math.max(512f,vanillaChunks*64f);
        float limit=Math.min(2048f,voxySections*512f);
        float target=Math.min(requested,limit);
        if(target<=end)return new Range(start,end);
        return new Range(start*(target/end),target);
    }
}
