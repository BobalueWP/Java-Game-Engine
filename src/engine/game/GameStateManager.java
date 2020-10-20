package engine.game;

import java.util.Stack;

import engine.graphics.Graphics;
import engine.main.GameState;

public class GameStateManager {
	
	public Stack<GameState> states;
	
	public GameStateManager() {
		states = new Stack<>();
	}
	
	public void update(double delta) {
		states.peek().update(delta);
	}
	
	public void render(Graphics g) {
		states.peek().render(g);
	}
}
