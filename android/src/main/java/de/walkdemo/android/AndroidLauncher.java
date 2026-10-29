package de.walkdemo.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import de.walkdemo.Main;

/** Startpunkt auf Android – startet die plattformunabhängige Main-Klasse aus dem core-Modul. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;   // Vollbild ohne Status- und Navigationsleiste
        config.useAccelerometer = false;  // nicht benötigt -> spart Akku
        config.useCompass = false;
        initialize(new Main(), config);
    }
}
