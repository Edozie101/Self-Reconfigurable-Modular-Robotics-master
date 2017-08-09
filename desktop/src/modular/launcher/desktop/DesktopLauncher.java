package modular.launcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import modular.menu.Launcher;

/**
 * @author Group 2
 * @version 1.0
 * 
 * Contains Desktop Launcher (and main method) for libgdx application using Lwjgl for rendering.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 900;
		new LwjglApplication(new Launcher(), config);
	}
}
