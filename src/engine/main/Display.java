package engine.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.game.GameStateManager;
import engine.game.Keyboard;
import engine.main.Mouse;
import engine.graphics.Color;

public class Display {

	private static JFrame window = new JFrame();
	static MyCanvas canvas;

	private static boolean running;

	private static int frames;

	private static int fps;
	private static int ups;

	private static GameStateManager gsm = new GameStateManager();
	private static Graphics graphics;
	private static Mouse mouse;
	private static Keyboard keyboard;
	private static Color backgroundColor;

	private static boolean fullscreen = false;

	private static GraphicsDevice divice;

	private static boolean init;
	private static BufferedImage icon;

	private static String title;

	private static boolean undecorated = true;
	private static Dimension size;
	
	private static boolean keepAspectRatio = false;

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

	private static void initCanvas() {
		canvas = new MyCanvas();
		canvas.setBackground(java.awt.Color.BLACK);

		mouse = new Mouse(canvas);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);

		keyboard = new Keyboard();
		canvas.addKeyListener(keyboard);
	}

	private static void init() {
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
		graphics.setColor(backgroundColor);
		graphics.drawFillRect(0, 0, getWidth(), getHeight());
		gsm.render(graphics);
		graphics.setColor(Color.WHITE);
		graphics.setFont(Font.DIALOG, Font.BOLD, 12);
		graphics.drawString("FPS:" + fps + " | UPS:" + ups, 0, Graphics.getHeight() - 16);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(Font.DIALOG, Font.BOLD, 12);
		graphics.drawString("x: " + Mouse.getX() + " y: " + Mouse.getY(), 0, Graphics.getHeight() - 32);
		graphics.drawRect(Mouse.getX() - 1, Mouse.getY() - 1, 3, 3);
		
		graphics.render(g);
	}


	/**
	 * <b>Title</b> is the title of the window. <br>
	 * <b>Icon</b> is the image displayed on the left top window and your windows tab. <br>
	 * <b>Size</b> is the size set for your panel and not your window size. (this is depending on if you use decorated or not!). <br>
	 * <b>Screen Device</b> is your monitor of choice. Your monitor number(your graphics card display number) is related to the number you put. <br>
	 * <b>Background Color</b> is the color you set for the background.<br>
	 * <b>undecorated</b> is the choice of using a border around the panel.
	 * <b>Display Scaling Type</b> is for the full screen either you choose to stretch or keep the aspect ratio.
	 */
	public static void settup(String title, BufferedImage icon, Dimension size, int screenDevice, Color backgroundColor, boolean undecorated, boolean keepAspectRatio) {
		init();
		Display.title = title;
		Display.icon = icon;
		Display.size = size;

		int width = (int)size.getWidth();
		int height = (int)size.getHeight();
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		if(graphics == null) graphics = new Graphics(width, height);

		divice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[screenDevice];

		Display.backgroundColor = backgroundColor;
		
		Display.undecorated = undecorated;
		
		Display.keepAspectRatio = keepAspectRatio;
	}

	public static void addGameState(GameState gameState) {
		GameState.setGsm(gsm);
		gsm.states.push(gameState);
	}

	public static void create(int fps) {
		reset(undecorated);
		frames = fps;
		running = true;
		canvas.start();
		window.pack();
	}

	public static int getFps() {return fps;}

	private static void setScreenDevice(int screenDevice) {divice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[screenDevice];}

	public static void windowMode() {
		if(fullscreen) {
			if(!undecorated) {
				reset(undecorated);
				window.pack();
			}
			divice.setFullScreenWindow(null);
			canvas.setBounds(0, 0, size.width, size.height);
			fullscreen = false;
		}
	}

	public static void fullScreen() {
		if(!fullscreen) {
			if(divice == null) setScreenDevice(0);
			
			if(!undecorated) reset(true);
			window.pack();
			
			divice.setFullScreenWindow(window);
			
			int dWidth = divice.getDisplayMode().getWidth();
			int dHeight = divice.getDisplayMode().getHeight();
			
			if(keepAspectRatio) {
				double s = 1;
				if(dWidth - size.width < dHeight - size.height) s = (double)(dWidth / size.getWidth());
				if(dWidth - size.width > dHeight - size.height) s = (double)(dHeight / size.getHeight());
				
				int x = (int)((dWidth - size.width * s) / 2);
				int y = (int)((dHeight - size.height * s) / 2);
				
				canvas.setBounds(x, y, (int)(size.getWidth() *s), (int)(size.getHeight()*s));

			}
			
			fullscreen = true;
		}
	}

	static void reset(boolean undecorated) {
		if(window!=null) window.dispose();
		window = new JFrame();
		closeOption();
		window.setTitle(title);
		window.setIconImage(icon);
		window.setUndecorated(undecorated);
		window.add(canvas);
		window.setVisible(true);
		window.createBufferStrategy(2);
		window.setBackground(new java.awt.Color(backgroundColor.getARGB()));
	}

	public static GameStateManager getGsm() {return gsm;}

	public static float getScaleWidth() {return getWidth() / (float) Graphics.getWidth();}

	public static float getScaleHeight() {return getHeight() / (float) Graphics.getHeight();}

	public static int getWidth() {return canvas.getWidth();}

	public static int getHeight() {return canvas.getHeight();}

	@SuppressWarnings("serial")
	static class MyCanvas extends JPanel implements Runnable {

		BufferStrategy bs;

		public MyCanvas() {
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
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
		public void run() {

			int w = getWidth();
			int h = getHeight();

			if(w == 0) w = 1;
			if(h == 0) h = 1;

			if(running) {
				long lastTime = System.nanoTime();
				int frames = 0;
				int updates = 0;
				long lastTimer = System.currentTimeMillis();

				double delta_u = 0;
				double delta_r = 0;

				bs = Display.window.getBufferStrategy();

				while(Display.running) {
					double nsPerUpdate_u = 1000000000 / 60;
					double nsPerUpdate_r = 1000000000 / Display.frames;

					long now = System.nanoTime();
					double elapsed = now - lastTime;

					delta_u += elapsed / nsPerUpdate_u;
					delta_r += elapsed / nsPerUpdate_r;

					lastTime = now;

					if (delta_u >= 1) {
						updates++;
						Display.update(delta_u);
						delta_u--;
					}

					if (delta_r >= 1){
						frames++;
						bs = Display.window.getBufferStrategy();

						if (bs == null) {
							Display.window.createBufferStrategy(2);
							return;
						}

						Graphics2D g2 = (Graphics2D)bs.getDrawGraphics();
						g2.clearRect(0, 0, window.getWidth(), window.getHeight());
						if(!undecorated)g2.translate(window.getRootPane().getX(), window.getRootPane().getY());
						Display.render(g2);

						window.getToolkit().sync();
						g2.dispose();
						bs.show();
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
