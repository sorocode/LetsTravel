package com.letsTravel.LetsTravel.util.tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShortCut {
	
	
	private ShortCut() {
	}
	
public static List<Vertex> run(List<Vertex> tour) {
	return new ArrayList<>(tour)
			.stream()
			.distinct() //for duplicated elements, the element appearing first in the encounter order is preserved.
			.collect(Collectors.toCollection(ArrayList::new));
	}
}
