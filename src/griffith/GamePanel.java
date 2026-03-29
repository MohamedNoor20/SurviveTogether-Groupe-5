import java.awt.Rectangle;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    Player player1;
    Player player2;

    Hazard firePool;
    Hazard waterPool;

    Door door;

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
    
}