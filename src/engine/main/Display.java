package engine.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.game.GameStateManager;
import engine.game.Keyboard;
import engine.main.Mouse;
import engine.graphics.Color;
import engine.graphics.Graphics;

public class Display {

	private static JFrame window = new JFrame();
	private static JPanel canvas = new MyCanvas();

	private static boolean running;

	private static int frames;

	private static int fps;
	private static int ups;

	private static GameStateManager gsm = new GameStateManager();
	private static Graphics graphics;
	private static Mouse mouse;
	private static Keyboard keyboard = new Keyboard();
	private static Color backgroundColor;

	private static boolean fullscreen = false;

	private static GraphicsDevice divice;

	public static void setTitle(String title) {
		window.setTitle(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		mouse = new Mouse(canvas);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		canvas.addKeyListener(keyboard);
	}

	private static void update(double delta) {
		if(backgroundColor == null) graphics.backgroundColor(Color.BLACK);
		else graphics.backgroundColor(backgroundColor);
		gsm.update(delta);
		Mouse.reset();
		Keyboard.reset();
	}

	private static void render(Graphics2D g) {
		graphics.setColor(Color.WHITE.changeAlpha(0));
		graphics.drawFillRect(0, 0, getWidth(), getHeight());
		gsm.render(graphics);
		graphics.setColor(Color.WHITE);
		graphics.setFont(Font.DIALOG, Font.BOLD, 12);
		graphics.drawString("FPS:" + fps + " | UPS:" + ups, 0, Graphics.getHeight() - 16);
		graphics.render(g);
	}

	public static void windowMode() {
		if(fullscreen) {
			divice.setFullScreenWindow(null);
			fullscreen = false;
		}
	}

	public static void addGameState(GameState gameState) {
		GameState.setGsm(gsm);
		gsm.states.push(gameState);
	}

	public static void create(int fps) {
		frames = fps;
		window.add(canvas);
		running = true;
		window.pack();
		window.setVisible(true);
	}

	public static int getFps() {return fps;}

	public static void setBackgroundColor(Color backgroundColor) {
		Display.backgroundColor = backgroundColor;
	}

	public static void setScreenDevice(int screenDivice) {
		divice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[screenDivice];
	}

	public static void setSize(int width, int height) {
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		if(graphics == null) graphics = new Graphics(width, height);
	}

	public static void setFullScreen() {
		if(!fullscreen) {
			if(divice == null) setScreenDevice(0);
			divice.setFullScreenWindow(window);
			fullscreen = true;
		}
	}

	public static GameStateManager getGsm() {return gsm;}

	public static float getScaleWidth() {return getWidth() / (float) Graphics.getWidth();}

	public static float getScaleHeight() {return getHeight() / (float) Graphics.getHeight();}

	public static int getWidth() {return canvas.getWidth();}

	public static int getHeight() {return canvas.getHeight();}

	@SuppressWarnings("serial")
	private static class MyCanvas extends JPanel implements Runnable {

		private Graphics2D g;
		private BufferedImage img;
		
		public MyCanvas() {
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			setDoubleBuffered(true);
		}

		@Override
		public void addNotify() {
			super.addNotify();
			new Thread(this).start();
		}
		
		@Override
		public void paint(java.awt.Graphics g) {
			super.paint(g);
			g.drawImage(img, 0, 0, null);
			g.dispose();
			
			Toolkit.getDefaultToolkit().sync();
		}
		
		@Override
		public void run() {
			
			img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			g = img.createGraphics();
			
			if(running) {
				long lastTime = System.nanoTime();
				double nsPerUpdate = 1000000000 / frames;
				int frames = 0;
				int updates = 0;
				long lastTimer = System.currentTimeMillis();
				double delta = 0;

				while(Display.running) {
					long now = System.nanoTime();
					double elapsed = now - lastTime;
					delta += elapsed / nsPerUpdate;
					lastTime = now;
					boolean shouldRender = false;

					while (delta >= 1) {
						updates++;
						Display.update(delta);
						delta--;
						shouldRender = true;
					}

					try {Thread.sleep(1);} 
					catch (InterruptedException e){e.printStackTrace();}

					if (shouldRender == true){
						frames++;
						Display.render(g);
						repaint();
					}	

					if (System.currentTimeMillis() - lastTimer >= 1000) {
						lastTimer += 1000;
						Display.fps = frames;
						Display.ups = updates;
						frames = 0;
						updates = 0;
					}
				}
				Display.fps++;
				Display.ups++;
			}
		}
	}
}
