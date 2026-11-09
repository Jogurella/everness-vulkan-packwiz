package com.viris.compat.everness;

import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.Pattern;

/** Preserve night vision and all other bytes; stop only the separate fog-color boost once. */
public final class FogConfigMigration {
    private FogConfigMigration() {}
    public static String migrate(Path game)throws IOException {
        Path root=game.toAbsolutePath().normalize(),marker=root.resolve(".everness/compat-migrations/natural-fog-colors-v1.done"),config=root.resolve("config/gammautils.json");
        if(Files.exists(marker))return "ALREADY_CHECKED";
        if(!Files.isRegularFile(config))return "NOT_APPLICABLE";
        if(Files.size(config)>524288)return "OVERSIZE_SKIP";
        byte[] before=Files.readAllBytes(config);String text=new String(before,StandardCharsets.UTF_8);
        boolean enabled;
        try {
            var rootJson=JsonParser.parseString(text).getAsJsonObject();var night=rootJson.getAsJsonObject("nightVision");
            if(night==null||!night.has("brightenFogColor")||!night.get("brightenFogColor").isJsonPrimitive()||!night.getAsJsonPrimitive("brightenFogColor").isBoolean())return "NO_SUPPORTED_KEY";
            enabled=night.get("brightenFogColor").getAsBoolean();
        } catch(RuntimeException e){return "INVALID_JSON_SKIP";}
        var key=Pattern.compile("(\"brightenFogColor\"[\\t\\r\\n ]*:[\\t\\r\\n ]*)(true|false)\\b").matcher(text);
        if(!key.find())return "NO_SUPPORTED_KEY";
        String prefix=key.group(1),value=key.group(2);
        if(key.find())return "AMBIGUOUS_SKIP";
        if(enabled!=Boolean.parseBoolean(value))return "AMBIGUOUS_SKIP";
        String result="ALREADY_OFF";
        if(enabled){
            key.reset();byte[] after=key.replaceFirst(java.util.regex.Matcher.quoteReplacement(prefix+"false")).getBytes(StandardCharsets.UTF_8);
            Path backups=root.resolve(".everness/compat-backups");Files.createDirectories(backups);
            Files.write(backups.resolve("gamma-fog-before-"+Long.toUnsignedString(System.nanoTime())+".json"),before,StandardOpenOption.CREATE_NEW);
            Path temp=Files.createTempFile(config.getParent(),".everness-fog-",".tmp");
            try{
                Files.write(temp,after);
                try{Files.move(temp,config,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(AtomicMoveNotSupportedException e){Files.move(temp,config,StandardCopyOption.REPLACE_EXISTING);}
                if(!java.util.Arrays.equals(Files.readAllBytes(config),after))throw new IOException("Fog config readback mismatch");
            }finally{Files.deleteIfExists(temp);}
            result="MIGRATED_WITH_BACKUP";
        }
        Files.createDirectories(marker.getParent());Files.writeString(marker,result+"\nOnly nightVision.brightenFogColor changed. Night vision and brightness remain available; later explicit changes are respected.\n",StandardCharsets.UTF_8,StandardOpenOption.CREATE_NEW);
        return result;
    }
}
