import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public interface GameObject {
	public void draw(Graphics2D g2d);
	//downside, had "setters" as protected, can't now
	//do I want this interface thing more than I want the access thing?
	public void move();
	public void initializeLocationOn(JPanel panel);

	public double getXPosition();
	public void setXPosition(double xPosition);

	public double getYPosition();
	public void setYPosition(double yPosition);

	public double getXVelocity();
	public void setXVelocity(double xVelocity);

	public double getYVelocity();
	public void setYVelocity(double yVelocity);

	public double getXDirection();
	public void setXDirection(double xDirection);

	public double getYDirection();
	public void setYDirection(double yDirection);

	public double getXCenter();
	public double getYCenter();

	public BufferedImage getImage();
	public void setImageLocation(String imageLocation);

	public int getHealthPoints();
	public void setHealthPoints(int helathPoints);
}
