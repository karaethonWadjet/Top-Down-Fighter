package game;

import java.awt.Color;

public class projectile extends Chaser {
	int destx;
	int desty;

	public projectile(int a, int b, Color col, int s, String p, Handler pa,
			int h, int x, int y) {
		super(a, b, col, s, p, pa, h);
		mt = movetype.projectile;
		destx = x;
		desty = y;
		// TODO Auto-generated constructor stub
	}

	public void face() {
		super.face(getAngle(destx, desty, x, y), 50);
	}

	public void move() {
		if (snappy){
		  x = destx;
		  y = desty;
		}
		else{
		moveTo(destx, desty);}
	}
	
}
