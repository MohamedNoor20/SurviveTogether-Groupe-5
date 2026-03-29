package griffith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {
	
	@Test
	void testMoveUp() {
		Player player = new Player(100, 100, Type.WATER);
		player.isJumping = false;
		player.moveUp();
		
		assertEquals(-15, player.yVelocity, "jumped seccesfuly");
		assertTrue(player.isJumping, "isJumping turned on");
	}

	@Test
	void testMoveDown() {
		Player player = new Player(100, 100, Type.FIRE);
		player.yVelocity = 0;
		player.moveDown();
		
		assertEquals(1, player.yVelocity, "yVelocity increases by 1 so it it goes down");
	}

	@Test
	void testMoveLeft() {
		Player player = new Player(100, 100, Type.FIRE);
		int startX = player.x;
		player.moveLeft();
		assertEquals(startX - 3, player.x, "player mmoves to the left");		
	}

	@Test
	void testMoveRight() {
		fail("Not yet implemented");
	}

	@Test
	void testGravity() {
		fail("Not yet implemented");
	}

}
