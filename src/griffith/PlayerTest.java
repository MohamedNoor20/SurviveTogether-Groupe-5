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
		fail("Not yet implemented");
	}

	@Test
	void testMoveLeft() {
		fail("Not yet implemented");
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
