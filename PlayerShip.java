import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.MouseInfo;
import java.awt.Point;

public class PlayerShip extends AbstractShip implements Shooter {
	//float
	//private JPanel panel;
	private static String imageLocation = GameTier.imagesFolder + "PlayerShip.png";
	private static int healthPoints = 3;
	private static long coolDownTime = 500;
	private long lastShotTime;

	public PlayerShip() {
		//offset for sprite width, length
		//could just spill it out
		super(PlayerShip.healthPoints, PlayerShip.imageLocation);
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
	public void initializeLocation() {
		this.setXPosition(panel.getWidth()/2);
		this.setYPosition(panel.getHeight()/2);
	}

	//decide how it's gonna work, if not different then pull it out

	/*
	@Override
	public void move() {
		//happens to bullets too, sometimes things get slower than they need be

		//could utilize super.move(); but that won't force an override, which is needed to setDirection

		System.out.println(this.getXPosition() + ".." + this.getYPosition());

		//System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + ".." + getXPosition());
		//System.out.println(MouseInfo.getPointerInfo().getLocation().getY() + ".." + getYPosition());
	}
	*/

	@Override
	public void moveDirection() {
		//center is a little off
		//this.setXDirection(MouseInfo.getPointerInfo().getLocation().getX() - getXCenter());
		//this.setYDirection(MouseInfo.getPointerInfo().getLocation().getY() - getYCenter());
		/*double dX = (MouseInfo.getPointerInfo().getLocation().getX() - getXCenter());
		double dY = (MouseInfo.getPointerInfo().getLocation().getY() - getYCenter());
		double l = Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
		this.setXDirection(dX/l);
		this.setYDirection(dY/l);*/
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		//Point2D.Double mouseLocationDouble = new Point2D.Double(mouseLocation.getX(), mouseLocation.getY());
		this.setDirectionToward(mouseLocation);
	}


	public void movementKeyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			this.setYVelocity(this.getYVelocity() + 1);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setYVelocity(this.getYVelocity() - 1);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setXVelocity(this.getXVelocity() + 1);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setXVelocity(this.getXVelocity() - 1);
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
