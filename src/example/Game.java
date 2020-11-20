package example;

import java.awt.event.KeyEvent;

import engine.game.Button;
import engine.game.GameTime;
import engine.game.Window;
import engine.game.Keyboard;
import engine.game.level.Level;
import engine.game.level.tile.AnimatedTile;
import engine.game.level.tile.NormalTile;
import engine.main.Audio;
import engine.main.Display;
import engine.main.GameState;
import engine.main.Graphics;
import engine.main.Image;
import engine.graphics.Color;
import engine.graphics.shader.Shader;

public class Game extends GameState {

	private Image iButton = new Image("res/buttons/mButton.png", 0, 0, 8, 8, 3, 6);
	private Image iGrass = new Image("res/tiles/tilesheet.png", 32, 0, 16, 16);
	private Image cIGrass = new Image("res/tiles/connected/grass.png", 0, 0, 16, 16, 16, 32);
	private Image iStone = new Image("res/tiles/Tilesheet.png", 48, 0, 16, 16);
	private Image iWater = new Image("res/tiles/tilesheet.png", 0, 16, 16, 16, 4, 1);
	private Image iLava = new Image("res/tiles/tilesheet.png", 0, 32, 16, 16, 4, 1);
	private Image iWindow = new Image("res/windows/mWindow.png", 0, 0, 8, 8, 3, 3);
	private Image iSnow = new Image("res/tiles/tilesheet.png", 16 * 5, 0, 16, 16);
	
	private static Image iFont = new Image("res/fonts/font.png", 0, 0, 8, 8, 16, 16);

	private int x, y;
	private GameState menu = new MainMenu("Menu");

	private Level level;
	private NormalTile grass;
	private NormalTile stone;
	private AnimatedTile water;
	private AnimatedTile lava;
	private NormalTile snow;

	private Button button = new Button("This is a button", 1, 64, iButton);

	private Audio audio;

	private Window window;

	boolean fullScreen;

	GameTime gameTime;

	// WIP  
	//PointLightSource pointLight;
	
	public Game(String name) {
		super(name);

		gameTime = new GameTime(0, 32, 6.0f, 18.0f, 6.0f, 1, 1);

		audio = new Audio("res/laser.wav");
		audio.setGain(0.0f);
		audio.setPan(0.0f);

		water = new AnimatedTile(iWater, 15, Color.BLUE);
		Level.addTile(water);

		grass = new NormalTile(cIGrass, Color.GREEN);
		grass.setConnectedTile(true);
		Level.addTile(grass);

		stone = new NormalTile(iStone, Color.GRAY);
		Level.addTile(stone);
		
		snow = new NormalTile(iSnow, Color.WHITE);
		Level.addTile(snow);
		
		lava = new AnimatedTile(iLava, 15, Color.RED);
		lava.hasBrightness(16, true);
		Level.addTile(lava);

		level = new Level("Map", "res/maps/map.png", 16, gameTime.getDayNightTransition());

		//level.addLayer("res/maps/map_01.png");
		
		window = new Window(iWindow, "Just a window", 0, 0, 0, 0, 4);
		window.addButton("This is a button.", 0, 0, iButton);

		window.setButtonFontImage(iFont);

		window.setButtonsUpToDown(8);
		window.centerButtonsVertical();
		window.centerToWindow();
		
		Shader.addPointLightSource(0, 0, 64, false);
		// WIP
		//pointLight = Graphics.getShader().getCurrentPointLighting();
	}

	@Override
	public void update(double delta) {
		// WIP
		//pointLight.setLocation(Mouse.getX(), Mouse.getY());
		
		gameTime.update();

		level.setBrightness(gameTime.getDayNightTransition());

		if(Keyboard.isKeyPressedOnce(KeyEvent.VK_F11)) fullScreen = !fullScreen;

		if(fullScreen) Display.fullScreen();
		else Display.windowMode();

		if(Keyboard.isKeyPressed(KeyEvent.VK_A)) x-= (int)(1);

		if(Keyboard.isKeyPressed(KeyEvent.VK_D)) x+= (int)(1);

		if(Keyboard.isKeyPressed(KeyEvent.VK_W)) y-= (int)(1);

		if(Keyboard.isKeyPressed(KeyEvent.VK_S)) y+= (int)(1);

		if(Keyboard.isKeyPressed(KeyEvent.VK_1)) push(menu);

		if(Keyboard.isKeyPressed(KeyEvent.VK_P)) audio.play();

		if(Keyboard.isKeyPressed(KeyEvent.VK_O)) audio.stop();

		if(Keyboard.isKeyPressedOnce(KeyEvent.VK_ESCAPE)) System.exit(0);

		level.setMapLocation(x, y);

		button.update();

		window.update();
		
		level.update();
	}

	public void render(Graphics g) {
		level.renderMap(g);

		g.setColor(Color.BLUE.setBrightness(100));
		g.drawFillRect(0, 0, 16, 16);

		g.setColor(Color.RED);
		g.drawRect(32, 0, 16, 16);

		g.setColor(Color.RED);
		g.drawLine(64, 0, 16, 16);

		g.drawScaledImage(iGrass.getImage(), 96, 1, 16, 16);

		g.setColor(Color.MAGENTA);
		g.drawCircle(128, 0, 16);

		gameTime.render(g);

		button.render(g);

		window.render(g);
	}
	
	public static Image getiFont() {
		return iFont;
	}
}
