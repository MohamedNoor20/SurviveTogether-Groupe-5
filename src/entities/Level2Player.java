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
import java.awt.Toolkit;

enum State { LEFT, RIGHT, FRONT }

public class Level2Player {

    private int x, y;
    private int speed = 5;
    private int jumpStrength = 12;
    private int gravity = 1;
    private int verticalSpeed = 0;

    private boolean left, right;
    private boolean onGround = true;
    private boolean alive = true;

    private int width = 48;
    private int height = 59;

    private State state = State.FRONT;

    private Image idleImg;
    private Image leftImg;
    private Image rightImg;
    private Image dieImg;

    public Level2Player(int x, int y, String idlePath, String leftImgPath, String rightImgPath, String diePath) {
        this.x = x;
        this.y = y;
        Toolkit tk = Toolkit.getDefaultToolkit();
        idleImg  = tk.getImage(idlePath);
        leftImg  = tk.getImage(leftImgPath);
        rightImg = tk.getImage(rightImgPath);
        dieImg   = tk.getImage(diePath);
    }

    public void setLeft(boolean v)  { left = v; }
    public void setRight(boolean v) { right = v; }

    public void jump() {
        if (onGround) {
            verticalSpeed = -jumpStrength;
            onGround = false;
        }
    }

    public void update() {
        if (!alive) return;

        if (left)  { x -= speed; state = State.LEFT; }
        if (right) { x += speed; state = State.RIGHT; }
        if (!left && !right) state = State.FRONT;

        verticalSpeed += gravity;
        y += verticalSpeed;
    }

    public void die() { alive = false; }

    public Image getImage() {
        if (!alive)            return dieImg;
        if (state == State.LEFT)  return leftImg;
        if (state == State.RIGHT) return rightImg;
        return idleImg;
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 8, y + 8, width - 16, height - 16);
    }

    public int getX()  { return x; }
    public int getY()  { return y; }
    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public boolean isAlive() { return alive; }

    public void setY(int y)             { this.y = y; }
    public void setVerticalSpeed(int v) { verticalSpeed = v; }
    public void setOnGround(boolean v)  { onGround = v; }
    public boolean isOnGround()         { return onGround; }
    public int getVerticalSpeed()       { return verticalSpeed; }
}
*/