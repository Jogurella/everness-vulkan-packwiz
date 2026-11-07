package org.polyfrost.evergreenhud.client.hud.direction;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.polyfrost.oneconfig.api.hud.v1.HudManager;

/** One visible entry point, exclusively in Minecraft's Options screen. */
public final class HudSettingsButton implements ClientModInitializer {
    @Override public void onInitializeClient(){
        ScreenEvents.AFTER_INIT.register((client,screen,width,height)->{
            if(!(screen instanceof OptionsScreen))return;
            if(Screens.getWidgets(screen).stream().anyMatch(w->w.getMessage().equals(Component.translatable("everness.hud.settings"))))return;
            int x=width>=480?width/2+110:5;
            int y=width>=480?height-27:5;
            Button button=Button.builder(Component.translatable("everness.hud.settings"),b->{
                client.gui.setScreen(null);HudManager.INSTANCE.openEditor();
            }).bounds(x,y,110,20).build();
            Screens.getWidgets(screen).add(button);
        });
    }
}
