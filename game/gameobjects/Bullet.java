package gameobjects;

/**
 * This class defines a bullet. It has a shooter and a fixed direction according to shooter.
 *
 * @author Omar Khashoggi
 */
public class Bullet extends AbstractGameObject {
	private Shooter shooter;
	public static double xVelocity;
	public static double yVelocity;
	public static String imageLocation;
	public static int healthPoints;

	/**
	 * The constructor only takes a shooter
	 *
	 * @param shooter the shooter of this bullet
	 */
	public Bullet(Shooter shooter) {
		super(Bullet.healthPoints, Bullet.imageLocation);
		this.shooter = shooter;
	}

	/**
	 * This method returns the shooter to custody
	 *
	 * @return returns the shooter
	 */
	public Shooter getShooter() {
		return this.shooter;
	}

	/**
	 * This method sets location on the center of the shooter.
	 * It also sets direction the same as the shooter and fixes the velocity according to direction and a static game play variable.
	 *
	 */
	@Override
	public void initializeLocation() {
		this.setXPosition(shooter.getXCenter() - this.getWidth()/2);
		this.setYPosition(shooter.getYCenter() - this.getHeight()/2);
		this.setDirection(shooter.getDirection());
		this.setXVelocity(Bullet.xVelocity * this.getXDirection());
		this.setYVelocity(Bullet.yVelocity * this.getYDirection());
	}

	/**
	 * Moves the position of the bullet by increasing its position with the fixed velocity. Also kills it if it gets out of bounds.
	 *
	 */
	@Override
	public void movePosition() {
		boolean isOutOfBounds = ( this.getXCenter() > panel.getWidth() ) || ( this.getXCenter() < 0 )  || ( this.getYCenter() > panel.getHeight() ) || ( this.getYCenter() < 0 );
		if (isOutOfBounds) {
			this.makeDead();
		} else {
			this.setXPosition(this.getXPosition() + this.getXVelocity());
			this.setYPosition(this.getYPosition() + this.getYVelocity());
		}
	}

	/**
	 * This method overrides moveDirection() as to not do anything. Bullet does not change direction,
	 *
	 */
	@Override
	public void moveDirection() {}; //I don't sway
}
