package com.mario.model.entities;

/**
 * Un slime vert. Absent du niveau 1, mais déjà reconnu par la factory :
 * il suffira d'en poser un dans Tiled (type "slime_green") pour qu'il apparaisse.
 */
public class SlimeGreen extends Entity {

    public static final String TYPE = "slime_green";
    public static final float DEFAULT_SPEED = 30f;

    private final float speed; // configurable depuis Tiled via la propriété "speed"

    public SlimeGreen(float x, float y, float speed) {
        super(TYPE, x, y, 16f, 12f);
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void update(float delta) {
        // à compléter (patrouille)
    }
}
