package griffith;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Level2Block {
    
    public static final int BLOCK_WIDTH = 31;
    public static final int BLOCK_HEIGHT = 32;
    
    private Image img;
    private Rectangle blockRect;
    private int x, y, w, h;
    
    public Level2Block() {
        img = new ImageIcon("src/static/image/elements/block.png").getImage();
    }
    
    public Level2Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = BLOCK_WIDTH;
        this.h = BLOCK_HEIGHT;
        this.blockRect = new Rectangle(x, y, w, h);
        img = new ImageIcon("src/static/image/elements/block.png").getImage();
    }
    
    public Level2Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.blockRect = new Rectangle(x, y, w, h);
        img = new ImageIcon("src/static/image/elements/block.png").getImage();
    }
    
    public Image getImage() { return img; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public Rectangle getRectangle() { return blockRect; }
}