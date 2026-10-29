# Walkdemo – libGDX Technology-Demo

Eine kleine interaktive Demo mit einer animierten Figur. Sie läuft auf Android und zum Testen auch auf dem Desktop.

## Was kann die App?

- Die Figur läuft ohne Eingabe zwischen den Bildschirmrändern hin und her.
- Ein Tipp oder Mausklick setzt ein Ziel; die Figur läuft dorthin.
- Am Ziel steht sie kurz mit einer eigenen Animation und patrouilliert danach weiter.
- Himmel, Wolken, Hügel und Boden werden direkt gezeichnet.
- Die Darstellung passt sich verschiedenen Bildschirmgrößen an.
- Oben werden FPS und der aktuelle Bewegungszustand angezeigt.

Nicht enthalten sind bisher Menüs, Punkte, Level, Gegner, Kollisionen, Sound und Speichern.

## Bedienung

Auf dem Handy tippen, am Desktop klicken: Die Figur läuft zur gewählten Stelle.

## Projektstruktur

| Ort | Aufgabe |
| --- | --- |
| `core/src/main/java/de/walkdemo/Main.java` | Hauptablauf: Eingabe, Landschaft und Anzeige |
| `core/src/main/java/de/walkdemo/Walker.java` | Figur: Bewegung, Ziel, Richtung und Animation |
| `assets/walker.png` | Sprite-Sheet mit den Einzelbildern der Figur |
| `lwjgl3/` | Start und Build der Desktop-Version |
| `android/` | Start und Build der Android-Version |
| `assets/` | Bilder und weitere Ressourcen |

`core` enthält den gemeinsamen App-Code. Die Ordner `lwjgl3` und `android` starten genau diesen Code auf der jeweiligen Plattform.

## Starten

Im Projektordner:

```powershell
./gradlew.bat lwjgl3:run
```

| Befehl | Ergebnis |
| --- | --- |
| `./gradlew.bat lwjgl3:jar` | Erstellt eine ausführbare Desktop-JAR |
| `./gradlew.bat build` | Baut alle konfigurierten Projektteile |
| `./gradlew.bat android:build` | Baut die Android-App; Android SDK erforderlich |

## Nächste Schritte

Mehr Figuren, Hindernisse und Kollisionen, Menü und Punktestand, Sounds oder Musik sowie zusätzliche Landschaften und Level.
