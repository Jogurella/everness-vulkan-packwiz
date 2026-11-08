# Everness Vulkan – 2026.11.08.1

Hand-/HUD-Tiefenpuffer bei reduzierter Vitrail-Renderauflösung korrigiert.

![Everness-Update 2026.11.08.1](.github/assets/update-2026.11.08.1-depthfix.png)

[Änderungsnotizen](CHANGELOG.md) · [Tatsächlicher Prüfstand und Grenzen](PACK-STATUS.md) · [Einstellungen übernehmen](SETTINGS.md)

## Installieren

- [Prism-Starter herunterladen](downloads/Everness-vulkan-Prism-Starter.zip), als Instanz in Prism importieren und Java 25 verwenden.
- Bei bestehender Installation das Pack aktualisieren. Für den vollständigen Einstellungsabgleich zusätzlich das [Einstellungspaket](downloads/Everness-vulkan-Einstellungen.zip) einmal mit Sicherung anwenden; [Anleitung](SETTINGS.md).
- Das aktuelle Pack enthält **66 Mods, 29 Ressourcenpacks und 4 Shaderarchive**. [Alle Downloads und Quellen](MODLIST.md).

## Gemeinsame Bedienung

HUD, Jade, Minimap, FOV, Vollbild, Ressourcenpack-Reihenfolge, Punchy-Profil und QoL-Optionen stammen aus derselben Vulkan-Vorlage. **Alt+B** schaltet AutoSwitch um; Tastenkombination und Item-Ausschlüsse sind im Modmenü einstellbar. **Ziffernblock \*** schaltet den Shader, **Ziffernblock +** lädt ihn neu, **O** öffnet die Shaderauswahl. Die Bezeichnungen der Shader-Funktionen werden jeweils an Iris bzw. Vitrail angepasst.

## Grafikkomponenten

Minecrafts eingebauter Vulkan-Renderer, Vitrail 0.13.0-beta mit Everness-Tiefenpufferkorrektur, VIRIS 0.3.6 und Voxy 0.2.20-beta. Voxy bleibt eine separat heruntergeladene offizielle Abhängigkeit. Die Vitrail-Änderung und vollständige Quellen stehen unter [patches/vitrail-depthfix](patches/vitrail-depthfix).

100 % Render-Skalierung ist der veröffentlichte Standard. Ein erfolgreicher Offline-Lauf belegt keine allgemeine Absturzfreiheit. **Zusätzliche native Startabbrüche wurden beobachtet und bleiben offen**; Details und einzelne Ergebnisse stehen im [Prüfbericht](PACK-STATUS.md). Es wurde kein Multiplayer-Server betreten.

Persönliche Konten, Welten, Karten-/LOD-Caches und Logdateien sind nicht enthalten. Lizenzen und Quellen: [THIRD-PARTY.md](THIRD-PARTY.md).
