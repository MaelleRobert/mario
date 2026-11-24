package com.mario.tiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mario.model.Level;
import com.mario.model.SpawnPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Seule classe qui connaît le format Tiled : elle lit le .tmx et fabrique un Level.
 *
 * Convention (le "contrat" avec Tiled) :
 *  - un calque d'objets nommé "entities" ;
 *  - chaque objet a un champ Class/Type ("player", "coin", ...) ;
 *  - la position de l'objet est celle de son point.
 */
public class TiledMapLoader {

    private static final String ENTITIES_LAYER = "entities";

    private final TmxMapLoader loader = new TmxMapLoader();

    /** Charge le fichier .tmx (l'appelant devient responsable de dispose()). */
    public TiledMap loadMap(String path) {
        return loader.load(path);
    }

    /** Extrait de la carte les informations utiles à la logique du jeu. */
    public Level buildLevel(TiledMap map) {
        MapProperties props = map.getProperties();
        int width = props.get("width", Integer.class);
        int height = props.get("height", Integer.class);
        int tileSize = props.get("tilewidth", Integer.class);

        return new Level(width, height, tileSize, readSpawnPoints(map));
    }

    private List<SpawnPoint> readSpawnPoints(TiledMap map) {
        List<SpawnPoint> result = new ArrayList<>();
        MapLayer layer = map.getLayers().get(ENTITIES_LAYER);
        if (layer == null) {
            Gdx.app.error("TiledMapLoader", "Calque '" + ENTITIES_LAYER + "' introuvable");
            return result;
        }

        for (MapObject object : layer.getObjects()) {
            MapProperties p = object.getProperties();
            String type = readType(p);
            Float x = p.get("x", Float.class);
            Float y = p.get("y", Float.class);
            if (type == null || x == null || y == null) {
                Gdx.app.error("TiledMapLoader", "Objet ignoré (type/x/y manquant) : " + object.getName());
                continue;
            }
            result.add(new SpawnPoint(type, x, y));
        }
        return result;
    }

    /** Selon la version, LibGDX expose le champ Class de Tiled sous "type" ou "class". */
    private String readType(MapProperties p) {
        String type = p.get("type", String.class);
        return type != null ? type : p.get("class", String.class);
    }
}
