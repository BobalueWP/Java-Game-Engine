package engine.graphics.shapes;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.main.Cache;

public class Text extends Shape {

	String str;
	static Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, 12);
	engine.main.Image img;
	
	int textType = 0;
	
	public Text(engine.main.Image img, String str, int x, int y) {
		super(x, y, 0, 0);
		this.img = img;
		this.str = str;
		
		textType = 1;
	}
	
	public Text(String str, int x, int y) {
		super(x, y, 0, 0);
		this.str = str;
		
		textType = 0;
	}

	@Override
	void dArray() {

		switch (textType) {
		case 0:
			String id = str + font.toString();

			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();

			g.dispose();

			int fWidth = fm.stringWidth(str);
			int fHeight = fm.getHeight();

			if(Cache.getCache(id) == null) {
				Cache sCache = new Cache(id);
				BufferedImage image = new BufferedImage(fWidth, fHeight, BufferedImage.TYPE_INT_ARGB);

				g = image.createGraphics();

				g.setFont(font);
				if(color != null)
					g.setColor(new java.awt.Color(color.getARGB()));
				g.drawString(str, 0, fm.getAscent());

				g.dispose();

				int[] rgb = image.getRGB(0, 0, fWidth, fHeight, null, 0, fWidth);
				sCache.holdIntArray(rgb);
				sCache.addCache();
			}
			rgb = Cache.getCache(id).getIntArray();
			break;
		case 1:
			int iWidth = img.getImages()[0].getWidth();

			for (int i = 0; i < str.length(); i++) {
				int in = str.getBytes()[i];

				int xa = in % 16;
				int ya = in / 16;
				setColor(color);
				new Image(img.getImage(xa + ya * 16), x + (i * (iWidth)), y);
			}
			break;
			
		default:
			break;
		}
	}

	BufferedImage gImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = gImg.createGraphics();
	FontMetrics fm;
	
	@Override
	void renderText() {
		super.renderText();

		g.setFont(font);
		fm = g.getFontMetrics();

		g.dispose();

		width = fm.stringWidth(str);
		height = fm.getHeight();
	}

	@Override
	void dGraphics(Graphics2D g) {
		switch (textType) {
		case 0:
			g.setFont(font);
			g.setColor(new java.awt.Color(color.getARGB()));
			g.drawString(str, x, y+fm.getAscent());
			break;

		case 1:
			int iWidth = img.getImages()[0].getWidth();

			for (int i = 0; i < str.length(); i++) {
				int in = str.getBytes()[i];

				int xa = in % 16;
				int ya = in / 16;

				BufferedImage img = this.img.getImage(xa + ya * 16);

				Graphics2D g2 = img.createGraphics();

				g2.setComposite(AlphaComposite.SrcAtop);
				g2.setColor(java.awt.Color.decode(color.getARGB()+""));
				g2.fillRect(0, 0, img.getWidth(), img.getHeight());
				g2.dispose();

				g.drawImage(img, null, x + (i * (iWidth)), y);
				
				
			}
			break;
			
		default:
			break;
		}
	}

	public void setFont(Font awtFont) {
		font = awtFont;
	}

	public void setFont(String name, int style, int size) {
		font = new Font(name, style, size);
	}
}
