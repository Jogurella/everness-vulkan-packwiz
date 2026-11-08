# Weather Glass / Cozy: tatsächliche Abnahme 2026.11.08.2

## Sichtprüfung am 8. November 2026 nachgeholt

Minecraft 26.3, Fabric 0.20.1, Java 25, RTX 4090; vollständige Pack-Basis mit Weather Glass 0.6.0 und **Cozy - Normal**, 16x-Glasdetails, 100 % Shader-Render-Skalierung, Frame Generation aus. Beide Backends wurden einzeln in getrennten Singleplayer-Kopien verwendet. Es wurde kein Multiplayer-Server betreten und keine produktive Welt verändert.

### Direkt am Bildschirm beobachtet

- Vulkan: Regenfilm/Tropfen auf Glas, sichtbarer Steinboden, Landschaft, Kiste, Hand und Totem. Im korrigierten zweiten Schneelauf auch Schneefall, Schnee am Boden und weiterhin sichtbare Glasflächen. Die vollständig entwickelte Frost-/Beschlagschicht wurde unter Vulkan nicht abschließend am Bildschirm abgenommen. In den betrachteten Ansichten kein Durchsicht-/Höhlenfehler festgestellt.
- OpenGL: Schneefall und Frost/beschlagene Glasflächen mit sichtbarem Boden und Kiste. Die Glaswirkung wurde auch am Übergang zwischen Glasblock, Scheiben und gefärbtem Glas betrachtet. Das ist keine vollständige Prüfung jeder Glasfarbe oder Vierergruppen-Anordnung.
- Vitrail-Shader über die vorhandene Belegung neu geladen; die Anzeige „Shaders Reloaded!“ erschien, anschließend endete der Lauf regulär. Kein Nachweis für beliebig häufige Shaderwechsel oder alle Dimensionen. Der Test-Hotkey schaltete außerdem Texture Builder mit um; die unveränderte veröffentlichte Tastenbelegung wurde deshalb nicht als konfliktfrei freigeprüft.
- Everness Launcher 0.10.1: **News aktualisieren → Everness News**, beide Vorschaubilder und beide geöffneten Updateartikel für 2026.11.08.2 sichtbar, inklusive Weather Glass und Cozy Companion.

### Korrektur der Testszene

Die erste automatische Biome-Umschaltung war wegen eines zu großen Befehlsbereichs abgelehnt worden. Die ursprünglichen 165-Sekunden-Läufe belegen daher Regen und Tag/Nacht, **keinen erfolgreichen Schneetest**. Der nur in den Testkopien verwendete Helfer wurde korrigiert. Die Nachtests `visual-opengl` und `cold-vulkan-repeat` liefen jeweils 180 Sekunden, loggten das tatsächliche Client-Biom `minecraft:snowy_plains` und endeten mit Code 0. Erst diese Nachtests und die Bildschirmansichten werden als Schnee-/Frostnachweis gewertet.

### Nicht behoben / noch offen

Der zusätzliche Vulkan-Lauf `cold-vulkan` endete vor dem Schneeteil unerwartet mit `0xCFFFFFFF`, ohne erklärende Java-Ausnahme oder gefundenen JVM-Fatalbericht. Der folgende Lauf war erfolgreich; das erklärt den Abbruch nicht und macht ihn nicht ungeschehen. Eine Ursache durch Weather Glass, Vitrail, Voxy oder eine externe Beendigung ist nicht nachgewiesen. **Keine allgemeine Stabilitätsfreigabe.** Frühere native Abbrüche bleiben ebenfalls offen.

Nicht freigeprüft sind die vollständige Matrix aller Shaderprofile und Glasfarben, die vollständig entwickelte Vulkan-Frostschicht, Nvidium ohne Shader, wiederholte Dimensions-/Shaderwechsel, Wischen/Interaktionen, Frame Generation und Langzeitbetrieb. Die Autorenseite nennt weiter mögliche Artefakte bei Scheiben in Vierergruppen. Beta-Staub und Particle-Rain-Wind bleiben in der Packvorgabe aus. Es wird kein FPS-Gewinn behauptet.

### Einzelne Läufe

| Lauf | Ergebnis |
|---|---|
| `vulkan` | Regulär beendet; Endcode 0. |
| `opengl` | Regulär beendet; Endcode 0. |
| `visual-vulkan` | Regulär beendet; Endcode 0. |
| `cold-vulkan` | Unerwartet beendet; Endcode `0xCFFFFFFF`, Ursache offen. |
| `visual-opengl` | Regulär beendet; Endcode 0. |
| `cold-vulkan-repeat` | Regulär beendet; Endcode 0. |
