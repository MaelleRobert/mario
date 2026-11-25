package com.mario.model.entities;

/** Le joueur. Pour l'instant immobile : déplacements et physique viendront ensuite. */
public class Player extends Entity {

    public static final String TYPE = "player";

    public Player(float x, float y) {
        super(TYPE, x, y, 14f, 16f);
    }

    @Override
    public void update(float delta) {
        // à compléter (étape suivante : déplacements)
    }
}
