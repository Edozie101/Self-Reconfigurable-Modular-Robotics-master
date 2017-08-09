package modular.menu;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import modular.util.Constants;
import modular.util.PhysicsConstants;;

public class PhysicsMenu implements Screen {
	
	private Stage stage;
	
	private Launcher launcher;

	public PhysicsMenu(final Launcher launcher ){
		this.launcher = launcher;
		create();
		
	}
	
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Array<TextButton> buttons = new Array<TextButton>();
		buttons.add(new TextButton("SET ALL TO DEFAULT", Constants.SKIN, "default")); //0
		buttons.add(new TextButton("COLLISION ON", Constants.SKIN, "default")); //1
		buttons.add(new TextButton("GRAVITY ON", Constants.SKIN, "default")); //2
		buttons.add(new TextButton("SURFACE FRICTION ON ", Constants.SKIN, "default")); //3
		buttons.add(new TextButton("DYNAMIC FRICTION ON ", Constants.SKIN, "default")); //4
		buttons.add(new TextButton("QUIT", Constants.SKIN, "default")); //5
		buttons.add(new TextButton("SET GRAVITY CONSTANT TO INPUT", Constants.SKIN, "default")); //6
		buttons.add(new TextButton("SET SURFACE FRICTION CONSTANT TO INPUT", Constants.SKIN, "default")); //7
		buttons.add(new TextButton("SET DYNAMIC FRICTION CONSTANT TO INPUT", Constants.SKIN, "default")); //8
		buttons.add(new TextButton("SET IMPULSE CONSTANT TO INPUT", Constants.SKIN, "default")); //9
		
		
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
		
		Array<TextField> fields = new Array<TextField>();
		fields.add(new TextField("INPUT FIELD", Constants.SKIN, "default")); //0
		for(TextField field: fields){
				field.setPosition(Constants.VIEWPORT_WIDTH/2-buttonWidth/4, buttonHeight*5+50);		
		}
		
		// QUIT listener
		buttons.get(5).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				launcher.setScreen(new MainMenu(launcher));				
			}
		});
		buttons.get(0).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				PhysicsConstants.resetPhysics();
			}
		});
		buttons.get(1).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(PhysicsConstants.COLLISION == true){
					PhysicsConstants.setCollision(false);
					buttons.get(1).setText("COLLISION OFF");
				}else{
					PhysicsConstants.setCollision(true);
					buttons.get(1).setText("COLLISION ON");
				}
			}
		});
		buttons.get(2).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(PhysicsConstants.GRAVITY==true){
					PhysicsConstants.setGravity(false);
					buttons.get(2).setText("GRAVITY OFF");
				}else{
					PhysicsConstants.setGravity(true);
					buttons.get(2).setText("GRAVITY ON");
				}
			}
		});
		buttons.get(3).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(PhysicsConstants.SFRICTION==true){
					PhysicsConstants.setSFriction(false);
					buttons.get(3).setText("SURFACE FRICTION OFF");
				}else{
					PhysicsConstants.setSFriction(true);
					buttons.get(3).setText("SURFACE FRICTION ON");
				}
			}
		});
		buttons.get(4).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(PhysicsConstants.DFRICTION==true){
					PhysicsConstants.setDFriction(false);
					buttons.get(4).setText("DYNAMIC FRICTION OFF");
				}else{
					PhysicsConstants.setDFriction(true);
					buttons.get(4).setText("DYNAMIC FRICTION ON");
				}
			}
		});
		buttons.get(6).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				String temp = fields.get(0).getText();
				PhysicsConstants.userGravConst(Float.valueOf(temp));
			}
		});
		buttons.get(7).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				String temp = fields.get(0).getText();
				PhysicsConstants.userSFricConst(Float.valueOf(temp));
			}
		});
		buttons.get(8).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				String temp = fields.get(0).getText();
				PhysicsConstants.userDFricConst(Float.valueOf(temp));
			}
		});
		buttons.get(9).addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				String temp = fields.get(0).getText();
				PhysicsConstants.userImpulse(Float.valueOf(temp));
			}
		});
		for(TextButton tempButton: buttons){
			stage.addActor(tempButton);
		}
		for(TextField tempField: fields){
			stage.addActor(tempField);
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
