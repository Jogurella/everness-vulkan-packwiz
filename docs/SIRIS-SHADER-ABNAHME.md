# Abnahme 2026.11.08.3: SIRIS und zwei Shader

## Dateiprüfungen

- Pixelated: alle 635 Archiveinträge nach lokalem Patch bytegleich mit der Nutzerdatei. Keine ZIP-Integritätsfehler in den Eingaben. Beide Shader-Einstellungsdateien sind vor dem Spielstart exakt die gelieferten Bytes.
- Erstinstallation, vorhandene defekte Zieldatei mit Sicherung, fehlendes/falsches Original, erneuter Start ohne Zurücksetzen persönlicher Werte sowie Wiederherstellung einer später fehlenden Pixelated-ZIP geprüft. Keine Änderungen an produktiven Profilen durch diese Tests.
- Alte Shader und Einstellungen werden beim ersten Start unter `.everness-shader-setup/backups/` gesichert. Nur überlappende Schlüssel aus `vitrail/options.txt` werden entfernt, damit die gelieferten Shaderwerte tatsächlich gelten; unabhängige Schlüssel bleiben erhalten.
- 127 bestehende Einstellungsdateien bytegleich zum vorherigen Repository-Stand. Ressourcenpacks, QoL-Mods, Hotkeys, HUD, FOV und Fensteroptionen erhalten.
- Packwiz-Index, Downloads, Einstellungsmanifest, Starter und Einstellungen-Export werden vor Veröffentlichung mit Prüfsummen geprüft.

## Vulkan-Laufzeit

Offline-Ablauf mit beiden Shadern, Neuladen und erneutem Weltbeitritt regulär abgeschlossen.

**SIRIS 1.1.6** ersetzt Purescale. Vorgabe: **Custom 100 %**, DLAA-NGX-Modus, Modell M, **2× Frame Generation** auf RTX 40/50; keine reduzierte interne Auflösung angefordert. Die SIRIS-Option `disablePackTaa=true` schaltet das doppelte Shader-TAA ab.

Die erste Vulkan-Gegenprobe mit SIRIS-Einstellung **DLAA** ergab trotz dieses Namens 2561×1440 intern bei 3842×2160 Ausgabe. Deshalb nutzt die endgültige Vorgabe **Custom 100 %**, dessen explizite Rendergröße nicht von der NGX-Empfehlung abhängt. Der Bericht unterscheidet die erste Gegenprobe und die endgültige 100-%-Prüfung; höhere Zahlen aus reduzierter Auflösung gelten nicht als Nachweis für native 4K-Leistung.

## Grenzen

Nur getrennte Singleplayer-Testkopien, kein Serverbeitritt und keine Änderung einer produktiven Welt. Die Tests sind Kompatibilitätsprüfungen und keine Zusage von 140/150 echten FPS. Sichtbare Details und der endgültige FG-/Rendergrößen-Nachweis werden unten ergänzt. Frühere unregelmäßige native Vulkan-Prozessabbrüche sind durch den reinen Modwechsel nicht pauschal gelöst. Langzeit-Multiplayer, alle Dimensionen, alle Shaderprofile und andere Grafikkarten bleiben unvollständig geprüft.

## Sichtprüfung und Einordnung

Direkt im Vulkan-Testfenster gesehen: Unbound bei Tag sowie Pixelated bei Nacht mit beleuchtetem Boden, Wasser, Glas, sichtbarer Kiste, Hand und Totem. Die Pixelated-Aufnahme nach erneutem Weltbeitritt wurde zusätzlich kontrolliert: kein durchsichtiger Boden in dieser Ansicht. OpenGL: Pixelated bei Nacht direkt im Testfenster und Unbound bei Tag anhand der echten Spielaufnahme kontrolliert. Das sind kurze Einzelansichten, keine Prüfung aller Animationen oder Bewegungsartefakte. Frost/Beschlag von Weather Glass unterscheidet sich sichtbar zwischen den Backends; eine vollständige Weather-Glass-Abnahme ist nicht Teil dieses SIRIS-Updates.

Vulkan: Beide Shader, Shaderwechsel, Neuladen, FG aus/ein und erneuter Weltbeitritt geprüft. Im endgültigen Lauf wurde **3842×2160 intern → 3842×2160 Ausgabe** protokolliert; SIRIS meldete aktive 2× FG auch nach dem erneuten Beitritt. Die FG-Ausgabe stammt aus SIRIS' eigener Presenter-Messung und ist kein unabhängiger Benchmark. OpenGL: beide Shader, Neuladen und erneuter Weltbeitritt; die unveränderte Purescale-Frame-Generation wurde hierbei nicht separat abgenommen. Es wurden keine Server betreten.

## Ergebnisdaten

```json
{
  "label": "vulkan-native",
  "renderer": "vulkan",
  "exitCode": 0,
  "complete": true,
  "shaderSwitch": true,
  "shaderReload": true,
  "backendVulkan": true,
  "backendOpenGL": false,
  "joinCount": 2,
  "unboundScreenshotSaved": true,
  "pixelatedScreenshotSaved": true,
  "nightScreenshotSaved": true,
  "voxyShaderDrawPathLogged": true,
  "shaderCompileFailure": false,
  "shaderReportedErrors": false,
  "qaFailure": false,
  "renderSizePairs": [
    [
      1600,
      900,
      1600,
      900
    ],
    [
      3842,
      2160,
      3842,
      2160
    ]
  ],
  "native4kConfirmed": true,
  "frameGeneration2xActive": true,
  "frameGenerationOffObserved": true,
  "fgOffRequested": true,
  "fgReenabledRequested": true,
  "fgActiveAfterRejoin": true,
  "screenshots": [
    "qa-pixelated-fg-on.png",
    "qa-pixelated-night-rain.png",
    "qa-pixelated-rejoin.png",
    "qa-pixelated-reload.png",
    "qa-unbound-fg-off.png",
    "qa-unbound-fg-on.png",
    "qa-unbound-fg-reenabled.png"
  ]
}
```
