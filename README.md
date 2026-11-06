# Everness Visuals — Vulkan

Client-Modpack für **Minecraft 26.3 · Fabric 0.19.5 · Java 25**. Vitrail + Distant Horizons (BLAZE_3D). Version **2026.11.06**.

## Installation in Prism

1. Den passenden **Prism-Starter** aus [Releases](https://github.com/Jogurella/everness-vulkan-packwiz/releases/tag/v2026.11.06) herunterladen und über **Instanz hinzufügen → Import aus ZIP** importieren. Das ist ein getrenntes Profil, kein automatischer Serverbeitritt.
2. Die lokalen Zusätze gemäß [Pack-Status](PACK-STATUS.md) aus einer vorhandenen, berechtigten Installation übernehmen. Beispiel mit Python 3.11 oder neuer, im geklonten Repository:

   ```powershell
   python tools/import-local.py --from-game "PFAD_ZUM_BISHERIGEN_MINECRAFT_ORDNER" --to-game "PFAD_ZUM_NEUEN_MINECRAFT_ORDNER"
   ```

3. Microsoft-Anmeldung im Launcher selbst erledigen und starten. Der Pre-Launch-Befehl lädt die öffentlich verwalteten Dateien herunter und prüft deren Hashes. Der Server **Forever Ever Server** ist voreingetragen.

Pack-URL: [https://raw.githubusercontent.com/Jogurella/everness-vulkan-packwiz/main/pack.toml](https://raw.githubusercontent.com/Jogurella/everness-vulkan-packwiz/main/pack.toml)

Für eine bereits vorhandene Instanz lautet der normale packwiz-Pre-Launch-Befehl:

```text
"$INST_JAVA" -jar packwiz-installer-bootstrap.jar -g -s client https://raw.githubusercontent.com/Jogurella/everness-vulkan-packwiz/main/pack.toml
```

Die offizielle `packwiz-installer-bootstrap.jar` muss im Minecraft-Ordner der Instanz liegen. Der Starter bringt sie unverändert mit. Standard-Fenster-/Grafik-, Mod- und Tastenwerte stammen aus dem jeweiligen bisherigen Prism-Profil. Kontoauswahl und Java-Pfad werden nicht importiert.

## Umfang und lokale Dateien

83 Download-Einträge sind exakt gepinnt; 14 lokale Dateien werden aus Rechte-/Versionsgründen nicht öffentlich gespiegelt. **Ohne diese Zusätze entspricht eine frische Installation nicht dem vollständigen bisherigen Pack.** Keine Ersatzversionen werden stillschweigend installiert. Details und SHA-512: [manual-files.json](manual-files.json).

| Lokal benötigte Datei | Typ |
| --- | --- |
| `mods/camerautils-fabric-1.1.2+26.3-superzoom-fix.jar` | mods |
| `mods/MyTotemDoll-2.7.0+26.3+fabric.jar` | mods |
| `mods/punchy-2.8g-fabric-26.3.jar` | mods |
| `resourcepacks/Darkmode - V.1.2 - jannik6879.zip` | resourcepacks |
| `resourcepacks/Deepslate-Tuff-Hellrot-26.3.zip` | resourcepacks |
| `resourcepacks/Enchantment Outlines.zip` | resourcepacks |
| `resourcepacks/FA+Player-v1.1.zip` | resourcepacks |
| `resourcepacks/Fresh Food.zip` | resourcepacks |
| `resourcepacks/VanillaTweaks_r144903_MC1.21.x.zip` | resourcepacks |
| `resourcepacks/§aBetter Fresher 3D Books v1.5.1.zip` | resourcepacks |
| `resourcepacks/§f§lActually §6§l3D §fBlocks & Items!§7.zip` | resourcepacks |
| `resourcepacks/§lColourful Containers§8.zip` | resourcepacks |
| `shaderpacks/ComplementaryReimagined_r5.9.3.zip` | shaderpacks |
| `shaderpacks/Tobias-Pixel.zip` | shaderpacks |

## Wartung

Mit [packwiz](https://packwiz.infra.link/) im Repository arbeiten. Nach gewollten Dateiänderungen `packwiz refresh` und `python tools/validate.py` ausführen. Modupdates bewusst einzeln prüfen und gegebenenfalls mit `packwiz unpin` freigeben; die zwei Renderer haben unterschiedliche Kompatibilitätsanforderungen. Keine pauschale Versionsanhebung.

Die unveränderten lokalen Spielprofile werden durch dieses Repository nicht angepasst. `tools/import-local.py --check-only` prüft die Zusätze, ohne Dateien zu ändern. Beim Import werden abweichende vorhandene Zieldateien zuerst lokal gesichert.

Siehe [Modliste](MODLIST.md), [Lizenz-/Herkunftshinweise](THIRD-PARTY.md) und [Pack-Status](PACK-STATUS.md).
