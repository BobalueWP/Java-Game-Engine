package engine.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private static boolean[] presses = new boolean[526];
	private static String keyTyped;
	private static boolean[] pressed = new boolean[526];
	
	public static boolean isKeyPressedOnce(int key) {
		if(isKeyPressed(key) & !pressed[key]) return true;
		return false;
	}
	
	public static boolean isKeyPressed(int key) {
		if(presses[key]) return true;
		return false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		presses[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		presses[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int code = e.getKeyChar();
		
		if(code != KeyEvent.VK_BACK_SPACE & code != KeyEvent.VK_ESCAPE & code != KeyEvent.VK_DELETE)
		keyTyped = e.getKeyChar() + "";
	}
	
	public static String getKeyTyped() {
		return keyTyped;
	}
	
	public static void reset() {
		for (int i = 0; i < presses.length; i++) pressed[i] = presses[i];
		keyTyped = "";
	}
}
