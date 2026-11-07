package com.seibel.distanthorizons.core.wrapperInterfaces.modAccessor;

import org.popcraft.chunky.ChunkyProvider;

/** Installed Chunky has no server-side provider while on a remote server. */
public final class ChunkyReadiness {
    private ChunkyReadiness(){}
    public static boolean isReady(){
        try{ChunkyProvider.get();return true;}
        catch(IllegalStateException e){
            if("Chunky is not loaded.".equals(e.getMessage()))return false;
            throw e;
        }
    }
}
