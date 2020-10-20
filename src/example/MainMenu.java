package example;

import java.awt.event.KeyEvent;
import engine.game.Keyboard;
import engine.main.GameState;
import engine.graphics.Graphics;

public class MainMenu extends GameState {

	public MainMenu(String name) {
		super(name);
	}

	@Override
	public void update(double delta) {
		if(Keyboard.isKeyPressed(KeyEvent.VK_2))
			pop();
	}

	@Override
	public void render(Graphics g) {
		
	}

}
