package griffith;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Level2Item {

    public static final int ITEM_WIDTH = 21;
    public static final int ITEM_HEIGHT = 21;

    private Image img;
    private int x, y, w, h;
    private int state;
    private boolean collected = false;

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

    public void setImage() {
        if (state % 2 == 0) {
            img = resizeImage(new ImageIcon("src/static/image/elements/water_gem.png"), ITEM_WIDTH, ITEM_HEIGHT).getImage();
        } else {
            img = resizeImage(new ImageIcon("src/static/image/elements/fire_gem.png"), ITEM_WIDTH, ITEM_HEIGHT).getImage();
        }
    }

    public void checkCollision(Level2Player player) {
        if (!collected && new Rectangle(x, y, w, h).intersects(player.getBounds())) {
            collected = true;
        }
    }

    public boolean isCollected() { return collected; }
    public Image getImage() { return img; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public int getState() { return state; }
}