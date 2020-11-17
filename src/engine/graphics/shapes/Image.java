package engine.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

	AffineTransform tx;
	AffineTransform g_tx;
	AffineTransformOp op;

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
		bImg = image.getImage();
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

		classErr = "image";
	}

	@Override
	void dGraphics(Graphics2D g) {
		//g_tx = g.getTransform();
		
		switch (flipType) {
		case 0x1:
			flipImageH();
			break;
		case 0x2:
			flipImageV();
			break;
		case 0x3:
			flipImage();
			break;
		default:
			break;
		}

		switch (thetaType) {
		case 90:
			rotateImage90();
			break;
		case 180:
			rotateImage180();
			break;
		case 270:
			rotateImage270();
			break;
		default:
			break;
		}
		
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
		
		//g.setTransform(g_tx);
	}

	void flipImageH() {
		String id = bImg.hashCode() + "H";		

		if(Cache.getCache(id) == null) {
			tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-bImg.getWidth(null), 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			bImg = op.filter(bImg, null);

			Cache imageCache = new Cache(id);

			imageCache.holdImage(bImg);
			imageCache.addCache();
		} else bImg = Cache.getCache(id).getImage();
	}

	void flipImageV() {
		String id = bImg.hashCode() + "V";		

		if(Cache.getCache(id) == null) {
			tx = AffineTransform.getScaleInstance(1, -1);
			tx.translate(0, -bImg.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			bImg = op.filter(bImg, null);

			Cache imageCache = new Cache(id);

			imageCache.holdImage(bImg);
			imageCache.addCache();
		}else bImg = Cache.getCache(id).getImage();
	}

	void flipImage() {
		String id = bImg.hashCode() + "B";		

		if(Cache.getCache(id) == null) {
			tx = AffineTransform.getScaleInstance(-1, -1);
			tx.translate(-bImg.getWidth(null), -bImg.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			bImg = op.filter(bImg, null);

			Cache imageCache = new Cache(id);

			imageCache.holdImage(bImg);
			imageCache.addCache();
		}else bImg = Cache.getCache(id).getImage();
	}

	void rotateImage90() {
		String id = bImg.hashCode() + "N";		

		if(Cache.getCache(id) == null) {
			double rotation = Math.toRadians(90);
			tx = AffineTransform.getRotateInstance(rotation, width/2, height/2);
			tx.translate(0, 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			bImg = op.filter(bImg, null);

			Cache imageCache = new Cache(id);

			imageCache.holdImage(bImg);
			imageCache.addCache();
		}else bImg = Cache.getCache(id).getImage();
	}
	
	void rotateImage180() {
		String id = bImg.hashCode() + "O";		

		if(Cache.getCache(id) == null) {
			double rotation = Math.toRadians(180);
			tx = AffineTransform.getRotateInstance(rotation, width/2, height/2);
			tx.translate(0, 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			bImg = op.filter(bImg, null);

			Cache imageCache = new Cache(id);

			imageCache.holdImage(bImg);
			imageCache.addCache();
		}else bImg = Cache.getCache(id).getImage();
	}
	
	void rotateImage270() {
		String id = bImg.hashCode() + "T";		

		if(Cache.getCache(id) == null) {
			double rotation = Math.toRadians(270);
			tx = AffineTransform.getRotateInstance(rotation, width/2, height/2);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			bImg = op.filter(bImg, null);

			Cache imageCache = new Cache(id);

			imageCache.holdImage(bImg);
			imageCache.addCache();
		}else bImg = Cache.getCache(id).getImage();
	}
}
