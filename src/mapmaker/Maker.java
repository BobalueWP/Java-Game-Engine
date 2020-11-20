package mapmaker;


import java.awt.Dimension;
import java.io.File;
import engine.game.Button;
import engine.game.Window;
import engine.main.Display;
import engine.main.GameState;
import engine.main.Graphics;
import engine.main.Image;
import engine.graphics.Color;

public class Maker extends GameState {

	private Image buttonImage = new Image("res/buttons/mButton.png", 0, 0, 8, 8, 3, 6);
	private Button save = new Button("Save", 0, 0, buttonImage);
	private Image windowImage = new Image("res/windows/mWindow.png", 0, 0, 8, 8, 3, 3);

	private Window window = new Window(windowImage, "Main", 0, 0, 800, 0, 8);

	String title = "Map Maker";
	Dimension size = new Dimension(800, 600);
	Color backgroundColor = Color.BLACK;
	
	public Maker(String name) {
		super(name);
		Display.settup(title, null, size, 0, backgroundColor, true, true);
		Display.addGameState(this);
		Display.create(60);
		
		window.addbutton(save);
		
		//window.
	}

	public void update(double delta) {
		window.update();
		
		if(save.isClicked()) {
			File mapFiles = new File("res/maps/");
			if(!mapFiles.isDirectory()) mapFiles.mkdirs();
		}
	}

	public void render(Graphics g) {
		window.render(g);
	}

	public static void main(String[] args) {
		new Maker("Main");
	}
}
