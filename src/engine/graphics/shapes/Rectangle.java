package engine.graphics.shapes;

import java.awt.Graphics2D;

import engine.graphics.Color;
import engine.main.Cache;

public class Rectangle extends Shape{

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	void dArray() {
		String id = "r"+x+""+y+""+width+""+height;
		if(Cache.getCache(id) == null) {
			Cache intCache = new Cache(id);

			int[] rgb = new int[width * height];

			for (int ya = 0; ya < height; ya++) for (int xa = 0; xa < width; xa++)
				if(ya == 0 | ya == height - 1 | xa == 0 | xa == width - 1)
					if(color != null) rgb[xa + ya * width] = color.getARGB();
					else rgb[xa + ya * width] = Color.WHITE.getARGB();
			intCache.holdIntArray(rgb);
			intCache.addCache();
		}
		rgb = Cache.getCache(id).getIntArray();
	}

	@Override
	void dGraphics(Graphics2D g) {
		g.setColor(new java.awt.Color(color.getARGB()));
		g.drawRect(x, y, width, height);
	}
}
