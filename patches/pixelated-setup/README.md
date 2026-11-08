# Lokaler Pixelated-Patch und Shader-Migration

`dev/everness/shaders/ShaderSetup.java` ist der eigene Fabric-preLaunch-Helfer (MIT, keine Mixins/Render-Hooks und keine Netzwerkanfragen). Er ersetzt den bisherigen Cozy-Helfer.

Die separat installierte Originaldatei `ComplementaryUnbound_r5.9.3.zip` muss SHA-256 `98ac741dee294d2a3c82d153349148c3bb7fb688c30eb3b49c3b9ac37e968c5a` haben. `pixelated.evp.gz` enthält Kopier-/Einfügeoperationen pro Archiveintrag, keine eigenständige Shader-ZIP. Alle 635 erzeugten Einträge werden gegen SHA-256 geprüft; `entry-manifest.json` dokumentiert sie. Ohne das richtige Original werden keine alten Shader entfernt und Minecraft darf weiter starten.

Erster Start dieser Migration: Pixelated lokal erzeugen/prüfen, die beiden mitgelieferten Sidecars übernehmen, entfernte Shaderverweise umstellen, überlappende Vitrail-Globalwerte sichern/bereinigen, alle weiteren Shader außerhalb der aktiven Liste sichern. Ein Marker verhindert wiederholte Einstellungs-Resets. Wenn später nur die Pixelated-ZIP fehlt, wird sie erneut erzeugt, ohne persönliche Einstellungen zurückzusetzen. Das normale Pack-Update entfernt den alten Cozy-Generator.

Credits: [Complementary Development](https://www.complementary.dev/), [Euphoria Patches](https://www.euphoriapatches.com/). Shader-/Patchdaten bleiben unter den enthaltenen ursprünglichen Lizenzen; unsere MIT-Lizenz erteilt daran keine zusätzlichen Rechte. Everness ist für die Modpack-Konfiguration und lokalen Änderungen verantwortlich.
