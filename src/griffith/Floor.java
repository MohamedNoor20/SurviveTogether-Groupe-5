package griffith;

import java.awt.Rectangle;

public class Floor {
	public Rectangle area; // Stores the floor's position and size
	public boolean onGround = false;

	// Constructor - creates a new Floor
	public Floor(Rectangle area) {
		this.area = area;
	}

	// Check if player is standing on this floor (for jumping)
	public boolean isOnFloor(Player p) {
		// Create a thin sensor at the bottom of the player
		Rectangle playerBox = new Rectangle(p.x, p.y + p.height, p.width, 1);
		return area.intersects(playerBox);
	}

	public void stopFallThrough(Player p) {
		Rectangle playerBox = p.getBounds();

		if (area.intersects(playerBox)) {
			// Calculate how far the player is inside the floor on each side
			int overlapLeft = (p.x + p.width) - area.x;
			int overlapRight = (area.x + area.width) - p.x;
			int overlapTop = (p.y + p.height) - area.y;// 2
			int overlapBottom = (area.y + area.height) - p.y; //8

			// Find the smallest overlap to see which side was hit
			int min = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));
			// Floor
			if (min == overlapTop && p.yVelocity > 0) {
				p.y = area.y - p.height;
				p.yVelocity = 0;
				p.isJumping = false;
			}
			// Ceiling
			else if (min == overlapBottom && p.yVelocity < 0) {
				p.y = area.y + area.height;
				p.yVelocity = 0;
			}
			// Left wall
			else if (min == overlapLeft) {
				p.x = area.x - p.width;
			}
			// Right wall
			else if (min == overlapRight) {
				p.x = area.x + area.width;
			}
		}
	}
}