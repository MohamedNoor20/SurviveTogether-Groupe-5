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

   }