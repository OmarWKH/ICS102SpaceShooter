package gameobjects;

import tier.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This class defines an enemy type. This enemy is a shooter who stands still.
 *
 * @author Omar Khashoggi
 */
public class ShooterEnemyShip extends AbstractEnemyShip implements Shooter {
	public static int healthPoints;
	public static String imageLocation;
	public static long coolDownTime;
	private long lastShotTime;

	/**
	 * This constructor takes HP, sprite location, target, and position
	 * 
	 * @param healthPoints Ship HP
	 * @param imageLocation sprite location
	 * @param target the target of this ship
	 * @param location the positions this ship starts at
	 */
	public ShooterEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation, target, location);
		int toShootOrNotToShoot = (new Random()).nextInt(2);
		this.lastShotTime = System.currentTimeMillis() * toShootOrNotToShoot;
	}

	/**
	 * This is the same constructor but it sets HP and sprite location to the static variables of those values
	 *
	 * @param target the target of this ship
	 * @param location the positions this ship starts at
	 */
	public ShooterEnemyShip(AbstractGameObject target, Point2D.Double location) {
		this(ShooterEnemyShip.healthPoints, ShooterEnemyShip.imageLocation, target, location);
	}

	/**
	 * This method is responisble for firing bullets. When it's called, it check if it's time for the shooter to shoot. If so, it asks the engine to fire a bullet in it's name and restarts the cooldown.
	 *
	 */
	public void shoot() {
		if (timeToShoot()) {
			GameTier.gameEngine.addBullet(this);
			lastShotTime = System.currentTimeMillis();
		}
	}

	public boolean timeToShoot() {
		return (System.currentTimeMillis() - lastShotTime >= ShooterEnemyShip.coolDownTime);
	}

	/**
	 * This method is empty. This enemy type does not move.
	 *
	 */
	public void movePosition() {}; //I stay put
}