package game;

import java.awt.Color;

public class Chaser extends Mover {
	private boolean snappy = true;
	
	public Chaser(int a, int b, Color col, int s, String p, Handler pa) {
		super(a, b, col, s, p, pa);
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
    		
		
    		if (a - x > 5){
    			right();
    		}
    		else{
    			left();
    		}
    		if (b - y > 5){
    			down();
    		}
    		else{
    			up();
    		}
    		
    	}
	}
	public boolean reached(){
		return snappy;
	}
	public void setreach(boolean b){
		snappy = b;
	}
}
