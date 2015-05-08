import javax.swing.JPanel;

public class Bullet extends AbstractGameObject {
	private GameObject shooter;
	private static double xVelocity = 5;
	private static double yVelocity = 5;
	private static String imageLocation = GameTier.imagesFolder + "Bullet.png";
	//if all of them need to set it, abstractr method or force constructor
	private static int healthPoints = 1;

	public Bullet(Shooter shooter) {
		super(Bullet.healthPoints, Bullet.imageLocation);
		this.shooter = shooter;
		//this.setXVelocity(Bullet.xVelocity);
		//this.setYVelocity(Bullet.yVelocity);
	}

	@Override
	public void initializeLocation() {
		//not accurate to mouse
		//speed change depending on how far the mouse is, this because of how direction is calculated and how it gets into position
		//could be kind of a mechanic, the faster it is the more harmful, and the less accurate, that's the lazy to a fault solution

		this.setXPosition(shooter.getXCenter() - this.getImage().getWidth()/2);
		this.setYPosition(shooter.getYCenter() - this.getImage().getHeight()/2);
		this.setXDirection(shooter.getXDirection());
		this.setYDirection(shooter.getYDirection());
	}

	@Override
	public void movePosition() {
		//if out
			//kill(this)
		this.setXPosition(this.getXPosition() + this.getXDirection()*Bullet.xVelocity);
		this.setYPosition(this.getYPosition() + this.getYDirection()*Bullet.yVelocity);

		//could have higher methods to do repeating things, example: move according to direction
		//could ask super to move xP+xV, and this sets V
	}

	@Override
	public void moveDirection() {}; //I don't sway
}