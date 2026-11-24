package com.mario.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Dessine les calques de tuiles de la carte. Possède la TiledMap (donc la libère).
 */
public class WorldRenderer implements Disposable {

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;

    public WorldRenderer(TiledMap map) {
        this.map = map;
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        map.dispose();
    }
}
