package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Slasher extends Chaser {
	boolean slasho = false;
	Rectangle swordbox;
	public Slasher(int a, int b, Color col, int s, String p) {
		super(a, b, col, s, p);
		// TODO Auto-generated constructor stub
	}

	
	public void draw(Graphics2D g2d){
		if (!dead){
		g2d.drawImage(pic,facer,null);}
		g2d.setColor(Color.BLUE);
		g2d.setTransform(facer);
		noob = new Rectangle(x,y, 60 , 60);
		swordbox = new Rectangle(x,y-40,60,40);
		//g2d.draw(noob);
		//g2d.draw(swordbox);
		if (slasho){
			
			g2d.setColor(Color.RED);
			g2d.fillRect(0,-40,60,40);
			g2d.setColor(Color.BLUE);
			g2d.drawString("SLASHUMS", 0, 0);
		}
		g2d.setTransform(identity);
	}
	public void slash(){
		slasho = !slasho;
	}
	public boolean slashing(){
		return slasho;
	}
	public boolean hit(Mover m){
		//System.out.println(swordbox.intersects(m.noob));
		return swordbox.intersects(m.noob);
	
	}
	
}
