package engine.graphics.shader;

import engine.main.Bounds;
import engine.main.Graphics;
import engine.main.Vector2f;

public class Shade {

	private Vector2f pos;
	private int width;
	private int height;

	private float value;
	private boolean offsetLocation;

	public Shade(int x, int y, int width, int height, float value, boolean offsetLocation) {
		pos = new Vector2f(x, y);
		this.width = width;
		this.height = height;
		this.value = value;
		this.offsetLocation = offsetLocation;
	}

	public void update() {
		if(offsetLocation) u(getOffsetX(), getOffsetY());
		else u(getX(), getY());
	}

	public void setLocation(int x, int y) {pos.setLocation(x, y);}

	public void setValue(float value) {this.value = value;}

	private void u(int x, int y) {
		Bounds b = new Bounds(x, y, width, height, 1, 0, 0, Graphics.getWidth(), Graphics.getHeight());

		//for (int ya = b.getMinY(); ya < b.getMaxY(); ya++) 
		//	for (int xa = b.getMinX(); xa < b.getMaxX(); xa++)
		//		GraphicsImage.getShader().setBrightness(xa, ya, value);
	}
	
	private int getX() {return (int)pos.getX();}
	
	private int getY() {return (int)pos.getY();}
	
	private int getOffsetX() {return (int)pos.getOffsetLocation().getX();}

	private int getOffsetY() {return (int)pos.getOffsetLocation().getY();}
}
