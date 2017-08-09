package modular.util;

public class PhysicsConstants {
	//Constants
	public static float GRAVCONST= 9.8f;
	public static float IMPULSE = 1f;
	public static float SFRICCONST = 0.05f;
	public static float DFRICCONST = 0.01f;
	public static boolean COLLISION = true;
	public static boolean GRAVITY = true;
	public static boolean SFRICTION = true;
	public static boolean DFRICTION = true;
			
	//Final constants
	private final static float rGRAVCONST= GRAVCONST;
	private final static float rIMPULSE = IMPULSE;
	private final static float rSFRICCONST = SFRICCONST;
	private final static float rDFRICCONST = DFRICCONST;
	private final static boolean rCOLLISION = COLLISION;
	private final static boolean rGRAVITY = GRAVITY;
	private final static boolean rSFRICTION = SFRICTION;
	private final static boolean rDFRICTION = DFRICTION;
	
	//Setters
	public static void setGravConst(float x){
		GRAVCONST = x;
	}
	public static void setImpulse(float x){
		IMPULSE = x;
	}
	public static void setDFricConst(float x){
		DFRICCONST = x;
	}
	public static void setSFricConst(float x){
		SFRICCONST = x;
	}
	public static void setGravity(boolean x){
		GRAVITY = x;
	}
	public static void setCollision(boolean x){
		COLLISION = x;
	}
	public static void setSFriction(boolean x){
		SFRICTION = x;
	}
	public static void setDFriction(boolean x){
		DFRICTION = x;
	}
	
	//Setters using multiplication (for user interface)
	public static void userGravConst(float x){
		GRAVCONST = x*GRAVCONST;
	}
	public static void userImpulse(float x){
		IMPULSE = x*IMPULSE;
	}
	public static void userDFricConst(float x){
		DFRICCONST = x*DFRICCONST ;
	}
	public static void userSFricConst(float x){
		SFRICCONST = x*SFRICCONST;
	}
	//Resets all constants to default
	public static void resetPhysics(){
		COLLISION = rCOLLISION;
		GRAVITY = rGRAVITY;
		SFRICTION = rSFRICTION;
		DFRICTION = rDFRICTION;
		DFRICCONST = rDFRICCONST;
		SFRICCONST = rSFRICCONST;
		GRAVCONST = rGRAVCONST;
		IMPULSE = rIMPULSE;			
	}
	public static void print(){
		System.out.println(""+COLLISION+" "+GRAVITY+" "+ SFRICTION+" "+DFRICTION+" "+DFRICCONST+" "+SFRICCONST+" "+GRAVCONST+" "+IMPULSE);
	}
}
