# Vulkan 2026.11.09.1: Lichtzustand und Kistenmodelle

## Ergebnis und veröffentlichter Umfang

**Bestätigt behoben im Offline-Vergleich:** Der Voxy-Shaderpfad hielt nach Vitrail-Neuladen oder Weltwechsel einen alten Lichtzustand fest. Die veröffentlichte **VIRIS 0.3.6+mc26.3.everness-lifecycle.1** erneuert ihre Pipeline bei ausgetauschten `PackValues`, selbst wenn Shadername, Profil, Optionen, Dimension und Größe gleich bleiben. Keine neue Rendering-Engine, kein VulkanMod, kein eingebettetes Voxy.

**Kompatibilitätsvorgabe:** EBE-Kisten aus, EBE-Schilder an. So bleibt die Zeichnung der Kisten bei den ursprünglichen Ressourcenpack-Modellen. Das verhindert diesen zusätzlichen Kistenrenderer, ist aber kein kausaler Nachweis, dass jedes gemeldete Flackern von EBE stammt: EMF meldet bereits selbst eine Laufzeitanpassung der EBE-Konfiguration für Custom-Modelle. Die unveränderten Original-Packs werden nicht ersetzt oder zusammengeführt.

**Keine vollständige optische Abnahme:** Rüstungsflackern, Elytra-Schatten, Modellstreifen und der 5070-Laptopabsturz bleiben offen. Eine zusätzliche getestete EMF-/ETF-Schattenbrücke ist nicht enthalten. Vollständige Fernberg-/LOD-Schatten sind weiterhin nicht implementiert.

## Tatsächlich durchgeführte Spieltests

Getrennte Kopien des veröffentlichten Modbestands: Minecraft 26.3, Fabric 0.20.1, JDK 25, **nativer Vulkan-Renderer**, RTX 4090, Vitrail 0.13.0-beta mit bestehendem Everness-Depthfix, Voxy 0.2.20-beta, Sodium 0.9.2, SIRIS 1.1.6. 70 reguläre Mods und ein ausschließlich lokaler Beobachter. 29 Ressourcenpack-Archive und die beiden Shaderarchive sind SHA-256-identisch mit der Referenz. Kein produktives Profil und keine bestehende Welt verändert, kein Server betreten.

Die künstliche Einzelspielerwelt enthält viele Kisten, getaktete Redstone-Lampen, natürliche Landschaft sowie eine Modellreihe. Die frühere künstliche NoAI-Tiergruppe wurde entfernt; natürliche Mobs können weiterhin spawnen.

| Gegenprobe | Ergebnis |
|---|---|
| Bisherige offizielle VIRIS 0.3.6 | Ablauf regulär beendet; nach Wechsel von 6000 auf 18000 Weltzeit und Neuladen blieb die Fernpipeline bei 6000. 20 Messpunkte mit bereits aktivem, aber noch altem Zustand. |
| Lifecycle-Fix + EBE-Kisten aus, FG aus | Ablauf regulär beendet; **0 Messpunkte mit dauerhaft altem Zustand**, 3 erkannte Neuaufbauten; Unbound und Pixelated ohne gemeldeten Vitrail-Packfehler. |
| Gleiche veröffentlichte Vorlage mit SIRIS/2× FG an | Separater regulärer Ablauf mit Start, Shader-Neuladen, aus/an, Welt verlassen/erneut betreten und Wechsel auf Pixelated; **0 dauerhaft alte Lichtzustände**. Kein unabhängiger FG-Ausgabe-/Latenzbenchmark. |
| Zusätzlicher EMF-/ETF-Brückenprototyp | Schatten-/Shader-APIs erkannten Vitrail; keine Zustandslecks im normalen Pass. Auffällige gestreckte Modellflächen im Bild; ähnliche Streifen auch ohne diese Brücke. Ursache nicht bewiesen, Zusatzbrücke nicht veröffentlicht. |

Einzelne Beobachtungen unmittelbar beim Kettenwechsel sehen den noch nicht erstmals gezeichneten Zustand `worldTime=0`. Der Fix baut im folgenden Renderdurchlauf neu. Diese Übergangspunkte sind separat in der kompakten Ergebnisdatei enthalten und werden nicht als dauerhafte Abweichung verschwiegen. Beide Kandidatenläufe beendeten den Prozess mit Exitcode 0; das ist ein gezielter Regressionstest, keine Langzeit-Stabilitätsgarantie.

## Sichtprüfung und Grenzen

- Unbound bei Tag und Nacht; nahe und entfernte Landschaft nach gleichem Shader-Neuladen, danach Shader aus/an, erneuter Einzelspielerbeitritt und Pixelated. Die mit dem alten Stand sichtbare Tagesbeleuchtung der LODs bei Nacht war mit dem Fix weg. Wasserfläche und Nebel/Horizont wurden in den gespeicherten Ansichten kontrolliert; keine vollständige Wetter-/Biome-/Dimensionsmatrix.
- Normal-, Redstone-, Doppel- und Enderkisten waren sichtbar. Kontrollierte Vanilla-Blockevents öffneten und schlossen ihre Deckel; die gespeicherten offenen/erneut geschlossenen Zustände zeigten keinen stehengebliebenen zweiten Deckel. **Nicht identisch mit einem vollständigen Test echter Inventar-GUIs oder Serverpaketfolgen.** Eine flackerfreie Langzeitaufnahme wird nicht behauptet.
- Verzauberte Rüstung in FPV im Stand und an mehreren Punkten einer Schwertbewegung aufgenommen. Einzelbilder können leichtes zeitliches Flackern nicht ausschließen; kein bestätigter Rüstungsfix.
- Elytra-Gleitflug mit tatsächlich ausgerüsteter Elytra und `isFallFlying=true` bestätigt. Flügel am Modell sichtbar. Der gewünschte vollständige Elytra-Schatten ist damit **nicht** als behoben abgenommen.
- Ohne Shader war im kontrollierten Blick ein blauer Vanilla-Himmel mit Horizont sichtbar. Der Bericht „Himmel fehlt“ ließ sich dort nicht als vollständig schwarzer/fehlender Himmel reproduzieren; keine eigene Himmelskorrektur enthalten.
- Dünne bis stark gestreckte Modellstreifen waren zeitweise in der Testszene zu sehen, auch ohne zusätzliche EMF-/ETF-Brücke. Bereits die frühere Farm-Abnahme kannte gestreckte Mob-Geometrie. Das ist weiterhin ein offener visueller Fehler und keine bestandene Abnahme aller Animationen.
- [VIRIS nennt ausdrücklich fehlende LOD-Schatten](https://github.com/laurensanp/viris#known-issues). Die Pack-Schattenkarten werden im LOD-Pfad noch nicht vollständig übergeben. Eine höhere Sicht-/Schattendistanz ersetzt diese fehlende Anbindung nicht.
- Ein lokaler RTX-4090-Test beweist keine Behebung des gemeldeten RTX-5070-Laptopabsturzes. Dafür fehlen dessen genauer Absturzbericht und Treiber-/Backendzustand. Multiplayer wurde nicht getestet.

Offline-Auth-/Realms-Meldungen ohne Anmeldung und die vorhandene fehlende Medal-Vulkan-Manifestdatei waren im lokalen Protokoll sichtbar. Keine davon wird als durch dieses Update behoben bezeichnet. Keine privaten Logs, Welten oder Caches werden veröffentlicht. Ein erster Lauf scheiterte an der Paketaufteilung des ausschließlich lokalen Testhelfers; nach dessen Korrektur wurde die Referenz neu gestartet. Dieser Helferfehler ist kein nachgewiesener Modpackabsturz.

## Installation, Einstellungen und Herkunft

Im Everness Launcher Vulkan aktualisieren, danach Minecraft neu starten. Packwiz ersetzt die bisherige VIRIS-JAR; nicht beide Versionen parallel behalten. **Alle 131 übrigen Einstellungsdateien bleiben bytegleich**, nur `render_enhanced_chests` in Datei 132 ändert sich auf `false`. Persönlich erhaltene EBE-Einstellungen gegebenenfalls in Mod Menu zurückstellen. Shaderdetails, Auflösung, Sichtweite, SIRIS/FG-Vorgaben, FOV, Hotkeys, HUD, QoL und Packreihenfolge wurden nicht zur Kaschierung der Fehler reduziert. OpenGL unverändert.

VIRIS: laurensanp, MIT, Commit `2d3061bcb73751a6c70cef50eb3b592b6a4659f3`. Eigene Änderung: ausschließlich die Pipeline-Invalidierung in `VoxyFrame`; bestehende weitere Klassendateien bytegleich. Der mitgelieferte portable Neubau erzeugte denselben Inhalt aller JAR-Einträge; lediglich ZIP-Zeitstempel können abweichen. [Quelldatei, Patch, Lizenz und Bauanleitung](../patches/viris-lifecycle), [kompakte Ergebnisse](visual-fix-test-summary.json). Voxy, Vitrail, EMF und ETF werden getrennt geladen.

Das Updatebild wurde mit dem integrierten Bildgenerator erstellt und passend zum tatsächlichen Umfang korrigiert. Es ist mit **Beispielbild** beschriftet und kein Spielnachweis. [Exakte Prompts](illustrations/update-2026.11.09.1-prompts.txt).
