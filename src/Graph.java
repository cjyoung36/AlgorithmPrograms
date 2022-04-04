import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * CSC 331 Algorithm Analysis
 * Project 3: Shortest Path
 * 
 * This class defines a directed, weighted graph.
 * It tracks a set of vertices, along with which other vertices
 * they are adjacent to.
 * 
 * @author Chris Young
 * @version 2021-01-26
 */
public class Graph {

	// Adjacency list to hold vertices and edges.
	// It is implemented as a HashMap, where the key is a particular Vertex
	// object and the value associated with that key is another map of
	// adjacent Vertex objects and the associated edge weight.
	private HashMap<Vertex, HashMap<Vertex, Double>> adjList;

	/**
	 * Constructor
	 */
	public Graph() {
		adjList = new HashMap<Vertex, HashMap<Vertex, Double>>();
	}

	/**
	 * Get a collection of the vertices in the graph.
	 *
	 * @return a collection of the vertices in this Graph.
	 */
	public Set<Vertex> getVertices() {
		return adjList.keySet();
	}

	/**
	 * Find a vertex in the graph whose name matches the given name.
	 * 
	 * @param name - the vertex name to find.
	 * @return the vertex with the matching name.
	 *     If no vertices have a matching name, return null.
	 *     If multiple vertices have a matching name, just return one.
	 */
	public Vertex getVertexByName(String name) {
		for (Vertex v: adjList.keySet()) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Get the number of vertices in this Graph.
	 *
	 * @return the number of vertices in this Graph.
	 */
	public int numVertices(){
		return adjList.size();
	}

	/**
	 * Add the edge (u, v) to this Graph with weight w. 
	 * 
	 * @param u  a vertex in this Graph.
	 * @param v  a vertex in this Graph.
	 * @param w the weight of this edge.
	 */
	public void addEdge(Vertex u, Vertex v, double w) {
		adjList.get(u).put(v, w);
	}

	/**
	 * Determine if the edge (u, v) exists in this Graph.
	 *
	 * @param u  a vertex in this Graph.
	 * @param v  a vertex in this Graph.
	 *
	 * @return true if edge (u, v) exists in this Graph and
	 *         false otherwise.
	 */
	public boolean edgeExists(Vertex u, Vertex v) {
		return  adjList.get(u).keySet().contains(v);
	}

	/**
	 * Get the weight associated with edge (u, v).
	 *
	 * @param u  a vertex in this Graph.
	 * @param v  a vertex in this Graph.
	 *
	 * @return weight of edge (u, v) if exists in this Graph and infinity 
	 * otherwise.
	 */
	public double getWeight(Vertex u, Vertex v) {
		if (edgeExists(u, v)) {
			return adjList.get(u).get(v);
		}
		return Double.POSITIVE_INFINITY;
	}

	/**
	 * Add a new vertex to this Graph.
	 * 
	 * @param v - the Vertex to be added.
	 */
	public void addVertex(Vertex v) {
		adjList.put(v, new HashMap<Vertex, Double>());
	}

	/**
	 *
	 * Get a collection of the vertices adjacent to the 
	 * given vertex.
	 * 
	 * @param v a vertex in this Graph.
	 */
	public Collection<Vertex> getAdjacentVertices(Vertex v) {
		return adjList.get(v).keySet();
	}

	/**
	 * Find the number of edges from the given one to other vertices.
	 *
	 * @param v a vertex in this Graph.
	 *
	 * @return the number of edges in this Graph to other vertices from v.
	 */
	public int outDegree(Vertex v) {
		return getAdjacentVertices(v).size();
	}

	// ========================================================================
	// TODO: fill in the following methods.
	// =============================================

	/**
	 * Remove the edge (u, v) from this Graph.
	 *
	 * @param u  a vertex in this Graph.
	 * @param v  a vertex in this Graph.
	 */
	public void removeEdge(Vertex u, Vertex v) {
		if (edgeExists(u, v)) {
			adjList.get(u).remove(v);
		}
	}

	/**
	 * Remove vertex v and all related edges from this Graph. 
	 *
	 * @param v the Vertex in this Graph.
	 */
	public void removeVertex(Vertex v) {
		for (Vertex u : adjList.keySet()) {
			if (edgeExists(u, v)) {
				removeEdge(u,v);
			}

		}
		adjList.remove(v);
	}

	/**
	 * Get the number of edges in this Graph.
	 *
	 * @return the number of edges in this Graph.
	 */
	@SuppressWarnings("unused")
	public int numEdges() {
		int edges = 0;

		for (Vertex u : adjList.keySet()) {
			HashMap<Vertex, Double> adjVertices = adjList.get(u);
			for (Vertex v : adjVertices.keySet()) {
				edges++;
			}
		}

		return edges;
	}

	/**
	 * Find the number of edges from other vertices to the given one.
	 *
	 * @param v a vertex in this Graph.
	 *
	 * @return the number of edges in this Graph from other vertices to v.
	 */
	public int inDegree(Vertex v) {
		int count = 0;

		for (Vertex u : adjList.keySet()) {
			HashMap<Vertex, Double> adjVertices = adjList.get(u);
			if (adjVertices.keySet().contains(v)) {
				count++;
			}
		}

		return count;
	}

	/**
	 *  Construct a String representation of the graph.
	 *  (Note that the method should not print anything.)
	 *  
	 *  Example of a graph string:
	 *  
	 *  This graph has 4 vertices and 10 edges:
	 *  A in degree: 2, out degree: 2
	 *   - Edges to: B, D
	 *  C in degree: 2, out degree: 2
	 *   - Edges to: B, D
	 *  B in degree: 3, out degree: 3
	 *   - Edges to: A, C, D
	 *  D in degree: 3, out degree: 3
	 *   - Edges to: A, C, B
	 *
	 * @return a string representing the graph.
	 */
	@Override
	public String toString() {
		String out = "";
		out = out.concat("This graph has " + numVertices() + " vertices and " + numEdges() + " edges:\n");

		for (Vertex u : adjList.keySet()) {
			out = out.concat(u.getName() + " in degree: " + inDegree(u) + ", out degree: " + outDegree(u) + "\n");
			// iterate through each vertex's adjList
			out = out.concat(" - Edges to: ");
			if (outDegree(u) == 0) {
				out = out.concat("(none)\n");
			} else {
				Set<Vertex> adjVertices = adjList.get(u).keySet();
				int size = adjVertices.size();
				int cntr = 0;
				for (Vertex v : adjVertices) {
					if (cntr < size - 1) {
						out = out.concat(v.getName() + ", ");
						cntr++;
					} else {
						out = out.concat(v.getName());
					}
					
				}
				out = out.concat("\n");
			}

		}

		return out;
	}

}