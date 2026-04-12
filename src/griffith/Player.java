package griffith;

import java.awt.Rectangle;

/**
 * Temporary Player for Person 4 tests(susan ogozi)
 */
public class Player {
	public int x, y; // the position (susan ogozi)
	public Type type; // Fire and water type (susan ogozi)
	public boolean alive = true;

	public int yVelocity = 0;
	public int gravity = 1;
	public boolean isJumping = false;
	public int jumpPower = -15;
	public int playerSize = 50;
	public int screen = 768;

	// Mohamed
	public int width = 40;
	public int height = 46;

	// create the player
	public Player(int x, int y, Type type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public void moveUp() {
		if (!isJumping) {
			this.yVelocity = jumpPower;
			this.isJumping = true;
		}
	}

	public void moveDown() {
		this.yVelocity += 1;
	}

	public void moveLeft() {
		if (this.x - 1 > 0) {
			this.x -= 5;
		} else {
			this.x -= 0;
		}

	}

	public void moveRight() {
		if (this.x + 1 + playerSize <= screen) {
			this.x += 5;
		} else {
			this.x += 0;
		}
	}
	// Mohamed
	public void gravity() {
	    // Apply gravity to velocity
	    this.yVelocity += gravity;
	    // Apply velocity to position
	    this.y += yVelocity;
	}


	// Mohamed
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

}