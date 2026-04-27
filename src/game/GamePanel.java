package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import entities.*;
import griffith.Main;
import utils.Timer;

public class GamePanel extends JPanel implements KeyListener, Runnable {

	static final int BaseW = 768;
	static final int BaseH = 768;
	final int UI_Height = 50;
	final int TileSize = 16 * 3;

	Thread gameThread;
	public Player player1;
	public Player player2;

	// Game Items
	Hazard firePool, waterPool, greenPool;
	Door door;
	Bottom bottom;
	ArrayList<Floor> floors;
	ArrayList<Floor> iceFloor;
	ArrayList<Floor> openWall;
	ArrayList<Coin> coins;
	ArrayList<Portal> portal;
	ArrayList<BouncePad> bouncePad;

	Timer gameTimer = new Timer();
	private Main main;
	private String difficulty;
	public Image coinImage;

	// Controls
	boolean p1Up, p1Down, p1Left, p1Right;
	boolean p2Up, p2Down, p2Left, p2Right;

	private final Rectangle menuBtnBase = new Rectangle(678, 8, 80, 30);
	private boolean menuBtnHover = false;

	private double scale;
	private int offsetX;
	private int offsetY;

	public boolean pressBottom = false;

	public GamePanel() {
		this(null, "easy");
	}

	public GamePanel(Main main, String difficulty) {
		this.main = main;
		this.difficulty = difficulty;

		this.setPreferredSize(new Dimension(BaseW, BaseH));
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

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (main == null)
					return;

				Point p = toGameCoords(e);

				boolean was = menuBtnHover;
				menuBtnHover = menuBtnBase.contains(p);
				if (menuBtnHover != was)
					repaint();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (main == null)
					return;

				Point p = toGameCoords(e);

				if (menuBtnBase.contains(p)) {
					main.showMainMenu();
				}
			}
		});

		// here it will load the level
		Level currentLevel;

		if ("muad".equals(difficulty)) {
			currentLevel = new MuadLevel();
		} else {
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
		this.bouncePad = currentLevel.bouncePad;
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
		if (!pressBottom) {
			for (Floor floor : openWall) {
				floor.stopFallThrough(player1);
				floor.stopFallThrough(player2);
			}
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

		if (firePool != null) {
			firePool.check(player1);
			firePool.check(player2);
		}
		if (waterPool != null) {
			waterPool.check(player1);
			waterPool.check(player2);
		}
		if (greenPool != null) {
			greenPool.check(player1);
			greenPool.check(player2);
		}

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

		if (bouncePad != null) {
			for (BouncePad pad : bouncePad) {
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
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
				}
				if (main != null)
					main.showMainMenu();
				gameThread = null;
				return;
			}

			if (door != null && door.bothInside(player1, player2)) {
				repaint();
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
				}
				if (main != null)
					main.showMainMenu();
				gameThread = null;
				return;
			}

			try {
				Thread.sleep(16);
			} catch (Exception e) {
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
	}

	// this changes the clickable area to the new position of the menu
	private Point toGameCoords(MouseEvent e) {
	    int x = (int) ((e.getX() - offsetX) / scale);
	    int y = (int) ((e.getY() - offsetY) / scale);
	    return new Point(x, y);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		Graphics2D g2 = (Graphics2D) g.create();

		if (main != null) {
			scale = Math.min(getWidth() / (double) BaseW, getHeight() / (double) BaseH);

			int physicalWidth = (int) (BaseW * scale);
			int physicalHeight = (int) (BaseH * scale);

			offsetX = (getWidth() - physicalWidth) / 2;
			offsetY = (getHeight() - physicalHeight) / 2;

			g2.translate(offsetX, offsetY);
			g2.scale(scale, scale);
		}

		// Clip to base area so nothing draws outside the square
		g2.setClip(0, 0, BaseW, BaseH);

		drawBackground(g2);
		drawHazards(g2);
		drawDoor(g2);
		floorColor(g2);
		drawPortal(g2);
		drawBouncePads(g2);
		drawCoins(g2);
		bottomColor(g2);
		drawFireboy(g2);
		drawWatergirl(g2);
		drawControlsInfo(g2);
		drawMessages(g2);
		drawMenuButton(g2);

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 18));
		FontMetrics fm = g2.getFontMetrics();
		String timeStr = "Time: " + gameTimer.getSeconds() + "s";
		g2.drawString(timeStr, (BaseW - fm.stringWidth(timeStr)) / 2, 28);

		g2.dispose();
	}

	// --- GRAPHICS METHODS ---

	public void drawMenuButton(Graphics2D g2) {
		int x = menuBtnBase.x, y = menuBtnBase.y;
		int w = menuBtnBase.width, h = menuBtnBase.height;

		g2.setColor(menuBtnHover ? new Color(220, 220, 220) : new Color(160, 160, 160));
		g2.fillRoundRect(x, y, w, h, 8, 8);
		g2.setColor(new Color(60, 60, 60));
		g2.drawRoundRect(x, y, w - 1, h - 1, 8, 8);
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Arial", Font.BOLD, 14));
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString("MENU", x + (w - fm.stringWidth("MENU")) / 2, y + (h + fm.getAscent() - fm.getDescent()) / 2);
	}

	public void drawBackground(Graphics2D g2) {
		g2.setColor(new Color(28, 28, 52));
		g2.fillRect(0, 0, BaseW, BaseH - UI_Height);
		g2.setColor(new Color(38, 38, 65));
		for (int row = 0; row < 510; row += 28) {
			int offset = (row / 28 % 2 == 0) ? 0 : 40;
			for (int col = -40 + offset; col < BaseW; col += 80) {
				g2.drawRoundRect(col + 2, row + 2, 76, 24, 3, 3);
			}
		}
		g2.setColor(new Color(55, 42, 25));
		g2.fillRect(0, BaseH - UI_Height, BaseW, 4);
	}

	public void drawFireboy(Graphics2D g2) {
		if (!player1.alive) {
			g2.setColor(new Color(90, 90, 90));
			g2.fillRoundRect(player1.x + 6, player1.y + 16, 28, 24, 6, 6);
			g2.fillOval(player1.x + 8, player1.y + 2, 24, 22);
			g2.setColor(new Color(60, 60, 60));
			g2.drawLine(player1.x + 11, player1.y + 7, player1.x + 19, player1.y + 15);
			g2.drawLine(player1.x + 19, player1.y + 7, player1.x + 11, player1.y + 15);
			g2.drawLine(player1.x + 21, player1.y + 7, player1.x + 29, player1.y + 15);
			g2.drawLine(player1.x + 29, player1.y + 7, player1.x + 21, player1.y + 15);
			return;
		}
		int fx = player1.x, fy = player1.y;
		g2.setColor(new Color(200, 50, 10));
		g2.fillRoundRect(fx + 6, fy + 16, 28, 24, 6, 6);
		g2.setColor(new Color(220, 80, 20));
		g2.fillOval(fx + 8, fy + 2, 24, 22);
		g2.setColor(Color.WHITE);
		g2.fillOval(fx + 11, fy + 7, 8, 8);
		g2.fillOval(fx + 21, fy + 7, 8, 8);
		g2.setColor(new Color(60, 20, 0));
		g2.fillOval(fx + 13, fy + 9, 4, 4);
		g2.fillOval(fx + 23, fy + 9, 4, 4);
		g2.setColor(new Color(255, 140, 0));
		g2.fillPolygon(new int[] { fx + 17, fx + 20, fx + 23 }, new int[] { fy + 2, fy - 7, fy + 2 }, 3);
		g2.setColor(new Color(255, 220, 50));
		g2.fillPolygon(new int[] { fx + 18, fx + 20, fx + 22 }, new int[] { fy + 2, fy - 4, fy + 2 }, 3);
		g2.setColor(new Color(160, 40, 5));
		g2.fillRoundRect(fx + 8, fy + 38, 10, 8, 3, 3);
		g2.fillRoundRect(fx + 22, fy + 38, 10, 8, 3, 3);
	}

	public void drawWatergirl(Graphics2D g2) {
		if (!player2.alive) {
			g2.setColor(new Color(90, 90, 90));
			g2.fillRoundRect(player2.x + 6, player2.y + 16, 28, 24, 6, 6);
			g2.fillOval(player2.x + 8, player2.y + 2, 24, 22);
			g2.setColor(new Color(60, 60, 60));
			g2.drawLine(player2.x + 11, player2.y + 7, player2.x + 19, player2.y + 15);
			g2.drawLine(player2.x + 19, player2.y + 7, player2.x + 11, player2.y + 15);
			g2.drawLine(player2.x + 21, player2.y + 7, player2.x + 29, player2.y + 15);
			g2.drawLine(player2.x + 29, player2.y + 7, player2.x + 21, player2.y + 15);
			return;
		}
		int wx = player2.x, wy = player2.y;
		g2.setColor(new Color(20, 80, 200));
		g2.fillRoundRect(wx + 6, wy + 16, 28, 24, 6, 6);
		g2.setColor(new Color(40, 120, 230));
		g2.fillOval(wx + 8, wy + 2, 24, 22);
		g2.setColor(Color.WHITE);
		g2.fillOval(wx + 11, wy + 7, 8, 8);
		g2.fillOval(wx + 21, wy + 7, 8, 8);
		g2.setColor(new Color(0, 20, 80));
		g2.fillOval(wx + 13, wy + 9, 4, 4);
		g2.fillOval(wx + 23, wy + 9, 4, 4);
		g2.setColor(new Color(100, 180, 255));
		g2.fillOval(wx + 17, wy - 7, 8, 8);
		g2.fillPolygon(new int[] { wx + 17, wx + 21, wx + 25 }, new int[] { wy, wy - 6, wy }, 3);
		g2.setColor(new Color(15, 60, 160));
		g2.fillRoundRect(wx + 8, wy + 38, 10, 8, 3, 3);
		g2.fillRoundRect(wx + 22, wy + 38, 10, 8, 3, 3);
	}

	public void drawHazards(Graphics2D g2) {
		if (firePool != null) {
			int fX = firePool.area.x, fY = firePool.area.y;
			int w = firePool.area.width, h = firePool.area.height;
			g2.setColor(new Color(180, 40, 10));
			g2.fillRoundRect(fX, fY + 25, w, h, 4, 4);
			g2.setColor(new Color(230, 90, 10, 200));
			g2.fillRoundRect(fX + 4, fY + 15, w - 8, h, 4, 4);
			g2.setColor(new Color(255, 150, 0));
			int[] fx = { fX + 10, fX + 20, fX + 30, fX + 40, fX + 55, fX + 70 };
			for (int i = 0; i < fx.length - 1; i += 2)
				g2.fillPolygon(new int[] { fx[i], fx[i] + 10, fx[i + 1] }, new int[] { fY + 15, fY - 3, fY + 15 }, 3);
		}
		if (waterPool != null) {
			int wX = waterPool.area.x, wY = waterPool.area.y;
			int w = waterPool.area.width, h = waterPool.area.height;
			g2.setColor(new Color(10, 40, 140));
			g2.fillRoundRect(wX, wY + 25, w, h, 4, 4);
			g2.setColor(new Color(30, 100, 210, 210));
			g2.fillRoundRect(wX + 2, wY + 15, w - 4, h, 4, 4);
			g2.setColor(new Color(100, 180, 255, 180));
			g2.drawArc(wX + 4, wY + 11, 30, 14, 0, 180);
			g2.drawArc(wX + 38, wY + 11, 30, 14, 0, 180);
		}
		if (greenPool != null) {
			int gX = greenPool.area.x, gY = greenPool.area.y;
			int w = greenPool.area.width, h = greenPool.area.height;
			g2.setColor(new Color(0, 100, 5));
			g2.fillRoundRect(gX, gY + 25, w, h, 4, 4);
			g2.setColor(new Color(0, 100, 5, 210));
			g2.fillRoundRect(gX + 2, gY + 15, w - 4, h, 4, 4);
			g2.setColor(new Color(100, 180, 255, 180));
			g2.drawArc(gX + 4, gY + 11, 30, 14, 0, 180);
			g2.drawArc(gX + 38, gY + 11, 30, 14, 0, 180);
		}
	}

	public void drawDoor(Graphics2D g2) {
		if (door == null)
			return;
		Rectangle area = door.getArea();
		g2.setColor(new Color(70, 55, 35));
		g2.fillRoundRect(area.x - 4, area.y - 4, area.width + 8, area.height + 4, 6, 6);
		g2.setColor(new Color(120, 80, 30));
		g2.fillRect(area.x, area.y, area.width, area.height);
		g2.setColor(new Color(100, 65, 22));
		g2.fillRect(area.x + 6, area.y + 5, 20, area.height - 10);
		g2.fillRect(area.x + 34, area.y + 5, 20, area.height - 10);
		g2.setColor(new Color(220, 180, 60));
		g2.fillOval(area.x + area.width / 2 - 4, area.y + area.height / 2 - 4, 9, 9);
		g2.setColor(new Color(255, 240, 100, 60));
		g2.fillOval(area.x + 5, area.y - 10, area.width - 10, 18);
		g2.setColor(new Color(50, 35, 15));
		g2.drawRoundRect(area.x - 4, area.y - 4, area.width + 8, area.height + 4, 6, 6);
	}

	public void floorColor(Graphics2D g2) {
		Color mudColor = new Color(139, 90, 43);
		Color mudShadow = new Color(94, 64, 32);
		for (Floor floor : floors) {
			g2.setColor(mudColor);
			g2.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
			g2.setColor(mudShadow);
			g2.fillRect(floor.area.x, floor.area.y + floor.area.height - 3, floor.area.width, 3);
		}
		if (!pressBottom) {
			for (Floor floor : openWall) {
				g2.setColor(mudColor);
				g2.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
				g2.setColor(mudShadow);
				g2.fillRect(floor.area.x, floor.area.y + floor.area.height - 3, floor.area.width, 3);
			}
		}
		Color iceColor = new Color(245, 245, 220);
		Color iceShadow = new Color(180, 180, 160);
		for (Floor floor : iceFloor) {
			g2.setColor(iceColor);
			g2.fillRect(floor.area.x, floor.area.y, floor.area.width, floor.area.height);
			g2.setColor(iceShadow);
			g2.fillRect(floor.area.x, floor.area.y + floor.area.height - 3, floor.area.width, 3);
		}
	}

	public void drawCoins(Graphics2D g2) {
		for (Coin coin : coins) {
			if (!coin.isCollected && coinImage != null) {
				g2.drawImage(coinImage, coin.area.x, coin.area.y, coin.area.width, coin.area.height, null);
			}
		}
	}

	public void bottomColor(Graphics2D g2) {
		if (bottom == null)
			return;
		Rectangle area = bottom.getArea();
		if (!pressBottom) {
			g2.setColor(new Color(255, 0, 0));
			g2.fillRect(area.x, area.y, area.width, area.height);
		}
		if (bottom.press(player1) || bottom.press(player2)) {
			pressBottom = true;
		}
		if (pressBottom) {
			g2.setColor(new Color(0, 255, 0));
			g2.fillRect(area.x, area.y, area.width, area.height);
		}
	}

	public void drawPortal(Graphics2D g2) {
		if (portal == null)
			return;
		for (Portal p : portal) {
			g2.setColor(new Color(200, 50, 50, 150));
			g2.fillOval(p.area.x, p.area.y, p.area.width, p.area.height);
			g2.setColor(new Color(50, 200, 255, 150));
			g2.fillOval(p.X, p.Y, p.area.width, p.area.height);
		}
	}

	public void drawBouncePads(Graphics2D g2) {
		if (bouncePad == null)
			return;
		for (BouncePad pad : bouncePad) {
			g2.setColor(new Color(50, 220, 50));
			g2.fillRoundRect(pad.area.x, pad.area.y, pad.area.width, pad.area.height, 8, 8);
		}
	}

	public void drawControlsInfo(Graphics2D g2) {
		g2.setColor(new Color(15, 15, 30));
		g2.fillRect(0, BaseH - UI_Height, BaseW, UI_Height);
		g2.setColor(new Color(230, 80, 20));
		g2.setFont(new Font("Arial", Font.BOLD, 13));
		g2.drawString("FIREBOY", 30, BaseH - 28);
		g2.setColor(new Color(200, 160, 140));
		g2.setFont(new Font("Arial", Font.PLAIN, 12));
		g2.drawString("\u2190 / \u2192  move   \u2191  jump", 30, BaseH - 10);
		g2.setColor(new Color(60, 140, 230));
		g2.setFont(new Font("Arial", Font.BOLD, 13));
		g2.drawString("WATERGIRL", 414, BaseH - 28);
		g2.setColor(new Color(140, 170, 210));
		g2.setFont(new Font("Arial", Font.PLAIN, 12));
		g2.drawString("A / D  move   W  jump", 414, BaseH - 10);
	}

	public void drawMessages(Graphics2D g2) {
		if (door != null && door.bothInside(player1, player2)) {
			g2.setColor(new Color(0, 0, 0, 140));
			g2.fillRoundRect(260, 220, 280, 60, 12, 12);
			g2.setColor(new Color(80, 240, 120));
			g2.setFont(new Font("Arial", Font.BOLD, 32));
			g2.drawString("YOU WIN!", 320, 262);
		}
		if (!player1.alive || !player2.alive) {
			g2.setColor(new Color(0, 0, 0, 140));
			g2.fillRoundRect(250, 220, 300, 60, 12, 12);
			g2.setColor(new Color(240, 60, 60));
			g2.setFont(new Font("Arial", Font.BOLD, 32));
			g2.drawString("GAME OVER", 290, 262);
		}
	}
}