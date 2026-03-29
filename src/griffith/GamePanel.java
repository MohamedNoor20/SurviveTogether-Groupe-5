package griffith;


import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    Player player1;
    Player player2;

    Hazard firePool;
    Hazard waterPool;

    Door door;
    // Mohamed
    public GamePanel() {

        // Create players
        player1 = new Player(50, 50, Type.FIRE);
        player2 = new Player(50, 150, Type.WATER);
        
        addKeyListener(new KeyHandler(player1,player2));
        setFocusable(true);

        // Create hazards
        firePool = new Hazard(new Rectangle(300, 100, 100, 50), Type.FIRE);
        waterPool = new Hazard(new Rectangle(300, 100, 150, 200), Type.WATER);

        // Create door
        door = new Door(new Rectangle(600, 150, 50, 80));
    }
    // Mohamed
    // This runs the frame
    public boolean updateGame() {

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
    
}