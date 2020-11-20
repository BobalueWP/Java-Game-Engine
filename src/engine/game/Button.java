package engine.game;

import java.awt.image.BufferedImage;

import engine.main.Font;
import engine.main.Graphics;
import engine.main.Image;
import engine.main.Mouse;
import engine.main.Vector2f;
import engine.graphics.Color;

public class Button {

	private String text;
	private Vector2f pos;
	private int width;
	private int height;
	private int iWidth;
	private int iHeight;
	private boolean containsMouse;
	private boolean clicked;
	private int bWidth;
	private int bHeight;
	private Font font = new Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 12);
	private int mouseButton = 1;
	private Color fontColor = Color.BLACK;
	private BufferedImage iImage;
	private BufferedImage cImage;
	private Image fontImage;
	private Image image;

	/**
	 * Create a new button. Sets the text of the button. The image must have an array.
	 * @param text
	 * @param image
	 */
	public Button(String text, Image image) {
		this.text = text;
		pos = new Vector2f();
		this.image = image;
		
		width = (int)(font.getWidth(text));
		height = (int)(font.getHeight());
		
		iWidth = (int)(image.getImage(0).getWidth());
		iHeight = (int)(image.getImage(0).getHeight());
		
		bWidth = (int)((iWidth * 2 + width));
		bHeight = (int)((iHeight * 2 + height));

		iImage = image.arrayToImage(3, 3, 0, 0, width, height);
		cImage = image.arrayToImage(3, 3, 0, 3, width, height);
	}
	
	/**
	 * Create a new button. Sets the text of the button and the position. The image must have an array.
	 * @param text
	 * @param x
	 * @param y
	 * @param image
	 */
	public Button(String text, int x, int y, Image image) {
		this.text = text;
		pos = new Vector2f(x, y);
		this.image = image;
		
		
		width = (int)(font.getWidth(text));
		height = (int)(font.getHeight());
		
		iWidth = (int)(image.getImage(0).getWidth());
		iHeight = (int)(image.getImage(0).getHeight());
		
		bWidth = (int)((iWidth * 2 + width));
		bHeight = (int)((iHeight * 2 + height));

		iImage = image.arrayToImage(3, 3, 0, 0, width, height);
		cImage = image.arrayToImage(3, 3, 0, 3, width, height);
	}

	public void setImageFont(Image image) {
		fontImage = image;
		
		width = (image.getImage(0).getWidth()) * text.length();
		height = image.getImage(0).getHeight();
		
		bWidth = (int)((iWidth * 2 + width));
		bHeight = (int)((iHeight * 2 + height));
		
		iImage = this.image.arrayToImage(3, 3, 0, 0, width, height);
		cImage = this.image.arrayToImage(3, 3, 0, 3, width, height);
	}

	/**
	 * Sets the mouseButton for the button to be clicked with.
	 * @param mouseButton
	 */
	public void setMouseButton(int mouseButton) {
		this.mouseButton = mouseButton;
	}

	/**
	 * Sets the Font of the button text.
	 * @param name
	 * @param style
	 * @param size
	 */
	public void setFont(String name, int style, int size) {
		font = new Font(name, style, (int) (size));
		width = font.getWidth(text);
		height = font.getHeight();
	}

	public void update() {
		containsMouse = Mouse.contains(
				(int)(pos.getX()), 
				(int)(pos.getY()), 
				(int)(bWidth-2), 
				(int)(bHeight-2));
		clicked = containsMouse & Mouse.getPresses(mouseButton);
	}

	public void render(Graphics g) {
		int i = 0;

		/*if(width != font.getWidth(getText())) {
			width = font.getWidth(getText());
			bWidth = (int)((iWidth * 2 + width));
		}

		if(height != font.getHeight()) {
			height = font.getHeight();
			bHeight = (int)((iHeight * 2 + height));
		}*/

		if(!clicked) g.drawScaledImage(iImage, (int)pos.getX(), (int)pos.getY(), bWidth, bHeight);
		else {
			i = 1;
			g.drawScaledImage(cImage, (int)pos.getX(), (int)pos.getY(), bWidth, bHeight);
		}


		int sx = (int)pos.getX() + (bWidth / 2) - (width / 2) - i;
		int sy = (int)pos.getY() + (bHeight / 2) - (height / 2) + i;

		if(fontImage == null) {
			g.setColor(fontColor);
			g.setFont(font.getAWTFont());
			g.drawString(text, sx, sy);
		} else {
			g.setColor(fontColor);
			g.drawStringImage(fontImage, text, sx, sy, Color.BLACK);
		}
	}

	public void setLocation(int x, int y) {
		pos.setLocation(x, y);
	}

	public boolean isClicked() {
		return clicked;
	}

	public boolean isContainsMouse() {
		return containsMouse;
	}

	public String getText() {
		return text;
	}

	public int getbWidth() {
		return bWidth;
	}

	public int getbHeight() {
		return bHeight;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public void addPos(int x, int y) {
		pos.add(x, y);
	}

	public void setY(double y) {
		pos.setY(y);
	}

	public void setX(double x) {
		pos.setX(x);
	}

	public Vector2f getPos() {
		return pos;
	}
}
