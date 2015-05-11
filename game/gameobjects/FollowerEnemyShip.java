package gameobjects;

import java.awt.geom.Point2D;

/**
 * This class defines an enemy type. This enemy is a follower who does not shoot.
 *
 * @author Omar Khashoggi
 */
public class FollowerEnemyShip extends AbstractEnemyShip {
	public static int healthPoints;
	public static String imageLocation;
	public static double xVelocity;
	public static double yVelocity;

	/**
	 * This constructor takes HP, sprite location, target, and position
	 * 
	 * @param healthPoints Ship HP
	 * @param imageLocation sprite location
	 * @param target the target of this ship
	 * @param location the positions this ship starts at
	 */
	public FollowerEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation, target, location);
	}

	/**
	 * This is the same constructor but it sets HP and sprite location to the static variables of those values
	 *
	 * @param target the target of this ship
	 * @param location the positions this ship starts at
	 */

	public FollowerEnemyShip(AbstractGameObject target, Point2D.Double location) {
		this(FollowerEnemyShip.healthPoints, FollowerEnemyShip.imageLocation, target, location);
	}

	/**
	 * This method makes this ship changes position toward its target, following it
	 *
	 */
	@Override
	public void movePosition() {
		this.setXVelocity(FollowerEnemyShip.xVelocity * this.getXDirection());
		this.setYVelocity(FollowerEnemyShip.yVelocity * -1 *this.getYDirection());
		super.movePosition();
	}

}