package gameobjects;

import java.awt.geom.Point2D;

/**
 * This abstract class defines a base enemy ships. Enemy ships have a target, and they always poitn toward it.
 *
 * @author Omar Khashoggi
 */
public abstract class AbstractEnemyShip extends AbstractShip {
	private AbstractGameObject target;
	private Point2D.Double location;

	/**
	 * This constructor takes HP, sprite location, target, and position
	 * 
	 * @param healthPoints Ship HP
	 * @param imageLocation sprite location
	 * @param target the target of this ship
	 * @param location the positions this ship starts at
	 */
	public AbstractEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target, Point2D.Double location) {
		super(healthPoints, imageLocation);
		this.target = target;
		this.location = location;
	}

	/**
	 * This method makes it so the ship always points toward its target's center
	 *
	 */
	@Override
	public void moveDirection() {
		this.setDirectionToward(target.getCenter());
	}

	/**
	 * This method sets the initial location of the ship to the passed location by the constructor, allowing random location generation. It also sets the initial direction to the target.
	 *
	 */
	@Override
	public void initializeLocation() {
		this.setPosition(location);
		this.setDirectionToward(target.getCenter());
	}
}