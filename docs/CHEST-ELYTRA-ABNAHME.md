# Vulkan 2026.11.09.2: echte Kisteninventare und Elytra-Schatten

## Ergebnis

**Kistenfehler im alten Stand reproduziert:** Mit der erhaltenen EBE-Option `render_enhanced_chests=true` zeigt die Offline-Gegenprobe beim echten Öffnen des Inventars einen angehobenen Deckel zusätzlich zur weiter sichtbaren geschlossenen Kiste. Mit ausgeschalteter EBE-Kistenoptimierung und denselben ursprünglichen Ressourcenpacks ist der Innenraum offen und kein zusätzlicher geschlossener Deckel zu sehen. Das war in der vorherigen Abnahme mit künstlichen Blockevents nicht hinreichend abgedeckt.

**Fehlender Schatten verzauberter Elytra reproduziert:** In derselben kontrollierten Flugpose zeigt der alte Stand die unverzauberten Flügel im Schatten, beim verzauberten Item fehlt der entsprechende Flügelanteil. Mit dem neuen gezielten Grundtexturpfad bleibt der Flügelschatten auch verzaubert sichtbar. Das ist ein bestätigter Teilfehler; es wird nicht behauptet, dass damit jede Server-/Cape-Ursache behoben sei.

## Ursachen und Eingriff

Die veröffentlichte EBE-Vorlage stand schon in 2026.11.09.1 auf `false`. Der Launcher darf persönliche Einstellungen erhalten; die tatsächliche Datei der betroffenen Installation stand noch auf `true`. Deshalb reichte das frühere Update nicht aus. Die neue VIRIS-Fassung prüft **vor** EBE seine Ressourcen erzeugt einmalig das aktive FA+Objects-Pack und diesen einen Schlüssel. Sie sichert die alte Datei, ändert nur den Kistenwert und legt einen Marker an. Spätere ausdrückliche Benutzeränderungen bleiben erhalten. EBE-Schilder, sämtliche Original-Packs und deren Reihenfolge bleiben bestehen.

Minecraft 26.3 kombiniert bei verzauberter Ausrüstung die Grundtextur mit einer eigenen Glint-Pipeline. Vitrail 0.13.0 führt diese kombinierte Pipeline nicht in seiner Schattenzuordnung; nicht bediente Schattenzeichnungen werden verworfen. Die neue kleine Anpassung übergibt ausschließlich während der tatsächlichen Vitrail-Schatten-Submission eine reine Zeichenkopie ohne Glint-Override an die vorhandene Minecraft-Routine. Sie behält die echte Grundtextur samt Alpha und Cape-Pfad. Der echte Gegenstand sowie die normale sichtbare Verzauberung bleiben unangetastet. Der bereits veröffentlichte Voxy-Lichtzustandsfix ist bytegleich enthalten.

## Tatsächliche Spieltests

Getrennte lokale Kopien mit **70 regulären Mods**, 29 unveränderten Original-Ressourcenpacks, Vitrail 0.13.0-beta mit bestehendem Everness-Depthfix, VIRIS 0.3.6 mit Lifecycle-Fix 1/2, Voxy 0.2.20-beta, Sodium 0.9.2, EBE 0.13.3, MatLib 1.0.0, EMF/ETF und SIRIS 1.1.6. Minecraft 26.3, Fabric 0.20.1, JDK 25, **nativer Minecraft-Vulkan**, RTX 4090. Ein ausschließlich lokaler Testhelfer ist nicht im Pack enthalten. Keine produktive Welt verändert und **kein Server betreten**.

| Fall | Tatsächlich geprüft |
|---|---|
| Alte Fassung, Unbound | Reales `useItemOn`, `ContainerScreen` und `ChestMenu`; Normal-, Redstone-, Doppel- und Enderkiste. 63/63/90/63 Slots einschließlich Spielerinventar. Doppelmodell bei der geöffneten Kiste sichtbar. |
| Neue Fassung, Unbound | Dieselben vier Inventaröffnungen, Schließen und erneut geschlossene Ansichten. Offener Innenraum ohne zusätzlich geschlossenen Deckel, Doppelkiste mit richtigem 54-Slot-Inventar. |
| Neue Fassung, Pixelated | Eigenständiger Kaltstart mit tatsächlich geladenem Pixelated, dieselben vier Inventare und Schattenansichten. Shaderauswahl anhand des gezeichneten Vitrail-Programms kontrolliert. |
| Flugschatten | Tatsächlich angelegte Elytra und `isFallFlying=true`, jeweils unverzaubert/verzaubert und mit Standard-/synthetischem Cape-Texturpfad. Aufnahmen in vier Blickrichtungen plus Modellreferenz; manuell insbesondere verzauberte Flügel und Cape-Variante verglichen. |
| EBE-Migration | 17 Java-Teilprüfungen: exaktes Backup, nur ein Wert geändert, CRLF/Format/Kommentar erhalten, Marker idempotent, spätere eigene Änderungen respektiert, ohne Objects-Pack keine Änderung, mehrdeutige/falsche Schlüssel übersprungen. Echte isolierte Kaltstartmigration `MIGRATED_WITH_BACKUP` zusätzlich beobachtet. |
| Verteilte JAR | Portables mitgeliefertes Buildskript ausgeführt; alle unkomprimierten JAR-Einträge identisch zum tatsächlich getesteten Kandidaten. Bestehende Klassen einschließlich `VoxyFrame` gegenüber Fix 1 unverändert; nur zwei Metadatendateien geändert, fünf eigene Klassen plus Hinweis ergänzt. |

Alle in der kompakten Ergebnisdatei aufgeführten Abläufe wurden regulär mit Exitcode 0 beendet, ohne QA-/Mixin-Fehler. Es sind gezielte Regressionen, keine allgemeine Langzeit-Stabilitätsgarantie. Die Flugpose wurde auf Client und lokalem Server stabilisiert; das prüft die Ausrüstungs-/Schattenzeichnung, nicht normales Fluggefühl, Eingabelatenz oder alle Flugwinkel.

Der Test kopierte die tatsächliche persönliche SIRIS-Performance-Einstellung (2561×1440 intern, 3842×2160 Ausgabe); FG war in den isolierten Vergleichen aus. Daraus wird **kein nativer 4K-FPS-Wert** und keine FPS-Steigerung abgeleitet. Die öffentlichen SIRIS-/Grafikvorgaben wurden nicht geändert. Beim ersten geplanten Pixelated-Lauf war zunächst nur Iris' Konfigurationsdatei geändert; Vitrail nutzte weiter seine eigene Unbound-Auswahl. Dieser Lauf zählt ausdrücklich als Unbound. Danach wurde `vitrail/pack.txt` korrigiert und Pixelated separat gestartet und anhand des tatsächlich gezeichneten Programms verifiziert.

Die vollständigen Vergleichsbilder und Rohprotokolle bleiben lokal. SHA-256-Werte der gespeicherten Schatten-/Inventaraufnahmen und die konkreten Laufresultate stehen in [chest-elytra-test-summary.json](chest-elytra-test-summary.json); das gezeichnete Updatebild ist kein Spielnachweis.

## Grenzen

- Der [offene Vitrail-Bericht zu serverabhängigen Elytra-Schatten](https://github.com/avpbynf/Vitrail-Shaders/issues/497) umfasst auch unverzauberte Elytra und launcherabhängige Capes. Der hier bestätigte kombinierte Glint-Fehler erklärt nicht automatisch jeden dieser Fälle. Der synthetische Cape-ID-Pfad ist kein authentifizierter Server-/Launcher-Cape-Test. Multiplayer wurde entsprechend dem Auftrag nicht betreten.
- Keine vollständige Abnahme aller zeitlichen Flackereffekte. Leichtes Rüstungsflackern in FPV, frühere gestreckte Mob-Modellflächen, der RTX-5070-Laptopabsturz und komplette Schatten entfernter LOD-Berge werden nicht als behoben ausgewiesen. [VIRIS nennt LOD-Schatten weiterhin als Grenze](https://github.com/laurensanp/viris#known-issues).
- Der ursprüngliche automatische Schattenbeobachter kollidierte mit vorhandenen Umschreibungen im ausschließlich lokalen QA-Helfer. Er wurde entfernt; seine Nullzähler sind kein Schattennachweis. Die Abnahme verwendet reale Bilder und den tatsächlich protokollierten Produktions-Hook. Die breitere frühere EMF-/ETF-Brücke ist weiterhin nicht enthalten.
- Keine Reduktion von Animationen, Partikeln, Shaderdetails, normaler Sichtweite oder Auflösung in der veröffentlichten Vorlage. Kein OpenGL-Update.

## Installation und Herkunft

Everness Launcher → Vulkan → Aktualisieren, Minecraft vollständig neu starten. Die bisherige VIRIS-JAR wird durch `viris-0.3.6+mc26.3-everness-lifecycle.2.jar` ersetzt; nicht beide Fassungen parallel installieren. Die erste Altconfig-Korrektur sichert nach `.everness/compat-backups/`, ihr Marker liegt unter `.everness/compat-migrations/ebe-original-chests-v1.done`. Alle 132 Vorlagendateien sind bytegleich mit 2026.11.09.1.

VIRIS von laurensanp, MIT, Upstream-Commit `2d3061bcb73751a6c70cef50eb3b592b6a4659f3`. Eigenentwickelt sind die dokumentierte Kistenmigration und die enge Ausrüstungs-Schattenanpassung; die vorhandene OpenGL-/Vulkan-Interop wird übernommen. [Vollständige Quellen, Lizenz, Provenienz und Buildskript](../patches/viris-equipment). Voxy bleibt separat und ist nicht eingebettet. SHA-256 der getesteten/veröffentlichten JAR: `aaf84361dc3baabcc3ab7229ac2f2f6195d6b0cd69af23e782f4eae8a94eab04`.

Das neue **Beispielbild** wurde mit dem integrierten Bildgenerator erzeugt. [Exakter Prompt](illustrations/update-2026.11.09.2-prompts.txt). Es zeigt die beiden Korrekturbereiche und den Erhalt der Einstellungen, keine unbelegten FPS- oder allgemeinen Fehlerfreiheitsversprechen.
