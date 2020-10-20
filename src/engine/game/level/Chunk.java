package engine.game.level;

import engine.game.level.tile.AutoTile;
import engine.game.level.tile.BasicTile;
import engine.main.Bounds;
import engine.main.Display;
import engine.main.Image;
import engine.main.Vector2f;
import engine.graphics.Graphics;
import engine.graphics.shader.Shader;

public class Chunk {

	private int id;
	private String imagePath;
	private int tileSize;
	private int mapWidth;
	private int mapHeight;
	private Vector2f pos = new Vector2f();
	private float brightness;
	private BasicTile[] tiles;
	private AutoTile autoTile;
	
	public Chunk(String imagePath, int tileSize) {
		this.imagePath = imagePath;
		this.tileSize = tileSize;

		Image image = new Image(imagePath);

		int[] rgb = image.getPixels();

		mapWidth = image.getWidth();
		mapHeight = image.getHeight();
		tiles = new BasicTile[mapWidth * mapHeight];

		for (int ya = 0; ya < mapWidth; ya++) for (int xa = 0; xa < mapHeight; xa++) {
			setTiles(xa, ya);
			for (BasicTile basicTile : Level.getEnumtTiles()) if(rgb[xa + ya * mapWidth] == basicTile.getColor().getARGB()) {
				BasicTile tile = tiles[xa + ya * mapWidth];
				tile.setBasicTile(basicTile);
				if(tile.getiBrightness() != 0) 
					Shader.addPointLightSource((int)tile.getPos().getX() + (tile.getWidth() / 2), (int)tile.getPos().getY() + (tile.getHeight() / 2), basicTile.getiBrightness(), true);
				if(!tile.getbBrightness())
					Shader.addShade((int)tile.getPos().getX(), (int)tile.getPos().getY(), tile.getWidth(), tile.getHeight(), tile.getBrightness(), true);
			}
		}
	}

	public void update() {
		
	}
	
	public void render(Graphics g) {
		Bounds b = new Bounds(
				(int)(pos.getX() + Level.getPos().getX()) / tileSize, 
				(int)(pos.getY() + Level.getPos().getY()) / tileSize, 
				Graphics.getWidth() / tileSize + 1, Graphics.getHeight() / tileSize + 2, 
				0, 0, mapWidth, mapHeight);

		for (int ya = b.getMinY(); ya < b.getMaxY(); ya++)
			for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
				BasicTile tile = tiles[xa + ya * mapWidth];
				tile.render(g);
			}
	}

	public void setBrightness(float brightness) {
		if(this.brightness != brightness)
			this.brightness = brightness;
	}

	private void setTiles(int x, int y) {
		tiles[x + y * mapWidth] = new BasicTile(
				(int)pos.getX() + (x * tileSize), 
				(int)pos.getY() + y * tileSize, brightness);
	}

	public boolean isWithinScreen() {
		return -Level.getPos().getX() + pos.getX() + (mapWidth * tileSize) > 0
				& -Level.getPos().getX() + pos.getX() <= Graphics.getWidth()
				& -Level.getPos().getY() + pos.getY() + (mapHeight * tileSize) >= 0 
				& -Level.getPos().getY() + pos.getY() <= Graphics.getWidth(); 
	}
}
