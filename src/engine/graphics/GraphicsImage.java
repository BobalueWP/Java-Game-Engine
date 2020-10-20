package engine.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.main.Bounds;
import engine.graphics.shader.Shader;
import engine.graphics.shapes.GraphicsRenderer;

public class GraphicsImage {

	private static BufferedImage image;
	private int[] pixels;

	private GraphicsRenderer renderer;

	private boolean[] pixelsSet;
	private boolean[] tps;

	private static BufferedImage shaderImage;
	private static int[] shaderPixels;

	private static Shader shader;
	private int renderMode = 2;

	int width;
	int height;

	Graphics2D g;
	
	/**
	 * This is a work in progress. There is a lot of things I need to do with this to make it work properly.
	 */
	public static final int REVERSED_SOFT_RENDER = 0;

	/**
	 * Basic software rendering.
	 */
	public static final int SOFT_RENDER = 1;

	/**
	 * Best to use this instead.
	 */
	public static final int HARD_RENDER = 2;

	public GraphicsImage(int width, int height, float scale) {

		this.width = (int) (width * scale);
		this.height = (int) (height * scale);
		
		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		g = image.createGraphics();
		renderer = new GraphicsRenderer(renderMode, g);
		
		shader = new Shader(getWidth(), getHeight());
		shaderImage = new BufferedImage(this.width, this.height, BufferedImage.TRANSLUCENT);
		shaderPixels = ((DataBufferInt)shaderImage.getRaster().getDataBuffer()).getData();

		pixelsSet = new boolean[this.width * this.height];
		tps = new boolean[this.width * this.height];
	}

	public void render() {
		switch (renderMode) {
		case REVERSED_SOFT_RENDER:
			for (int i = 0; i < pixels.length; i++) if(!pixelsSet[i]) {
				if(pixels[i] != Graphics.bgc.getARGB()) pixels[i] = Graphics.bgc.getARGB();
				if(shaderPixels[i] != Color.BLACK.changeAlpha(0).getARGB())shaderPixels[i] = Color.BLACK.changeAlpha(0).getARGB();
			}

			pixelsSet = new boolean[width * height];
			tps = new boolean[width * height];
			//shaderPixels = new int[width * height];

			// solid textures
			int grs = getRender().size();
			for (int i = 0; i < grs; i++) {
				int index = Math.abs(i - grs + 1);
				if(index < 0) index = 0;
				if(index > grs - 1) index = grs - 1;
				Render r = getRender().get(index);

				Bounds b = new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 0, 0, getWidth(), getHeight());

				for (int ya = b.getMinY(); ya < b.getMaxY(); ya++) for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
					int rPixel = r.getPixel(xa - r.getX(), ya - r.getY());
					int cga = Color.getAlpha(rPixel);
					int id = xa + ya * getWidth();
					if(cga != 0) {
						boolean ps = pixelsSet[id];
						if(cga == 255) {
							if(!ps) {
								setPixels(xa, ya, rPixel);
								pixelsSet[id] = true;
							}

						} else if(!ps) {
							setPixels(xa, ya, rPixel);
							pixelsSet[id] = true;
						} if(ps & Color.getAlpha(pixels[id]) != 255) {
							/*float sgb = getBrightness(xa, ya);
							float ggsgb = getShader().getBrightness(xa, ya);
							if(ggsgb > 0) {
								if(ggsgb <= 1) {
									int shadedPixel = Color.setBrightness(Color.combineColor(pixels[id], rPixel), (ggsgb * 100));
									setPixels(xa, ya, Color.combineColor(pixels[id], shadedPixel));
								}
							} else setPixels(xa, ya, Color.BLACK.changeAlpha(255).getARGB());*/
							//int test = Color.setBrightness(, r.getBrightness());
							
							//setPixels(xa, ya, Color.setBrightness(pixels[id], r.getBrightness()).combineColor(rPixel));
						}

					}
				}

				/*int tIndex=-1;

				if(Color.getAlpha(r.getPixels()[0]) != 0 & Color.getAlpha(r.getPixels()[0]) != 255)
					tIndex = index;

				if(tIndex != -1) {
					Render r2 = getRender().get(tIndex);
					Bounds b2 = new Bounds(r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight(), 0, 0, getWidth(), getHeight());

					for (int ya = b2.getMinY(); ya < b2.getMaxY(); ya++) for (int xa = b2.getMinX(); xa < b2.getMaxX(); xa++) {
						int id = xa + ya * getWidth();
						int rPixel = r2.getPixel(xa - r2.getX(), ya - r2.getY());
						int cga = Color.getAlpha(rPixel);
						if(tps[id]) {
							if(cga != 0 & cga != 255) {

								setPixels(xa, ya, Color.combineColor(pixels[id], rPixel));
							}
						}
					}
				}*/
			}

			// shadow/light
			for (int i = 0; i < getRender().size(); i++) {
				int index = Math.abs(i - getRender().size() + 1);
				if(index < 0) index = 0;
				if(index > getRender().size() - 1) index = getRender().size() - 1;
				Render r = getRender().get(index);
				boolean rhb = r.hasBrightness();

				Bounds b = new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 0, 0, getWidth(), getHeight());

				if(r.getBrightness() != 0)
					for (int ya = (int) (b.getMinY()); ya < b.getMaxY(); ya++) for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
						int rPixel = r.getPixel(xa - r.getX(), ya - r.getY());
						if(Color.getAlpha(rPixel) != 0) {
							float sgb = getBrightness(xa, ya);
							if(sgb != 1 & !rhb) setShadowPixels(xa, ya, Math.abs(sgb - 1));
							else setShadowPixels(xa, ya, 0);
							if(rhb) setBrightness(xa, ya, r.getBrightness());
						}
					}
			}

			break;

		case SOFT_RENDER:
			for (int i = 0; i < pixels.length; i++) {
				if(pixels[i] != Graphics.bgc.getARGB()) pixels[i] = Graphics.bgc.getARGB();
				if(shaderPixels[i] != 0x00) shaderPixels[i] = 0x00;
			}

			grs = getRender().size();
			for (int i = 0; i < grs; i++) {

				Render r = getRender().get(i);
				boolean rhb = r.hasBrightness();

				Bounds b = new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 0, 0, getWidth(), getHeight());

				for (int ya = b.getMinY(); ya < b.getMaxY(); ya++) for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
					int rPixel = r.getPixel(xa - r.getX(), ya - r.getY());
					int cga = Color.getAlpha(rPixel);
					if(cga != 0) {

						// Shader shapes and whatnots.
						float sgb = getBrightness(xa, ya);
						if(sgb != 1 & !rhb) setShadowPixels(xa, ya, Math.abs(sgb - 1));
						else setShadowPixels(xa, ya, 0);

						// Textures
						int id = xa + ya * getWidth();
						if(cga == 255) setPixels(xa, ya, rPixel);
						else {
							float ggsgb = getShader().getBrightness(xa, ya);
							if(ggsgb > 0) if(ggsgb < 1) setPixels(xa, ya, Color.setBrightness(pixels[id], ggsgb * 100));
							else;
							else setPixels(xa, ya, 0x00);
							setPixels(xa, ya, Color.combineColor(pixels[id], rPixel));
						}

						// Sets the brightness (lighting) for the shader on certain textures. Used for buttons and things to keep them visible.
						if(rhb) setBrightness(xa, ya, r.getBrightness());
					} 
				}
			}
			break;

		case 2:
			
			for (int i = 0; i < pixels.length; i++) if(shaderPixels[i] != 0x00) shaderPixels[i] = 0x00;
			
			// shadow/light
			for (int i = 0; i < getRender().size(); i++) {
				//int index = Math.abs(i - getRender().size() + 1);
				//if(index < 0) index = 0;
				//if(index > getRender().size() - 1) index = getRender().size() - 1;
				Render r = getRender().get(i);
				boolean rhb = r.hasBrightness();

				Bounds b = new Bounds(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 0, 0, getWidth(), getHeight());

				if(r.getBrightness() != 0)
					for (int ya = (int) (b.getMinY()); ya < b.getMaxY(); ya++) for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
						int rPixel = r.getPixel(xa - r.getX(), ya - r.getY());
							float sgb = getBrightness(xa, ya);
							if(sgb != 1 & !rhb) setShadowPixels(xa, ya, Math.abs(sgb - 1));
							else setShadowPixels(xa, ya, 0);
							//if(rhb) setBrightness(xa, ya, r.getBrightness());
						//}
					}
			}
			
			//g.dispose();
			break;
		}
	}

	private void setPixels(int x, int y, int value) {
		if(pixels[x + y * getWidth()] != value)
			if(getShaderAlpha(x, y) != 1) pixels[x + y * getWidth()] = value;
	}

	private void setBrightness(int xa, int ya, float value) {
		shader.setBrightness(xa, ya, value);
	}

	private float getBrightness(int xa, int ya) {
		return shader.getBrightness(xa, ya);
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public CopyOnWriteArrayList<Render> getRender() {
		return renderer.getRender();
	}

	public GraphicsRenderer getRenderer() {
		return renderer;
	}

	public void reset() {
		renderer.resetRender();
	}

	public  BufferedImage getImage() {
		return image;
	}

	public static Shader getShader() {
		return shader;
	}

	private boolean shaderBounds(float x, float y) {
		return x >= 0 & x < getWidth() & y >= 0 & y < getWidth();
	}

	public float getShaderAlpha(int x, int y) {
		if(shaderBounds(x, y))
			return Color.getAlpha(shaderPixels[x + y * getWidth()]) / 255.0f;
		return 0;
	}

	public void setShadowPixels(int x, int y, float value) {
		if(value < 0) value = 0;
		if(value > 1) value = 1;
		if(shaderBounds(x, y)) if(getShaderAlpha(x, y) != value) shaderPixels[x + y * getWidth()] = Color.changeAlpha(shaderPixels[x + y * getWidth()], (int)(value * 255));
	}

	public BufferedImage getShaderImage() {
		return shaderImage;
	}

	public int getRenderMode() {
		return renderMode;
	}
}
