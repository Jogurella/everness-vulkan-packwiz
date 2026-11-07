# Prüfstand — 2026.11.07.4

**Windows, RTX 4090, Minecraft 26.3, Fabric Loader 0.20.1, Java 25.** Nativer Minecraft-Vulkan-Renderer; kein VulkanMod und kein Distant Horizons im aktiven Pack.

## Inhalt und Herkunft

- 99 gepinnte Downloads: 66 Mod-JARs, 29 Ressourcenpacks, vier Shaderarchive.
- Kern: Sodium 0.9.2+mc26.3, Vitrail 0.13.0-beta, Voxy 0.2.20-beta und VIRIS 0.3.6. Fabric API bleibt 0.163.0+26.3 wie im vollständigen Profil.
- Gegenüber 2026.11.07.3 sind 97 Archive identisch; die bestehende eigene Inventarsteuerung wurde ergänzt und die Tobias-Pixel-Profilsyntax berichtigt.
- Die vorhandene Vitrail-JAR mit der lokalen Änderung in `DistantProgram.class` bleibt erhalten; die eigentliche VIRIS-Anbindung stammt unverändert aus dem MIT-Projekt von laurensanp.
- Der offizielle VIRIS-Download enthält dieselben Klassen wie der lokale Build. Unterschiede betreffen die Build-Metadaten (Loom 1.19.3/1.19.4) und JSON-Zeilenenden, nicht die Mixin-Konfiguration.
- 134 gemeinsame Einstellungsdateien mit SHA-256-Manifest. Keine Konten, Cookies, Welten, persönlichen Karten-/LOD-Caches oder Diagnoseaufnahmen.

## Prüfung des AutoSwitch-/Profil-Updates

- AutoSwitch 17.0.0, ClientSort 3.104.1, MaLiLib 0.30.2, Mouse Tweaks und Better Shulker Boxes entsprechen bytegenau den Archiven des bisherigen funktionierenden SMP-Profils.
- Alle sieben harten Zusatzmod-Abhängigkeiten mit Fabric Loaders eigener Versionsprüfung bestätigt. Keine direkten OpenGL- oder Lunar-Runtime-Hooks in der Zusatzmod.
- 16 Profilwechsel einschließlich Dateischreiben, erneutem Einlesen und Rückkehr aus Custom mit unverändertem Vitrail 0.13.0-beta geprüft.
- Der neue gemeinsame Spielstand wurde noch nicht erneut im Spiel geprüft; die früheren Rückmeldungen unten gelten für den Stand vor dieser Ergänzung.

## Bisher tatsächlich geprüft

- VIRIS-Quellcode mit JDK 25.0.5+8 gebaut; keine Voxy-, Sodium- oder Vitrail-Klassen/JARs eingebettet.
- In der getrennten Basistestinstanz auf der RTX 4090 nativen Vulkan-Backend, OpenGL-4.6-Interop und die Aufrufe der Voxy-Shaderpipeline beobachtet.
- Der Nutzer hat zuerst die Basistestinstanz und danach das vollständige Pack ohne DH als funktionierend zurückgemeldet. Ein Server wurde durch die Automatisierung nicht betreten.
- Datei-/Installer- und Updateprüfungen dieses Exports werden in `docs/INSTALLER-CHECK.json` festgehalten.

## Offen und bekannte Grenzen

Keine vollständig automatisierte Matrix aller drei Shader, Wasser-/Nebelszenen, Shaderwechsel, Reloads, erneuten Weltbeitritte oder Langzeitläufe. Keine garantierte FPS-Zahl und kein kontrollierter DH/Voxy-Leistungsvergleich.

VIRIS 0.3.6 ist experimentell und Windows-spezifisch. Laut [Upstream](https://github.com/laurensanp/viris#known-issues) fehlen Schatten auf LODs; Wasser oberhalb des Meeresspiegels kann Stufen zeigen, flaches Wasser kann Linien behalten, und Complementary-Wolken oberhalb der Kamera können Übergänge beeinflussen. Diese Grenzen werden durch das Beispielbild nicht als behoben dargestellt.
