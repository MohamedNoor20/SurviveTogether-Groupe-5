package griffith;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Level2SwitchBlock {
    
    public static final int BLOCK_WIDTH = 93;
    public static final int BLOCK_HEIGHT = 16;
    private boolean isVisible = true;
    private Image img;
    private Rectangle blockRect;
    private int x, y, w, h;
    
    public Level2SwitchBlock() {}
    
    public Level2SwitchBlock(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = BLOCK_WIDTH;
        this.h = BLOCK_HEIGHT;
        this.blockRect = new Rectangle(x, y, w, h);
        setImage();
    }
    
    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
    
    public void setImage() {
        img = resizeImage(new ImageIcon("src/static/image/elements/switch_block2.png"), BLOCK_WIDTH, BLOCK_HEIGHT).getImage();
    }
    
    public void setVisible(boolean value) {
        isVisible = value;
    }
    
    public boolean isVisible() {
        return isVisible;
    }
    
    public Image getImage() { return img; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public Rectangle getRectangle() { return blockRect; }
}