# Verbindlicher Ablauf für künftige Everness-Modpack-Updates

## Bisherige Zusatzaufträge

Vulkan 2026.11.08.4 ergänzt EBE Reloaded + MatLib für Kisten/Schilder. VulkBatch wurde nach Quellprüfung abgelehnt. Bestehende Shader-, HUD-, Hotkey- und Performancewerte bleiben erhalten. Prüfbericht `docs/FARM-PERFORMANCE-ABNAHME.md` beachten.

Weather Glass wurde mit 2026.11.08.2 aufgenommen und bleibt erhalten. Ab 2026.11.08.3 gibt es nur Pixelated + Unbound; Cozy samt Generator ist entfernt. Vulkan nutzt SIRIS statt Purescale, OpenGL behält Purescale. Den aktuellen Prüfstand und noch offene Sichtprüfungen in [docs/NEXT-PACK-UPDATE.md](docs/NEXT-PACK-UPDATE.md) und PACK-STATUS.md beachten. Neue Aufgaben nur nach konkretem Nutzerauftrag ergänzen.

Der Nutzer hat die vollständige Vorlage in `docs/EVERNESS-MODPACK-UPDATE.txt` als Standard freigegeben. Sie gilt ab dem nächsten Modpack-Update. Reine Änderungen an diesem Arbeitsablauf verändern die derzeitige Pack-Veröffentlichung nicht.

## Umfang und Version

- OpenGL und Vulkan getrennt behandeln; bei einem Pack-Auftrag nur dessen Repository ändern.
- Gewünschte Mods, Fehlerkorrekturen und Einstellungen aus dem konkreten Auftrag übernehmen. Unausgefüllte Platzhalter sind kein Auftrag für erfundene Inhaltsänderungen.
- Bei jeder veröffentlichten Pack-Inhaltsänderung `version` in `pack.toml` erhöhen, auch bei unveränderten Minecraft-/Fabric-Versionen.
- Änderungen, Abhängigkeiten und Prüfsummen anhand der tatsächlichen Dateien prüfen. Keine erfundenen FPS- oder Kompatibilitätsversprechen.
- Keine Kontodaten, Welten, privaten Karten-/LOD-Caches oder Logs veröffentlichen.

## Änderungsnotizen und Everness News

- `CHANGELOG.md` im Repository-Hauptordner in UTF-8 pflegen. Den neuen Versionsabschnitt ganz oben ergänzen, frühere Versionen darunter erhalten.
- Versionsnummer exakt wie in `pack.toml`; Format:

```markdown
## [2026.11.08.1] - 2026-11-08
### Neu
- Konkrete Ergänzung und Nutzen für Spieler.
### Behoben
- Konkret behobener Fehler.
### Geändert
- Geändertes Verhalten.
### Hinweise für Spieler
- Bedienung, Hotkeys, notwendige Schritte oder bekannte Grenzen.
```

- Leere Unterabschnitte weglassen. Nur Änderungen der jeweiligen Version beschreiben.
- Everness News übernimmt den passenden Versionsabschnitt ab Launcher 0.9.7 aus dem konfigurierten Branch. Ein Commit-Text ersetzt keinen erklärenden Changelog-Artikel; ein separates GitHub-Release ist für Pack-News nicht erforderlich.

## Eigenes Bild pro Update oder Fix

- Jede neue Pack-Version bekommt ein eigenes, auf ihre tatsächlichen Änderungen abgestimmtes Bild. Keine sachfremden Motive oder vollständigen Kopien früherer Updatebilder.
- Versionsspezifischer Dateiname im selben Repo unter `.github/assets/`; PNG/JPG/WebP, maximal 8 MiB, bevorzugt 16:9.
- Stil am bestehenden Everness-Updatebild orientieren: hochwertige Minecraft-Voxelwelt, dunkle Informationsflächen, gut lesbare helle Blockschrift, grüne/türkise Akzente.
- Packname, neue Version und zwei bis vier konkrete Änderungen mit kurzen Erklärungen zeigen; auch verkleinert lesbar. Keine unbelegten Messwerte oder Funktionen bewerben.
- Illustrationen mit `Beispielbild` kennzeichnen, wenn sonst eine reale Spielaufnahme oder ein echter Leistungsvergleich angenommen werden könnte. Generation-Prompt unter `docs/illustrations/` erhalten.
- In der vorhandenen Tabelle `[options.everness]` den Schlüssel `news-image` auf den Repo-relativen Bildpfad setzen. Keine doppelte Tabelle; bestehende `settings-template`- und `settings-sha256`-Schlüssel erhalten.
- Das Bild zusätzlich unter der aktuellen Updatebeschreibung im README verlinken. Die explizite `news-image`-Angabe hat im Launcher Vorrang; keine lokalen Windows-Pfade oder fremden Bildadressen verwenden.

## Konsistenter Export und Veröffentlichung

- Betroffene Mods, Abhängigkeiten, Konfigurationen, Einstellungs-Vorlagen, Downloadpakete, Dokumentation und Prüfsummen konsistent halten.
- `packwiz refresh` ausführen. Bei indizierten Einstellungs-Vorlagen Version, Dateihashes, Manifest und `settings-sha256` gemeinsam aktualisieren.
- Vorhandene Repo-Prüfung ausführen, beispielsweise `python tools/validate.py`; Downloads und Prüfsummen kontrollieren.
- Newsbilder, reine Dokumentation und `AGENTS.md` über `.packwizignore` vom installierbaren Pack ausschließen. Keine Spieloptionen oder Moddateien unbeabsichtigt ausschließen.
- Changelog-Version, Bildpfad und vollständigen Export prüfen. Änderungen zusammen in den konfigurierten Branch veröffentlichen. Bei mehrstufigen Uploads erst den vollständigen, konsistenten Stand über den Branch freigeben.

## Abschluss und Launcher-Prüfung

- Neue Pack-Version, Commit, wichtigste Änderungen, tatsächlich durchgeführte Tests, bekannte Einschränkungen sowie Links zu Changelog und direktem Updatebild nennen.
- Danach im Everness-Launcher `News aktualisieren → Everness News → passenden Pack-Beitrag` prüfen: Bild und aktuelle Erklärung müssen bereits vor Installation des Pack-Updates sichtbar sein.
- Der reguläre News-Cache kann bis zu fünf Minuten alt sein; `News aktualisieren` erzwingt die Abfrage. Netzwerkfehler und zwischengespeicherte Inhalte berücksichtigen.
- Launcher- und Pack-Versionen getrennt halten. Eine nicht mögliche Launcher-/Spielprüfung als offen nennen und nicht als bestanden ausgeben.
