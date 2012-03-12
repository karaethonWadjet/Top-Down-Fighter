package game;

import java.awt.Color;

public class Chaser extends Mover {
	private boolean snappy = true;
	public Chaser(int a, int b, Color col, int s, String p, Handler pa, int h) {
		super(a, b, col, s, p, pa, h);
		mt = movetype.chaser;
		// TODO Auto-generated constructor stub
	}


	public void face(){
		super.face(getAngle(Parent.getPX(),Parent.getPY(),x,y),50);
	}
	
	public void move(){
		moveTo(Parent.getPX(),Parent.getPY());
	}
	public void moveTo(int a , int b){
		if (Math.abs(a-x) < 10 && Math.abs(b-y) < 10 && !snappy){
    		//x = a;
    		//y = b;
			snappy = true;
    	}
    	else{	
    		float ratio = (a - x)/(b - y);
    		if (ratio < 1){
    			if (x+speed <=800){
    				x+=speed/(b-y);
    			}
    			if (y+speed <=600){
    				y+=speed;
    			}
    		}
    		else {
    			if (x+speed <=800){
    				x+=speed;
    			}
    			if (y+speed <=600){
    				y+=(a-x)/speed;
    			}
    		}
    		
//    		if (a - x > 5){
//    			right();
//    		}
//    		else{
//    			left();
//    		}
//    		if (b - y > 5){
//    			down();
//    		}
//    		else{
//    			up();
//    		}
    		
    	}
	}
	public boolean reached(){
		return snappy;
	}
	public void setreach(boolean b){
		snappy = b;
	}
}
