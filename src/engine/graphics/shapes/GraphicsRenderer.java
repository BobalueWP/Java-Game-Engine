package engine.graphics.shapes;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.graphics.Color;
import engine.graphics.Render;
import engine.main.Cache;
import engine.main.Image;
import engine.main.Vector2f;

public class GraphicsRenderer {

	private CopyOnWriteArrayList<Render> render = new CopyOnWriteArrayList<>();

	private Color color = Color.WHITE;
	private Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, 12);

	private float fBrightness = 1;
	private boolean bBrightness = true;

	private int renderMode;

	public GraphicsRenderer(int renderMode, Graphics2D g, BufferedImage GImage) {
		this.renderMode = renderMode;
		Shape.setG(g);
		Shape.setGImage(GImage);
	}

	public void drawScaledImage(Image image, int x, int y, int width, int height) {
		engine.graphics.shapes.Image i = new engine.graphics.shapes.Image(image, x, y, width, height);
		i.setRender(render);
		i.setColor(color);
		i.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawScaledImage(BufferedImage image, int x, int y, int width, int height) {
		engine.graphics.shapes.Image i = new engine.graphics.shapes.Image(image, x, y, width, height);
		i.setRender(render);
		i.setColor(color);
		i.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawImage(Image image, int x, int y) {
		engine.graphics.shapes.Image i = new engine.graphics.shapes.Image(image, x, y);
		i.setRender(render);
		i.setColor(color);
		i.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawImage(BufferedImage image, int x, int y) {
		engine.graphics.shapes.Image i = new engine.graphics.shapes.Image(image, x, y);
		i.setRender(render);
		i.setColor(color);
		i.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawFillRect(int x, int y, int width, int height) {
		engine.graphics.shapes.FillRectangle fr = new engine.graphics.shapes.FillRectangle(x, y, width, height);
		fr.setRender(render);
		fr.setColor(color);
		fr.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawRect(int x, int y, int width, int height) {
		engine.graphics.shapes.Rectangle r = new engine.graphics.shapes.Rectangle(x, y, width, height);
		r.setRender(render);
		r.setColor(color);
		r.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		Line l = new Line(x0, y0, x1, y1);
		l.setRender(render);
		l.setColor(color);
		l.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawCircle(int x, int y, int radius) {
		/*if(renderMode < 2) {
			String id = "c"+x+""+y+""+radius;
			if(Cache.getCache(id) == null) {
				Cache cirCache = new Cache(id);
				int width = radius * 2 + 1;
				int height = radius * 2 + 1;

				int[] rgb = new int[width * height];

				for (int ya = 0; ya < height; ya++) {
					for (int xa = 0; xa < width; xa++) {
						float dis = (float) Vector2f.distance(x + radius, y + radius, x + xa, y + ya);
						if(dis <= radius & dis > radius - 1) if(color != null)rgb[xa + ya * width] = color.getARGB();
					}
				}
				cirCache.holdIntArray(rgb);
				cirCache.addCache();
			}
			addRender(x, y, radius * 2 + 1, radius * 2 + 1, Cache.getCache(id).getIntArray());

			reset();
		} else {

		}*/
	}

	public void drawStringImage(Image image, String str, int x, int y, Color color) {
		engine.graphics.shapes.Text s = new engine.graphics.shapes.Text(image, str, x, y);
		s.setRender(render);
		s.setColor(color);
		s.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void drawString(String str, int x, int y) {
		engine.graphics.shapes.Text s = new engine.graphics.shapes.Text(str, x, y);
		s.setRender(render);
		s.setColor(color);
		s.setFont(font);
		s.draw(renderMode, fBrightness, bBrightness);
		reset();
	}

	public void setColor(Color color) {
		if(this.color != color) this.color = color;
	}

	private void reset() {
		//if(color == null) color = Color.WHITE;
		if(color == Color.WHITE) color = Color.WHITE;
		if(bBrightness != true) bBrightness = true;
		if(fBrightness != 1) fBrightness = 1;
	}

	public CopyOnWriteArrayList<Render> getRender() {
		return render;
	}

	public void resetRender() {
		render = new CopyOnWriteArrayList<>();
	}

	public void setFont(Font awtFont) {
		font = awtFont;
	}

	public void setFont(String name, int style, int size) {
		font = new Font(name, style, size);
	}

	public void setBrightness(boolean bBrightness) {
		this.bBrightness = bBrightness;
	}

	public void rotate90() {
		Shape.rotate90();
	}
	
	public void rotate180() {
		Shape.rotate180();
	}
	
	public void rotate270() {
		Shape.rotate270();
	}
	
	public void scale(double sx, double sy) {
		Shape.scale(sx, sy);
	}
	
	public void translate(double tx, double ty) {
		Shape.translate(tx, ty);
	}
	
	public void flip(int flipType) {
		Shape.flip(flipType);
	}
}
