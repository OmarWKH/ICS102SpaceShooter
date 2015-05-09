import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public interface GameObject {
	public void draw(Graphics2D g2d);
	//downside, had "setters" as protected, can't now
	//do I want this interface thing more than I want the access thing?
	public void move();
	public void movePosition();
	public void moveDirection();

	public void initializeLocation();

	public double getXPosition();
	public void setXPosition(double xPosition);
	public double getYPosition();
	public void setYPosition(double yPosition);
	public Point2D.Double getPosition();
	public void setPosition(Point2D.Double point);

	public double getXVelocity();
	public void setXVelocity(double xVelocity);

	public double getYVelocity();
	public void setYVelocity(double yVelocity);

	public double getXDirection();
	public void setXDirection(double xDirection);
	public double getYDirection();
	public void setYDirection(double yDirection);
	public Point2D.Double getDirection();
	public void setDirection(Point2D location);
	public void setDirectionToward(Point2D location);

	public double getXCenter();
	public double getYCenter();
	public Point2D.Double getCenter();


	public BufferedImage getImage();
	public void setImage(BufferedImage image);
	public void setImageLocation(String imageLocation);
	public double getWidth();
	public double getHeight();

	public int getHealthPoints();
	public void setHealthPoints(int helathPoints);
	public void gotHit();
	public boolean isDead();
	public void makeDead();
}
