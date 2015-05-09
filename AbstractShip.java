import javax.swing.JPanel;

public abstract class AbstractShip extends AbstractGameObject {
	public AbstractShip(int healthPoints, String imageLocation) {
		super(healthPoints, imageLocation);
	}

	@Override
	public void movePosition() {
		double newXPosition = this.getXPosition() + this.getXVelocity();
		double newYPosition = this.getYPosition() - this.getYVelocity();
		
		//follower gets stuck
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