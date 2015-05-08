import javax.swing.JPanel;

public abstract class AbstractEnemyShip extends AbstractShip {
	private AbstractGameObject target;

	public AbstractEnemyShip(int healthPoints, String imageLocation, AbstractGameObject target) {
		super(healthPoints, imageLocation);
		this.target = target;
	}

	@Override
	public void moveDirection() {
		double dX = (target.getXCenter() - getXCenter());
		double dY = (target.getYCenter() - getYCenter());
		double l = Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
		//this.setXDirection(target.getXCenter() - this.getXCenter());
		//this.setYDirection(target.getYCenter() + this.getYCenter());
		this.setXDirection(dX/l);
		this.setYDirection(dY/l);
		System.out.println(dX/l);
	}

	@Override
	public void initializeLocation() {
		this.setPosition(GameEngine.getRandomLocation());
	}
}