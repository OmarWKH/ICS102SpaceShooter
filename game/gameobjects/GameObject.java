package gameobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

/**
 * This interface defines many of any game object methods.
 * It's an interface first and not an abstract class so we can create subinterfaces with different functions like Shooter. So if an object is references as Shooter it would still have access to these methods.
 *
 * @author Omar Khashoggi
 */
public interface GameObject {
	/**
	 * This method draws the object.
	 *
	 * @param g2d the Graphics2D to do the drawing
	 */
	public void draw(Graphics2D g2d);
	
	/**
	 * This method moves the object.
	 *
	 */
	public void move();
	/**
	 * This method moves the object only in term of position.
	 *
	 */
	public void movePosition();
	/**
	 * This method moves the object only in terms of direction (rotation).
	 *
	 */
	public void moveDirection();

	/**
	 * This method is called when a game object is created and is ready to be given a location on a panel.
	 *
	 */
	public void initializeLocation();

	/**
	 * This method get the position on x-axis.
	 *
	 * @return returns the position on x-axis
	 */
	public double getXPosition();
	/**
	 * This method sets the position on x-axis.
	 *
	 * @param xPosition the position on x-axis
	 */
	public void setXPosition(double xPosition);
	/**
	 * This method get the position on y-axis.
	 *
	 * @return returns the position on y-axis
	 */
	public double getYPosition();
	/**
	 * This method sets the position on y-axis.
	 *
	 * @param yPosition the position on y-axis
	 */
	public void setYPosition(double yPosition);
	/**
	 * This method get the position.
	 *
	 * @return returns the position
	 */
	public Point2D.Double getPosition();
	/**
	 * This method sets the position.
	 *
	 * @param point the position
	 */
	public void setPosition(Point2D.Double point);

	/**
	 * This method get the velocity on x-axis.
	 *
	 * @return returns the velocity on x-axis
	 */
	public double getXVelocity();
	/**
	 * This method sets the velocity on x-axis.
	 *
	 * @param xVelocity the velocity on x-axis
	 */
	public void setXVelocity(double xVelocity);

	/**
	 * This method get the velocity on y-axis.
	 *
	 * @return returns the velocity on y-axis
	 */
	public double getYVelocity();
	/**
	 * This method sets the velocity on y-axis.
	 *
	 * @param yVelocity the velocity on y-axis
	 */
	public void setYVelocity(double yVelocity);

	/**
	 * This method get the directoin on x-axis.
	 *
	 * @return returns the direction on x-axis
	 */
	public double getXDirection();
	/**
	 * This method sets the directoin on x-axis.
	 *
	 * @param xDirection the direction on x-axis
	 */
	public void setXDirection(double xDirection);
	/**
	 * This method get the directoin on y-axis.
	 *
	 * @return returns the direction on y-axis
	 */
	public double getYDirection();
	/**
	 * This method sets the directoin on y-axis.
	 *
	 * @param yDirection the direction on y-axis
	 */
	public void setYDirection(double yDirection);
	/**
	 * This method gets the direction as a point.
	 *
	 * @return returns the direction as a point
	 */
	public Point2D.Double getDirection();
	/**
	 * This method sets the direction from a point.
	 *
	 * @param location a point containing the direction
	 */
	public void setDirection(Point2D location);
	/**
	 * This method sets the directoin toward a point.
	 *
	 * @param location a point containing the location of a point to set direction to
	 */
	public void setDirectionToward(Point2D location);

	/**
	 * This method gets the center on x-axis.
	 *
	 * @return returns the center on x-axis
	 */
	public double getXCenter();
	/**
	 * This method get the center on y-axis.
	 *
	 * @return returns the center on y-axis
	 */
	public double getYCenter();
	/**
	 * This method gets the center as a point.
	 *
	 * @return returns the center as a point
	 */
	public Point2D.Double getCenter();

	/**
	 * This method gets the sprite (BufferedImage).
	 *
	 * @return returns the sprite
	 */
	public BufferedImage getImage();
	/**
	 * This method sets the sprite (BufferedImage).
	 *
	 * @param image the BufferedImage to be set as sprite
	 */
	public void setImage(BufferedImage image);
	/**
	 * This method sets the sprite given the location of the image.
	 *
	 * @param imageLocation the path to the image
	 */
	public void setImageLocation(String imageLocation);
	/**
	 * This method gets the width of the sptie.
	 *
	 * @return returns the width of the sprite
	 */
	public double getWidth();
	/**
	 * This method gets the height of the sprite.
	 *
	 * @return returns the height of the sprite
	 */
	public double getHeight();

	/**
	 * This method returns the number of HP.
	 *
	 * @return returns number of HP
	 */
	public int getHealthPoints();
	/**
	 * This method sets the number of HP.
	 *
	 * @param healthPoints the number to set the HP as
	 */
	public void setHealthPoints(int healthPoints);
	/**
	 * The method informs the object that it's been hit to take appropriate action. Like reducing HP.
	 *
	 */
	public void gotHit();
	/**
	 * This method checks whether the object is dead or not.
	 *
	 * @return returns whether the object is dead or not
	 */
	public boolean isDead();
	/**
	 * This method makes the object dead. It's called when a situation calls for immediate death.
	 *
	 */
	public void makeDead();
}
