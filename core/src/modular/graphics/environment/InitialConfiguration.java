package modular.graphics.environment;

import java.util.Map;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.*;

import modular.graphics.cube.*;

/**
 * @author Group2
 * @version 1.1.0
 * Generates the initial configuration of the board.
 */
public class InitialConfiguration {
	
	/**
	 * @return an array containing a few (pseudo-random) cubes for testing purposes.
	 */
	public Array<Cube> drawInitialConfig(){	
		Array<Cube> cubes = new Array<>();
		cubes.add(new Cube(0f, 0f,0f,0f));
		cubes.add(new Cube(1f,1f, 0f, 0f));
		cubes.add(new Cube(2f, 0f, 0f, 1f));
		for(Cube cube: cubes)
			cube.modelInstance.transform.setToTranslation(cube.info.position);
		return cubes;
	}
	
	/**
	 * @param map represents @see java.util.Map which is used to create all the various moving cubes in their initial positions.
	 * @return an array containing all the cubes generated by the given map
	 */
	public Array<Cube> drawInitialConfig(Map<Float, Vector3> map){
		Array<Cube> cubes = new Array<>();
		for(Float key: map.keySet()){
			Vector3 position = map.get(key);
			cubes.add(new Cube(key, position.x, position.y, position.z));
		}
		for(Cube cube: cubes)
			cube.modelInstance.transform.setToTranslation(cube.info.position);
		return cubes;
	}
}
