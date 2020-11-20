package engine.game;

import java.awt.image.BufferedImage;

import engine.main.Graphics;

public class Animator {

	private BufferedImage[] images;
	
	private int imageIndex;
	
	private int frameSpeed;
	private int frameCount;
	
	private boolean running;
	
	public Animator(BufferedImage[] images, int frames) {
		this.images = images;
		this.frameSpeed = frames;
	}
	
	public void update() {
		if(running) {
			frameCount+=1;
			if(frameCount >= frameSpeed) {
				imageIndex++;
				if(imageIndex >= images.length) imageIndex = 0;
				frameCount = 0;
			}
		}
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(images[imageIndex], x, y);
	}
	
	public void play() {
		running = true;
	}
	
	public void pause() {
		running = false;
	}
	
	public void stop() {
		running = false;
		imageIndex = 0;
		frameCount = 0;
	}
	
	public BufferedImage getImage() {
		return images[imageIndex];
	}
}
