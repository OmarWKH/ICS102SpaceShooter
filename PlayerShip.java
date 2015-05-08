import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.MouseInfo;

public class PlayerShip extends AbstractGameObject implements Shooter {
	//float
	//private JPanel panel;
	private static String imageLocation = GameTier.imagesFolder + "PlayerShip.png";
	private static int healthPoints = 3;
	private static long coolDownTime = 500; 
	private long lastShotTime;

	public PlayerShip() {
		//offset for sprite width, length
		//could just spill it out
		super(healthPoints, imageLocation);
		//bad that 3 is not clear to be HP?
		//this.setImageLocation(imageLocation);
	}
	/*
	public PlayerShip() {
		this(null, null);
		//could try catch it, related to GameObject and draw(g2d)
	}
	*/

	@Override
	public void initializeLocationOn(JPanel panel) {
		this.panel = panel;
		this.setXPosition(panel.getWidth()/2);
		this.setYPosition(panel.getHeight()/2);
	}

	//decide how it's gonna work, if not different then pull it out
	@Override
	public void move() {
		//happens to bullets too, sometimes things get slower than they need be

		//could utilize super.move(); but that won't force an override, which is needed to setDirection
		double newXPosition = this.getXPosition() + this.getXVelocity();
		double newYPosition = this.getYPosition() - this.getYVelocity();
		
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

		System.out.println(this.getXPosition() + ".." + this.getYPosition());

		this.setXDirection(MouseInfo.getPointerInfo().getLocation().getX() - getXPosition());
		this.setYDirection(MouseInfo.getPointerInfo().getLocation().getY() - getYPosition());

		//System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + ".." + getXPosition());
		//System.out.println(MouseInfo.getPointerInfo().getLocation().getY() + ".." + getYPosition());
	}

	
	public void movementKeyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			this.setYVelocity(this.getYVelocity() + 0.1);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setYVelocity(this.getYVelocity() - 0.1);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setXVelocity(this.getXVelocity() + 0.1);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setXVelocity(this.getXVelocity() - 0.1);
		}
	}

	public void movementKeyReleased(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			this.setYVelocity(0);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setYVelocity(0);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setXVelocity(0);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setXVelocity(0);
		}
	}

	@Override
	public void shoot() {
		if (timeToShoot()) {
			GameTier.gameEngine.addBullet(this);
			lastShotTime = System.currentTimeMillis();
		} else {
			System.out.println("Player cooling down");
		}
	}

	@Override
	public boolean timeToShoot() {
		return (System.currentTimeMillis() - lastShotTime >= PlayerShip.coolDownTime);
	}



	//limit access to pressing W, password as argument? lol
	/*
	public void moveUp() {
		this.setYVelocity(this.getYVelocity() + 1);
		//System.out.println("I ran");
	}
	*/
	/*
	public void movementControls(ActionEvent ae) {
		if (ae.getActionCommand().equalsIgnoreCase("W")) {
			this.setYVelocity(this.getYVelocity() + 1);
		}
	}
	*/
	/*
	public void paint(Graphics g) {
		System.out.println(this.getXPosition() + ".." + this.getYPosition());
		System.out.println(this.getXVelocity() + ".." + this.getYVelocity());
	}
	*/
}
