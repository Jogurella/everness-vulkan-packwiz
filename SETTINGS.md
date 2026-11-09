# Einstellungsstand 2026.11.09.2

Die gemeinsame Vorlage stammt aus dem veröffentlichten Vulkan-Pack. OpenGL setzt den OpenGL-Backendwert und die entsprechenden Iris-Shader-Tasten. HUD-Felder, Jade-Skalierung, Minimap, FOV, Vollbild, Ressourcenpack-Reihenfolge, Punchy und QoL-Mod-Einstellungen entsprechen der Vorlage. Zusätzliche OpenGL-Shader bleiben zur Auswahl erhalten.

Bei einer neuen Installation sind die Werte im Prism-Starter bereits enthalten. Ein reguläres Update bewahrt vorhandene persönliche Werte. Für den ausdrücklich gewünschten vollständigen Abgleich Minecraft beenden und das kleine [Einstellungspaket](downloads/Everness-vulkan-Einstellungen.zip) entpacken. Dann mit Python 3.11 oder neuer:

```powershell
python tools/restore-settings.py --game-dir "PFAD_ZUM_MINECRAFT_ORDNER" --again
```

Vor dem Ersetzen werden alle Dateien und Prüfsummen geprüft; bisherige Werte werden unter `.everness-settings/backups` gesichert. Im OpenGL-Profil werden die drei veralteten HUD-Dateien dabei gesichert aus dem aktiven HUD-Verzeichnis genommen, damit keine alten Anzeigen daneben stehen. Die neue Vorlage ist vollständig in `settings-manifest.json` aufgelistet. Konten, Welten, persönliche Karten-/LOD-Daten und Logs sind nicht enthalten.

Weather Glass bleibt in der bisherigen Konfiguration enthalten. Cozy und sein Generator sind ab 2026.11.08.3 entfernt.

## 2026.11.08.3: genau zwei Shader

Die beiden Sidecars `shaderpacks/Pixelated.zip.txt` und `shaderpacks/ComplementaryUnbound_r5.9.3.zip.txt` sind exakt die Nutzerdateien. Der erste Start übernimmt sie einmal mit Sicherung vorhandener Werte und bereinigt alte Shaderverweise. Spätere eigene Werte bleiben erhalten. **SIRIS 1.1.6** ersetzt Purescale. Vorgabe: **Custom 100 %**, DLAA-NGX-Modus, Modell M, **2× Frame Generation** auf RTX 40/50; keine reduzierte interne Auflösung angefordert. Die SIRIS-Option `disablePackTaa=true` schaltet das doppelte Shader-TAA ab.

## 2026.11.08.4: EBE

Neu ist ausschließlich `config/enhanced_bes.properties`: Kisten/Schilder an, andere EBE-Modelltypen aus, alle Schildtexte an, Ressourcenpack-Kompatibilitätsmodus an. Bestehende 131 Einstellungsdateien sind unverändert. Mod Menu bietet Schalter zum Zurückstellen. Das Einstellungsmanifest und beide Export-ZIPs enthalten die neue Datei; persönliche vorhandene Werte werden von packwiz als `preserve` behandelt.

## 2026.11.09.1: Kistenmodelle und Erhalt persönlicher Werte

Einzige Änderung an der bisherigen Vorlage: `config/enhanced_bes.properties`, `render_enhanced_chests=false`. EBE-Schilder bleiben an; Fresh Animations Objects, Skins, Animationen, Reihenfolge der 29 Packs, beide Shader-Sidecars, SIRIS, Sichtweite und sämtliche QoL-/HUD-/Hotkey-Dateien bleiben bytegleich. 131 von 132 bisherigen Einstellungsdateien sind unverändert.

Packwiz markiert Einstellungsdateien weiter als `preserve`. Der Everness Launcher übernimmt neue Vorlagen mit seiner normalen Vorlagenfunktion; ist „Eigene Einstellungen behalten“ aktiviert, respektiert er die vorhandenen Werte. Deshalb gegebenenfalls den EBE-Kistenschalter in Mod Menu manuell ausschalten. Es gibt keine versteckte erzwungene Config-Migration. Die neue VIRIS-JAR wird unabhängig von persönlichen Einstellungen aktualisiert.

## 2026.11.09.2: Vorlage unverändert, gezielte Kistenmigration beim Spielstart

Alle 132 Dateien der Vorlage sind SHA-256-identisch mit 2026.11.09.1. Darunter weiterhin `render_enhanced_chests=false`; keine Änderung an Grafik, Shadern, Auflösung, Sichtweite, SIRIS, FOV, Hotkeys, HUD oder QoL. Packwiz behält `preserve=true` bei.

Neu gegenüber dem historischen Hinweis oben: Die angepasste VIRIS-JAR korrigiert eine noch vorhandene persönliche Alt-Einstellung `render_enhanced_chests=true` einmalig, **vor** EBE seine Ressourcen baut, wenn `FA+Objects` in der aktiven Packliste steht. Sie sichert die unveränderte alte Datei unter `.everness/compat-backups/`, ändert nur diesen einen Wert und legt danach `.everness/compat-migrations/ebe-original-chests-v1.done` an. Bereits ausgeschaltete Kisten bleiben aus. Spätere eigene Änderungen werden nach erfolgreicher Migration respektiert. Mehrdeutige/unbekannte oder fehlende Dateien werden nicht überschrieben. Fehler blockieren den Spielstart nicht.
