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
