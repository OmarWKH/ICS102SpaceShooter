import java.awt.geom.Point2D;
import java.util.Random;

public class ShooterEnemyShip extends AbstractEnemyShip implements Shooter {
	public static int healthPoints = 1;
	public static String imageLocation = GameTier.imagesFolder + "PlayerShip.png";
	public static long coolDownTime = 2000;
	private long lastShotTime;

	public ShooterEnemyShip(AbstractGameObject target, Point2D.Double location) {
		this(ShooterEnemyShip.healthPoints, ShooterEnemyShip.imageLocation, target, location);
	}
/*
	public ShooterEnemyShip(String imageLocation, AbstractGameObject target) {
		this(ShooterEnemyShip.healthPoints, imageLocation, target);
	}

	public ShooterEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target) {
		super(healthPoints, imageLocation, target);
	}
*/
	public ShooterEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation, target, location);
		int toShootOrNotToShoot = (new Random()).nextInt(2);
		this.lastShotTime = System.currentTimeMillis() * toShootOrNotToShoot;
	}

	//could pull out to ship?
	public void shoot() {
		if (timeToShoot()) {
			GameTier.gameEngine.addBullet(this);
			lastShotTime = System.currentTimeMillis();
		}
	}

	public boolean timeToShoot() {
		return (System.currentTimeMillis() - lastShotTime >= ShooterEnemyShip.coolDownTime);
	}

	public void movePosition() {}; //I stay put
}