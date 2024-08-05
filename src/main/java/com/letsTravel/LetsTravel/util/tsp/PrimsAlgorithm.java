package com.letsTravel.LetsTravel.util.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrimsAlgorithm {

	private PrimsAlgorithm()
	{
	}
	
/*
1: Initialize a tree with a single vertex, chosen arbitrarily from the graph. The vertex choosen in unsortedList(index 0)
2: Grow the tree by one edge: of the edges that connect the tree to vertices not yet in the tree, 
find the minimum-weight edge, and transfer it to the tree.
3: Repeat step 2 (until all vertices are in the tree).
* */	
public static List<Vertex>  run(List<Vertex> unsortedList, double[][] distances)
{
	Comparator<Vertex> compareMethod = new VertexComparator();

//This will initialize all vertex shortest edge to the initial base vertex. This is important because it creates a new edge for all
// vertices. It is required as it seeds the next part of the algorithm (the while loop)
// It satisfies the "Initialize of the tree with a single vertex" part of Prim's Algorithm.
	final Vertex baseVertex = unsortedList.get(0);
	unsortedList
			.stream()
			.forEach(vertex -> vertex.edge = new Edge(vertex, baseVertex, distances[vertex.getID()][baseVertex.getID()]));
	Collections.sort(unsortedList, compareMethod);

//This implements prim's algorithm. It "pops" off and adds to the tree the minimum edge and then 
//sorts and checks each edge to make sure it is the smallest.
//Sort is run BEFORE the new weights are assigned to insure that the new graph will be connected.
//This is mainly because the initial initialization happens before the while loop.
	List<Vertex> sortedList     = new ArrayList<>();
	while(!unsortedList.isEmpty())
	{
		Vertex pointConnected = unsortedList.get(0);
		pointConnected.connectedVertices.add(pointConnected.edge);

		//The following adds a childEdge helping the graph stay connected.
	    Edge parentEdge = pointConnected.edge;

	    if(sortedList.isEmpty())
	    {
			//special case for root node
			Vertex rootVertex = unsortedList.get(0);
			rootVertex.connectedVertices.remove(0);
			Vertex firstVertex = unsortedList.get(1);
			Edge updateEdge = firstVertex.edge;
			rootVertex.edge.parent = rootVertex.getID();
			rootVertex.edge.child = updateEdge.parent;
			rootVertex.edge.Owner = rootVertex;
			rootVertex.edge.Child = unsortedList.get(1);
			rootVertex.edge.weight = updateEdge.weight;
			rootVertex.connectedVertices.add(rootVertex.edge);
			Edge childEdge = new Edge(firstVertex, rootVertex, rootVertex.edge.weight);
			firstVertex.connectedVertices.add(childEdge);
	    }
	    else
	    {
	    	sortedList.forEach(vertex -> {
				if(parentEdge.child == vertex.getID()) {
					Edge childEdge = new Edge(parentEdge.Child, parentEdge.Owner, parentEdge.weight);
					vertex.connectedVertices.add(childEdge);
				}
			});
	    }

		sortedList.add(pointConnected);
		unsortedList.remove(pointConnected);
		Collections.sort(unsortedList, compareMethod); //lowest weight connected to MST comes to the top

		if (!unsortedList.isEmpty()) {
			Vertex basedVertex = unsortedList.get(0);
			unsortedList.stream()
					.forEach(vertex -> {
				if (vertex != basedVertex) {
					double checkNewWeight = distances[vertex.getID()][basedVertex.getID()];
					if (checkNewWeight < vertex.edge.weight) {
						vertex.edge.child = basedVertex.getID();
						vertex.edge.Child = basedVertex;
						vertex.edge.weight = checkNewWeight;
					}
				}
			});
		}
	}
	return sortedList;
}


}
