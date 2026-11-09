# VIRIS: Everness-Nebel- und Himmelskorrektur

Grundlage: [VIRIS 0.3.6](https://github.com/laurensanp/viris/tree/2d3061bcb73751a6c70cef50eb3b592b6a4659f3), laurensanp, MIT. Darauf die bereits veröffentlichte [Lifecycle-2-Basis](../viris-equipment). Diese Fassung ist ein eigener Everness-Zusatz und kein offizielles Upstream-Release. Minecraft 26.3, Fabric Loader 0.20.1, JDK 25, Voxy 0.2.20-beta, Sodium 0.9.2, Vitrail 0.13.0-beta samt bisherigem Depthfix. Voxy/Vitrail/Gamma Utils werden nicht eingebettet; übernommen ist die ursprüngliche Interop-Implementierung.

## Eigene Änderungen

- `FogConfigMigration` / `FogCompatPreLaunch`: einmalige gesicherte Korrektur nur des booleschen `nightVision.brightenFogColor` in Gamma Utils. Nachtsicht bleibt an. Bytegenaue Sicherung, spätere Benutzeränderungen respektiert; 28 gezielte Prüfungen gemeinsam mit der Distanzpolitik.
- `NetherFogPolicy` / `NetherAtmosphereMixin`: atmosphärische Nether-Distanz nur ohne aktiven Vitrail-Shader und nur bei aktivem Vulkan/Voxy verlängern. Nicht in Wasser/Lava/Pulverschnee, bei Blindheit/Dunkelheit oder Bossnebel. Grenzen anhand realer Chunk-/LOD-Distanz; keine Änderung der Shaderdetails.
- `SkyFogUniform` / `SkyFogCaptureMixin` / `SkyFogScopeMixin`: eigener kleiner Ringpuffer nur für den Overworld-Standardhimmel bei Vulkan/Voxy ohne aktiven Shader. Er schreibt die Nebelparameter des Himmels ausdrücklich in einen separaten Pass; die Voxy-Geländeparameter mit deaktivierter Renderdistanzbegrenzung bleiben unverändert. Ringrotation nach dem Himmel, Freigabe beim Schließen des SkyRenderer. Tag/Abend/Nacht/Regen, Voxy an/aus, Shaderwechsel und Weltbeitritt offline verglichen.

Alle vorhandenen Klassen der Basis einschließlich Licht-Lifecycle, Kistenmigration und Ausrüstungsschatten sind unverändert. Nur zwei JSON-Metadatendateien ändern sich, acht eigene Klassen und ein Notice kommen hinzu. Kein Voxy, Vitrail oder Gamma Utils in der JAR. Die 28 Teilprüfungen und ein Build ersetzen keine Spielprüfung; [Abnahme und Grenzen](../../docs/NETHER-HORIZON-ABNAHME.md).

## Nachbauen

Python und JDK 25 verwenden; benannten 26.3-Classpath mit Minecraft, Fabric, Mixin/MixinExtras, Vitrail, Voxy und der Basis bereitstellen. Das Skript akzeptiert ausschließlich die SHA-gepinnte [Lifecycle-2-JAR](../../downloads/mods/viris-0.3.6+mc26.3-everness-lifecycle.2.jar).

```powershell
python build.py --base-jar "C:\build\viris-0.3.6+mc26.3-everness-lifecycle.2.jar" --classpath "C:\build\libs\*" --javac "C:\jdk-25\bin\javac.exe" --output "C:\build\viris-0.3.6+mc26.3-everness-lifecycle.3.jar"
```

Alle unkomprimierten Einträge des portablen Neubaus wurden mit der tatsächlich getesteten JAR verglichen. ZIP-Zeitstempel können abweichen.

Getestete JAR SHA-256: `1250ec3539068e361cdd7d9d67b166e31712ba9e340b22c46c320103afe3b25a`. [Download](../../downloads/mods/viris-0.3.6%2Bmc26.3-everness-lifecycle.3.jar) · [Provenienz](provenance.json).

VIRIS 0.4.0 erschien während der Vorbereitung und wurde anhand der Originalquellen geprüft. Sein dokumentierter Schwerpunkt ist Photon-Unterstützung, nicht dieser Nebel-/Himmelsfehler. Deshalb bleibt diese gezielte Veröffentlichung auf dem bereits geprüften 0.3.6-Stack. Es wird keine neueste offizielle VIRIS-Version vorgetäuscht.
