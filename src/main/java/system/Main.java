package system;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config =
	            new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280,720);
		config.setTitle("CODE:SAKURA");
		config.setForegroundFPS(30);
	    new Lwjgl3Application( new MyGame(),
	            config
	        );
		
	}
}
