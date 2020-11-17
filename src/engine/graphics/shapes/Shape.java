package engine.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.CopyOnWriteArrayList;
import engine.graphics.Color;
import engine.graphics.Render;

public abstract class Shape {

	int x;
	int y;
	int width;
	int height;
	Color color;
	int[] rgb;
	static CopyOnWriteArrayList<Render> render;
	int renderMode;
	static Graphics2D g;
	static BufferedImage GImage;
	String classErr;
	float fBrightness;
	boolean bBrightness;
	static int w;
	static AffineTransform Tx;

	static int flipType = 0x0;
	public static final int HORIZONTAL = 0x1;
	public static final int VERTICAL = 0x2;
	public static final int BOTH = 0x3;

	static int thetaType = 0;
	
	public Shape(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		w = width;
	}

	public void draw(int renderMode, float fBrightness, boolean bBrightness) {
		this.renderMode = renderMode;
		this.fBrightness = fBrightness;
		this.bBrightness = bBrightness;

		renderText();

		if(renderMode < 2) { 
			dArray();
			render.add(new Render(x, y, width, height, rgb, fBrightness, bBrightness));
		} 

		if(renderMode == 2) {
			dGraphics(g);
			if(flipType != 0) flipType = 0;
			if(thetaType != 0) thetaType = 0;
		}

		if(renderMode == 3) {
			dArray();
			if(rgb != null)
				setGraphicsRGB(rgb);
			else System.err.println("rgb is not set in class: " + classErr);
		}
	}

	abstract void dArray();

	abstract void dGraphics(Graphics2D g);

	void renderText() {}

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

	public int[] getRgb() {
		return rgb;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setRender(CopyOnWriteArrayList<Render> r) {
		render = r;
	}

	public void setRenderMode(int renderMode) {
		this.renderMode = renderMode;
	}

	public static void setG(Graphics2D g) {
		Shape.g = g;
	}

	public static void setGImage(BufferedImage GImage) {
		Shape.GImage = GImage;
	}

	int[] gPixels() {
		int x = this.x;
		int y = this.y;
		int width = this.width;
		int height = this.height;

		if(x > GImage.getWidth()) x = GImage.getWidth();
		if(x < 0) x = 0;

		if(y > GImage.getHeight()) y = GImage.getHeight();
		if(y < 0) y = 0;

		if(x + width > GImage.getWidth()) width = GImage.getWidth() - x;
		if(width < 0) width = 1;

		if(y + height > GImage.getHeight()) height = GImage.getHeight() - y;
		if(height < 0) height = 1;

		return ((DataBufferInt)GImage.getSubimage(x, y, width, height).getRaster().getDataBuffer()).getData();
	}

	void setGraphicsRGB(int[] rgb) {
		int w = width;

		if(x > GImage.getWidth()) x = GImage.getWidth();
		if(x < 0) x = 0;

		if(y > GImage.getHeight()) y = GImage.getHeight();
		if(y < 0) y = 0;

		if(x + width > GImage.getWidth()) width = GImage.getWidth() - x;
		if(width < 0) width = 1;

		if(y + height > GImage.getHeight()) height = GImage.getHeight() - y;
		if(height < 0) height = 1;

		GImage.setRGB(x, y, width, height, rgb, 0, w);
	}

	static void rotate90() {thetaType = 90;}

	static void rotate180() {thetaType = 180;}
	
	static void rotate270() {thetaType = 270;}
	
	static void scale(double sx, double sy) {
		Tx = g.getTransform();
		g.scale(sx, sy);
	}

	static void flip(int flipType) {
		Shape.flipType = flipType;
	}

	static void translate(double tx, double ty) {
		Tx = g.getTransform();
		g.translate(tx, ty);
	}
}
