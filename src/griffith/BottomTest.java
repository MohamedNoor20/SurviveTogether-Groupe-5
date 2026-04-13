package griffith;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;

public class BottomTest {

    @Test
    public void testPressTrue() {
        Bottom bottom = new Bottom(new Rectangle(100, 100, 50, 50));
        Player player1 = new Player(110, 110, Type.FIRE);
        Player player2 = new Player(110, 110, Type.FIRE);

        assertTrue(bottom.press(player1) || bottom.press(player2));
    }

}
