# Prüfstand 2026.11.08.2: Weather Glass + Cozy Companion

## Nachgewiesen

- Originale Weather-Glass-JAR: `0.6.0+26.3-fabric`, CurseForge-Datei 9508351, clientseitig; Loader-Mindestversion 0.19.5 und Fabric API sind durch den vorhandenen Bestand erfüllt.
- Komplettes Vulkan-Pack mit **68 ausgelieferten Mods**, 29 Ressourcenpacks und 4 direkt geladenen Shaderarchiven. Eine weitere Shader-ZIP (Cozy r3) wird lokal erstellt. Zusätzlich war nur die nicht veröffentlichte Offline-Testmod geladen.
- Minecraft Vulkan-Backend, Cozy-Shader aktiv, Weather Glass geladen, Regen-Glas-Szene und anschließend Schnee/Nacht durchlaufen; **165 Sekunden**, Prozess-Endcode **0**. Im Vulkan-Test war auch VIRIS/Voxy aktiv, im OpenGL-Test Iris; Continuity, MoreCulling und Particle Rain blieben aus dem vollständigen Bestand geladen.
- Shader-Rekonstruktion mit allen **468 Archiv-Einträgen** bytegleich zur Nutzerdatei. Fehlendes/falsches Original und eigene/beschädigte Zielkopie blockieren den Start nicht und werden nicht überschrieben. Wiederholter Start schreibt eine passende Zielkopie nicht erneut.
- Separate Singleplayer-Kopie; keine produktive Welt und kein Server betreten. Vorhandene übrige Einstellungsdateien sind gegen den vorherigen Repository-Stand bytegleich.

## Noch offen / bekannte Grenzen

Windows verweigerte die Steuerung des Testfensters mit `GetCursorPos 0x80070005`. Eine sichtbare Abnahme der Tropfen, Frostkanten, Scheiben-Zusammenstöße und aller Shader-Varianten ist deshalb **nicht bestanden**, sondern offen. Die Spielprotokolle belegen geladenen Shader und regulären Lauf, nicht die korrekte Darstellung jedes Pixels. Ebenso sind kein aktueller Langzeittest, keine vollständige Dimensions-/Shaderwechsel-Matrix und keine FPS-Steigerung nachgewiesen. Die Tests liefen mit dem tatsächlich angelegten 3842×2160-Framebuffer; sie waren Kompatibilitätstests, keine kontrollierten Leistungsbenchmarks.

Die [Autorenseite](https://www.curseforge.com/minecraft/mc-mods/weather-glass) führt mögliche Artefakte bei Scheiben in Vierergruppen und noch ungetestetes Nvidium auf. Die zusätzlichen Beta-Staub- und Particle-Rain-Windfunktionen sind in unserer Vorgabe aus; Particle Rain selbst bleibt installiert. Früher gemeldete Voxy-Geisterflächen nach Dimensionswechseln und die unregelmäßigen nativen Startabbrüche sind durch dieses Inhaltsupdate nicht als behoben ausgewiesen.

Erwartete Offline-Anmeldewarnungen und bereits im Ressourcenpack-Bestand enthaltene ignorierte `.DS_Store`-Einträge sind keine durch Weather Glass behobenen Fehler. Es wird keine allgemeine Absturz- oder Fehlerfreiheit zugesichert.

## Bisheriger Prüfstand

# Prüfstand 2026.11.08.1

66 Mods, 29 Ressourcenpacks und 4 Shaderarchive. Minecraft 26.3, Fabric 0.20.1, Java 25. Tests ausschließlich in einer separaten Singleplayer-Welt; keine produktiven Welten oder Server verändert.

## Vulkan-Korrektur

- Ursprünglichen Java-Absturz mit 80 % Vitrail-Render-Skalierung in der separaten Offline-Welt reproduziert: 3842 × 2160 Tiefenpuffer trifft auf 3073 × 1728 Farbpuffer.
- Geänderte Vitrail-Klasse passt den zusätzlichen Hand-/HUD-Tiefenpuffer an das aktive Weltziel an; alle übrigen Klassen des bisherigen Builds bleiben gleich.
- Erfolgreiche 60-Sekunden-Läufe mit sichtbarer Hand, Schwert und Totem sowie Shader und Voxy bei 80 % und 100 % dokumentiert. Die Prüftabelle nennt jeden einzelnen Lauf.
- Beim Sichttest waren Hände, die Farmplattform und die Landschaft sichtbar. Kein vollständiger Test aller Texturen, Dimensionen, Wetterzustände oder Shaderwechsel.

## Offene Grenzen

In zwei weiteren Startversuchen brach der Java-Prozess nativ mit `0xC0000005` ab, ohne den ursprünglichen Java-Größenfehler. Windows nannte einmal `ntdll.dll`, einmal `jvm.dll`. Die Ursache dieser zusätzlichen, unregelmäßigen Abbrüche ist nicht nachgewiesen; sie werden durch die erfolgreiche Größenkorrektur nicht als behoben ausgegeben. Shader-Neuladen und eine vollständige Shaderwechsel-Matrix sind nicht freigeprüft. Es wurde kein Multiplayer-Server betreten und keine allgemeine Absturzfreiheit zugesichert.

## Protokollierte Teilprüfungen

| Lauf | 60 Sekunden mit Hand | Größenfehler | Ergebniscode |
| --- | --- | --- | --- |
| unpatched80 | Nein | Ja | 15 |
| patched80 | Nein | Nein | 3221225477 |
| patched80-repeat | Ja | Nein | 0 |
| patched100 | Nein | Nein | 3221225477 |
| patched100-repeat | Ja | Nein | 0 |
| patched-photon80 | Ja | Nein | 0 |
| opengl-parity | Ja | Nein | 0 |

Eine Messung der FPS-Steigerung war nicht Gegenstand dieses Fehlers; es wird kein FPS-Gewinn behauptet.
