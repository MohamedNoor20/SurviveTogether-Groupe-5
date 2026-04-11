package griffith;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;
//Tests all game logic (Susan Ogozi)
public class GameLogicTest {
    // this is test 1 to check if fire kills water (susan ogozi)
    @Test
    void fireKillsWater() {
        Player water = new Player(0, 0, Type.WATER);  //water girl at the hazard (susan ogozi)    
        Hazard fire = new Hazard(new Rectangle(0, 0, 50, 50), Type.FIRE); // the fire pool (susan ogozi)
        
        fire.check(water); //runs the collision check (susan ogozi)
        assertFalse(water.alive);  // must be dead (susan ogozi)
    }
    //test 2 to check if fire does not kill fireboy (susan ogozi)
    @Test
    void fireDoesNotKillFireboy() {
        Player fireboy = new Player(0, 0, Type.FIRE); //fire boy at hazard (susan ogozi)
        Hazard fire = new Hazard(new Rectangle(0, 0, 50, 50), Type.FIRE);
        
        fire.check(fireboy); //  collision but safe (susan ogozi)
        assertTrue(fireboy.alive);  // stays alive (susan ogozi)
    }
    @Test
    //test 3 to  check if water kills fireboy (susan ogozi)
    void waterKillsFireboy() {  
        Player fireboy = new Player(0, 0, Type.FIRE); //fireboy in water (susan ogozi)
        Hazard water = new Hazard(new Rectangle(0, 0, 50, 50), Type.WATER); // water pool (susan ogozi)
        water.check(fireboy); //wrong type death (susan ogozi)
        assertFalse(fireboy.alive);  // dies (susan ogozi)

    }
    @Test
    //test 4 to check if water does not kill water girl (susan ogozi)
    void waterDoesNotKillWatergirl() {
        Player watergirl = new Player(0, 0, Type.WATER); //water girl in water(susan ogozi)
        Hazard water = new Hazard(new Rectangle(0, 0, 50, 50), Type.WATER);
        water.check(watergirl); // correct type safe (susan ogozi)
        assertTrue(watergirl.alive); // Stays alive (susan ogozi)
    }
    @Test
    //test 5 condition for when both players are inside the door(susan ogozi)
    void testWinCondition() {
        Player fireboy = new Player(10, 10, Type.FIRE);// both inside door (susan ogozi)
        Player watergirl = new Player(10, 10, Type.WATER);
        Door door = new Door(new Rectangle(0, 0, 50, 50)); // door area (susan ogozi)
        assertTrue(door.isInside(fireboy) && door.isInside(watergirl)); // if the conditions are met Win game (susan ogozi)
    }
    @Test
    //test 6 if player outside the door no win (susan ogozi)
    void testPlayerOutsideDoor() {
        Player fireboy = new Player(100, 100, Type.FIRE); // far outside the door (susan ogozi)
        Door door = new Door(new Rectangle(0, 0, 50, 50)); // door 0-50 (susan ogozi)
        assertFalse(door.isInside(fireboy)); //outside false (susan ogozi)
    
    }
    @Test
    //test 7 no collision player is safe (susan ogozi)
    void testNoCollisionNoDeath() {
        Player fireboy = new Player(100, 100, Type.FIRE); //far from hazard (susan ogozi)
        Hazard fire = new Hazard(new Rectangle(0, 0, 50, 50), Type.FIRE); // hazard 0-50
        fire.check(fireboy); //no overlap (susan ogozi)
        assertTrue(fireboy.alive); //safe during normal movement  (susan ogozi)
    }
    @Test
    //test 8 to test if player 
    void testIceFloorFlag() {
        Player fireboy = new Player(0,0,Type.FIRE);
        Hazard iceFloor = new Hazard(new Rectangle(0,0,50,50), Type.ICE);
        iceFloor.check(fireboy);
        assertTrue(fireboy.onIce);  
    }
    @Test
 // test 9 checks if GREEN hazard kills fireboy (susan ogozi)
    void testGreenKillsFireboy() {
        Player fireboy = new Player(0, 0, Type.FIRE);
        Hazard green = new Hazard(new Rectangle(0, 0, 50, 50), Type.GREEN);
        green.check(fireboy);
        assertFalse(fireboy.alive); //green kills fire boy(susan ogozi)
}
    @Test
 // test 10  checks if GREEN hazard kills watergirl (susan ogozi)
    void testGreenKillsWatergirl() {
        Player watergirl = new Player(0, 0, Type.WATER); //water girl at green harzard (susan ogozi)
        Hazard green = new Hazard(new Rectangle(0, 0, 50, 50), Type.GREEN); //green pool(susan ogozi)
        green.check(watergirl);
        assertFalse(watergirl.alive); //green kills water girl(susan ogozi)
    }
    @Test
 // test 11 checks if watergirl gets onIce flag when touching ice floor (susan ogozi)
    //movement class uses this flag to slow watergirl down vice versa for fire boy (susan ogozi)
    void testIceFloorFlagWatergirl() {
        Player watergirl = new Player(0, 0, Type.WATER);
        Hazard iceFloor = new Hazard(new Rectangle(0, 0, 50, 50), Type.ICE);
        iceFloor.check(watergirl);
        assertTrue(watergirl.onIce); 
    }
    @Test
 // test 12 checks watergirl outside door and returns false  (susan ogozi)
    void testPlayerOutsideDoorWatergirl() {
        Player watergirl = new Player(100, 100, Type.WATER); 
        Door door = new Door(new Rectangle(0, 0, 50, 50));
        assertFalse(door.isInside(watergirl)); 
    }
    @Test
 // test 13  checks watergirl stays alive when not touching hazard (susan ogozi)
    void testNoCollisionNoDeathWatergirl() {
        Player watergirl = new Player(100, 100, Type.WATER);
        Hazard water = new Hazard(new Rectangle(0, 0, 50, 50), Type.WATER);
        water.check(watergirl); 
        assertTrue(watergirl.alive);
}
    @Test
 // test 14  checks timer is running after start is called (susan ogozi)
    void testTimerStartsRunning() {
        Timer t = new Timer(); //create timer (susan ogozi)
        t.start(); // start the timer (susan ogozi)
        assertTrue(t.running); //timer must be running (susan ogozi)
}
    @Test
 // test 15  checks timer stops after stop is called (susan ogozi)
    void testTimerStops() {
        Timer t = new Timer();
        t.start();// start the timer (susan ogozi)
        t.stop();// stop the timer (susan ogozi)
        assertFalse(t.running);//timer must be stopped (susan ogozi)
}
    @Test
 // test 16  checks timer is not running before start is called (susan ogozi)
    void testTimerNotRunningAtStart() {
        Timer t = new Timer(); // create timer (susan ogozi)
        assertFalse(t.running); // not started yet (susan ogozi)
}
}