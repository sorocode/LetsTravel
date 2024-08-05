package com.letsTravel.LetsTravel.tsptest;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.letsTravel.LetsTravel.domain.place.Location;
import com.letsTravel.LetsTravel.domain.place.Place;

import static java.lang.Math.toIntExact;

public class Christofides {

	@Test
	void tspTest() {
		List<Place> places = new ArrayList<>();
		places.add(new Place(new Location(1.0f, 4.0f)));
		places.add(new Place(new Location(4.0f, 4.0f)));
		places.add(new Place(new Location(10.0f, 7.0f)));
		places.add(new Place(new Location(1.0f, 100.0f)));
		places.add(new Place(new Location(-2.0f, 0.0f)));
		places.add(new Place(new Location(-7.0f, -3.0f)));
		places.add(new Place(new Location(-17.0f, 14.0f)));
		places.add(new Place(new Location(17.0f, 7.0f)));
		places.add(new Place(new Location(19.0f, -4.0f)));
		places.add(new Place(new Location(37.0f, 73.0f)));
		places.add(new Place(new Location(-43.0f, 50.0f)));
		places.add(new Place(new Location(57.0f, -2.0f)));
		places.add(new Place(new Location(20.0f, 40.0f)));
		Christofides.christofidesAlgorithm(places, 5);
	}

	public static ChristofidesTour christofidesAlgorithm(List<Place> places, int secondsToRunTwoOpt) {
		Benchmark benchmark = new Benchmark();
		benchmark.startMark();
		List<Vertex> theGraph = parseGraph(places);
		float[][] distances = getDistances(theGraph);
		List<Vertex> minimumSpanningTree = PrimsAlgorithm.run(theGraph, distances);
		createEvenlyVertexedEularianMultiGraphFromMST(minimumSpanningTree, distances);
		List<Vertex> eulerTour = HierholzerAlgorithm.run(minimumSpanningTree);
		List<Vertex> travelingSalesPath = ShortCut.run(eulerTour);
		TwoOpt twoOpt = new TwoOpt(travelingSalesPath, distances, secondsToRunTwoOpt);
		travelingSalesPath = twoOpt.run();
		ChristofidesTour finalAnswer = finalAnswer(travelingSalesPath, distances);
		benchmark.endMark();
		System.out.println("Program took: " + benchmark.resultTime() + " ms");
		return finalAnswer;
	}

	private static List<Vertex> parseGraph(List<Place> places) {
		AtomicInteger index = new AtomicInteger();
		return places.stream().map(place -> {
			int id;
			float x, y;
			id = index.getAndIncrement();
			x = place.getLocation().getLatitude();
			y = place.getLocation().getLongitude();
			return new Vertex(id, x, y);
		}).collect(Collectors.toCollection(ArrayList::new));

	}

	// function calculates distances between all points on the graph
	private static float[][] getDistances(List<Vertex> graph) {
		float[][] distanceGraph = new float[graph.size()][graph.size()];
		for (int i = 0; i < graph.size(); i++) {
			for (int j = 0; j < graph.size(); j++) {
				distanceGraph[i][j] = difference(graph.get(i), graph.get(j));
			}
		}
		return distanceGraph;
	}

	// function that calculates the difference in location using A^2 + B^2 = C^2
	private static int difference(Vertex a, Vertex b) {
		long difference = Math.round(Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2)));
		return toIntExact(difference);
	}

	/*
	 * This splits even and odds from each other and then creates a
	 * "perfect matching" (it doesn't actually, but attempts something close to),
	 * and then reconnects the graph. Please note this creates a Eulerian Multigraph
	 * which means edges can be connected to each other twice. 2 edges "A-B" "A-B"
	 * can exist from the same main.java.Vertex.
	 */
	private static List<Vertex> createEvenlyVertexedEularianMultiGraphFromMST(List<Vertex> minimumSpanningTree, float[][] distances) {

		List<Vertex> oddNumbers = minimumSpanningTree.stream().filter(vertex -> vertex.connectedVertices.size() % 2 == 1).collect(Collectors.toCollection(ArrayList::new));

		while (!oddNumbers.isEmpty()) {
			float distance = Float.MAX_VALUE;
			final Vertex parent = oddNumbers.get(0);

			// Compare pointers to not use root node.
			double minDistanceToNextNode = oddNumbers.stream().mapToDouble(vertex -> vertex == parent ? Float.MAX_VALUE : distances[parent.getID()][vertex.getID()]).min().getAsDouble();

			Vertex child = oddNumbers.stream().filter(vertex -> distances[parent.getID()][vertex.getID()] == minDistanceToNextNode && vertex != parent).findFirst().get();

			Edge fromParentToChildEdge = new Edge(parent, child, distance);
			Edge fromChildToParentEdge = new Edge(child, parent, distance);
			parent.connectedVertices.add(fromParentToChildEdge);
			child.connectedVertices.add(fromChildToParentEdge);
			oddNumbers.remove(parent);
			oddNumbers.remove(child);
		}
		return minimumSpanningTree;
	}

	private static ChristofidesTour finalAnswer(List<Vertex> TSP, float[][] distances) {
		int lineFormatting = 0;
		for (Vertex vertex : TSP) {
			System.out.print(vertex.getID() + " ");
			if (lineFormatting == 20) {
				lineFormatting = 0;
				System.out.println();
			}
			lineFormatting++;
		}
		System.out.println();

		// logic to connect end node to start node
		float totalDistance = (float) TSP.stream().mapToDouble(
				vertex -> TSP.indexOf(vertex) == TSP.size() - 1 ? distances[TSP.get(0).getID()][TSP.get(TSP.size() - 1).getID()] : distances[vertex.getID()][TSP.get(TSP.indexOf(vertex) + 1).getID()])
				.sum();
		System.out.println("Total distance covered of the " + TSP.size() + " vertices is: " + totalDistance);

		List<Integer> finalTour = TSP.stream().map(vertex -> {
			return vertex.getID();
		}).collect(Collectors.toCollection(ArrayList::new));
		return new ChristofidesTour(finalTour, totalDistance);
	}

}
