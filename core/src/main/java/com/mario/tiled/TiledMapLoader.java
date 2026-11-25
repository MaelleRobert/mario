package com.mario.tiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mario.model.Level;
import com.mario.model.SpawnPoint;
import com.mario.model.entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Seule classe qui connaît le format Tiled : elle lit le .tmx et fabrique un Level.
 *
 * Convention (le "contrat" avec Tiled) :
 *  - un calque d'objets nommé "entities" ;
 *  - chaque objet a un champ Class/Type ("player", "coin", ...) ;
 *  - la position de l'objet est celle de son point ;
 *  - les propriétés personnalisées de l'objet (ex. "speed") sont transmises à la factory.
 */
public class TiledMapLoader {

    private static final String ENTITIES_LAYER = "entities";

    private final TmxMapLoader loader = new TmxMapLoader();
    private final TiledEntityFactory factory = new TiledEntityFactory();

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

        return new Level(width, height, tileSize, createEntities(map));
    }

    private List<Entity> createEntities(TiledMap map) {
        List<Entity> entities = new ArrayList<>();
        for (SpawnPoint spawn : readSpawnPoints(map)) {
            Entity entity = factory.create(spawn);
            if (entity != null) {
                entities.add(entity);
            }
        }
        return entities;
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
            result.add(new SpawnPoint(type, x, y, copyProperties(p)));
        }
        return result;
    }

    private Map<String, Object> copyProperties(MapProperties p) {
        Map<String, Object> copy = new HashMap<>();
        Iterator<String> keys = p.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            copy.put(key, p.get(key));
        }
        return copy;
    }

    /** Selon la version, LibGDX expose le champ Class de Tiled sous "type" ou "class". */
    private String readType(MapProperties p) {
        String type = p.get("type", String.class);
        return type != null ? type : p.get("class", String.class);
    }
}
