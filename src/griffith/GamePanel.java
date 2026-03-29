import java.awt.Rectangle;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    Player player1;
    Player player2;

    Hazard firePool;
    Hazard waterPool;

    Door door;
    // Mohamed
    public GamePanel() {
        setFocusable(true);

        // Create players
        player1 = new Player(50, 50, Type.FIRE);
        player2 = new Player(50, 150, Type.WATER);

        // Create hazards
        firePool = new Hazard(new Rectangle(300, 100, 100, 50), Type.FIRE);
        waterPool = new Hazard(new Rectangle(300, 100, 100, 50), Type.WATER);

        // Create door
        door = new Door(new Rectangle(600, 150, 50, 80));
    }
    // Mohamed
    // This runs the frame
    public void updateGame() {

        // This will call Susan's logic
        firePool.check(player1);
        firePool.check(player2);

        waterPool.check(player1);
        waterPool.check(player2);

        // Win condition
        if (door.isInside(player1) && door.isInside(player2)) {
            System.out.println("YOU WIN!");
        }

        // Lose condition
        if (!player1.alive || !player2.alive) {
            System.out.println("GAME OVER");
        }
    }
    
}