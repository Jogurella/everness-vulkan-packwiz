package com.viris.compat.everness;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
public final class ChestCompatPreLaunch implements PreLaunchEntrypoint {
 @Override public void onPreLaunch(){
  if(!FabricLoader.getInstance().isModLoaded("enhancedblockentities"))return;
  try {System.out.println("[Everness chest compatibility] "+ChestConfigMigration.migrate(FabricLoader.getInstance().getGameDir()));}
  catch(Exception e){System.getLogger("Everness chest compatibility").log(System.Logger.Level.WARNING,"Could not migrate EBE chest setting; startup continues",e);}
 }
}
