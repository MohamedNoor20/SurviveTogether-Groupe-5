package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Rectangle;

import entities.*;

class PortalTest {
	
	@Test
	public void testPortalInitialization() {
		// here we create a pprtal
		Portal portal = new Portal(new Rectangle(0, 0, 50, 50), 500, 500);
		// here we check if it was created or not
		assertEquals(500, portal.X);
        assertEquals(500, portal.Y);
	}
	@Test
	public void testSuccessfulTeleport() {}
	

}