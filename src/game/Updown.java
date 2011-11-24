package game;

import java.awt.Color;

public class Updown extends Mover {
	int max;
	int count;
	boolean up;
	public Updown(int a, int b, Color col, int s, String p, int c, Handler pa, int h) {
		super(a, b, col, s, p, pa, h);
		up = true;
		count = 0;
		mt = movetype.updown;
		max = c;
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		count++;
		if (count == max){
			up = !up;
			count = 0;
		}
		if (up){
			up();
			face(2*Math.PI,20);
		}
		else{
			down();
			face(Math.PI, 20);
		}
	}
}
