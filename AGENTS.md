# Modpack-Update-Ablauf

## Veröffentlichung

- Jeder beauftragte Modpack-Update oder Gameplay-Fix wird mit passendem Updatebild im jeweiligen GitHub-Repository veröffentlicht. Das gehört zum vereinbarten Arbeitsablauf.
- Die betroffenen Mods, Abhängigkeiten, Konfigurationsdateien, packwiz-Metadaten, Einstellungs-Vorlagen und Downloadpakete müssen zum veröffentlichten Stand passen.
- Installation, Updates und Prüfsummen passend zur Änderung prüfen; tatsächlich erfolgte Spieltests von noch offenen Tests unterscheiden.
- Persönliche Kontodaten, Welten und private Karten-/LOD-Caches gehören nicht in den öffentlichen Export.

## Updatebilder

- Every published update or fix gets its own cover showing the actual changes in that update. Do not reuse an unrelated feature image as the new update cover.
- Use a version-specific filename under `.github/assets/`, set `options.everness.news-image` in `pack.toml`, and show the cover near the beginning of `README.md`.
- Keep previous feature illustrations available in their relevant sections.
- Generated illustrative covers use the visible caption `Beispielbild`. Do not present them as game screenshots or measured performance comparisons.
- Preserve the generation prompt under `docs/illustrations/`.
- Keep `AGENTS.md` excluded from the installed Minecraft payload via `.packwizignore`.
