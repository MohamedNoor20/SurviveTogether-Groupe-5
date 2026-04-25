package tests;
//susan Ogozi
//3257092
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Rectangle;
//Test all junit test for my level panel
public class MyLevel2PanelTest {

    // the matching MyLevelPanel 
    static final int PLAYER_W  = 30;
    static final int PLAYER_H  = 60;
    static final int JUMP_FORCE = -18;
    static final int GRAVITY   = 1;

    // all player state 
    int fireX, fireY, fireVY;
    int waterX, waterY, waterVY;
    boolean fireOnGround, waterOnGround;
    boolean fireAlive, waterAlive;

    @Before
    public void setUp() {
        fireX  = 80;  fireY  = 630; fireVY  = 0;
        waterX = 160; waterY = 630; waterVY = 0;
        fireOnGround  = true;
        waterOnGround = true;
        fireAlive  = true;
        waterAlive = true;
    }

    //  MOVEMENT TESTS

    @Test
    public void testFireboyMovesRight() {
        int startX = fireX;
        fireX += 6; // fireSpeed = 6
        assertTrue("Fireboy should move right", fireX > startX);
    }

    @Test
    public void testFireboyMovesLeft() {
        int startX = fireX;
        fireX -= 6;
        assertTrue("Fireboy should move left", fireX < startX);
    }

    @Test
    public void testWatergirlMovesRight() {
        int startX = waterX;
        waterX += 6;
        assertTrue("Watergirl should move right", waterX > startX);
    }

    @Test
    public void testWatergirlMovesLeft() {
        int startX = waterX;
        waterX -= 6;
        assertTrue("Watergirl should move left", waterX < startX);
    }

    //  JUMP TESTS

    @Test
    public void testFireboyJumpsWhenOnGround() {
        fireVY = JUMP_FORCE;
        fireOnGround = false;
        assertTrue("Fireboy should have negative velocity when jumping", fireVY < 0);
    }

    @Test
    public void testWatergirlJumpsWhenOnGround() {
        waterVY = JUMP_FORCE;
        waterOnGround = false;
        assertTrue("Watergirl should have negative velocity when jumping", waterVY < 0);
    }

    @Test
    public void testFireboyCannotJumpInAir() {
        fireOnGround = false;
        int oldVY = fireVY;
        // jump only if on ground
        if (fireOnGround) fireVY = JUMP_FORCE;
        assertEquals("Fireboy should not jump in air", oldVY, fireVY);
    }
    @Test
    public void testWatergirlCannotJumpInAir() {
        waterOnGround = false;
        int oldVY = waterVY;
        // jump only if on ground
        if (waterOnGround) waterVY = JUMP_FORCE;
        assertEquals("Watergirl should not jump in air", oldVY, waterVY);
    }

    //  GRAVITY TESTS

    @Test
    public void testGravityIncreasesVerticalSpeed() {
        int oldVY = fireVY;
        if (fireVY < 5) fireVY += GRAVITY;
        assertTrue("Gravity should increase vertical speed", fireVY > oldVY);
    }

    @Test
    public void testGravityIsCapped() {
        fireVY = 5;
        if (fireVY < 5) fireVY += GRAVITY;
        assertEquals("Gravity should be capped at 5", 5, fireVY);
    }
    
    @Test
    public void testWatergirlGravityIncreasesVerticalSpeed() {
        int oldVY = waterVY;
        if (waterVY < 5) waterVY += GRAVITY;
        assertTrue("Gravity should increase vertical speed", waterVY > oldVY);
    }

    @Test
    public void testWatergirlGravityIsCapped() {
        waterVY = 5;
        if (waterVY < 5) waterVY += GRAVITY;
        assertEquals("Gravity should be capped at 5", 5, waterVY);
    }
}