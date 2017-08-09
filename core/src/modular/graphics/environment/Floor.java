package modular.graphics.environment;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.*;
import modular.util.Constants;

/**
 * @author Group2
 * Generates the floor as a single, repeating, bi-sided plane with a unique texture.
 */
public class Floor {
	public ModelInstance createFloor(){
		//create the material and the material properties, as well as the texture region.
		Material material = new Material();
		Texture texture = Constants.FLOOR_CUBE_TEXTURE;
		texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		TextureRegion region = new TextureRegion(texture);
		region.setRegion(0, 0, Constants.SIZE, Constants.SIZE);
		material.set(new TextureAttribute(TextureAttribute.Diffuse, region));
		//combine the various models into a single texture.
		ModelBuilder modelBuilder = new ModelBuilder();
		modelBuilder.begin();
		MeshPartBuilder meshBuilder = modelBuilder.part("floor", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);
		meshBuilder.setUVRange(0f, 0f, 1f, 1f);
		//generate both sides of the floor.
		meshBuilder.rect(new Vector3(-0.5f, -0.5f, -0.5f), new Vector3(-0.5f, -0.5f, Constants.SIZE-0.5f), new Vector3(Constants.SIZE-0.5f, -0.5f, Constants.SIZE-0.5f), new Vector3(Constants.SIZE-0.5f, -0.5f, -0.5f), new Vector3(0f,-1f, 0f));
		meshBuilder.rect(new Vector3(-0.5f, -0.5f, -0.5f), new Vector3(Constants.SIZE-0.5f, -0.5f, -0.5f), new Vector3(Constants.SIZE-0.5f, -0.5f, Constants.SIZE-0.5f), new Vector3(-0.5f, -0.5f, Constants.SIZE-0.5f), new Vector3(0f,-1f, 0f));
		return new ModelInstance(modelBuilder.end());
	}
}
