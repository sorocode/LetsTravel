package com.letsTravel.LetsTravel.util.tsp;

import java.util.ArrayList;


public class Vertex 
{
	   private int id;
	   private float x_var;
	   private float y_var;	
	   public ArrayList<Edge> connectedVertices;
	   public   Edge 	   edge; //used for prims algorithm

	   boolean evenEdge;
	   
	   public Vertex(int id, float x_var, float y_var)
	   {	   
		   this.id = id;
		   this.x_var = x_var;
		   this.y_var = y_var;
		   this.connectedVertices = new ArrayList<>();
		   this.edge       = null;  
	   }
	   
	   int getID()
	   {
		      return id;
	   }

	   float getX()
	   {
		   	return x_var;
	    }
		   
	    float getY()
	    {
		   return y_var;
		}
	    
		   
}