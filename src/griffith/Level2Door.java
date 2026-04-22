package griffith;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Level2Door {
    
    public static final int DOOR_WIDTH = 56;
    public static final int DOOR_HEIGHT = 64;
    private int state;
    
    private Image img;
    private int x, y, w, h;
    
    public Level2Door() {}
    
    public Level2Door(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.w = DOOR_WIDTH;
        this.h = DOOR_HEIGHT;
        this.state = state;
        setImage();
    }
    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}