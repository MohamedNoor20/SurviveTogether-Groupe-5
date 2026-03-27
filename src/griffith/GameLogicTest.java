package griffith;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;

public class GameLogicTest {
    
    @Test
    void fireKillsWater() {
        Player water = new Player(0, 0, Type.WATER);     
        Hazard fire = new Hazard(new Rectangle(0, 0, 50, 50), Type.FIRE);
        
        fire.check(water);
        assertFalse(water.alive);  
    }
    @Test
    void fireDoesNotKillFireboy() {
        Player fireboy = new Player(0, 0, Type.FIRE);
        Hazard fire = new Hazard(new Rectangle(0, 0, 50, 50), Type.FIRE);
        
        fire.check(fireboy);
        assertTrue(fireboy.alive);  
    }
    @Test
    void waterKillsFireboy() {  
        Player fireboy = new Player(0, 0, Type.FIRE);
        Hazard water = new Hazard(new Rectangle(0, 0, 50, 50), Type.WATER);
        water.check(fireboy);
        assertFalse(fireboy.alive);  

    }
}