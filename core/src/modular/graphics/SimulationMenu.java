package modular.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import modular.graphics.cube.Cube;
import modular.menu.Launcher;
import modular.util.Constants;;

public class SimulationMenu implements Screen{
	
	private Stage stage;
	
	private Launcher launcher;
	
	private Drawer drawer;
	
	private Game game;
	
	private WorldInitialization world;
	
	private SelectBox<String> cubeList;
	
	private String[] cubes;
	
	private Array<TextButton> buttons;
	
	private BitmapFont font;
	
	private SpriteBatch batch;
	
	private String text;
	private String info;
	
	private int buttonWidth;
	private int buttonHeight;
	
	public SimulationMenu(Launcher launcher, Drawer drawer){
		this.launcher = launcher;
		this.drawer = drawer;
		this.game = launcher;
		this.world = drawer.world;
		create();
	}
	
	public void create(){
		stage = new Stage(new ScreenViewport());
		
		Gdx.input.setInputProcessor(stage);
	
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		batch = new SpriteBatch();
		
		cubes = new String[world.movingCubes.size+1];
		cubeList = new SelectBox<String>(Constants.SKIN, "default");
		
		buttons = new Array<TextButton>();
		buttons.add(new TextButton("QUIT", Constants.SKIN, "default")); 			//0
		buttons.add(new TextButton("???", Constants.SKIN, "default"));				//1
		buttonWidth = Constants.VIEWPORT_WIDTH/5; 
		buttonHeight = Constants.VIEWPORT_HEIGHT/7;
		int tempY = 75, i = 0;
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
		
		cubeList.setPosition(Constants.VIEWPORT_WIDTH - (Constants.VIEWPORT_WIDTH - buttonWidth/2), buttonHeight*5+50);
		cubes[0] = "None selected";
		int cubeCount = 1;
		for(Cube cube: world.movingCubes){
			cubes[cubeCount] = ("Cube "+cube.getID());
			cubeCount++;
		}
		cubeList.setItems(cubes);
		cubeList.setSelectedIndex(0);
		
		buttons.get(0).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){						
				int temp = cubeList.getSelectedIndex();
				if(temp != drawer.cubeSelected && drawer.cubeSelected != 0){
					drawer.prevCubeSelected = drawer.cubeSelected;
				}		
				drawer.cubeSelected = temp;
				drawer.render.init();
				game.setScreen(drawer);
				
			}
		});
		buttons.get(1).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){						
				buttons.get(1).setText("????");
			}
		});
		
		//for(TextButton tempButton: buttons){
			stage.addActor(buttons.get(0));
		//}
		stage.addActor(cubeList);
		
	}
	
	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		stage.act(delta);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){						
			int temp = cubeList.getSelectedIndex();
			if(temp != drawer.cubeSelected && drawer.cubeSelected != 0){
				drawer.prevCubeSelected = drawer.cubeSelected;
			}		
			drawer.cubeSelected = temp;
			drawer.render.init();
			game.setScreen(drawer);		
		}
					
		Gdx.gl.glClearColor(0f, 0.4f, 0.8f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(cubeList.getSelectedIndex() == 0 && drawer.cubeSelected == 0){
			text = "No cube selected";
			info = " ";
		}else if(cubeList.getSelectedIndex() == 0){
			text = "Selected cube "+world.movingCubes.get(drawer.cubeSelected-1).getID();
			info = world.movingCubes.get(drawer.cubeSelected-1).info.toString();
		}else{
			text = "Selected cube "+world.movingCubes.get(cubeList.getSelectedIndex()-1).getID();
			info = world.movingCubes.get(cubeList.getSelectedIndex()-1).info.toString();
		}
		
		batch.begin();	
		font.draw(batch,text,Constants.VIEWPORT_WIDTH - (Constants.VIEWPORT_WIDTH - buttonWidth/2), buttonHeight*6);
		font.draw(batch,info,Constants.VIEWPORT_WIDTH - (Constants.VIEWPORT_WIDTH - buttonWidth/2)+40, buttonHeight*6-25);
		batch.end();
		
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
