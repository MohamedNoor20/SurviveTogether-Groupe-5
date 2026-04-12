package griffith;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;

public class CoinTest {
	
	Player player;
	Coin coin;
	
	@Test
    void testCoinCollectionStatus() {
		player = new Player(100, 100, Type.FIRE);
		coin = new Coin(new Rectangle(100, 100, 20, 20)); 
		// the coin should not be collected before the player touch it 
		assertFalse(coin.isCollected, "Coin not collected.");
		//now we will make the player touch the coin and it should change 
		coin.checkCollision(player);
        assertTrue(coin.isCollected, "Coin is collected.");
		
		
	}
	
	@Test
    void testPlayerScoreIncreases() {
		player = new Player(100, 100, Type.FIRE);
		coin = new Coin(new Rectangle(100, 100, 20, 20));
		// test when the game starts the score should be 0
        assertEquals(0, player.score, "Player score should be 0.");
        // if the player collects one coin the score should increase by one  
        coin.checkCollision(player);
        assertEquals(1, player.score, "Player score should increase by 1.");

    }
}
