package griffith;
import java.awt.Rectangle; 
//Fire/Water death hazards (Susan Ogozi)
public class Hazard {
	 public Rectangle area; //hazard hit box
	 public Type type; // firee or water hazard (Susan Ogozi)
	 //creates the hazard pool (Susan Ogozi)
	public Hazard(Rectangle area, Type type) {
		this.area = area; // the position (Susan Ogozi)
		this.type = type; // the fire or water type (Susan Ogozi)
	}
	/*if player touches hazard area
	  Collision logic also player postion and type (Susan Ogozi)*/
public void check(Player p) {
	// Player 40x40 hitbox i.e sprite size (Susan Ogozi)
	 Rectangle playerBox = new Rectangle(p.x, p.y, 40, 40);
	 if (area.intersects(playerBox)) {
         System.out.println("TOUCH!");
         /*If the player touches the hazard the what happens to that player
          * Death logic (Susan Ogozi)*/
       if (p.type != this.type) {
	                p.alive = false;
	                System.out.println("DEAD!");
	            }
	           
	        }
	    
}
}
