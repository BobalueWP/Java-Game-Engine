package engine.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

	private BufferedImage image;
	private BufferedImage[] images;
	private int width;
	private int height;
	private int[] pixels;
	private int wOffset;
	private int hOffset;

	static int runTimes = 0;

	int test = 0;
	
	/**
	 * Create a whole image.
	 * @param path
	 */
	public Image(String path) {
		BufferedImage img = null;
		File file = new File(path);
		try {img = ImageIO.read(file);} 
		catch (IOException e) {e.printStackTrace();}
		image = converter(img);
		setDimentions(image);
		pixels = ((DataBufferInt)image.getData().getDataBuffer()).getData();
	}

	/**
	 * Create a sub image from a whole image.
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Image(String path, int x, int y, int width, int height) {
		this(path);
		BufferedImage si = image.getSubimage(x, y, width, height);
		image = converter(si);
		setDimentions(image);
		pixels = ((DataBufferInt)image.getData().getDataBuffer()).getData();
	}

	/**
	 * Create an array of images from a whole image.
	 * @param path
	 * @param x
	 * @param y
	 * @param iWidth
	 * @param iHeight
	 * @param mWidth
	 * @param mHeight
	 */
	public Image(String path, int x, int y, int iWidth, int iHeight, int mWidth, int mHeight) {
		this(path);

		images = new BufferedImage[mWidth * mHeight];

		for (int i = 0; i < images.length; i++) {
			int xa = i % mWidth;
			int ya = i / mWidth;
			BufferedImage si = image.getSubimage(xa * iWidth + x, ya * iHeight + y, iWidth, iHeight);
			images[i] = converter(si);
		}
	}

	/**
	 * Sets the array of images as a whole image and can change the size of the image. <br>
	 * Note: the array must be a 3x3.
	 * @param col
	 * @param row
	 * @param colOffset
	 * @param rowOffset
	 * @param wOffset
	 * @param hOffset
	 * @return BufferedImgae
	 */
	public BufferedImage arrayToImage(int col, int row, int colOffset, int rowOffset, int wOffset, int hOffset) {

		this.wOffset = wOffset;
		this.hOffset = hOffset;

		int bWidth = images[0].getWidth();
		int bHeight = images[0].getHeight();

		BufferedImage img = new BufferedImage((bWidth * 3) + wOffset + 1, (bHeight * 3) + hOffset + 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();

		for (int ya = 0; ya < row; ya++) {
			for (int xa = 0; xa < col; xa++) {
				int ix = xa * bWidth;
				int iy = ya * bHeight;
				int iWidth = bWidth;
				int iHeight = bHeight;

				if(xa == 1) iWidth = bWidth + wOffset;
				if(xa == 2) ix = xa * bWidth + wOffset;
				if(ya == 1) iHeight = bHeight + hOffset;
				if(ya == 2) iy = ya * bHeight + hOffset;

				g.drawImage(images[(xa + colOffset) + (ya + rowOffset) * col], ix, iy, iWidth, iHeight, null);
			}
		}

		g.dispose();
		return img;
	}

	private void setDimentions(BufferedImage image) {
		if(image != null) {
		this.width = image.getWidth();
		this.height = image.getHeight();
		}
	}

	BufferedImage converter(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] rgbArray = image.getRGB(0, 0, width, height, null, 0, width);
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		newImage.setRGB(0, 0, width, height, rgbArray, 0, width);
		return newImage;
	}

	public BufferedImage[] getImages() {return images;}

	public BufferedImage getImage(int index) {return images[index];}

	public BufferedImage getImage() {return image;}

	public int getWidth() {return width;}

	public int getHeight() {return height;}

	public int getPixels(int x, int y) {return pixels[x + y * width];}

	public int[] getPixels() {return pixels;}

	public int getwOffset() {return wOffset;}

	public int gethOffset() {return hOffset;}

	public void setImage(BufferedImage image) {this.image = image;}
}
