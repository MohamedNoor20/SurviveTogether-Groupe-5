package griffith;

/**
 * Temporary Player for Person 4 tests(susan ogozi)
 */
public class Player {
	public int x, y; // the position (susan ogozi)
	public Type type; // Fire and water type (susan ogozi)
	public boolean alive = true;
	public int yVelocity = 0;

	public int gravity = 1;
	public int jumpPower = 1;
	public int playerSize = 50;

	public int screen = 800;
	public int floor = 510;

	// create the player
	public Player(int x, int y, Type type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}


	public void moveUp(){
		this.y -= 1;
	}

	public void moveDown(){
		this.y += 1;
	}

	public void moveLeft(){
		if (this.x - 1 > 0){
			this.x -= 1;
		}
		this.x -= 0;

	}

	public void moveRight(){
		if (this.x + 1 + playerSize <= screen) {
            this.x += 1;
        } else {
		this.x += 0;
		}
	}

	

}