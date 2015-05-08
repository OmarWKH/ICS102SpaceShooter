import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.geom.Point2D;

public abstract class AbstractGameObject implements GameObject {
	//float
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private double xDirection;
	private double yDirection;
	//even if static, a method to initlize it would make it less, hardcody
	public static JPanel panel = GameTier.gameWindow.getInGamePanel();
	private BufferedImage image;
	private AffineTransform affineTransform;
	private int helathPoints;
	//dead
	//collision

	public AbstractGameObject(int helathPoints, String imageLocation) {
		this.setHealthPoints(helathPoints);
		this.setImageLocation(imageLocation);
		affineTransform = new AffineTransform();
	}
	
	public AbstractGameObject() {
		this(1, "bullet.png");
	}
	

	//this won't work unless Directions are done in move and a image is passed
	@Override
	public void draw(Graphics2D g2d) {
		//double xCenter = xPosition + image.getWidth()/2;
		//double yCenter = yPosition + image.getHeight()/2;
		//System.out.println(image.getWidth() + ".." + image.getHeight());

		affineTransform.setToIdentity();
		affineTransform.rotate(xDirection, yDirection, this.getXCenter(), this.getYCenter());
		affineTransform.translate(xPosition, yPosition);
		g2d.drawImage(image, affineTransform, null);
		g2d.setColor(Color.RED);
		g2d.drawOval((int)xPosition, (int)yPosition, 10, 10);
		g2d.setColor(Color.GREEN);
		//g2d.drawOval(panel.getWidth()/2, panel.getHeight()/2, 10, 10);
		g2d.drawOval((int)this.getXCenter(), (int)this.getYCenter(), 10, 10);
	}

	@Override
	public void move() {
		this.movePosition();
		this.moveDirection();
	}

	public abstract void movePosition();
	public abstract void moveDirection();

	//if panel is scaled location won't update accordingly
	@Override
	public abstract void initializeLocation();

	public double getXPosition() {
		return this.xPosition;
	}
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getYPosition() {
		return this.yPosition;
	}
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public void setPosition(Point2D.Double point) {
		this.setXPosition(point.getX());
		this.setYPosition(point.getY());
	}

	public double getXVelocity() {
		return this.xVelocity;
	}
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getYVelocity() {
		return this.yVelocity;
	}
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getXDirection() {
		return this.xDirection;
	}
	public void setXDirection(double xDirection) {
		this.xDirection = xDirection;
	}

	public double getYDirection() {
		return this.yDirection;
	}
	public void setYDirection(double yDirection) {
		this.yDirection = yDirection;
	}

	public double getXCenter() {
		return this.getXPosition() + this.getImage().getWidth()/2;
	}
	public double getYCenter() {
		return this.getYPosition() + this.getImage().getHeight()/2;
	}

	public BufferedImage getImage() {
		return this.image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public void setImageLocation(String imageLocation) {
		try {
			image = ImageIO.read(new File(imageLocation));
		} catch (IOException ioe) {
			System.out.println("Failed to load image at: " + imageLocation);
			ioe.printStackTrace();
			System.exit(0);
		}
	}

	public int getHealthPoints() {
		return this.helathPoints;
	}
	public void setHealthPoints(int helathPoints) {
		this.helathPoints = helathPoints;
	}
}
