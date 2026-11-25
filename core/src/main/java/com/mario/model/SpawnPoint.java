package com.mario.model;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * Décrit un objet posé dans Tiled : son type, sa position en pixels (axe Y vers le haut)
 * et ses propriétés personnalisées ("speed", ...).
 * Le modèle ne connaît rien de Tiled : il ne voit que ces données.
 */
public class SpawnPoint {

    private final String type;
    private final Vector2 position;
    private final Map<String, Object> properties;

    public SpawnPoint(String type, float x, float y, Map<String, Object> properties) {
        this.type = type;
        this.position = new Vector2(x, y);
        this.properties = new HashMap<>(properties);
    }

    public String getType() {
        return type;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    /** Propriété numérique personnalisée, ou la valeur par défaut si elle est absente. */
    public float getFloat(String key, float defaultValue) {
        Object value = properties.get(key);
        return (value instanceof Number) ? ((Number) value).floatValue() : defaultValue;
    }
}
