# Lokale Änderungssourcen

Diese Quellen dokumentieren die im bestehenden Profil bereits installierten lokalen Builds. Keine offiziellen Releases. Die HUD-Änderungen unterliegen der GPL-3.0 des Ausgangsprojekts; die DH-/Vitrail-Änderungen stehen unter LGPL-3.0 wie die Ausgangsprojekte. Originalautoren und Lizenzhinweise bleiben erhalten.

HUD: `LunarCompassStyle.java`, `HudSettingsButton.java` und der ASM-Transformator `PatchCompass.java`; ursprüngliche EvergreenHUD-Version 3.9.16 für Minecraft 26.3. Der zweite Client-Einstieg lautet `org.polyfrost.evergreenhud.client.hud.direction.HudSettingsButton`. Die bereits vorhandenen Einstiegspunkte bleiben erhalten; Fabric Screen API wird zusätzlich benötigt.

DH: `ChunkyReadiness.java` und `PatchChunky.java` korrigieren den noch nicht verfügbaren Chunky-Anbieter. Ausgangsversion Distant Horizons 3.3.4 (26.3). Vitrail: `PatchDistantWarm.java` ergänzt ausschließlich die Live-DH-Abfrage vor der spekulativen Shaderkompilierung in 0.13.0-beta.

Die ASM-Programme nehmen den Pfad der ursprünglichen Klasse und den gewünschten Ausgabepfad entgegen. Zum Kompilieren: Java/JDK 25, ASM 9.x; für die HUD-/DH-Helfer außerdem der originale Minecraft-26.3-/Fabric-/Mod-Klassenpfad. Die veränderten Klassen werden danach in die unveränderte Ausgangs-JAR eingesetzt; sämtliche übrigen Einträge und Lizenzen bleiben erhalten. Die zugehörigen Originalquellen sind über `THIRD-PARTY.md` verlinkt.
