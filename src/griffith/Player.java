package griffith;

/**
 * Temporary Player for Person 4 tests(susan ogozi)
 */
public class Player {
	public int x, y; // the position (susan ogozi)
	public Type type; // Fire and water type (susan ogozi)
	public boolean alive = true;

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
		this.x -= 1;
	}

	public void moveRight(){
		this.x += 1;
	}

}