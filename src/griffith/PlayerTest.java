package griffith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {
	
	@Test
	void testMoveUp() {
		Player player = new Player(100, 100, Type.WATER);
		//this will test if we did jump will the player jump to where we expect and will the state of isJumping turn on/true

		player.isJumping = false;
		player.moveUp();
		
		assertEquals(-15, player.yVelocity, "jumped seccesfuly");
		assertTrue(player.isJumping, "isJumping turned on");
	}

	@Test
	void testMoveDown() {
		Player player = new Player(100, 100, Type.FIRE);
		//this will test if the player did move 1 pixels to the down

		player.yVelocity = 0;
		player.moveDown();
		
		assertEquals(1, player.yVelocity, "yVelocity increases by 1 so it goes down");
	}

	@Test
	void testMoveLeft() {
		Player player = new Player(100, 100, Type.FIRE);
		//this will test if the player did move 3 pixels to the left side
		int startX = player.x;
		player.moveLeft();
		
		assertEquals(startX - 3, player.x, "player mmoves to the left");		
	}

	@Test
	void testMoveRight() {
		Player player = new Player(100, 100, Type.FIRE);
		//this will test if the player did move 3 pixels to the right side

		int startX = player.x;
		player.moveRight();
		
		assertEquals(startX + 3, player.x, "player mmoves to the Right");	
	}

	@Test
	void testGravity() {
		Player player = new Player(100, 100, Type.FIRE);
		//this will test if we did put the player on 100 in the y access then we called the gravity method will the player go down

		player.y = 100;
		player.yVelocity = 0;
		player.gravity();
		
		assertEquals(1, player.yVelocity, "Gravity working");
	}

}
