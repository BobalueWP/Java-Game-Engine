package engine.game.level.tile;

import engine.game.Animator;
import engine.main.Image;
import engine.graphics.Color;

public class AnimatedAutoTile extends BasicTile {

Animator animator;
	
	public AnimatedAutoTile(Image image, int frames, Color color) {
		super(image, color);
		animator = new Animator(image.getImages(), frames);
		animator.play();
		setAnimator(animator);
	}
	
	public void hasBrightness(int iBrightness, boolean bBrightness) {
		setiBrightness(iBrightness);
		setbBrightness(bBrightness);
	}
	
}
