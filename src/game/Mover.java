package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Mover {
	AffineTransform identity = new AffineTransform();
	AffineTransform facer = new AffineTransform();
	Image pic;
	public enum Direction{Left, Right, Up, Down};
	int x;
	int y;
	Color c;
	int speed;
	Direction d;
	boolean dead;
	String type;
	Rectangle2D noob = new Rectangle();

	public Mover(int a, int b, Color col, int s, String p){
		x = a;
		y = b;
		c = col;
		speed = s;
		dead = false;
		//d = dir;
		facer.setTransform(identity);
		try {
			pic = ImageIO.read(new File(p));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void left(){
		if (x-speed >= 0){
			x-=speed;
		}
		d = Direction.Left;
	}
	public void right(){
		if (x+speed <=1024){
			x+=speed;
		}
		d = Direction.Right;
	}
	public void up(){
		if (y-speed >=0){
			y-=speed;
		}
		d = Direction.Up;
	}
	public void down(){
		if (y+speed <=768){
			y+=speed;
		}
		d = Direction.Down;
	}
	
	public void face(double radAngle, int a){
		facer.setTransform(identity);
		facer.translate(x - a + 10,y - a + 5);
		facer.rotate(radAngle,a,a);
	}
	
	public void draw(Graphics2D g2d){
		//g2d.setColor(c);
		//g2d.fillOval(x-20, y-20, 40, 40);
		//g2d.drawString("lol",x, y);
		//kirby.paintIcon(g2d, x-20, y-20);
		noob = new Rectangle(x,y, 60 , 60);
		
		if (!dead){
		//g2d.draw(noob);
		g2d.drawImage(pic,facer,null);}
		g2d.setColor(Color.BLUE);
		//g2d.setTransform(facer);
		//g2d.setTransform(identity);
	}
	
	public void die(){
		dead = true;
	}
	public boolean isDead(){
		return dead;
	}
	
	public boolean collision(Mover m){
	
		//return (Math.abs(m.x - this.x) <= 5 && Math.abs(m.y - this.y) <= 5);
		return noob.intersects(m.noob);
		
	}
	
}
