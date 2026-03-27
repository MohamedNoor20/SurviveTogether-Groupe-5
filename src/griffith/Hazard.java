package griffith;

import java.awt.Rectangle;

public class Hazard {
    Rectangle area;
    Type type; 

    public Hazard(Rectangle area, Type type) {
        this.area = area;
        this.type = type;
    }

   
    public void check(Player p) {
        if (area.intersects(new Rectangle(p.x, p.y, 40, 40))) {
            if (p.type != this.type) {
                p.alive = false;
            }
        }
    }
}