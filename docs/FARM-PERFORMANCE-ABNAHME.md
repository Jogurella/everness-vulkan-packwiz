# Farm-/Lager-Update 2026.11.08.4: Prüfbericht

## Enthalten und unverändert

Neu: **Enhanced Block Entities Reloaded fabric-0.13.3+26.3**, Modrinth-Version `tj4Apu60`, und **MatLib fabric-1.0.0+26.3**, `VIhnl3tD`. Beide unveränderte offizielle Downloads; SHA-512 in ihren Packwiz-Dateien. Eigene Arbeit beschränkt sich auf Auswahl, Konfiguration, getrennte Tests und Pack-Veröffentlichung.

Der bestehende Aufbau bleibt: **Minecraft 26.3, Fabric 0.20.1, Java 25, nativer Minecraft-Vulkan-Renderer, Vitrail 0.13.0-beta mit bisherigem Everness-Depthfix, VIRIS 0.3.6, Voxy 0.2.20-beta, Sodium 0.9.2, SIRIS 1.1.6**. Kein VulkanMod. Pixelated und Complementary Unbound r5.9.3 bleiben die beiden Shader. Alle **131 bisherigen Einstellungsdateien bytegleich** zum vorherigen Repo-Stand, zusätzlich nur `config/enhanced_bes.properties`.

## Konfiguration und sichtbare Abwägung

EBE für Kisten und Schilder an, vollständige Schildtexte, Ressourcenpack-Kompatibilitätsmodus an. EBE für Betten, Glocken, Shulkerboxen und verzierte Töpfe aus, um deren bisherige Modelle nicht zusätzlich zu ersetzen. Mobskins und Mobanimationspacks werden nicht entfernt oder in ihrer Animationsrate reduziert.

EBE ersetzt die Kisten-Modellzeichnung einschließlich Deckelrenderer. Normales Öffnen/Schließen bleibt, zusätzliche Verformungen/Wackelbewegungen aus Fresh Animations Objects sind damit nicht zwingend identisch. Unter **Mod Menu → Enhanced Block Entities Reloaded → Einstellungen** kann man Kisten und Schilder getrennt zurückstellen. Eine vollständige optische Gleichheit aller Custom-Modelle wird nicht behauptet.

## Tatsächlich durchgeführte Offline-Prüfung

- Zwei getrennte Kopien des veröffentlichten Vulkan-Modbestands und aller 29 Ressourcenpacks. Die Vergleichskopie ohne EBE/MatLib, die Kandidatenkopie mit beiden Mods. Keine produktiven Profile oder Welten verändert und kein Server betreten.
- NVIDIA RTX 4090. Im Log bestätigter Backendname **Vulkan**; SIRIS für die Testkopien ausdrücklich ohne FG. Protokollierte Rendergröße **3842×2160 intern → 3842×2160 Ausgabe**. Die abweichenden zwei Pixel stammen vom tatsächlichen Randlos-Fenster. Die veröffentlichten SIRIS-/FG-Werte wurden durch diese Testoption nicht verändert.
- Künstliche Szene mit **256 Kisten**, 96 eingefügten NoAI-Tieren (48 Schafe, 32 Kühe, 16 Frösche), getakteter Redstone-Leitung/Lampen und einer Modellreihe mit normaler, Redstone-, Doppel-, Enderkiste, Shulkerbox, Bett, Schild und Laterne.
- Der erste Kandidatenlauf und die Vergleichskopie beendeten den automatisierten Ablauf regulär mit Exitcode 0: Unbound aktiv, Kisten geschlossen → geöffnet → erneut geschlossen, Wechsel auf Pixelated und erneuter Eintritt in dieselbe Offline-Welt. Die Vitrail-Abfrage meldete keinen Packfehler. Echte Spielaufnahmen der Kistenstadien wurden lokal kontrolliert: normale, Redstone-, Doppel- und Enderkiste waren sichtbar und ihre Deckel öffneten bzw. schlossen.
- Ein Zusatzlauf entfernte die Testtiere nach der ersten Messphase. Dabei wurde die Figur manuell bewegt und die Szene geändert. Deshalb wird dieser Lauf **nicht als FPS-Vergleich** verwendet.

Die aus der Spielanzeige entnommenen Sekundenwerte sind kein unabhängiger Frametime-Benchmark. Die kurzen ersten Läufe enthalten Aufwärm-/Ladeschwankungen; außerdem war ein Geometriefehler der künstlichen Tiergruppe sichtbar. **Ein bestimmter FPS-Zuwachs ist damit nicht nachgewiesen.** EBE ist als gezielte Kistenoptimierung enthalten, nicht als Zusage hoher FPS in jeder Mob-/Redstonefarm.

## Auffälligkeiten und offene Punkte

- Große gestreckte Geometrie über der künstlichen Tiergruppe war **sowohl im bisherigen Pack ohne EBE als auch mit EBE** sichtbar. Das ist kein durch diesen Modwechsel nachgewiesener neuer Fehler, aber auch keine bestandene Abnahme aller Mobanimationen. Ob derselbe Fehler bei normalen beweglichen Tieren im Multiplayer auftritt, ist offen. Dieses Update behebt ihn nicht.
- Die lokale reine Kistenszene nach Entfernen der Tiere war sichtbar; durch die manuelle Bewegung nicht als Vergleich verwendbar. Langzeitspiel, alle Mobmodelle und Objektanimationen, alle Dimensionen, Shaderprofile, Schattenarten und Serverbeitritt sind nicht vollständig geprüft.
- Auth-/Realms-Fehler sind in den Offline-Kopien ohne Anmeldung erwartbar. Der vorhandene Windows-Perflib-Fehler und eine fehlende Medal-Vulkan-Manifestdatei wurden ebenfalls protokolliert. Sie sind nicht als durch EBE behoben ausgewiesen. Rohe Logs und private Daten werden nicht veröffentlicht.

## Warum VulkBatch 1.1.0 nicht im Update ist

Die veröffentlichte 1.1.0-JAR und der dazu passende Quellstand wurden statisch geprüft; die Mod wurde nicht installiert. `MixinBufferBuilder` ruft lediglich `BatchManager.defer/release` auf: ThreadLocal-Flag und AtomicInteger-Zähler, aber keine tatsächliche Wiederverwendung eines Puffers oder Meshes. `MixinLevelRenderer` kann `RenderSection.compileAsync` innerhalb von fünf Spielticks abbrechen; eine Warteschlange oder spätere Wiederanforderung fehlt. Bei Redstone-Änderungen wäre dadurch eine veraltete Darstellung möglich. Der eigentliche Effekt ist deshalb nicht die beworbene Pufferoptimierung.

Diese Ablehnung betrifft konkret die geprüfte Fassung 1.1.0 und ist kein pauschales Urteil über künftige Fassungen. [Geprüfter VulkBatch-Quellstand](https://github.com/WMXRZWYM/VulkBatch-Optimizer/tree/ea6f881eead37ea0a61b707b65b6b0b3a1f77770). JAR-SHA-256: `287c3ca80128b4eb2052e9ebcb00d3ce2fba22ce02532ea0b48ee6cdb1fb83cc`.

## Quellen und Nachweise

- [EBE Reloaded, offizielles Projekt](https://modrinth.com/mod/ebe-reloaded), [geprüfter Quellstand](https://github.com/Mat0u5/EnhancedBlockEntitiesReloaded/tree/54a6dc560602cab7d26eb85610cad037928f4143).
- [MatLib, offizielles Projekt](https://modrinth.com/mod/matlib).
- [VulkBatch, offizielles Projekt](https://www.curseforge.com/minecraft/mc-mods/vulkbatch).
- [Kompakte Testergebnisse ohne private Logs](farm-performance-test-summary.json).
- EBE und MatLib werden getrennt und unverändert geladen. Voxy bleibt separat; keine Einbettung in eigene JARs.
