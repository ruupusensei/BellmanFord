
public class Edge {

	Vertex source;
	Vertex destination;
	int weight;
	
	public Edge(Vertex source, Vertex destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	
	public Edge() { //non-parameterized constructor for edges
		this.source = null;
		this.destination = null;
		this.weight = 0;
	}

}
