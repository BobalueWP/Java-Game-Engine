package engine.game;

import engine.main.Graphics;
import engine.main.Vector2f;
import engine.graphics.Color;

public class GameTime {

	private boolean dayTime;

	private int sec;
	private int min;
	private int hour;

	private float time;

	private float dayNightTransition = 0;
	private float dayNightTransitionSpeed = 1;

	private float dayTimeHour;
	private float nightTimeHour;
	
	private Vector2f pos;

	private float speed = 1.0f;

	public GameTime(int x, int y, float dayTimeHour, float nightTimeHour, float time, float timeSpeed, float dayNightTransitionSpeed) {
		pos = new Vector2f(x, y);
		this.dayTimeHour = dayTimeHour;
		this.nightTimeHour = nightTimeHour;
		
		float t = time % 24;
		
		this.time = t * 60 * 60;

		if(t >= 0 & t < 12) dayTime = true;

		if(t >= 12 & t < 24) dayTime = false;

		if(t >= dayTimeHour) {
			if(t <= dayTimeHour + dayNightTransitionSpeed) dayNightTransition = t - 6.0f;
			else dayNightTransition = 1;
		} else if(t > nightTimeHour + dayNightTransitionSpeed) dayNightTransition = 0;
		
		if(t >= nightTimeHour) {
			if(t <= nightTimeHour + dayNightTransitionSpeed) dayNightTransition = Math.abs(t - 18.0f - 1);
			else dayNightTransition = 0;
		} else if(t > dayTimeHour + dayNightTransitionSpeed) dayNightTransition = 1;

		this.speed = timeSpeed;
		this.dayNightTransitionSpeed = dayNightTransitionSpeed / 60 / 60;
	}

	public void update() {
		time += speed;

		sec = (int)time % 60;

		min = (int)time / 60 % 60;

		hour = (int)time / 60 / 60;

		if(hour == 24) time = 0;

		if(hour >= 0 & hour < 11) { 
			dayTime = true;
			if(hour >= dayTimeHour & dayNightTransition < 1) dayNightTransition += dayNightTransitionSpeed;
		}

		if (hour >= 12 & hour < 24) {
			dayTime = false;
			if(hour >= nightTimeHour & dayNightTransition > 0) dayNightTransition -= dayNightTransitionSpeed;
		}

		if(dayNightTransition > 1) dayNightTransition = 1;
		if(dayNightTransition < 0) dayNightTransition = 0;
	}

	public void render(Graphics g) {
		String var = "";
		if(dayTime) var = "am";
		else var = "pm";
		g.setColor(Color.WHITE);
		g.drawString(time() + " " + var, (int)pos.getX(), (int)pos.getY());
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isSetTime(float hour, float min, float sec) {
		return this.hour == hour && this.min == min && this.sec == sec;
	}

	public boolean isSetHour(float hour) {
		if(this.hour == hour) return true;
		return false;
	}

	private String time() {
		return String.format("%02d : %02d : %02d", hour(), min(), sec());
	}

	private int min() {
		return (int) min;
	}

	private int hour() {
		return (int) hour;
	}

	private int sec() {
		return (int) sec;
	}

	public float getDayNightTransition() {
		return dayNightTransition;
	}
}
