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
        
         /*If the player touches the hazard the what happens to that player
          * Death logic  (Susan Ogozi)*/
       if ( this.type == Type.GREEN || p.type != this.type) { // this is the green poison players die when they enter this green pool (susan ogozi)
    		   p.alive = false;  //they are not alive if they enter the pool(susan ogozi)
	            }
	        
	 if (this.type == Type.ICE) { //this just flags that players on ice stay alive(susan ogozi) 
		 p.onIce = true; // to tell if they are on the ice or not 
	 }
}
else {
	p.onIce = false; 
}
}
}