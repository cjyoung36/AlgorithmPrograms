import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileNotFoundException;

/**
 * CSC 331 Algorithm Analysis
 * Project 3: Shortest Path
 * Unit tests
 * 
 * @author Scott Spurlock
 * @version 2021-01-29
 */
public class UnitTests {

	/***********************************************
	 * Test Graph class
	 */

	@Test
	public void testRemoveEdge() {
		Graph g = new Graph();
		Vertex a = new Vertex("A");
		g.addVertex(a);
		Vertex b = new Vertex("B");
		g.addVertex(b);
		Vertex c = new Vertex("C");
		g.addVertex(c);
		
		g.addEdge(a, b, 2);
		g.addEdge(a, c, 3);
		g.addEdge(c, b, 4);

		g.removeEdge(a, b);

		boolean result = !g.edgeExists(a, b)
				&& g.edgeExists(a, c)
				&& g.edgeExists(c, b);
		
		assertTrue("Graph.removeEdge(): edge not correctly removed.", result);
	}

	@Test
	public void testRemoveVertex() {
		Graph g = new Graph();
		Vertex a = new Vertex("A");
		g.addVertex(a);
		Vertex b = new Vertex("B");
		g.addVertex(b);
		Vertex c = new Vertex("C");
		g.addVertex(c);
		
		g.addEdge(a, b, 2);
		g.addEdge(a, c, 3);
		g.addEdge(c, b, 4);

		g.removeVertex(c);

		boolean result = g.getVertices().contains(a) &&
						 g.getVertices().contains(b) &&
						!g.getVertices().contains(c) &&
						 g.edgeExists(a, b) && 
						!g.edgeExists(a, c);

		assertTrue("Graph.removeVertex(): vertex not correctly removed.", result);
	}
	
	@Test
	public void testNumEdges() {
		Graph g = new Graph();
		Vertex a = new Vertex("A");
		g.addVertex(a);
		Vertex b = new Vertex("B");
		g.addVertex(b);
		Vertex c = new Vertex("C");
		g.addVertex(c);
		
		boolean result = g.numEdges() == 0;
		
		g.addEdge(a, b, 2);
		result = result && g.numEdges() == 1;
		
		g.addEdge(a, c, 3);
		result = result && g.numEdges() == 2;
		
		g.addEdge(c, b, 4);
		result = result && g.numEdges() == 3;
		
		assertTrue("Graph.numEdges(): number of edges not correctly calculated.", result);
	}
	
	@Test
	public void testInDegree() {
		Graph g = new Graph();
		Vertex a = new Vertex("A");
		g.addVertex(a);
		Vertex b = new Vertex("B");
		g.addVertex(b);
		Vertex c = new Vertex("C");
		g.addVertex(c);
		
		boolean result = g.inDegree(a) == 0;
		
		g.addEdge(a, b, 2);
		result = result && g.inDegree(a) == 0;
		result = result && g.inDegree(b) == 1;
		
		g.addEdge(b, c, 3);
		result = result && g.inDegree(a) == 0;
		result = result && g.inDegree(b) == 1;
		result = result && g.inDegree(c) == 1;
		
		g.addEdge(c, b, 4);
		result = result && g.inDegree(a) == 0;
		result = result && g.inDegree(b) == 2;
		result = result && g.inDegree(c) == 1;
		
		assertTrue("Graph.numEdges(): inDegree not correctly calculated.", result);
	}
	
	
	@Test
	public void testGraphToString() {
		Graph g = new Graph();
		Vertex a = new Vertex("A");
		g.addVertex(a);
		Vertex b = new Vertex("B");
		g.addVertex(b);
		Vertex c = new Vertex("C");
		g.addVertex(c);
		
		g.addEdge(a, b, 2);
		g.addEdge(a, c, 3);
		g.addEdge(c, b, 4);
		
		boolean result = true;
		result = result && g.toString().startsWith("This graph has 3 vertices and 3 edges:\n");
		result = result && g.toString().contains("A in degree: 0, out degree: 2\n - Edges to: B, C\n");
		result = result && g.toString().contains("C in degree: 1, out degree: 1\n - Edges to: B\n");
		result = result && g.toString().contains("B in degree: 2, out degree: 0\n - Edges to: (none)\n");
		
		assertTrue("Graph.toString(): string not correctly formatted.", result);
	}
	
	/***********************************************
	 * Test GraphMaker class
	 */

	@Test
	public void testMakeGraphFromFile() {
		GraphMaker gm = new GraphMaker();
		Graph g = null;
		
		try {
			g = gm.makeGraphFromFile("SimpleGraph.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Error running makeGraphFromFile(); SimpleGraph.csv not found.");
		}

		Vertex a = g.getVertexByName("A");
		Vertex b = g.getVertexByName("B");
		Vertex c = g.getVertexByName("C");
		Vertex d = g.getVertexByName("D");
		
		boolean result = g.getVertices().size() == 4
				&& a != null && b != null && c != null && d != null
				&& g.getAdjacentVertices(a).contains(b) && g.getAdjacentVertices(a).contains(d)
				&& g.getAdjacentVertices(b).contains(a) && g.getAdjacentVertices(b).contains(c) && g.getAdjacentVertices(b).contains(d)
				&& g.getAdjacentVertices(c).contains(b) && g.getAdjacentVertices(c).contains(d)
				&& g.getAdjacentVertices(d).contains(a) && g.getAdjacentVertices(d).contains(c) && g.getAdjacentVertices(d).contains(b)
				&& g.getWeight(a, b) == 3 && g.getWeight(a, d) == 5 && g.getWeight(a, c) == Double.POSITIVE_INFINITY && g.getWeight(a, a) == Double.POSITIVE_INFINITY
				&& g.getWeight(d, a) == 5 && g.getWeight(d, b) == 1 && g.getWeight(d, c) == 1 && g.getWeight(d, d) == Double.POSITIVE_INFINITY;
		
		assertTrue("GraphMaker.makeGraphFromFile(): graph not correctly created.", result);
	}
	
	
	/***********************************************
	 * Test Dijkstras class
	 */

	@Test
	public void testShortestPath() {
		GraphMaker gm = new GraphMaker();
		Graph g = null;
		
		try {
			g = gm.makeGraphFromFile("SimpleGraph.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Error running makeGraphFromFile(); SimpleGraph.csv not found.");
		}

		Vertex a = g.getVertexByName("A");
		Vertex b = g.getVertexByName("B");
		Vertex c = g.getVertexByName("C");
		Vertex d = g.getVertexByName("D");
				
		Dijkstras dijk = new Dijkstras();
		dijk.shortestPath(g, a);
		
		boolean result = true;
		result = result && a.getDistance() == 0;
		result = result && b.getDistance() == 3;
		result = result && c.getDistance() == 5;
		result = result && d.getDistance() == 4;

		assertTrue("Dijkstras.shortestPath(): Incorrect result.", result);
	}
	

	@Test
	public void testRunShortestPath() {
		GraphMaker gm = new GraphMaker();
		Graph g = null;
		
		try {
			g = gm.makeGraphFromFile("SimpleGraph.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Error running makeGraphFromFile(); SimpleGraph.csv not found.");
		}

		Dijkstras dijk = new Dijkstras();
		String out = dijk.runShortestPath(g, "A", "C");
		
		boolean result = out.equals("The shortest distance is 5.0\n" + 
				"A -> B -> D -> C");

		assertTrue("Dijkstras.shortestPath(): Incorrect result.", result);
	}
	
}
