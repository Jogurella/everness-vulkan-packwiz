# VIRIS: gezielte Everness-Lichtkorrektur

Grundlage: [VIRIS 0.3.6 von laurensanp](https://github.com/laurensanp/viris/tree/2d3061bcb73751a6c70cef50eb3b592b6a4659f3), MIT. Minecraft 26.3, Fabric 0.20.1, JDK 25, Vitrail 0.13.0-beta (vorhandener Everness-Depthfix), Voxy 0.2.20-beta. Kein VulkanMod.

Eigene Änderung: `VoxyFrame` vergleicht zusätzlich die Objektidentität der aktuellen `PackValues` mit der im vorhandenen `PackPipeline` gespeicherten. Bei Austausch werden alte GPU-Ressourcen über den bestehenden `dropPackPipeline`-Pfad freigegeben und die Pipeline neu gebaut. Der bisherige Schlüssel nur aus Packname, Profil, Optionen, Dimension und Auflösung reichte dafür nicht.

`VoxyFrame.java` ist die vollständige geänderte Quelldatei; `lifecycle.patch` der minimale Unterschied. Alle anderen bestehenden Klassendateien bleiben bytegleich zur offiziellen JAR. Keine neuen EMF-/ETF-Mixins, kein Voxy-/Vitrail-Code eingebettet. Lizenzdateien der Original-JAR bleiben erhalten.

## Bauen

Die unveränderte offizielle `viris-0.3.6+mc26.3.jar` und den normalen Entwicklungs-Classpath für Minecraft 26.3, Fabric, Voxy, Vitrail, LWJGL und deren Abhängigkeiten bereitstellen. Dann unter Windows mit Python und JDK 25:

```powershell
python build.py --upstream-jar "C:\build\viris-0.3.6+mc26.3.jar" --classpath "C:\build\libs\*" --javac "C:\jdk-25\bin\javac.exe" --output "C:\build\viris-0.3.6+mc26.3-everness-lifecycle.1.jar"
```

Das Skript prüft die offizielle SHA-512, kompiliert nur die geänderte äußere Klasse und übernimmt innere Klassen aus der Original-JAR. Die Byte-Prüfung begrenzt Änderungen auf `VoxyFrame.class` und die Versionsangabe; zusätzlich kommt der Herkunftshinweis hinzu. ZIP-Zeitstempel können bei Neubauten abweichen. Ein erfolgreicher Build ersetzt keinen Spieltest.

[Download der getesteten JAR](../../downloads/mods/viris-0.3.6%2Bmc26.3-everness-lifecycle.1.jar) · [Offline-Abnahme](../../docs/VISUAL-FIX-ABNAHME.md)

SHA-256 der veröffentlichten JAR: `5b0cbc7974983ce52f13ec47ce2e84f4736c9d5d66090307424b78395936638a`.
