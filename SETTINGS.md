# Einstellungsstand 2026.11.08.4

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
