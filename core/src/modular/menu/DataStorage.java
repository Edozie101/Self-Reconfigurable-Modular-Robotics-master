package modular.menu;

/**
 * @author Group2
 * This class stores values and data used by the program throughout the simulation.
 */
public class DataStorage {
	private float mass = 1;
	private final float MASS = 1;
	private float size = 1;
	private final float SIZE = 1;
	private float side = 1;
	private final float SIDE = 1;
	private float animSpeed = 1;
	private final float ANIMSPEED = 1;
	private float gravConst = 1;
	private final float GRAVCONST = 1;
	private float fricConst = 1;
	private final float FRICCONST = 1;
	private float impulse = 1;
	private final float IMPULSE = 1;
	private boolean collision = true;
	private final boolean COLLISION = true;
	private boolean gravity = true;
	private final boolean GRAVITY = true;
	private boolean dFriction = true;
	private final boolean DFRICTION = true;
	private boolean sFriction = true;
	private final boolean SFRICTION = true;
	private int algorithm = 1;
	private final int ALGORITHM = 1;
	

	public void setMass(float x){
		mass = x;
	}

	public float getMass(){
		return mass;
	}

	public void setSize(float x){
		size = x;
	}
	
	public float getSize(){
		return size;
	}

	public void setSide(float x){
		side = x;
	}
	public float getSide(){
		return side;
	}
	public void setAnimSpeed(float x){
		animSpeed = x;
	}
	public float getAnimSpeed(){
		return animSpeed;
	}
	public void setGravConst(float x){
		gravConst = x;
	}
	public float getGravConst(){
		return gravConst;
	}
	public void setFricConst(float x){
		fricConst = x;
	}
	public float getFricConst(){
		return fricConst;
	}
	public void setImpulse(float x){
		impulse = x;
	}
	public float getImpulse(){
		return impulse;
	}
	public void setCollision(boolean x){
		collision = x;
	}
	public boolean getCollision(){
		return collision;
	}
	public void setGravity(boolean x){
		gravity = x;
	}
	public boolean getGravity(){
		return gravity;
	}
	public void setDFriction(boolean x){
		dFriction = x;
	}
	public boolean getDFriction(){
		return dFriction;
	}
	public void setSFriction(boolean x){
		sFriction = x;
	}
	public boolean getSFriction(){
		return sFriction;
	}
	public void setAlgorithm(int i){
		if(i <= 1){
			algorithm = 1;
		}else if(i == 2){
			algorithm = 2;
		}else{
			algorithm = 3;
		}
	}
	public int getAlgorithm(){
		return algorithm;
	}
	public void rstConstants(){
		mass = MASS;
		size = SIZE;
		side = SIDE;
		animSpeed = ANIMSPEED;
	}
	public void rstPhysics(){
		collision = COLLISION;
		gravity = GRAVITY;
		dFriction = DFRICTION;
		sFriction = SFRICTION;
		gravConst = GRAVCONST;
		fricConst = FRICCONST;
		impulse = IMPULSE;			
	}
	public void rstAlgorithms(){
		algorithm = ALGORITHM;
	}
		
}
