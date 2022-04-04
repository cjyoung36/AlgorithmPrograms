/*
 * CSC 331 Algorithm Analysis
 * Project 3: Shortest Path
 * 
 * Includes two methods, shortestPath, which implements Dijkstra's Algorithm,
 * and runShortestPath, which calls shortestPath and formats a string to
 * display the distance and path to a particular vertex.
 * 
 * @author Chris Young
 * @version 2021-01-26
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class Dijkstras {

	/**
	 * 	Given a graph and source vertex, run Dijkstra's algorithm.
	 * 
	 * @param graph - the object holding the graph.
	 * @param source - the object holding the source vertex.
	 * @return a HashMap linking each vertex to its parent.
	 */
	public HashMap<Vertex, Vertex> shortestPath(Graph graph, Vertex source) {
		// This HashMap associates a vertex with its parent vertex.
		// It should get filled in and then returned at the end of the method.
		HashMap<Vertex, Vertex> parents = new HashMap<Vertex, Vertex>();

		// Initialize the distance to all the vertices in the graph to infinity.
		// (Note that each vertex object should store its own distance from the 
		// source using its setDistance method.)
		// Initialize the distance to the source vertex to 0.
		// Also initialize the parents HashMap so that each vertex has a parent
		// of null.

		// get all vertices from the graph
		Collection<Vertex> vertices = graph.getVertices();
		// add all vertices to parents, set vertex parent to null
		for (Vertex v : vertices) {
			parents.put(v, null);
			if (source == v) {
				v.setDistance(0.0);
			} else {
				v.setDistance(Double.POSITIVE_INFINITY);
			}
		}

		// Make a PriorityQueue (imported above in Java.Util.PriorityQueue)
		// of DijkstraVertex objects and add all the vertices.

		// Q keeps track of undiscovered / unfinished vertices
		PriorityQueue<Vertex> Q = new PriorityQueue<>();

		// add all vertices from the collection into Q
		for (Vertex v : vertices) {
			Q.add(v);
		}

		while (!Q.isEmpty()) {
			// get next closest vertex from queue
			Vertex curr = Q.remove();
			// get adjacent vertices 
			for(Vertex v : graph.getAdjacentVertices(curr)) {
				Vertex adj = v;

				if(adj.getDistance() > curr.getDistance() + graph.getWeight(curr, v)){
					Q.remove(adj);
					adj.setDistance(curr.getDistance() + graph.getWeight(curr, v)); 
					Q.add(adj);
					parents.put(adj, curr);
				}
			}
		}
		
		return parents;

	}



	/**
	 * Given a graph and the names of the start and end vertices,
	 * run Dijkstra's algorithm (shortestPath method above), then
	 * work backwards from the end vertex to find the path (sequence
	 * of vertices) from start to end.
	 * 
	 * The output string to be returned should be formatted like this,
	 * e.g.:
	 * The shortest distance is 0.605
	 * RTR1 -> RTR4 -> RTR3 -> RTR5
	 * 
	 * @param graph - the object holding the graph
	 * @param startVertexName - a string indicating the name of the vertex
	 * 			(to be found within the graph) to use as the source.
	 * @param endVertexName - a string indicating the name of the vertex
	 *          (to be found within the graph) to use as the destination.
	 * @return a String that displays the distance and sequence of vertices
	 *          on the shortest path.
	 */
	public String runShortestPath(Graph graph, String startVertexName, String endVertexName) {
		String out = "";

		Vertex start = graph.getVertexByName(startVertexName);
		Vertex end = graph.getVertexByName(endVertexName);
		
		HashMap<Vertex, Vertex> parents = shortestPath(graph, start);
		Stack<Vertex> S = new Stack<>();
		
		double sum = 0.0;
		
		while (end != start) {
			// start at the end
			// find end's parent
			Vertex p = parents.get(end);
			// find edge weight from end to its parent
			double edge_weight = graph.getWeight(p, end);
			// sum
			sum += edge_weight;
			// log traversed nodes into stack
			S.push(end);
			// make parent the new end
			end = p;
		}
		
		String sum_string = Double.toString(sum);
		sum_string = "The shortest distance is ".concat(sum_string).concat("\n");
		out = out.concat(start.getName());

		while (!S.isEmpty()) {
			Vertex next = S.pop();
			String name = next.getName();
			out = out.concat(" -> " + name);
		}

		out = sum_string.concat(out);
		
		// Return the output string.
		return out;
	}

}
