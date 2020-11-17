package engine.graphics.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.main.Cache;

public class FillRectangle extends Shape{

	public FillRectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	void dArray() {
		String id = "fr"+x+""+y+""+width+""+height;
		if(Cache.getCache(id) == null) {
			Cache intCache = new Cache(id);

			int[] rgb = new int[width * height];

			if(color != null) 
				for (int i = 0; i < rgb.length; i++) {
					int dx = 0;
					if(x<0) dx = x;
					int x = i / width + dx;
					int y = i % height;
					rgb[x+y*width] = color.getARGB();
				}
			intCache.holdIntArray(rgb);
			intCache.addCache();
		}
		
		rgb = Cache.getCache(id).getIntArray();
		
		classErr = "fill rect";
	}

	@Override
	void dGraphics(Graphics2D g) {
		g.setColor(new Color(color.getARGB()));
		g.fillRect(x, y, width, height);
	}
}
