package modular.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Group2
 * @version 1.0
 * This class contains various constants to be used throughout the program.
 */
public class Constants {
	
	/**
	 * Skin for the menu as read from an editable JSON file.
	 */
	public final static Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

	public static float MASS = 1f;
	private final static float rMASS = MASS;
	/**
	 * Size of the cube in terms of edge length.
	 */
	public static float SIDE = 1f;
	private final static float rSIDE = SIDE;
	/**
	 * Size of the board as a square.
	 */
	public static float SIZE = 12f;
	private final static float rSIZE = SIZE;
	/**
	 * Translation animation speed in terms of units moved / second.
	 */
	public static float SLIDE_SPEED = 1f;
	private final static float rSLIDE_SPEED = SLIDE_SPEED;
	/**
	 * Width of the field for the camera movements.
	 */
	public static int VIEWPORT_WIDTH = 1600;
	private static final int rVIEWPORT_WIDTH = VIEWPORT_WIDTH;
	/**
	 * Height of the field for the camera movements.
	 */
	public static int VIEWPORT_HEIGHT = 900;
	private static final int rVIEWPORT_HEIGHT = VIEWPORT_HEIGHT;
	
	/**
	 * Vector depicting north (+ve x-axis).
	 */
	public final static Vector3 NORTH = new Vector3(1f, 0f, 0f);
	/**
	 * Vector depicting south (-ve x-axis).
	 */
	public final static Vector3 SOUTH = new Vector3(-1f, 0f, 0f);
	/**
	 * Vector depicting east (+ve z-axis).
	 */
	public final static Vector3 EAST = new Vector3(0f, 0f, 1f);
	/**
	 * Vector depicting west (-ve z-axis).
	 */
	public final static Vector3 WEST = new Vector3(0f, 0f, -1f);
	/**
	 * Vector depicting up (+ve y-axis).
	 */
	public final static Vector3 UP = new Vector3(0f, 1f, 0f);
	/**
	 * Vector depicting down (-ve y-axis).
	 */
	public final static Vector3 DOWN = new Vector3(0f, -1f, 0f);
		
	/**
	 * Standard texture for the floor of the board.
	 */
	public final static Texture FLOOR_CUBE_TEXTURE = new Texture(Gdx.files.internal("FloorCube.png"));
	/**
	 * Standard texture for the start area on the board.
	 */
	public final static Texture START_CUBE_TEXTURE = new Texture(Gdx.files.internal("StartCube.png"));
	/**
	 * Standard texture for the end area on the board.
	 */
	public final static Texture END_CUBE_TEXTURE = new Texture(Gdx.files.internal("EndCube.png"));
	/**
	 * Standard texture for the obstacle cubes.
	 */
	public final static Texture OBSTACLE_CUBE_TEXTURE = new Texture(Gdx.files.internal("ObstacleCube.png"));
	/**
	 * Standard texture for the cubes when they haven't moved in the current or previous step of the simulation.
	 */
	public final static Texture REGULAR_CUBE_TEXTURE = new Texture(Gdx.files.internal("RegularCube.png"));
	/**
	 * Standard texture for the cubes when they have moved in the current or previous step of the simulation.
	 */
	public final static Texture MOVING_CUBE_TEXTURE = new Texture(Gdx.files.internal("MovingCube.png"));
	/**
	 * Additional texture.
	 */
	public final static Texture CONFIG_CUBE_TEXTURE = new Texture(Gdx.files.internal("ConfigCube.png"));
	
	/**
	 * @see FLOOR_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material FLOOR_CUBE = new Material(TextureAttribute.createDiffuse(FLOOR_CUBE_TEXTURE));
	/**
	 * @see START_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material START_CUBE = new Material(TextureAttribute.createDiffuse(START_CUBE_TEXTURE));
	/**
	 * @see END_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material END_CUBE = new Material(TextureAttribute.createDiffuse(END_CUBE_TEXTURE));
	/**
	 * @see OBSTACLE_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material OBSTACLE_CUBE = new Material(TextureAttribute.createDiffuse(OBSTACLE_CUBE_TEXTURE));
	/**
	 * @see REGULAR_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material REGULAR_CUBE = new Material(TextureAttribute.createDiffuse(REGULAR_CUBE_TEXTURE));;
	/**
	 * @see MOVING_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material MOVING_CUBE = new Material(TextureAttribute.createDiffuse(MOVING_CUBE_TEXTURE));
	/**
	 * @see CONFIG_CUBE_TEXTURE
	 * Material based on given texture.
	 */
	public final static Material CONFIG_CUBE = new Material(TextureAttribute.createDiffuse(CONFIG_CUBE_TEXTURE));
	
	public static Integer DEBUG = 0;
	
	public static void setMass(float x){
		MASS = x;
	}
	public static void setSize(float size){
		SIZE = size;
	}
	public static void setSide(float side){
		SIDE = side;
	}
	public static void setSpeed(float speed){
		SLIDE_SPEED = speed;
	}
	public static void setWidth(int width){
		VIEWPORT_WIDTH = width;
	}
	public static void setHeight(int height){
		VIEWPORT_HEIGHT = height;
	}
	
	//Setters using multiplication (for user interface)
	public static void userMass(float x){
		MASS = x*MASS;
	}
	public static void userSize(float size){
		SIZE = size*SIZE;
	}
	public static void userSide(float side){
		SIDE = side*SIDE;
	}
	public static void userSpeed(float speed){
		SLIDE_SPEED = speed*SLIDE_SPEED;
	}
	public static void userWidth(int width){
		VIEWPORT_WIDTH = width*VIEWPORT_WIDTH;
	}
	public static void userHeight(int height){
		VIEWPORT_HEIGHT = height*VIEWPORT_HEIGHT;
	}
	
	public static void setDebug(Integer a){
		DEBUG = a;
	}
	
	public static void resetConstants(){
		SIZE = rSIZE;
		SIDE = rSIDE;
		MASS = rMASS;
		SLIDE_SPEED = rSLIDE_SPEED;
		VIEWPORT_WIDTH = rVIEWPORT_WIDTH;
		VIEWPORT_HEIGHT = rVIEWPORT_HEIGHT; 
	}
	public static void print(){
		System.out.println(""+MASS+" "+SIZE+" "+SIDE+" "+SLIDE_SPEED);
	}
}
