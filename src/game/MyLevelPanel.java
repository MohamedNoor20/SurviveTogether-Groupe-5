package game;
//Susan Ogozi
//3157092
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    Main mainFrame;

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
    
    //timer
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
    static final int PLAYER_H =60;

    // Game state
    enum State { PLAYING, ENTERING, WIN, DEAD }
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

    public MyLevelPanel() {
        setPreferredSize(new Dimension(W, H));
        setBackground(new Color(18, 18, 38));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);

        loadImages();
        buildLevel();
        addMenuButton();
    }

    public void setMainFrame(Main m) {
        this.mainFrame = m;
    }

    //Load all images
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

        //RIGHT PILLAR 
        walls.add(new Rectangle(680, 600, 24, 100));
        walls.add(new Rectangle(680, 600, 88, 24));

        //1ST PLATFORM
        floors.add(new Rectangle(0, 530, 155, 24));

        // left closed bricks
       
        walls.add(new Rectangle(0, 445, 80, 24));
        walls.add(new Rectangle(0, 469, 24, 61));  // closes first gap permanent wall

        floors.add(new Rectangle(155, 530, 478, 24));

        // 2ND PLATFORM 
        floors.add(new Rectangle(120, 375, 648, 24));

        // Closed middle section
        walls.add(new Rectangle(340, 282, 24, 80));
        walls.add(new Rectangle(340, 282, 88, 24));
      

        // 3RD PLATFORM 
        floors.add(new Rectangle(120, 200, 192, 24));
        floors.add(new Rectangle(470, 200, 192, 24));

        // 4TH PLATFORM (TOP) 
        floors.add(new Rectangle(0, 110, 470, 24));
        floors.add(new Rectangle(662, 110, 106, 24));

        // Closed left section (top)
        walls.add(new Rectangle(120, 110, 24, 100));
       
        
    
        
        // SWITCH PAIRS 3 total  one for each closed brick
       

        // SWITCH PAIR 1 - Left on 1st platform
        Rectangle switch1Button = new Rectangle(300, 514, 30, 16);  // On 1st platform floor
        Rectangle switch1Block = new Rectangle(80, 469, 10, 61);     // Bottom door of alcove
        switchPairs.add(new SwitchPair(switch1Button, switch1Block));
        diamonds.add(new Diamond(30, 480, 1));  // Red diamond inside alcove

        // SWITCH PAIR 2 - Middle closed brick on 2nd platform
        Rectangle switch2Button = new Rectangle(600, 359, 30, 16);  // Right side of 2nd platform
        Rectangle switch2Block = new Rectangle(428, 306, 10, 66);   // Bottom door of middle brick
        switchPairs.add(new SwitchPair(switch2Button, switch2Block));
        diamonds.add(new Diamond(370, 330, 0));  // Blue diamond inside middle brick

        // SWITCH PAIR 3 - Left closed brick on 4th platform
        Rectangle switch3Button = new Rectangle(550, 184, 30, 16);  // Right side of 3rd platform
        Rectangle switch3Block = new Rectangle(190, 134, 10, 66);   // Bottom door of top brick
        switchPairs.add(new SwitchPair(switch3Button, switch3Block));
        diamonds.add(new Diamond(150, 150, 1));  // Red diamond inside top brick

        //FIRE POOLS - merged with floors 
        
        firePool1 = new Rectangle(200, 526, 70, 12);   // 2st platform

        //WATER POOLS - merged with floors
        waterPool1 = new Rectangle(360, 694, 70, 14);  // ground
        waterPool2 = new Rectangle(400, 523, 70, 30);  // 1st platform

        // GREEN/POISON POOL - LEFT SIDE OF 2ND PLATFORM 
        greenPool = new Rectangle(180, 370, 70, 14);    // Left side of 2nd platform

        // DOORS at top 
        fireDoor = new Rectangle(0, 40, 56, 80);
        waterDoor = new Rectangle(700, 40, 56, 80);

        //ADDITIONAL DIAMONDS
        // Red diamonds IN fire pools (fire boy collects)
        diamonds.add(new Diamond(240, 670, 1));  // firePool1
        diamonds.add(new Diamond(230, 500, 1));  // firePool2

        // Blue diamonds IN water pools (water girl collects)
        diamonds.add(new Diamond(390, 670, 0));  // waterPool1
        diamonds.add(new Diamond(430, 500, 0));  // waterPool2

        // Safe platform diamonds
        diamonds.add(new Diamond(60, 490, 1));   // left 1st platform - red

        diamonds.add(new Diamond(490, 160, 0));  // 3rd platform right - blue
        diamonds.add(new Diamond(200, 70, 0));   // top platform - blue
        diamonds.add(new Diamond(350, 70, 1));   // top platform - red
    }

    private void addMenuButton() {
        JButton btn = new JButton() {
            protected void paintComponent(Graphics g) {
                if (menuBtnImg != null) {
                    g.drawImage(menuBtnImg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        btn.setBounds(W - 100, 8, 70, 25);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            if (mainFrame != null) mainFrame.showMainMenu();
        });

        add(btn);
    
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

        //Water player movement
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

        //Gravity
        if (fireVY < 5) fireVY += GRAVITY;
        if (waterVY < 5) waterVY += GRAVITY;
        fireY += fireVY;
        waterY += waterVY;

        //Floor collision
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

        // Switch checks (update all switches) 
        Rectangle fb = fireRect();
        Rectangle wb = waterRect();

        for (SwitchPair pair : switchPairs) {
            boolean playerOnSwitch = pair.switchButton.intersects(fb) || pair.switchButton.intersects(wb);
            pair.pressed = playerOnSwitch;

            // If switch not pressed, block is solid
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
        
        elapsedSeconds = (int)((System.currentTimeMillis() - startTime) / 1000);
        
        //  Door and win check 
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

        if (!player.intersects(r)) return;

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

        int minOverlap = Math.min(Math.min(overlapBottom, overlapTop),
                                 Math.min(overlapLeft, overlapRight));

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
    	    Rectangle fFeet = new Rectangle(fireX + PLAYER_W/2 - 5, fireY + PLAYER_H - 6, 3, 6);
    	    Rectangle wFeet = new Rectangle(waterX + PLAYER_W/2 - 5, waterY + PLAYER_H - 6, 3, 6);
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
   }