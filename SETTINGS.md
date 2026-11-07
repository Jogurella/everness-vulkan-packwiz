# Gemeinsame Einstellungen 2026.11.07.3

131 Dateien aus dem getrennt getesteten VIRIS-Vulkan-Profil; 206 Minecraft-/Mod-Hotkeys. FOV, Ressourcenpack-Reihenfolge, Punchy einschließlich aktiver Profilwahl, EvergreenHUD/OneConfig, Jade, Xaero, Inventar-/Werkzeugfunktionen, CameraUtils, Animationen, Voxy sowie Vitrail- und Shaderwerte sind enthalten.

Neue Installationen verwenden den [aktuellen Prism-Starter](https://github.com/Jogurella/everness-vulkan-packwiz/raw/refs/heads/main/downloads/Everness-vulkan-Prism-Starter.zip), **Fabric Loader 0.20.1** und **Java 25**. Der Starter enthält die Einstellungen vor dem ersten Start.

Bestehende Prism-Installationen müssen den Loader in Prisms Versionsverwaltung auf 0.20.1 oder neuer bringen. Für die Übernahme der gemeinsamen Vorlage Minecraft schließen und den [Einstellungssatz](https://github.com/Jogurella/everness-vulkan-packwiz/raw/refs/heads/main/downloads/Everness-vulkan-Einstellungen.zip) entpacken. Darin ausführen:

```powershell
python tools/restore-settings.py --game-dir "PFAD_ZUM_MINECRAFT_ORDNER"
```

Das Werkzeug prüft zuerst alle SHA-256-Hashes und sichert abweichende Dateien unter `.everness-settings/backups`. `--check-only` prüft ohne Änderung; `--again` stellt bewusst erneut her. Jede Vorlagenrevision wird einmal angewandt. Im Everness-Launcher erfolgt die Übernahme dieser indizierten Vorlage vor dem Spielstart mit Sicherung.

Die absolute Litematica-Verzeichnisangabe ist portabel als `schematics` gespeichert. Konto-/Cookie-Dateien, Hardware-/Diagnosecaches, per-Welt-Schematic-Platzierungen und persönliche Karten-/LOD-Daten werden nicht veröffentlicht. Distant Horizons ist aus der aktiven Vorlage entfernt; sein alter Cache wird nicht gelöscht.
