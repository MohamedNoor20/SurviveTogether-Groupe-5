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

   }