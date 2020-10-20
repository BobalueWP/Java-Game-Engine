package engine.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.graphics.Color;
import engine.main.Cache;

public class Image extends Shape{

	final int SCALED = 0;
	final int B_SCALED = 1;
	final int ORIGANAL = 2;
	final int B_ORIGANAL = 3;

	int type;

	engine.main.Image img;
	BufferedImage bImg;
	
	public Image(engine.main.Image image, int x, int y, int width, int height) {
		super(x, y, width, height);
		img = image;
		bImg = image.getImage();
		type = 0;
	}

	public Image(BufferedImage image, int x, int y, int width, int height) {
		super(x, y, width, height);
		bImg = image;
		type = 1;
	}

	public Image(engine.main.Image image, int x, int y) {
		super(x, y, image.getImage().getWidth(), image.getImage().getHeight());
		this.bImg = image.getImage();
		img = image;
		type = 2;
	}

	public Image(BufferedImage image, int x, int y) {
		super(x, y, image.getWidth(), image.getHeight());
		this.bImg = image;
		type = 3;
	}

	@Override
	void dArray() {
		int id;
		switch (type) {
		case SCALED:
			id = img.hashCode()/2;

			if(Cache.getCache(id) == null) {
				Cache imageCache = new Cache(id);

				int iWidth = img.getWidth();
				int iHeight = img.getHeight();

				int[] rgb = img.getPixels();
				int[] pixels = new int[width * height];

				float wFrac = (float)width / iWidth;
				float hFrac = (float)height / iHeight;

				for (int ya = 0; ya < height; ya++) for (int xa = 0; xa < width; xa++) {
					int px = (int)(xa / wFrac);
					int py = (int)(ya / hFrac);
					pixels[xa + ya * width] = rgb[px + py * iWidth];
				}

				imageCache.holdIntArray(pixels);
				imageCache.addCache();
			}

			rgb = Cache.getCache(id).getIntArray();
			break;

		case B_SCALED:
			id = bImg.hashCode()/2;

			if(Cache.getCache(id) == null) {
				Cache imageCache = new Cache(id);

				int iWidth = bImg.getWidth();
				int iHeight = bImg.getHeight();

				int[] rgb = ((DataBufferInt)bImg.getData().getDataBuffer()).getData();
				int[] pixels = new int[width * height];

				float wFrac = (float)width / iWidth;
				float hFrac = (float)height / iHeight;

				for (int ya = 0; ya < height; ya++) for (int xa = 0; xa < width; xa++) {
					int px = (int)(xa / wFrac);
					int py = (int)(ya / hFrac);
					pixels[xa + ya * width] = rgb[px + py * iWidth];
				}

				imageCache.holdIntArray(pixels);
				imageCache.addCache();
			}
			rgb = Cache.getCache(id).getIntArray();
			break;

		case ORIGANAL:
			id = img.hashCode();
			if(Cache.getCache(id) == null) {
				Cache imageCache = new Cache(id);

				int[] rgb = img.getPixels();

				if(color != null) for (int i = 0; i < rgb.length; i++) if(Color.getAlpha(rgb[i]) != 0) rgb[i] = color.getARGB();

				imageCache.holdIntArray(rgb);
				imageCache.addCache();
			}
			rgb = Cache.getCache(id).getIntArray();
			break;

		case B_ORIGANAL:
			id = bImg.hashCode();
			if(Cache.getCache(id) == null) {
				Cache imageCache = new Cache(id);

				int[] rgb = ((DataBufferInt)bImg.getData().getDataBuffer()).getData();

				if(color != null) for (int i = 0; i < rgb.length; i++) if(Color.getAlpha(rgb[i]) != 0) rgb[i] = color.getARGB();

				imageCache.holdIntArray(rgb);
				imageCache.addCache();
			}
			rgb = Cache.getCache(id).getIntArray();
			break;
		default:
			break;
		}
	}

	@Override
	void dGraphics(Graphics2D g) {
		switch (type) {
		case SCALED:
			g.drawImage(bImg, x, y, width, height, null);
			break;

		case B_SCALED:
			g.drawImage(bImg, x, y, width, height, null);
			break;

		case ORIGANAL:
			g.drawImage(bImg, x, y, null);
			break;

		case B_ORIGANAL:
			g.drawImage(bImg, x, y, null);
			break;
		default:
			break;
		}
	}
}
