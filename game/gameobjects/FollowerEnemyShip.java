package gameobjects;

import java.awt.geom.Point2D;

public class FollowerEnemyShip extends AbstractEnemyShip {
	public static int healthPoints;
	public static String imageLocation;
	public static double xVelocity;
	public static double yVelocity;

	public FollowerEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation, target, location);
	}

	public FollowerEnemyShip(AbstractGameObject target, Point2D.Double location) {
		this(FollowerEnemyShip.healthPoints, FollowerEnemyShip.imageLocation, target, location);
	}

	public void movePosition() {
		this.setXVelocity(FollowerEnemyShip.xVelocity * this.getXDirection());
		this.setYVelocity(FollowerEnemyShip.yVelocity * -1 *this.getYDirection());
		super.movePosition();
	}

}