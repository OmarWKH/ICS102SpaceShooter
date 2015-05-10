package gameobjects;

public class Bullet extends AbstractGameObject {
	private Shooter shooter;
	public static double xVelocity;
	public static double yVelocity;
	public static String imageLocation;
	public static int healthPoints;

	public Bullet(Shooter shooter) {
		super(Bullet.healthPoints, Bullet.imageLocation);
		this.shooter = shooter;
	}

	public Shooter getShooter() {
		return this.shooter;
	}

	@Override
	public void initializeLocation() {
		this.setXPosition(shooter.getXCenter() - this.getWidth()/2);
		this.setYPosition(shooter.getYCenter() - this.getHeight()/2);
		this.setDirection(shooter.getDirection());
		this.setXVelocity(Bullet.xVelocity * this.getXDirection());
		this.setYVelocity(Bullet.yVelocity * this.getYDirection());
	}

	@Override
	public void movePosition() {
		boolean isOutOfBounds = ( this.getXCenter() > panel.getWidth() ) || ( this.getXCenter() < 0 )  || ( this.getYCenter() > panel.getHeight() ) || ( this.getYCenter() < 0 );
		if (isOutOfBounds) {
			this.gotHit();
		} else {
			this.setXPosition(this.getXPosition() + this.getXVelocity());
			this.setYPosition(this.getYPosition() + this.getYVelocity());
		}
	}

	@Override
	public void moveDirection() {}; //I don't sway
}
