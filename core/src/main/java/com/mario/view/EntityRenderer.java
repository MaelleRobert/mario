package com.mario.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mario.model.entities.Coin;
import com.mario.model.entities.Entity;
import com.mario.model.entities.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dessine les entités. L'apparence est décidée ici (vue), jamais dans Tiled :
 * un registre associe chaque type d'entité à une animation.
 * Un type sans animation enregistrée est simplement ignoré.
 */
public class EntityRenderer implements Disposable {

    private final SpriteBatch batch = new SpriteBatch();
    private final List<Texture> textures = new ArrayList<>();
    private final Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private float stateTime = 0f;

    public EntityRenderer() {
        // type, fichier, largeur/hauteur d'une image, ligne de la feuille, nb d'images, durée d'une image
        registerAnimation(Player.TYPE, "sprites/knight.png", 32, 32, 0, 4, 0.2f); // animation "idle"
        registerAnimation(Coin.TYPE, "sprites/coin.png", 16, 16, 0, 12, 0.08f);
    }

    private void registerAnimation(String type, String file, int frameWidth, int frameHeight,
                                   int row, int frameCount, float frameDuration) {
        Texture texture = new Texture(Gdx.files.internal(file));
        textures.add(texture);
        TextureRegion[][] grid = TextureRegion.split(texture, frameWidth, frameHeight);
        TextureRegion[] frames = Arrays.copyOfRange(grid[row], 0, frameCount);
        animations.put(type, new Animation<>(frameDuration, frames));
    }

    public void render(OrthographicCamera camera, List<Entity> entities, float delta) {
        stateTime += delta;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity entity : entities) {
            Animation<TextureRegion> animation = animations.get(entity.getType());
            if (animation == null) {
                continue; // aucun rendu défini pour ce type
            }
            TextureRegion frame = animation.getKeyFrame(stateTime, true);
            // La position de l'entité est son centre : on centre l'image dessus.
            float x = entity.getX() - frame.getRegionWidth() / 2f;
            float y = entity.getY() - frame.getRegionHeight() / 2f;
            batch.draw(frame, x, y);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
