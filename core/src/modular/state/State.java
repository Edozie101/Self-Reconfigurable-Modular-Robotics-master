package modular.state;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;

/**
 * @author Group2
 * @Version 1.0
 * Interface for board states.
 */
public interface State {

	/**
	 * Current state of the board.
	 */
	Map<Float, Vector3> currentState = null;
	/**
	 * Current state of all obstacle cubes.
	 */
	Map<Float, Vector3> obstacleState = null;
	/**
	 * Desired final state of the board.
	 */
	Map<Float, Vector3> finalState = null;
	/**
	 * @param positionReader sets the current state of the board
	 */
	void setCurrState(Map<Float, Vector3> positionReader);
	/**
	 * @param positionReader sets the obstacle state of the board
	 */
	void setObstState(Map<Float, Vector3> positionReader);
	/**
	 * @param positionReader sets the final state of the board
	 */
	void setFinalState(Map<Float, Vector3> positionReader);
	/**
	 * @param movesReader sets the replay state of the board if it exists.
	 */
	void setReplayState(ArrayList<Map<Float, Vector3[]>> movesReader);
}
