# VIRIS: Everness-Kistenmigration und Ausrüstungsschatten

Grundlage: [VIRIS 0.3.6, laurensanp, MIT](https://github.com/laurensanp/viris/tree/2d3061bcb73751a6c70cef50eb3b592b6a4659f3), darauf der bereits veröffentlichte [Lifecycle-Fix 1](../viris-lifecycle). Dies ist eine eigene Everness-Anpassung, kein offizielles Upstream-Release. Minecraft 26.3, Fabric 0.20.1, JDK 25, Vitrail 0.13.0-beta mit bestehendem Everness-Depthfix, Voxy 0.2.20-beta und Sodium 0.9.2. Kein VulkanMod, kein eingebettetes Voxy.

## Eigene Änderungen

- `ChestConfigMigration`/`ChestCompatPreLaunch`: einmaliger, gesicherter Wechsel ausschließlich der EBE-Kistenoption vor der Ressourcenerzeugung. Nur mit aktivem FA+Objects; Marker respektiert spätere Benutzereingaben. 17 gezielte Prüfungen einschließlich Byteerhalt/CRLF, Backup, Marker, unbekannter/mehrdeutiger Keys und fehlendem Objects-Pack.
- `VitrailEquipmentShadowScopeMixin`/`EquipmentShadowScope`: verschachtelungssicherer Bereich um Vitrails tatsächliche Schatten-Geometriesammlung einschließlich Submission. `finally` räumt den ThreadLocal-Bereich auf.
- `EnchantedEquipmentShadowMixin`: Im Schattenbereich eine reine Zeichenkopie des ItemStacks mit `ENCHANTMENT_GLINT_OVERRIDE=false` an die unveränderte Minecraft-Ausrüstungsroutine geben. Dadurch bleiben echte Textur, Alpha und Cape-Pfad erhalten. Außerhalb des Schattenbereichs wird der originale Stack weitergereicht; kein Inventar-/NBT-Eingriff und kein abgeschalteter sichtbarer Glanz.

Minecraft 26.3 benutzt für verzauberte Ausrüstung eine kombinierte Glint-Pipeline. Vitrail 0.13 führt diese nicht in seiner Schatten-Elementtabelle; nicht zugeordnete Schattenzeichnungen werden verworfen. Die Korrektur nutzt deshalb den vorhandenen alpha-getesteten Grundtexturpfad. Ein bloßer Pipeline-Alias würde die Glint-Texturmatrix übernehmen und wurde nicht verwendet. Die vollständige Methode wird umschlossen, um mit den vorhandenen EMF-/ETF-Umschreibungen ihres inneren Aufrufs auszukommen.

Gegenüber Lifecycle-Fix 1 ändern sich nur `fabric.mod.json` und `viris.mixins.json`; fünf eigene Klassen und ein Hinweis kommen hinzu. **Alle vorhandenen Klassen einschließlich des Voxy-Lichtfixes sind bytegleich.** Keine Voxy-/Vitrail-/EMF-/ETF-Klassen eingebettet, keine zusätzliche allgemeine EMF-/ETF-Brücke.

## Nachbauen

Zuerst die gepinnte [Lifecycle-1-Basis](../../downloads/mods/viris-0.3.6+mc26.3-everness-lifecycle.1.jar) bereitstellen (SHA-256 `5b0cbc7974983ce52f13ec47ce2e84f4736c9d5d66090307424b78395936638a`). Deren eigener Source-/Baupfad liegt unter [viris-lifecycle](../viris-lifecycle). Den normalen benannten 26.3-Entwicklungs-Classpath einschließlich dieser JAR, Fabric, Minecraft, Vitrail, Voxy, Mixin und MixinExtras bereitstellen. Dann mit Python und JDK 25 unter Windows:

```powershell
python build.py --base-jar "C:\build\viris-0.3.6+mc26.3-everness-lifecycle.1.jar" --classpath "C:\build\libs\*" --javac "C:\jdk-25\bin\javac.exe" --output "C:\build\viris-0.3.6+mc26.3-everness-lifecycle.2.jar"
```

Das Skript akzeptiert ausschließlich den SHA-gepinnten Vorgänger, kompiliert genau fünf Quelldateien und prüft die erlaubten Änderungen. ZIP-Zeitstempel können abweichen; der unkomprimierte JAR-Inhalt wird mit dem tatsächlich getesteten Kandidaten verglichen. Ein Build allein ist kein Spielnachweis.

[Getestete JAR](../../downloads/mods/viris-0.3.6%2Bmc26.3-everness-lifecycle.2.jar) · [Prüfbericht und Grenzen](../../docs/CHEST-ELYTRA-ABNAHME.md)

SHA-256: `aaf84361dc3baabcc3ab7229ac2f2f6195d6b0cd69af23e782f4eae8a94eab04`
