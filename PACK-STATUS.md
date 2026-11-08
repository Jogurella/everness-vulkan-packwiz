# Prüfstand 2026.11.08.1

66 Mods, 29 Ressourcenpacks und 4 Shaderarchive. Minecraft 26.3, Fabric 0.20.1, Java 25. Tests ausschließlich in einer separaten Singleplayer-Welt; keine produktiven Welten oder Server verändert.

## Vulkan-Korrektur

- Ursprünglichen Java-Absturz mit 80 % Vitrail-Render-Skalierung in der separaten Offline-Welt reproduziert: 3842 × 2160 Tiefenpuffer trifft auf 3073 × 1728 Farbpuffer.
- Geänderte Vitrail-Klasse passt den zusätzlichen Hand-/HUD-Tiefenpuffer an das aktive Weltziel an; alle übrigen Klassen des bisherigen Builds bleiben gleich.
- Erfolgreiche 60-Sekunden-Läufe mit sichtbarer Hand, Schwert und Totem sowie Shader und Voxy bei 80 % und 100 % dokumentiert. Die Prüftabelle nennt jeden einzelnen Lauf.
- Beim Sichttest waren Hände, die Farmplattform und die Landschaft sichtbar. Kein vollständiger Test aller Texturen, Dimensionen, Wetterzustände oder Shaderwechsel.

## Offene Grenzen

In zwei weiteren Startversuchen brach der Java-Prozess nativ mit `0xC0000005` ab, ohne den ursprünglichen Java-Größenfehler. Windows nannte einmal `ntdll.dll`, einmal `jvm.dll`. Die Ursache dieser zusätzlichen, unregelmäßigen Abbrüche ist nicht nachgewiesen; sie werden durch die erfolgreiche Größenkorrektur nicht als behoben ausgegeben. Shader-Neuladen und eine vollständige Shaderwechsel-Matrix sind nicht freigeprüft. Es wurde kein Multiplayer-Server betreten und keine allgemeine Absturzfreiheit zugesichert.

## Protokollierte Teilprüfungen

| Lauf | 60 Sekunden mit Hand | Größenfehler | Ergebniscode |
| --- | --- | --- | --- |
| unpatched80 | Nein | Ja | 15 |
| patched80 | Nein | Nein | 3221225477 |
| patched80-repeat | Ja | Nein | 0 |
| patched100 | Nein | Nein | 3221225477 |
| patched100-repeat | Ja | Nein | 0 |
| patched-photon80 | Ja | Nein | 0 |
| opengl-parity | Ja | Nein | 0 |

Eine Messung der FPS-Steigerung war nicht Gegenstand dieses Fehlers; es wird kein FPS-Gewinn behauptet.
