package entities;

import java.awt.Rectangle;

public class BouncePad {
	
    public Rectangle area;
    public int bouncePower = -22; 

    public BouncePad(Rectangle area) {
        this.area = area;
    }
    
    public void checkBounce(Player p) {
        // it checks if the player touches the bounce pad to make him jump
        if (p.getBounds().intersects(area)) {
            p.yVelocity = bouncePower;
            p.isJumping = true;
        }
    }
	

}
