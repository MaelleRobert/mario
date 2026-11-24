package com.mario;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.Game;
import com.mario.view.GameScreen;

/**
 * Point d'entrée du jeu : se contente d'afficher le premier écran.
 * Toute la logique vit dans les packages model / view / controller.
 */
public class MarioGame extends Game {

    private static final String FIRST_LEVEL = "maps/niveau1.tmx";

    @Override
    public void create() {
        setScreen(new GameScreen(FIRST_LEVEL));
    }

    @Override
    public void dispose() {
        // Game.setScreen() ne libère jamais l'écran à notre place.
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}
