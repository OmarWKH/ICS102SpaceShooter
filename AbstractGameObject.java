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
	public static JPanel panel; // = GameTier.gameWindow.getInGamePanel();
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

	@Override
	public abstract void movePosition();
	@Override
	public abstract void moveDirection();

	//if panel is scaled location won't update accordingly
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
	//set/get (x, y)
	//set/get (point)
	//set/get (x),(y)

	@Override
	public void setDirection(Point2D location) {
		this.setXDirection(location.getX());
		this.setYDirection(location.getY());
	}
	@Override
	public Point2D.Double getDirection() {
		return new Point2D.Double(this.getXDirection(), this.getYDirection());
	}

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

	@Override
	public void gotHit() {
		this.setHealthPoints(this.getHealthPoints() - 1);
		if (isDead()) {
			GameTier.gameEngine.manDown(this);
		}
	}

	@Override
	public boolean isDead() {
		return (getHealthPoints() <= 0);
	}

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

	private static Point2D.Double normalize(Point2D location1, Point2D location2) {
		double xComponent = (location1.getX() - location2.getX());
		double yComponent = (location1.getY() - location2.getY());
		double length = Math.sqrt( Math.pow(xComponent,2) + Math.pow(yComponent,2) );
		return new Point2D.Double(xComponent/length, yComponent/length);
	}
}
