package gameobjects;

import tier.*;
import java.awt.event.KeyEvent;
import java.awt.MouseInfo;
import java.awt.Point;

public class PlayerShip extends AbstractShip implements Shooter {
	public static String imageLocation;
	public static long coolDownTime;
	public static double xAcceleration;
	public static double yAcceleration;
	public static double maxXVelocity;
	public static double maxYVelocity;
	public static int healthPoints;
	private long lastShotTime;

	public PlayerShip() {
		super(PlayerShip.healthPoints, PlayerShip.imageLocation);
	}

	@Override
	public void initializeLocation() {
		this.setXPosition(panel.getWidth()/2);
		this.setYPosition(panel.getHeight()/2);
	}

	@Override
	public void moveDirection() {
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		this.setDirectionToward(mouseLocation);
	}


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
			//System.out.println("Player cooling down");
		}
	}

	@Override
	public boolean timeToShoot() {
		return (System.currentTimeMillis() - lastShotTime >= PlayerShip.coolDownTime);
	}
}
