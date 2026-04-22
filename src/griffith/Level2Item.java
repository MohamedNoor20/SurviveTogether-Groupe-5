package griffith;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Level2Item {
    
    public static final int ITEM_WIDTH = 21;
    public static final int ITEM_HEIGHT = 21;
    private int state;
    
    private Image img;
    private int x, y, w, h;
    
    public Level2Item() {}
    
    public Level2Item(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.w = ITEM_WIDTH;
        this.h = ITEM_HEIGHT;
        this.state = state;
        setImage();
    }
    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}