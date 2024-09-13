package com.letsTravel.LetsTravel.util.tsp;

import java.util.List;

public class ChristofidesTour {
    private List<Integer> finalTour;
    private double tourCost;

    public ChristofidesTour(List<Integer> finalTour, double tourCost) {
        this.finalTour = finalTour;
        this.tourCost = tourCost;
    }

    public ChristofidesTour() {
    }
    
    public List<Integer> getFinalTour() {
        return finalTour;
    }

    public void setFinalTour(List<Integer> finalTour) {
        this.finalTour = finalTour;
    }

    public double getTourCost() {
        return tourCost;
    }

    public void setTourCost(double tourCost) {
        this.tourCost = tourCost;
    }

	@Override
	public String toString() {
		return "ChristofidesTour [finalTour=" + finalTour + ", tourCost=" + tourCost + "]";
	}
    
    
}
