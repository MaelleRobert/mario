package com.mario.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Décrit un objet posé dans Tiled : son type ("player", "coin"...) et sa position
 * en pixels (axe Y vers le haut, comme dans LibGDX).
 * Le modèle ne connaît rien de Tiled : il ne voit que ces données.
 */
public class SpawnPoint {

    private final String type;
    private final Vector2 position;

    public SpawnPoint(String type, float x, float y) {
        this.type = type;
        this.position = new Vector2(x, y);
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
}
