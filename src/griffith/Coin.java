package griffith;
	 
	import java.awt.Rectangle;
public class Coin {
	public Rectangle area;
	public boolean isCollected = false;
	
	public Coin (Rectangle area) {
		this.area = area;
	}
	
	public void checkCollision (Player p) {
		if (!isCollected && area.intersects(p.getBounds())) {
			this.isCollected = true;
			p.score++;
		}
	}

}
