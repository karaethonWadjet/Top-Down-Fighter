package game;

import java.awt.Color;

public class Chaser extends Mover {
	protected boolean snappy = false;
	public Chaser(int a, int b, Color col, int s, String p, Handler pa, int h) {
		super(a, b, col, s, p, pa, h);
		mt = movetype.chaser;
		// TODO Auto-generated constructor stub
	}


	public void face(){
		super.face(getAngle(Parent.getPX(),Parent.getPY(),x,y),50);
	}

	public void move(){
		//if (!Parent.getspin()){
		moveTo(Parent.getPX(),Parent.getPY());//}
		//else{
			//moveTo((Parent.getPX() > x ? 0 : 800),(Parent.getPY() > y ? 0 : 600));
		//}
	}
	public void moveTo(double a , double b){		
		double distance = Math.pow(Math.pow((a-x), 2) + Math.pow((b-y), 2), .5);
		double ticks = distance / speed;
		double xmove = Math.abs(a-x) / ticks;
		double ymove = Math.abs(b-y) / ticks;
		if (distance < 5 && !snappy){
    		//x = a;
    		//y = b;
			snappy = true;
    	}
    	else{
    		
    		//float ratio = Math.abs((a - x)/(b - y));
    			if (x+xmove <=800 && x < a){
    				//x+=(ratio < 1 ? speed : speed*ratio);
    				x+=xmove;
    			}
    			else if (x-xmove >=0 && x > a){
    				//x-=(ratio < 1 ? speed : speed*ratio);
    				x-=xmove;
    			}
    			if (y+ymove <=600 && y < b){
    				//y+=(ratio < 1 ? speed*ratio : speed);
    				y+=ymove;
    			}
    			else if (y-ymove >= 0 && y > b){
    				//y-=(ratio < 1 ? speed*ratio : speed);
    				y-=ymove;
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
