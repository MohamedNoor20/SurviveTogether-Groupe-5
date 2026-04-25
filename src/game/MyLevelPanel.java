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
}