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
		//compute vector pull out
		this.setDirectionToward(target.getCenter());
		//this.setXDirection(target.getXCenter() - this.getXCenter());
		//this.setYDirection(target.getYCenter() + this.getYCenter());
		/*
		double dX = (target.getXCenter() - getXCenter());
		double dY = (target.getYCenter() - getYCenter());
		double l = Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
		this.setXDirection(dX/l);
		this.setYDirection(dY/l);
		*/
	}

	@Override
	public void initializeLocation() {
		this.setPosition(location);
		this.setDirectionToward(target.getCenter());
	}
}