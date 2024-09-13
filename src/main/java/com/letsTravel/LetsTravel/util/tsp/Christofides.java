package com.letsTravel.LetsTravel.util.tsp;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.letsTravel.LetsTravel.domain.place.Place;

public class Christofides {

	public static ChristofidesTour christofidesAlgorithm(List<Place> places, int secondsToRunTwoOpt) {
		// places가 1개일 때 0번째 place와 1번째 place를 비교해야하는데
		// 1번째 place가 없어서 exception 발생해서 추가
		// -- 2024.08.07 강봉수
		// places가 1개일 때 빈 Integer Array를 반환하니 PlanService.sortPlacesByTraversalOrder에서 고장나서 수정
		// -- 2024.09.13 강봉수
		if (places.size() < 2) {
			return new ChristofidesTour(new ArrayList<Integer>(Arrays.asList(0)), 0);
		}

		// Benchmark benchmark = new Benchmark();
		// benchmark.startMark();
		List<Vertex> theGraph = parseGraph(places);
		double[][] distances = getDistances(theGraph);
		List<Vertex> minimumSpanningTree = PrimsAlgorithm.run(theGraph, distances);
		createEvenlyVertexedEularianMultiGraphFromMST(minimumSpanningTree, distances);
		List<Vertex> eulerTour = HierholzerAlgorithm.run(minimumSpanningTree);
		List<Vertex> travelingSalesPath = ShortCut.run(eulerTour);
		TwoOpt twoOpt = new TwoOpt(travelingSalesPath, distances, secondsToRunTwoOpt);
		travelingSalesPath = twoOpt.run();
		ChristofidesTour finalAnswer = finalAnswer(travelingSalesPath, distances);
		// benchmark.endMark();
		return finalAnswer;
	}

	private static List<Vertex> parseGraph(List<Place> places) {
		AtomicInteger index = new AtomicInteger();
		return places.stream().map(place -> {
			int id;
			double x, y;
			id = index.getAndIncrement();
			x = place.getLocation().getLatitude();
			y = place.getLocation().getLongitude();
			return new Vertex(id, x, y);
		}).collect(Collectors.toCollection(ArrayList::new));

	}

	// function calculates distances between all points on the graph
	private static double[][] getDistances(List<Vertex> graph) {
		double[][] distanceGraph = new double[graph.size()][graph.size()];
		for (int i = 0; i < graph.size(); i++) {
			for (int j = 0; j < graph.size(); j++) {
				distanceGraph[i][j] = difference(graph.get(i), graph.get(j));
			}
		}
		return distanceGraph;
	}

	// function that calculates the difference in location using A^2 + B^2 = C^2
	private static double difference(Vertex a, Vertex b) {
		double difference = Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
		return difference;
	}

	/*
	 * This splits even and odds from each other and then creates a
	 * "perfect matching" (it doesn't actually, but attempts something close to),
	 * and then reconnects the graph. Please note this creates a Eulerian Multigraph
	 * which means edges can be connected to each other twice. 2 edges "A-B" "A-B"
	 * can exist from the same main.java.Vertex.
	 */
	private static List<Vertex> createEvenlyVertexedEularianMultiGraphFromMST(List<Vertex> minimumSpanningTree, double[][] distances) {
		List<Vertex> oddNumbers = minimumSpanningTree.stream().filter(vertex -> vertex.connectedVertices.size() % 2 == 1).collect(Collectors.toCollection(ArrayList::new));

		while (!oddNumbers.isEmpty()) {
			double distance = Double.MAX_VALUE;
			final Vertex parent = oddNumbers.get(0);

			// Compare pointers to not use root node.
			double minDistanceToNextNode = oddNumbers.stream().mapToDouble(vertex -> vertex == parent ? Double.MAX_VALUE : distances[parent.getID()][vertex.getID()]).min().getAsDouble();

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

	private static ChristofidesTour finalAnswer(List<Vertex> TSP, double[][] distances) {
		// logic to connect end node to start node
		double totalDistance = TSP.stream().mapToDouble(
				vertex -> TSP.indexOf(vertex) == TSP.size() - 1 ? distances[TSP.get(0).getID()][TSP.get(TSP.size() - 1).getID()] : distances[vertex.getID()][TSP.get(TSP.indexOf(vertex) + 1).getID()])
				.sum();

		List<Integer> finalTour = TSP.stream().map(vertex -> {
			return vertex.getID();
		}).collect(Collectors.toCollection(ArrayList::new));
		return new ChristofidesTour(finalTour, totalDistance);
	}

}
