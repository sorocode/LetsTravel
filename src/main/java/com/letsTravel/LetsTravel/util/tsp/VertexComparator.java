package com.letsTravel.LetsTravel.util.tsp;

import java.util.Comparator;


public class VertexComparator implements Comparator<Vertex> {

    public int compare(Vertex vertexOne, Vertex vertexTwo) {
        if (vertexOne.edge.weight > vertexTwo.edge.weight) {
            return 1;
        } else if (vertexOne.edge.weight < vertexTwo.edge.weight) {
            return -1;
        } else {
            return 0;
        }
    }

}
