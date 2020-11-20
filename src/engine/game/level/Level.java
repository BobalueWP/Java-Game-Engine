package engine.game.level;

import java.util.concurrent.CopyOnWriteArrayList;

import engine.game.level.tile.BasicTile;
import engine.main.Graphics;
import engine.main.Vector2f;

public class Level {

	private static Vector2f pos = new Vector2f();
	private String name;
	private int tileSize;
	private float brightness;
	private String imagePath = "";

	private static CopyOnWriteArrayList<BasicTile> enumtTiles = new CopyOnWriteArrayList<>();

	private CopyOnWriteArrayList<Chunk> chunks = new CopyOnWriteArrayList<>();

	public Level(String name, String imagePath, int tileSize, float brightness) {
		this.name = name;
		this.imagePath = imagePath;
		this.tileSize = tileSize;
		this.brightness = brightness;

		Vector2f.setOffset(pos.getX(), pos.getY());

		addChunk();
	}

	public void update() {
		Vector2f.setOffset(pos.getX(), pos.getY());
		for (Chunk chunk : chunks) if(chunk.isWithinScreen()) chunk.update();
		for (BasicTile tile : getEnumtTiles()) if(tile.getAnimator() != null) tile.updateAnimator();
		//GraphicsImage.getShader().setShadeBrightness(brightness);
		//GraphicsImage.getShader().update();
	}

	public void renderMap(Graphics g) {
		for (Chunk chunk : chunks) if(chunk.isWithinScreen()) {
			chunk.setBrightness(brightness);
			chunk.render(g);
		}
	}

	public void addLayer(String imagePath) {
		chunks.add(new Chunk(imagePath, tileSize));
	}

	public void addChunk() {
		chunks.add(new Chunk(imagePath, tileSize));
	}

	public void setMapLocation(int x, int y) {pos.setLocation(x, y);}

	public void setBrightness(float brightness) {this.brightness = brightness;}

	public static Vector2f getPos() {return pos;}

	public static CopyOnWriteArrayList<BasicTile> getEnumtTiles() {return enumtTiles;}

	public static void addTile(BasicTile tile) {getEnumtTiles().add(tile);}
}
