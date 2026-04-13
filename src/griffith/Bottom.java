package griffith;

import java.awt.Rectangle;

public class Bottom {
	
	Rectangle area; // this is the door hit box (susan ogozi)

	public Bottom(Rectangle area) {
		this.area = area;
	}

	public boolean press(Player p) {
		return area.intersects(new Rectangle(p.x, p.y, 40, 40));
	
	}
}
