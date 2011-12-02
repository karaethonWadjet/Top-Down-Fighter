package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PChar extends Chaser {
	boolean slasho = false;
	int ticks;
	Rectangle swordbox;
	boolean spinning = false;
	double angle = 2*Math.PI;
	public PChar(int a, int b, Color col, int s, String p, Handler pa, int h) {
		super(a, b, col, s, p, pa, h);
		// TODO Auto-generated constructor stub
	}
	
	public void face(){
		if (!spinning){
			super.face(getAngle(Parent.x, Parent.y, x, y), 50);
		}
		else{
			angle -= .15;
			if (angle <= 0){
				angle = 2*Math.PI;
			}
			facer.setToIdentity();
			facer.translate(x - 45,y - 45);
			facer.rotate(angle,50,50);
		}
	}
	public void draw(Graphics2D g2d){
		if (!dead){
		g2d.drawImage(pic,facer,null);
		g2d.setColor(Color.BLUE);
		//g2d.setTransform(facer);
		noob = new Rectangle(x,y, 60 , 60);
		swordbox = new Rectangle(x,y-40,60,40);
		//g2d.draw(noob);
		//g2d.draw(swordbox);
		if (hp <= 0){
			hp = 0;
			this.die();
		}
		if (slasho){
			ticks++;
			//limits the slash time
			if (ticks >= 5){
				ticks = 0;
				slash();
			}
			g2d.setColor(Color.RED);
			g2d.fillRect(0,-40,60,40);
			g2d.setColor(Color.BLUE);
			g2d.drawString("SLASHUMS", 0, 0);
		}
		g2d.setTransform(identity);}
	}
	public void slash(){
		slasho = !slasho;
	}
	public boolean slashing(){
		return slasho;
	}
	public void spin(){
		spinning = !spinning;
	}
	public boolean hit(Mover m){
		//System.out.printlan(swordbox.intersects(m.noob));
		return swordbox.intersects(m.noob);
	
	}
	
}
