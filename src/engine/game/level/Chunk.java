package engine.game.level;

import engine.game.level.tile.BasicTile;
import engine.main.Bounds;
import engine.main.Graphics;
import engine.main.Image;
import engine.main.Vector2f;

public class Chunk {

	//private int id;
	//private String imagePath;
	private int tileSize;
	private int mapWidth;
	private int mapHeight;
	private Vector2f pos = new Vector2f();
	private float brightness;
	private BasicTile[] tiles;

	Bounds b;

	public Chunk(String imagePath, int tileSize) {
		//this.imagePath = imagePath;
		this.tileSize = tileSize;

		Image image = new Image(imagePath);
			
		int[] rgb = image.getPixels();

		mapWidth = image.getWidth();
		mapHeight = image.getHeight();
		tiles = new BasicTile[mapWidth * mapHeight];

		b = new Bounds(
				((int)pos.getX() + (int)Level.getPos().getX()), 
				((int)pos.getY() + (int)Level.getPos().getY()), 
				Graphics.getWidth() + tileSize, Graphics.getHeight() + tileSize, 
				tileSize,
				0, 0, mapWidth, mapHeight);

		for (int ya = 0; ya < mapHeight; ya++) for (int xa = 0; xa < mapWidth; xa++) {
			setTiles(xa, ya);
			for (BasicTile basicTile : Level.getEnumtTiles()) if(rgb[xa + ya * mapWidth] == basicTile.getColor().getARGB()) {
				BasicTile tile = tiles[xa + ya * mapWidth];
				tile.setBasicTile(basicTile);
				/*if(tile.getiBrightness() != 0) 
					Shader.addPointLightSource((int)tile.getPos().getX() + (tile.getWidth() / 2), (int)tile.getPos().getY() + (tile.getHeight() / 2), basicTile.getiBrightness(), true);
				if(!tile.getbBrightness())
					Shader.addShade((int)tile.getPos().getX(), (int)tile.getPos().getY(), tile.getWidth(), tile.getHeight(), tile.getBrightness(), true);*/
			}
		}
	}

	public void update() {
		b.set((int)(pos.getX() + Level.getPos().getX()), (int)(pos.getY() + Level.getPos().getY()));

		for (int ya = b.getMinY(); ya < b.getMaxY(); ya++)
			for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
				BasicTile cTile = tiles[xa + ya * mapWidth];

				int index = 0;

				if(cTile.isConnectedTile()) {
					index = connectedTextureIndex(cTile, xa, ya);
					cTile.setOriginalIndex(index);
					cTile.setFlip(flipFix(index));
					cTile.setTheta(thetaFix(index));
					int fIndex = indexFix(index);
					if(cTile.getConnectedIndex() != fIndex) {
						cTile.setConnectedIndex(fIndex);
					}
				}

				cTile.update();
			}
	}

	public void render(Graphics g) {
		for (int ya = b.getMinY(); ya < b.getMaxY(); ya++)
			for (int xa = b.getMinX(); xa < b.getMaxX(); xa++) {
				BasicTile cTile = tiles[xa + ya * mapWidth];

				cTile.render(g);

				/*if(cTile.isConnectedTile() & cTile.getConnectedIndex() != 511) {
					g.setFont(Font.SERIF, Font.PLAIN, 9);
					g.setColor(Color.BLACK);
					g.drawString(cTile.getOIndex() + "", (int)(-Level.getPos().getX()) + xa * tileSize, (int)(-Level.getPos().getY()) + ya * tileSize);
				}*/
			}
	}

	int connectedTextureIndex(BasicTile cTile, int x, int y) {
		int index = 0;

		for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {

			int sx = x + (i - 1);
			int sy = y + (j - 1);

			if(sx < 0) sx = 0;
			if(sy < 0) sy = 0;
			if(sx > mapWidth-1) sx = mapWidth-1;
			if(sy > mapHeight-1) sy = mapHeight-1;

			BasicTile sTile = tiles[sx + sy * mapWidth];

			if(cTile.getColor() == sTile.getColor())
				index += (int)Math.pow(2,  i + j * 3);
		}

		return index;
	}

	double thetaFix(int index) {
		double theta = 0;
		// 90
		theta = setArrayTheta(theta, index, 90, new int[] {
				24,25,26,28,29,30,56,57,58,59,60,61,88,89,90,92,93,94,
				120,121,122,123,124,125,
				216,217,219,220,221,223,248,249,250,251,252,253,254,281,282,284,285,286,280,
				312,313,314,315,316,317,344,345,346,348,349,350,376,377,378,379,380,381,
				440,441,444,445,472,473,475,476,477,479,
				507
		});

		//180
		theta = setArrayTheta(theta, index, 180, new int[] {
				18,19,22,23,27,31,50,51,63,82,83,86,87,91,95,
				114,115,127,159,191,155,178,179,187,
				242,255,274,275,278,279,243,283,287,
				306,307,383,338,319,351,339,342,343,347,370,371,
				411,415,

		});

		//270
		theta = setArrayTheta(theta, index, 270, new int[] {
				48,49,52,53,54,55,62,
				112,113,116,117,118,119,126,176,177,180,181,184,185,188,189,190,
				240,241,244,245,
				304,305,308,309,310,311,318,369,368,372,373,374,375,382,
				438,439,446,447,
				502,503
		});

		return theta;
	}

	int indexFix(int index) {
		// 510
		index = setArrayIndex(index, 510, new int[] {
				507,255,447
		});

		// 509
		index = setArrayIndex(index, 509, new int[] {
				504,505,508,219,223,475,479,63,127,319,383,438,439,502,503
		});

		// 506
		index = setArrayIndex(index, 506, new int[] {
				251,191,446
		});

		// 501 L
		index = setArrayIndex(index, 501, new int[] {
				27,31,54,55,91,95,
				118,119,
				216,217,220,221,283,287,
				310,351,311,375,347,374,
				432,433,436,437,472,473,476,477,496,497,
				500
		});

		// 499
		index = setArrayIndex(index, 499, new int[] {
				59,62,
				123,126,155,159,182,183,
				218,222,252,253,246,247,248,249,
				315,318,379,382,
				411,415,434,435,440,441,444,445,474,478,498,
		});

		// 471 ||
		index = setArrayIndex(index, 471, new int[] {
				56,57,60,61,
				120,121,124,125,146,147,150,151,
				210,211,214,215,
				312,313,316,317,376,377,380,381,
				402,403,406,407,466,467,470
		});

		// 469 [
		index = setArrayIndex(index, 469, new int[]{
				18,19,22,23,24,25,28,29,48,49,52,53,82,88,89,83,86,87,92,93,
				112,113,116,117,133,144,145,148,149,
				208,209,212,213,280,284,285,274,275,278,279,281,
				304,305,308,309,338,339,342,343,344,345,348,349,368,369,372,373,
				400,401,404,405,464,465,468
		});
		
		// 443 ,'
		index = setArrayIndex(index, 443, new int[] {
				254
		});
		
		//442
		index = setArrayIndex(index, 442, new int[] {
				190,187,250
		});

		// 414 :|
		index = setArrayIndex(index, 414, new int[] {
				58,
				122,154,158,178,179,184,185,188,189,
				243,242,
				314,378,
				410
		});

		// 413 L'
		index = setArrayIndex(index, 413, new int[] {
				26,30,50,51,90,94,
				114,115,152,153,156,157,176,177,180,181,
				240,241,244,245,282,286,
				306,307,346,350,370,371,
				408,409,412
		});


		// 16
		index = setArrayIndex(index, 16, new int[] {
				17,20,21,80,81,84,85,
				272,273,276,277,
				336,337,340,341,
		});

		return index;
	}

	int flipFix(int index) {
		int flip = -1;

		flip = setArrayflip(flip, index, 1, new int[]{
				59,
				123,
				218,222,
				315,379,
				474,478
		});

		flip = setArrayflip(flip, index, 2, new int[]{
				182,183,
				246,247,
				440,441,444,445
		});

		return flip;
	}

	int setArrayIndex(int index, int val, int[] vals) {
		for (int i = 0; i < vals.length; i++) if(index == vals[i]) return val;
		return index;
	}

	double setArrayTheta(double theta, int index, int sTheta, int[] vals) {
		for (int i = 0; i < vals.length; i++) if(index == vals[i]) return sTheta;
		return theta;
	}

	int setArrayflip(int flip, int index, int sFlip, int[] vals) {
		for (int i = 0; i < vals.length; i++) if(index == vals[i]) return sFlip;
		return flip;
	}

	public void setBrightness(float brightness) {
		if(this.brightness != brightness)
			this.brightness = brightness;
	}

	private void setTiles(int x, int y) {
		tiles[x + y * mapWidth] = new BasicTile(
				(int)pos.getX() + x * tileSize, 
				(int)pos.getY() + y * tileSize, brightness);
	}

	public boolean isWithinScreen() {
		return -Level.getPos().getX() + pos.getX() + (mapWidth * tileSize) > 0
				& -Level.getPos().getX() + pos.getX() < Graphics.getWidth()
				& -Level.getPos().getY() + pos.getY() + (mapHeight * tileSize) > 0 
				& -Level.getPos().getY() + pos.getY() < Graphics.getHeight(); 
	}
}
