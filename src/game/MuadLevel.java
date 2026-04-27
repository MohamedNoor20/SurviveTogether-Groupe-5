package game;

import entities.*;
import java.awt.Rectangle;

public class MuadLevel extends Level{


public MuadLevel() {
    // 1. PLAYER SPAWNS (Bottom Left)
    p1StartX = 50; 
    p1StartY = 650;
    p2StartX = 120; 
    p2StartY = 650;


    door = new Door(new Rectangle(640,680, 60, 80));

 
    bottom = new Bottom(new Rectangle(100, 100, 80, 10)); 
    firePool = new Hazard(new Rectangle(100, 100, 80, 10), Type.FIRE);
    waterPool = new Hazard(new Rectangle(100, 100, 80, 10), Type.WATER);
    greenPool = new Hazard(new Rectangle(100, 100, 80, 10), Type.GREEN);
    iceFloor.add(new Floor(new Rectangle(150, 715, 490, 10)));
    
    floors.add(new Floor(new Rectangle(0, 715, 768, 40)));


    floors.add(new Floor(new Rectangle(0, 550, 300, 20)));


    floors.add(new Floor(new Rectangle(350, 420, 250, 20)));


    floors.add(new Floor(new Rectangle(50, 280, 200, 20)));


    floors.add(new Floor(new Rectangle(450, 160, 318, 20)));
    

    coins.add(new Coin(new Rectangle(150, 510, 20, 20)));
    coins.add(new Coin(new Rectangle(450, 380, 20, 20)));
    portal.add(new Portal(new Rectangle(600, 655, 40, 60), 450, 100));
    bouncePad.add(new BouncePad(new Rectangle(350, 700, 50, 15)));


	}
}





/*
 to add an open wall use this:
 
openWall.add(new Floor(new Rectangle(300, 120, 10, 90)));
---------------------------------------------------------

this to add normal floor:

floors.add(new Floor(new Rectangle(0, 715, 768, 40)));
---------------------------------------------------------

this to add ice floor:

iceFloor.add(new Floor(new Rectangle(150, 540, 490, 10)));
---------------------------------------------------------

this to add a door:

door = new Door(new Rectangle(640,680, 60, 80));
---------------------------------------------------------

this to add coins:

coins.add(new Coin(new Rectangle(150, 510, 20, 20)));
---------------------------------------------------------

this to add a button:

bottom = new Bottom(new Rectangle(100, 100, 80, 10)); 
---------------------------------------------------------

this to add fire pool or water pool or green pool:

firePool = new Hazard(new Rectangle(100, 100, 80, 10), Type.FIRE);
waterPool = new Hazard(new Rectangle(100, 100, 80, 10), Type.WATER);
greenPool = new Hazard(new Rectangle(100, 100, 80, 10), Type.GREEN);

*/



