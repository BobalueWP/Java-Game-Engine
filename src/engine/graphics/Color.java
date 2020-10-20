package engine.graphics;

public class Color {

	public static final Color WHITE = new Color(255, 255, 255, 255);
	public static final Color LIGHT_GRAY = new Color(1.0f, 0.75f, 0.75f, 0.75f);
	public static final Color GRAY = new Color(255, 128, 128, 128);
	public static final Color DARK_GRAY = new Color(1.0f, 0.25f, 0.25f, 0.25f);
	public static final Color BLACK = new Color(255, 0, 0, 0);

	public static final Color RED = new Color(255, 255, 0, 0);
	public static final Color DARK_RED = new Color(255, 128, 0, 0);
	public static final Color LIGHT_RED = new Color(255, 255, 128, 128);
	
	public static final Color YELLOW = new Color(255, 255, 255, 0);
	
	public static final Color GREEN = new Color(255, 0, 255, 0);
	public static final Color LIGHT_GREEN = new Color(255, 128, 255, 128);
	public static final Color DARK_GREEN = new Color(255, 0, 128, 0);
	
	public static final Color CYAN = new Color(255, 0, 255, 255);
	
	public static final Color BLUE = new Color(255, 0, 0, 255);
	public static final Color LIGHT_BLUE = new Color(255, 128, 128, 255);
	public static final Color DARK_BLUE = new Color(255, 0, 0, 128);
	
	public static final Color MAGENTA = new Color(255, 255, 0, 255);

	private int value;

	public Color(int color) {
		value = color;
	}

	public Color(int alpha, int red, int green, int blue) {
		value = (alpha & 0xff) << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff) << 0;
	}

	public Color(float alpha, float red, float green, float blue) {
		value = ((int)(alpha * 255.0f) & 0xff) << 24 | ((int)(red * 255.0f) & 0xff) << 16 | ((int)(green * 255.0f) & 0xff) << 8 | ((int)(blue * 255.0f) & 0xff) << 0;
	}

	public Color combine(Color color) {
		int alpha = (getAlpha() / 2) + (color.getAlpha() / 2);
		int red = (getRed() / 2) + (color.getRed() / 2);
		int green = (getGreen() / 2) + (color.getGreen() / 2);
		int blue = (getBlue() / 2) + (color.getBlue() / 2);

		if(alpha > 255) alpha = 255;
		if(red > 255) red = 255;
		if(green > 255) green = 255;
		if(blue > 255) blue = 255;

		if(alpha < 0) alpha = 0;
		if(red < 0) red = 0;
		if(green < 0) green = 0;
		if(blue < 0) blue = 0;

		return new Color(alpha, red, green, blue);
	}

	public Color saturation(float sat) {
		if(sat > 100) sat = 100;
		if(sat < 0) sat = 0;

		float s = Math.abs(sat / 100 - 1) * 255;

		int rFrac =  (int) (Math.abs(getRed() / 255.0f - 1) * s);
		int gFrac =  (int) (Math.abs(getGreen() / 255.0f - 1) * s);
		int bFrac =  (int) (Math.abs(getBlue() / 255.0f - 1) * s);

		int r = getRed() + rFrac;
		int g = getGreen() + gFrac;
		int b = getBlue() + bFrac;

		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;

		if(r < 0) r = 0;
		if(g < 0) g = 0;
		if(b < 0) b = 0;

		return new Color(255, r, g, b);
	}

	public static int getHue(float hue) {
		float h = (hue / 360.0f * 6) % 6;

		float r = 0;
		float g = 0;
		float b = 0;

		switch ((int)h) {
		case 0:
			r = 1.0f;
			g = (h - 0);
			b = 0;
			break;

		case 1:
			r = 1.0f - (h - 1);
			g = 1.0f;
			b = 0;
			break;

		case 2:
			r = 0;
			g = 1.0f;
			b = (h - 2);
			break;

		case 3:
			r = 0;
			g = 1.0f - (h - 3);
			b = 1.0f;
			break;

		case 4:
			r = (h - 4);
			g = 0;
			b = 1.0f;
			break;

		case 5:
			r = 1.0f;
			g = 0;
			b = 1.0f - (h - 5);
			break;

		default:
			break;
		}

		return 0xff000000 | (((int)(r * 255) & 0xff) << 16) | (((int)(g * 255) & 0xff) << 8) | (((int)(b * 255) & 0xff) << 0);
	}

	public double colorDistance(Color color) {
		double redDistance = this.getRed() - color.getRed();
		double greenDistance = this.getGreen() - color.getGreen();
		double blueDistance = this.getBlue() - color.getBlue();
		double distance = Math.sqrt(redDistance * redDistance + greenDistance * greenDistance + blueDistance * blueDistance);
		return distance;
	}

	public static int saturation(int rgb, float sat) {
		if(sat > 100) sat = 100;
		if(sat < 0) sat = 0;

		float s = Math.abs(sat / 100 - 1) * 255;

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		int rFrac =  (int) (Math.abs(r / 255.0f - 1) * s);
		int gFrac =  (int) (Math.abs(g / 255.0f - 1) * s);
		int bFrac =  (int) (Math.abs(b / 255.0f - 1) * s);

		r = getRed(rgb) + rFrac;
		g = getGreen(rgb) + gFrac;
		b = getBlue(rgb) + bFrac;

		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;

		if(r < 0) r = 0;
		if(g < 0) g = 0;
		if(b < 0) b = 0;

		return 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | ((b & 0xff) << 0);
	}

	public static int setBrightness(int rgb, float brightness) {
		if(brightness > 100) brightness = 100;
		if(brightness < 0) brightness = 0;
		float s = Math.abs(brightness / 100 - 1) * 255;

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		int rFrac =  (int) (1 - (r / 255.0f) * s);
		int gFrac =  (int) (1 - (g / 255.0f) * s);
		int bFrac =  (int) (1 - (b / 255.0f) * s);

		r = getRed(rgb) + rFrac;
		g = getGreen(rgb) + gFrac;
		b = getBlue(rgb) + bFrac;

		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;

		if(r < 0) r = 0;
		if(g < 0) g = 0;
		if(b < 0) b = 0;

		return 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | ((b & 0xff) << 0);
	}

	public static int getBrightness(int rgb) {
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		return (int) ((((r + g + b) / 3.0f) / 255.0f) * 100.0f);
	}

	public Color setBrightness(float brightness) {
		float s = Math.abs(brightness / 100 - 1) * 255;

		int rFrac =  (int) (1 - (getRed() / 255.0f) * s);
		int gFrac =  (int) (1 - (getGreen() / 255.0f) * s);
		int bFrac =  (int) (1 - (getBlue() / 255.0f) * s);

		int r = getRed() + rFrac;
		int g = getGreen() + gFrac;
		int b = getBlue() + bFrac;

		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;

		if(r < 0) r = 0;
		if(g < 0) g = 0;
		if(b < 0) b = 0;

		return new Color(0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | ((b & 0xff) << 0));
	}

	public int getBrightness() {
		return ((getRed() + getGreen() + getBlue()) / 255) * 100;
	}

	public static int combineColor(int v1, int v2) {
		int v1_Red = getRed(v1);
		int v1_Green = getGreen(v1);
		int v1_Blue = getBlue(v1);

		int v2_Red = getRed(v2);
		int v2_Green = getGreen(v2);
		int v2_Blue = getBlue(v2);

		int alpha = 255;

		int red = (int) ((v1_Red + v2_Red) / 2);

		int green = (int) ((v1_Green + v2_Green) / 2);

		int blue = (int) ((v1_Blue + v2_Blue) / 2);

		if(alpha > 255) alpha = 255;
		if(red > 255) red = 255;
		if(green > 255) green = 255;
		if(blue > 255) blue = 255;

		if(alpha < 0) alpha = 0;
		if(red < 0) red = 0;
		if(green < 0) green = 0;
		if(blue < 0) blue = 0;

		int value = ((alpha & 0xff) << 24) | ((red & 0xff) << 16) | ((green & 0xff) << 8) | ((blue & 0xff) << 0);

		return value;
	}

	public static int getAlpha(int rgb) {return (rgb >> 24) & 0xff;}

	public static int getRed(int rgb) {return (rgb >> 16) & 0xff;}

	public static int getGreen(int rgb) {return (rgb >> 8) & 0xff;}

	public static int getBlue(int rgb) {return (rgb >> 0) & 0xff;}

	public int getARGB() {
		return value;
	}

	public static int changeAlpha(int color, int alpha) {
		if(alpha > 255) alpha = 255;
		if(alpha < 0) alpha = 0;

		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);

		return (alpha & 0xff) << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff) << 0;
	}

	public Color changeAlpha(int alpha) {
		if(alpha > 255) alpha = 255;
		if(alpha < 0) alpha = 0;
		int red = getRed();
		int green = getGreen();
		int blue = getBlue();

		value = (alpha & 0xff) << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff) << 0;
		return this;
	}

	public static boolean hasAlpha(int[] pixels) {
		for (int i = 0; i < pixels.length; i++)
			if(getAlpha(pixels[i]) != 0) {
				if(getAlpha(pixels[i]) != 255) return true;
				else return false;
			}
		return false;
	}

	public int getAlpha() {
		return (value >> 24) & 0xff;
	}

	public int getRed() {
		return (value >> 16) & 0xff;
	}

	public int getGreen() {
		return (value >> 8) & 0xff;
	}

	public int getBlue() {
		return (value >> 0) & 0xff;
	}
}
