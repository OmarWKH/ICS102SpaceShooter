package gameobjects;

import java.awt.geom.Point2D;

public abstract class AbstractEnemyShip extends AbstractShip {
	private AbstractGameObject target;
	private Point2D.Double location;

	public AbstractEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation);
		this.target = target;
		this.location = location;
	}

	@Override
	public void moveDirection() {
		this.setDirectionToward(target.getCenter());
	}

	@Override
	public void initializeLocation() {
		this.setPosition(location);
		this.setDirectionToward(target.getCenter());
	}
}