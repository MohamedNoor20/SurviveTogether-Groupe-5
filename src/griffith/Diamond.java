package griffith;
import java.awt.Rectangle;
public class Diamond {
	public Type type;            
    public int x, y;              
    public boolean collected = false;
    
    public Diamond(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y; 
}
    public boolean canCollect(Type playerType) {
        return this.type == playerType && !collected;
    }

    public void collect(Player p) {
        Rectangle playerBox = new Rectangle(p.x, p.y, 40, 40);
        Rectangle diamondBox = new Rectangle(x, y, 20, 20);

       
        if (diamondBox.intersects(playerBox) && canCollect(p.type)) {
            collected = true; 
}
    }
}