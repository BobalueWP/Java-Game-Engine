package example;

import engine.main.Display;
import engine.graphics.Color;
import engine.graphics.Graphics;

public class Boot {

	public Boot() {
		Display.setTitle("Game Engine");
		Graphics.setScale(0.5f);
		Display.setSize(1920, 1080);
		Display.setBackgroundColor(Color.BLUE);
		Display.addGameState(new Game("Game"));
		Display.create(60);
	}
	
	public static void main(String[] args) {
		new Boot();
	}
}
