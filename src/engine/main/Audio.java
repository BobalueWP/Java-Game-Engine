package engine.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

	private Clip clip;
	private int frames;
	private FloatControl masterGain;
	private FloatControl pan;
	private BooleanControl mute;

	public static final float MAX_GAIN = 6.0206f;
	public static final float MIN_GAIN = -80.0f;

	public Audio(String path) {
		File file = new File(path);

		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}

		masterGain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		pan = (FloatControl)clip.getControl(FloatControl.Type.PAN);
		mute = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);
	}

	public void play() {
		if(!clip.isRunning()) {
			clip.setFramePosition(frames);
			clip.start();
		}
	}

	public void stop() {
		if(clip.isRunning()) {
			frames = 0;
			clip.stop();
		}
	}

	public void pause() {
		if(clip.isRunning()) {
			frames = clip.getFramePosition();
			clip.stop();
		}
	}

	public void setGain(float gain) {
		if(gain > MAX_GAIN) gain = MAX_GAIN;
		if(gain < MIN_GAIN) gain = MIN_GAIN;
		masterGain.setValue(gain);
	}

	public void setPan(float pan) {
		if(pan > 1.0f) pan = 1.0f;
		if(pan < -1.0f) pan = -1.0f;
		this.pan.setValue(pan);
	}

	public void setMute(boolean mute) {
		this.mute.setValue(mute);
	}

	public void setControls(float gain, float pan, boolean mute) {
		if(gain > MAX_GAIN) gain = MAX_GAIN;
		if(gain < MIN_GAIN) gain = MIN_GAIN;
		masterGain.setValue(gain);

		if(pan > 1.0f) pan = 1.0f;
		if(pan < -1.0f) pan = -1.0f;
		this.pan.setValue(pan);

		this.mute.setValue(mute);
	}
	
	public Clip getClip() {
		return clip;
	}
}
