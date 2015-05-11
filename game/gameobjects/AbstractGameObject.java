package gameobjects;

import tier.*;
import engine.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * This class provides implmentation to the interface GameObject. It's the basis of all game objects
 *
 * @author Omar Khashoggi
 */
public abstract class AbstractGameObject implements GameObject {
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private double xDirection;
	private double yDirection;
	private int helathPoints;
	private BufferedImage image;
	private AffineTransform affineTransform;
	public static JPanel panel;
	
	/**
	 * This constructor creates a game object with given HP and sprite location
	 *
	 * @param healthPoints the HP of the game object
	 * @param imageLocation the location of the sprite
	 */
	public AbstractGameObject(int healthPoints, String imageLocation) {
		this.setHealthPoints(healthPoints);
		this.setImageLocation(imageLocation);
		affineTransform = new AffineTransform();
	}

	/**
	 * This method draws the object. It rotates it around the center according to direction and draws it on it's poisition.
	 *
	 * @param g2d the Graphics2D to do the drawing
	 */
	@Override
	public void draw(Graphics2D g2d) {
		affineTransform.setToIdentity();
		affineTransform.rotate(xDirection, yDirection, this.getXCenter(), this.getYCenter());
		affineTransform.translate(xPosition, yPosition);

		g2d.drawImage(image, affineTransform, null);

		/*
		g2d.setColor(Color.RED);
		g2d.drawOval((int)xPosition, (int)yPosition, 10, 10);
		g2d.setColor(Color.GREEN);
		g2d.drawOval(panel.getWidth()/2, panel.getHeight()/2, 10, 10);
		g2d.drawOval((int)this.getXCenter(), (int)this.getYCenter(), 10, 10);
		*/
	}

	/**
	 * This method moves the object. It moves both position and direction.
	 *
	 */
	@Override
	public void move() {
		this.movePosition();
		this.moveDirection();
	}

	@Override
	public abstract void movePosition();
	@Override
	public abstract void moveDirection();

	@Override
	public abstract void initializeLocation();

	@Override
	public double getXPosition() {
		return this.xPosition;
	}
	@Override
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public double getYPosition() {
		return this.yPosition;
	}
	@Override
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public Point2D.Double getPosition() {
		return new Point2D.Double(this.getXPosition(), this.getYPosition());
	}
	@Override
	public void setPosition(Point2D.Double point) {
		this.setXPosition(point.getX());
		this.setYPosition(point.getY());
	}

	@Override
	public double getXVelocity() {
		return this.xVelocity;
	}
	@Override
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	@Override
	public double getYVelocity() {
		return this.yVelocity;
	}
	@Override
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	@Override
	public double getXDirection() {
		return this.xDirection;
	}
	@Override
	public void setXDirection(double xDirection) {
		this.xDirection = xDirection;
	}

	@Override
	public double getYDirection() {
		return this.yDirection;
	}
	@Override
	public void setYDirection(double yDirection) {
		this.yDirection = yDirection;
	}

	@Override
	public void setDirection(Point2D location) {
		this.setXDirection(location.getX());
		this.setYDirection(location.getY());
	}
	@Override
	public Point2D.Double getDirection() {
		return new Point2D.Double(this.getXDirection(), this.getYDirection());
	}

	/**
	 * This method sets the directoin toward a point. A vector is defined by the given location and the game object's center, that vector is normalized because it's used in velocity calculations.
	 *
	 * @param location a point containing the location of a point to set direction to
	 */
	@Override
	public void setDirectionToward(Point2D location) {
		Point2D.Double unitVector = normalize(location, this.getCenter());
		this.setDirection(unitVector);
		//this.setXDirection(target.getXCenter() - this.getXCenter());
		//this.setYDirection(target.getYCenter() + this.getYCenter());
	}

	@Override
	public double getWidth() {
		return this.getImage().getWidth();
	}

	@Override
	public double getHeight() {
		return this.getImage().getHeight();
	}

	@Override
	public double getXCenter() {
		return this.getXPosition() + this.getWidth()/2;
	}
	@Override
	public double getYCenter() {
		return this.getYPosition() + this.getHeight()/2;
	}
	@Override
	public Point2D.Double getCenter() {
		return new Point2D.Double(this.getXCenter(), this.getYCenter());
	}

	/**
	 * This method is called if the object got hit, decreasing it's HP by 1. It then checks if it's dead. And if it is, informs the game engine.
	 *
	 */
	@Override
	public void gotHit() {
		this.setHealthPoints(this.getHealthPoints() - 1);
		if (isDead()) {
			GameTier.gameEngine.manDown(this);
		}
	}

	/**
	 * This method checks if an object is dead.
	 *
	 * @return returns true if HP is less than or equals 0. False otherwise.
	 */
	@Override
	public boolean isDead() {
		return (getHealthPoints() <= 0);
	}

	/**
	 * This method makes te object dead. It sets HP to -1 and calls gotHit()
	 *
	 */
	@Override
	public void makeDead() {
		this.setHealthPoints(-1);
		this.gotHit();
	}

	@Override
	public BufferedImage getImage() {
		return this.image;
	}
	@Override
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	@Override
	public void setImageLocation(String imageLocation) {
		try {
			image = ImageIO.read(new File(imageLocation));
		} catch (IOException ioe) {
			System.out.println("Failed to load image at: " + imageLocation);
			ioe.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public int getHealthPoints() {
		return this.helathPoints;
	}
	@Override
	public void setHealthPoints(int helathPoints) {
		this.helathPoints = helathPoints;
	}

	/**
	 * The method normalized a vector given its two end points. It calculates the x and y component (n1-n2). Find the length of the vector by simple trigonometry. Then divides the components by the length, normalizing the vector.
	 *
	 * @param locatoin1 the first end point
	 * @param location2 the second end point
	 * @return returns a normalized vector in the form of a point (made vector by the game object's center)
	 */
	private static Point2D.Double normalize(Point2D location1, Point2D location2) {
		double xComponent = (location1.getX() - location2.getX());
		double yComponent = (location1.getY() - location2.getY());
		double length = Math.sqrt( Math.pow(xComponent,2) + Math.pow(yComponent,2) );
		return new Point2D.Double(xComponent/length, yComponent/length);
	}
}
