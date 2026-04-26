package game;

//Susan Ogozi
//3157092
import java.util.HashMap;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import griffith.Main;

import javax.swing.JButton;
import java.awt.Image;
import javax.swing.ImageIcon;

public class MyLevelPanel extends JPanel implements KeyListener, Runnable {

	// Screen
	static final int W = 768;
	static final int H = 768;
	static final int UI_H = 50;

	// Game thread
	Thread gameThread;
	private Main mainFrame;

	// Keys
	boolean p1Up, p1Left, p1Right;
	boolean p2Up, p2Left, p2Right;

	// Floors and blocks
	ArrayList<Rectangle> floors = new ArrayList<>();
	ArrayList<Rectangle> walls = new ArrayList<>();

	// Hazards
	Rectangle firePool1, firePool2;
	Rectangle waterPool1, waterPool2;
	Rectangle greenPool;

	// Doors
	Rectangle fireDoor, waterDoor;

	// timer
	long startTime = 0;
	int elapsedSeconds = 0;

	// Multiple switches and blocks
	ArrayList<SwitchPair> switchPairs = new ArrayList<>();

	// Diamonds
	ArrayList<Diamond> diamonds = new ArrayList<>();

	// Images
	Image brickImg;
	Image switchImg;
	Image switchOnImg;
	Image fireDoorImg;
	Image waterDoorImg;
	Image fireGemImg;
	Image waterGemImg;
	Image fireObstacleImg;
	Image waterObstacleImg;
	Image poisonObstacleImg;
	Image fireEnterGif;
	Image waterEnterGif;
	Image gameClearImg;
	Image gameOverImg;
	Image menuBtnImg;
	Image switchDoorImg;
	Image backgroundImg;
	// Characters
	Image fireIdle, fireLeft, fireRight, fireDie;
	Image waterIdle, waterLeft, waterRight, waterDie;

	// State of players
	boolean fireFacingLeft = false;
	boolean waterFacingLeft = false;
	boolean fireMoving = false;
	boolean waterMoving = false;

	// Player physics
	int fireX = 80, fireY = 630;
	int waterX = 160, waterY = 630;

	int fireVY = 0, waterVY = 0;
	boolean fireOnGround = false;
	boolean waterOnGround = false;
	boolean fireAlive = true;
	boolean waterAlive = true;
	int fireSpeed = 6;
	int waterSpeed = 6;
	static final int GRAVITY = 1;
	static final int JUMP_FORCE = -18;
	static final int PLAYER_W = 30;
	static final int PLAYER_H = 60;

	// Game state
	enum State {
		PLAYING, ENTERING, WIN, DEAD
	}

	State gameState = State.PLAYING;
	long stateTime = 0;

	// Switch pair class
	static class SwitchPair {
		Rectangle switchButton;
		Rectangle switchBlock;
		boolean pressed = false;

		SwitchPair(Rectangle button, Rectangle block) {
			this.switchButton = button;
			this.switchBlock = block;
		}
	}

	// Diamond inner class
	static class Diamond {
		int x, y, w, h;
		int type; // 0=water(blue), 1=fire(red)
		boolean collected = false;

		Diamond(int x, int y, int type) {
			this.x = x;
			this.y = y;
			this.w = 18;
			this.h = 18;
			this.type = type;
		}

		Rectangle getBounds() {
			return new Rectangle(x, y, w, h);
		}
	}

	public MyLevelPanel(Main mainFrame) {
		this.mainFrame = mainFrame;
		setPreferredSize(new Dimension(W, H));
		setBackground(new Color(18, 18, 38));
		setLayout(null);
		setFocusable(true);
		addKeyListener(this);

		loadImages();
		buildLevel();
		add(mainFrame.createMenuButton(668, 8, 70, 25)); // menu position
	}

	public void setMainFrame(Main m) {
		this.mainFrame = m;
	}

	// Load all images
	private Image img(String path) {
		java.io.File f = new java.io.File(path);
		if (!f.exists()) {
			System.out.println("MISSING: " + path);
			return null;
		}
		return new ImageIcon(path).getImage();
	}

	private void loadImages() {
		brickImg = img("src/static/image/elements/block.png");
		switchImg = img("src/static/image/elements/switch.png");
		switchOnImg = img("src/static/image/elements/switch.png");
		switchDoorImg = img("src/static/image/elements/switch_block2.png");
		fireDoorImg = img("src/static/image/elements/fire_door.png");
		waterDoorImg = img("src/static/image/elements/water_door.png");
		fireGemImg = img("src/static/image/elements/fire_gem.png");
		waterGemImg = img("src/static/image/elements/water_gem.png");
		fireObstacleImg = img("src/static/image/elements/fire_obstacle.png");
		waterObstacleImg = img("src/static/image/elements/water_obstacle.png");
		poisonObstacleImg = img("src/static/image/elements/poison_obstacle.png");
		fireEnterGif = img("src/static/image/character/fire boy_enter room.gif");
		waterEnterGif = img("src/static/image/character/water girl_enter room.gif");
		gameClearImg = img("src/static/image/text/gameclear.gif");
		gameOverImg = img("src/static/image/text/gameover.png");
		menuBtnImg = img("src/static/image/text/MenuBtn.png");
		backgroundImg = img("src/static/image/background/game_play_background.png");

		// Characters
		fireIdle = img("src/static/image/character/fire_boy_character.png");
		fireLeft = img("src/static/image/character/fire boy_run left.gif");
		fireRight = img("src/static/image/character/fire boy_run right.gif");
		fireDie = img("src/static/image/character/die.gif");

		waterIdle = img("src/static/image/character/water_girl_character.png");
		waterLeft = img("src/static/image/character/water girl_run left.gif");
		waterRight = img("src/static/image/character/water girl_run right.gif");
		waterDie = img("src/static/image/character/die.gif");
	}

	private void buildLevel() {

		// GROUND FLOOR
		floors.add(new Rectangle(0, 700, W, 24));

		// RIGHT PILLAR
		walls.add(new Rectangle(680, 600, 24, 100));
		walls.add(new Rectangle(680, 600, 88, 24));

		// 1ST PLATFORM
		floors.add(new Rectangle(0, 530, 155, 24));

		// left closed bricks

		walls.add(new Rectangle(0, 445, 80, 24));
		walls.add(new Rectangle(0, 469, 24, 61)); // closes first gap permanent wall

		floors.add(new Rectangle(155, 530, 478, 24));

		// 2ND PLATFORM
		floors.add(new Rectangle(120, 375, 648, 24));

		// Closed middle section
		walls.add(new Rectangle(340, 282, 24, 94));
		walls.add(new Rectangle(340, 282, 88, 24));

		// 3RD PLATFORM
		floors.add(new Rectangle(120, 200, 192, 24));
		floors.add(new Rectangle(470, 200, 192, 24));

		// 4TH PLATFORM (TOP)
		floors.add(new Rectangle(0, 110, 470, 24));
		floors.add(new Rectangle(662, 110, 106, 24));

		// Closed left section (top)
		walls.add(new Rectangle(120, 110, 24, 100));

		// SWITCH PAIR 0 - Horizontal block on 1st PLATFORM
		// This block has TWO switches controlling it
		Rectangle switch0Block = new Rectangle(630, 506, 180, 20); // The horizontal block

		// Switch 0a - on GROUND FLOOR
		Rectangle switch0ButtonGround = new Rectangle(300, 686, 30, 14);
		switchPairs.add(new SwitchPair(switch0ButtonGround, switch0Block));

		// Switch 0b - on 1st PLATFORM (second switch for the SAME block)
		Rectangle switch0ButtonPlatform = new Rectangle(550, 514, 30, 16);
		switchPairs.add(new SwitchPair(switch0ButtonPlatform, switch0Block));

		// SWITCH PAIR 1 - Left on 1st platform alcove (vertical block)
		Rectangle switch1Button = new Rectangle(300, 514, 30, 16);
		Rectangle switch1Block = new Rectangle(80, 469, 10, 61);
		switchPairs.add(new SwitchPair(switch1Button, switch1Block));

		// SWITCH PAIR 2 - Middle closed brick on 2nd platform
		Rectangle switch2Button = new Rectangle(600, 359, 30, 16); // Right side of 2nd platform
		Rectangle switch2Block = new Rectangle(428, 306, 10, 66); // Bottom door of middle brick
		switchPairs.add(new SwitchPair(switch2Button, switch2Block));
		diamonds.add(new Diamond(370, 330, 0)); // Blue diamond inside middle brick

		// SWITCH PAIR 3 - Left closed brick on 4th platform
		Rectangle switch3Button = new Rectangle(550, 184, 30, 16); // Right side of 3rd platform
		Rectangle switch3Block = new Rectangle(190, 134, 10, 66); // Bottom door of top brick
		switchPairs.add(new SwitchPair(switch3Button, switch3Block));
		diamonds.add(new Diamond(150, 150, 1)); // Red diamond inside top brick

		// FIRE POOLS - merged with floors

		firePool1 = new Rectangle(200, 526, 70, 12); // 2st platform

		// WATER POOLS - merged with floors
		waterPool1 = new Rectangle(360, 694, 70, 14); // ground
		waterPool2 = new Rectangle(400, 523, 70, 30); // 1st platform

		// GREEN/POISON POOL - LEFT SIDE OF 2ND PLATFORM
		greenPool = new Rectangle(180, 370, 70, 14); // Left side of 2nd platform

		// DOORS at top
		fireDoor = new Rectangle(0, 40, 56, 80);
		waterDoor = new Rectangle(700, 40, 56, 80);

		// ADDITIONAL DIAMONDS
		// Red diamonds IN fire pools (fire boy collects)
		diamonds.add(new Diamond(240, 670, 1)); // firePool1
		diamonds.add(new Diamond(230, 500, 1)); // firePool2

		// Blue diamonds IN water pools (water girl collects)
		diamonds.add(new Diamond(390, 670, 0)); // waterPool1
		diamonds.add(new Diamond(430, 500, 0)); // waterPool2

		// Safe platform diamonds
		diamonds.add(new Diamond(60, 490, 1)); // left 1st platform - red

		diamonds.add(new Diamond(490, 160, 0)); // 3rd platform right - blue
		diamonds.add(new Diamond(200, 70, 0)); // top platform - blue
		diamonds.add(new Diamond(350, 70, 1)); // top platform - red
	}

	// Start
	public void startGame() {
		startTime = System.currentTimeMillis();
		gameThread = new Thread(this);
		gameThread.start();
	}

	// Game loop
	@Override
	public void run() {
		while (gameThread != null) {
			update();
			repaint();

			if (gameState == State.ENTERING) {
				if (System.currentTimeMillis() - stateTime > 1500) {
					gameState = State.WIN;
					stateTime = System.currentTimeMillis();
				}
			}

			if (gameState == State.WIN) {
				if (System.currentTimeMillis() - stateTime > 2500) {
					gameThread = null;
					if (mainFrame != null)
						mainFrame.showMainMenu();
					return;
				}
			}

			if (gameState == State.DEAD) {
				if (System.currentTimeMillis() - stateTime > 1500) {
					gameThread = null;
					if (mainFrame != null)
						mainFrame.showMainMenu();
					return;
				}
			}

			try {
				Thread.sleep(16);
			} catch (InterruptedException ignored) {
			}
		}
	}

//  Update 
	private void update() {
		if (gameState != State.PLAYING)
			return;

		// Fire player movement
		fireMoving = false;
		if (p1Left) {
			fireX -= fireSpeed;
			fireFacingLeft = true;
			fireMoving = true;
		}
		if (p1Right) {
			fireX += fireSpeed;
			fireFacingLeft = false;
			fireMoving = true;
		}

		// Water player movement
		waterMoving = false;
		if (p2Left) {
			waterX -= waterSpeed;
			waterFacingLeft = true;
			waterMoving = true;
		}
		if (p2Right) {
			waterX += waterSpeed;
			waterFacingLeft = false;
			waterMoving = true;
		}

		// Gravity
		if (fireVY < 5)
			fireVY += GRAVITY;
		if (waterVY < 5)
			waterVY += GRAVITY;
		fireY += fireVY;
		waterY += waterVY;

		// Floor collision
		fireOnGround = false;
		waterOnGround = false;

		for (Rectangle floor : floors) {
			resolveFloor(floor, true);
			resolveFloor(floor, false);
		}
		for (Rectangle wall : walls) {
			resolveFloor(wall, true);
			resolveFloor(wall, false);
		}

		// FIRST: Get player rectangles
		Rectangle fb = fireRect();
		Rectangle wb = waterRect();

		// SECOND: Switch checks - MULTIPLE switches can control the SAME block
		HashMap<Rectangle, Boolean> blockPressed = new HashMap<>();

		for (SwitchPair pair : switchPairs) {
			boolean playerOnSwitch = pair.switchButton.intersects(fb) || pair.switchButton.intersects(wb);

			// If ANY switch for this block is pressed, mark the block as pressed
			if (playerOnSwitch) {
				blockPressed.put(pair.switchBlock, true);
			}
		}

		// Now apply the pressed state to all switch pairs
		for (SwitchPair pair : switchPairs) {
			Boolean isPressed = blockPressed.get(pair.switchBlock);
			pair.pressed = (isPressed != null && isPressed);

			// If block is NOT pressed by any switch, it's solid
			if (!pair.pressed) {
				resolveFloor(pair.switchBlock, true);
				resolveFloor(pair.switchBlock, false);
			}
		}

		// Clamp to screen
		if (fireX < 0)
			fireX = 0;
		if (fireX > W - PLAYER_W)
			fireX = W - PLAYER_W;
		if (waterX < 0)
			waterX = 0;
		if (waterX > W - PLAYER_W)
			waterX = W - PLAYER_W;

		// Hazard collision
		checkHazards();

		// Diamond pickup
		for (Diamond d : diamonds) {
			if (!d.collected) {
				if (d.type == 1 && d.getBounds().intersects(fb)) {
					d.collected = true;
				}
				if (d.type == 0 && d.getBounds().intersects(wb)) {
					d.collected = true;
				}
			}
		}

		elapsedSeconds = (int) ((System.currentTimeMillis() - startTime) / 1000);

		// Door and win check
		Rectangle fd = fireDoor != null ? fireDoor : new Rectangle(0, 0, 0, 0);
		Rectangle wd = waterDoor != null ? waterDoor : new Rectangle(0, 0, 0, 0);
		boolean fInDoor = fd.intersects(fb);
		boolean wInDoor = wd.intersects(wb);

		if (fInDoor && wInDoor && gameState == State.PLAYING) {
			gameState = State.ENTERING;
			stateTime = System.currentTimeMillis();
		}
	}

	private void resolveFloor(Rectangle r, boolean isFirePlayer) {
		int px = isFirePlayer ? fireX : waterX;
		int py = isFirePlayer ? fireY : waterY;
		int vy = isFirePlayer ? fireVY : waterVY;

		Rectangle player = new Rectangle(px + 4, py, PLAYER_W - 8, PLAYER_H);

		if (!player.intersects(r))
			return;

		int playerBottom = py + PLAYER_H;
		int playerTop = py;
		int playerRight = px + PLAYER_W;
		int playerLeft = px;

		int rectTop = r.y;
		int rectBottom = r.y + r.height;
		int rectLeft = r.x;
		int rectRight = r.x + r.width;

		int overlapBottom = playerBottom - rectTop;
		int overlapTop = rectBottom - playerTop;
		int overlapLeft = playerRight - rectLeft;
		int overlapRight = rectRight - playerLeft;

		int minOverlap = Math.min(Math.min(overlapBottom, overlapTop), Math.min(overlapLeft, overlapRight));

		if (minOverlap == overlapBottom && vy >= 0) {
			// Landing on top
			if (isFirePlayer) {
				fireY = rectTop - PLAYER_H;
				fireVY = 0;
				fireOnGround = true;
			} else {
				waterY = rectTop - PLAYER_H;
				waterVY = 0;
				waterOnGround = true;
			}
		} else if (minOverlap == overlapTop && vy < 0) {
			// Hitting from below
			if (isFirePlayer) {
				fireY = rectBottom;
				fireVY = 2;
			} else {
				waterY = rectBottom;
				waterVY = 2;
			}
		} else if (minOverlap == overlapLeft) {
			// Hit from left side
			if (isFirePlayer) {
				fireX = rectLeft - PLAYER_W;
			} else {
				waterX = rectLeft - PLAYER_W;
			}
		} else if (minOverlap == overlapRight) {
			// Hit from right side
			if (isFirePlayer) {
				fireX = rectRight;
			} else {
				waterX = rectRight;
			}
		}
	}

	private void checkHazards() {

		// very small center strip — only triggers when standing directly on the color
		Rectangle fFeet = new Rectangle(fireX + PLAYER_W / 2 - 5, fireY + PLAYER_H - 6, 3, 6);
		Rectangle wFeet = new Rectangle(waterX + PLAYER_W / 2 - 5, waterY + PLAYER_H - 6, 3, 6);
		// FIRE POOLS — watergirl dies, fireboy is safe
		if (firePool1 != null && firePool1.intersects(wFeet)) {
			waterAlive = false;
		}

		// WATER POOLS — fireboy dies, watergirl is safe
		if (waterPool1 != null && waterPool1.intersects(fFeet)) {
			fireAlive = false;
		}
		if (waterPool2 != null && waterPool2.intersects(fFeet)) {
			fireAlive = false;
		}

		// GREEN POOL — both die, no one is safe
		if (greenPool != null && greenPool.intersects(fFeet)) {
			fireAlive = false;
		}
		if (greenPool != null && greenPool.intersects(wFeet)) {
			waterAlive = false;
		}

		if (!fireAlive || !waterAlive) {
			gameState = State.DEAD;
			stateTime = System.currentTimeMillis();
		}
	}

	private Rectangle fireRect() {
		return new Rectangle(fireX + 4, fireY, PLAYER_W - 8, PLAYER_H);
	}

	private Rectangle waterRect() {
		return new Rectangle(waterX + 4, waterY, PLAYER_W - 8, PLAYER_H);
	}

	// Paint
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawBackground(g2);
		drawHazards(g2);
		drawDoors(g2);
		drawBricks(g2);
		drawSwitches(g2);
		drawDiamonds(g2);
		drawUI(g2);
		drawOverlay(g2);
		if (gameState != State.ENTERING) {
			drawFirePlayer(g2);
			drawWaterPlayer(g2);
		}
	}

	// Background
	private void drawBackground(Graphics2D g) {
		if (backgroundImg != null) {
			// Draw background image stretched to fit the panel
			g.drawImage(backgroundImg, 0, 0, W, H - UI_H, this);
		} else {
			// Fallback color if image is missing
			g.setColor(new Color(28, 28, 52));
			g.fillRect(0, 0, W, H - UI_H);
		}

		// Bottom UI bar (keep this)
		g.setColor(new Color(15, 15, 30));
		g.fillRect(0, H - UI_H, W, UI_H);
		g.setColor(new Color(55, 42, 25));
		g.fillRect(0, H - UI_H, W, 4);
	}

	private void drawBricks(Graphics2D g) {
		for (Rectangle r : floors)
			drawBrickRect(g, r);
		for (Rectangle r : walls)
			drawBrickRect(g, r);

		// Draw switch blocks (only if not pressed)
		for (SwitchPair pair : switchPairs) {
			if (pair.switchBlock == null)
				continue;

			if (!pair.pressed) {
				if (switchDoorImg != null) {
					g.drawImage(switchDoorImg, pair.switchBlock.x, pair.switchBlock.y, pair.switchBlock.width,
							pair.switchBlock.height, this);
				} else {
					drawBrickRect(g, pair.switchBlock);
				}
			}
		}
	}

	private void drawBrickRect(Graphics2D g, Rectangle r) {
		if (r == null)
			return;
		int bW = 48, bH = 24;

		if (brickImg != null) {
			for (int row = 0; row * bH < r.height + bH; row++) {
				int offsetX = (row % 2 == 0) ? 0 : bW / 2;
				for (int col = -bW; col < r.width + bW; col += bW) {
					int bx = r.x + col + offsetX;
					int by = r.y + row * bH;
					if (bx + bW > r.x && bx < r.x + r.width && by + bH > r.y && by < r.y + r.height) {
						Shape oldClip = g.getClip();
						g.setClip(r.x, r.y, r.width, r.height);
						g.drawImage(brickImg, bx, by, bW, bH, this);
						g.setClip(oldClip);
					}
				}
			}
		} else {
			g.setColor(new Color(160, 82, 45));
			g.fillRect(r.x, r.y, r.width, r.height);
			g.setColor(new Color(100, 50, 20));
			for (int row = 0; row * bH < r.height; row++) {
				int offsetX = (row % 2 == 0) ? 0 : bW / 2;
				for (int col = -bW; col < r.width + bW; col += bW) {
					int bx = r.x + col + offsetX;
					int by = r.y + row * bH;
					if (bx + bW > r.x && bx < r.x + r.width) {
						g.drawRect(bx, by, bW, bH);
					}
				}
			}
			g.setColor(new Color(200, 120, 70, 120));
			g.fillRect(r.x, r.y, r.width, 3);
			g.setColor(new Color(60, 30, 10, 200));
			g.fillRect(r.x, r.y + r.height - 3, r.width, 3);
		}
	}

	// Hazards
	private void drawHazards(Graphics2D g) {
		drawFirePool(g, firePool1);
		drawFirePool(g, firePool2);
		drawWaterPool(g, waterPool1);
		drawWaterPool(g, waterPool2);
		drawGreenPool(g, greenPool);
	}

	private void drawFirePool(Graphics2D g, Rectangle r) {
		if (r == null)
			return;
		if (fireObstacleImg != null) {
			g.drawImage(fireObstacleImg, r.x, r.y, r.width, r.height, this);
		} else {
			g.setColor(new Color(180, 40, 10));
			g.fillRoundRect(r.x, r.y + 2, r.width, r.height - 2, 4, 4);
			g.setColor(new Color(255, 150, 0, 200));
			for (int i = 0; i < 3; i++) {
				g.fillPolygon(new int[] { r.x + 15 + i * 30, r.x + 20 + i * 30, r.x + 25 + i * 30 },
						new int[] { r.y, r.y - 12, r.y }, 3);
			}
		}
	}

	private void drawWaterPool(Graphics2D g, Rectangle r) {
		if (r == null)
			return;
		if (waterObstacleImg != null) {
			g.drawImage(waterObstacleImg, r.x, r.y, r.width, r.height, this);
		} else {
			g.setColor(new Color(30, 100, 210, 210));
			g.fillRoundRect(r.x, r.y + 2, r.width, r.height - 2, 4, 4);
			g.setColor(new Color(100, 180, 255, 180));
			g.drawArc(r.x + 4, r.y - 4, 30, 10, 0, 180);
			g.drawArc(r.x + 40, r.y - 4, 30, 10, 0, 180);
		}
	}

	private void drawGreenPool(Graphics2D g, Rectangle r) {
		if (r == null)
			return;
		if (poisonObstacleImg != null) {
			g.drawImage(poisonObstacleImg, r.x, r.y, r.width, r.height, this);
		} else {
			g.setColor(new Color(0, 160, 10, 210));
			g.fillRoundRect(r.x, r.y + 2, r.width, r.height - 2, 4, 4);
			g.setColor(new Color(100, 255, 120, 180));
			g.drawArc(r.x + 4, r.y - 4, 25, 10, 0, 180);
			g.drawArc(r.x + 30, r.y - 4, 25, 10, 0, 180);
		}
	}

	// Doors
	private void drawDoors(Graphics2D g) {
		if (gameState == State.ENTERING) {
			if (fireEnterGif != null && fireDoor != null) {
				g.drawImage(fireEnterGif, fireDoor.x - 10, fireDoor.y - 20, fireDoor.width + 20, fireDoor.height + 20,
						this);
			}
			if (waterEnterGif != null && waterDoor != null) {
				g.drawImage(waterEnterGif, waterDoor.x - 10, waterDoor.y - 20, waterDoor.width + 20,
						waterDoor.height + 20, this);
			}

		} else {
			if (fireDoor != null) {
				if (fireDoorImg != null) {
					g.drawImage(fireDoorImg, fireDoor.x, fireDoor.y, fireDoor.width, fireDoor.height, this);
				} else {
					g.setColor(new Color(200, 50, 10));
					g.fillRect(fireDoor.x, fireDoor.y, fireDoor.width, fireDoor.height);
				}
			}
			if (waterDoor != null) {
				if (waterDoorImg != null) {
					g.drawImage(waterDoorImg, waterDoor.x, waterDoor.y, waterDoor.width, waterDoor.height, this);
				} else {
					g.setColor(new Color(20, 80, 200));
					g.fillRect(waterDoor.x, waterDoor.y, waterDoor.width, waterDoor.height);
				}
			}
		}
	}

	private void drawSwitches(Graphics2D g) {
		for (SwitchPair pair : switchPairs) {
			if (pair.switchButton == null)
				continue;
			Image si = pair.pressed ? switchOnImg : switchImg;
			if (si != null) {
				g.drawImage(si, pair.switchButton.x, pair.switchButton.y, pair.switchButton.width,
						pair.switchButton.height, this);
			} else {
				g.setColor(pair.pressed ? Color.GREEN : Color.RED);
				g.fillRect(pair.switchButton.x, pair.switchButton.y, pair.switchButton.width, pair.switchButton.height);
			}
		}
	}

	// Diamonds
	private void drawDiamonds(Graphics2D g) {
		for (Diamond d : diamonds) {
			if (d.collected)
				continue;
			Image gem = (d.type == 0) ? waterGemImg : fireGemImg;
			if (gem != null) {
				g.drawImage(gem, d.x, d.y, d.w, d.h, this);
			} else {
				if (d.type == 0) {
					g.setColor(new Color(20, 120, 255));
				} else {
					g.setColor(new Color(255, 80, 10));
				}
				int[] dx = { d.x + d.w / 2, d.x + d.w, d.x + d.w / 2, d.x };
				int[] dy = { d.y, d.y + d.h / 2, d.y + d.h, d.y + d.h / 2 };
				g.fillPolygon(dx, dy, 4);
				g.setColor(Color.WHITE);
				g.drawPolygon(dx, dy, 4);
			}
		}
	}

	// Fire player drawing
	private void drawFirePlayer(Graphics2D g) {
		int fx = fireX, fy = fireY;

		if (!fireAlive) {
			if (fireDie != null) {
				g.drawImage(fireDie, fx, fy, PLAYER_W, PLAYER_H, this);
			} else {
				g.setColor(new Color(90, 90, 90));
				g.fillRoundRect(fx + 6, fy + 16, 28, 24, 6, 6);
				g.fillOval(fx + 8, fy + 2, 24, 22);
				g.setColor(new Color(60, 60, 60));
				g.drawLine(fx + 11, fy + 7, fx + 19, fy + 15);
				g.drawLine(fx + 19, fy + 7, fx + 11, fy + 15);
				g.drawLine(fx + 21, fy + 7, fx + 29, fy + 15);
				g.drawLine(fx + 29, fy + 7, fx + 21, fy + 15);
			}
			return;
		}

		if (fireMoving && fireFacingLeft && fireLeft != null) {
			g.drawImage(fireLeft, fx, fy, PLAYER_W, PLAYER_H, this);
		} else if (fireMoving && !fireFacingLeft && fireRight != null) {
			g.drawImage(fireRight, fx, fy, PLAYER_W, PLAYER_H, this);
		} else if (fireIdle != null) {
			g.drawImage(fireIdle, fx, fy, PLAYER_W, PLAYER_H, this);
		} else {
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
	}

	// Water player drawing
	private void drawWaterPlayer(Graphics2D g) {
		int wx = waterX, wy = waterY;

		if (!waterAlive) {
			if (waterDie != null) {
				g.drawImage(waterDie, wx, wy, PLAYER_W, PLAYER_H, this);
			} else {
				g.setColor(new Color(90, 90, 90));
				g.fillRoundRect(wx + 6, wy + 16, 28, 24, 6, 6);
				g.fillOval(wx + 8, wy + 2, 24, 22);
				g.setColor(new Color(60, 60, 60));
				g.drawLine(wx + 11, wy + 7, wx + 19, wy + 15);
				g.drawLine(wx + 19, wy + 7, wx + 11, wy + 15);
				g.drawLine(wx + 21, wy + 7, wx + 29, wy + 15);
				g.drawLine(wx + 29, wy + 7, wx + 21, wy + 15);
			}
			return;
		}

		if (waterMoving && waterFacingLeft && waterLeft != null) {
			g.drawImage(waterLeft, wx, wy, PLAYER_W, PLAYER_H, this);
		} else if (waterMoving && !waterFacingLeft && waterRight != null) {
			g.drawImage(waterRight, wx, wy, PLAYER_W, PLAYER_H, this);
		} else if (waterIdle != null) {
			g.drawImage(waterIdle, wx, wy, PLAYER_W, PLAYER_H, this);
		} else {
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
	}

	// UI bar at bottom
	private void drawUI(Graphics2D g) {
		g.setColor(new Color(230, 80, 20));
		g.setFont(new Font("Arial", Font.BOLD, 13));
		g.drawString("FIREBOY", 30, H - 28);
		g.setColor(new Color(200, 160, 140));
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("\u2190 / \u2192  move   \u2191  jump", 30, H - 12);

		g.setColor(new Color(60, 140, 230));
		g.setFont(new Font("Arial", Font.BOLD, 13));
		g.drawString("WATERGIRL", 414, H - 28);
		g.setColor(new Color(140, 170, 210));
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("A / D  move   W  jump", 414, H - 12);
		// Timer in the middle
		Font timerFont = new Font("Arial", Font.BOLD, 18);
		g.setFont(timerFont);
		FontMetrics fm = g.getFontMetrics(timerFont);
		String timeText = "Time: " + elapsedSeconds + "s";
		int textWidth = fm.stringWidth(timeText);
		int tx = (getWidth() - textWidth) / 2;
		int panelH = getHeight();
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(getWidth() / 2 - 65, 5, 130, 30);
		g.setColor(Color.YELLOW);
		g.drawString(timeText, tx, 26);
	}

	// Overlay (win / dead)
	private void drawOverlay(Graphics2D g) {
		if (gameState == State.WIN) {
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(0, 0, W, H);
			if (gameClearImg != null) {
				int iw = 400, ih = 150;
				g.drawImage(gameClearImg, (W - iw) / 2, (H - ih) / 2 - 60, iw, ih, this);
			} else {
				g.setColor(new Color(80, 240, 120));
				g.setFont(new Font("Arial", Font.BOLD, 36));
				g.drawString("GAME CLEARED!", 250, H / 2);
			}
		}
		if (gameState == State.DEAD) {
			if (gameOverImg != null) {
				int iw = 400, ih = 150;
				g.drawImage(gameOverImg, (W - iw) / 2, (H - ih) / 2 - 60, iw, ih, this);
			} else {
				g.setColor(new Color(0, 0, 0, 160));
				g.fillRoundRect(220, 270, 340, 70, 12, 12);
				g.setColor(new Color(240, 60, 60));
				g.setFont(new Font("Arial", Font.BOLD, 36));
				g.drawString("GAME OVER", 278, 318);
			}
		}

		if (gameState == State.ENTERING) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, W, H);
		}
	}

	// Key events
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == KeyEvent.VK_UP) {
			p1Up = true;
			if (fireOnGround) {
				fireVY = JUMP_FORCE;
				fireOnGround = false;
			}
		}
		if (k == KeyEvent.VK_LEFT)
			p1Left = true;
		if (k == KeyEvent.VK_RIGHT)
			p1Right = true;
		if (k == KeyEvent.VK_W) {
			p2Up = true;
			if (waterOnGround) {
				waterVY = JUMP_FORCE;
				waterOnGround = false;
			}
		}
		if (k == KeyEvent.VK_A)
			p2Left = true;
		if (k == KeyEvent.VK_D)
			p2Right = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == KeyEvent.VK_UP)
			p1Up = false;
		if (k == KeyEvent.VK_LEFT)
			p1Left = false;
		if (k == KeyEvent.VK_RIGHT)
			p1Right = false;
		if (k == KeyEvent.VK_W)
			p2Up = false;
		if (k == KeyEvent.VK_A)
			p2Left = false;
		if (k == KeyEvent.VK_D)
			p2Right = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}