package griffith;
 
import java.awt.Rectangle;
 
// Floor/Platform that players can stand on and collide with from all sides
public class Floor {
	public double velocityY = 0;
	public Rectangle area; // Stores the floor's position and size
	public int width = 40;
	public int height = 40;
	public boolean onGround = false;
	
	// Constructor - creates a new Floor
	public Floor(Rectangle area) {
		this.area = area;
	}
	
	// Check if player is standing on this floor (for jumping)
	public boolean isOnFloor(Player p) {
		// Create a thin sensor at the bottom of the player
		Rectangle playerBox = new Rectangle(p.x, p.y + height, width, 1);
		return area.intersects(playerBox);
	}
	
	// Main collision detection - handles all 4 sides
	public void stopFallThrough(Player p) {
		Rectangle playerBox = new Rectangle(p.x, p.y, width, height);
		
		// Only process collision if player actually touching this floor
		if (area.intersects(playerBox)) {
			
			// ===== CEILING COLLISION (Player jumping UP) =====
			// When player jumps up (velocityY < 0) and hits ceiling from below
			if (velocityY < 0 && p.y < area.y && p.y + height > area.y) {
			    p.y = area.y - height;  // Push DOWN properly
			    velocityY = 0;
			}
			
			// ===== FLOOR COLLISION (Player falling DOWN) =====
			// When player falls down (velocityY >= 0) and hits floor from above
			else if (velocityY >= 0 && p.y + height > area.y) {
				// Push player up above the floor
				p.y = area.y - height;
				velocityY = 0;  // Stop falling
				onGround = true;  // Player can jump again!
			}
			
			// ===== WALL COLLISION (Moving sideways) =====
			// These are optional - remove if you don't want wall collision
			
			// Left wall collision
			else if (p.x < area.x) {
				p.x = area.x - width;  // Push player left
			}
			
			// Right wall collision
			else if (p.x + width > area.x + area.width) {
				p.x = area.x + area.width;  // Push player right
			}
		}
	}
}