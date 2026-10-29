package de.walkdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Technologiedemonstration libGDX: Eine Figur läuft animiert über den Bildschirm.
 * Läuft auf Android (und zum Testen auch auf dem Desktop).
 *
 * Antippen = Figur läuft zur angetippten Stelle.
 */
public class Main extends ApplicationAdapter {

    // Mindestgröße der Spielwelt in Welteinheiten. Der ExtendViewport erweitert sie
    // passend zum Seitenverhältnis des Handys – so gibt es keine schwarzen Balken
    // und nichts wird verzerrt, egal welches Gerät.
    private static final float MIN_WORLD_W = 480f;
    private static final float MIN_WORLD_H = 270f;
    private static final float GROUND_Y = 60f;

    private static final Color SKY_TOP = Color.valueOf("8EC9F2");
    private static final Color SKY_BOTTOM = Color.valueOf("DDF1FF");
    private static final Color HILL = Color.valueOf("A9D8A0");
    private static final Color GRASS = Color.valueOf("6DBF6A");
    private static final Color EARTH = Color.valueOf("7A5A44");
    private static final Color CLOUD = new Color(1f, 1f, 1f, 0.9f);

    private ExtendViewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private Texture sheet;
    private Walker walker;

    private final Vector2 touch = new Vector2();
    private float cloudOffset;

    @Override
    public void create() {
        viewport = new ExtendViewport(MIN_WORLD_W, MIN_WORLD_H);
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();

        font = new BitmapFont();                 // in libGDX eingebaute Standardschrift
        font.setUseIntegerPositions(false);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.valueOf("1F2430"));

        sheet = new Texture(Gdx.files.internal("walker.png"));
        sheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        walker = new Walker(sheet, MIN_WORLD_W / 2f, GROUND_Y);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Bildschirmkoordinaten (Pixel, y nach unten) -> Weltkoordinaten
                viewport.unproject(touch.set(screenX, screenY));
                walker.walkTo(touch.x, viewport.getWorldWidth());
                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        // Wird beim Start und bei jeder Größen-/Orientierungsänderung aufgerufen
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        // Delta begrenzen, damit nach einer Pause (z. B. App im Hintergrund) nichts "springt"
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        float worldW = viewport.getWorldWidth();
        float worldH = viewport.getWorldHeight();

        walker.update(delta, worldW);
        cloudOffset = (cloudOffset + 8f * delta) % (worldW + 200f);

        ScreenUtils.clear(SKY_BOTTOM);
        viewport.apply();

        drawBackground(worldW, worldH);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        walker.draw(batch);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond()
                + "   Zustand: " + walker.stateName()
                + "   (Tippen = Ziel setzen)", 10f, worldH - 10f);
        batch.end();
    }

    private void drawBackground(float w, float h) {
        shapes.setProjectionMatrix(viewport.getCamera().combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Himmel als Farbverlauf (Farben: unten links, unten rechts, oben rechts, oben links)
        shapes.rect(0, GROUND_Y, w, h - GROUND_Y, SKY_BOTTOM, SKY_BOTTOM, SKY_TOP, SKY_TOP);

        // Wolken, die langsam nach rechts ziehen
        shapes.setColor(CLOUD);
        for (int i = 0; i < 3; i++) {
            float cx = (cloudOffset + i * (w + 200f) / 3f) % (w + 200f) - 100f;
            float cy = h - 60f - i * 25f;
            shapes.circle(cx, cy, 18f, 32);
            shapes.circle(cx + 20f, cy + 6f, 22f, 32);
            shapes.circle(cx + 42f, cy, 16f, 32);
        }

        // Hügel im Hintergrund
        shapes.setColor(HILL);
        for (float hx = 0; hx < w + 120f; hx += 160f) {
            shapes.circle(hx, GROUND_Y - 30f, 90f, 48);
        }

        // Boden
        shapes.setColor(EARTH);
        shapes.rect(0, 0, w, GROUND_Y);
        shapes.setColor(GRASS);
        shapes.rect(0, GROUND_Y - 8f, w, 8f);

        shapes.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        font.dispose();
        sheet.dispose();
    }
}
