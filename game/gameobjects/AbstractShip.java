package gameobjects;

/**
 * This abstract class defines a basic ship. This ship moves in a certain way and wraps around the world
 *
 * @author Omar Khashoggi
 */
public abstract class AbstractShip extends AbstractGameObject {
	/**
	 * The constructor takes HP and sptire location
	 *
	 * @param healthPoints HP
	 * @param imageLocation sprite locatoin
	 */
	public AbstractShip(int healthPoints, String imageLocation) {
		super(healthPoints, imageLocation);
	}

	/**
	 * Moves the position of the ship by increasing position with velocity. Taking into account wraping around edges
	 *
	 */
	@Override
	public void movePosition() {
		double newXPosition = this.getXPosition() + this.getXVelocity();
		double newYPosition = this.getYPosition() - this.getYVelocity();
		
		//follower gets stuck, therefore generator exludes edges
		if (this.getXCenter() > panel.getWidth()) {
			this.setXPosition(newXPosition - panel.getWidth());
		} else if (this.getXCenter() < 0) {
			//was < width, had flickering, but appeared both side same time which is pretty darn cool
			this.setXPosition(newXPosition + panel.getWidth());
		} else {
			this.setXPosition(newXPosition);
		}

		if (this.getYCenter() > panel.getHeight()) {
			this.setYPosition(newYPosition - panel.getHeight());
		} else if (this.getYCenter() < 0) {
			this.setYPosition(newYPosition + panel.getHeight());
		} else {
			this.setYPosition(newYPosition);
		}
	}

	@Override
	public abstract void moveDirection();

	@Override
	public abstract void initializeLocation();
}