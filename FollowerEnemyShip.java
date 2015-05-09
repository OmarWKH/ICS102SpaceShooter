import java.awt.geom.Point2D;

public class FollowerEnemyShip extends AbstractEnemyShip {
	public static int healthPoints; // = 1;
	public static String imageLocation; // = GameTier.imagesFolder + "PlayerShip.png";
	public static double xVelocity; // = 1;
	public static double yVelocity; // = 1;

	public FollowerEnemyShip(AbstractGameObject target, Point2D.Double location) {
		this(FollowerEnemyShip.healthPoints, FollowerEnemyShip.imageLocation, target, location);
	}
/*
	public FollowerEnemyShip(String imageLocation, AbstractGameObject target) {
		this(FollowerEnemyShip.healthPoints, imageLocation, target);
	}

	public FollowerEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target) {
		this(healthPoints, imageLocation, target);
	}
*/
	public FollowerEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation, target, location);
	}

	public void movePosition() {
		//this.setXPosition(this.getXPosition() + this.getXDirection()*this.getXVelocity());
		//this.setYPosition(this.getYPosition() + this.getYDirection()*this.getYVelocity());
		//v*d pull out?
		this.setXVelocity(FollowerEnemyShip.xVelocity * this.getXDirection());
		this.setYVelocity(FollowerEnemyShip.yVelocity * -1 *this.getYDirection());
		super.movePosition();
		//force to call super?
		//change velocity, this will make velocity increase
		//call super.movePosition to move, garuntee wrap

	}

}