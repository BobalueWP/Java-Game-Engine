package engine.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
	
	public Shape(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(int renderMode, float fBrightness, boolean bBrightness) {
		renderText();

		if(renderMode < 2) { 
			dArray();
			render.add(new Render(x, y, width, height, rgb, fBrightness, bBrightness));
		} else {
			dGraphics(g);
			// for the shader
			int size = width * height;
			if(size < 0) size = 0;
			rgb = new int[size];
			render.add(new Render(x, y, width, height, rgb, fBrightness, bBrightness));
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
	
	/*AffineTransform tx;
	AffineTransformOp op;

	void flipImageH() {
		tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
	}

	void flipImageV() {
		tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -image.getHeight(null));
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
	}

	void flipImage() {
		tx = AffineTransform.getScaleInstance(-1, -1);
		tx.translate(-image.getWidth(null), -image.getHeight(null));
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
	}*/
}
