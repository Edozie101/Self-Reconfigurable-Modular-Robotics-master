package modular.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import modular.state.AIState;

/**
 * @author Group2
 * Initializes grid data, part of possible grid based waypoint search.
 */
public class GridDataInitializer {
	
	/**
	 * @param grids ArrayList of all grids
	 */
	public static void initGridData( ArrayList<Grid> grids){
		
		Map<Float, Vector3> obstacleState;
		obstacleState = new HashMap<>(AIState.obstacleState);
		
		Map<Vector3, int[]> positionState;
		positionState = new HashMap<>();
		
		for(Vector3 position : obstacleState.values()){
			
			positionState.put(position, new int[4]);
			//positionState.get(position)[0] = -1;
			//positionState.get(position)[1] = -1;
			//positionState.get(position)[2] = -1;
			//positionState.get(position)[3] = -1;
		}
		
		for(Grid grid: grids){
			int gridSize = grid.xdimension;;
			int bot = grid.z;
			int left = grid.x;
			int top = bot+gridSize;
			int right = left+gridSize;
			
			
			ArrayList<Vector3> obstacles = new ArrayList<>();

			for(Vector3 position : obstacleState.values()){
				if(position.x > left-1 && position.x < right){
					if(position.z > bot-1 && position.z < top){
						obstacles.add(position);
						grid.obstacles++;
					}
				}
			}
			if(obstacles.isEmpty()){
				if(grid.data.North != null && grid.data.South != null){
					grid.data.North.setSouthCost(0);
					grid.data.South.setNorthCost(0);
				}
				if(grid.data.North != null && grid.data.East != null){
					grid.data.North.setEastCost(0);
					grid.data.East.setNorthCost(0);
				}
				if(grid.data.North != null && grid.data.West != null){
					grid.data.North.setWestCost(0);	
					grid.data.West.setNorthCost(0);
				}
				if(grid.data.East != null && grid.data.South != null){
					grid.data.East.setSouthCost(0);
					grid.data.South.setEastCost(0);
				}
				if(grid.data.East != null && grid.data.West != null){
					grid.data.East.setWestCost(0);
					grid.data.West.setEastCost(0);
				}
				if(grid.data.South != null && grid.data.West != null){
					grid.data.South.setWestCost(0);
					grid.data.West.setSouthCost(0);	
				}	
			} else {
				for(Vector3 obstacle : obstacles){
					for(Vector3 position : obstacleState.values()){
						
						if(positionState.get(obstacle)[0] == 0){
							if(position.x > obstacle.x-2 && position.x < obstacle.x+2 && position.z > obstacle.z && position.z < obstacle.z+2){
								positionState.get(obstacle)[0] = 1;
								positionState.get(position)[2] = 1;
							}
						}
						if(positionState.get(obstacle)[1] == 0){
							if(position.x > obstacle.x && position.x < obstacle.x+2 && position.z > obstacle.z-2 && position.z < obstacle.z+2){
								positionState.get(obstacle)[1] = 1;
								positionState.get(position)[3] = 1;
							}
						}
						if(positionState.get(obstacle)[2] == 0){
							if(position.x > obstacle.x-2 && position.x < obstacle.x+2 && position.z > obstacle.z-2 && position.z < obstacle.z){
								positionState.get(obstacle)[2] = 1;
								positionState.get(position)[0] = 1;
							}
						}
						if(positionState.get(obstacle)[3] == 0){
							if(position.x > obstacle.x-2 && position.x < obstacle.x-1 && position.z > obstacle.z-2 && position.z < obstacle.z+2){
								positionState.get(obstacle)[3] = 1;
								positionState.get(position)[1] = 1;
							}
						}
					}//end of finding obstacles avoidability values		
					
					
				}//end of "for each obstacle inside this grid" loop
				
			}//end of "if grid not empty" else
		
		}//end of "for each grid" loop
	}
}
