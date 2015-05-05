import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.KeyEvent;

public class PlayerShip {
	//float
	private double x;
	private double y;
	private double xv;
	private double yv;

	public PlayerShip() {

	}

	public void move() {
		x += xv;
		y += yv;
	}

	/*
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_W) {
			xv++;
		}
		System.out.println(x + ".." + y);
		System.out.println(xv + ".." + yv);
	}
	*/

	//limit access to pressing W, password as argument? lol
	public void moveUp() {
		yv++;
	}

	public void paint(Graphics g) {
		System.out.println(x + ".." + y);
		System.out.println(xv + ".." + yv);
	}
}