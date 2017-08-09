package modular.physics;

import com.badlogic.gdx.math.Vector3;

import modular.graphics.cube.Cube;
import modular.graphics.cube.SimpleCube;

public class Collision {
	public static boolean isColliding(Cube cubeA, Cube cubeB){
		if((cubeA.info.center.x - cubeB.info.center.x) - (cubeA.info.halfwidth.x - cubeB.info.halfwidth.x) > 0.01f) return false;
		if((cubeA.info.center.y - cubeB.info.center.y) - (cubeA.info.halfwidth.y - cubeB.info.halfwidth.y) > 0.01f) return false;
		if((cubeA.info.center.z - cubeB.info.center.z) - (cubeA.info.halfwidth.z - cubeB.info.halfwidth.z) > 0.01f) return false;
		
		return true;
	}
	
	public static boolean isCollidingFloor(Cube cube){
		if(cube.info.min.y < 0f) return true;
		return true;
	}
	
	public static boolean isCollidingObstacle(Cube cube, SimpleCube box){
		if((cube.info.center.x - box.info.center.x) - (cube.info.halfwidth.x - box.info.halfwidth.x) > 0.01f) return false;
		if((cube.info.center.y - box.info.center.y) - (cube.info.halfwidth.y - box.info.halfwidth.y) > 0.01f) return false;
		if((cube.info.center.z - box.info.center.z) - (cube.info.halfwidth.z - box.info.halfwidth.z) > 0.01f) return false;
		
		return true;
	}
	
	public static void resolveCollision(Cube cube, Vector3 restoration){
		cube.transform.translate(restoration);
	}
}
