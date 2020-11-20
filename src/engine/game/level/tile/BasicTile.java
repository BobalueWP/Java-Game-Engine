package engine.game.level.tile;

import java.awt.image.BufferedImage;

import engine.game.Animator;
import engine.main.Graphics;
import engine.main.Image;
import engine.main.Vector2f;
import engine.graphics.Color;

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
	private int x;
	private int y;

	private boolean connectedTile;
	private int connectedIndex = 511;
	private boolean cti = false;
	private double theta = 0;

	private int flipType;
	
	public BasicTile(int x, int y, float brightness) {
		pos = new Vector2f(x, y);
		this.brightness = brightness;
	}

	public void updateAnimator() {
		if(animator != null) animator.update();
	}

	public void update() {
		x = (int)getTileOffset().getX();
		y = (int)getTileOffset().getY();
	}

	boolean rotated = false;
	private int oIndex;

	public void render(Graphics g) {
		if(animator != null) {
			g.setBrightness(bBrightness);
			animator.render(g, x, y);
		} else if(!connectedTile & image != null) {
			g.setBrightness(bBrightness);
			g.drawImage(getImage(), (int)getTileOffset().getX(), (int)getTileOffset().getY());
		} else if(connectedTile) {
			g.flip(flipType);
			if(theta == 90) g.rotate90();
			if(theta == 180) g.rotate180();
			if(theta == 270) g.rotate270();
			g.drawImage(image.getImage(connectedIndex), x, y);
		}
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
		this.connectedTile = basicTile.connectedTile;
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

	public boolean hasConnectedTexturs() {return false;}

	public boolean isConnectedTile() {return connectedTile;}

	public void setConnectedTile(boolean connectedTile) {this.connectedTile = connectedTile;}

	public void setConnectedIndex(int connectedIndex) {this.connectedIndex = connectedIndex;}

	public Animator getAnimator() {return animator;}

	public void setTheta(double theta) {this.theta = theta;}

	public int getConnectedIndex() {return connectedIndex;}

	public void setCTI(boolean cti) {this.cti = cti;}

	public boolean isCTI() {return cti;}

	public void setFlip(int flipType) {
		this.flipType = flipType;
	}

	public void setOriginalIndex(int oIndex) {
		this.oIndex = oIndex;
	}
	
	public int getOIndex() {
		return oIndex;
	}
}
