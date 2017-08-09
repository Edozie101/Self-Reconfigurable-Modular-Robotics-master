package modular.menu;

import com.badlogic.gdx.Game;
import modular.state.BoardState;
import modular.state.ReplayState;

/**
 * @author Group 2
 * @version 1.0.1
 * Contains a launcher which extends the libgdx Game class, which is necessary in order to change the scene (and thus incorporate a main menu).
 */
public class Launcher extends Game{
	/**
	 * The board state before applying simulation.
	 */
	public BoardState boardState = null;
	/**
	 * The board state which allows to replay a particular simulation.
	 */
	public ReplayState replayState = null;
	/**
	 * Sets the screen of the game to the main menu.
	 */
	@Override
	public void create() {
		setScreen(new MainMenu(this));
	}	
}
