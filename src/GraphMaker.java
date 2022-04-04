import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CSC 331 Algorithm Analysis
 * Project 3: Shortest Path
 * The GraphMaker handles making a graph by reading from a file.
 * 
 * @author Scott Spurlock
 * @version 2021-01-26
 */
public class GraphMaker {

	/**
	 * This method takes in a file name and creates the corresponding graph.
	 * The file should be formatted as follows:
	 * Line 1 contains the names of V vertices, separated by commas.
	 * Lines 2 through V+1 contain a table of edge weights (like an adjacency
	 * matrix), separated by commas. Zeros indicate that no edge exists.
	 * 
	 * Below is an example of a graph with 4 vertices labeled A through D.
	 * vertex A has 2 edges, one to vertex B with a weight of 3 and one to
	 * vertex D with a weight of 5.
	 * 
	 * A,B,C,D
	 * 0,3,0,5
	 * 3,0,6,1
	 * 0,6,0,1
	 * 5,1,1,0
	 * 
	 * @param fileName
	 * @return Graph object constructed based on the input file.
	 * @throws FileNotFoundException
	 */
	public Graph makeGraphFromFile(String fileName) throws FileNotFoundException {
		// Create a new Graph and read from the file to add
		// vertices and edges.
		//
		// Notes:
		// - you can use a scanner to read in a whole line from the file, and
		//   then use the split method to convert the comma-separated items
		//   into an array.
		//
		// - you can create a new Vertex like this:
		//   Vertex v = new Vertex("Some Name");
		//
		//   and add it to a Graph like this:
		//   graph.addVertex(v);
		//
		//   and, given two Vertex objects, u and v, you can add an edge 
		//   from u to v with weight 5 like this:
		//   graph.addEdge(u, v, 5.0);

		Graph graph = new Graph();
		File file = new File(fileName);
		Scanner s = new Scanner(file);

		// read in first row of vertices into line
		String line = s.nextLine();
		// split line and put into vertices[]
		String[] vertices = line.split(",");
		// get size of vertices[]
		int size = vertices.length;

		// add all vertices to the graph
		for (String v : vertices) {
			Vertex u = new Vertex(v);
			graph.addVertex(u);
		}
		//System.out.println("DEBUG -- \n" + graph);
		// we can now use vertices[] as a reference when adding edges

		// i = row  - starting at 1 to size + 1
		for (int i = 1; i < size + 1; i++) {
			// read in next line
			line = s.nextLine();
			// vals[] contains next row of values to be parsed
			String[] vals = line.split(",");

			// iterate over the row that corresponds to vertex at vertices[i - 1]
			String currV = vertices[i - 1];
			//Vertex u = new Vertex(currV);
			//System.out.println("DEBUG -- currV: " + currV);
			
			// j = column - starting at 0 to size
			for (int j = 0; j < size; j++) {
				// parse from vals[]
				Double d = Double.parseDouble(vals[j]);
				//System.out.println("DEBUG -- current values: " + d);
				// check for non-zero value
				if (d > 0.0) {
					String adjV = vertices[j];
					//Vertex v = new Vertex(adjV);
					//System.out.println("DEBUG -- adjV: " + adjV);
					// create two vertices references by string names
					// add edge b/w two with weight d
					Vertex u = graph.getVertexByName(currV);
					Vertex v = graph.getVertexByName(adjV);
					graph.addEdge(u, v, d);
				}
			}
		}

		s.close();
		return graph;
	}

}
