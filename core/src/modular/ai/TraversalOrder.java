package modular.ai;

import java.util.ArrayList;

public class TraversalOrder {
	public static ArrayList<Grid> path = new ArrayList<>();
	
	public static void add(Grid g){
		path.add(g);
	}
}
