package game.gameobject.whacamoleobjects;

import game.controlling.InputModel;
import game.gameobject.AspectModel;
import game.gameobject.PhysicsModel;
import game.minigame.whacamoleminigame.Level;

/**
 * Class that models the mole character in the Whac-a-Mole minigame.
 */
public class Mole extends WamObject {

    /**
     * Simple constructor aimed at initializing Mole fields.
     * 
     * @param appearanceTime the time at which it is scheduled to appear
     * @param currentLevel   the level to which the object belongs
     * @param holeNumber     the identifier of the hole assigned to the mole
     * @param physicsModel   the physics model of the WamObject
     * @param aspectModel    the aspect model of the mole 
     * @param inputModel     the input model of the WamObject
     */
    public Mole(final long appearanceTime, 
                final Level currentLevel, 
                final int holeNumber,
                final PhysicsModel physicsModel, 
                final AspectModel aspectModel, 
                final InputModel inputModel) {
        super(appearanceTime, currentLevel, holeNumber, physicsModel, aspectModel, inputModel);
    }

    /**
     * Method that checks if the mole caused 
     * the end of the game, i.e. if it managed 
     * to re-enter its hole without being hit.
     */
    @Override
    public boolean isGameOver() {
        return this.getStatus().equals(Status.MISSED);
    }

    /**
     * Method that checks if the mole instance is 
     * still used in the game or can be deleted.
     */
    @Override
    public boolean isStillInUse() {
        return !this.getStatus().equals(Status.HIT);
    }
}
