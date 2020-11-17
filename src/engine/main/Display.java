package engine.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.game.GameStateManager;
import engine.game.Keyboard;
import engine.main.Mouse;
import engine.graphics.Color;
import engine.graphics.Graphics;

public class Display {

	private static JFrame window = new JFrame();
	private static MyCanvas canvas;

	private static boolean running;

	private static int frames;
	static double ttps = 0;

	private static int fps;
	private static int ups;

	private static GameStateManager gsm = new GameStateManager();
	private static Graphics graphics;
	private static Mouse mouse;
	private static Keyboard keyboard;
	private static Color backgroundColor;

	private static boolean fullscreen = false;

	private static GraphicsDevice divice;

	private static int windowDecorationStyle;

	static boolean init;
	static BufferedImage icon;

	static String title;

	static boolean undecorated = true;
	
	static void closeOption() {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				int option = JOptionPane.showConfirmDialog(window, 
						"Yo! Are you sure you want to exit "+title+"?", "Close "+title+"?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) System.exit(0);
				if (option == JOptionPane.NO_OPTION) window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
	}

	static void initCanvas() {
		canvas = new MyCanvas();
		canvas.setBackground(java.awt.Color.BLACK);

		mouse = new Mouse(canvas);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);

		keyboard = new Keyboard();
		canvas.addKeyListener(keyboard);
	}

	static void init() {
		if(!init) initCanvas();
		init = true;
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

	public static void setTitle(String title) {
		Display.title = title;
		init();
	}

	public static void setIcon(BufferedImage icon) {
		Display.icon = icon;
		init();
	}

	public static void setWindowType(int windowDecorationStyle) {
		Display.windowDecorationStyle = windowDecorationStyle;
		if(windowDecorationStyle == 0) undecorated = false;
	}

	public static void addGameState(GameState gameState) {
		GameState.setGsm(gsm);
		gsm.states.push(gameState);
	}

	public static void create(int fps) {
		reset(undecorated);
		frames = fps;
		running = true;
		window.pack();
		canvas.start();
	}

	public static int getFps() {return fps;}

	public static void setBackgroundColor(Color backgroundColor) {
		Display.backgroundColor = backgroundColor;
		init();
	}

	public static void setScreenDevice(int screenDivice) {
		divice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[screenDivice];
	}

	public static void setSize(int width, int height) {
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		if(graphics == null) graphics = new Graphics(width, height);
	}

	public static void windowMode() {
		if(fullscreen) {
			if(!undecorated) {
				reset(undecorated);
				window.pack();
			}
			divice.setFullScreenWindow(null);
			fullscreen = false;
			if(windowDecorationStyle != 0) window.getRootPane().setWindowDecorationStyle(windowDecorationStyle);

		}
	}

	public static void fullScreen() {
		if(!fullscreen) {
			if(divice == null) setScreenDevice(0);
			if(!undecorated) reset(true);
			divice.setFullScreenWindow(window);
			if(windowDecorationStyle != 0) window.getRootPane().setWindowDecorationStyle(0);
			fullscreen = true;
		}
	}

	static void reset(boolean undecorated) {
		if(window!=null) window.dispose();
		window = new JFrame();
		closeOption();
		window.setTitle(title);
		window.setIconImage(icon);
		if(undecorated) {
			window.getRootPane().setWindowDecorationStyle(windowDecorationStyle);
			window.setUndecorated(true);
		}
		window.setUndecorated(undecorated);
		window.add(canvas);
		window.setVisible(true);
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
		private int cWidth;
		private int cHeight;

		public MyCanvas() {
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			setDoubleBuffered(true);
		}

		@Override
		public void addNotify() {
			super.addNotify();
		}

		void start() {
			new Thread(this).start();
		}

		void stop() {
			new Thread(this).stop();
		}

		@Override
		public void paint(java.awt.Graphics g) {
			super.paint(g);
			g.drawImage(img, 0, 0, getWidth() + cWidth, getHeight() + cHeight, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}

		@Override
		public void run() {

			int w = getWidth();
			int h = getHeight();

			if(w == 0) w = 1;
			if(h == 0) h = 1;

			img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			g = img.createGraphics();

			if(running) {
				long lastTime = System.nanoTime();
				int frames = 0;
				int updates = 0;
				long lastTimer = System.currentTimeMillis();

				double delta_u = 0;
				double delta_r = 0;

				while(Display.running) {
					double nsPerUpdate_u = 1000000000 / 60;
					double nsPerUpdate_r = 1000000000 / Display.frames;

					long now = System.nanoTime();
					double elapsed = now - lastTime;

					delta_u += elapsed / nsPerUpdate_u;
					delta_r += elapsed / nsPerUpdate_r;

					lastTime = now;

					while (delta_u > 1) {
						updates++;
						Display.update(delta_u);
						delta_u--;
					}

					if (delta_r > 1){
						frames++;
						Display.render(g);
						repaint();
						delta_r--;
					}	

					try {Thread.sleep(1);} 
					catch (InterruptedException e){e.printStackTrace();}

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
