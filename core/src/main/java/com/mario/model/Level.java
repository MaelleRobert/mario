package com.mario.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Données logiques d'un niveau : dimensions et points d'apparition des entités.
 * Aucune dépendance au dessin.
 */
public class Level {

    private final int widthInTiles;
    private final int heightInTiles;
    private final int tileSize;
    private final List<SpawnPoint> spawnPoints;

    public Level(int widthInTiles, int heightInTiles, int tileSize, List<SpawnPoint> spawnPoints) {
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.tileSize = tileSize;
        this.spawnPoints = new ArrayList<>(spawnPoints);
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

    public List<SpawnPoint> getSpawnPoints() {
        return Collections.unmodifiableList(spawnPoints);
    }

    /** Premier point d'apparition du type demandé, ou null s'il n'y en a pas. */
    public SpawnPoint findFirst(String type) {
        for (SpawnPoint sp : spawnPoints) {
            if (sp.getType().equals(type)) {
                return sp;
            }
        }
        return null;
    }
}
