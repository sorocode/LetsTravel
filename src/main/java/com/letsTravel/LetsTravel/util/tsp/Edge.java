package com.letsTravel.LetsTravel.util.tsp;

public class Edge {
	
	int parent;
	int child;
	double weight;
	Vertex Owner;
	Vertex Child;

public Edge(Vertex parentEdge, Vertex childEdge, double distance) {
	parent = parentEdge.getID();
	child = childEdge.getID();
	weight = distance;
	Owner = parentEdge;
	Child = childEdge;
}

}
