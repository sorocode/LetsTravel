package com.letsTravel.LetsTravel.tsptest;

public class Edge {
	
	int parent;
	int child;
	float weight;
	Vertex Owner;
	Vertex Child;

public Edge(Vertex parentEdge, Vertex childEdge, float distance) {
	parent = parentEdge.getID();
	child = childEdge.getID();
	weight = distance;
	Owner = parentEdge;
	Child = childEdge;
}

}
