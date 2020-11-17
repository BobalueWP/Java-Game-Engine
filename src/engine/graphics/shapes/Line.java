package engine.graphics.shapes;

import java.awt.Graphics2D;

public class Line extends Shape {

	int x0;
	int x1;
	int y0;
	int y1;
	
	public Line(int x0, int y0, int x1, int y1) {
		super(x0, y0, x1-x0, y1-y0);
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
	}

	int[] pll(int x0, int y0, int x1, int y1, int[] rgb) {
		int dx = x1 - x0;
		int dy = y1 - y0;
		int yi = 1;

		if(dy < 0) {
			yi = -1;
			dy = -dy;
		}

		int D = 2 * (dy - dx);
		int y = y0;

		for (int x = 0; x <= Math.abs(dx); x++) {
			if(x >= Math.abs(dx)) x = Math.abs(dx);
			if(y >= Math.abs(dy)) y = Math.abs(dy);
			int index = x + y * Math.abs(dx);

			if(color != null) rgb[index] = color.getARGB();
			if(D >= 0) {
				y = y + yi;
				D = D - 2 * dx;
			}
			D = D + 2 * dy;
		}
		return rgb;
	}

	int[] plh(int x0, int y0, int x1, int y1, int[] rgb) {
		int dx = x1 - x0;
		int dy = y1 - y0;
		int xi = 1;

		if(dx < 0) {
			xi = -1;
			dx = -dx;
		}

		int D = 2 * (dx - dy);
		int x = x0;

		for (int y = 0; y <= Math.abs(dy); y++) {
			if(x >= Math.abs(dx)) x = Math.abs(dx);
			if(y >= Math.abs(dy)) y = Math.abs(dy);
			int index = x + y * Math.abs(dx);

			if(color != null) rgb[index] = color.getARGB();
			if(D >= 0) {
				x = x + xi;
				D = D - 2 * dy;
			}
			D = D + 2 * dx;
		}
		return rgb;
	}

	@Override
	void dArray() {
				/*int dx = width - x;
				int dy = height - y;

				if(dx < 1) dx = 1;
				if(dy < 1) dy = 1;

				int size = Math.abs(dx * dy);

				int[] rgb = new int[size];

				float slope = 0;
				
				if(dx > dy) { 
					slope = (float)Math.abs(dx) / (float)Math.abs(dy);

					int y = 0;
					int c = 0;

					for (int x = 0; x <= dx-1; x++) {
						c++;
						
						int index =x+y*dx;
						if(index < size)
						if(color != null) rgb[index] = color.getARGB();
						
						if(c>slope) {
							y++;
							c = 0;
						}
					}
				}*/
				
				//this.rgb = rgb;
				
				/*if(dy<dx) {
					if(x0>x1)rgb = pll(x1,y1,x0,y0, rgb);
					else rgb = pll(x0,y0,x1,y1, rgb);
				} else {
					if(y0>y1)rgb = plh(x1,y1,x0,y0, rgb);
					else rgb = plh(x0,y0,x1,y1, rgb);
				}*/
		classErr = "line";
	}

	@Override
	void dGraphics(Graphics2D g) {
		if(color != null)
		g.setColor(new java.awt.Color(color.getARGB()));
		
		/*if(x1 - x0 == 0) x = -1;
		else x = 0;
		
		if(y1 - y0 == 0) y = -1;
		else y = 0;*/
		
		//g.clearRect(0, 0, w, h);
		g.drawLine(x0, y0, x1, y1);
	}
}
