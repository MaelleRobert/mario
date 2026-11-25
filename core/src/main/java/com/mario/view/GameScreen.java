package com.mario.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mario.model.Level;
import com.mario.model.entities.Entity;
import com.mario.model.entities.Player;
import com.mario.tiled.TiledMapLoader;

/**
 * Écran de jeu. Étape 2 : affiche la carte et les entités créées depuis Tiled.
 * La caméra se déplace encore aux flèches (elle suivra le joueur plus tard).
 */
public class GameScreen implements Screen {

    // Zone visible en pixels du monde (la carte fait 640 x 320 : on en voit la moitié).
    private static final float VIEW_WIDTH = 320f;
    private static final float VIEW_HEIGHT = 240f;
    private static final float CAMERA_SPEED = 150f; // pixels par seconde

    private final Level level;
    private final WorldRenderer worldRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport = new FitViewport(VIEW_WIDTH, VIEW_HEIGHT, camera);

    public GameScreen(String mapPath) {
        TiledMapLoader loader = new TiledMapLoader();
        TiledMap map = loader.loadMap(mapPath);
        this.level = loader.buildLevel(map);
        this.worldRenderer = new WorldRenderer(map);

        for (Entity e : level.getEntities()) {
            Gdx.app.log("LEVEL", e.getType() + " x=" + e.getX() + " y=" + e.getY());
        }

        // La caméra démarre sur le joueur s'il existe, sinon au centre de la carte.
        Player player = level.getPlayer();
        if (player != null) {
            camera.position.set(player.getX(), player.getY(), 0);
        } else {
            camera.position.set(level.getWidthInPixels() / 2, level.getHeightInPixels() / 2, 0);
        }
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        moveCamera(delta);
        clampCamera();
        camera.update();

        worldRenderer.render(camera, level.getEntities(), delta);
    }

    private void moveCamera(float delta) {
        float step = CAMERA_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  camera.position.x -= step;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += step;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    camera.position.y += step;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  camera.position.y -= step;
    }

    /** Empêche la caméra de montrer ce qui est hors de la carte. */
    private void clampCamera() {
        camera.position.x = clampAxis(camera.position.x, viewport.getWorldWidth(), level.getWidthInPixels());
        camera.position.y = clampAxis(camera.position.y, viewport.getWorldHeight(), level.getHeightInPixels());
    }

    private float clampAxis(float position, float viewSize, float mapSize) {
        if (mapSize <= viewSize) {
            return mapSize / 2; // la carte tient dans la vue : on la centre
        }
        return MathUtils.clamp(position, viewSize / 2, mapSize - viewSize / 2);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }

    @Override public void show() { }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }
}
