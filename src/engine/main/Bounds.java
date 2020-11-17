package engine.main;

public class Bounds {

	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	int width;
	int height;
	int modifier;
	
	private int minW;
	private int minH;
	private int maxW;
	private int maxH;
	
	public Bounds(int x, int y, int width, int height, int modifier, int minWidth, int minHeight, int maxWidth, int maxHeight) {
		
		minW = minWidth;
		minH = minHeight;
		maxW = maxWidth;
		maxH = maxHeight;
		
		minX = x/modifier;
		minY = y/modifier;
		maxX = (x + width)/modifier;
		maxY = (y + height)/modifier;
		
		this.width = width;
		this.height = height;
		this.modifier = modifier;
		con();
	}
	
	public void set(int x, int y) {
		minX = x/modifier;
		minY = y/modifier;
		maxX = (x + width)/modifier;
		maxY = (y + height)/modifier;
		con();
	}
	
	void con() {
		if(minX < minW) minX = minW;
		if(minY < minH) minY = minH;
		if(minX > maxW) minX = maxW;
		if(minY > maxH) minY = maxH;
		if(maxX > maxW) maxX = maxW;
		if(maxY > maxH) maxY = maxH;
	}
	
	public int getMinX() {return minX;}
	
	public int getMaxX() {return maxX;}
	
	public int getMinY() {return minY;}
	
	public int getMaxY() {return maxY;}
}
