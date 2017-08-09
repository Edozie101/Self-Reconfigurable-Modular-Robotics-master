package modular.graphics.cube;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Group2
 * @version 1.0.1
 * Interface representing a moving module.
 */
public interface MovingBox extends Box {
	/**
	 * The transformation matrix to be applied at the next simulation step.
	 */
	Matrix4 transform = null;
	/**
	 * The direction in which the box should move at the next simulation step.
	 */
	Vector3 direction = null;
	/**
	 * Whether the box has to be updated at the next simulation step.
	 */
	boolean hasToMove = true;
	/**
	 * To be used to change the texture of the box upon idling.
	 */
	void goIdle();
	/**
	 * To be used to change the texture of the box upon animating.
	 */
	void goAnim();
	/**
	 * Used to initialize the animation of the box.
	 */
	void animate();
	void selectedCube();
}
