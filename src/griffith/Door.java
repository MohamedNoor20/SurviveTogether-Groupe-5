package griffith;

import java.awt.Rectangle;

//win condition for door (susan ogozi)
public class Door {
	Rectangle area; // this is the door hit box (susan ogozi)
	// creates the door for winning

	public Door(Rectangle area) {
		this.area = area;
	}

	// check if the player is inside the door (susan ogozi)
	public boolean isInside(Player p) {
		// player overlaps the door? (susan ogozi)
		return area.intersects(new Rectangle(p.x, p.y, 40, 40));
	}
	public boolean bothInside(Player fireboy, Player watergirl) {
	    return isInside(fireboy) && isInside(watergirl);
	}
}