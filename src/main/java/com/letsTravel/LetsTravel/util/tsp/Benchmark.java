package com.letsTravel.LetsTravel.util.tsp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Benchmark
{
    private LocalDateTime start;
    private LocalDateTime end;
    private long time = 0;
    private boolean startCheck = false;
    private boolean endCheck = false;

public Benchmark()
{
   
}
   
    void startMark()
    {
        startCheck = true;
        start = LocalDateTime.now();
    }
   
    void endMark()
    {
        endCheck = true;
        end = LocalDateTime.now();
    }
   
    double currentTime()
    {
    	return ChronoUnit.MILLIS.between(start, LocalDateTime.now());
    }
    
    long resultTime()
    {
        if(startCheck && endCheck) {
            return ChronoUnit.MILLIS.between(start, end);
        } else {
            System.out.print("BenchMark Failed. ");
        }
        return time;
    }
   
}