package com.letsTravel.LetsTravel.util.tsp;

import java.util.LinkedList;
import java.util.List;

public class HierholzerAlgorithm {
    private HierholzerAlgorithm() {
    }

    public static List<Vertex> run(List<Vertex> vertexGraph) {
        List<Vertex> firstPath;
        List<Vertex> currentTour = new LinkedList<>(vertexGraph);
        firstPath = new LinkedList<>();
        while (currentTour.get(0).connectedVertices.size() != 0) {
            List<Vertex> returnedPath = returnAPath(currentTour.get(0));
            firstPath = runHelper(returnedPath);
        }
        return firstPath;
    }

    private static List<Vertex> runHelper(List<Vertex> firstPath) {
        for (int i = 0; i < firstPath.size(); i++) {
            if (firstPath.get(i).connectedVertices.size() > 0) {
                List<Vertex> addToPath;
                addToPath = returnAPath(firstPath.get(i));
                int indexSaved = i + 1;
                int addPathSize = addToPath.size();
                for (int j = 1; j < addPathSize; j++) {
                    firstPath.add(indexSaved, addToPath.get(0));
                    addToPath.remove(0);
                }
            }
        }

        return firstPath;
    }


    private static List<Vertex> returnAPath(Vertex pathStart) {
        List<Vertex> returnValue = new LinkedList<>();
        Vertex pathFinish = null;
        Vertex firstNode = pathStart;
        while (firstNode != pathFinish) {
            pathFinish = pathStart.connectedVertices.get(0).Child;
            pathStart.connectedVertices.remove(0);
            //remove the parent edge from child node
            for (int i = 0; i < pathFinish.connectedVertices.size(); i++) {
                if (pathFinish.connectedVertices.get(i).child == pathStart.getID()) {
                    pathFinish.connectedVertices.remove(i);
                    break;
                }
            }
            returnValue.add(pathStart);
            pathStart = pathFinish;
        }
        returnValue.add(pathFinish); //This needs to be added to "complete" the loop.
        return returnValue;
    }

}
