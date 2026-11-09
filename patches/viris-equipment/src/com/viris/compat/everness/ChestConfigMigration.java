package com.viris.compat.everness;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Locale;
import java.util.regex.Pattern;

/** One targeted upgrade of the old Everness EBE default, before EBE builds its resources. */
public final class ChestConfigMigration {
    private ChestConfigMigration() {}
    public static String migrate(Path game) throws IOException {
        Path root=game.toAbsolutePath().normalize();
        Path marker=root.resolve(".everness/compat-migrations/ebe-original-chests-v1.done");
        if(Files.exists(marker))return "ALREADY_CHECKED";
        Path options=root.resolve("options.txt"), config=root.resolve("config/enhanced_bes.properties");
        if(!Files.isRegularFile(options)||!Files.isRegularFile(config))return "NOT_APPLICABLE";
        if(Files.size(options)>1048576||Files.size(config)>131072)return "OVERSIZE_SKIP";
        String active=Files.readAllLines(options,StandardCharsets.UTF_8).stream().filter(l->l.startsWith("resourcePacks:")).findFirst().orElse("").toLowerCase(Locale.ROOT);
        if(!active.contains("fa+objects"))return "NOT_APPLICABLE";
        byte[] before=Files.readAllBytes(config);
        String text=new String(before,StandardCharsets.UTF_8);
        var key=Pattern.compile("(?m)^([\\t ]*render_enhanced_chests[\\t ]*=[\\t ]*)(true|false)(?=[\\t ]*(?:$|[#!]))").matcher(text);
        if(!key.find())return "NO_SUPPORTED_KEY";
        String prefix=key.group(1), value=key.group(2);
        if(key.find())return "AMBIGUOUS_SKIP";
        String result="ALREADY_OFF";
        if("true".equals(value)) {
            key.reset();
            byte[] after=key.replaceFirst(java.util.regex.Matcher.quoteReplacement(prefix+"false")).getBytes(StandardCharsets.UTF_8);
            Path backups=root.resolve(".everness/compat-backups");Files.createDirectories(backups);
            Path backup=backups.resolve("ebe-before-original-chests-"+Long.toUnsignedString(System.nanoTime())+".properties");
            Files.write(backup,before,StandardOpenOption.CREATE_NEW);
            Path temp=Files.createTempFile(config.getParent(),".everness-chests-",".tmp");
            try {
                Files.write(temp,after);
                try {Files.move(temp,config,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}
                catch(AtomicMoveNotSupportedException e){Files.move(temp,config,StandardCopyOption.REPLACE_EXISTING);}
                if(!java.util.Arrays.equals(Files.readAllBytes(config),after))throw new IOException("Chest config readback mismatch");
            } finally {Files.deleteIfExists(temp);}
            result="MIGRATED_WITH_BACKUP";
        }
        Files.createDirectories(marker.getParent());
        Files.writeString(marker,result+"\nOnly render_enhanced_chests was changed. Later explicit user edits are respected.\n",StandardCharsets.UTF_8,StandardOpenOption.CREATE_NEW);
        return result;
    }
}
