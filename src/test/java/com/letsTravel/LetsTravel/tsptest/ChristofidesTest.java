package com.letsTravel.LetsTravel.tsptest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.letsTravel.LetsTravel.domain.place.Location;
import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.util.tsp.Christofides;

public class ChristofidesTest {

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
		System.out.println(Christofides.christofidesAlgorithm(places, 5)); ;
	}
}
