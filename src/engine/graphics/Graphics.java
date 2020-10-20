package engine.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.graphics.shader.Shader;
import engine.main.Image;

public class Graphics {

	static Color bgc = Color.BLACK;
	boolean[] tps;

	private static float scale = 1f;

	private int width;
	private int height;

	static GraphicsImage gsi;
	private Graphics2D g;

	public Graphics(int width, int height) {
		gsi = new GraphicsImage(width, height, scale);

		this.width = width;
		this.height = height;

		g = (Graphics2D) gsi.getImage().getGraphics();
	}

	public void backgroundColor(Color color) {
		bgc = color;
		if(gsi.getRenderMode() == 3)g.setBackground(java.awt.Color.decode(color.getARGB()+""));
	}

	public void render(Graphics2D g) {
		gsi.render();
		gsi.reset();
		g.drawImage(gsi.getImage(), 0, 0, width, height, null);
		g.drawImage(gsi.getShaderImage(), 0, 0, width, height, null);
	}

	public void drawScaledImage(Image image, int x, int y, int width, int height) {
		gsi.getRenderer().drawScaledImage(image, x, y, width, height);
	}

	public void drawScaledImage(BufferedImage image, int x, int y, int width, int height) {
		gsi.getRenderer().drawScaledImage(image, x, y, width, height);
	}

	public void drawImage(BufferedImage image, int x, int y) {
		gsi.getRenderer().drawImage(image, x, y);
	}

	public void drawImage(Image image, int x, int y) {
		gsi.getRenderer().drawImage(image, x, y);
	}

	public void drawFillRect(int x, int y, int width, int height) {
		gsi.getRenderer().drawFillRect(x, y, width, height);
	}

	public void drawRect(int x, int y, int width, int height) {
		gsi.getRenderer().drawRect(x, y, width, height);
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		gsi.getRenderer().drawLine(x0, y0, x1, y1);
	}

	public void drawCircle(int x, int y, int radius) {
		gsi.getRenderer().drawCircle(x, y, radius);
	}

	public void drawStringImage(Image image, String str, int x, int y, Color color) {
		gsi.getRenderer().drawStringImage(image, str, x, y, color);
		/*int iWidth = image.getImages()[0].getWidth();

		for (int i = 0; i < str.length(); i++) {
			int in = str.getBytes()[i];

			int xa = in % 16;
			int ya = in / 16;

			BufferedImage img = image.getImage(xa + ya * 16);

			Graphics2D g2 = img.createGraphics();

			g2.setComposite(AlphaComposite.SrcAtop);
			g2.setColor(java.awt.Color.decode(color.getARGB()+""));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.dispose();

			g.drawImage(img, null, x + (i * (iWidth)), y);
		}*/
	}

	public void drawString(String str, int x, int y) {
		gsi.getRenderer().drawString(str, x, y);
	}

	public static void setScale(float scale) {
		Graphics.scale = scale;
	}

	public void setGWidth(int width) {
		if(this.width != width) this.width = width;
	}

	public void setGHeight(int height) {
		if(this.height != height) this.height = height;
	}

	public void setFont(Font awtFont) {
		gsi.getRenderer().setFont(awtFont);
	}

	public static int getWidth() {
		return gsi.getWidth();
	}

	public static int getHeight() {
		return gsi.getHeight();
	}

	public void setFont(String name, int style, int size) {
		gsi.getRenderer().setFont(name, style, size);
	}

	public void setColor(Color color) {
		gsi.getRenderer().setColor(color);
	}

	/*public void clearRect(int x, int y, int width, int height) {
		gsi.getRenderer().clearRect(x, y, width, height);
	}*/

	public void setBrightness(boolean bBrightness) {
		gsi.getRenderer().setBrightness(bBrightness);
	}

	void resetRenderMode() {
		g.setColor(null);
		g.setFont(null);
	}

	public static Shader getShader() {
		return GraphicsImage.getShader();
	}
}