package example;

import engine.main.Display;
import engine.main.Graphics;
import engine.main.Image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JRootPane;

import engine.graphics.Color;

public class Boot {

	String title = "Game Engine";
	BufferedImage icon = new Image("res/tiles/tilesheet.png", 32, 0, 16, 16).getImage();
	Dimension size = new Dimension(1024, 1024);
	int screenDevice = 0;
	Color backgroundColor = Color.BLUE;
	boolean undecorated = true;
	boolean keepAspectRatio = true;
	
	public Boot() {
		Graphics.setScale(0.5f); // must be set before everything else
		Display.settup(title, icon, size, screenDevice, backgroundColor, undecorated, keepAspectRatio);
		Display.addGameState(new Game("Game"));
		Display.create(60);
	}
	
	public static void main(String[] args) {
		new Boot();
	}
}
