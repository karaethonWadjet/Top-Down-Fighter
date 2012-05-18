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
		if (!Parent.getspin()){
		moveTo(Parent.getPX(),Parent.getPY());}
		else{
			moveTo((Parent.getPX() > x ? 0 : 800),(Parent.getPY() > y ? 0 : 600));
		}
	}
	public void moveTo(int a , int b){
		if (Math.abs(a-x) < 5 && Math.abs(b-y) < 5 && !snappy){
    		//x = a;
    		//y = b;
			snappy = true;
    	}
    	else{
    		if (b - y == 0){
    		
    		}
    		else{
    		//float ratio = Math.abs((a - x)/(b - y));
    			if (x+speed/(b-y) <=800 && x < a){
    				//x+=(ratio < 1 ? speed : speed*ratio);
    				x+=speed;
    			}
    			else if (x-speed/(b-y) >=0 && x > a){
    				//x-=(ratio < 1 ? speed : speed*ratio);
    				x-=speed;
    			}
    			if (y+speed <=600 && y < b){
    				//y+=(ratio < 1 ? speed*ratio : speed);
    				//y+=speed;
    			}
    			else if (y-speed >= 0 && y > b){
    				//y-=(ratio < 1 ? speed*ratio : speed);
    				y-=speed;
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
