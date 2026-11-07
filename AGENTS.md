# Pack update images

- Every published update or fix gets its own cover showing the actual changes in that update. Do not reuse an unrelated feature image as the new update cover.
- Use a version-specific filename under `.github/assets/`, set `options.everness.news-image` in `pack.toml`, and show the cover near the beginning of `README.md`.
- Keep previous feature illustrations available in their relevant sections.
- Generated illustrative covers use the visible caption `Beispielbild`. Do not present them as game screenshots or measured performance comparisons.
- Preserve the generation prompt under `docs/illustrations/`.
- Keep `AGENTS.md` excluded from the installed Minecraft payload via `.packwizignore`.
