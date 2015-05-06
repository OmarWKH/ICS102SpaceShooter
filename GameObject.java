import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public abstract class GameObject {
	//float
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private double xDirection;
	private double yDirection;
	public JPanel panel;
	private BufferedImage image;
	private AffineTransform affineTransform;
	private int helathPoints;
	//dead
	//collision

	public GameObject(int helathPoints) {
		this.setHealthPoints(helathPoints);
		affineTransform = new AffineTransform();
	}
	/*
	public GameObject() {
		this(0, 0, null, 1);
	}
	*/

	//this won't work unless Directions are done in move and a image is passed
	public void draw(Graphics2D g2d) {
		double xCenter1 = xPosition;
		double yCenter1 = yPosition;
		double xCenter = xPosition + image.getWidth();
		double yCenter = yPosition - image.getHeight();
		System.out.println(xCenter + ".." + xCenter1 + ".." + xPosition);

		affineTransform.setToIdentity();
		affineTransform.rotate(xDirection, yDirection, xCenter, yCenter);
		affineTransform.translate(xCenter, yCenter);
		g2d.drawImage(image, affineTransform, null);
		g2d.setColor(Color.RED);
		g2d.drawOval((int)xCenter, (int)yCenter, 10, 10);
		g2d.setColor(Color.GREEN);
		try {
			g2d.drawOval(panel.getWidth()/2, panel.getHeight()/2, 10, 10);
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.exit(0);
		}
	}

	public abstract void move();
	//if panel is scaled location won't update accordingly
	public abstract void initializeLocationOn(JPanel panel);

	public double getXPosition() {
		return this.xPosition;
	}
	protected void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getYPosition() {
		return this.yPosition;
	}
	protected void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getXVelocity() {
		return this.xVelocity;
	}
	protected void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getYVelocity() {
		return this.yVelocity;
	}
	protected void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getXDirection() {
		return this.xDirection;
	}
	protected void setXDirection(double xDirection) {
		this.xDirection = xDirection;
	}

	public double getYDirection() {
		return this.yDirection;
	}
	protected void setYDirection(double yDirection) {
		this.yDirection = yDirection;
	}

	public BufferedImage getImage() {
		return this.image;
	}
	protected void setImage(BufferedImage image) {
		this.image = image;
	}
	protected void setImageLocation(String imageLocation) {
		try {
			image = ImageIO.read(new File(imageLocation));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public int getHealthPoints() {
		return this.helathPoints;
	}
	protected void setHealthPoints(int helathPoints) {
		this.helathPoints = helathPoints;
	}
}
