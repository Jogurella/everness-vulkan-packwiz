package dev.everness.cozy;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.zip.*;

/** Builds the user's local Cozy recipe from a pinned, separately installed original. */
public final class CozySetup implements PreLaunchEntrypoint {
    private static final String BASE = "ComplementaryUnbound_r5.9.3.zip";
    private static final String TARGET = "Cozy-Companion-r3.zip";
    private static final String SHA256 = "98ac741dee294d2a3c82d153349148c3bb7fb688c30eb3b49c3b9ac37e968c5a";
    private static final String FIX = "shaders/lib/atmospherics/volumetricLight/volumetricLight.glsl";
    private static final String NEEDLE = "texture2D(colortex13, texCoord)";
    private static final String REPLACEMENT = "texture2D(colortex13, gl_FragCoord.xy / vec2(viewWidth, viewHeight))";

    @Override public void onPreLaunch() {
        try { System.out.println("[Cozy Setup] " + prepare(FabricLoader.getInstance().getGameDir())); }
        catch (Exception error) {
            // An optional shader must never prevent Minecraft from starting.
            System.err.println("[Cozy Setup] Cozy konnte nicht vorbereitet werden; vorhandene Dateien bleiben erhalten: " + error.getMessage());
        }
    }

    public static String prepare(Path gameDirectory) throws Exception {
        Path dir = gameDirectory.toAbsolutePath().normalize().resolve("shaderpacks");
        Path source = dir.resolve(BASE), target = dir.resolve(TARGET);
        if (!Files.isRegularFile(source)) return "Original fehlt; Shaderauswahl unveraendert: " + BASE;
        String actual = HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(source)));
        if (!SHA256.equals(actual)) return "Andere Originalversion; keine Shaderdatei veraendert.";
        Map<String,byte[]> expected = recipe(source);
        if (Files.exists(target)) {
            try (ZipFile old = new ZipFile(target.toFile())) {
                if (old.size() != expected.size()) return "Eigene Cozy-Kopie wird erhalten (andere Eintraege).";
                for (var row : expected.entrySet()) {
                    ZipEntry entry = old.getEntry(row.getKey());
                    if (entry == null) return "Eigene Cozy-Kopie wird erhalten (fehlender Eintrag).";
                    try (InputStream stream = old.getInputStream(entry)) {
                        if (!Arrays.equals(row.getValue(), stream.readAllBytes())) return "Eigene Cozy-Kopie wird erhalten (geaenderter Inhalt).";
                    }
                }
            } catch (IOException error) { return "Vorhandene Cozy-Datei nicht lesbar; sie wurde nicht ueberschrieben."; }
            return "Cozy r3 bereits vollstaendig vorhanden.";
        }
        Files.createDirectories(dir);
        Path temporary = Files.createTempFile(dir, ".cozy-r3-", ".building");
        try {
            try (ZipFile original = new ZipFile(source.toFile()); ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(temporary))) {
                for (var row : expected.entrySet()) {
                    ZipEntry old = original.getEntry(row.getKey());
                    ZipEntry fresh = new ZipEntry(row.getKey());
                    if (old.getTime() >= 0) fresh.setTime(old.getTime());
                    out.putNextEntry(fresh);out.write(row.getValue());out.closeEntry();
                }
            }
            // No replacement flag: user files created concurrently are preserved.
            Files.move(temporary, target);
        } finally { Files.deleteIfExists(temporary); }
        return "Cozy r3 lokal aus dem offiziellen Complementary-Original erstellt.";
    }

    private static Map<String,byte[]> recipe(Path source) throws Exception {
        Map<String,byte[]> rows = new LinkedHashMap<>();
        try (ZipFile zip = new ZipFile(source.toFile())) {
            for (var entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = entries.nextElement();
                try (InputStream stream = zip.getInputStream(entry)) { rows.put(entry.getName(), stream.readAllBytes()); }
            }
        }
        String code = text(rows, FIX);
        if (code.indexOf(NEEDLE) < 0 || code.indexOf(NEEDLE) != code.lastIndexOf(NEEDLE)) throw new IOException("Kompatibilitaetsstelle ist nicht eindeutig.");
        rows.put(FIX, bytes(code.replace(NEEDLE, REPLACEMENT)));
        List<String> lines = new ArrayList<>();
        for (String preset : List.of("NORMAL", "PIXELATED")) {
            List<String> tokens = new ArrayList<>();
            for (String line : resource("/cozy/" + preset + ".txt").split("\\R")) {
                if (!line.matches("[A-Za-z_][A-Za-z_0-9]*=.+")) continue;
                String[] pair = line.split("=",2);String value = pair[1].trim();
                tokens.add(value.equals("true") ? pair[0] : value.equals("false") ? "!"+pair[0] : pair[0]+"="+value);
            }
            lines.add("profile.COZY_"+preset+"="+String.join(" ",tokens));
        }
        String properties = "shaders/shaders.properties";
        rows.put(properties, bytes(text(rows,properties)+"\n# Local Cozy visual presets; original quality profiles remain available.\n"+String.join("\n",lines)+"\n"));
        for (String lang : List.of("en_US","de_DE")) {
            String path = "shaders/lang/"+lang+".lang";
            if (rows.containsKey(path)) rows.put(path,bytes(text(rows,path)+"\nprofile.COZY_NORMAL=Cozy - Normal\nprofile.COZY_PIXELATED=Cozy - Pixelated\n"));
        }
        return rows;
    }
    private static String text(Map<String,byte[]> rows,String path) throws IOException {
        if (!rows.containsKey(path)) throw new IOException("Originaleintrag fehlt: "+path);
        return new String(rows.get(path),StandardCharsets.UTF_8);
    }
    private static String resource(String name) throws IOException {
        try (InputStream in = CozySetup.class.getResourceAsStream(name)) {
            if (in == null) throw new IOException("Rezept fehlt: "+name);
            return new String(in.readAllBytes(),StandardCharsets.UTF_8);
        }
    }
    private static byte[] bytes(String s) { return s.getBytes(StandardCharsets.UTF_8); }
    public static void main(String[] args) throws Exception { System.out.println(prepare(Path.of(args[0]))); }
}
