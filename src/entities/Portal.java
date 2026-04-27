package entities;

import java.awt.Rectangle;

public class Portal {
	
	public Rectangle area;
	public int X;
	public int Y;
	
	public Portal (Rectangle area, int X, int Y) {
		
		this.area = area;
		this.X = X;
		this.Y = Y;
	}
	
	public void checkTeleport(Player p) {
		if (p.getBounds().intersects(area)) {
			
			p.x = X;
			p.y = Y;
		}
	}
	

}
