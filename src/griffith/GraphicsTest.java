package griffith;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Color;

public class GraphicsTest {

    GamePanel game = new GamePanel();

    //testing the position
    @Test
    void testFireboyX() {
        //checking if fireboy X is on screen
        assertEquals(50, game.getFireboyX());
        }

    @Test
    void testFireboyY() {
        //checking if fireboy Y is on screen
        assertEquals(330, game.getFireboyY());
        }

    @Test
    void testWatergirlX() {
        //checking if watergirl X is valid
        assertEquals(130, game.getWatergirlX());
        }

    @Test
    void testWatergirlY() {
        //checking if watergirl Y is valid
        assertEquals(330, game.getWatergirlY());
        }

    //testing colors 
    @Test
    void testFireboyColor() {
        //fireboy should be red
        assertEquals(new Color(200, 50, 10), game.getFireboyColor());
        }

    @Test
    void testWatergirlColor() {
        //watergirl must be blue
        assertEquals(new Color(20, 80, 200), game.getWatergirlColor());
        }

    //testing alive status
    @Test
    void testFireboyAlive() {
        assertTrue(game.isFireboyAlive());
        }

    @Test
    void testWatergirlAlive() {
        assertTrue(game.isWatergirlAlive());
        }
    }