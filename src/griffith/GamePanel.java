package griffith;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

    Player player1;
    Player player2;

    Hazard firePool;
    Hazard waterPool;

    Door door;
    //player 1
    boolean p1Up;
    boolean p1Down;
    boolean p1Left;
    boolean p1Right;
    // player 2
    boolean p2Up;
    boolean p2Down;
    boolean p2Left;
    boolean p2Right;
    // Mohamed
    public GamePanel() {

		//Create players
		player1 = new Player(50, 50, Type.FIRE);
		player2 = new Player(50, 150, Type.WATER);
		
        setFocusable(true);
        // to be able to use KeyListener library
        addKeyListener(this);

        // Create hazards
        firePool = new Hazard(new Rectangle(300, 100, 100, 50), Type.FIRE);
        waterPool = new Hazard(new Rectangle(300, 100, 150, 200), Type.WATER);

        // Create door
        door = new Door(new Rectangle(600, 150, 50, 80));
    }
    // Mohamed
    // This runs the frame
    public boolean updateGame() {


        if (p1Up) player1.moveUp();
        if (p1Down) player1.moveDown();
        if (p1Left) player1.moveLeft();
        if (p1Right) player1.moveRight();

        if (p2Up) player2.moveUp();
        if (p2Down) player2.moveDown();
        if (p2Left) player2.moveLeft();
        if (p2Right) player2.moveRight();

        // This will call Susan's logic
        firePool.check(player1);
        firePool.check(player2);

        waterPool.check(player1);
        waterPool.check(player2);

        // Win condition
        if (door.isInside(player1) && door.isInside(player2)) {
            return false;
        }

        // Lose condition
        if (!player1.alive || !player2.alive) {
            return false;
        }
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // player 1 controls (arrow keys)
        if (key == KeyEvent.VK_UP) p1Up = true;
        if (key == KeyEvent.VK_DOWN) p1Down = true;
        if (key == KeyEvent.VK_LEFT) p1Left = true;
        if (key == KeyEvent.VK_RIGHT) p1Right = true;

        // player 2 controls (W, A, S, D)
        if (key == KeyEvent.VK_W) p2Up = true;
        if (key == KeyEvent.VK_S) p2Down = true;
        if (key == KeyEvent.VK_A) p2Left = true;
        if (key == KeyEvent.VK_D) p2Right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // this will dedect if the user stoped pressing the key

        //player 1
        if (key == KeyEvent.VK_UP) p1Up = false;
        if (key == KeyEvent.VK_DOWN) p1Down = false;
        if (key == KeyEvent.VK_LEFT) p1Left = false;
        if (key == KeyEvent.VK_RIGHT) p1Right = false;

        // player 2 
        if (key == KeyEvent.VK_W) p2Up = false;
        if (key == KeyEvent.VK_S) p2Down = false;
        if (key == KeyEvent.VK_A) p2Left = false;
        if (key == KeyEvent.VK_D) p2Right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //it is empty because we need it for the interface :)
    }

    
}