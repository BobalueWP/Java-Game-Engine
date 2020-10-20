package engine.game.level.tile;

import java.awt.image.BufferedImage;

import engine.game.Animator;
import engine.main.Image;
import engine.main.Vector2f;
import engine.graphics.Color;
import engine.graphics.Graphics;

public class BasicTile {

	private Vector2f pos;
	private int width;
	private int height;
	private Color color;
	private Image image;

	protected Animator animator;
	private float brightness = 0;

	private int iBrightness = 0;
	private boolean bBrightness = false;
	private Image autoTile;
	
	public BasicTile(int x, int y, float brightness) {
		pos = new Vector2f(x, y);
		this.brightness = brightness;
	}

	protected BasicTile(Image image, Color color) {
		this.image = image;
		this.color = color;

		if(image.getImages() == null) {
			this.width = image.getWidth();
			this.height = image.getHeight();
		} else {
			this.width = image.getImages()[0].getWidth();
			this.height = image.getImages()[0].getHeight();
		}
	}

	public void setBasicTile(BasicTile basicTile) {
		this.image = basicTile.image;
		this.color = basicTile.color;

		this.width = basicTile.width;
		this.height = basicTile.height;

		this.animator = basicTile.animator;
		this.iBrightness = basicTile.iBrightness;
		this.bBrightness = basicTile.bBrightness;

		this.autoTile = basicTile.autoTile;
		
	}

	public void update() {
		if(animator != null) animator.update();
	}

	public void render(Graphics g) {
		if(animator != null) {
			g.setBrightness(bBrightness);
			animator.render(g, (int)getTileOffset().getX(), (int)getTileOffset().getY());
		} else if(image != null) {
			g.setBrightness(bBrightness);
			g.drawImage(getImage(), (int)getTileOffset().getX(), (int)getTileOffset().getY());
		}
	}

	public Image getImage() {return image;}
	
	public Vector2f getPos() {return pos;}
	
	public Color getColor() {return color;}
	
	public void setAnimator(Animator animator) {this.animator = animator;}
	
	public int getiBrightness() {return iBrightness;}
	
	public boolean getbBrightness() {return bBrightness;}
	
	public int getWidth() {return width;}

	public int getHeight() {return height;}
	
	public float getBrightness() {return brightness;}
	
	public void setiBrightness(int iBrightness) {
		if(this.iBrightness != iBrightness)
			this.iBrightness = iBrightness;
	}
	
	public void setbBrightness(boolean bBrightness) {
		if(this.bBrightness != bBrightness)
			this.bBrightness = bBrightness;
	}
	
	private Vector2f getTileOffset() {return pos.getOffsetLocation();}
}
