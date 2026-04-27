package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Rectangle;

import entities.*;

class PortalTest {
	
	@Test
	public void testPortalInitialization() {
		// here we create a portal
		Portal portal = new Portal(new Rectangle(0, 0, 50, 50), 500, 500);
		// here we check if it was created or not
		assertEquals(500, portal.X);
        assertEquals(500, portal.Y);
	}
	@Test
	public void testSuccessfulTeleport() {
		//here we create a portal and then we put fire boy inside the portal 
		Portal portal = new Portal(new Rectangle(10, 10, 40, 40), 200, 300);
		Player player = new Player(20, 20, Type.FIRE);
		portal.checkTeleport(player);
		//then we check if fire boy did teleport
		assertEquals(200, player.x);
        assertEquals(300, player.y);
		
	}
	

}