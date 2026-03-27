package griffith;

/**
 * TEMP Player for Person 4 tests
 * Person 2 will replace with real one
 */
public class Player {
    public int x, y;
    public Type type;
    public boolean alive = true;
    
    public Player(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}