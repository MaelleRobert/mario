package com.mario.model.entities;

/** Une pièce à ramasser. */
public class Coin extends Entity {

    public static final String TYPE = "coin";

    public Coin(float x, float y) {
        super(TYPE, x, y, 16f, 16f);
    }

    @Override
    public void update(float delta) {
        // Une pièce ne bouge pas.
    }
}
