
public class Graph {

	int V; //number of vertices in the graph
	int E; //number of edges in the graph
	
	Edge edges[]; //array to hold all edges
	Vertex vertices[]; //array to hold all vertices
	
	public Graph(int V, int E) { //define number of vertices and edges when initializing graph
		
		this.V = V;
		this.E = E;
		
		this.edges = new Edge[E]; // array to store all edges
		for (int i = 0; i < E; i++) { //initialize Graph with all Edge objects "blank". Edit data in main method. 
			this.edges[i] = new Edge();	
		}
		
		this.vertices = new Vertex[V]; // array to store all vertices
		for (int i = 0; i < V; i++) { //initialize Graph with all Vertex objects "blank". Edit data in main method. 
			this.vertices[i] = new Vertex(i); //assigns vertices labels 0-V
		}
	}
	
	
	public boolean BellmanFord(int source) {
		
		int distances[] = new int[this.V]; //array to store the distance to each vertex from source vertex
		
		/* perform INITIALIZE SINGLE SOURCE 
		 * set all distances initially to "infinity" (here we use largest Integer value in Java)
		 * all predecessors are already initialized as "nil" (null here) set by Vertex constructor
		 * but they are reset here as well. */
		
		for (int i = 0; i < this.V; i++) {
			distances[i] = Integer.MAX_VALUE; 
			vertices[i].predecessor = null;
		}
		
		distances[source] = 0;  //set the initial source vertex at distance 0
		
		//now that INITIALIZE SINGLE SOURCE is complete, RELAX each edge |V| - 1 times
		
		for (int i = 0; i < this.V -1; i++) { //iterate |V| - 1 times 
			for (int j = 0; j < this.E; j++) { //iterate over each edge
				Vertex u = this.edges[j].source; //source vertex of current edge
				Vertex v = this.edges[j].destination; //destination vertex of current edge
				int w = this.edges[j].weight; //weight of current edge
			
				/* if the distance to the source is not "infinity" and the current distance to the source 
				 * plus the weight of the current edge is less than than the current distance to the 
				 * destination, then we set the new distance to the destination as the distance to the source
				 * plus the weight of the current edge.
				 * 
				 * We ignore the case where the distance to u from the source is infinity as
				 * infinity + some weight will never be less than another given distance.*/
				
				if (distances[u.label] != Integer.MAX_VALUE && distances[u.label] + w < distances[v.label]) {
					distances[v.label] = distances[u.label] + w;
					v.predecessor = u;
				}
				
			}
		}
		
		/* Now we must check for negative cycles. If we relax the edges for a Vth time and the distances change
		 * then we know that a negative cycle exists and we can return FALSE.*/
		
			for (int j = 0; j < this.E; j++) { //iterate over each edge
				Vertex u = this.edges[j].source; //source vertex of current edge
				Vertex v = this.edges[j].destination; //destination vertex of current edge
				int w = this.edges[j].weight; //weight of current edge
			
				/* if we reach the condition to change the distance to a vertex, then we terminate the method 
				 * and return FALSE, as this indicates the presence of a negative weight cycle. */
				
				if (distances[u.label] != Integer.MAX_VALUE && distances[u.label] + w < distances[v.label]) {
					System.out.println("Negative cycle exists in the graph");
					return false;
				}
				
			}
		
		/* If we make it past the negative cycle test, then we know we have found the shortest distance 
		 * to each vertex from the source vertex in the graph.
		 * We want to print the actual paths that result in the shortest distance from the source 
		 * to each other vertex in the graph, the PrintPaths method accomplishes this. 
		 * We then call the PrintDistances method to display the distance from the source to each other vertex. */
		
		for (int i = 0; i < this.V; i++) {
			System.out.println("Path from " + vertices[source].label + " to " + vertices[i].label + ":");
			this.PrintPath(vertices[source], vertices[i]);
			System.out.println();
		}
		
		this.PrintDistances(distances, source);
		
		return true;
	}
	
	public void PrintDistances(int distances[], int sourceVertex) {
		System.out.println("Distance to each vertex from the source " + sourceVertex + ":");
		for (int i = 0; i < this.V; i++) {
			
			if (distances[i] == Integer.MAX_VALUE) {
				System.out.println("No path exists from " + sourceVertex + " to " + i);
			}
			else {
			System.out.println("Distance from " + sourceVertex + " to " + i + " is: " + distances[i]);
			}
		}
	}
	
	public void PrintPath(Vertex source, Vertex destination) {
		
		if(destination.label == source.label) { //base case where the source and destination are the same vertex
			System.out.print(source.label + " ");
		}
		
		else if (destination.predecessor == null) { //no predecessor means that no viable path exists
			System.out.print("No path from " + source.label + " to " + destination.label + " exists.");
		}
		else {
			this.PrintPath(source, destination.predecessor);//recur towards the source via the destination's predecessor
			System.out.print(destination.label + " ");
		}
	}
	
	public static void main(String[] args) {
		
		Graph graph = new Graph(7, 10); //our Graph has 7 vertices and 10 edges
		
		//manually edit all Edge attributes
		
		graph.edges[0].source = graph.vertices[0];
		graph.edges[0].destination = graph.vertices[0];
		graph.edges[0].weight = 6;

		graph.edges[1].source = graph.vertices[0];
		graph.edges[1].destination = graph.vertices[2];
		graph.edges[1].weight = 5;
		
		graph.edges[2].source = graph.vertices[0];
		graph.edges[2].destination = graph.vertices[3];
		graph.edges[2].weight = 5;
		
		graph.edges[3].source = graph.vertices[3];
		graph.edges[3].destination = graph.vertices[2];
		graph.edges[3].weight = -2;
		
		graph.edges[4].source = graph.vertices[2];
		graph.edges[4].destination = graph.vertices[1];
		graph.edges[4].weight = -2;
		
		graph.edges[5].source = graph.vertices[1];
		graph.edges[5].destination = graph.vertices[4];
		graph.edges[5].weight = -1;
		
		graph.edges[6].source = graph.vertices[2];
		graph.edges[6].destination = graph.vertices[4];
		graph.edges[6].weight = 1;
		
		graph.edges[7].source = graph.vertices[3];
		graph.edges[7].destination = graph.vertices[5];
		graph.edges[7].weight = -1;
		
		graph.edges[8].source = graph.vertices[5];
		graph.edges[8].destination = graph.vertices[6];
		graph.edges[8].weight = 3;
		
		graph.edges[9].source = graph.vertices[4];
		graph.edges[9].destination = graph.vertices[6];
		graph.edges[9].weight = 3;
		
		//run Bellman-Ford to find the shortest distances and paths from a source to all other vertices
		graph.BellmanFord(0);
		System.out.println();
	}
}