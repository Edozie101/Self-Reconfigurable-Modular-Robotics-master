package modular.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import modular.graphics.cube.Cube;
import modular.graphics.cube.SimpleCube;
import modular.graphics.environment.Floor;
import modular.graphics.environment.InitialConfiguration;
import modular.graphics.environment.Obstacles;
import modular.menu.Launcher;
import modular.state.BoardState;
import modular.util.Constants;

/**
 * @author Group2
 * @version 1.1
 * Initialises and pre-renders the board state graphically.
 */
public class WorldInitialization {
	/**
	 * Unique tag assigned to the class.
	 */
	public static final String tag = WorldInitialization.class.getName();
	
	/**
	 * Reference to the floor initialiser.
	 */
	public Floor floor;
	/**
	 * Reference to the obstacle initialiser.
	 */
	public Obstacles obstacles;
	/**
	 * Reference to the moving cube initialiser.
	 */
	public InitialConfiguration init;
	
	public Array<SimpleCube> startConfig;
	public Array<SimpleCube> endConfig;
	/**
	 * Contains all moving cubes in an array.
	 */
	public Array<Cube> movingCubes;
	/**
	 * Contains a map of all cube ids assigned to their respective cubes.
	 */
	public Map<Float, Cube> movingCubesMap;
	/**
	 * Contains an array of all obstacle cubes.
	 */
	public Array<SimpleCube> obstacleCubes;
	/**
	 * Contains the instance of the floor as a single, unified, bi-sided, repeating texture.
	 */
	public ModelInstance floorCubes;
	/**
	 * Contains an array of all the models instances to be rendered.
	 */
	public Array<ModelInstance> instances = new Array<>();

	/**
	 * Convenience reference to the launcher.
	 */
	private Launcher launcher;
	/**
	 * Sets the reference to the launcher an initializes all of the necessary objects.
	 * @param launcher
	 */
	public WorldInitialization(Launcher launcher){//State state){
		//this.state = state;
		this.launcher = launcher;
		initObjects();
	}
	
	/**
	 * Initialises all of the various fields to be drawn by the renderer.
	 */
	private void initObjects(){
		
		ModelInstance temp;
		floor = new Floor();
		obstacles = new Obstacles(5f, new Vector3((float)Math.floor((int)Constants.SIZE/3), 0f, (float)Math.floor((int)Constants.SIZE/2)));
		init = new InitialConfiguration();
		movingCubesMap = new HashMap<Float, Cube>();
		temp = floor.createFloor();
		floorCubes = temp;
		instances.add(temp);
		if(launcher.boardState != null){
			if(BoardState.obstacleState != null){
			instances.addAll(obstacles.drawObstacles(BoardState.obstacleState));//state.obstacleState));
			obstacleCubes = obstacles.cubes;
			}
			movingCubes = init.drawInitialConfig(BoardState.currentState);//state.currentState);
				endConfig = new Array<>();
				startConfig = new Array<>();
				instances.addAll(obstacles.drawFinalState(BoardState.currentState));
				instances.addAll(obstacles.drawFinalState(BoardState.finalState));
		} else if(launcher.replayState != null){
			if(launcher.replayState.obstacleState != null){
				instances.addAll(obstacles.drawObstacles(launcher.replayState.obstacleState));//state.obstacleState));
				obstacleCubes = obstacles.cubes;
				}
			movingCubes = init.drawInitialConfig(launcher.replayState.currentState);//state.currentState);
		}
		for(Cube cubeToAdd : movingCubes){
			movingCubesMap.put(cubeToAdd.id, cubeToAdd);
			instances.add(cubeToAdd.modelInstance);
			cubeToAdd.init = this;
		}
		if(launcher.replayState != null){
			for(Map<Float, Vector3[]> map : launcher.replayState.replayState){
				for(Float f: map.keySet()){
					movingCubesMap.get(f).previousPosition.addLast(map.get(f)[0]);
					movingCubesMap.get(f).nextPosition.addLast(map.get(f)[1]);
				}
			}
		}
		if(obstacleCubes != null)
		for(SimpleCube obstacle: obstacleCubes){
			obstacle.init = this;
		}
	}
	/**
	 * Currently not used.
	 * @param deltaTime
	 */
	public void update(float deltaTime){ //currently not used
		//updateCubes(deltaTime);
	}
	
	public Array<Cube> getmovingCubes(){
		return movingCubes;
	}
	public Array<SimpleCube> getobstacleCubes(){
		return obstacleCubes;
	}
}
