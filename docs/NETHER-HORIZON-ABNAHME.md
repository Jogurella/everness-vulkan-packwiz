# Nether-Ferne, Nebelfarben und Standardhimmel – Abnahme 2026.11.10.1

Die überhellen biomefarbigen LOD-Flächen und die harte Overworld-Himmelskante wurden im alten Stand reproduziert. Die gezielten Änderungen wurden anschließend ausschließlich in getrennten Singleplayer-Kopien mit dem tatsächlichen 70-Mod-/29-Pack-Bestand geprüft. **Kein Server wurde betreten; keine vorhandene Benutzerwelt wurde verändert.** Ein Build allein ist hier ausdrücklich kein Spielnachweis.

## Ursache und Korrektur

1. Gamma Utils hatte zusätzlich zur Nachtsicht `nightVision.brightenFogColor=true` aktiv. Es normalisiert dunkle Nebelfarben auf einen maximalen Farbkanal von 1. Diese stark aufgehellten Werte kamen auch beim Shader-/LOD-Nebel an. Die neue Vorgabe ist `false`; Nachtsicht an/100 und alle übrigen Helligkeitswerte bleiben erhalten. Beispiel Warped Forest im 8-Chunk-Kontrolllauf: vorher `(1, 0.1923, 1)`, nachher `(0.0829, 0.01595, 0.0829)`. Der absichtlich biomeigene Nebel bleibt bestehen, die leuchtenden flachen Farbflächen verschwinden im Vergleich.
2. Ohne Shader begrenzte der atmosphärische Nether-Nebel die Umgebung auf 96 Blöcke. Voxys Standardpipeline kann außerdem den LOD-Abschluss überspringen, wenn dieser Wert schon innerhalb der normalen Sichtweite liegt. Die enge Ergänzung verlängert nur atmosphärischen Nether-Nebel mit aktivem Vulkan/Voxy, begrenzt anhand vorhandener Chunk-/LOD-Distanz. Im 8-Chunk-Kontrolllauf: 512 Blöcke statt 96; bei der vorhandenen 17-Chunk-Einstellung: 1088. Keine höhere normale Chunkzahl eingestellt. Im Bild sind die zuvor besuchten künstlichen LOD-Markierungen jenseits der echten Clientchunks wieder sichtbar.
3. Voxy setzt Gelände-Renderdistanznebel auf `1e9`, damit er seine Ferne zeichnen kann. Mit diesem gemeinsamen Zustand zeigte der Standardhimmel trotz korrekter Java-`skyEnd`-Werte eine flache harte Scheibenkante. Ein eigener kleiner Ringpuffer nur für den shaderlosen Overworld-Himmel schreibt die vom Himmel benötigten Nebelwerte getrennt; die Welt-/LOD-Werte bleiben erhalten. Der Verlauf ist im direkten Voxy-An/Aus-Vergleich und nach Shaderwechsel/Weltbeitritt wieder weich. Die genaue Treiber-/Backend-intern verursachende Aliasing-Stelle wird damit nicht als vollständig aufgelöst behauptet; die enge Pass-Korrektur ist im Bild bestätigt.

## Tatsächlich geprüfte Abläufe

| Prüfung | Ergebnis und Umfang |
|---|---|
| Alter Stand | Reproduzierte Himmelskante nur mit Voxy an und überhelle Nether-LOD-Farben. Gleiche isolierte Welt, Kamera und Grafikeinstellungen als Kontrollstand. |
| Nether-Biom-Matrix | Nether Wastes, Soul Sand Valley, Crimson Forest, Warped Forest und Basalt Deltas. Jeweils tatsächliches Client-Biom anhand des Registry-Keys geprüft; Modi ohne Shader, Unbound und Pixelated. Nebel-/Shaderwerte aufgezeichnet, repräsentative Bilder verglichen. |
| Nether ohne Shader | Vorher 96, danach 512 Blöcke im 8-Chunk-Kontrolllauf; zusätzlicher Lauf mit den tatsächlichen 17 Chunks und 1088-Blöcke-Nebel. LOD-Markierungen zuvor eingelesen; Clientchunk bei 512 Blöcken beim Foto nicht real geladen. |
| Schutzfälle | Echter Blindheits-Effekt behält 1.25–5 Blöcke, echte Lava-Untertauchung 0.25–1 Block. Aktive Shader behalten 10–96 als eigene Ausgangs-Nebelwerte; die Distanzergänzung greift dort nicht ein. Wasser/Pulverschnee/Dunkelheit/Bossnebel sind zusätzlich im Code ausgeschlossen, nicht alle als gesonderte Spielszenen aufgenommen. |
| Endgültige JAR | Eigener Kaltstart, Voxy an/aus/wieder an, Tag/Abend/Nacht/Regen, Unbound in Overworld und Nether, Wechsel zu Pixelated, erneutes Laden desselben Pixelated-Packs, zurück ohne Shader, Welt verlassen und erneut betreten. Beide aktiven Shader anhand tatsächlich gezeichneter Vitrail-Programme bestätigt, nicht bloß anhand der Konfigurationsdatei. |
| Einmalmigration | 28 gezielte Java-Prüfungen gemeinsam mit der Distanzpolitik: exakter Backupinhalt, CRLF/Unicode und andere Werte erhalten, idempotenter Marker, spätere Änderungen respektiert, falsche Typen/mehrdeutige/fehlende/übergroße Dateien übersprungen, Distanzgrenzen und nicht endliche Werte. Echte Kaltstartmigration mit `MIGRATED_WITH_BACKUP` beobachtet. |
| Bau und Export | Portabler JDK-25-Neubau: alle unkomprimierten JAR-Einträge identisch zum getesteten Kandidaten. Bestehende Klassen aus lifecycle.2 unverändert. Alle 132 Vorlagen geprüft, davon nur Gamma-Utils-Nebelfarbflag geändert. 29 Ressourcen- und beide Shaderarchive im Test bytegleich. |

Die breite Nether-Matrix wurde zuerst mit der Nebelkorrektur vor Ergänzung des Sky-Puffers aufgenommen. Danach wurde die endgültige JAR mit dem zusätzlichen, ausschließlich Overworld-ohne-Shader betreffenden Sky-Puffer im gesonderten oben beschriebenen Ablauf getestet. Die Nether-Klassen blieben dabei unverändert. Verworfene erste Helferläufe mit zu großer `fillbiome`-Fläche bzw. einem Mixinklassen-Paketfehler zählen nicht als Abnahme; die anschließend erfolgreichen Läufe prüfen echte Biome. Ein eigener Zwischenlauf ohne kopierte neue JAR zählt ebenfalls nicht als Endkandidat. Die Ergebnisdatei enthält nur regulär abgeschlossene, entsprechend bezeichnete gültige Läufe.

Die erfolgreiche Endprüfung endete mit Exitcode 0, ohne QA-, Mixin- oder Shader-Kompilierfehler. Rohbilder/Logs bleiben lokal; [Ergebnisdatei und Bildprüfsummen](nether-horizon-test-summary.json) sind veröffentlicht. Der echte Test hatte die bereits vorhandene SIRIS-Skalierung Performance (2561×1440 intern → 3842×2160 Ausgabe), FG aus. Zwischenzeitlich lief eine unabhängige Offline-Entwicklungsinstanz; deshalb und wegen der synthetischen Szene sind die eingeblendeten FPS **kein Leistungsbenchmark und kein nativer 4K-Nachweis**.

## Umfang und Grenzen

- Nur Vulkan. 17 normale Chunks, Shaderdetails, Auflösungsvorgaben, Animationen, Partikel, FOV, HUD, Hotkeys und QoL werden nicht reduziert. 131 andere Einstellungsdateien sowie die 29 Original-Packs bleiben erhalten.
- Voxy zeigt ausschließlich bereits eingelesene LODs. Die Korrektur lädt keine bislang unbekannten Serverchunks und generiert keine fremden Weltdaten. Aktive Shader behalten ihre eigene absichtliche Nether-Nebel-/Sichtweitenbegrenzung; diese Veröffentlichung ist kein Entfernen allen Nether-Nebels.
- RTX-5070-Laptopabsturz, alle Rüstungs-/Cape-Flackerfälle, authentifizierte Server-Elytra-Fälle, vollständige LOD-Bergschatten und Langzeit-Multiplayer sind nicht als behoben abgenommen. Die vorherigen Licht-, Kisten- und Ausrüstungsschattenkorrekturen bleiben bytegleich erhalten.
- VIRIS 0.4.0 wurde als neues Upstream-Release anhand der Quellen geprüft, ist aber nicht die Basis dieser engen Korrektur. Der gepinnte 0.3.6-Stack bleibt nachvollziehbar erhalten.

## Installation und Herkunft

Everness Launcher → Vulkan aktualisieren → Minecraft vollständig neu starten. Genau eine aktive VIRIS-JAR: `viris-0.3.6+mc26.3-everness-lifecycle.3.jar`. Die Einmalmigration korrigiert ausschließlich `nightVision.brightenFogColor`, sichert vorher nach `.everness/compat-backups/` und legt `.everness/compat-migrations/natural-fog-colors-v1.done` an. Bereits ausgeschaltete oder später bewusst geänderte Werte werden respektiert.

Grundlage [VIRIS 0.3.6](https://github.com/laurensanp/viris/tree/2d3061bcb73751a6c70cef50eb3b592b6a4659f3), laurensanp, MIT; übernommen ist die OpenGL-/Vulkan-Interop. Eigenentwickelt sind die dokumentierten Nebel-/Himmel-Zusätze auf dem bisherigen Everness-Fix. [Vollständige Quellen, MIT-Lizenz, Provenienz und Bauanleitung](../patches/viris-fog). Voxy und Vitrail bleiben separat. SHA-256: `1250ec3539068e361cdd7d9d67b166e31712ba9e340b22c46c320103afe3b25a`.

Das mit dem integrierten Bildgenerator erzeugte **Beispielbild** ist keine Spielaufnahme. [Generationsprompt](illustrations/update-2026.11.10.1-prompts.txt). News und Bild enthalten den gewünschten Teaser: **Something big is coming stay tuned**.
