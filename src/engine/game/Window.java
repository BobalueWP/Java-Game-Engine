package engine.game;

import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;
import engine.main.Image;
import engine.main.Vector2f;
import engine.graphics.Graphics;

public class Window {

	private String name;
	private Vector2f pos;
	private int width;
	private int height;
	private BufferedImage img;
	private Image image;
	private CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
	private int offset;
	boolean bv;
	boolean bud;
	int gap;
	
	public Window(Image image, String name, int x, int y, int width, int height, int offset) {
		this.image = image;
		this.name = name;
		pos = new Vector2f(x, y);
		this.width = (int) (width);
		this.height = (int) (height);
		this.offset = (int) (offset);
		
		this.img = image.arrayToImage(3, 3, 0, 0, width, height);
		
	}
	
	public void update() {
		for (Button button : buttons) button.update();
	}
	
	public void render(Graphics g) {
		g.drawScaledImage(img, (int)pos.getX(), (int)pos.getY(), width, height);
		for (Button button : buttons) button.render(g);
	}
	
	public void setButtonFont(String name, int style, int size) {
		for (Button button : buttons) {
			button.setFont(name, style, size);
		}
	}
	
	public void setButtonFontImage(Image image) {
		for (Button button : buttons) {
			button.setImageFont(image);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Button getButton(String text) {
		for (Button button : buttons) if(button.getText() == text) return button;
		return null;
	}
	
	public Button getButton(int index) {
		return buttons.get(index);
	}
	
	public void addButton(String text, int x, int y, Image image) {
		Button button = new Button(text, 0, 0, image);
		
		if(width < button.getbWidth() + (offset * 2)) width = button.getbWidth() + (offset * 2);
		if(height < button.getbHeight() + (offset * 2)) height = button.getbHeight() + (offset * 2);
		
		img = this.image.arrayToImage(3, 3, 0, 0, width, height);
		
		if(x > this.width - button.getbWidth() - offset) x = this.width - button.getbWidth() - offset;
		if(x < 8) x = offset;
		if(y > this.height - button.getbHeight() - offset) y = this.height - button.getbHeight() - offset;
		if(y < 8) y = offset;
		
		int xa = getX() + x;
		int ya = getY() + y;
		
		button.setLocation(xa, ya);
		buttons.add(button);
	}

	public void addbutton(Button button) {
		int x = (int) button.getPos().getX();
		int y = (int) button.getPos().getY();
		
		if(width < button.getbWidth() + (offset * 2)) width = button.getbWidth() + (offset * 2);
		if(height < button.getbHeight() + (offset * 2)) height = button.getbHeight() + (offset * 2);
		
		img = this.image.arrayToImage(3, 3, 0, 0, width, height);
		
		if(x > this.width - button.getbWidth() - offset) x = this.width - button.getbWidth() - offset;
		if(x < 8) x = offset;
		if(y > this.height - button.getbHeight() - offset) y = this.height - button.getbHeight() - offset;
		if(y < 8) y = offset;
		
		int xa = getX() + x;
		int ya = getY() + y;
		
		button.setLocation(xa, ya);
		buttons.add(button);
	}
	
	public void offsetButtons(int ox, int oy) {
		for (Button button : buttons) button.addPos(ox, oy);
	}
	
	public int getX() {
		return (int)pos.getX();
	}
	
	public int getY() {
		return (int)pos.getY();
	}

	public void setButtonsUpToDown(int gap) {
		bud = true;
		
		this.gap = gap;
		
		int y = (int)pos.getY() + this.offset;
		Button db = buttons.get(0);
		
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			b.setY(y + i * (db.getbHeight() + gap));
		}
		
		height = (buttons.size() * db.getbHeight()) + ((buttons.size() - 1) * gap) + (offset * 2);
		
		this.img = image.arrayToImage(3, 3, 0, 0, width, height);
	}

	public void centerButtonsVertical() {
		bv = true;
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			b.setX(pos.getX() + width / 2 - b.getbWidth() / 2);
			if(width + (offset * 2) < b.getbWidth()) width = b.getbWidth() + (offset * 2);
		}
	}
	
	public void centerToWindow() {
		pos.setLocation(Graphics.getWidth() / 2 - width / 2, Graphics.getHeight() / 2 - height / 2);
		if(bud) setButtonsUpToDown(gap);
		if(bv) centerButtonsVertical();
	}
}
