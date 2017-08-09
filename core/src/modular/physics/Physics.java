package modular.physics;

import com.badlogic.gdx.math.Vector3;

import modular.graphics.cube.Cube;
import modular.graphics.cube.SimpleCube;
import modular.util.Constants;
import modular.util.PhysicsConstants;

public class Physics {
	public static boolean grav = PhysicsConstants.GRAVITY;
	public static boolean fric = PhysicsConstants.DFRICTION;
	
	Vector3 displacement;
	Vector3 velocity;
	Vector3 acceleration;
	Vector3 resForce;
	Vector3 gravForce;
	Vector3 dampForce;
	Vector3 impulse;
	
	boolean noCollision = true;
	boolean obstCollision = false;
	public float obst = 0f;
	
	public float alpha; 
	public Cube cube;
	public void calculateEverything(Cube cube, Vector3 direction, float delta, Vector3 axisOfRotation){
		displacement=new Vector3().set(direction).sub(cube.info.position);
		impulse=new Vector3(displacement).nor().scl(PhysicsConstants.IMPULSE);
		velocity = new Vector3(impulse).scl(1 / Constants.MASS);
		acceleration = new Vector3(impulse).scl(delta);
		this.cube = cube;
		if(Collision.isCollidingFloor(cube)){
			acceleration.y = 0f;
			//restore cube to surface level
			Collision.resolveCollision(cube, new Vector3(0f, -cube.info.min.y, 0f));
			cube.info.position.add(0f,1f,0f);
			noCollision = false;
		} else if(grav)
			while(!Collision.isCollidingFloor(cube))
				acceleration.y = -PhysicsConstants.GRAVCONST;
		else
			acceleration.y = 0f;
		
		/*for(Cube temp: cube.init.getmovingCubes()){
			if(Collision.isColliding(cube, temp)){
				Collision.resolveCollision(cube, new Vector3(cube.info.min).sub(temp.info.min)); //restore the current cube
				
				if(cube.info.min.x - temp.info.min.x < 0f){
					velocity.x = 0f;
					acceleration.x = 0f;
				}
				
				if(cube.info.min.y - temp.info.min.y < 0f){
					velocity.y = 0f;
					acceleration.y = 0f;
				}
				
				if(cube.info.min.z - temp.info.min.z < 0f){
					velocity.z = 0f;
					acceleration.z = 0f;
				}
			}
		}*/
		if(cube.init.getobstacleCubes()!=null)
		for(SimpleCube temp: cube.init.getobstacleCubes()){
			if(Collision.isCollidingObstacle(cube, temp)){
				Collision.resolveCollision(cube, new Vector3(cube.info.min).sub(temp.info.min)); //restore the current cube
				noCollision = false;
				if(cube.info.min.y - temp.info.min.y < 0f){
					velocity.y = 0f;
					acceleration.y = 0f;
				}
			}
			if(!temp.info.min.epsilonEquals(cube.info.min, 0.01f) && Math.abs(temp.info.min.x - cube.info.min.x) < 0.1f && Math.abs(temp.info.min.z - cube.info.min.z) < 0.1f && cube.info.min.y - temp.info.min.y > 0.1f){
				obstCollision = true;
				if(obst <= cube.info.min.y && obst <= temp.info.max.y && obst != cube.info.min.y && obst != cube.info.max.y){
					if(temp.info.min.y < cube.info.min.y)
					obst = 1f;
				}else if(obst != cube.info.min.y && obst != cube.info.max.y)
					obst = 1f;
			}
		}
		
		impulse=new Vector3(velocity).scl(Constants.MASS);
		
		
		gravForce = new Vector3(0f, Constants.MASS * acceleration.y, 0f);
		
		//no vertical friction
		if(fric && acceleration.x > 0f){
			acceleration.x -= PhysicsConstants.DFRICCONST * PhysicsConstants.GRAVCONST;
		} else if(fric && acceleration.z > 0f){
			acceleration.z -= PhysicsConstants.DFRICCONST * PhysicsConstants.GRAVCONST;
		}
		dampForce = new Vector3(acceleration).scl(Constants.MASS);
		
		resForce = new Vector3(gravForce).add(dampForce);
		
		cube.info.velocity=velocity;
		cube.info.acceleration=acceleration;
		cube.info.resultantForce = resForce;
		cube.info.position.add(displacement);
		if(cube.info.position.y >= 0f && (noCollision || obstCollision))
			cube.info.position.sub(0f, cube.info.position.y-obst, 0f);
		//cube.modelInstance.transform.idt().translate((new Vector3()).set(position).interpolate(direction, alpha, interpol).rotate(axisOfRotation, 0f + alpha*(90f - 0f)));//rotate(axisOfRotation, (alpha *(toAngle - fromAngle)));				
		//System.out.println(displacement+ " " + velocity+ " " + acceleration+ " " + resForce+ " " + gravForce+ " " + dampForce + " " +impulse);
	}

	public void firstMovement() {
		if(fric && acceleration.x > 0f){
			acceleration.x -= PhysicsConstants.DFRICCONST * PhysicsConstants.GRAVCONST;
		} else if(fric && acceleration.z > 0f){
			acceleration.z -= PhysicsConstants.DFRICCONST * PhysicsConstants.GRAVCONST;
		}
	}

	public Physics clear() {
		return new Physics();
	}
	
	public float getAlpha(float delta){
		velocity.add(new Vector3(resForce).scl(delta));
		cube.info.velocity=velocity;
		if(Math.abs(velocity.x) > 0.1f)
			alpha = delta * (Math.abs(velocity.x) );//+ Math.abs(cube.info.acceleration.x));
		else if(Math.abs(velocity.z) > 0.1f)
			alpha = delta * (Math.abs(velocity.z) );// + Math.abs(cube.info.acceleration.z));
		if(Math.abs(velocity.y) > 0.1f)
			alpha = delta * (Math.abs(velocity.y) ) ;//+ Math.abs(cube.info.acceleration.y));
		
		return alpha;
	}
	
	public Vector3 calculateNewPos(Vector3 direction, Vector3 position){
		Vector3 result = new Vector3();
		displacement=new Vector3().set(direction).sub(cube.info.position);
		impulse=new Vector3(displacement).nor().scl(PhysicsConstants.IMPULSE);
		velocity = new Vector3(impulse).scl(1 / Constants.MASS);
		acceleration = new Vector3(impulse).scl(1/Constants.MASS);
		

		impulse=new Vector3(velocity).scl(Constants.MASS);
		
		
		gravForce = new Vector3(0f, Constants.MASS * acceleration.y, 0f);
		
		//no vertical friction
		if(fric && acceleration.x > 0f){
			acceleration.x -= PhysicsConstants.DFRICCONST * PhysicsConstants.GRAVCONST;
		} else if(fric && acceleration.z > 0f){
			acceleration.z -= PhysicsConstants.DFRICCONST * PhysicsConstants.GRAVCONST;
		}
		dampForce = new Vector3(acceleration).scl(Constants.MASS);
		
		resForce = new Vector3(gravForce).add(dampForce);
		
		result = position.add(resForce.scl(30f)); //assuming constant 30 fps
		
		return result;
	}
}
