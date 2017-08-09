package modular.ai;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Group2
 * Class used for storing possible paths inside grids, part of possible grid based waypoint search
 */
public class Path{
	//cost (in units lost) of moving from those directions
	int northCost;
	int eastCost;
	int southCost;
	int westCost;
	//boundaries of the path
	Vector2[] northPath;
	Vector2[] eastPath;
	Vector2[] southPath;
	Vector2[] westPath;
	
	public Path(){
		
	}
	//Cost getters
	public int getNorthCost(){
		return northCost;
	}
	public int getEastCost(){
		return eastCost;
	}
	public int getSouthCost(){
		return southCost;
	}
	public int getWestCost(){
		return westCost;
	}
	//Path getters
	public Vector2[] getNorthPath(){
		return northPath;
	}
	public Vector2[] getEastPath(){
		return eastPath;
	}
	public Vector2[] getSouthPath(){
		return southPath;
	}
	public Vector2[] getWestPath(){
		return westPath;
	}
	// Cost setters
	public void setNorthCost(int x){
		northCost = x;
	}
	public void setEastCost(int x){
		eastCost = x;
	}
	public void setSouthCost(int x){
		southCost = x;
	}
	public void setWestCost(int x){
		westCost = x;
	}
	//Path setters
	public void setNorthPath(Vector2[] x){
		northPath = x;
	}
	public void setEastPath(Vector2[] x){
		eastPath = x;
	}
	public void setSouthPath(Vector2[] x){
		southPath = x;
	}
	public void setWestPath(Vector2[] x){
		westPath = x;
	}
	//end of Path class
}
