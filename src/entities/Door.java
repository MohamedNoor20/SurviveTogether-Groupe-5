package entities;

import java.awt.Rectangle;

//win condition for door (susan ogozi)
public class Door {
	private Rectangle area; // this is the door hit box (susan ogozi)
	// creates the door for winning

	public Door(Rectangle area) {
		this.area = area;
	}

	// check if the player is inside the door (susan ogozi)
	public boolean isInside(Player p) {
		// player overlaps the door? (susan ogozi)
		return area.intersects(new Rectangle(p.x, p.y, 40, 40));
	
	}
	// Checks if BOTH players are inside door  win condition(susan ogozi)
	public boolean bothInside(Player fireboy, Player watergirl) {
	    return isInside(fireboy) && isInside(watergirl);
	}
	
	// Public getter for the door's hitbox (susan ogozi)
	public Rectangle getArea() {
		return area;
	}
}