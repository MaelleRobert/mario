package com.mario.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.mario.model.entities.Entity;

import java.util.List;

/**
 * Dessine le monde : d'abord les calques de tuiles, puis les entités par-dessus.
 * Possède la TiledMap (donc la libère).
 */
public class WorldRenderer implements Disposable {

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final EntityRenderer entityRenderer = new EntityRenderer();

    public WorldRenderer(TiledMap map) {
        this.map = map;
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render(OrthographicCamera camera, List<Entity> entities, float delta) {
        mapRenderer.setView(camera);
        mapRenderer.render();
        entityRenderer.render(camera, entities, delta);
    }

    @Override
    public void dispose() {
        entityRenderer.dispose();
        mapRenderer.dispose();
        map.dispose();
    }
}
