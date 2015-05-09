public class FollowerEnemyShip extends AbstractEnemyShip {
	private static int healthPoints = 1;
	private static String imageLocation = GameTier.imagesFolder + "PlayerShip.png";
	private static double xVelocity = 1;
	private static double yVelocity = 1;

	public FollowerEnemyShip(AbstractGameObject target) {
		this(FollowerEnemyShip.healthPoints, FollowerEnemyShip.imageLocation, target);
	}

	public FollowerEnemyShip(String imageLocation, AbstractGameObject target) {
		this(FollowerEnemyShip.healthPoints, imageLocation, target);
	}

	public FollowerEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target) {
		super(healthPoints, imageLocation, target);
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