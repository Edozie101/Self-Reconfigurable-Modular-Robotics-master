package modular.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import modular.menu.Launcher;

/**
 * @author Group2
 * @version 2.1
 * Class represents the main simulation screen which renders all the various modules, as well as their update functions for moving.
 */
public class Drawer  implements Screen {
	/**
	 * The specific tag of this drawer (as the class name).
	 */
	public static final String tag = Drawer.class.getName();
	/**
	 * The world state.
	 */
	private World w;
	/**
	 * The world initialisation state.
	 */
	public WorldInitialization world;
	/**
	 * The renderer responsible for update animation and positions.
	 */
	public WorldRenderer render;
	
	/**
	 * Current elapsed time in the simulation in terms of continuous units.
	 */
	public static float elapsedTime = 0;
	
	/**
	 * The launcher object which contains the reference to the board state.
	 */
	private Launcher launcher;

	
	public int cubeSelected = 0;
	public int prevCubeSelected = cubeSelected;
	
	/**
	 * Constructor invokes the Screen super constructor, sets the local launcher reference and generates the world based on the board state.
	 * @param launcher
	 */
	public Drawer(Launcher launcher){
		super();
		this.launcher = launcher;
		create();
	}
	
	/**
	 * Generate the world, the initial configuration and the renderer.
	 */
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_ERROR);
		w = new World(launcher);
		world = w.world;
		render = w.render;
	}

	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render (float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			launcher.create();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			launcher.setScreen(new SimulationMenu(launcher, this));
		}
		if(cubeSelected != 0)
		{
			world.movingCubes.get(cubeSelected-1).selectedCube();
			if(prevCubeSelected != 0){
				world.movingCubes.get(prevCubeSelected-1).canChange = true;
				world.movingCubes.get(prevCubeSelected-1).goAnim();	
			}
		}
		world.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(100/255.0f, 149/255.0f, 237/255.0f,
				 255/255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update cube positions
		for(int i = 0; i < world.getmovingCubes().size; i++){
		//for(Cube cube: world.getmovingCubes()){
			world.getmovingCubes().get(i).update();
			world.getmovingCubes().get(i).update(true);
		}
		//render entire scene
		render.render();
	}
	
	/**
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose () {
		render.dispose();
	}
	
	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
    public void resume () {
    }

    /**
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize (int width, int height) {
    	render.resize(width, height);
    }

    /**
     * @see com.badlogic.gdx.Screen#pause()
     */
    @Override
    public void pause () {
    }

	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}   
}
