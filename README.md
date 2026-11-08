# Everness Vulkan – 2026.11.08.3

Zwei Shader mit deinen Einstellungen: **Pixelated** und **Complementary Unbound r5.9.3**.

![Everness-Update 2026.11.08.3](.github/assets/update-2026.11.08.3-siris-shaders-vulkan.png)

**SIRIS 1.1.6** ersetzt Purescale. Vorgabe: **Custom 100 %**, DLAA-NGX-Modus, Modell M, **2× Frame Generation** auf RTX 40/50; keine reduzierte interne Auflösung angefordert. Die SIRIS-Option `disablePackTaa=true` schaltet das doppelte Shader-TAA ab.

[Änderungsnotizen](CHANGELOG.md) · [Prüfbericht](docs/SIRIS-SHADER-ABNAHME.md) · [Einstellungen](SETTINGS.md) · [Downloadliste](MODLIST.md)

## Aktualisieren

Im Everness Launcher das Vulkan-Pack aktualisieren und Minecraft neu starten. Alte Shader werden beim ersten Start außerhalb der aktiven Liste gesichert. Die beiden mitgelieferten Einstellungsdateien werden einmal übernommen; spätere eigene Änderungen bleiben erhalten. Wenn bisher ein entfernter Shader ausgewählt war, wird Pixelated gewählt. Ein bereits gewähltes Unbound bleibt aktiv.

**Minecraft 26.3 · Fabric Loader 0.20.1 · Java 25.** Das Pack enthält **68 Mods und 29 Ressourcenpacks**. Ein offizielles Shaderarchiv wird heruntergeladen und ein zweites lokal gepatcht; danach sind genau zwei Shader verfügbar. Keine Welten, Konten oder privaten Karten-/Voxy-Daten enthalten.

## Neuinstallation mit Prism / packwiz

Die Instanz [Everness-vulkan-Prism-Starter.zip](downloads/Everness-vulkan-Prism-Starter.zip) in Prism importieren, Java 25 und dein eigenes Minecraft-Konto auswählen. Der vorkonfigurierte packwiz-Start lädt die gepinnten Dateien. Alternativ pack.toml über packwiz verwenden. Die [Einstellungs-ZIP](downloads/Everness-vulkan-Einstellungen.zip) und `tools/restore-settings.py` sichern und übertragen die veröffentlichten Vorgaben. [Prüfsummen](downloads/SHA256SUMS.txt).

## Shader und Quellen

**Complementary Unbound r5.9.3** stammt von EminGT / [Complementary Development](https://www.complementary.dev/) und wird unverändert über den offiziellen Modrinth-Download bezogen. **Pixelated** basiert darauf sowie auf [Euphoria Patches von SpacEagle17 und Mitwirkenden](https://www.euphoriapatches.com/). Für diese Modpack-Zusammenstellung und lokale Anpassungen ist Everness verantwortlich. Die ursprünglichen Lizenzdateien bleiben im lokalen Ergebnis unverändert enthalten.

Der eigene kleine [Patch-/Migrationshelfer](patches/pixelated-setup) erzeugt Pixelated nur lokal aus dem geprüften Original, sichert alte Shader und stellt die Auswahl um. Er bettet keine Voxy-Mod-JAR und keine vollständige modifizierte Shader-ZIP ein. Seine Java-Quellen stehen unter MIT; Shader-/Patchdaten unterliegen den jeweiligen Shaderlizenzen.

## Prüfstand

Offline-Ablauf mit beiden Shadern, Neuladen und erneutem Weltbeitritt regulär abgeschlossen. Detailprüfungen und offene Punkte stehen im [Prüfbericht](docs/SIRIS-SHADER-ABNAHME.md). Kein universelles Kompatibilitäts- oder Leistungsversprechen.
