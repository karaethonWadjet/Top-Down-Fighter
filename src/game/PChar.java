package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PChar extends Chaser {
	boolean slasho = false;
	int ticks;
	int q;
	int qcd = 500;
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
		g2d.setColor(Color.CYAN);
		//g2d.setTransform(facer);
		noob = new Rectangle(x-40,y-40, 80 , 80);
		swordbox = new Rectangle(x-50,y-70,50,20);
		//g2d.draw(noob);
		g2d.draw(swordbox);
		g2d.fillRect(x-50,y-70,qcd/10,20);
		if (hp <= 0){
			hp = 0;
			this.die();
		}
		if (slasho){
			ticks++;
			//limits the slash time
			if (ticks >= 10){
				ticks = 0;
				slash();
			}
			g2d.setColor(Color.RED);
			g2d.fillRect(0,-40,60,40);
			g2d.setColor(Color.BLUE);
			g2d.drawString("SLASHUMS", 0, 0);
		}
		if (spinning){
			q++;
			//I can't be having you spin to win all day
			if (q >= 500){
				q = 0;
				spinning = false;
				qcd = 0;
			}
		}
		if (qcd != 500){
			qcd++;
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
		if (qcd == 500){
		spinning = true;}
	}
	public boolean hit(Mover m){
		//System.out.printlan(swordbox.intersects(m.noob));
		if (spinning){
			return noob.intersects(m.noob);
		}
		return swordbox.intersects(m.noob);
	
	}
	
}
