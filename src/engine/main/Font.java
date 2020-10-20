package engine.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Font {

	private java.awt.Font font;
	private BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D g = image.createGraphics();
	
	public static final String DIALOG = java.awt.Font.DIALOG;
	public static final String DIALOG_INPUT = java.awt.Font.DIALOG_INPUT;
	public static final String SANS_SERIF = java.awt.Font.SANS_SERIF;
	public static final String MONOSPACED = java.awt.Font.MONOSPACED;
	public static final String SERIF = java.awt.Font.SERIF;
	
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int ITALIC = java.awt.Font.ITALIC;
	public static final int PLAIN = java.awt.Font.PLAIN;
	
	public Font(String name, int style, int size) {
		font = new java.awt.Font(name, style, size);
		g.setFont(font);
	}
	
	public int getWidth(String str) {
		return g.getFontMetrics().stringWidth(str);
	}
	
	public int getHeight() {
		return g.getFontMetrics().getHeight();
	}
	
	public java.awt.Font getAWTFont() {
		return font;
	}
}
