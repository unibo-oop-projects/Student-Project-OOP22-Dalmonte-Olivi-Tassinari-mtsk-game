package game.minigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import api.ColorRGB;
import api.Point2D;
import api.Vector2D;
import game.gameobject.Circle;
import game.gameobject.GameObject;
import game.gameobject.catchthesqareobjects.BombSquare;

/**
 * Minigame where the player has to catch sqares before the time runs out.
 */
public class CatchTheSquare implements Minigame {
    private static final int RIGHT_BOUND = 1600;
    private static final int BOTTOM_BOUND = 900;
    private static final int CIRCLE_RADIUS = 100;
    private static final int BOMB_SIDE = (int) (CIRCLE_RADIUS * 1.5);
    private static final int BOMB_SPAWN_FREQ = 4;

    private long totalElapsed;
    private int totalSquaresSpawned;
    private final List<GameObject> gObjects;
    private final Random r;

    /**
     * Constructor for the minigame, it initializes its fields.
     */
    public CatchTheSquare() {
        this.gObjects = new ArrayList<>();
        this.totalElapsed = 0;
        this.totalSquaresSpawned = 0;
        this.r = new Random();
        gObjects.add(new Circle(Point2D.origin(), Vector2D.nullVector(), CIRCLE_RADIUS));
    }

    /**
     * The game is over if any of the sqares timers runs out.
     * 
     * @return if the minigame is over.
     */
    @Override
    public boolean isGameOver() {
        return !gObjects.stream()
                .filter(o -> o instanceof BombSquare)
                .map(obj -> (BombSquare) obj)
                .allMatch(b -> b.getTimer() >= 0);
    }

    /**
     * Changes the sta states of the objects according the elapsed time.
     * 
     * @param elapsed the elapsed time.
     */
    @Override
    public void compute(final long elapsed) {
        totalElapsed += elapsed;
        if ((int) (totalElapsed / 1000) % BOMB_SPAWN_FREQ == 0
                && totalSquaresSpawned < totalElapsed / (BOMB_SPAWN_FREQ * 1000)) {
            gObjects.add(new BombSquare(randSpawnPoint(), BOMB_SIDE, ColorRGB.black()));
            totalSquaresSpawned++;
        }
        gObjects.forEach(b -> b.updatePhysics(elapsed, this));

    }

    /**
     * A method to get a random spawn point that is at least {@value #BOMB_SIDE}*2
     * distant from other objects.
     * 
     * @return the new spawn point.
     */
    private Point2D randSpawnPoint() {
        final Point2D p = new Point2D(r.nextInt(BOMB_SIDE / 2, RIGHT_BOUND - BOMB_SIDE / 2),
                r.nextInt(BOMB_SIDE / 2, BOTTOM_BOUND - BOMB_SIDE / 2));
        for (final GameObject gameObject : gObjects) {
            if (p.distance(gameObject.getCoor()) < BOMB_SIDE * 2) {
                return randSpawnPoint(); // Throws exeption if there's no space for new object, adjust sizes and spawn
                                         // frequency
            }
        }
        return p;
    }

    /**
     * Returns the list of the gameObjects present in the minigame.
     * 
     * @return the list of the gameObjects present in the minigame.
     */
    @Override
    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gObjects);
    }
}
