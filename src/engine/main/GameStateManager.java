package engine.main;

import java.util.Stack;

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
