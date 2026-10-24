# libGDX Technology Demonstration

Eine **Technology Demonstration** für das [libGDX](https://libgdx.com/) Framework – ein Proof of Concept, der zeigt, wie man mit libGDX arbeitet und was das Framework kann.

## Was ist libGDX?

libGDX ist ein **Cross-Platform Game Development Framework** für Java. Es ermöglicht die Entwicklung von Spielen und interaktiven Anwendungen, die auf mehreren Plattformen laufen:
- Desktop (Windows, macOS, Linux)
- Android
- iOS
- Web (HTML5/WebGL)

## Warum braucht man ein Framework?

Ohne Framework müsste man für jede Plattform separate Projekte entwickeln und sich mit Low-Level-Details beschäftigen:

### Probleme ohne Framework:
- **Plattformspezifischer Code**: Für Windows, Android und iOS müsste man jeweils andere APIs verwenden
- **Grafik-Rendering**: Direktes Arbeiten mit OpenGL/Vulkan ist komplex und fehleranfällig
- **Input-Handling**: Jede Plattform hat andere Mechanismen für Tastatur, Maus, Touch
- **Audio**: Verschiedene Audio-APIs pro Plattform
- **Dateizugriff**: Unterschiedliche Dateisysteme und Zugriffsmethoden
- **Performance-Optimierung**: Man muss sich selbst um effiziente Grafikoperationen kümmern

### Vorteile mit libGDX:
✅ **Write Once, Run Anywhere**: Ein Codebase für alle Plattformen  
✅ **Abstrahierte APIs**: Einfacher Zugriff auf Grafik, Audio, Input, Dateien  
✅ **Bewährte Tools**: Sprite-Batching, Texture-Management, Scene2D UI  
✅ **Performance**: Optimiert für 2D/3D-Spiele mit hohen FPS  
✅ **Community & Dokumentation**: Große Community, viele Tutorials und Extensions  
✅ **Open Source**: Kostenlos, gut dokumentiert, aktiv entwickelt

## Was kann man mit libGDX machen?

- **2D-Spiele**: Platformer, Puzzle-Spiele, Arcade-Spiele
- **3D-Spiele**: First-Person, Third-Person, Strategiespiele
- **UI-Anwendungen**: Interaktive Visualisierungen, Tools
- **Physics-basierte Spiele**: Integration mit Box2D (2D) oder Bullet (3D)
- **Particle-Effekte**: Feuer, Rauch, Explosionen
- **Shader-Effekte**: Benutzerdefinierte visuelle Effekte

## Projektstruktur

Dieses Projekt wurde mit [gdx-liftoff](https://github.com/libgdx/gdx-liftoff) generiert und folgt der Standard-Multi-Module-Struktur:

```
tech-demo/
├── core/          # Hauptlogik – plattformunabhängig
├── lwjgl3/        # Desktop-Launcher (Windows, macOS, Linux)
├── android/       # Android-Launcher
└── assets/        # Ressourcen (Bilder, Sounds, etc.)
```

### Module im Detail:

- **`core`**: Hier liegt der gesamte Spielcode. Dieser Code läuft auf allen Plattformen identisch.
- **`lwjgl3`**: Desktop-Plattform mit LWJGL3 (Lightweight Java Game Library)
- **`android`**: Android-Plattform – benötigt Android SDK
- **`assets`**: Alle Ressourcen (Texturen, Sounds, Fonts) werden hier abgelegt

## Das Demo-Programm verstehen

Die Hauptklasse `Main.java` zeigt die grundlegende Struktur einer libGDX-Anwendung:

```java
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;    // Zeichnet Texturen effizient
    private Texture image;         // Das libGDX-Logo

    @Override
    public void create() {
        // Initialisierung beim Start
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        // Wird 60x pro Sekunde aufgerufen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);  // Bildschirm löschen
        batch.begin();
        batch.draw(image, 140, 210);                // Logo zeichnen
        batch.end();
    }

    @Override
    public void dispose() {
        // Aufräumen beim Beenden
        batch.dispose();
        image.dispose();
    }
}
```

### Lifecycle-Methoden:
- **`create()`**: Wird einmal beim Start aufgerufen – hier initialisiert man Ressourcen
- **`render()`**: Wird kontinuierlich aufgerufen (Game Loop) – hier wird gezeichnet
- **`dispose()`**: Wird beim Beenden aufgerufen – hier gibt man Ressourcen frei

## Projekt ausführen

### Desktop-Version starten:
```bash
./gradlew lwjgl3:run
```

### Als JAR-Datei bauen:
```bash
./gradlew lwjgl3:jar
```
Die ausführbare JAR findet sich dann in `lwjgl3/build/libs/`

### Android-Version bauen:
```bash
./gradlew android:build
```

## Nützliche Gradle-Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `./gradlew lwjgl3:run` | Desktop-Version starten |
| `./gradlew lwjgl3:jar` | Ausführbare JAR erstellen |
| `./gradlew build` | Alle Plattformen bauen |
| `./gradlew clean` | Build-Ordner löschen |
| `./gradlew android:lint` | Android-Projekt validieren |

### Gradle-Flags:
- `--daemon`: Gradle-Daemon verwenden (schneller)
- `--offline`: Cached Dependencies verwenden
- `--refresh-dependencies`: Alle Dependencies neu validieren

## Weiterführende Ressourcen

- [libGDX Wiki](https://libgdx.com/wiki/) – Offizielle Dokumentation
- [libGDX Setup Guide](https://libgdx.com/wiki/start/setup) – Projekt-Setup
- [libGDX API Docs](https://libgdx.com/dev/javadoc/) – Vollständige API-Referenz
- [libGDX Discord](https://discord.gg/6pgDK9F) – Community-Support

## Proof of Concept – Nächste Schritte

Dieses Projekt zeigt die Grundlagen. Um libGDX weiter zu demonstrieren, kannst du folgende Features hinzufügen:

1. **Input-Handling**: Tastatur/Maus-Steuerung implementieren
2. **Animation**: Sprite-Sheets und animierte Charaktere
3. **UI**: Buttons und Menüs mit Scene2D
4. **Physik**: Integration von Box2D für realistische Bewegungen
5. **Audio**: Hintergrundmusik und Sound-Effekte
6. **Particle-Effekte**: Visuelle Effekte für mehr Dynamik

---

**Projektinfo**: Generiert mit gdx-liftoff | Java 17 | libGDX Framework
