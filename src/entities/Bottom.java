package entities;

import java.awt.Rectangle;

public class Bottom {
    private Rectangle area;   // hitbox (now private)

    public Bottom(Rectangle area) {
        this.area = area;
    }

    // Checks if a player is pressing the bottom (overlaps)
    public boolean press(Player p) {
        // Assuming player has x, y and size 40x40 (like in Door.isInside)
        return area.intersects(new Rectangle(p.x, p.y, 40, 40));
    }

    // Public getter for the area
    public Rectangle getArea() {
        return area;
    }
}