package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;

import entities.*;
import griffith.Main;
import utils.Timer;

import javax.swing.JButton;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GamePanel extends JPanel implements KeyListener, Runnable {

	final int TileSize = 16 * 3;
	final int ScreenWidth = TileSize * 16;
	final int ScreenHeight = TileSize * 16;
	final int UI_Height = 50;

	Thread gameThread;
	public Player player1;
	public Player player2;
	
	// Game Items
	Hazard firePool;
	Hazard waterPool;
	Hazard greenPool;
	Door door;
	Bottom bottom;
	ArrayList<Floor> floors;
	ArrayList<Floor> iceFloor;
	ArrayList<Floor> openWall;
	ArrayList<Coin> coins;
	ArrayList<Portal> portal;
	ArrayList<BouncePad> bouncePads;
	
	Timer gameTimer = new Timer(); 
	private Main main;
	private String difficulty;
	public Image coinImage;

	// Controls
	boolean p1Up, p1Down, p1Left, p1Right;
	boolean p2Up, p2Down, p2Left, p2Right;
	
	public boolean pressBottom = false;

	public GamePanel() {
		this(null, "easy");
	}

	public GamePanel(Main main, String difficulty) {
		this.main = main;
		this.difficulty = difficulty;

		double scale = (main != null) ? main.scale : 1.0;
		int scaledW = (int) (ScreenWidth * scale);
		int scaledH = (int) (ScreenHeight * scale);

		this.setPreferredSize(new Dimension(scaledW, scaledH));
		this.setDoubleBuffered(true);
		this.setLayout(null);
		setBackground(new Color(18, 18, 38));

		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/static/image/elements/coin.png"));
			coinImage = icon.getImage();
		} catch (Exception e) {
			System.out.println("Could not find the coin image.");
		}

		setFocusable(true);
		addKeyListener(this);

		//here it will load the level
		Level currentLevel;
		
		if ("muad".equals(difficulty)) {
		    currentLevel = new MuadLevel();
		    
		// if you want to add you level to be loaded you need to do this 
		//example:     
		/*
		  else if ("example".equals(difficulty)) {
		    currentLevel = new example(); 
		}
		  
		  
		  
		 */    
		   
		    
		} else {
			//this is the default level
		    currentLevel = new EasyLevel();
		}

		// Spawn point for players 
		player1 = new Player(currentLevel.p1StartX, currentLevel.p1StartY, Type.FIRE);
		player2 = new Player(currentLevel.p2StartX, currentLevel.p2StartY, Type.WATER);

		// here it loads all the data of the level
		this.floors = currentLevel.floors;
		this.iceFloor = currentLevel.iceFloor;
		this.openWall = currentLevel.openWall;
		this.coins = currentLevel.coins;
		this.door = currentLevel.door;
		this.bottom = currentLevel.bottom;
		this.firePool = currentLevel.firePool;
		this.waterPool = currentLevel.waterPool;
		this.greenPool = currentLevel.greenPool;
		this.portal = currentLevel.portal;
		this.bouncePads = currentLevel.bouncePads;


		// ADD MENU BUTTON
		if (main != null) {
			JButton menuButton = new JButton("MENU");
			menuButton.setBounds(ScreenWidth - 90, 10, 80, 30);
			menuButton.setFont(new Font("Arial", Font.BOLD, 14));
			menuButton.setBackground(new Color(200, 200, 200));
			menuButton.setForeground(Color.BLACK);
			menuButton.setFocusPainted(false);
			menuButton.addActionListener(e -> main.showMainMenu());
			this.add(menuButton);
		}
	}

	public void startGame() {
		gameTimer.start(); 
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void updateGame() {
		for (Floor floor : floors) {
			floor.stopFallThrough(player1);
			floor.stopFallThrough(player2);
			player1.speed = 5;
		}
		for (Floor floor : iceFloor) {
			floor.stopFallThrough(player1);
			floor.stopFallThrough(player2);
			if (floor.isOnFloor(player1)) {
				player1.speed = 2;
			}
		}
		if(!pressBottom) {
			for (Floor floor : openWall) {
				floor.stopFallThrough(player1);
				floor.stopFallThrough(player2);
			}
		}

		if (p1Up) player1.moveUp();
		if (p1Left) player1.moveLeft();
		if (p1Right) player1.moveRight();

		if (p2Up) player2.moveUp();
		if (p2Left) player2.moveLeft();
		if (p2Right) player2.moveRight();

		player1.gravity();
		player2.gravity();

		if (firePool != null) { firePool.check(player1); firePool.check(player2); }
		if (waterPool != null) { waterPool.check(player1); waterPool.check(player2); }
		if (greenPool != null) { greenPool.check(player1); greenPool.check(player2); }
		
		if (bottom != null) {
		    bottom.press(player1);
		    bottom.press(player2);
		}

		// here we check if the player did pick up the coin
		for (Coin coin : coins) {
			coin.checkCollision(player1);
			coin.checkCollision(player2);
		}
		
		if (portal != null) {
			for (Portal portal : portal) {
				portal.checkTeleport(player1);
				portal.checkTeleport(player2);
			}
		}
		
		if (bouncePads != null) {
		    for (BouncePad pad : bouncePads) {
		        pad.checkBounce(player1);
		        pad.checkBounce(player2);
		    }
		}
	}

	@Override
	public void run() {
		while (gameThread != null) {
			updateGame();
			repaint();

			if (!player1.alive || !player2.alive) {
				repaint();
				try { Thread.sleep(1500); } catch (Exception e) {}
				if (main != null) main.showMainMenu();
				gameThread = null; 
				return;
			}

			if (door != null && door.bothInside(player1, player2)) {
				repaint();
				try { Thread.sleep(1500); } catch (Exception e) {}
				if (main != null) main.showMainMenu();
				gameThread = null; 
				return;
			}

			try { Thread.sleep(16); } catch (Exception e) {}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// player 1 controls (arrow keys)
		if (key == KeyEvent.VK_UP)
			p1Up = true;
		if (key == KeyEvent.VK_DOWN)
			p1Down = true;
		if (key == KeyEvent.VK_LEFT)
			p1Left = true;
		if (key == KeyEvent.VK_RIGHT)
			p1Right = true;

		// player 2 controls (W, A, S, D)
		if (key == KeyEvent.VK_W)
			p2Up = true;
		if (key == KeyEvent.VK_S)
			p2Down = true;
		if (key == KeyEvent.VK_A)
			p2Left = true;
		if (key == KeyEvent.VK_D)
			p2Right = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// player 1
		if (key == KeyEvent.VK_UP)
			p1Up = false;
		if (key == KeyEvent.VK_DOWN)
			p1Down = false;
		if (key == KeyEvent.VK_LEFT)
			p1Left = false;
		if (key == KeyEvent.VK_RIGHT)
			p1Right = false;

		// player 2
		if (key == KeyEvent.VK_W)
			p2Up = false;
		if (key == KeyEvent.VK_S)
			p2Down = false;
		if (key == KeyEvent.VK_A)
			p2Left = false;
		if (key == KeyEvent.VK_D)
			p2Right = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	// --- GRAPHICS METHODS ---

	public void drawFireboy(Graphics g) {
		if (!player1.alive) {
			g.setColor(new Color(90, 90, 90));
			g.fillRoundRect(player1.x + 6, player1.y + 16, 28, 24, 6, 6);
			g.fillOval(player1.x + 8, player1.y + 2, 24, 22);
			g.setColor(new Color(60, 60, 60));
			g.drawLine(player1.x + 11, player1.y + 7, player1.x + 19, player1.y + 15);
			g.drawLine(player1.x + 19, player1.y + 7, player1.x + 11, player1.y + 15);
			g.drawLine(player1.x + 21, player1.y + 7, player1.x + 29, player1.y + 15);
			g.drawLine(player1.x + 29, player1.y + 7, player1.x + 21, player1.y + 15);
			return;
		}
		int fx = player1.x; int fy = player1.y;
		g.setColor(new Color(200, 50, 10)); g.fillRoundRect(fx + 6, fy + 16, 28, 24, 6, 6);
		g.setColor(new Color(220, 80, 20)); g.fillOval(fx + 8, fy + 2, 24, 22);
		g.setColor(Color.WHITE); g.fillOval(fx + 11, fy + 7, 8, 8); g.fillOval(fx + 21, fy + 7, 8, 8);
		g.setColor(new Color(60, 20, 0)); g.fillOval(fx + 13, fy + 9, 4, 4); g.fillOval(fx + 23, fy + 9, 4, 4);
		g.setColor(new Color(255, 140, 0)); g.fillPolygon(new int[] { fx + 17, fx + 20, fx + 23 }, new int[] { fy + 2, fy - 7, fy + 2 }, 3);
		g.setColor(new Color(255, 220, 50)); g.fillPolygon(new int[] { fx + 18, fx + 20, fx + 22 }, new int[] { fy + 2, fy - 4, fy + 2 }, 3);
		g.setColor(new Color(160, 40, 5)); g.fillRoundRect(fx + 8, fy + 38, 10, 8, 3, 3); g.fillRoundRect(fx + 22, fy + 38, 10, 8, 3, 3);
	}

	public void drawWatergirl(Graphics g) {
		if (!player2.alive) {
			g.setColor(new Color(90, 90, 90));
			g.fillRoundRect(player2.x + 6, player2.y + 16, 28, 24, 6, 6);
			g.fillOval(player2.x + 8, player2.y + 2, 24, 22);
			g.setColor(new Color(60, 60, 60));
			g.drawLine(player2.x + 11, player2.y + 7, player2.x + 19, player2.y + 15);
			g.drawLine(player2.x + 19, player2.y + 7, player2.x + 11, player2.y + 15);
			g.drawLine(player2.x + 21, player2.y + 7, player2.x + 29, player2.y + 15);
			g.drawLine(player2.x + 29, player2.y + 7, player2.x + 21, player2.y + 15);
			return;
		}
		int wx = player2.x; int wy = player2.y;
		g.setColor(new Color(20, 80, 200)); g.fillRoundRect(wx + 6, wy + 16, 28, 24, 6, 6);
		g.setColor(new Color(40, 120, 230)); g.fillOval(wx + 8, wy + 2, 24, 22);
		g.setColor(Color.WHITE); g.fillOval(wx + 11, wy + 7, 8, 8); g.fillOval(wx + 21, wy + 7, 8, 8);
		g.setColor(new Color(0, 20, 80)); g.fillOval(wx + 13, wy + 9, 4, 4); g.fillOval(wx + 23, wy + 9, 4, 4);
		g.setColor(new Color(100, 180, 255)); g.fillOval(wx + 17, wy - 7, 8, 8);
		g.fillPolygon(new int[] { wx + 17, wx + 21, wx + 25 }, new int[] { wy, wy - 6, wy }, 3);
		g.setColor(new Color(15, 60, 160)); g.fillRoundRect(wx + 8, wy + 38, 10, 8, 3, 3); g.fillRoundRect(wx + 22, wy + 38, 10, 8, 3, 3);
	}

	public void drawHazards(Graphics g) {
		if (firePool != null) {
			int fX = firePool.area.x; int fY = firePool.area.y; int w = firePool.area.width; int h = firePool.area.height;
			g.setColor(new Color(180, 40, 10)); g.fillRoundRect(fX, fY + 25, w, h, 4, 4);
			g.setColor(new Color(230, 90, 10, 200)); g.fillRoundRect(fX + 4, fY + 15, w - 8, h, 4, 4);
			g.setColor(new Color(255, 150, 0));
			int[] fx = { fX + 10, fX + 20, fX + 30, fX + 40, fX + 55, fX + 70 };
			for (int i = 0; i < fx.length - 1; i += 2) g.fillPolygon(new int[] { fx[i], fx[i] + 10, fx[i + 1] }, new int[] { fY + 15, fY - 3, fY + 15 }, 3);
		}
		if (waterPool != null) {
			int wX = waterPool.area.x; int wY = waterPool.area.y; int w = waterPool.area.width; int h = waterPool.area.height;
			g.setColor(new Color(10, 40, 140)); g.fillRoundRect(wX, wY + 25, w, h, 4, 4);
			g.setColor(new Color(30, 100, 210, 210)); g.fillRoundRect(wX + 2, wY + 15, w - 4, h, 4, 4);
			g.setColor(new Color(100, 180, 255, 180)); g.drawArc(wX + 4, wY + 11, 30, 14, 0, 180); g.drawArc(wX + 38, wY + 11, 30, 14, 0, 180);
		}
		if (greenPool != null) {
			int gX = greenPool.area.x; int gY = greenPool.area.y; int w = greenPool.area.width; int h = greenPool.area.height;
			g.setColor(new Color(0, 100, 5)); g.fillRoundRect(gX, gY + 25, w, h, 4, 4);
			g.setColor(new Color(0, 100, 5, 210)); g.fillRoundRect(gX + 2, gY + 15, w - 4, h, 4, 4);
			g.setColor(new Color(100, 180, 255, 180)); g.drawArc(gX + 4, gY + 11, 30, 14, 0, 180); g.drawArc(gX + 38, gY + 11, 30, 14, 0, 180);
		}
	}

	public void drawDoor(Graphics g) {
		if (door == null) return;
		Rectangle area = door.getArea();  
		g.setColor(new Color(70, 55, 35)); g.fillRoundRect(area.x - 4, area.y - 4, area.width + 8, area.height + 4, 6, 6);
		g.setColor(new Color(120, 80, 30)); g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(new Color(100, 65, 22)); g.fillRect(area.x + 6, area.y + 5, 20, area.height - 10); g.fillRect(area.x + 34, area.y + 5, 20, area.height - 10);
		g.setColor(new Color(220, 180, 60)); g.fillOval(area.x + area.width / 2 - 4, area.y + area.height / 2 - 4, 9, 9);
		g.setColor(new Color(255, 240, 100, 60)); g.fillOval(area.x + 5, area.y - 10, area.width - 10, 18);
		g.setColor(new Color(50, 35, 15)); g.drawRoundRect(area.x - 4, area.y - 4, area.width + 8, area.height + 4, 6, 6);
	}
	
	public void drawMessages(Graphics g) {
		if (door != null && door.bothInside(player1, player2)) {
			g.setColor(new Color(0, 0, 0, 140)); g.fillRoundRect(260, 220, 280, 60, 12, 12);
			g.setColor(new Color(80, 240, 120)); g.setFont(new Font("Arial", Font.BOLD, 32)); g.drawString("YOU WIN!", 320, 262);
		}
		if (!player1.alive || !player2.alive) {
			g.setColor(new Color(0, 0, 0, 140)); g.fillRoundRect(250, 220, 300, 60, 12, 12);
			g.setColor(new Color(240, 60, 60)); g.setFont(new Font("Arial", Font.BOLD, 32)); g.drawString("GAME OVER", 290, 262);
		}
	}

	public void drawControlsInfo(Graphics g) {
		g.setColor(new Color(15, 15, 30)); g.fillRect(0, ScreenHeight - UI_Height, ScreenWidth, UI_Height);
		g.setColor(new Color(230, 80, 20)); g.setFont(new Font("Arial", Font.BOLD, 13)); g.drawString("FIREBOY", 30, 740);
		g.setColor(new Color(200, 160, 140)); g.setFont(new Font("Arial", Font.PLAIN, 12)); g.drawString("\u2190 / \u2192  move   \u2191  jump", 30, 760);
		g.setColor(new Color(60, 140, 230)); g.setFont(new Font("Arial", Font.BOLD, 13)); g.drawString("WATERGIRL", 414, 740);
		g.setColor(new Color(140, 170, 210)); g.setFont(new Font("Arial", Font.PLAIN, 12)); g.drawString("A / D  move   W  jump", 414, 760);
	}

	public void drawBackground(Graphics g) {
		g.setColor(new Color(28, 28, 52)); g.fillRect(0, 0, ScreenWidth, ScreenHeight - UI_Height);
		g.setColor(new Color(38, 38, 65));
		for (int row = 0; row < 510; row += 28) {
			int offset = (row / 28 % 2 == 0) ? 0 : 40;
			for (int col = -40 + offset; col < ScreenWidth; col += 80) { g.drawRoundRect(col + 2, row + 2, 76, 24, 3, 3); }
		}
		g.setColor(new Color(55, 42, 25)); g.fillRect(0, ScreenHeight - UI_Height, ScreenWidth, 4);
	}

	public void floorColor(Graphics g) {
	    Color mudColor = new Color(139, 90, 43);
	    Color mudShadow = new Color(94, 64, 32);
	    for (Floor floor : floors) {
	        g.setColor(mudColor); g.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
	        g.setColor(mudShadow); g.fillRect(floor.area.x, floor.area.y + floor.area.height - 3, floor.area.width, 3);
	    }
	    if(!pressBottom) {
	        for (Floor floor : openWall) {
	            g.setColor(mudColor); g.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
	            g.setColor(mudShadow); g.fillRect(floor.area.x, floor.area.y + floor.area.height - 3, floor.area.width, 3);
	        }
	    }
	    Color iceColor = new Color(245, 245, 220);
	    Color iceShadow = new Color(180, 180, 160);
	    for (Floor floor : iceFloor) {
	        g.setColor(iceColor); g.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
	        g.setColor(iceShadow); g.fillRect(floor.area.x, floor.area.y + floor.area.height - 3, floor.area.width, 3);
	    }
	}

	public void drawCoins(Graphics g) {
		for (Coin coin : coins) {
			if (!coin.isCollected && coinImage != null) {
				g.drawImage(coinImage, coin.area.x, coin.area.y, coin.area.width, coin.area.height, null);
			}
		}
	}

	public void bottomColor(Graphics g) {
		if (bottom == null) return;
		Rectangle area = bottom.getArea();  
		if (!pressBottom) { g.setColor(new Color(255, 0, 0)); g.fillRect(area.x, area.y, area.width, area.height); }
		if (bottom.press(player1) || bottom.press(player2)) {
			g.setColor(new Color(0, 255, 0)); g.fillRect(area.x, area.y, area.width, area.height); pressBottom = true;
		}
		if (pressBottom) { g.setColor(new Color(0, 255, 0)); g.fillRect(area.x, area.y, area.width, area.height); }
	}
	
	public void drawPortal(Graphics g) {
		if (portal == null)return;
		for (Portal p : portal) {
			g.setColor(new Color(200, 50, 50, 150));
	        g.fillOval(p.area.x, p.area.y, p.area.width, p.area.height);
	        
	        g.setColor(new Color(50, 200, 255, 150));
	        g.fillOval(p.X, p.Y, p.area.width, p.area.height);
		}
	}
	
	public void drawBouncePads(Graphics g) {
	    if (bouncePads == null) return;
	    for (BouncePad pad : bouncePads) {
	        g.setColor(new Color(50, 220, 50)); 
	        g.fillRoundRect(pad.area.x, pad.area.y, pad.area.width, pad.area.height, 8, 8);
	    }
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (main != null) g2.scale(main.scale, main.scale);

		drawBackground(g2);
		drawHazards(g2);
		drawDoor(g2);
		drawFireboy(g2);
		drawWatergirl(g2);
		drawMessages(g2);
		drawControlsInfo(g2);
		floorColor(g2);
		drawPortal(g2);
		drawBouncePads(g2);
		
		g.setColor(Color.WHITE); g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Time: " + gameTimer.getSeconds() + "s", 350, 30);

		drawCoins(g2);
		bottomColor(g2);
	}
}