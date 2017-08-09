package modular.graphics;

import modular.menu.Launcher;

/**
 * @author Group2
 * @version 1.0
 * Generates and contains the state of the various objects to be used by the other graphical modules, including the renderer and boardstate.
 */

public class World {
	/**
	 * reference to the launcher.
	 */
	public Launcher launcher;
	/**
	 * Reference to the initial state.
	 */
	public WorldInitialization world;
	/**
	 * Reference to the renderer.
	 */
	public WorldRenderer render;
	
	public static int finishedMovements = 0;
	/**
	 * Constructor creates the world and renderer.
	 * @param launcher
	 */
	public World(Launcher launcher){
		this.launcher = launcher;
		create();
	}
	
	/**
	 * Generate the relevant fields.
	 */
	private void create(){
		world = new WorldInitialization(launcher);
		render = new WorldRenderer(world);
	}
}
