package modular.graphics.cube;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Group2
 * @version 1.0
 * Standard box interface for all renderable cube models.
 */
public interface Box {
	/**
	 * Model fields.
	 */
	Model model = null;
	/**
	 * Renderable instance of the model.
	 */
	ModelInstance modelInstance = null;
	CubeInfo info = null;
	/**
	 * The position of the box in 3d space.
	 */
	Vector3 position = null;
	/**
	 * The material associated with the box.
	 */
	Material material = null;
	/**
	 * Unique static id of the class extending the box amongst all other similar objects.
	 */
	float id = 0f;	
	/**
	 * Dispose of the fields in the object.
	 */
	void dispose();
	/**
	 * Update the object.
	 */
	void update();
}
