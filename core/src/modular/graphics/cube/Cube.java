package modular.graphics.cube;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Queue;

import modular.graphics.World;
import modular.graphics.WorldInitialization;
import modular.physics.Collision;
import modular.physics.Physics;
import modular.util.Constants;

/**
 * @author Group2
 * @version 1.7.3
 * Contains the basic requirements for any moving modules as well as a sliding animation.
 */
public class Cube extends Model implements MovingBox{
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
	 * @see modular.graphics.cube.Box#material
	 */
	public Material material;
	/**
	 * @see modular.graphics.cube.Box#id
	 */
	public float id;	
	/**
	 * @see modular.graphics.cube.MovingBox#transform
	 */
	public Matrix4 transform;
	/**
	 * @see modular.graphics.cube.MovingBox#direction
	 */
	public Vector3 direction = new Vector3();
	/**
	 * A queue containing the next position at every point in the simulation.
	 */
	public Queue<Vector3> nextPosition;
	/**
	 * A queue containing the previous position at every point in the simulation.
	 */
	public Queue<Vector3> previousPosition;
	/**
	 * @see modular.graphics.cube.MovingBox#hasToMove
	 */
	public boolean hasToMove = true; //to be updated when cube has a location to move to on next tick
	/**
	 * The next position for the cube at the next step of the simulation.
	 */
	public Vector3 nextPos;
	/**
	 * The current position of the cube at this particular step in the simulation.
	 */
	public Vector3 previousPos;
	/**
	 * The specific instance of the animation to be applied during the next update.
	 */
	private SlidingAnimation anim = null;
	/**
	 * Whether the cube is allowed to move (i.e. if the animation is finished).
	 */
	public boolean allow = true;
	public WorldInitialization init;
	public Physics physics;
	public boolean canChange = true;
	/**
	 * Creates a cube with the given parameters.
	 * @param id @see id
	 * @param x @see position
	 * @param y @see position
	 * @param z @see position
	 */
	public Cube(float id, float x, float y, float z){
		this.id = id;
		physics = new Physics();
		updateInfo(x,y,z);
		transform = new Matrix4();
		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(Constants.SIDE, Constants.SIDE, Constants.SIDE, (this.material = Constants.REGULAR_CUBE), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		modelInstance = new ModelInstance(model);
		nextPosition = new Queue<>();
		previousPosition = new Queue<>();
	}
	
	private void updateInfo(float x, float y, float z){
		info = new CubeInfo();
		info.position = new Vector3(x, y, z);
		info.velocity = new Vector3();
		info.acceleration = new Vector3();
		info.resultantForce = new Vector3();
		info.min = info.position;
		info.max = new Vector3(info.position.x + info.size, info.position.y + info.size, info.position.z + info.size);
		info.center = new Vector3(info.min).add(info.max).scl(0.5f);
		info.halfwidth = new Vector3(0.5f, 0.5f, 0.5f);
	}
	/**
	 * Updates the cube (and animates it) based on the given new position.
	 * @param position to moved to
	 */
	public void update(Vector3 position){
		this.info.position = position;
		this.direction = position.sub(this.info.position);
		this.material = Constants.MOVING_CUBE;
		animate();
		update(true);

	}
	
	/**
	 * @see modular.graphics.cube.Box#update()
	 */
	public void update() {
		if(hasToMove){
			if(allow && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
				if(nextPosition.size == 0){
					goIdle();
					hasToMove = false;
					allow = true;
				} else{
					//System.out.println(nextPosition + " " + previousPosition);
					nextPos = nextPosition.removeFirst();
					if(previousPos == null)
						previousPos = new Vector3(nextPos);
					direction = (new Vector3(nextPos)).sub(previousPos);
					if(!previousPos.epsilonEquals(nextPos, 0.001f));
						allow = true;
					animate();
					previousPos.set(nextPos);
					//info.position = modelInstance.transform.getTranslation(new Vector3());
				}
			}
		} else
			goIdle();
		if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
			System.out.println(info);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
			if(info.position.y > 0.001f){
				Gdx.input.setInputProcessor(new CameraInputController(new PerspectiveCamera()));
				physics.calculateEverything(this, new Vector3(info.position).sub(Vector3.Y.scl(info.position.y+1)), 0, new Vector3(0f, 0f, 0f));
				
				if(info.position.y >= 0f)
					this.modelInstance.transform.setToTranslation(info.position);
			}
		}
	}
	
	/**
	 * @see update()
	 * @param x to distinguish it from the regular update method. Currently not used for anything.
	 */
	public void update(boolean x) {
		final float delta = Math.min(Gdx.graphics.getDeltaTime(), 1/30f);
		if(anim != null && hasToMove){
			anim.update(delta);
			if(this.info.position.x - Math.floor(this.info.position.x) > 0.0001f || this.info.position.y - Math.floor(this.info.position.y) > 0.0001f || this.info.position.z - Math.floor(this.info.position.z) > 0.0001f)
				this.modelInstance.transform.setToTranslation((float)Math.floor(this.info.position.x), (float)Math.floor(this.info.position.y), (float)Math.floor(this.info.position.z));
		}
	}
	
	/**
	 * @see com.badlogic.gdx.graphics.g3d.Model#dispose()
	 */
	@Override
	public void dispose(){
		super.dispose();
		info.position = null;
		direction = null;
		material.clear();
		nextPosition.clear();
		previousPosition.clear();
		info = null;
	}
	
	/**
	 * @see modular.graphics.cube.MovingBox#goIdle()
	 */
	@Override
	public void goIdle(){
		if(canChange)
		this.modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(Constants.REGULAR_CUBE_TEXTURE));
	}
	
	/**
	 * @see modular.graphics.cube.MovingBox#goAnim()
	 */
	@Override
	public void goAnim(){
		if(canChange)
		this.modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(Constants.MOVING_CUBE_TEXTURE));
	}

	public void selectedCube(){
		canChange = true;
		
		this.modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(Constants.START_CUBE_TEXTURE));
	}
	
	public String getID(){
		return ""+id;
	}
	/**
	 * @see modular.graphics.cube.MovingBox#animate()
	 */
	@Override
	 public void animate(){
			goAnim();
			anim = new SlidingAnimation();
			anim.cube = this;
			anim.from.set(previousPos);
			anim.to.set(nextPos);
			physics.alpha = 0f;
			anim.speed = Constants.SLIDE_SPEED;
			anim.alpha = 0;
			anim.fromAngle = 0;
			anim.toAngle = 90f;
			if(direction.y <= -0.1f || direction.y >= 0.1f){
				if(direction.x < 0f || direction.z < 0f){
					if(direction.z < 0f)
						anim.axisOfRotation.set(new Vector3(-1,0,0));
					else
						anim.axisOfRotation.set(new Vector3(0,0,1));
				} else{
					if(direction.z > 0f)
						anim.axisOfRotation.set(new Vector3(1,0,0));
					else
						anim.axisOfRotation.set(new Vector3(0,0,-1));
				}
			}
			//anim.axisOfRotation.set((new Vector3().sub(direction)).crs(rotDirection));
			if(nextPosition.size == 0){
				World.finishedMovements++;
			}
		}
		
		/**
		 * @author Group2
		 * @version 4.1.0
		 * A unique sliding animation based on the (smooth) linear interpolation between two given position.
		 * Can also perform rotation about an axis (not currently implemented.
		 */
		public static class SlidingAnimation extends Animation{
			public final Vector3 from = new Vector3();
			public final Vector3 to = new Vector3();
			public final Vector3 axisOfRotation = new Vector3();
			public float speed;
			public float alpha;
			public float fromAngle;
			public float toAngle;
			public Cube cube;
			/**
			 * Linearly interpolates the object position based on the current frame.
			 * @param delta
			 */
			public void update(float delta){
				cube.physics.calculateEverything(cube, to, delta, axisOfRotation);
				alpha +=cube.physics.getAlpha(delta);
				if(alpha >= Constants.SIDE){
					alpha = Constants.SIDE;
					cube.allow = true;
					cube.info.position.set(to);
				}
				Vector3 translate;
				if(Physics.grav && Physics.fric)
					translate = (new Vector3()).set(from).interpolate(to, alpha, Interpolation.pow2);
				else
					translate = (new Vector3()).set(from).interpolate(to, alpha, Interpolation.smooth2);
				cube.modelInstance.transform.idt().translate(translate).rotate(axisOfRotation, fromAngle + alpha*(toAngle - fromAngle));//rotate(axisOfRotation, (alpha *(toAngle - fromAngle)));
				cube.physics = cube.physics.clear();
			}
		}
}
