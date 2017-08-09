package modular.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;

import modular.graphics.WorldInitialization;
import modular.graphics.cube.Cube;

/**
 * @author Group2
 * @version 1.2.1
 * Contains the necessary information to show a replay of a simulation. Does not currently support partial simulation replays.
 */
@SuppressWarnings("unused")
public class ReplayState implements State {
	/**
	 * Current moving cube state of the board.
	 */
	public Map<Float, Vector3> currentState = null;
	/**
	 * Current obstacle state of the board.
	 */
	public Map<Float, Vector3> obstacleState = null;
	/**
	 * Desired final state of the board.
	 */
	public Map<Float, Vector3> finalState = null;
	/**
	 * Contains each step of the simulation at a different index.
	 */
	public ArrayList<Map<Float, Vector3[]>> replayState = null;
	/**
	 * @see modular.state.State#setCurrState(java.util.Map)
	 */
	@Override
	public void setCurrState(Map<Float, Vector3> positionReader) {
		currentState = positionReader;
	}

	/**
	 * @see modular.state.State#setObstState(java.util.Map)
	 */
	@Override
	public void setObstState(Map<Float, Vector3> positionReader) {
		obstacleState = positionReader;
	}

	/**
	 * @see modular.state.State#setFinalState(java.util.Map)
	 */
	@Override
	public void setFinalState(Map<Float, Vector3> positionReader) {
		finalState = positionReader;
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
}
