package com.viris.compat.everness;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
public final class FogCompatPreLaunch implements PreLaunchEntrypoint {
    @Override public void onPreLaunch(){
        if(!FabricLoader.getInstance().isModLoaded("gammautils"))return;
        try{System.out.println("[Everness fog compatibility] "+FogConfigMigration.migrate(FabricLoader.getInstance().getGameDir()));}
        catch(Exception e){System.getLogger("Everness fog compatibility").log(System.Logger.Level.WARNING,"Fog config migration skipped; startup continues",e);}
    }
}
