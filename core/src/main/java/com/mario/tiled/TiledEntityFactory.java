package com.mario.tiled;

import com.badlogic.gdx.Gdx;
import com.mario.model.SpawnPoint;
import com.mario.model.entities.Coin;
import com.mario.model.entities.Entity;
import com.mario.model.entities.Player;
import com.mario.model.entities.SlimeGreen;

import java.util.HashMap;
import java.util.Map;

/**
 * Fabrique les entités à partir du "type" écrit dans Tiled.
 *
 * Au lieu d'un grand switch, on garde un registre : type (String) -> créateur.
 * Ajouter un nouveau type d'entité = écrire sa classe + une ligne register(...).
 * Placer une entité déjà connue dans une carte ne demande aucun changement de code.
 */
public class TiledEntityFactory {

    /** Sait créer une entité à partir d'un point d'apparition. */
    @FunctionalInterface
    public interface EntityCreator {
        Entity create(SpawnPoint spawn);
    }

    private final Map<String, EntityCreator> creators = new HashMap<>();

    public TiledEntityFactory() {
        registerDefaults();
    }

    public void register(String type, EntityCreator creator) {
        creators.put(type, creator);
    }

    /** Crée l'entité correspondante, ou renvoie null (avec un message) si le type est inconnu. */
    public Entity create(SpawnPoint spawn) {
        EntityCreator creator = creators.get(spawn.getType());
        if (creator == null) {
            Gdx.app.error("TiledEntityFactory", "Type d'entité inconnu : '" + spawn.getType() + "'");
            return null;
        }
        return creator.create(spawn);
    }

    private void registerDefaults() {
        register(Player.TYPE, s -> new Player(s.getX(), s.getY()));
        register(Coin.TYPE, s -> new Coin(s.getX(), s.getY()));
        register(SlimeGreen.TYPE, s -> new SlimeGreen(
            s.getX(), s.getY(), s.getFloat("speed", SlimeGreen.DEFAULT_SPEED)));
    }
}
