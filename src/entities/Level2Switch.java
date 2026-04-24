package entities;
/*
 * OLD MENU SYSTEM 
 * 
 * This class was used in the original version of the game.
 * It has been replaced by:
 *   - NewMainMenuPanel
 *   - DifficultyPanel
 *
 * This file is kept for reference to show project upgrade (.
 * (susan ogozi)
 */

/*
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Level2Switch {

    public static final int SWITCH_WIDTH = 30;
    public static final int SWITCH_HEIGHT = 12;

    private Image img;
    private int x, y, w, h;
    private Rectangle switchRect;
    private boolean isSwitchOn = false;
    private Level2SwitchBlock manageBlock;

    public Level2Switch() {}

    public Level2Switch(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = SWITCH_WIDTH;
        this.h = SWITCH_HEIGHT;
        this.switchRect = new Rectangle(x, y, w, h);
        setImage();
    }

    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public void setImage() {
        img = resizeImage(new ImageIcon("src/static/image/elements/switch.png"), SWITCH_WIDTH, SWITCH_HEIGHT).getImage();
    }

    public void setManageBlock(Level2SwitchBlock manageBlock) {
        this.manageBlock = manageBlock;
    }

    public void setSwitchState(boolean value) {
        this.isSwitchOn = value;
        manage();
    }

    public void manage() {
        if (manageBlock != null) {
            if (isSwitchOn) {
                manageBlock.setVisible(false);
            } else {
                manageBlock.setVisible(true);
            }
        }
    }

    public Image getImage() { return img; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public Rectangle getRectangle() { return switchRect; }
    public boolean isSwitchOn() { return isSwitchOn; }
}
*/