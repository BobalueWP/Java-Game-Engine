package example;

import engine.main.Display;
import engine.main.Image;
import engine.graphics.Color;
import engine.graphics.Graphics;

public class Boot {

	public Boot() {
		Display.setTitle("Game Engine");
		
		// this is the scale for the image that is being drawn to
		Graphics.setScale(1.0f);
		
		// window size
		Display.setSize(1024, 1024);
		
		// sets the default window panel
		//Display.setWindowType(0);
		
		// sets your very own icon for the window.
		Display.setIcon(new Image("res/tiles/tilesheet.png", 32, 0, 16, 16).getImage());
		
		//sets the screen to window mode. this is automatically set.
		//Display.windowMode();
		
		Display.setBackgroundColor(Color.BLUE);
		Display.addGameState(new Game("Game"));
		Display.create(60);
	}
	
	public static void main(String[] args) {
		new Boot();
	}
}
