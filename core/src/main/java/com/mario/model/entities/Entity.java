package com.mario.model.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Tout ce qui existe dans un niveau (joueur, pièce, ennemi...).
 *
 * Convention : la position est celle du CENTRE de l'entité, en pixels du monde
 * (axe Y vers le haut). C'est aussi ce que représente le point posé dans Tiled.
 */
public abstract class Entity {

    private final String type;
    protected final Vector2 position;
    private final float width;
    private final float height;

    protected Entity(String type, float x, float y, float width, float height) {
        this.type = type;
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    /** Met à jour l'état de l'entité (déplacement, IA...). */
    public abstract void update(float delta);

    /** Identifiant du type, identique à celui écrit dans Tiled ("player", "coin"...). */
    public String getType() {
        return type;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
