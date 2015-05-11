package gameobjects;

import tier.*;
import java.awt.event.KeyEvent;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 * This class defines a ship that's controlled by a player and can shoot.
 *
 * @author Omar Khashoggi
 */
public class PlayerShip extends AbstractShip implements Shooter {
	public static String imageLocation;
	public static long coolDownTime;
	public static double xAcceleration;
	public static double yAcceleration;
	public static double maxXVelocity;
	public static double maxYVelocity;
	public static int healthPoints;
	private long lastShotTime;

	/**
	 * This constructor sets HP and sprite locatoin from static game play values
	 *
	 */
	public PlayerShip() {
		super(PlayerShip.healthPoints, PlayerShip.imageLocation);
	}

	/**
	 * This mehod makes the player start at the center
	 *
	 */
	@Override
	public void initializeLocation() {
		this.setXPosition(panel.getWidth()/2);
		this.setYPosition(panel.getHeight()/2);
	}

	/**
	 * This method makes the player directoin the same as the mouse
	 *
	 */
	@Override
	public void moveDirection() {
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		this.setDirectionToward(mouseLocation);
	}

	/**
	 * This method handles key controls for movement. It increases the velocity by a set acceleration when the appropriate key events are passed. It does not allow the velocity to be above a certain max velocity.
	 *
	 * @param keyEvent the key event
	 */
	public void movementKeyPressed(KeyEvent keyEvent) {
		boolean atMaxXVelocity = Math.abs(this.getXVelocity()) >= PlayerShip.maxXVelocity;
		boolean atMaxYVelocity = Math.abs(this.getYVelocity()) >= PlayerShip.maxYVelocity;

		if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			if (!atMaxYVelocity) {
				this.setYVelocity(this.getYVelocity() + PlayerShip.yAcceleration);
			}
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_S || keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!atMaxYVelocity) {
				this.setYVelocity(this.getYVelocity() - PlayerShip.yAcceleration);
			}
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (!atMaxXVelocity) {
				this.setXVelocity(this.getXVelocity() + PlayerShip.xAcceleration);
			}
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
			if (!atMaxXVelocity) {
				this.setXVelocity(this.getXVelocity() - PlayerShip.xAcceleration);
			}
		}
	}

	/**
	 * This method handles key controls for stoppig movement. If the appropriate key event is passed it sets velocity to 0.
	 *
	 * @param keyEvent the key event
	 */
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
	
	/**
	 * This method is responisble for firing bullets. When it's called, it check if it's time for the shooter to shoot. If so, it asks the engine to fire a bullet in it's name and restarts the cooldown.
	 *
	 */
	@Override
	public void shoot() {
		if (timeToShoot()) {
			GameTier.gameEngine.addBullet(this);
			lastShotTime = System.currentTimeMillis();
		} else {
			//System.out.println("Player cooling down");
		}
	}

	@Override
	public boolean timeToShoot() {
		return (System.currentTimeMillis() - lastShotTime >= PlayerShip.coolDownTime);
	}
}
