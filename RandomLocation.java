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

	RandomLocation(int width, int height, int numberOfLocations) {
		this.numberOfLocations = numberOfLocations;
		this.width = width;
		this.height = height;
		this.populateRandomUniqueList();
		iterator = this.locations.listIterator(0);
	}

	private void populateRandomUniqueList() {
		double stepX = this.width/numberOfLocations;
		double stepY = this.height/numberOfLocations;
		this.locations = new LinkedList<>();

		ListIterator<Point2D.Double> addingIterator = locations.listIterator(0);
		for (int x = 0, y = 0; (x < width) && (y < height); x += stepX, y += stepY) {
			addingIterator.add(new Point2D.Double(x, y));
		}

		//still not sure I'll always get n, area not touched
		Random randomizer = new Random();
		Collections.shuffle(locations, randomizer);
		randomizer = new Random();
		Collections.shuffle(locations, randomizer);
	}

	public Point2D.Double next() {
		if (!iterator.hasNext()) {
			this.populateRandomUniqueList();
		}
		return iterator.next();
	}
/*
	private static LinkedList<E> shuffle(LinkedList<E> list) {
		//keep copy
		mergeShuffle(list, 0, list.size()-1);
	}
*/
	/*
	private static LinkedList<E> mergeShuffle(LinkedList<E> list, int start, int end) {
		if (start < end) {
			int midPoint = (end + start)/2;

			mergeShuffle(list, start, midPoint);
			mergeShuffle(list, midPoint+1, end);

			merge(start, midPoint, end);
		}

		//ListIterator iterator = list.listIterator(start);
		
		if (shuffle1 > shuffle2) {
			iterator.add(shuffle1);
			iterator.add(suffle2);
		} else {
			iterator.add(suffle2);
			iterator.add(shuffle1);
		}
		*/
/*
	}

	temp merge(start, mid, end) {
		temp;
		for (int i = start, i <= end; i++) {
			temp[i] = list[i];
		}

		int startOf1 = start;
		int startOf2 = mid + 1;
		int c = start;
		while (startOf1 <= mid && startOf2 <= end) {
			if (list[startOf1] < list[startOf2]) {
				temp[c] = list[startOf1];
				startOf1++;
			} else {
				temp[c] = list[startOf2];
				startOf2++;
			}
			c++;
		}

		if (startOf1 <= mid) {
			temp[c] = list[startOf1];
			startOf1++;
			c++;
		}

		return temp;
	}
*/
}
