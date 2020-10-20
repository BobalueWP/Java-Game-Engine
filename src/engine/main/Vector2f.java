package engine.main;

import java.awt.geom.Point2D;

public class Vector2f extends Point2D {

	private double x;
	private double y;
	private static double x_offset;
	private static double y_offset;
	
	public Vector2f() {
		setLocation(0, 0);
	}
	
	public Vector2f(double x, double y) {
		setLocation(x, y);
	}
	
	public void set(Vector2f newPos) {
		setLocation(newPos.x, newPos.y);
	}
	
	public void zero() {
		setLocation(0, 0);
	}
	
	@Override
	public void setLocation(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public void normalize() {
		double length = mag();
		if (length != 0.0f) {
			double s = 1.0d / length;
			x = x * s;
			y = y * s;
		}
	}
	
	public boolean equals(double x, double y) {
		return (this.x == x && this.y == y);
	}
	
	public Vector2f add(Vector2f v) {
		return new Vector2f(this.x += v.x, this.y += v.y);
	}
	
	public Vector2f add(double x, double y) {
		return new Vector2f(this.x += x, this.y += y);
	}
	
	public Vector2f add(double loc) {
		return new Vector2f(this.x += loc, this.y += loc);
	}
	
	public Vector2f sub(Vector2f v) {
		return new Vector2f(this.x -= v.x, this.y -= v.y);
	}
	
	public Vector2f sub(double x, double y) {
		return new Vector2f(this.x -= x, this.y -= y);
	}
	
	public Vector2f sub(double loc) {
		return new Vector2f(this.x -= loc, this.y -= loc);
	}
	
	public Vector2f mult(Vector2f v) {
		return new Vector2f(this.x *= v.x, this.y *= v.y);
	}
	
	public Vector2f mult(double x, double y) {
		return new Vector2f(this.x *= x, this.y *= y);
	}
	
	public Vector2f mult(double loc) {
		return new Vector2f(this.x *= loc, this.y *= loc);
	}
	
	public Vector2f div(Vector2f v) {
		return new Vector2f(this.x /= v.x, this.y /= v.y);
	}
	
	public Vector2f div(double x, double y) {
		return new Vector2f(this.x /= x, this.y /= y);
	}
	
	public Vector2f div(double loc) {
		return new Vector2f(this.x /= loc, this.y /= loc);
	}
	
	public Vector2f getLocation(){
		return new Vector2f(this.x, this.y);
	}
	
	public Vector2f midpoint(Vector2f v) {
		return new Vector2f(this.x + v.x / 2, this.y + v.y / 2);
	}
	
	public Vector2f midpoint(double x, double y) {
		return new Vector2f(this.x + x / 2, this.y + y / 2);
	}
	
	public Vector2f limit(double max) {
		if(magSq() > Math.pow(max, 2)) {
			normalize();
			mult(max);
		}
		return this;
	}
	
	public Vector2f direction(Vector2f v) {
		Vector2f dif = new Vector2f(x, y).sub(v);
		return dif.div(dif.mag()).mult(-1);
	}
	
	public Vector2f direction(double x, double y) {
		Vector2f d = new Vector2f(this.x, this.y);
		Vector2f dif = d.sub(x, y);
		return dif.div(dif.mag()).mult(-1);
	}
	
	public double magSq() {
		return this.x * this.x + this.y * this.y;
	}
	
	public double mag() {
		return Math.sqrt(magSq());
	}
	
	public double getAngle(Vector2f v, double offset) {
		return Math.toDegrees(heading(v)) - offset;
	}
	
	public double getAngle(double x, double y, double offset) {
		return Math.toDegrees(heading(x, y)) - offset;
	}
	
	public double heading(Vector2f v) {
		return Math.atan2(v.y - this.y, v.x - this.x);
	}
	
	public double heading(double x, double y) {
		return Math.atan2(y - this.y, x - this.x);
	}
	
	public static void setOffset(double x_offset, double y_offset){
		Vector2f.x_offset = x_offset;
		Vector2f.y_offset = y_offset;
	}
	
	public Vector2f getOffsetLocation() {
		return new Vector2f(x - x_offset, y - y_offset);
	}
	
	public double getOffsetDistance(Vector2f v){
		double x = getOffsetLocation().x - v.getOffsetLocation().x;
		double y = getOffsetLocation().y - v.getOffsetLocation().y;
		return Math.sqrt(x * x + y * y);
	}
	
	@Override
	public double getX() {return x;}

	@Override
	public double getY() {return y;}
	
	public static double getX_offset() {return x_offset;}
	
	public static double getY_offset() {return y_offset;}
	
	public void setY(double y) {this.y = y;}
	
	public void setX(double x) {this.x = x;}
}
