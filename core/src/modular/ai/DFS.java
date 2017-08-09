package modular.ai;

import java.util.*;

public class DFS {
	public ArrayList<Grid> grids;
	public HashMap<Grid, Boolean> visited;
	public static Grid start;
	public static Grid target;
	public static TraversalOrder path;
	
	public DFS(){
		path = new TraversalOrder();
		grids = Grid.initGrids(4,4);
		visited = new HashMap<>();
		for(Grid g: grids){
			visited.put(g,  false);
			if(g.data.start){
				start = g;
			}
			if(g.data.end){
				target = g;
			}
		}
		GridDataInitializer.initGridData(grids);	
		DFS(start);
		System.out.println(TraversalOrder.path);
	}
	
	public void DFS(Grid g){
		visited.replace(g, true);
		int cost[] = new int[4];
		if(check(g.top)){
			cost[0] = calculateCost(g.top);
			if(g.top == target){
				TraversalOrder.add(target);
			} else if(g.top == start){
				cost[0] = 9999;
			}
		}else if(check(g.left)){
			cost[1] = calculateCost(g.left);
			if(g.top == target){
				TraversalOrder.add(target);
			} else if(g.top == start){
				cost[1] = 9999;
			}
		}else if(check(g.right)){
			cost[2] = calculateCost(g.right);
			if(g.top == target){
				TraversalOrder.add(target);
			} else if(g.top == start){
				cost[2] = 9999;
			}
		}else if(check(g.bottom)){
			cost[3] = calculateCost(g.bottom);
			if(g.top == target){
				TraversalOrder.add(target);
			} else if(g.top == start){
				cost[3] = 9999;
			}
		}
		
		Grid bestChoice = g.top;
		int bestChoiceCost = cost[0];
		for(int i = 1; i < 4; i++){
			if(cost[i] > bestChoiceCost){
				bestChoiceCost = cost[i];
			}
		}
		if(bestChoiceCost == cost[1])
			bestChoice=g.left;
		else if(bestChoiceCost == cost[2])
			bestChoice=g.right;
		else if(bestChoiceCost == cost[3])
			bestChoice=g.bottom;
		
		path.add(bestChoice);
		
		DFS(bestChoice);
	}
	
	public int calculateCost(Grid g){
		int cost = 0;
		return cost;
	}
	
	public boolean check(Grid g){
		return g == null;
	}
}
