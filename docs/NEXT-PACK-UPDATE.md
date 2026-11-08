# Vorgemerkt für das nächste Modpack-Update

Auftrag vom 8. November 2026: **Weather Glass im nächsten Update zu Everness Vulkan und Everness OpenGL hinzufügen.** Noch keine Mod-Dateien installieren und keine laufenden Spielprofile ändern. Das getrennte Lumen-Testpack ist von diesem Auftrag nicht umfasst.

## Einbau und Abnahme

- [ ] Zum Einbauzeitpunkt die aktuelle offizielle **Minecraft 26.3 / Fabric**-Datei, Abhängigkeiten, Lizenz und Prüfsummen erneut prüfen und im Pack pinnen. Recherchierter Ausgangsstand: Weather Glass 0.5.0, Autor duffopuffo; Quelle: https://www.curseforge.com/minecraft/mc-mods/weather-glass .
- [ ] In beiden Pack-Repositories aufnehmen und die gemeinsamen Mod-Einstellungen synchron halten. Als Ausgangspunkt die leichte Vanilla-Detailstufe (16x) verwenden, sofern die dann gewählte Version sie anbietet.
- [ ] OpenGL mit Sodium/Iris, Vulkan mit Minecrafts eingebautem Vulkan-Renderer, Vitrail und VIRIS/Voxy jeweils separat offline prüfen. Die Projektseite bestätigt Fabric 26.3 und nennt getestete Sodium-/Iris-/Continuity-/MoreCulling-Unterstützung; eine eigene native Vulkan-Abnahme ist damit noch nicht belegt.
- [ ] Regen/Schnee vor und hinter normalen, gefärbten und getönten Glasblöcken und Glasscheiben prüfen; Shader an/aus, Tag/Nacht, verbundene Texturen, MoreCulling und Particle Rain berücksichtigen. Beim OpenGL-Pack den Nvidium-Zustand beachten.
- [ ] Bekannte Einschränkungen des Ausgangsstands 0.5.0 beim Einbau erneut prüfen: Darstellungsfehler bei Glasscheiben in Vierergruppen, noch ungetestetes Nvidium sowie als Beta bezeichnete Staub-/Particle-Rain-Funktionen. Verbleibende Fehler offen dokumentieren.
- [ ] Erst mit dem tatsächlichen Pack-Update Version, Packwiz-Index, Einstellungsmanifest, Downloads und Prüfsummen aktualisieren.
- [ ] Ein eigenes KI-generiertes Weather-Glass-Updatebild nach AGENTS.md erstellen: Minecraft-Fenster mit Regentropfen als passendes Motiv, jeweiliger Packname und endgültige Updateversion, Kennzeichnung „Beispielbild“. Nur tatsächlich enthaltene und geprüfte Funktionen beschreiben.
- [ ] Bild zusammen mit den passenden Änderungsnotizen in beiden Repositories veröffentlichen, über `news-image` verknüpfen und die Darstellung in Everness News prüfen. Zur bloßen Vormerkung wird noch kein fertiges Updatebild behauptet.

**Status:** Vorgemerkt, noch nicht eingebaut und noch kein eigener Weather-Glass-Spieltest durchgeführt. Die aktuelle Pack-Version bleibt durch diese Aufgabenliste unverändert.
