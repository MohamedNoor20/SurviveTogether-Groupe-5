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

    door = new Door(new Rectangle(650, 70, 60, 80));
    floors.add(new Floor(new Rectangle(0, 715, 300, 40)));
    floors.add(new Floor(new Rectangle(468, 715, 300, 40)));
    floors.add(new Floor(new Rectangle(0, 500, 200, 20)));
    floors.add(new Floor(new Rectangle(0, 280, 300, 20)));
    floors.add(new Floor(new Rectangle(450, 150, 318, 20)));
    iceFloor.add(new Floor(new Rectangle(250, 500, 400, 10)));
    greenPool = new Hazard(new Rectangle(300, 700, 168, 10), Type.GREEN);
    firePool = new Hazard(new Rectangle(300, 473, 80, 7), Type.FIRE);
    waterPool = new Hazard(new Rectangle(450, 473, 80, 7), Type.WATER);
    bouncePad.add(new BouncePad(new Rectangle(200, 700, 50, 15)));
    bouncePad.add(new BouncePad(new Rectangle(250, 270, 50, 15)));
    portal.add(new Portal(new Rectangle(600, 440, 40, 60), 50, 220));
    bottom = new Bottom(new Rectangle(150, 270, 80, 10)); 
    openWall.add(new Floor(new Rectangle(500, 60, 10, 90)));
    coins.add(new Coin(new Rectangle(330, 450, 20, 20)));
    coins.add(new Coin(new Rectangle(480, 450, 20, 20)));
    coins.add(new Coin(new Rectangle(100, 240, 20, 20)));
    coins.add(new Coin(new Rectangle(600, 110, 20, 20)));
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

the same thing for the others like Bounce Pad and Portals 

*/



