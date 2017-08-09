package modular.state;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;

public class AIState {
	/**
	 * The current state of the board.
	 */
	public static Map<Float, Vector3> currentState;
	/**
	 * The positions of the obstacles.
	 */
	public static Map<Float, Vector3> obstacleState = null;
	/**
	 * The desired target state of the board.
	 */
	public static Map<Float, Vector3> finalState = null;
	
	public static void setCurrentState(Map<Float, Vector3> x){
		currentState = new HashMap<>(x);
	}
	public static void setObstacleState(Map<Float, Vector3> x){
		obstacleState = new HashMap<>(x);
	}
	public static void setFinalState(Map<Float, Vector3> x){
		finalState = new HashMap<>(x);
	}

}
