package engine;

import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import java.util.Collections;

/**
 * This class is used to get a number of random unique locations in an area.
 * 
 * @author Omar Khashoggi
 */
public class RandomLocation {
	private LinkedList<Point2D.Double> locations;
	private ListIterator<Point2D.Double> iterator;
	private int numberOfLocations;
	private int width;
	private int height;
	private double exludingFactor;
	private boolean isForward = true;

	/**
	 * This constructor intitlizes a RandomLocation with thrice the number of lcoations in the specified area (width and height) exluding an area according to the exluding factor.
	 *
	 */
	RandomLocation(int width, int height, int numberOfLocations, double exludingFactor) {
		this.width = width;
		this.height = height;
		this.numberOfLocations = 3*numberOfLocations;
		this.exludingFactor = exludingFactor;
		this.populateRandomUniqueList();
		iterator = this.locations.listIterator(0);
	}

	/**
	 * This constructos intitlizes a RandomLocation with a number of lcoations in the specified area (width and height) exluding an area according to the default exlusing factor 0.5.
	 *
	 */
	RandomLocation(int width, int height, int numberOfLocations) {
		this(width, height, numberOfLocations, 0.05);
	}

	/**
	 * This methods adds unique locations to the list and shuffles them to randomize, location are within the area exluding a precentage from the outer boundries.
	 * 
	 */
	private void populateRandomUniqueList() {
		double minX = exludingFactor*width;
		double maxX = width - exludingFactor*width;
		double minY = exludingFactor*height;
		double maxY = height - exludingFactor*height;
		double distanceX = maxX - minX;
		double distanceY = maxY - minY;

		double stepX = distanceX/numberOfLocations;
		double stepY = distanceY/numberOfLocations;

		this.locations = new LinkedList<>();
		ListIterator<Point2D.Double> addingIterator = locations.listIterator(0);
		for (double x = minX; x < maxX; x += stepX) {
			for (double y = minY; y < maxY; y+= stepY) {
				addingIterator.add(new Point2D.Double(x, y));
			}
		}
		//still not sure I'll always get n, area not touched
		Collections.shuffle(locations);
	}

	/**
	 * This method is used to get the next random unique location
	 * If the list is exhausted it goes the other way.
	 *
	 * @return returns a random unique location as Point2D.Double
	 */
	public Point2D.Double next() {
		if (!iterator.hasNext()) {
			setForward(false);
			//this.populateRandomUniqueList();
		} else if (!iterator.hasPrevious()) {
			setForward(true);
		}

		if (isForward) {
			return iterator.next();
		} else {
			return iterator.previous();
		}
	}

	private void setForward(boolean forwardOrNot) {
		this.isForward = forwardOrNot;
	}
}
