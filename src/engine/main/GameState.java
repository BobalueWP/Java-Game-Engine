package engine.main;

import engine.game.GameStateManager;

public abstract class GameState {

	private static GameStateManager gsm;
	private String name;
	
	public GameState(String name) {
		this.name = name;
	}
	
	public abstract void update(double delta);
	public abstract void render(Graphics g);
	
	public void pop() {
		gsm.states.pop();
	}
	
	public void push(GameState gameState) {
		gsm.states.push(gameState);
	}
	
	static void setGsm(GameStateManager gsm) {
		GameState.gsm = gsm;
	}
	
	public GameStateManager getGsm() {
		return gsm;
	}
	
	public String getName() {
		return name;
	}
	
	public GameState getState(String name) {
		for (int i = 0; i < gsm.states.size(); i++)
			if(gsm.states.get(i).name == name) return gsm.states.get(i);
		return null;
	}
}
