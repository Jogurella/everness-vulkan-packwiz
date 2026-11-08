# Vitrail Hand-/HUD-Tiefenpufferkorrektur

Ausgang: Vitrail 0.13.0-beta, Commit `7a3cdcbe1dfd7e04fb2b3021ee8352d341e7647e`, LGPL-3.0-only. Der vorhandene optionale DH-/Chunky-Guard bleibt erhalten.

Eigene Änderung: Nach der Auswahl des aktiven Welt-Renderziels wird `hud3DTarget` auf dessen Breite und Höhe gebracht. Eine Größenänderung erfolgt nur bei tatsächlich abweichenden Maßen. Minecraft 26.3 verwendet diesen separaten Tiefenpuffer beim Zeichnen der Hand, Bildschirm-Effekte und 3D-Anzeigen. Bei 80 % Render-Skalierung war die Farbe 3073 × 1728, der zusätzliche Tiefenpuffer aber 3842 × 2160. Der Renderpass wird dann vom Spiel zurückgewiesen.

Genau eine vorhandene Klasse sowie die Mod-Versionsmetadaten sind verändert; die sonstigen Klassen und Lizenztexte der Ausgangs-JAR bleiben erhalten. Keine zusätzliche Hilfsmod, kein eingebettetes Voxy. Standard bleibt 100 % Render-Skalierung. Vollständige gepinnte Quellen einschließlich Änderung: [Quellarchiv](../../downloads/sources/vitrail-0.13.0-everness-depthfix.1-source.zip). Der kleine Diff und die komplette geänderte Klasse liegen hier ebenfalls.

Prüfungen und Grenzen stehen in [PACK-STATUS.md](../../PACK-STATUS.md). Ein erfolgreicher Offline-Test ist keine Zusage für jeden Server oder beliebige andere Shader-Mod-Kombinationen.
