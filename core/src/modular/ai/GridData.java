package modular.ai;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Group2
 * This class is used as data storage for each grid, part of possible grid based waypoint search
 */
public class GridData {
	
	int gridID;
	Grid grid;
	
	Path North;
	Path East;
	Path South;
	Path West;
	
	public boolean end;
	
	public boolean start;
	
	/**
	 * @param grid reference to the actual grid object which data is stored
	 */
	public GridData(Grid grid){
		
		this.grid = grid;
		gridID = grid.getID();
		//initialize Paths
		if(grid.top != null){
			North = new Path();
		}
		if(grid.right != null){
			East = new Path();
		}
		if(grid.bottom != null){
			South = new Path();
		}
		if(grid.left != null){
			West = new Path();
		}
		end = false;
		start = false;
	}

}
