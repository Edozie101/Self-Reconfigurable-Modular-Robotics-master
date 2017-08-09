package modular.graphics.cube;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import modular.graphics.WorldInitialization;
import modular.util.Constants;

/**
 * @author Group2
 * @version 1.1.2
 * Contains basic instance field used to represent a standard, non-moving cube.
 */
public class SimpleCube extends Model implements Box{
	/**
	 * @see modular.graphics.cube.Box#model
	 */
	public Model model;
	/**
	 * @see modular.graphics.cube.Box#modelInstance
	 */
	public ModelInstance modelInstance;
	public CubeInfo info;
	/**
	 * @see modular.graphics.cube.Box#position
	 */
	public Vector3 position;
	/**
	 * @see modular.graphics.cube.Box#material
	 */
	public Material material;
	/**
	 * @see modular.graphics.cube.Box#id
	 */
	public float id;
	public WorldInitialization init;
	/**
	 * Constructor initialises the various instance field of the box.
	 * @param id The id of the box.
	 * @param x The x-axis position of the box.
	 * @param y The y-axis position of the box.
	 * @param z The z-axis position of the box.
	 */
	public SimpleCube(float id, float x, float y, float z){
		this.id = id;
		updateInfo(x,y,z);
		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(Constants.SIDE, Constants.SIDE, Constants.SIDE, Constants.OBSTACLE_CUBE, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		modelInstance = new ModelInstance(model);
		this.modelInstance.transform.setToTranslation(info.position);
	}
	
	private void updateInfo(float x, float y, float z){
		info = new CubeInfo();
		info.position = new Vector3(x, y, z);
		info.min = info.position;
		info.max = new Vector3(info.position.x + info.size, info.position.y + info.size, info.position.z + info.size);
		info.center = new Vector3(info.min).add(info.max).scl(0.5f);
		info.halfwidth = new Vector3(0.5f, 0.5f, 0.5f);
	}
	/**
	 * @see com.badlogic.gdx.graphics.g3d.Model#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		position = null;
	}
	
	/**
	 * @see modular.graphics.cube.Box#update()
	 */
	@Override
	public void update() {
		
	}

	public void setFinal() {
		Attribute a = new BlendingAttribute(0.5f);
		this.modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(Constants.END_CUBE_TEXTURE));
		this.modelInstance.materials.get(0).set(a);
	}

	public void setInitl() {
		Attribute a = new BlendingAttribute(0.5f);
		this.modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(Constants.START_CUBE_TEXTURE));
		this.modelInstance.materials.get(0).set(a);
	}

}
