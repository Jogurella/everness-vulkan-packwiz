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
