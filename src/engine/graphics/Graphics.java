package engine.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.graphics.shader.Shader;
import engine.graphics.shapes.GraphicsRenderer;
import engine.main.Image;

public class Graphics {

	static Color bgc = Color.BLACK;
	boolean[] tps;

	private static float scale = 1f;

	private static int width;
	private static int height;

	private Graphics2D g;

	private GraphicsRenderer renderer;
	
	private static BufferedImage image;
	private int[] pixels;
	
	public Graphics(int width, int height) {
		Graphics.width = width;
		Graphics.height = height;

		image = new BufferedImage((int)(width * scale), (int)(height * scale), BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		g = (Graphics2D) image.getGraphics();
		
		renderer = new GraphicsRenderer(2, g, image);
	}

	public void backgroundColor(Color color) {
		bgc = color;
		g.setBackground(java.awt.Color.decode(color.getARGB()+""));
	}

	public void render(Graphics2D g) {
		g.drawImage(image, 0, 0, width, height, null);
		//g.drawImage(gsi.getShaderImage(), 0, 0, width, height, null);
	}

	public void drawScaledImage(Image image, int x, int y, int width, int height) {
		getRenderer().drawScaledImage(image, x, y, width, height);
	}

	public void drawScaledImage(BufferedImage image, int x, int y, int width, int height) {
		getRenderer().drawScaledImage(image, x, y, width, height);
	}

	public void drawImage(BufferedImage image, int x, int y) {
		getRenderer().drawImage(image, x, y);
	}

	public void drawImage(Image image, int x, int y) {
		getRenderer().drawImage(image, x, y);
	}

	public void drawFillRect(int x, int y, int width, int height) {
		getRenderer().drawFillRect(x, y, width, height);
	}

	public void drawRect(int x, int y, int width, int height) {
		getRenderer().drawRect(x, y, width, height);
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		getRenderer().drawLine(x0, y0, x1, y1);
	}

	public void drawCircle(int x, int y, int radius) {
		getRenderer().drawCircle(x, y, radius);
	}

	public void drawStringImage(Image image, String str, int x, int y, Color color) {
		getRenderer().drawStringImage(image, str, x, y, color);
	}

	public void drawString(String str, int x, int y) {
		getRenderer().drawString(str, x, y);
	}

	public static void setScale(float scale) {
		Graphics.scale = scale;
	}

	public void setGWidth(int width) {
		if(Graphics.width != width) Graphics.width = width;
	}

	public void setGHeight(int height) {
		if(Graphics.height != height) Graphics.height = height;
	}

	public void setFont(Font awtFont) {
		getRenderer().setFont(awtFont);
	}

	public static int getWidth() {
		return (int)(width * scale);
	}

	public static int getHeight() {
		return (int)(height * scale);
	}

	public void setFont(String name, int style, int size) {
		getRenderer().setFont(name, style, size);
	}

	public void setColor(Color color) {
		getRenderer().setColor(color);
	}

	/*public void clearRect(int x, int y, int width, int height) {
		gsi.getRenderer().clearRect(x, y, width, height);
	}*/

	public void setBrightness(boolean bBrightness) {
		getRenderer().setBrightness(bBrightness);
	}

	void resetRenderMode() {
		g.setColor(null);
		g.setFont(null);
	}

	//public static Shader getShader() {
	//	return GraphicsImage.getShader();
	//}

	public void rotate90() {
		getRenderer().rotate90();
	}
	
	public void rotate180() {
		getRenderer().rotate180();
	}
	
	public void rotate270() {
		getRenderer().rotate270();
	}
	
	public void scale(double sx, double sy) {
		getRenderer().scale(sx, sy);
	}
	
	
	public void translate(double tx, double ty) {
		getRenderer().translate(tx, ty);
	}
	
	
	public void flip(int flipType) {
		getRenderer().flip(flipType);
	}
	
	GraphicsRenderer getRenderer() {
		return renderer;
	}
}