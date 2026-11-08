# Cozy Companion Setup 1.0.0

Original: [Complementary Unbound r5.9.3](https://www.complementary.dev/) von EminGT / Complementary Development, Complementary License Agreement 1.7. Das Original wird unverändert über seine offizielle Modrinth-Datei installiert. **Dieses Repository lädt keine vollständige veränderte Complementary-ZIP hoch.**

Eigener MIT-Helper: ein Fabric-PreLaunch-Einstieg ohne Mixins, Render-Hooks oder Hintergrundarbeit. Er erstellt bei fehlender Datei lokal `shaderpacks/Cozy-Companion-r3.zip` aus dem per SHA256 gepinnten Original. Beigefügt sind nur die beiden eigenen Optionsrezepte und der kleine Koordinaten-Ersatz. Original, Lizenz und sonstige Programme werden unverändert übernommen. Bestehende eigene oder beschädigte Cozy-Dateien werden erhalten; Fehler blockieren den Spielstart nicht.

Der End-Voxy-Koordinatenfix ersetzt die eine ungültige `texCoord`-Abfrage im Volumetrik-Helfer durch normalisierte Fragmentkoordinaten. Zwei zusätzliche Profile heißen **Cozy - Normal** und **Cozy - Pixelated**. Andere Shader, Optionen und Tastaturbelegungen werden nicht verändert.

Prüfung: frische Rekonstruktion aller 468 Einträge bytegleich zur vom Nutzer gelieferten r3-ZIP; Wiederholung ohne Neuschreiben, fehlendes/falsches Original und Erhalt einer eigenen Datei geprüft. Die Gesamt-ZIP-Prüfsumme darf sich durch die lokale ZIP-Kompression unterscheiden. Spieltests und deren Grenzen: [PACK-STATUS.md](../../PACK-STATUS.md).

Bauen: Java/JDK 25, Fabric Loader 0.20.1 als Compiler-Classpath; `javac --release 25 -cp fabric-loader-0.20.1.jar -d classes dev/everness/cozy/CozySetup.java`. In die JAR gehören die Klassen, Fabric-Metadaten mit `preLaunch`-Entrypoint sowie `cozy/NORMAL.txt` und `cozy/PIXELATED.txt`. Die ausgelieferte JAR enthält keine Shaderprogramme oder Shadertexturen von Complementary.
