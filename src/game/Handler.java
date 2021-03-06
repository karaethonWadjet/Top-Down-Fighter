package game;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import javazoom.jl.player.Player;

public class Handler extends JPanel implements MouseMotionListener {
	/**
	 * hey hey hey
	 */
	JPanel mainMenu = new JPanel();
	JLabel text = new JLabel("HOLY CRAP CLICK THE BUTTON");
	JButton starto = new JButton("Start");
	JFrame frame;
	JMenuBar bar = new JMenuBar();
	JMenu pile = new JMenu("pile"), halp = new JMenu("halp");
	JMenuItem ret = new JMenuItem("Main Menu"), quit = new JMenuItem("alt-QQ"),
			credits = new JMenuItem("ab00t");
	private static final long serialVersionUID = 1L;
	Thread music;
	PChar player;
	Mover[] zombies = new Mover[4];
	Image ubw = null, gu = null, bro = null, troll = null;
	Player lplayer, lplayer2;
	boolean[] directions = { false, false, false, false };
	boolean reached = true, victory, back = false;
	String B = "Move here";
	int destx = 225, desty = 225, x, y;
	AudioClip ac;
	Graphics2D g2 = (Graphics2D) getGraphics();
	ArrayList<projectile> shots = new ArrayList<projectile>();
	boolean gamerunning = false;
	String status;

	public Handler() {
		super(new GridLayout(1, 1));
		try {
			gu = ImageIO.read(new File("data/pchar.png"));
			ubw = ImageIO.read(new File("data/unlimited_blade_works.jpg"));
			bro = ImageIO.read(new File("data/Broseph.png"));
			troll = ImageIO.read(new File("data/coolface.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// file menu bar
		quit.addActionListener(new close());
		ret.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gamerunning = false;
			}

		});
		pile.add(ret);
		pile.add(quit);
		halp.add(credits);
		bar.add(pile);
		bar.add(halp);
		// main menu init
		starto.addActionListener(new startgame());
		// add(text, BorderLayout.NORTH);
		// add(starto, BorderLayout.SOUTH);
		// registering controls
		addMouseMotionListener(this);
		addKeyListener(new shoop());
		addMouseListener(new hoop());
		setPreferredSize(new Dimension(800, 600));
		// setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		setFocusable(true);
		frame = new JFrame("Top down fighter");
		frame.setJMenuBar(bar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		// frame.add(mainMenu, BorderLayout.CENTER);
		frame.setSize(new Dimension(800, 600));
		// Display the window.
		// frame.pack();
		frame.setVisible(true);
		mainMenu();
	}

	public void mainMenu() {
		try {
			FileInputStream fis = new FileInputStream(
					"data/Cool Bro has Chill Day.mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);
			lplayer2 = new Player(bis);
		} catch (Exception e) {
			System.out.println("Problem?");
			System.out.println(e);
		}
		music = new Thread() {
			public void run() {
				try {
					lplayer2.play();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		};
		music.start();

	}

	public int getPX() {
		return player.x;
	}

	public int getPY() {
		return player.y;
	}

	public boolean getspin() {
		return player.spinning;
	}

	public void restart() {
		if (music != null) {
			lplayer2.close();
		}
		try {
			FileInputStream fis2 = new FileInputStream("data/bgm110b.mp3");
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			lplayer2 = new Player(bis2);
		} catch (Exception e) {
			System.out.println("Problem playing file OH NOES");
			System.out.println(e);
		}
		player = new PChar(225, 225, Color.RED, 5, "data/pchar.png", this, 100);
		zombies[0] = new Chaser(400, 400, Color.BLUE, 1, "data/face.png", this,
				10);
		zombies[1] = new Chaser(600, 600, Color.BLUE, 1, "data/face.png", this,
				10);
		zombies[2] = new Updown(500, 100, Color.GREEN, 1, "data/face.png", 60,
				this, 10);
		zombies[3] = new Chaser(100, 400, Color.RED, 1, "data/face.png", this,
				10);
		music = new Thread() {
			public void run() {
				try {
					lplayer2.play();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		};
		music.start();
	}

	public void gamerun() {
		int regen = 500;
		gamerunning = true;
		restart();
		while ((!victory || !player.isDead()) && gamerunning) {
			repaint();
			status = (player.hp >= 50 ? "Healthy" : (player.hp == 0 ? "Dead"
					: "Dying"));
			if (!player.isDead()) {
				// passive regen while not in combat, so pro
				if (player.hp < 100 && !status.equals("Getting Hurt")) {
					regen--;
					if (regen == 0) {
						player.hp++;
						regen = 500;
					}
				}
				if (!player.reached()) {
					player.moveTo(destx, desty);
				}
				if (directions[0]) {
					player.up();
				}
				if (directions[1]) {
					player.down();
				}
				if (directions[2]) {
					player.left();
				}
				if (directions[3]) {
					player.right();
				}
				// preparing to separate enemies and background into another
				// class
				// to support multiple 'levels'
				victory = true;
				for (int f = 0; f < zombies.length; f++) {
					if (!zombies[f].isDead()) {
						victory = false;
						zombies[f].move();
						if ((player.slashing() || player.spinning)
								&& player.hit(zombies[f])) {
							zombies[f].hit(1);
						}
						if (zombies[f].collision(player)) {
							player.hit(1);
							status = "Getting Hurt";
						}
					}
				}
				if (!shots.isEmpty()) {
					for (int c = 0; c < shots.size(); c++) {
						if (shots.get(c).reached()) {
							shots.remove(c);
						} else {
							shots.get(c).move();
						}
					}
				}

			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lplayer2.close();
		mainMenu();
	}

	public static void main(String[] BLARG) {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
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

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		/*
		 * javax.swing.SwingUtilities.invokeLater(new Runnable() { public void
		 * run() { launchframe();
		 * 
		 * } });
		 */
		// Create and set up the content pane.
		Handler newContentPane = new Handler();
		newContentPane.setOpaque(true); // content panes must be opaque

	}

	public void mouseDragged(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();
	}

	public void mouseMoved(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();

	}

	public void update0(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 800, 600);
		g2d.drawImage(bro, 0, 0, null);
		g2d.drawImage(troll, 475, 0, null);
		g2d.setColor(Color.RED);
		g2d.fill3DRect(300, 260, 200, 75, true);
		g2d.setColor(Color.BLUE);
		g2d.fill3DRect(300, 350, 200, 75, false);
		g2d.setColor(Color.BLACK); // BLAPCK
		g2d.drawString("MAIN MENU sord....", 375, 100);
		g2d.drawString("START!!!", 375, 300);
		g2d.drawString("the...instuctions", 375, 400);

	}

	public void update1(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// ubw.paintIcon(this, g2d, 0, 0);
		g2d.drawImage(ubw, 0, 0, null);
		g2d.setColor(Color.BLUE);
		g2d.drawString(B, x, y);
		g2d.setColor(Color.BLACK);
		g2d.drawLine(player.x, player.y, x, y);
		player.draw(g2d);
		if (!reached) {
			g2d.setColor(Color.RED);
			g2d.fillOval(destx - 10, desty - 10, 20, 20);
		}
		player.face();
		player.draw(g2d);
		for (int i = 0; i < zombies.length; i++) {
			zombies[i].face();
			zombies[i].draw(g2d);
		}
		g2d.setColor(Color.RED);
		g2d.drawRect(300, 10, 200, 50);
		g2d.fillRect(300, 10, player.hp * 2, 50);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Status: " + status, 100, 50);
		// g2d.drawString(NRG + "/100",570, 40);
		g2d.drawString(player.hp + "/100", 358, 40);
		if (player.isDead()) {
			g2d.drawString("Ur dead", 400, 400);
		} else if (victory) {
			g2d.drawString("A winnar is you", 400, 300);
		}
		if (!shots.isEmpty()) {
			for (int j = 0; j < shots.size(); j++) {
				shots.get(j).face();
				shots.get(j).draw(g2d);
			}
		}
	}

	public void paint(Graphics g) {
		if (gamerunning) {
			update1(g);
		} else {
			update0(g);
		}
	}

	public double getAngle(int x1, int y1, int x2, int y2) {

		double Xd = x1 - x2;
		double Yd = y1 - y2;

		double radAngle = Math.atan(Yd / Xd) + Math.PI / 2; // Use atan to
															// calculate the
															// angle
		if (Xd < 0) {
			radAngle += Math.PI;
		}
		return radAngle;
	}

	// EVENT LISTENERS DOWN HERE
	public class hoop implements MouseListener {

		public void mouseClicked(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {

		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseReleased(MouseEvent arg0) {
			if (gamerunning) {
				switch (arg0.getButton()) {
				case MouseEvent.BUTTON1:
					if (B == "BLINK ON!" && !player.isDead()) {
						player.x = arg0.getX();
						player.y = arg0.getY();
						destx = arg0.getX();
						desty = arg0.getY();
					} else {
						// player.slash();
						// FIRE!!!
						shots.add(new projectile(getPX(), getPY(), Color.GREEN,
								5, "utterbs", null, 1, arg0.getX(), arg0.getY()));
					}
					repaint();
					break;
				case MouseEvent.BUTTON3:
					destx = arg0.getX();
					desty = arg0.getY();
					player.setreach(false);
					break;
				}
			} else {
				if (arg0.getX() >= 300 && arg0.getX() <= 500
						&& arg0.getY() >= 260 && arg0.getY() <= 335) {
					Thread bluh = new Thread() {
						public void run() {
							gamerun();
						}
					};
					bluh.start();
				} else if (arg0.getX() >= 300 && arg0.getX() <= 500
						&& arg0.getY() >= 350 && arg0.getY() <= 425) {
					JOptionPane
							.showMessageDialog(
									null,
									"WASD or right click to move \n Space bar or left click to attack \n Kill everything that moves \n glhf",
									"Controls", JOptionPane.QUESTION_MESSAGE);
				}

			}
		}
	}

	public class shoop implements KeyListener {

		public void keyPressed(KeyEvent arg0) {
			if (gamerunning) {
				reached = true;
				player.setreach(true);
				switch (arg0.getKeyCode()) {
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
				case KeyEvent.VK_Q:
					player.spin();
					break;
				case KeyEvent.VK_SHIFT:
					B = "BLINK ON!";
					break;
				case KeyEvent.VK_SPACE:
					player.slash();
					// FIRE!!!
					shots.add(new projectile(getPX(), getPY(), Color.GREEN, 5,
							"utterbs", null, 1, MouseInfo.getPointerInfo()
									.getLocation().x, MouseInfo
									.getPointerInfo().getLocation().y));
					break;
				case KeyEvent.VK_R:
					int n = JOptionPane.showConfirmDialog(frame, "Restart?",
							"WOULDST THOU", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (n == 0) {
						restart();
					}
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			if (gamerunning) {
				switch (e.getKeyCode()) {
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
		}

		public void keyTyped(KeyEvent e) {

		}

	}

	public class close implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	public class startgame implements ActionListener {
		public void actionPerformed(ActionEvent c) {
			gamerun();
		}
	}
}
