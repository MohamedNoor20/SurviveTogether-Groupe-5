package griffith;
import java.awt.Rectangle; 

public class Hazard {
	 public Rectangle area;
	 public Type type;
	public Hazard(Rectangle area, Type type) {
		this.area = area;
		this.type = type;
	}
public void check(Player p) {
	 Rectangle playerBox = new Rectangle(p.x, p.y, 40, 40);
	 if (area.intersects(playerBox)) {
         System.out.println("TOUCH!");
}
}
}

