package modular.menu;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import modular.util.Constants;;

/**
 * @author Group2
 * 
 * Contains the AI menu with the respective interface, class not used in the actual program right now
 */
public class AIMenu implements Screen {
	
	private Stage stage;
	
	private Launcher launcher;
	
	/**
	 * @param launcher for reference
	 */
	public AIMenu(final Launcher launcher ){
		this.launcher = launcher;
		create();
		
	}
	/**
	 * Generate the various buttons in their respective positions and their necessary listeners.
	 */
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Array<TextButton> buttons = new Array<TextButton>();
		buttons.add(new TextButton("QUIT", Constants.SKIN, "default")); //0
		buttons.add(new TextButton("ALGORITHM #1 (ACTIVE)", Constants.SKIN, "default")); //1
		buttons.add(new TextButton("ALGORITHM #2", Constants.SKIN, "default")); //2
		buttons.add(new TextButton("ALGORITHM #3", Constants.SKIN, "default")); //3
		
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
		// quit listener
		buttons.get(0).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				stage.dispose();
				launcher.setScreen(new MainMenu(launcher));				
			}
		});
		buttons.get(1).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
					buttons.get(1).setText("ALGORITHM #1 (ACTIVE)");
					buttons.get(2).setText("ALGORITHM #2");
					buttons.get(3).setText("ALGORITHM #3");
			}
		});
		buttons.get(2).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
					buttons.get(1).setText("ALGORITHM #1");
					buttons.get(2).setText("ALGORITHM #2 (ACTIVE)");
					buttons.get(3).setText("ALGORITHM #3");
			}
		});
		buttons.get(3).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
					buttons.get(1).setText("ALGORITHM #1");
					buttons.get(2).setText("ALGORITHM #2");
					buttons.get(3).setText("ALGORITHM #3  (ACTIVE)");
			}
		});
		
		for(TextButton tempButton: buttons){
			stage.addActor(tempButton);
		}
	}
	
	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0.4f, 0.8f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
