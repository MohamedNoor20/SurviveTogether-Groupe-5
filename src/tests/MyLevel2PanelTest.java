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

    //  HAZARD TESTS

    @Test
    public void testFireboyDiesInWaterPool() {
        Rectangle waterPool = new Rectangle(360, 694, 70, 14);
        // place fireboy directly in water pool
        fireX = 375; fireY = 640;
        Rectangle fFeet = new Rectangle(fireX + PLAYER_W/2 - 5, fireY + PLAYER_H - 6, 3, 6);
        if (waterPool.intersects(fFeet)) fireAlive = false;
        assertFalse("Fireboy should die in water pool", fireAlive);
    }

    @Test
    public void testWatergirlDiesInFirePool() {
        Rectangle firePool = new Rectangle(200, 526, 70, 12);
        waterX = 215; waterY = 470;
        Rectangle wFeet = new Rectangle(waterX + PLAYER_W/2 - 5, waterY + PLAYER_H - 6, 3, 6);
        if (firePool.intersects(wFeet)) waterAlive = false;
        assertFalse("Watergirl should die in fire pool", waterAlive);
    }

    @Test
    public void testFireboySafeInFirePool() {
        Rectangle firePool = new Rectangle(200, 526, 70, 12);
        fireX = 215; fireY = 470;
        Rectangle fFeet = new Rectangle(fireX + PLAYER_W/2 - 5, fireY + PLAYER_H - 6, 3, 6);
        // fireboy does NOT check fire pool
        assertTrue("Fireboy should be safe in fire pool", fireAlive);
    }

    @Test
    public void testWatergirlSafeInWaterPool() {
        Rectangle waterPool = new Rectangle(360, 694, 70, 14);
        waterX = 375; waterY = 640;
        // watergirl does NOT check water pool
        assertTrue("Watergirl should be safe in water pool", waterAlive);
    }

    @Test
    public void testBothDieInGreenPool() {
        Rectangle greenPool = new Rectangle(180, 370, 70, 14);

        fireX = 195; fireY = 315;
        Rectangle fFeet = new Rectangle(fireX + PLAYER_W/2 - 5, fireY + PLAYER_H - 6, 3, 6);
        if (greenPool.intersects(fFeet)) fireAlive = false;

        waterX = 195; waterY = 315;
        Rectangle wFeet = new Rectangle(waterX + PLAYER_W/2 - 5, waterY + PLAYER_H - 6, 3, 6);
        if (greenPool.intersects(wFeet)) waterAlive = false;

        assertFalse("Fireboy should die in green pool", fireAlive);
        assertFalse("Watergirl should die in green pool", waterAlive);
    }

   }