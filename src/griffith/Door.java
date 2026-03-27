package griffith;

import java.awt.Rectangle;

public class Door { //
      Rectangle area;

    public Door(Rectangle area) {
        this.area = area;
    }
    public boolean isInside(Player p) {
        return area.intersects(new Rectangle(p.x, p.y, 40, 40));
}
}