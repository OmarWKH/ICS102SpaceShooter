import javax.swing.JPanel;

public class Bullet extends AbstractGameObject {
	private GameObject shooter;
	private static double bulletXVelocity = 0.1;
	private static double bulletYVelocity = 0.1;
	private static String imageLocation = GameTier.imagesFolder + "Bullet.png";
	//if all of them need to set it, abstractr method or force constructor
	private static int healthPoints = 1;

	public Bullet(Shooter shooter) {
		super(healthPoints, imageLocation);
		this.shooter = shooter;
		this.setXVelocity(bulletXVelocity);
		this.setYVelocity(bulletYVelocity);
	}

	@Override
	public void initializeLocationOn(JPanel panel) {
		this.panel = panel;
		this.setXPosition(shooter.getXCenter() - this.getImage().getWidth()/2);
		this.setYPosition(shooter.getYCenter() - this.getImage().getHeight()/2);
		this.setXDirection(shooter.getXDirection());
		this.setYDirection(shooter.getYDirection());
	}

	@Override
	public void move() {
		this.setXPosition(this.getXPosition() + this.getXDirection()*this.getXVelocity());
		this.setYPosition(this.getYPosition() + this.getYDirection()*this.getYVelocity());
	}
}