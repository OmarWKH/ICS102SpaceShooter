public class ShooterEnemyShip extends AbstractEnemyShip implements Shooter {
	private static int healthPoints = 1;
	private static String imageLocation = GameTier.imagesFolder + "PlayerShip.png";
	private static double xVelocity = 1;
	private static double yVelocity = 1;
	private static long coolDownTime = 2000;
	private long lastShotTime;

	public ShooterEnemyShip(AbstractGameObject target) {
		this(ShooterEnemyShip.healthPoints, ShooterEnemyShip.imageLocation, target);
	}

	public ShooterEnemyShip(String imageLocation, AbstractGameObject target) {
		this(ShooterEnemyShip.healthPoints, imageLocation, target);
	}

	public ShooterEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target) {
		super(healthPoints, imageLocation, target);
	}

	//could pull out to ship?
	public void shoot() {
		if (timeToShoot()) {
			GameTier.gameEngine.addBullet(this);
			lastShotTime = System.currentTimeMillis();
		} else {
			System.out.println("Player cooling down");
		}
	}

	public boolean timeToShoot() {
		return (System.currentTimeMillis() - lastShotTime >= ShooterEnemyShip.coolDownTime);
	}

	public void movePosition() {
		//ughh, such bad usage of this stuff :P

		//this.shoot();

	}; //I stay put
}