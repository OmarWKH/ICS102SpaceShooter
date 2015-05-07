import javax.swing.JPanel;

public class Bullet extends AbstractGameObject {
	private GameObject shooter;
	private static double bulletXVelocity = 0.1;
	private static double bulletYVelocity = 0.1;
	private static String imageLocation = GameTier.imagesFolder + "bullet.png";
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
		this.setXPosition(shooter.getXPosition());
		this.setYPosition(shooter.getYPosition());
		this.setXDirection(shooter.getXDirection());
		this.setYDirection(shooter.getYDirection());
		//velocity must be in the direction, need to math it up, trignometry, is that what it's called?
		System.out.printf("Px %f Py %f Dx %f Dy %f Px %f Py %f Dx %f Dy %f", this.getXPosition(), this.getYPosition(), this.getXDirection(), this.getYDirection(), 
																			 shooter.getXPosition(), shooter.getYPosition(), shooter.getXDirection(), shooter.getYDirection());
	}

	@Override
	public void move() {
		this.setXPosition(this.getXPosition() + this.getXVelocity());
		this.setYPosition(this.getYPosition() - this.getYVelocity());
	}
}