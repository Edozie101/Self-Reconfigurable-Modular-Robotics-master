package modular.graphics.environment;

import java.util.Map;

import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

import modular.graphics.cube.*;

/**
 * @author Group2
 * @version 1.1.0
 * Class generates all of the obstacles.
 */
public class Obstacles {
	/**
	 * Size of a larger cubic structure made out of obstacle cubes.
	 */
	private float size;
	/**
	 * The (closest-to-origin) corner positions of the cubic structure.
	 */
	private Vector3 cornerPosition;
	/**
	 * Array containing all of the obstacles.
	 */
	public Array<SimpleCube> cubes = new Array<>();
	/**
	 * Generates obstacles based on the given specification.
	 * @param size @see size
	 * @param cornerPosition @see cornerPosition
	 */
	public Obstacles(float size, Vector3 cornerPosition){
		this.size = size;
		this.cornerPosition = cornerPosition;
	}
	
	/**
	 * @return an array containing all the renderable models of the cubes at the given positions.
	 */
	public Array<ModelInstance> drawObstacles(){
		Array<ModelInstance> instances = new Array<>();
		SimpleCube cube;
		for(float x = cornerPosition.x; x < size + cornerPosition.x; x+= 1f){
			for(float y = cornerPosition.y; y < size + cornerPosition.y; y+= 1f){
				for(float z = cornerPosition.z; z < size + cornerPosition.z; z+= 1f){
					cube = new SimpleCube(1f ,x, y, z);
					cubes.add(cube);
					instances.add(cube.modelInstance);
				}
			}
		}
		return instances;
	}
	
	/**
	 * @param map represents @see java.util.Map which is used to create all the various obstacle cubes in their initial positions.
	 * @return an array containing all the renderable models of the cubes at the given positions.
	 */
	public Array<ModelInstance> drawObstacles(Map<Float, Vector3> map){
		Array<ModelInstance> instances = new Array<>();
		SimpleCube cube;
		for(Float key: map.keySet()){
			Vector3 position = map.get(key);
			cube = new SimpleCube(key, position.x, position.y, position.z);
			cubes.add(cube);
			instances.add(cube.modelInstance);
		}
		return instances;
	}
	
	public Array<ModelInstance> drawFinalState(Map<Float, Vector3> map){
		Array<ModelInstance> instances = new Array<>();
		SimpleCube cube;
		if(map != null)
		for(Float key: map.keySet()){
			Vector3 position = map.get(key);
			cube = new SimpleCube(key, position.x, position.y, position.z);
			cube.setFinal();
			cubes.add(cube);
			instances.add(cube.modelInstance);
		}
		return instances;
	}
	
	public Array<ModelInstance> drawInitState(Map<Float, Vector3> map){
		Array<ModelInstance> instances = new Array<>();
		SimpleCube cube;
		for(Float key: map.keySet()){
			Vector3 position = map.get(key);
			cube = new SimpleCube(key, position.x, position.y, position.z);
			cube.setInitl();
			cubes.add(cube);
			instances.add(cube.modelInstance);
		}
		return instances;
	}
}
