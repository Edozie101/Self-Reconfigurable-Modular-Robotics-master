package modular.graphics.cube;

import com.badlogic.gdx.math.Vector3;

import modular.util.Constants;

public class CubeInfo {
	public Vector3 position;
	public Vector3 velocity;
	public Vector3 acceleration;
	public Vector3 resultantForce;
	public float mass = Constants.MASS;
	public float size = Constants.SIDE;
	
	public Vector3 min;
	public Vector3 max;
	public Vector3 center;
	public Vector3 halfwidth;
	public Vector3 ppos;
	
	@Override
	public String toString(){
		return "position = " + position.toString() + "; velocity = " + velocity.toString()+ "; acceleration = " + acceleration.toString()+ "; total force = " + resultantForce.toString();
	}
}
