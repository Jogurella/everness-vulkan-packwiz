## [2026.11.08.4] - 2026-11-08
### Neu
- **Enhanced Block Entities Reloaded 0.13.3 für 26.3** und die erforderliche **MatLib 1.0.0 für 26.3**. Geschlossene Kisten können als Blockmodelle statt ständig als einzelne Block-Entities gezeichnet werden. Das zielt auf Lager- und Farmbereiche mit vielen Kisten.
- Angepasste EBE-Vorgaben: Kisten und Schilder an, alle Schildtexte sichtbar; EBE für Betten, Glocken, Shulkerboxen und verzierte Töpfe aus. Ressourcenpack-Kompatibilitätsmodus an.
- Eigenes Farm-Updatebild, aktualisierter Einstellungs-Export und [Prüfbericht](docs/FARM-PERFORMANCE-ABNAHME.md).
### Hinweise für Spieler
- Im Everness Launcher das **Vulkan-Pack aktualisieren und Minecraft neu starten**. EBE ist rein clientseitig; der Server braucht diese beiden Mods nicht.
- Über **Mod Menu → Enhanced Block Entities Reloaded → Einstellungen** lässt sich die Kisten-/Schilderoptimierung wieder abschalten. EBE verwendet bei Kisten seinen eigenen animierten Deckelrenderer; zusätzliche Modellbewegungen aus Fresh Animations Objects können dadurch abweichen. Die Mob-Animationspacks werden nicht entfernt oder vereinfacht.
- Alle **131 bisherigen Einstellungsdateien bytegleich erhalten**, einschließlich Shaderdetails, Sichtweite, SIRIS/FG, FOV, Hotkeys, HUD und Ressourcenpack-Reihenfolge. OpenGL bleibt unverändert.
- **VulkBatch 1.1.0 wird nicht aufgenommen:** Die Quellprüfung fand abgebrochene Chunk-Neubauten ohne spätere Wiederholung und Zähler statt tatsächlicher Pufferwiederverwendung. Das ist für aktive Redstonefarmen keine geeignete Vorgabe.
- Die Offline-Prüfung ist kein Nachweis für einen bestimmten FPS-Zuwachs auf deinem Server. Ein bereits im bisherigen Pack sichtbarer gestreckter Mob-Geometriefehler trat auch ohne EBE auf und ist durch dieses Update nicht behoben; Details und Messgrenzen stehen im Prüfbericht.

## [2026.11.08.3] - 2026-11-08
### Neu
- **SIRIS 1.1.6** ersetzt Purescale. Vorgabe: **Custom 100 %**, DLAA-NGX-Modus, Modell M, **2× Frame Generation** auf RTX 40/50; keine reduzierte interne Auflösung angefordert. Die SIRIS-Option `disablePackTaa=true` schaltet das doppelte Shader-TAA ab.
- **Pixelated** exakt aus der bereitgestellten Nutzerdatei: alle 635 Archiv-Einträge werden lokal inhaltsgleich rekonstruiert. Grundlage ist das separat installierte offizielle Complementary Unbound r5.9.3; keine vollständige modifizierte Shader-ZIP wird neu hochgeladen.
- Eigenes Updatebild und passender Everness-Newsartikel.
### Geändert
- Shaderauswahl auf **Pixelated** und **Complementary Unbound r5.9.3** beschränkt. Beide erhalten die vom Nutzer mitgelieferten `.zip.txt`-Einstellungen.
- Andere Shader und ihre alten Vorgaben entfernt, einschließlich Photon, Reimagined, Tobias-Pixel und Cozy Companion. Der Cozy-Generator ist entfernt und legt kein zusätzliches Pack mehr an.
- Der kleine lokale Patch-/Migrationshelfer ersetzt den bisherigen Cozy-Helfer. Beim ersten Start werden alte Shader außerhalb der aktiven Liste gesichert und alte Shaderverweise auf Pixelated umgestellt; bereits gültige Unbound-Auswahl bleibt erhalten. Keine Render-Hooks oder Hintergrundarbeit.
- **127 bestehende Einstellungsdateien bytegleich erhalten**, einschließlich Hotkeys, HUD, FOV, Ressourcenpack-Reihenfolge und übrigen QoL-Optionen. Nur Shaderauswahl/-vorgaben und die ausdrücklich ersetzte Vulkan-FG-Anbindung werden angepasst.
### Hinweise für Spieler
- Im Everness Launcher aktualisieren und Minecraft neu starten. Die erste lokale Shader-Einrichtung erfolgt automatisch. Sicherungen liegen unter `.everness-shader-setup/backups/` im Spielordner.
- Offline-Ablauf mit beiden Shadern, Neuladen und erneutem Weltbeitritt regulär abgeschlossen. [Prüfbericht und Grenzen](docs/SIRIS-SHADER-ABNAHME.md).
- Es wird kein FPS-Ziel oder allgemeine Fehlerfreiheit zugesichert. Bekannte frühere native Vulkan-Abbrüche und die vollständige Shader-/Dimensionsmatrix sind damit nicht pauschal als gelöst ausgewiesen.

## [2026.11.08.2] - 2026-11-08
### Neu
- **Weather Glass 0.6.0 für Fabric 26.3:** Regen auf Glas sowie Schnee und Frost. Einstellbar über Mods → Weather Glass → Konfigurieren; rein clientseitig.
- **Cozy Companion r3** mit den Profilen **Cozy - Normal** und **Cozy - Pixelated**. Die kleine Start-Hilfe erzeugt die lokale Variante automatisch aus dem bereits enthaltenen offiziellen Complementary Unbound r5.9.3; das Original bleibt erhalten. Die vom Nutzer gelieferte r3-ZIP und die erzeugte Fassung stimmen in allen 468 Einträgen überein.
- Eigenes Updatebild für Everness Vulkan und zugehöriger Newsartikel.
### Geändert
- Weather Glass startet mit normalen Regen-/Schneemengen und **16x**-Details. Der zusätzliche Glas-Weichzeichner und die Beta-Staub-/Windfunktionen sind zunächst aus.
- Einstellungs-Vorlage, Prism-Starter, Downloadliste und Prüfsummen auf den neuen Stand gebracht. Vorhandene Hotkeys, HUD-Positionen, FOV, Vollbild, Sichtweite und aktive Shaderauswahl wurden in der Vorlage nicht verändert.
### Hinweise für Spieler
- Pack im Everness Launcher aktualisieren und Minecraft neu starten. **Cozy-Companion-r3** steht anschließend in der Shaderauswahl; das gewünschte Cozy-Profil wird in dessen Shader-Einstellungen gewählt. Vorhandene eigene Cozy-Dateien werden erhalten, niemals still ersetzt.
- Nachprüfung: Regen unter Vulkan, Schneefall unter beiden Backends und Frost/Beschlag unter OpenGL sichtbar. Die vollständig entwickelte Vulkan-Frostschicht ist noch nicht abschließend sichtbar abgenommen. Die korrigierten Schnee-Testkopien liefen jeweils 180 Sekunden und endeten regulär. Der erste 165-Sekunden-Test hatte wegen eines zu großen Test-Biombereichs noch keinen Schnee nachgewiesen. Kein Server wurde betreten und kein FPS-Gewinn gemessen.
- **Sichtprüfung nachgeholt:** beide Updateartikel und Bilder sind im Launcher sichtbar. Ein zusätzlicher Vulkan-Test wurde unerwartet mit `0xCFFFFFFF` beendet; der anschließende Gegenlauf war erfolgreich, die Ursache bleibt offen. Keine allgemeine Stabilitätsfreigabe und keine vollständige Shader-/Dimensionswechsel-Matrix. [Genauer Prüfbericht](docs/WEATHER-GLASS-ABNAHME.md).

## [2026.11.08.1] - 2026-11-08
### Behoben
- Absturz beim ersten Zeichnen der Hand mit reduzierter Vitrail-Render-Skalierung: Der separate Hand-/HUD-Tiefenpuffer folgt jetzt der aktiven Weltauflösung.
### Geändert
- Vitrail 0.13.0-beta mit eng begrenzter Everness-Tiefenpufferkorrektur; Minecraft 26.3, Fabric 0.20.1, VIRIS 0.3.6 und Voxy 0.2.20-beta bleiben erhalten.
### Hinweise für Spieler
- Minecraft zum Aktualisieren vollständig beenden. Vorhandene eigene Grafikwerte bleiben beim normalen Pack-Update erhalten; der Standard bleibt native Render-Skalierung 100 %.
- Offline-Prüfstand und offene Grenzen: [PACK-STATUS.md](PACK-STATUS.md). Es wurde kein Multiplayer-Server für Tests betreten.

# 2026.11.07.4

- Fehlende Atlas-Inventarsteuerung 0.1.6+mc26.3 wieder ergänzt, unverändert aus dem bisherigen SMP-Profil.
- Alt+B, frei belegbare Tastenkombination, Item-Ausschlüsse und AutoTotem-/Shulker-Einstellungen als drei Konfigurationsdateien ergänzt.
- Tobias-Pixel: 197 An/Aus-Werte je Profil in die reguläre Toggle-Schreibweise gebracht. Alle vier Profile bestehen 16 Speicher-/Ladewechsel mit Vitrail. Shaderquellen und logische Profilwerte bleiben gleich.
- 66 Mods, 29 Ressourcenpacks, vier Shaderarchive; 134 gemeinsame Einstellungsdateien.
- OpenGL-Repository und bestehende Welten bleiben unverändert.

## 2026.11.07.3

- Distant Horizons aus den aktiven Downloads und der Einstellungs-Vorlage entfernt.
- Voxy 0.2.20-beta und VIRIS 0.3.6 über gepinnte Original-Autoren-Downloads ergänzt; kein Einbetten von Voxy.
- Fabric Loader auf 0.20.1 angehoben; der neue Prism-Starter enthält diese Version. Ältere Prism-Instanzen müssen ihren Loader aktualisieren.
- Die übrigen 96 Mod-, Ressourcenpack- und Shaderarchive bleiben bytegenau gleich.
- Aktuellen gemeinsamen Einstellungsstand des getesteten VIRIS-Profils übernommen, einschließlich Voxy-Einstellungen, Hotkeys, HUD und Shaderwerten.
- Beispielbild und Erklärung zu Voxy/DH ergänzt; Grenzen und offene Tests sind dokumentiert.

## 2026.11.07.2

Vollständige Einstellungs-Vorlage und automatische Übernahme mit Sicherung vor dem Spielstart ergänzt.
