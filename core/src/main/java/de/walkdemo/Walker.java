package de.walkdemo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * Die Spielfigur: Laufanimation, Idle-Animation und Bewegung.
 *
 * Verhalten:
 *  - Ohne Eingabe läuft die Figur hin und her und dreht an den Bildschirmrändern um.
 *  - Nach einem Tipp läuft sie zur angetippten Stelle, bleibt kurz stehen
 *    (Idle-Animation) und läuft dann wieder hin und her.
 */
public class Walker {

    private enum State { PATROL, GO_TO_TARGET, IDLE }

    // Sprite-Sheet: 128x128 px pro Frame, Zeile 0 = 8 Lauf-Frames, Zeile 1 = 4 Idle-Frames
    private static final int FRAME_PX = 128;
    private static final int WALK_FRAMES = 8;
    private static final int IDLE_FRAMES = 4;
    /** Weg (in Sprite-Pixeln), den der Standfuß pro Laufzyklus zurücklegt – verhindert "rutschende" Füße. */
    private static final float STRIDE_PX = 57.3f;
    /** Abstand der Fußsohlen zur Frame-Unterkante (Platz für den Schatten). */
    private static final float FEET_OFFSET_PX = 8f;

    private static final float SIZE = 96f;         // Darstellungsgröße in Welteinheiten
    private static final float SPEED = 70f;        // Welteinheiten pro Sekunde
    private static final float IDLE_TIME = 2f;     // Sekunden Pause nach Erreichen des Ziels
    private static final float MARGIN = SIZE * 0.3f;

    private final Animation<TextureRegion> walk;
    private final Animation<TextureRegion> idle;

    private State state = State.PATROL;
    private float x;                 // Mittelpunkt der Figur (Welt-x)
    private final float groundY;     // Höhe des Bodens (Welt-y)
    private boolean facingRight = true;
    private float targetX;
    private float idleTimer;
    private float stateTime;

    public Walker(Texture sheet, float startX, float groundY) {
        this.x = startX;
        this.groundY = groundY;

        TextureRegion[][] grid = TextureRegion.split(sheet, FRAME_PX, FRAME_PX);

        TextureRegion[] walkFrames = new TextureRegion[WALK_FRAMES];
        System.arraycopy(grid[0], 0, walkFrames, 0, WALK_FRAMES);
        TextureRegion[] idleFrames = new TextureRegion[IDLE_FRAMES];
        System.arraycopy(grid[1], 0, idleFrames, 0, IDLE_FRAMES);

        // Animationstempo an die Laufgeschwindigkeit koppeln:
        // ein Zyklus (8 Frames) dauert so lange, wie die Figur für eine Schrittlänge braucht.
        float strideWorld = STRIDE_PX * (SIZE / FRAME_PX);
        float walkFrameDuration = strideWorld / SPEED / WALK_FRAMES;

        walk = new Animation<>(walkFrameDuration, walkFrames);
        walk.setPlayMode(Animation.PlayMode.LOOP);
        idle = new Animation<>(0.35f, idleFrames);
        idle.setPlayMode(Animation.PlayMode.LOOP);
    }

    /** Ziel setzen (z. B. nach einem Tipp auf den Bildschirm). */
    public void walkTo(float worldX, float worldWidth) {
        targetX = MathUtils.clamp(worldX, MARGIN, worldWidth - MARGIN);
        setState(State.GO_TO_TARGET);
    }

    public void update(float delta, float worldWidth) {
        stateTime += delta;
        float minX = MARGIN;
        float maxX = worldWidth - MARGIN;

        switch (state) {
            case PATROL:
                x += (facingRight ? SPEED : -SPEED) * delta;
                if (x >= maxX) { x = maxX; facingRight = false; }
                if (x <= minX) { x = minX; facingRight = true; }
                break;

            case GO_TO_TARGET:
                float dx = targetX - x;
                float step = SPEED * delta;
                if (Math.abs(dx) <= step) {
                    x = targetX;
                    idleTimer = IDLE_TIME;
                    setState(State.IDLE);
                } else {
                    facingRight = dx > 0;
                    x += Math.signum(dx) * step;
                }
                break;

            case IDLE:
                idleTimer -= delta;
                if (idleTimer <= 0) setState(State.PATROL);
                break;
        }
        // Falls das Handy gedreht oder das Fenster verkleinert wurde
        x = MathUtils.clamp(x, minX, Math.max(minX, maxX));
    }

    public void draw(SpriteBatch batch) {
        Animation<TextureRegion> anim = (state == State.IDLE) ? idle : walk;
        TextureRegion frame = anim.getKeyFrame(stateTime);

        float drawX = x - SIZE / 2f;
        float drawY = groundY - FEET_OFFSET_PX * (SIZE / FRAME_PX);

        // Der Sprite schaut nach rechts; für links mit negativer Breite gespiegelt zeichnen
        if (facingRight) {
            batch.draw(frame, drawX, drawY, SIZE, SIZE);
        } else {
            batch.draw(frame, drawX + SIZE, drawY, -SIZE, SIZE);
        }
    }

    private void setState(State newState) {
        boolean wasIdle = state == State.IDLE;
        boolean isIdle = newState == State.IDLE;
        if (wasIdle != isIdle) {
            stateTime = 0f; // Animation beim Wechsel Laufen <-> Stehen von vorne starten
        }
        state = newState;
    }

    public String stateName() {
        return state.name();
    }
}
