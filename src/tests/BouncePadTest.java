package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;

import entities.*;

public class BouncePadTest {

    @Test
    public void testBouncePadInitialization() {
    	// here we create a bounce pad and then we check if it is created
        BouncePad pad = new BouncePad(new Rectangle(100, 100, 50, 15));
        assertNotNull(pad.area);
    }

    @Test
    public void testSuccessfulBounce() {
    	//here we are adding a bounce pad and making fire boy using it
    	BouncePad pad = new BouncePad(new Rectangle(50, 50, 50, 15));
    	Player player = new Player(60, 50, Type.FIRE);
    	player.yVelocity = 0;
    	pad.checkBounce(player);
    	// here we check is the player did jump high as we expecting and if jump state is true
    	assertEquals(-22, player.yVelocity, 0.01, "Player jupmed higher using the bounce");
        assertTrue(player.isJumping, "Player's jump is true");

    }
}