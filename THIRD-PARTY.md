# Herkunft, lokale Änderungen und Lizenzen

Die Modrinth-Einträge verwenden weiterhin die exakt erfassten Autoren-Downloads. Die bisher nicht eingebundenen Archivstände aus der bestehenden lokalen Installation werden auf ausdrücklichen Wunsch des Pack-Inhabers unverändert als versionierte Release-Dateien bereitgestellt. Die Archive behalten ihre enthaltenen Originaltexte, Credits und Lizenzdateien. Lokale Änderungen werden nicht als offizielle Autorenversionen ausgegeben.

## Bestehende lokale Mod-Änderungen

Camera Utils enthält den Superzoom; My Totem Doll die vorhandenen Render-/GUI-Korrekturen; Punchy die vorhandene Kreativflug-Korrektur; das OpenGL-Voxy die bisherige Kompatibilitätsfassung. Die bereits veröffentlichten HUD-, DH- und Vitrail-Änderungsquellen stehen unter `patches/`. Die Kartenmod ist der vorhandene Forever-Everness-Client; die Vulkan-Fassung enthält dessen PBO-Kompatibilitätskorrektur.

## Drittanbieterbedingungen bleiben bestehen

Dieses Repository erteilt keine zusätzlichen Rechte an fremden Mods, Ressourcenpacks oder Shadern. Einige Komponenten führen weitergehende Beschränkungen in ihren eigenen Lizenzen auf. Die MIT-Lizenz im Stamm gilt ausschließlich für die neu erstellten Manifest-, Dokumentations- und Wartungsdateien, nicht für fremde Inhalte oder die unter GPL/LGPL stehenden Änderungen.

Beispiele: EvergreenHUD GPL-3.0; Distant Horizons und Vitrail LGPL-3.0; My Totem Doll CC BY-ND 4.0 laut beiliegender Lizenz; Punchy mit eigener Custom License; Camera Utils und Voxy mit den jeweiligen Rechtevorbehalten; SEUS mit seiner beiliegenden SEUS-Lizenz. Die Lizenzhinweise werden durch diese Zusammenstellung nicht ersetzt.

Upstream-Quellen der schon dokumentierten Änderungen: https://github.com/Polyfrost/EvergreenHUD, https://gitlab.com/distant-horizons-team/distant-horizons/ und https://github.com/avpbynf/Vitrail-Shaders.

## VIRIS/Voxy-Update 2026.11.07.3

VIRIS 0.3.6 ist von laurensanp unter MIT: https://github.com/laurensanp/viris. Der offizielle Autoren-Build wird direkt verlinkt; die Renderer-Anbindung wurde hier nicht selbst entwickelt. Lizenzkopie: `licenses/VIRIS-MIT.txt`.

Voxy 0.2.20-beta ist von MCRcortex und All Rights Reserved. Es wird separat über den offiziellen Modrinth-Download installiert, nicht in VIRIS eingebettet und nicht erneut hier hochgeladen. Vitrail (LGPL-3.0) bleibt als getrennte vorhandene Komponente erhalten. Historische DH-Patches in diesem Repository sind keine aktive Installation von DH.

## AutoSwitch-Erweiterung 2026.11.07.4

`atlas-inventory-controls-0.1.6+mc26.3.jar` ist die bisher im eigenen SMP-Profil verwendete Client-Erweiterung (Metadaten: MIT), hier bytegenau übernommen. AutoSwitch, ClientSort, MaLiLib und Better Shulker Boxes bleiben eigenständige Komponenten. Tobias-Pixel bleibt das vorhandene angepasste Shaderpack; verändert wurden nur die An/Aus-Tokens seiner vier Profildefinitionen, keine Shaderquellen.

## 2026.11.08.1: Vitrail-Tiefenpuffer

Vitrail bleibt LGPL-3.0-only, Originalprojekt avpbynf/Vitrail-Shaders. Die eigene Änderung und die vollständigen gepinnten Quellen liegen unter [patches/vitrail-depthfix](patches/vitrail-depthfix). Voxy bleibt separat über die offizielle Quelle eingebunden.

## 2026.11.08.2: Weather Glass und Cozy Companion

Weather Glass 0.6.0 ist von DuffoPuffo, PolyForm Shield License 1.0.0. Die unveränderte JAR wird direkt vom offiziellen CurseForge-CDN geladen; [Projekt und Autor](https://www.curseforge.com/minecraft/mc-mods/weather-glass), [Lizenzkopie](licenses/Weather-Glass-PolyForm-Shield-1.0.0.txt).

Cozy Companion r3 basiert auf [Complementary Unbound r5.9.3 von Complementary Development](https://www.complementary.dev/), Complementary License Agreement 1.7. Es wird keine vollständige geänderte Shader-ZIP öffentlich rehostet. Das offizielle Original wird unverändert über Modrinth geladen. Die eigene MIT-Start-Hilfe und die beiden Settings-Rezepte liegen unter [patches/cozy-companion-setup](patches/cozy-companion-setup); sie rekonstruieren die lokale Nutzerfassung und erhalten alle Original-Lizenztexte. Die Helper-MIT-Lizenz erteilt keine zusätzlichen Rechte an Complementary.
