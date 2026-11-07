# Einstellungen 2026.11.07.2

Vollständiger, portabler Einstellungsstand aus **Everness-Vulkan**: 132 Dateien und 206 Minecraft-/Mod-Tastenbelegungen. Die beiden Renderer behalten ihre jeweiligen Werte.

Enthalten: Minecraft-Optionen samt Ressourcenpack-Reihenfolge, CameraUtils-Zoom, Punchy einschließlich des versteckten aktiven Profils, EvergreenHUD/OneConfig, Jade, Xaero, JEI, Inventar-/Werkzeugfunktionen, Totem, Animationen, Shader-Auswahl und alle gespeicherten Shaderprofile. `settings-manifest.json` nennt jeden Pfad und SHA-256-Hash. `launcher-settings.json` dokumentiert RAM, JVM- und Fensterwerte; der neue Prism-Starter setzt sie bereits.

## Automatisch vor dem Spielstart

Der Everness-Launcher übernimmt diese vollständige, per SHA-256 gebundene Vorlage vor dem ersten Start bzw. beim Wechsel auf einen geänderten Vorlagenstand. Vorhandene falsche Standardwerte werden mit Sicherung ersetzt. Dazu gehören FOV, sämtliche Minecraft-Optionen und Tasten, aktive Ressourcenpacks und deren Reihenfolge, Shader-Auswahl und Shaderfunktionen sowie die Mod-Konfigurationen. RAM und Fensterwerte werden gemeinsam übernommen.

Jede Datei wird im tatsächlichen Spielordner geprüft. Der Nachweis und der Sicherungspfad stehen unter `.everness/settings-audit.json`. Eine unveränderte Vorlage setzt spätere eigene Anpassungen nicht bei jedem Start zurück. Ein reines Archivupdate ohne geänderte Vorgaben setzt ebenfalls keine eigenen Einstellungen zurück.

## Neue Installation

Den [aktuellen Prism-Starter](https://github.com/Jogurella/everness-vulkan-packwiz/releases/tag/v2026.11.07.2) importieren. Er enthält die Einstellungen bereits vor dem ersten Spielstart; packwiz lädt die unveränderten gepinnten Mod-/Packarchive nach.

## Bereits installiertes GitHub-Profil korrigieren

Minecraft schließen. Das Repository herunterladen/klonen und im entpackten Repository mit Python 3.11+ ausführen:

```powershell
python tools/restore-settings.py --game-dir "PFAD_ZUM_MINECRAFT_ORDNER"
```

Der Zielordner ist der Spielordner mit `mods`, `config` und `options.txt`, nicht der Launcherordner. Alle Dateien werden zuerst geprüft; abweichende vorhandene Werte werden lokal unter `.everness-settings/backups` gesichert. Dieser Stand wird nur einmal angewandt. Spätere eigene Einstellungen bleiben erhalten. `--check-only` prüft, `--again` stellt bewusst erneut wieder her. Der Release-Download **Everness-vulkan-Einstellungen.zip** enthält denselben kleinen Wiederherstellungssatz.

Der aktuelle Everness-Launcher hat `options.txt` selbst bei leeren Installationen übersprungen. Die Korrektur im Launcher erlaubt Erstbelegung fehlender Optionen und bewahrt vorhandene Optionen bei normalen Updates. Für bereits erzeugte Standardwerte ist die oben beschriebene einmalige Wiederherstellung vorgesehen.

Nur die absolute Litematica-Verzeichnisangabe wurde portabel gemacht (`schematics`). Konto-/Cookie-Dateien, Eingabeverlauf, per-Welt-Schematic-Platzierungen, alte IPN-Daten, Diagnose-/Hardware-Caches, Welten und LOD-Daten sind keine gemeinsamen Standardeinstellungen und werden nicht verteilt. Der Totem-Skin und die Mod-Schalter bleiben wie im Ausgangsprofil.
