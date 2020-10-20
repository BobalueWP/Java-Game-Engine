package engine.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Render {

	private int[] pixels;
	private int width;
	private int height;
	private int x;
	private int y;
	private float fBrightness = 1;
	private boolean bBrightness = true;
	private BufferedImage image;
	static Graphics2D g;
	
	public Render(int x, int y, int width, int height, int[] pixels, float fBrightness, boolean bBrightness) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.bBrightness = bBrightness;
		this.fBrightness = fBrightness;
	}
	
	public Render(int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
	}

	public int getPixel(int x, int y) {
		if(bounds(x, y) & pixels != null)
		return pixels[x + y * getWidth()];
		return 0;
	}
	
	private boolean bounds(int x, int y) {
		return x >= 0 & x < width & y >= 0 & y < height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getBrightness() {
		return fBrightness;
	}
	
	public boolean hasBrightness() {
		return bBrightness;
	}
	
	public void setbBrightness(boolean bBrightness) {
		this.bBrightness = bBrightness;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
