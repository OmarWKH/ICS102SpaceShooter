import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import java.util.Random;
import java.util.Collections;

public class RandomLocation {
	private LinkedList<Point2D.Double> locations;
	private ListIterator<Point2D.Double> iterator;
	private int numberOfLocations;
	private int width;
	private int height;
	private double exludingFactor;

	RandomLocation(int width, int height, int numberOfLocations, double exludingFactor) {
		this.width = width;
		this.height = height;
		this.numberOfLocations = numberOfLocations;
		this.exludingFactor = exludingFactor;
		this.populateRandomUniqueList();
		iterator = this.locations.listIterator(0);
	}

	RandomLocation(int width, int height, int numberOfLocations) {
		this(width, height, numberOfLocations, 0.05);
	}

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

	public Point2D.Double next() {
		if (!iterator.hasNext()) {
			this.populateRandomUniqueList();
		}
		return iterator.next();
	}
}
