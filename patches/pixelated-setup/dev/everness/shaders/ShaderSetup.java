package dev.everness.shaders;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.zip.*;

/** One-time, local shader migration. No render mixins, network, or in-game tasks. */
public final class ShaderSetup implements PreLaunchEntrypoint {
    private static final String REVISION = "2026.11.08.3";
    private static final String BASE = "ComplementaryUnbound_r5.9.3.zip";
    private static final String PIXEL = "Pixelated.zip";
    private static final String BASE_HASH = "98ac741dee294d2a3c82d153349148c3bb7fb688c30eb3b49c3b9ac37e968c5a";
    private static final Set<String> ALLOWED = Set.of(BASE, PIXEL);
    private static final int MAX_SIZE = 128 * 1024 * 1024;

    @Override public void onPreLaunch() {
        try { System.out.println("[Everness Shader Setup] " + prepare(FabricLoader.getInstance().getGameDir())); }
        catch (Exception error) {
            System.err.println("[Everness Shader Setup] Optionales Shader-Setup fehlgeschlagen; Minecraft startet weiter: " + error);
        }
    }

    private static String hash(byte[] data) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(data));
    }
    private static Path guard(Path root, Path path) throws IOException {
        path = path.toAbsolutePath().normalize();
        if (!path.startsWith(root)) throw new IOException("Pfad ausserhalb des Spielordners");
        Path existing = path;
        while (!Files.exists(existing, LinkOption.NOFOLLOW_LINKS)) existing = existing.getParent();
        if (!existing.toRealPath().startsWith(root.toRealPath())) throw new IOException("Verlinkter Pfad ausserhalb des Spielordners");
        if (Files.isSymbolicLink(path)) throw new IOException("Verlinkte Zieldatei wird nicht veraendert");
        return path;
    }
    private static byte[] resource(String name) throws IOException {
        try (InputStream in = ShaderSetup.class.getResourceAsStream("/shader-setup/" + name)) {
            if (in == null) throw new IOException("Rezept fehlt: " + name);
            return in.readAllBytes();
        }
    }
    private static byte[] exactRead(InputStream in, int count) throws IOException {
        if (count < 0 || count > MAX_SIZE) throw new IOException("Ungueltige Patch-Laenge");
        byte[] data = in.readNBytes(count);
        if (data.length != count) throw new EOFException("Unvollstaendiger Shader-Patch");
        return data;
    }
    private static String safeEntry(String name) throws IOException {
        if (name.isEmpty() || name.startsWith("/") || name.contains("\\") || name.contains(":")) throw new IOException("Ungueltiger Archiveintrag");
        for (String part : name.split("/")) if (part.equals("..")) throw new IOException("Ungueltiger Archiveintrag");
        return name;
    }
    private static LinkedHashMap<String, byte[]> patch(Path source) throws Exception {
        LinkedHashMap<String, byte[]> result = new LinkedHashMap<>();
        try (ZipFile original = new ZipFile(source.toFile());
             DataInputStream in = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(resource("pixelated.evp.gz"))))) {
            if (!Arrays.equals(exactRead(in, 5), new byte[]{'E','V','P','X',1})) throw new IOException("Falsches Patch-Format");
            int entries = in.readInt();
            if (entries < 1 || entries > 20000) throw new IOException("Ungueltige Anzahl Archiveintraege");
            long total = 0;
            for (int i = 0; i < entries; i++) {
                String name = safeEntry(in.readUTF());
                String expected = in.readUTF();
                ZipEntry oldEntry = original.getEntry(name);
                byte[] old = new byte[0];
                if (oldEntry != null) {
                    if (oldEntry.getSize() > MAX_SIZE) throw new IOException("Originaleintrag zu gross");
                    try (InputStream stream = original.getInputStream(oldEntry)) { old = stream.readAllBytes(); }
                }
                ByteArrayOutputStream made = new ByteArrayOutputStream();
                int ops = in.readInt();
                if (ops < 0 || ops > 1000000) throw new IOException("Ungueltige Anzahl Patch-Schritte");
                for (int j = 0; j < ops; j++) {
                    int type = in.readUnsignedByte();
                    if (type == 0) {
                        int start = in.readInt(), length = in.readInt();
                        if (start < 0 || length < 0 || (long)start + length > old.length) throw new IOException("Patch passt nicht zum Original");
                        made.write(old, start, length);
                    } else if (type == 1) {
                        made.write(exactRead(in, in.readInt()));
                    } else throw new IOException("Unbekannter Patch-Schritt");
                    if (made.size() > MAX_SIZE) throw new IOException("Patch-Eintrag zu gross");
                }
                byte[] value = made.toByteArray();
                if (!hash(value).equals(expected)) throw new IOException("Patch-Pruefsumme falsch: " + name);
                if (result.put(name, value) != null) throw new IOException("Doppelter Archiveintrag");
                total += value.length;
                if (total > 256L * 1024 * 1024) throw new IOException("Shader-Patch zu gross");
            }
            if (in.read() != -1) throw new IOException("Unerwartete Daten am Patch-Ende");
        }
        return result;
    }
    private static boolean matches(Path file, Map<String,byte[]> rows) {
        if (!Files.isRegularFile(file)) return false;
        try (ZipFile zip = new ZipFile(file.toFile())) {
            if (zip.size() != rows.size()) return false;
            for (var row : rows.entrySet()) {
                ZipEntry entry = zip.getEntry(row.getKey());
                if (entry == null || entry.getSize() > MAX_SIZE) return false;
                try (InputStream in = zip.getInputStream(entry)) {
                    if (!Arrays.equals(row.getValue(), in.readAllBytes())) return false;
                }
            }
            return true;
        } catch (IOException error) { return false; }
    }
    private static void backup(Path game, Path backup, Path source) throws IOException {
        if (!Files.exists(source, LinkOption.NOFOLLOW_LINKS)) return;
        guard(game, source);
        if (!Files.isRegularFile(source)) throw new IOException("Einstellungsziel ist keine normale Datei");
        Path saved = guard(game, backup.resolve(game.relativize(source)));
        Files.createDirectories(saved.getParent());
        Files.copy(source, saved);
    }
    private static void atomicWrite(Path game, Path file, byte[] data) throws IOException {
        guard(game, file); Files.createDirectories(file.getParent());
        Path tmp = Files.createTempFile(file.getParent(), ".everness-", ".pending");
        try {
            Files.write(tmp, data);
            try { Files.move(tmp, file, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
            catch (AtomicMoveNotSupportedException e) { Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING); }
        } finally { Files.deleteIfExists(tmp); }
    }
    private static String choosePack(String value) {
        return ALLOWED.contains(value) ? value : PIXEL;
    }
    private static void migrateSelection(Path game, Path backup, String relative, String key) throws Exception {
        Path file = guard(game, game.resolve(relative));
        if (!Files.isRegularFile(file)) return;
        String old = Files.readString(file);
        StringBuilder changed = new StringBuilder();
        boolean found = false;
        for (String line : old.split("\\R", -1)) {
            if (line.startsWith(key + "=")) {
                line = key + "=" + choosePack(line.substring(key.length() + 1)); found = true;
            }
            changed.append(line).append('\n');
        }
        if (!found) return;
        byte[] data = changed.toString().getBytes(StandardCharsets.UTF_8);
        // Do not rewrite an already valid selection just to normalize line endings.
        Properties props = new Properties(); props.load(new StringReader(old));
        if (ALLOWED.contains(props.getProperty(key))) return;
        backup(game, backup, file); atomicWrite(game, file, data);
    }

    public static String prepare(Path selectedGame) throws Exception {
        Path game = selectedGame.toAbsolutePath().normalize();
        if (!Files.isDirectory(game)) throw new IOException("Spielordner fehlt");
        Path shaders = guard(game, game.resolve("shaderpacks"));
        Path source = guard(game, shaders.resolve(BASE));
        Path target = guard(game, shaders.resolve(PIXEL));
        Path state = guard(game, game.resolve(".everness-shader-setup"));
        Path marker = guard(game, state.resolve(REVISION + ".done"));
        if (Files.isRegularFile(marker) && Files.isRegularFile(target)) return "Bereits eingerichtet; persoenliche Einstellungen bleiben erhalten.";
        if (!Files.isRegularFile(source)) return "Offizielles Complementary-Original fehlt; bestehende Shader bleiben unveraendert.";
        if (!hash(Files.readAllBytes(source)).equals(BASE_HASH)) return "Andere Complementary-Version; bestehende Shader bleiben unveraendert.";
        Map<String,byte[]> expected = patch(source); // Validate the complete recipe before changing any file.
        Path backup = guard(game, state.resolve("backups/" + REVISION + "-" + UUID.randomUUID()));
        boolean generated = !matches(target, expected);
        if (generated) {
            Path temporary = Files.createTempFile(shaders, ".pixelated-", ".pending");
            try {
                try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(temporary))) {
                    for (var row : expected.entrySet()) {
                        ZipEntry entry = new ZipEntry(row.getKey()); entry.setTime(0);
                        out.putNextEntry(entry); out.write(row.getValue()); out.closeEntry();
                    }
                }
                if (!matches(temporary, expected)) throw new IOException("Erstelltes Shaderpack unvollstaendig");
                backup(game, backup, target);
                Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
            } finally { Files.deleteIfExists(temporary); }
        }
        if (Files.isRegularFile(marker)) return "Fehlendes Pixelated-Archiv lokal wiederhergestellt.";

        // User explicitly requested these exact sidecars for both backends.
        for (String name : List.of(PIXEL + ".txt", BASE + ".txt")) {
            Path dst = guard(game, shaders.resolve(name)); byte[] wanted = resource(name);
            if (!Files.isRegularFile(dst) || !Arrays.equals(wanted, Files.readAllBytes(dst))) {
                backup(game, backup, dst); atomicWrite(game, dst, wanted);
            }
        }
        migrateSelection(game, backup, "config/iris.properties", "shaderPack");
        migrateSelection(game, backup, "vitrail/pack.txt", "pack");

        // Remove only overlapping shader overrides; unrelated Vitrail settings are preserved.
        Path overrides = guard(game, game.resolve("vitrail/options.txt"));
        if (Files.isRegularFile(overrides)) {
            Set<String> keys = new HashSet<>();
            for (String name : List.of(PIXEL + ".txt", BASE + ".txt")) {
                Properties p = new Properties(); p.load(new StringReader(new String(resource(name), StandardCharsets.UTF_8)));
                keys.addAll(p.stringPropertyNames());
            }
            String old = Files.readString(overrides); StringBuilder kept = new StringBuilder(); boolean changed = false;
            for (String line : old.split("\\R", -1)) {
                int equals = line.indexOf('=');
                if (equals > 0 && keys.contains(line.substring(0, equals).trim())) changed = true;
                else kept.append(line).append('\n');
            }
            if (changed) { backup(game, backup, overrides); atomicWrite(game, overrides, kept.toString().getBytes(StandardCharsets.UTF_8)); }
        }

        int retired = 0;
        try (var entries = Files.list(shaders)) {
            for (Path file : entries.sorted().toList()) {
                String name = file.getFileName().toString(); String lower = name.toLowerCase(Locale.ROOT);
                if (ALLOWED.contains(name) || ALLOWED.stream().anyMatch(n -> (n + ".txt").equals(name))) continue;
                boolean archive = Files.isRegularFile(file) && (lower.endsWith(".zip") || lower.endsWith(".zip.txt"));
                boolean unpacked = Files.isDirectory(file) && Files.isDirectory(file.resolve("shaders"));
                if (!archive && !unpacked) continue;
                guard(game, file);
                Path saved = guard(game, backup.resolve("shaderpacks").resolve(name));
                Files.createDirectories(saved.getParent());
                Files.move(file, saved); retired++;
            }
        }
        String report = "revision=" + REVISION + "\npixelatedEntries=" + expected.size() + "\nretired=" + retired + "\nbackup=" + backup + "\n";
        atomicWrite(game, marker, report.getBytes(StandardCharsets.UTF_8));
        return "Pixelated " + (generated ? "lokal gepatcht" : "vollstaendig vorhanden") + "; genau zwei Shader eingerichtet, " + retired + " alte Eintraege gesichert. Backup: " + backup;
    }
    public static void main(String[] args) throws Exception { System.out.println(prepare(Path.of(args[0]))); }
}
