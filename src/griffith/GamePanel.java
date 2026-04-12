package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JButton;

public class GamePanel extends JPanel implements KeyListener, Runnable {

	final int TileSize = 16 * 3;
	final int ScreenWidth = TileSize * 16;
	final int ScreenHeight = TileSize * 16;
	final int UI_Height = 50;

	Thread gameThread;
	Player player1;
	Player player2;
	Hazard firePool;
	Hazard waterPool;
	Hazard greenPool;
	Door door;
	Timer gameTimer = new Timer(); // tracks level time (Susan Ogozi) 
	ArrayList<Floor> floors;

	private Main main;

	// player 1
	boolean p1Up;
	boolean p1Down;
	boolean p1Left;
	boolean p1Right;
	// player 2
	boolean p2Up;
	boolean p2Down;
	boolean p2Left;
	boolean p2Right;

	// constructor for testing
	public GamePanel() {
		this(null);
	}

	public GamePanel(Main main) {
		this.main = main;

		this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
		this.setDoubleBuffered(true);

		// dark background
		setBackground(new Color(18, 18, 38));

		// Create players
		player1 = new Player(50, 670, Type.FIRE);
		player2 = new Player(130, 670, Type.WATER);

		setFocusable(true);
		addKeyListener(this);

		// Create hazards
		firePool = new Hazard(new Rectangle(210, 695, 80, 5), Type.FIRE);
		waterPool = new Hazard(new Rectangle(430, 695, 80, 5), Type.WATER);
		greenPool = new Hazard(new Rectangle(550, 365, 80, 5), Type.GREEN);

		// Create door
		door = new Door(new Rectangle(10, 40, 60, 80));

		// Create floors
		floors = new ArrayList<>();
		// ground floor
		floors.add(new Floor(new Rectangle(0, 715, 768, 10)));
		floors.add(new Floor(new Rectangle(685, 630, 10, 95)));
		floors.add(new Floor(new Rectangle(685, 630, 83, 10)));
		// 1st floor
		floors.add(new Floor(new Rectangle(0, 540, 640, 10)));
		floors.add(new Floor(new Rectangle(83, 455, 10, 95)));
		floors.add(new Floor(new Rectangle(0, 455, 83, 10)));

		// 2nd floor
		floors.add(new Floor(new Rectangle(123, 385, 645, 10)));
		floors.add(new Floor(new Rectangle(350, 300, 10, 95)));
		floors.add(new Floor(new Rectangle(350, 300, 83, 10)));
		floors.add(new Floor(new Rectangle(433, 300, 10, 95)));

		// 3rd floor
		floors.add(new Floor(new Rectangle(123, 210, 187, 10)));
		floors.add(new Floor(new Rectangle(123, 120, 10, 90)));
		floors.add(new Floor(new Rectangle(300, 120, 10, 90)));
		floors.add(new Floor(new Rectangle(475, 210, 187, 10)));

		// 4th floor
		floors.add(new Floor(new Rectangle(0, 120, 475, 10)));

		// ADD MENU BUTTON - only add if we have a main reference (not in test mode)
		if (main != null) {
			JButton menuButton = new JButton("MENU");
			menuButton.setBounds(688, 10, 80, 30);
			menuButton.setFont(new Font("Arial", Font.BOLD, 14));
			menuButton.setBackground(new Color(200, 200, 200));
			menuButton.setForeground(Color.BLACK);
			menuButton.setFocusPainted(false);
			menuButton.addActionListener(e -> main.showMenu(false));
			this.setLayout(null);
			this.add(menuButton);
		}
	}

	public void startGame() {
		gameTimer.start(); // start tracking time Susan Ogozi 
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		while (gameThread != null) {

			// game logic
			for (Floor floor : floors) {
				floor.stopFallThrough(player1);
				floor.stopFallThrough(player2);
			}

			if (p1Up)
				player1.moveUp();
			if (p1Left)
				player1.moveLeft();
			if (p1Right)
				player1.moveRight();

			if (p2Up)
				player2.moveUp();
			if (p2Left)
				player2.moveLeft();
			if (p2Right)
				player2.moveRight();

			player1.gravity();
			player2.gravity();

			firePool.check(player1);
			firePool.check(player2);
			waterPool.check(player1);
			waterPool.check(player2);
			greenPool.check(player1);
			greenPool.check(player2);

			repaint();

			// Lose condition
			// show menu after a short delay
			if (!player1.alive || !player2.alive) {
				repaint();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (main != null) {
					main.showMenu(true);
				}
				gameThread = null; // stop the loop
				return;
			}

			try {
				Thread.sleep(16); // ~60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
	public void keyTyped(KeyEvent e) {
		// empty 
	}

	// AFAQ AHMED - GRAPHICS METHODS

	public int getFireboyX() {
		return player1.x;
	}

	public int getFireboyY() {
		return player1.y;
	}

	public int getWatergirlX() {
		return player2.x;
	}

	public int getWatergirlY() {
		return player2.y;
	}

	public boolean isFireboyAlive() {
		return player1.alive;
	}

	public boolean isWatergirlAlive() {
		return player2.alive;
	}

	public java.awt.Color getFireboyColor() {
		return new java.awt.Color(200, 50, 10);
	}

	public java.awt.Color getWatergirlColor() {
		return new java.awt.Color(20, 80, 200);
	}

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

		int fx = player1.x;
		int fy = player1.y;

		g.setColor(new Color(200, 50, 10));
		g.fillRoundRect(fx + 6, fy + 16, 28, 24, 6, 6);
		g.setColor(new Color(220, 80, 20));
		g.fillOval(fx + 8, fy + 2, 24, 22);
		g.setColor(Color.WHITE);
		g.fillOval(fx + 11, fy + 7, 8, 8);
		g.fillOval(fx + 21, fy + 7, 8, 8);
		g.setColor(new Color(60, 20, 0));
		g.fillOval(fx + 13, fy + 9, 4, 4);
		g.fillOval(fx + 23, fy + 9, 4, 4);
		g.setColor(new Color(255, 140, 0));
		g.fillPolygon(new int[] { fx + 17, fx + 20, fx + 23 }, new int[] { fy + 2, fy - 7, fy + 2 }, 3);
		g.setColor(new Color(255, 220, 50));
		g.fillPolygon(new int[] { fx + 18, fx + 20, fx + 22 }, new int[] { fy + 2, fy - 4, fy + 2 }, 3);
		g.setColor(new Color(160, 40, 5));
		g.fillRoundRect(fx + 8, fy + 38, 10, 8, 3, 3);
		g.fillRoundRect(fx + 22, fy + 38, 10, 8, 3, 3);
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

		int wx = player2.x;
		int wy = player2.y;

		g.setColor(new Color(20, 80, 200));
		g.fillRoundRect(wx + 6, wy + 16, 28, 24, 6, 6);
		g.setColor(new Color(40, 120, 230));
		g.fillOval(wx + 8, wy + 2, 24, 22);
		g.setColor(Color.WHITE);
		g.fillOval(wx + 11, wy + 7, 8, 8);
		g.fillOval(wx + 21, wy + 7, 8, 8);
		g.setColor(new Color(0, 20, 80));
		g.fillOval(wx + 13, wy + 9, 4, 4);
		g.fillOval(wx + 23, wy + 9, 4, 4);
		g.setColor(new Color(100, 180, 255));
		g.fillOval(wx + 17, wy - 7, 8, 8);
		g.fillPolygon(new int[] { wx + 17, wx + 21, wx + 25 }, new int[] { wy, wy - 6, wy }, 3);
		g.setColor(new Color(15, 60, 160));
		g.fillRoundRect(wx + 8, wy + 38, 10, 8, 3, 3);
		g.fillRoundRect(wx + 22, wy + 38, 10, 8, 3, 3);
	}

	public void drawHazards(Graphics g) {
		if (firePool != null) {
			int fireX = firePool.area.x;
			int fireY = firePool.area.y;
			int w = firePool.area.width;
			int h = firePool.area.height;
			g.setColor(new Color(180, 40, 10));
			g.fillRoundRect(fireX, fireY + 25, w, h, 4, 4);
			g.setColor(new Color(230, 90, 10, 200));
			g.fillRoundRect(fireX + 4, fireY + 15, w - 8, h, 4, 4);
			g.setColor(new Color(255, 150, 0));
			int[] fx = { fireX + 10, fireX + 20, fireX + 30, fireX + 40, fireX + 55, fireX + 70 };
			for (int i = 0; i < fx.length - 1; i += 2) {
				g.fillPolygon(new int[] { fx[i], fx[i] + 10, fx[i + 1] },
						new int[] { fireY + 15, fireY - 3, fireY + 15 }, 3);
			}
		}

		if (waterPool != null) {
			int waterX = waterPool.area.x;
			int waterY = waterPool.area.y;
			int w = waterPool.area.width;
			int h = waterPool.area.height;
			g.setColor(new Color(10, 40, 140));
			g.fillRoundRect(waterX, waterY + 25, w, h, 4, 4);
			g.setColor(new Color(30, 100, 210, 210));
			g.fillRoundRect(waterX + 2, waterY + 15, w - 4, h, 4, 4);
			g.setColor(new Color(100, 180, 255, 180));
			g.drawArc(waterX + 4, waterY + 11, 30, 14, 0, 180);
			g.drawArc(waterX + 38, waterY + 11, 30, 14, 0, 180);
		}
		
		if (greenPool != null) {
			int greenX = greenPool.area.x;
			int greenY = greenPool.area.y;
			int w = greenPool.area.width;
			int h = greenPool.area.height;
			g.setColor(new Color(0, 100, 5));
			g.fillRoundRect(greenX, greenY + 25, w, h, 4, 4);
			g.setColor(new Color(0, 100, 5, 210));
			g.fillRoundRect(greenX + 2, greenY + 15, w - 4, h, 4, 4);
			g.setColor(new Color(100, 180, 255, 180));
			g.drawArc(greenX + 4, greenY + 11, 30, 14, 0, 180);
			g.drawArc(greenX + 38, greenY + 11, 30, 14, 0, 180);
		}
	}

	public void drawDoor(Graphics g) {
		if (door == null)
			return;
		g.setColor(new Color(70, 55, 35));
		g.fillRoundRect(door.area.x - 4, door.area.y - 4, door.area.width + 8, door.area.height + 4, 6, 6);
		g.setColor(new Color(120, 80, 30));
		g.fillRect(door.area.x, door.area.y, door.area.width, door.area.height);
		g.setColor(new Color(100, 65, 22));
		g.fillRect(door.area.x + 6, door.area.y + 5, 20, door.area.height - 10);
		g.fillRect(door.area.x + 34, door.area.y + 5, 20, door.area.height - 10);
		g.setColor(new Color(220, 180, 60));
		g.fillOval(door.area.x + door.area.width / 2 - 4, door.area.y + door.area.height / 2 - 4, 9, 9);
		g.setColor(new Color(255, 240, 100, 60));
		g.fillOval(door.area.x + 5, door.area.y - 10, door.area.width - 10, 18);
		g.setColor(new Color(50, 35, 15));
		g.drawRoundRect(door.area.x - 4, door.area.y - 4, door.area.width + 8, door.area.height + 4, 6, 6);
	}

	public void drawMessages(Graphics g) {
		if (door.isInside(player1) && door.isInside(player2)) {
			g.setColor(new Color(0, 0, 0, 140));
			g.fillRoundRect(260, 220, 280, 60, 12, 12);
			g.setColor(new Color(80, 240, 120));
			g.setFont(new Font("Arial", Font.BOLD, 32));
			g.drawString("YOU WIN!", 320, 262);
		}

		if (!player1.alive || !player2.alive) {
			g.setColor(new Color(0, 0, 0, 140));
			g.fillRoundRect(250, 220, 300, 60, 12, 12);
			g.setColor(new Color(240, 60, 60));
			g.setFont(new Font("Arial", Font.BOLD, 32));
			g.drawString("GAME OVER", 290, 262);
		}
	}

	public void drawControlsInfo(Graphics g) {
		g.setColor(new Color(15, 15, 30));
		g.fillRect(0, ScreenHeight - UI_Height, ScreenWidth, UI_Height);
		g.setColor(new Color(230, 80, 20));
		g.setFont(new Font("Arial", Font.BOLD, 13));
		g.drawString("FIREBOY", 30, 740);
		g.setColor(new Color(200, 160, 140));
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("\u2190 / \u2192  move   \u2191  jump", 30, 760);
		g.setColor(new Color(60, 140, 230));
		g.setFont(new Font("Arial", Font.BOLD, 13));
		g.drawString("WATERGIRL", 414, 740);
		g.setColor(new Color(140, 170, 210));
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("A / D  move   W  jump", 414, 760);
	}

	public void drawBackground(Graphics g) {
		g.setColor(new Color(28, 28, 52));
		g.fillRect(0, 0, ScreenWidth, ScreenHeight - UI_Height);
		g.setColor(new Color(38, 38, 65));
		for (int row = 0; row < 510; row += 28) {
			int offset = (row / 28 % 2 == 0) ? 0 : 40;
			for (int col = -40 + offset; col < 800; col += 80) {
				g.drawRoundRect(col + 2, row + 2, 76, 24, 3, 3);
			}
		}
		g.setColor(new Color(55, 42, 25));
		g.fillRect(0, ScreenHeight - UI_Height, ScreenWidth, 4);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawHazards(g);
		drawDoor(g);
		drawFireboy(g);
		drawWatergirl(g);
		drawMessages(g);
		// Display timer on screen Susan Ogozi 3157092
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Time: " + gameTimer.getSeconds() + "s", 350, 30);
		drawControlsInfo(g);
		g.setColor(new Color(100, 100, 100));
		for (Floor floor : floors) {
			g.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
		}
	}
}