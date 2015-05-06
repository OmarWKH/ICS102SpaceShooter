import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.MouseInfo;

public class PlayerShip extends GameObject {
	//float
	//private JPanel panel;

	public PlayerShip() {
		//offset for sprite width, length
		//could just spill it out
		super(3);
		//bad that 3 is not clear to be HP?
		this.setImageLocation("PlayerShipOpenGameArtAttribute.png");
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
		this.setXPosition(this.getXPosition() + this.getXVelocity());
		this.setYPosition(this.getYPosition() - this.getYVelocity());
		this.setXDirection(MouseInfo.getPointerInfo().getLocation().getX() - getXPosition());
		this.setYDirection(MouseInfo.getPointerInfo().getLocation().getY() - getYPosition());
	}

	
	public void keyPressed(KeyEvent keyEvent) {
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

	public void keyReleased(KeyEvent keyEvent) {
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
