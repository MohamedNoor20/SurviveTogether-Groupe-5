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
}
