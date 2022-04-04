import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * CSC 331 Algorithm Analysis
 * Project 3: Shortest Path
 * 
 * The driver for the program.
 * 
 * @author Chris Young
 * @version 2021-01-26
 */

public class Main {
	public static void main(String[] args) throws FileNotFoundException  {

		Scanner scnr = new Scanner(System.in);
		
		// Ask user to enter the name of the file containing the graph details,
		// e.g., Routers.csv or JapanCities.csv.
		System.out.print("Enter the filename: ");
		String filename = scnr.nextLine();
		
		// Create a new GraphMaker object and use it to make a new graph.
		GraphMaker graphMaker = new GraphMaker();
		Graph graph = graphMaker.makeGraphFromFile(filename);

		// Print the graph out. (Note that this automatically calls graph.toString.)
		System.out.println(graph);

		// Prompt the user to enter the starting and ending vertices.
		System.out.print("Enter the starting vertex: ");
		String startVertexName = scnr.nextLine();
		System.out.print("Enter the ending vertex: ");
		String endVertexName = scnr.nextLine();

		// Run Dijkstra's Algorithm.
		Dijkstras dijk = new Dijkstras();
		String result = dijk.runShortestPath(graph, startVertexName, endVertexName);
		System.out.println(result);

		// Clean up scanner.
		scnr.close();
	}
}
