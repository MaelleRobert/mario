package com.mario.model;

import com.mario.model.entities.Entity;
import com.mario.model.entities.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Données logiques d'un niveau : dimensions et entités. Aucune dépendance au dessin.
 */
public class Level {

    private final int widthInTiles;
    private final int heightInTiles;
    private final int tileSize;
    private final List<Entity> entities;

    public Level(int widthInTiles, int heightInTiles, int tileSize, List<Entity> entities) {
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.tileSize = tileSize;
        this.entities = new ArrayList<>(entities);
    }

    public void update(float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }
    }

    public float getWidthInPixels() {
        return widthInTiles * tileSize;
    }

    public float getHeightInPixels() {
        return heightInTiles * tileSize;
    }

    public int getTileSize() {
        return tileSize;
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /** Le joueur du niveau, ou null si la carte n'en contient pas. */
    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }
}
