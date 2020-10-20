package engine.main;

public class Bounds {

	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	
	public Bounds(int x, int y, int width, int height, int minWidth, int minHeight, int maxWidth, int maxHeight) {
		minX = x;
		minY = y;
		maxX = minX + width;
		maxY = minY + height;
		
		if(minY < minWidth) minY = minWidth;
		if(minX < minHeight) minX = minHeight;

		if(maxX > maxWidth) maxX = maxWidth;
		if(maxY > maxHeight) maxY = maxHeight;
	}
	
	public int getMinX() {return minX;}
	
	public int getMaxX() {return maxX;}
	
	public int getMinY() {return minY;}
	
	public int getMaxY() {return maxY;}
}
