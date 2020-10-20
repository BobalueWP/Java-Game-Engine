package engine.graphics.shader;

import java.util.concurrent.CopyOnWriteArrayList;

public class Shader {

	private static float[] brightness;

	private static int width;
	private static int height;

	private static CopyOnWriteArrayList<PointLightSource> pointLightSources = new CopyOnWriteArrayList<>();
	private static CopyOnWriteArrayList<Shade> shades = new CopyOnWriteArrayList<>();
	
	public Shader(int width, int height) {
		Shader.width = width;
		Shader.height = height;
		brightness = new float[width * height];
		for (int i = 0; i < brightness.length; i++) brightness[i] = 1;
	}

	public static void addPointLightSource(int x, int y, int radius, boolean offsetLocation) {
		pointLightSources.add(new PointLightSource(x, y, radius, offsetLocation));
	}

	public static void addShade(int x, int y, int width, int height, float value, boolean offsetLocation) {
		shades.add(new Shade(x, y, width, height, value, offsetLocation));
	}

	public void update() {
		for (Shade shade : shades) shade.update();			
		for (PointLightSource pointLightSource1 : pointLightSources) pointLightSource1.update();
	}

	public static boolean bounds(int x, int y) {
		return x >= 0 & x < width & y >= 0 & y < height;
	}

	public void setBrightness(int x, int y, float value) {
		if(bounds(x, y))
			if(getBrightness(x, y) != value) {
				if(value > 1) value = 1;
				if(value < 0) value = 0;
				brightness[x + y * width] = value;
			}
	}

	public void addBrightness(int x, int y, float value) {
		if(bounds(x, y)) brightness[x + y * width] += value;
		if(getBrightness(x, y) > 1) brightness[x + y * width] = 1;
	}

	public float getBrightness(int x, int y) {
		if(bounds(x, y)) return brightness[x + y * width];
		return 0;
	}

	public static float[] getBrightness() {return brightness;}

	public static int getWidth() {return width;}

	public static int getHeight() {return height;}

	public PointLightSource getCurrentPointLighting() {return pointLightSources.get(pointLightSources.size() - 1);}

	public void setShadeBrightness(float brightness) {
		for (Shade shade : shades) shade.setValue(brightness);
	}
}
