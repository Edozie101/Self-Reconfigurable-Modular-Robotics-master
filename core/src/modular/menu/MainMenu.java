package modular.menu;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import modular.ai.AI;
import modular.ai.History;
import modular.graphics.Drawer;
import modular.state.BoardState;
import modular.state.ReplayState;
import modular.util.AIConstants;
import modular.util.Constants;
import modular.util.PhysicsConstants;
import modular.util.fileHandler.Reader;

/**
 * @author Group 2
 * @version 1.3.1
 * Contains the main menu with the respective interface.
 */
public class MainMenu implements Screen{
	/**
	 * The stage of the main menu containing the various actors (buttons).
	 */
	private Stage stage;
	/**
	 * A file chooser object which provides an interface to use to obtain the necessary file paths.
	 */
	private FileChooser fileChooser;
	/**
	 * File Handler containing the initial configuration for the moving cubes.
	 */
	private FileHandle init;
	/**
	 * File Handler containing the initial configuration for the obstacles.
	 */
	private FileHandle obst;
	/**
	 * File Handler containing the final configuration for the moving cubes.
	 */
	private FileHandle end;
	/**
	 * File Handler containing a replay of a given simulation, ignoring the obstacles.
	 */
	private FileHandle replay;
	/*
	 * File Reader used to read the data from the various files.
	 */
	private Reader reader;
	/**
	 * The regular board state.
	 */
	public BoardState state;
	/**
	 * The replay board state.
	 */
	public ReplayState state2;
	/**
	 * Reference to the launcher
	 */
	private Launcher launcher;
	/*
	 * Reference to the Game superclass of the launcher.
	 */
	private Game game;
	/**
	 * Boolean to determine which board to use for initialization.
	 */
	private boolean replayMode = false;
	/**
	 * @param launcher for reference
	 */

	public MainMenu(final Launcher launcher){
		replayMode = false;
		state = null;
		state2 = null;
		this.launcher = launcher;
		this.game = launcher;
		create();
		
		
	}
	
	/**
	 * Generate the various buttons in their respective positions and their necessary listeners.
	 */
	public void create() {
		reader = new Reader();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Array<TextButton> buttons = new Array<TextButton>();
		buttons.add(new TextButton("SIMULATE", Constants.SKIN, "default")); 			//0
		buttons.add(new TextButton("LOAD REPLAY", Constants.SKIN, "default"));			//1
		buttons.add(new TextButton("LOAD OBSTACLE CONFIG", Constants.SKIN, "default"));	//2
		buttons.add(new TextButton("LOAD END CONFIG", Constants.SKIN, "default"));		//3
		buttons.add(new TextButton("LOAD INITIAL CONFIG", Constants.SKIN, "default"));	//4	

		buttons.add(new TextButton("CHANGE CONSTANTS", Constants.SKIN, "default"));		//5
		buttons.add(new TextButton("CHANGE PHYSICS", Constants.SKIN, "default"));		//6
		buttons.add(new TextButton("CHANGE AI", Constants.SKIN, "default"));			//7
		buttons.add(new TextButton("DEBUG", Constants.SKIN, "default"));				//8
		buttons.add(new TextButton("LAUNCH WITHOUT CONFIG", Constants.SKIN, "default"));//9
		
		int tempY = 75, i = 0, buttonWidth = Constants.VIEWPORT_WIDTH/5, buttonHeight = Constants.VIEWPORT_HEIGHT/7;
		for(TextButton button: buttons){
			if(i < 5){
				button.setPosition(Constants.VIEWPORT_WIDTH - (Constants.VIEWPORT_WIDTH - buttonWidth/2), tempY);
				tempY += buttonHeight;
			} else{
				button.setPosition(Constants.VIEWPORT_WIDTH - (buttonWidth + buttonWidth/2 ), tempY);
				tempY-=buttonHeight;
			}
			i++;
			if(i == 5)
				tempY-=buttonHeight;
			button.setWidth(buttonWidth);
			button.setHeight(buttonHeight);
		}
		
		Dialog dialog = new Dialog("Loading", Constants.SKIN);
		
		buttons.get(0).addListener(new ClickListener(){
			/**
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y){
				modular.util.Constants.setDebug(0);
				dialog.show(stage);
				
				Timer.schedule(new Timer.Task() {
					
					@Override
					public void run() {
						dialog.hide();
					}
				}, 5);
				
				//initialises state depending on whether a replay is loaded or not.
				if(replayMode){
					launcher.replayState = new ReplayState();
					if(obst != null)
						launcher.replayState.setObstState(reader.positionReader(new File(obst.path())));
					launcher.replayState.setReplayState(reader.movesReader(new File(replay.path())));
				} else {
					AI ai = null;
					try {
						ai = new AI(AIConstants.getAlgorithm());
					} catch (IOException e) {
						try {
							ai = new AI(1);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					launcher.boardState = new BoardState();
					if(init != null)
						launcher.boardState.setCurrState(reader.positionReader(new File(init.path())));
					if(obst != null)
						launcher.boardState.setObstState(reader.positionReader(new File(obst.path())));
					if(end != null)
						launcher.boardState.setFinalState(reader.positionReader(new File(end.path())));	
					ai.getAI().initialize(12,launcher.boardState.getBlockedPositions());
					for(Float key: BoardState.currentState.keySet()){
						Vector3 pos1 = BoardState.currentState.get(key);
						Vector3 pos2 = BoardState.finalState.get(key);
						ai.getAI().addUnit((int) pos1.x, (int) pos1.z, (int) pos1.y, (int) pos2.x, (int) pos2.z, (int) pos2.y);
					}
					ai.getAI().Execute();
					launcher.replayState = new ReplayState();
					if(obst != null)
						launcher.replayState.setObstState(reader.positionReader(new File(obst.path())));
					ArrayList<Map<Float, Vector3[]>> history = new ArrayList<>();
					//System.out.println(History.showHistory());
					for(int j = 1; j < History.history.size; j++){
						HashMap<Float, Vector3[]> temp = new HashMap<>();
						temp.put(History.history.get(j++), (new Vector3[]{new Vector3(History.history.get(j++), History.history.get(j++), History.history.get(j++)), new Vector3(History.history.get(j++), History.history.get(j++), History.history.get(j++))}));
						//temp.put((float) History.history.get((j+1)), (new Vector3[]{new Vector3(History.history.get((j+2)),starbots.history.get((j+4)),starbots.history.get((j+3))),new Vector3(starbots.history.get((j+5)),starbots.history.get((j+7)),starbots.history.get((j+6)))}));
						//if(starbots.history.get(j+1) == 0)
							//System.out.println(java.util.Arrays.toString(new Vector3[]{new Vector3(starbots.history.get((j+2)),starbots.history.get((j+4)),starbots.history.get((j+3))),new Vector3(starbots.history.get((j+5)),starbots.history.get((j+7)),starbots.history.get((j+6)))}));
						history.add(temp);
					}
					launcher.replayState.setReplayState(history);
					/*
					Starbots starbots = new Starbots();
					launcher.boardState = new BoardState();
					if(init != null)
						launcher.boardState.setCurrState(reader.positionReader(new File(init.path())));
					if(obst != null)
						launcher.boardState.setObstState(reader.positionReader(new File(obst.path())));
					if(end != null)
						launcher.boardState.setFinalState(reader.positionReader(new File(end.path())));	
					starbots.initialize(launcher.boardState.getBlockedPositions());

					for(Float key: launcher.boardState.currentState.keySet()){
						Vector3 pos1 = launcher.boardState.currentState.get(key);
						Vector3 pos2 = launcher.boardState.finalState.get(key);
						starbots.addUnit((int) pos1.x, (int) pos1.z, (int) pos1.y, (int) pos2.x, (int) pos2.z, (int) pos2.y);
					}
					for(Starbots.Unit key: starbots.units){
						starbots.Pathfinder(key);
					}
					starbots.Mover();
					launcher.replayState = new ReplayState();
					if(obst != null)
						launcher.replayState.setObstState(reader.positionReader(new File(obst.path())));
					ArrayList<Map<Float, Vector3[]>> history = new ArrayList<>();
					for(int j = 0; j < starbots.history.size()-8; j+=8){
						HashMap<Float, Vector3[]> temp = new HashMap<>();
						temp.put((float) starbots.history.get((j+1)), (new Vector3[]{new Vector3(starbots.history.get((j+2)),starbots.history.get((j+4)),starbots.history.get((j+3))),new Vector3(starbots.history.get((j+5)),starbots.history.get((j+7)),starbots.history.get((j+6)))}));
						//if(starbots.history.get(j+1) == 0)
							//System.out.println(java.util.Arrays.toString(new Vector3[]{new Vector3(starbots.history.get((j+2)),starbots.history.get((j+4)),starbots.history.get((j+3))),new Vector3(starbots.history.get((j+5)),starbots.history.get((j+7)),starbots.history.get((j+6)))}));
						history.add(temp);
					}
					//System.out.println(history);
					launcher.replayState.setReplayState(history);
					*/
				}
				game.setScreen(new Drawer(launcher));
				
			}
		}); 
		//LOAD INITIAL CONFIG
		buttons.get(4).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				fileChooser = new FileChooser("Choose Initial Config", Constants.SKIN ){
					@Override
					protected void result(Object object){
						if(object.equals("OK")){
							init = getFile();
							replayMode = false;
						}
					}
				};
				fileChooser.setDirectory(Gdx.files.internal("configs"));
				fileChooser.show(stage);
				
			}
		});
		//LOAD END CONFIG
		buttons.get(3).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				fileChooser = new FileChooser("Choose Final Config", Constants.SKIN ){
					@Override
					protected void result(Object object){
						if(object.equals("OK")){
							end = getFile();
							replayMode = false;
						}
					}
				};
				fileChooser.setDirectory(Gdx.files.internal("configs"));
				fileChooser.show(stage);
			}
		});
		//LOAD OBSTACLE CONFIG
		buttons.get(2).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				fileChooser = new FileChooser("Choose Obstacle Config", Constants.SKIN ){
					@Override
					protected void result(Object object){
						if(object.equals("OK")){
							obst = getFile();
						}
					}
				};
				fileChooser.setDirectory(Gdx.files.internal("configs"));
				fileChooser.show(stage);
			}
		});
		//LOAD REPLAY
		buttons.get(1).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!replayMode){
					fileChooser = new FileChooser("Choose Replay Config", Constants.SKIN ){
						@Override
						protected void result(Object object){
							if(object.equals("OK")){
								replay = getFile();
								replayMode = true;
							}
						}
					};
					fileChooser.setDirectory(Gdx.files.internal("configs"));
					fileChooser.show(stage);
				}
			}
		});
		
		//CHANGE CONSTANTS
		buttons.get(5).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.dispose();
				launcher.setScreen(new ConstantsMenu(launcher));
			}
		});
		
		//CHANGE PHYSICS
			buttons.get(6).addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){		
					stage.dispose();
					launcher.setScreen(new PhysicsMenu(launcher));
				}
			});
				
				
			//CHANGE AI
			buttons.get(7).addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					if(AIConstants.getAlgorithm() == 1){
						AIConstants.setAlgorithm(2);
						buttons.get(7).setText("MONTE CARLO ALGORITHM");
					}else if(AIConstants.getAlgorithm()==2){
						AIConstants.setAlgorithm(3);
						buttons.get(7).setText("COMBINATORIAL ALGORITHM");
					}else{
						AIConstants.setAlgorithm(1);
						buttons.get(7).setText("A* CATERPILLAR ALGORITHM ");
					}
				}
			});
			
			buttons.get(8).addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){						
					Constants.print();
					PhysicsConstants.print();
				}
			});
			buttons.get(9).addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					modular.util.Constants.setDebug(1);
					launcher.boardState = new BoardState();
					if(init != null)
						launcher.boardState.setCurrState(reader.positionReader(new File(init.path())));
					if(obst != null)
						launcher.boardState.setObstState(reader.positionReader(new File(obst.path())));
					if(end != null)
						launcher.boardState.setFinalState(reader.positionReader(new File(end.path())));	
					game.setScreen(new Drawer(launcher));
				}
			});
		for(TextButton tempButton: buttons){
			stage.addActor(tempButton);
		}
	}
	
	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0.4f, 0.8f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
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

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
