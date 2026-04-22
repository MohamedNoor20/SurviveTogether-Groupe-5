package griffith;

import java.awt.Rectangle;

public class Level2Player {

    public int x, y;
    public int width = 48;
    public int height = 59;

    public int velocityX = 0;
    public int velocityY = 0;

    public boolean jumping = false;

    public Level2Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += velocityX;
        y += velocityY;
    }

    public void jump() {
        if (!jumping) {
            velocityY = -12;
            jumping = true;
        }
    }

    public void applyGravity() {
        velocityY += 1;
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 8, y + 8, width - 16, height - 14);
    }
}