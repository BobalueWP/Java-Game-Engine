package engine.game;

import java.awt.image.BufferedImage;

import engine.main.Vector2f;
import engine.graphics.Graphics;

public abstract class Entity {

	private Vector2f pos;
	private int width;
	private int height;
	private String name;
	private BufferedImage[] images;
	
	public Entity(BufferedImage[] images, String name, int x, int y, int width, int height) {
		this.images = images;
		this.name = name;
		pos = new Vector2f(x, y);
		this.width = width;
		this.height = height;
	}
	
	public abstract void update(double delta);
	public abstract void render(Graphics g);
	
	public String getName() {
		return name;
	}
	
	public Vector2f getPos() {
		return pos;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public BufferedImage[] getImages() {
		return images;
	}
}
