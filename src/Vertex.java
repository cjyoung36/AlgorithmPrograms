/**
 * CSC 331 Algorithm Analysis
 * Project 3: Shortest Path
 * 
 * This object represents a Vertex within a graph.
 * 
 * One enhancement over a basic vertex is a field for the distance from the source
 * so that we can use it in a priority queue in Dijkstra's algorithm and the priority
 * queue will automatically keep the smallest distance vertex at the front of the queue.
 * Notice that this class implements Comparable, along with a CompareTo method, which
 * is what allows this to happen.
 * 
 * @author Chris Young
 * @version 2021-01-26
 */
public class Vertex implements Comparable<Vertex> {
	private String myName;

	// The distance variable keeps track of distance from the source vertex for
	// Dijkstra's Algorithm.
	private double distance;

	public Vertex(String name) {
		myName = name;
		distance = 0.0;
	}

	public String getName() {
		return myName;
	}

	public void setDistance(double dist) {
		distance = dist;
	}
	
	public double getDistance() {
		return distance;
	}

	/**
	 * This method will be automatically used by a priority queue to keep the 
	 * minimum distance vertex at the front of the queue (for Dijkstra's algorithm).
	 * 
	 * @param v another vertex to compare to this one
	 * @return -1 when this vertex's distance is smaller, 
	 *          1 when the other one is smaller
	 *          and 0 when they're the same.
	 */
	public int compareTo(Vertex v) {
		if (this.distance < v.distance) {
			return -1;
		}
		else if (this.distance > v.distance) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
