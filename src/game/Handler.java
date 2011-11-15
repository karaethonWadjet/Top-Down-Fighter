package game;



import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javazoom.jl.player.Player;



 

public class Handler extends JPanel implements MouseMotionListener{
	/**
	 * hey hey hey
	 */
	
	JFrame frame;
	private static final long serialVersionUID = 1L;
	private int HP;
	Thread music;
	Slasher player;
	Chaser sampley;
	Updown go;
	Image ubw = null;
	Image gu = null;
	Player lplayer;
	Player lplayer2;
	boolean[] directions = {false, false, false, false};
	boolean reached = true;
	String B = "Move here";
	int destx = 225;
	int desty = 225;
	int x;
	int y;
	AudioClip ac;
	Graphics2D g2 = (Graphics2D) getGraphics();
	boolean gameover = false;
	public Handler(){
		super(new GridLayout(0,1));
		try {
			gu = ImageIO.read(new File("data/pchar.png"));
			ubw = ImageIO.read(new File("data/unlimited_blade_works.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
        addMouseMotionListener(this);
        addKeyListener(new shoop());
        addMouseListener(new hoop());
        setPreferredSize(new Dimension(800, 600));
        //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setFocusable(true);
        frame = new JFrame("Top down fighter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
//	      Display the window.
	        frame.pack();
	        frame.setVisible(true);
	}
	
	
	public void restart(){
		if (music != null){
			lplayer2.close();
		}
		try {
/*            FileInputStream fis = new FileInputStream("data/kawai kenji - tenchi hou take.mp3");
			      BufferedInputStream bis = new BufferedInputStream(fis);
			            lplayer = new Player(bis);*/
			            FileInputStream fis2 = new FileInputStream("data/bgm110b.mp3");
			            BufferedInputStream bis2 = new BufferedInputStream(fis2);
			            lplayer2 = new Player(bis2);
					}
			        catch (Exception e) {
			            System.out.println("Problem playing file OH NOES");
			            System.out.println(e);
			        }
		player = new Slasher(225,225,Color.RED,5,"data/pchar.png");
    	sampley = new Chaser(400,400,Color.BLUE,1,"data/face.png");
    	go = new Updown(500,100, Color.GREEN,1,"data/face.png",60);
    	HP = 100;
    	music = new Thread() {
            public void run() {
                try { lplayer2.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        };
        music.start();
	}
	
	public void launchframe(){
		//stopmusic();
        //playzor();
        restart();
        while (!gameover){
    	repaint();
    	if (!player.isDead()){
    	/*if (Math.abs(destx-player.x) < 10 && Math.abs(desty-player.y) < 10){
    		destx = player.x;
			desty = player.y;
			reached = true;
    	}
    	else{
    		if (destx - player.x > 5){
    			player.right();
    		}
    		else{
    			player.left();
    		}
    		if (desty - player.y > 5){
    			player.down();
    		}
    		else{
    			player.up();
    		}
    		
    	}*/
    	if (!player.reached()){
    		player.moveTo(destx,desty);}
    	if (directions[0]){
			player.up();
		}
		if (directions[1]){
			player.down();
		}
		if (directions[2]){
			player.left();
		}
		if (directions[3]){
			player.right();
		}
		if (!sampley.isDead()){
			sampley.moveTo(player.x,player.y);}
		
		if (!go.isDead()){go.move();}
		
    	}
		if (HP <= 0){
			HP = 0;
			player.die();
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
       
	}	
	public static void main(String[] BLARG){
		 try {
	            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
	            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	        } catch (UnsupportedLookAndFeelException ex) {
	            ex.printStackTrace();
	        } catch (IllegalAccessException ex) {
	            ex.printStackTrace();
	        } catch (InstantiationException ex) {

	            ex.printStackTrace();
	        } catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }
	        /* Turn off metal's use of bold fonts */
	       // UIManager.put("swing.boldMetal", Boolean.FALSE);
	        
	        //Schedule a job for the event dispatch thread:
	        //creating and showing this application's GUI.
	        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                launchframe();
	                
	            }
	        });*/
	        //Create and set up the content pane.
	        Handler newContentPane = new Handler();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        newContentPane.launchframe();
	        
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x = arg0.getX();
		y = arg0.getY();
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x = arg0.getX();
		y = arg0.getY();

	}
	
	public void update(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		//ubw.paintIcon(this, g2d, 0, 0);		
		g2d.drawImage(ubw,0,0,null);
		g2d.setColor(Color.BLUE);
		g2d.drawString(B,x,y);
		g2d.setColor(Color.BLACK);		
		g2d.drawLine(player.x,player.y,x,y);
		player.draw(g2d);
		if (!reached){
		g2d.setColor(Color.RED);
		g2d.fillOval(destx-10, desty - 10, 20, 20);
		}
		player.face(getAngle(x,y,player.x,player.y), 50);
		player.draw(g2d);
		sampley.face(getAngle(player.x,player.y,sampley.x,sampley.y),50);
		sampley.draw(g2d);
		
		go.draw(g2d);
		
		g2d.setColor(Color.RED);
		g2d.drawRect(300,10,200,50);
		g2d.fillRect(300,10,HP*2,50);
		g2d.setColor(Color.WHITE);
		//g2d.drawString(NRG + "/100",570, 40);
		g2d.drawString(HP + "/100", 358, 40);
		if (player.isDead()){
			g2d.drawString("Ur dead", 400,400);
		}
		else {
			if (go.collision(player) && !go.isDead() || sampley.collision(player) && !sampley.isDead()){
				HP--;
			}
		}
		if (go.isDead() && sampley.isDead()){
			g2d.drawString("U WIN", 400,300);
		}
		
		
		if (player.slashing()){
			if (player.hit(sampley)){
				sampley.die();
			}
			if (player.hit(go)){
			go.die();}
		}
	}
	
	public void paint(Graphics g){
		update(g);
	}	

	public double getAngle(int x1, int y1, int x2, int y2){

		double Xd = x1 - x2;
		double Yd = y1 - y2;

		double radAngle = Math.atan(Yd/Xd)+ Math.PI/2; //Use atan to calculate the angle
		if (Xd < 0){
			radAngle += Math.PI;
		}
		return radAngle;
	}
	
	public class hoop implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getButton()){
			case MouseEvent.BUTTON1:
				if (B == "BLINK ON!"){
				player.x = arg0.getX();
				player.y = arg0.getY();
				destx = arg0.getX();
				desty = arg0.getY();}
				else{
					player.slash();
				}
				repaint();
				break;
			case MouseEvent.BUTTON3:
				destx = arg0.getX();
				desty = arg0.getY();
				player.setreach(false);
				break;
			}
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class shoop implements KeyListener{

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getKeyCode()){
			case KeyEvent.VK_W:
				directions[0] = true;
			break;
			case KeyEvent.VK_S:
				directions[1] = true;
				break;
			case KeyEvent.VK_A:
				directions[2] = true;
				break;
			case KeyEvent.VK_D:
				directions[3] = true;
				break;
			case KeyEvent.VK_SHIFT:
				B = "BLINK ON!";
				break;
			case KeyEvent.VK_R:
				int n = JOptionPane.showConfirmDialog(frame,
					    "Restart?",
					    "WOULDST THOU",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE
					   );
				System.out.println(n);
				if (n == 0){
					//gameover = true;
					restart();
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			switch(e.getKeyCode()){
			case KeyEvent.VK_W:
				directions[0] = false;
			break;
			case KeyEvent.VK_S:
				directions[1] = false;
				break;
			case KeyEvent.VK_A:
				directions[2] = false;
				break;
			case KeyEvent.VK_D:
				directions[3] = false;
				break;
			case KeyEvent.VK_SHIFT:
				B = "Move here";
				break;
			
			}
			
		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}
}

