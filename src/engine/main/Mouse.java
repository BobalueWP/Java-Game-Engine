package engine.main;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static final int LEFT = 1;
	public static final int CENTER = 2;
	public static final int RIGHT = 3;

	private static Vector2f pos = new Vector2f();

	private static double dWheel;

	private static double rotation;
	
	private static boolean[] presses = new boolean[4];
	private static boolean[] clicks = new boolean[4];
	private static JComponent canvas;
	private static Vector2f dragPos = new Vector2f();
	private static boolean pressed;
	private static boolean clicked;
	
	public Mouse(JComponent canvas) {
		Mouse.canvas = canvas;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		clicks[e.getButton()] = true;
		clicked = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		presses[e.getButton()] = true;
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		presses[e.getButton()] = false;
		pressed = false;
		dragPos.setLocation(0, 0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		rotation = e.getPreciseWheelRotation();
		dWheel = e.getPreciseWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		presses[e.getButton()] = true;
		dragPos.setLocation(e.getX() - pos.getX(), e.getY() - pos.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		pos.setLocation(e.getX(), e.getY());
	}
	
	public static double getDragPosX() {
		return dragPos.getX();
	}
	
	public static double getDragPosY() {
		return dragPos.getY();
	}
	
	public static void setCursor(int type) {
		canvas.setCursor(new Cursor(type));
	}
	
	public static boolean getClicks(int button) {
		return clicks[button];
	}
	
	public static boolean getPresses(int button) {
		return presses[button];
	}

	public static boolean contains(int x, int y, int width, int height) {
		return getX() >= x & getY() >= y & getX() <= x + width & getY() <= y + height;
	}
	
	public static void reset() {
		for (int i = 0; i < clicks.length; i++) if(clicks[i]) clicks[i] = false;
		if(rotation != 0) dWheel = 0;
	}
	
	public static int getX() {
		return (int) (pos.getX() / Display.getScaleWidth());
	}

	public static int getY() {
		return (int) (pos.getY() / Display.getScaleHeight());
	}

	public static double getD_Wheel() {
		return dWheel;
	}
	
	public static boolean isClicked() {
		return clicked;
	}
	
	public static boolean isPressed() {
		return pressed;
	}
	
	public static double getRotation() {
		return rotation;
	}
}
