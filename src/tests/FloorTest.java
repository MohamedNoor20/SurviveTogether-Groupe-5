package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import entities.Floor;
import entities.Player;
import entities.Type;

import java.awt.Rectangle;

public class FloorTest {

    @Test
    public void testIsOnFloor() {
        Floor floor = new Floor(new Rectangle(0, 100, 200, 20));
        Player player1 = new Player(50, 54, Type.FIRE);
        Player player2 = new Player(20, 54, Type.WATER);
        
        boolean result1 = floor.isOnFloor(player1);
        boolean result2 = floor.isOnFloor(player2);
        
        assertTrue(result1 && result2);
    }

    @Test
    public void topCollision() {
    		//                                       top   bottom 120
	        Floor floor = new Floor(new Rectangle(0, 100, 200, 20));
	        Player player1 = new Player(50, 70, Type.FIRE);
	        Player player2 = new Player(20, 70, Type.WATER);

	        player1.yVelocity = 5; // falling
	        player1.isJumping = true;
	        player2.yVelocity = 5; // falling
	        player2.isJumping = true;

	        floor.stopFallThrough(player1);
	        floor.stopFallThrough(player2);
	        // 100 - 46(height) = 54 to rest position
	        assertEquals(54, player1.y); 
	        assertEquals(0, player1.yVelocity);
	        assertFalse(player1.isJumping);
	        assertEquals(54, player2.y); 
	        assertEquals(0, player2.yVelocity);
	        assertFalse(player2.isJumping);
	    }

    @Test
    public void bottomCollision() {
        Floor floor = new Floor(new Rectangle(0, 100, 200, 20));
        Player player1 = new Player(50, 110, Type.FIRE);
        Player player2 = new Player(20, 100, Type.WATER);
        
        player1.yVelocity = -5; 
        player2.yVelocity = -5;

        floor.stopFallThrough(player1);
        floor.stopFallThrough(player2);
        
        assertEquals(120, player1.y); 
        assertEquals(0, player1.yVelocity);
        assertEquals(120, player2.y); 
        assertEquals(0, player2.yVelocity);
       
    }

    @Test
    public void leftCollision() {
        Floor floor = new Floor(new Rectangle(100, 100, 50, 50));
        // Player overlapping from the LEFT side — near x=100, moving rightward
        Player player = new Player(70, 110, Type.FIRE); // right edge = 110, just inside left wall

        floor.stopFallThrough(player);

        assertEquals(60, player.x); // pushed to area.x - player.width = 100 - 40 = 60
    }

    @Test
    public void rightCollision() {
        Floor floor = new Floor(new Rectangle(100, 100, 50, 50));
        // Player overlapping from the RIGHT side — near x=150
        Player player = new Player(140, 110, Type.FIRE); // x=140, right edge=180 > 150
        
        floor.stopFallThrough(player);

        assertEquals(150, player.x); // pushed to area.x + area.width = 150
    }
}