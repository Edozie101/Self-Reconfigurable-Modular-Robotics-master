package modular.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;

/**
 * @author Group2
 * @version 1.0
 *	Contains information regarding the board state of the current simulation.
 */
public class BoardState implements State {
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
	/**
	 * @see modular.state.State#setCurrState(java.util.Map)
	 */
	/**
	 * Contains each step of the simulation at a different index.
	 */
	public ArrayList<Map<Float, Vector3[]>> replayState = null;
	@Override
	public void setCurrState(Map<Float, Vector3> positionReader) {
		currentState = positionReader;
		AIState.setCurrentState(currentState);
	}

	/**
	 * @see modular.state.State#setObstState(java.util.Map)
	 */
	@Override
	public void setObstState(Map<Float, Vector3> positionReader) {
		obstacleState = new HashMap<>(positionReader);
		AIState.setObstacleState(obstacleState);
	}

	/**
	 * @see modular.state.State#setFinalState(java.util.Map)
	 */
	@Override
	public void setFinalState(Map<Float, Vector3> positionReader) {
		finalState = positionReader;
		AIState.setFinalState(finalState);
	}

	/**
	 * @see modular.state.State#setReplayState(java.util.ArrayList)
	 */
	@Override
	public void setReplayState(ArrayList<Map<Float, Vector3[]>> movesReader) {
		replayState = movesReader;
		currentState = new HashMap<>();
		Map<Float, Vector3[]> temp = movesReader.get(0);
		for(Float f: temp.keySet()){
			currentState.put(f, (temp.get(f))[0]);
		}
	}

	/**
	 * @return an arraylist containing all the position with obstacle cubes.
	 */
	public ArrayList<Vector3> getBlockedPositions() {
		if(obstacleState == null) return null;
		ArrayList<Vector3> list = new ArrayList<>();
		for(Float f: obstacleState.keySet()){
			list.add(new Vector3(((int) obstacleState.get(f).x),((int) obstacleState.get(f).z),((int) obstacleState.get(f).y)));
		}
		return list;
	}
}
