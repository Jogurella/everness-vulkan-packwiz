# Everness Visuals — Vulkan

**Minecraft 26.3 · Fabric 0.19.5 · Java 25 · Pack-Version 2026.11.06.1**

Vitrail und Distant Horizons über BLAZE_3D. Vollständiger Archivstand des bisherigen Prism-Profils: **97 Downloads**, einschließlich aller lokalen Mod-, Ressourcenpack- und Shaderanpassungen. **Keine zusätzliche lokale Dateisammlung erforderlich.**

## In Prism installieren

1. Unter [Releases](https://github.com/Jogurella/everness-vulkan-packwiz/releases/tag/v2026.11.06.1) **Everness-vulkan-Prism-Starter.zip** herunterladen.
2. In Prism **Instanz hinzufügen → Import aus ZIP** wählen und diese ZIP importieren.
3. Java 25 verwenden, dein Microsoft-Konto im Launcher selbst anmelden und starten. Der Pre-Launch-Updater lädt das vollständige Pack automatisch und prüft die Dateien.

Der neue Starter wird getrennt importiert. Es erfolgt kein automatischer Serverbeitritt. **Forever Ever Server** ist bereits in der Serverliste eingetragen.

## Packwiz-URL

[https://raw.githubusercontent.com/Jogurella/everness-vulkan-packwiz/main/pack.toml](https://raw.githubusercontent.com/Jogurella/everness-vulkan-packwiz/main/pack.toml)

Für eigene Launcher und bestehende packwiz-Installationen:

```text
"$INST_JAVA" -jar packwiz-installer-bootstrap.jar -g -s client https://raw.githubusercontent.com/Jogurella/everness-vulkan-packwiz/main/pack.toml
```

Die offizielle Bootstrap-JAR liegt im Minecraft-Ordner des Prism-Starters. Wer bereits die frühere Starter-Version verwendet, erhält die zusätzlichen Archive beim nächsten packwiz-Update über dieselbe Pack-URL.

## Enthalten

64 Mod-JARs, 29 Ressourcenpacks und 4 Shaderarchive. Die bisherigen Tastenbelegungen, Mod-Einstellungen, HUDs, Shaderprofile und die Ressourcenpack-Reihenfolge sind übernommen. Quellen und exakte SHA-512-Hashes stehen in den `*.pw.toml`-Dateien; [Mod-/Packliste](MODLIST.md) und [Pack-Status](PACK-STATUS.md) erläutern den Stand.

Accountdateien, Cookies, Spielstände und persönliche Karten-/LOD-Caches werden nicht verteilt. Der Export verändert die bestehenden Spielprofile nicht.

## Wartung

Mit [packwiz](https://packwiz.infra.link/) bearbeiten. Nach beabsichtigten Änderungen `packwiz refresh` und `python tools/validate.py` ausführen. Downloads bleiben gepinnt, damit Versionsupdates die Renderer und persönlichen Fixes nicht stillschweigend verändern. Updates gezielt testen.

Die Archivstände enthalten zum Teil lokale Änderungen. Es sind keine offiziellen Autoren-Builds; vorhandene Credits und Drittanbieter-Lizenzen bleiben erhalten. Siehe [THIRD-PARTY.md](THIRD-PARTY.md).
