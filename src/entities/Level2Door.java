package entities;
/*
 * 
 * (susan ogozi)
 */

/*
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Level2Door {

    public static final int DOOR_WIDTH = 56;
    public static final int DOOR_HEIGHT = 64;

    private Image img;
    private int x, y, w, h;
    private int state;

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

    public void setImage() {
        if (state % 2 == 0) {
            img = resizeImage(new ImageIcon("src/static/image/elements/water_door.png"), DOOR_WIDTH, DOOR_HEIGHT).getImage();
        } else {
            img = resizeImage(new ImageIcon("src/static/image/elements/fire_door.png"), DOOR_WIDTH, DOOR_HEIGHT).getImage();
        }
    }

    public boolean isPlayerInside(Level2Player player) {
        Rectangle doorRect = new Rectangle(x, y, w, h);
        return doorRect.intersects(player.getBounds());
    }

    public Image getImage() { return img; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public int getState() { return state; }
    public Rectangle getRectangle() { return new Rectangle(x, y, w, h); }
}
*/