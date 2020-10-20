package engine.graphics.shader;

import engine.main.Bounds;
import engine.main.Vector2f;
import engine.graphics.Graphics;

public class PointLightSource {

	private float[] brightness = Shader.getBrightness();
	private int width = Shader.getWidth();
	private int height = Shader.getHeight();
	private Vector2f pos;
	private int radius;
	private boolean offsetLocation;

	public PointLightSource(int x, int y, int radius, boolean offsetLocation) {
		pos = new Vector2f(x, y);
		this.radius = radius;
		this.offsetLocation = offsetLocation;
	}
	
	public void update() {
		if(offsetLocation) u(getOffsetX(), getOffsetY());
		else u(getX(), getY());
	}

	public void changePos(int x, int y) {
		pos.setLocation(x, y);
	}

	public void addBrightness(int x, int y, float value) {
		if(bounds(x, y)) if(getBrightness(x, y) != getBrightness(x, y) + value) {
			brightness[x + y * width] += value;
			if(getBrightness(x, y) > 1) brightness[x + y * width] = 1;
		}
	}

	public void setBrightness(int x, int y, float value) {
		if(bounds(x, y))
			if(getBrightness(x, y) != value) {
				if(value > 1) value = 1;
				if(value < 0) value = 0;
				brightness[x + y * width] = value;
			}
	}

	public float getBrightness(int x, int y) {
		return brightness[x + y * width];
	}

	private boolean bounds(int x, int y) {
		return x >= 0 & x < width & y >= 0 & y < height;
	}
	
	private void u(int x, int y) {
		Bounds b = new Bounds(x - radius, y - radius, radius * 2, radius * 2, 0, 0, Graphics.getWidth(), Graphics.getHeight());

		for (int ya = b.getMinY(); ya < b.getMaxY(); ya++) 
			for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
				if(getBrightness(xa, ya) != 1) {
					float dis = (float) ((Vector2f.distance(xa, ya, x, y)) / radius);
					if(dis > 1) continue;
					else {
						if(dis < 0) dis = 0;
						float br = Math.abs(dis - 1);
						addBrightness(xa, ya, br);
					}
				}
			}
	}
	
	private int getOffsetX() {return (int)pos.getOffsetLocation().getX();}
	
	private int getOffsetY() {return (int)pos.getOffsetLocation().getY();}
	
	private int getX() {return (int)pos.getX();}
	
	private int getY() {return (int)pos.getY();}

	public void setLocation(int x, int y) {pos.setLocation(x, y);}
}
