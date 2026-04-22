package griffith;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Level2Obstacle {
    
    public static final int OBSTACLE_WIDTH = 124;
    public static final int OBSTACLE_HEIGHT = 32;
    private int state;
    
    private Image img;
    private Rectangle obstacleRect;
    private int x, y, w, h;
    
    public Level2Obstacle() {}
    
    public Level2Obstacle(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.w = OBSTACLE_WIDTH;
        this.h = OBSTACLE_HEIGHT;
        this.state = state;
        this.obstacleRect = new Rectangle(x, y, w, h);
        setImage();
    }
    
    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
    
    public void setImage() {
        if (state < 0) {
            img = resizeImage(new ImageIcon("src/static/image/elements/poison_obstacle.png"), OBSTACLE_WIDTH, OBSTACLE_HEIGHT).getImage();
        } else if (state % 2 == 0) {
            img = resizeImage(new ImageIcon("src/static/image/elements/water_obstacle.png"), OBSTACLE_WIDTH, OBSTACLE_HEIGHT).getImage();
        } else {
            img = resizeImage(new ImageIcon("src/static/image/elements/fire_obstacle.png"), OBSTACLE_WIDTH, OBSTACLE_HEIGHT).getImage();
        }
    }
    
    public Image getImage() { return img; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public int getState() { return state; }
    public Rectangle getRectangle() { return obstacleRect; }
}