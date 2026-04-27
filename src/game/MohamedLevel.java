package game;

import entities.*;
import java.awt.Rectangle;

import entities.*;

public class MohamedLevel extends Level{
	
	public MohamedLevel(){

    p1StartX = 10; p1StartY = 670;
    p2StartX = 205; p2StartY = 670;
    
    // fire
    floors.add(new Floor(new Rectangle(0, 715, 768, 10)));
    floors.add(new Floor(new Rectangle(165, 650, 10, 65)));
    floors.add(new Floor(new Rectangle(165, 650, 200, 10)));

    floors.add(new Floor(new Rectangle(120, 405, 10, 90)));
    floors.add(new Floor(new Rectangle(0, 405, 120, 10)));
    floors.add(new Floor(new Rectangle(120, 490, 90, 10)));
    closeWall.add(new Floor(new Rectangle(120, 490, 10, 75))); 
    floors.add(new Floor(new Rectangle(0, 565, 305, 10)));
    floors.add(new Floor(new Rectangle(0, 280, 200, 10)));
    
    // middle wall
    floors.add(new Floor(new Rectangle(365, 125, 10, 535)));
    

    	// water
    floors.add(new Floor(new Rectangle(465, 650, 10, 65)));
    floors.add(new Floor(new Rectangle(465, 650, 250, 10)));
    floors.add(new Floor(new Rectangle(365, 565, 200, 10)));
    floors.add(new Floor(new Rectangle(555, 490, 220, 10)));
    floors.add(new Floor(new Rectangle(365, 415, 130, 10)));
    floors.add(new Floor(new Rectangle(620, 415, 150, 10)));
    
    floors.add(new Floor(new Rectangle(365, 250, 403, 10)));
    
    floors.add(new Floor(new Rectangle(250, 125, 300, 10)));
    floors.add(new Floor(new Rectangle(100, 200, 100, 10)));
    
    floors.add(new Floor(new Rectangle(550, 0, 10, 135)));
    
    openWall.add(new Floor(new Rectangle(365, 660, 10, 65)));
    
    
    greenPool = new Hazard(new Rectangle(365, 230, 403, 5), Type.GREEN);
    

    bottom = new Bottom(new Rectangle(20, 555, 15, 10)); 
    bottom2 = new Bottom(new Rectangle(530, 705, 15, 10)); 

    portal.add(new Portal(new Rectangle(710, 355, 40, 60), 10, 220));
    portal.add(new Portal(new Rectangle(10, 345, 40, 60), 10, 220));
    portal.add(new Portal(new Rectangle(375, 355, 40, 60), 600, 50));
    
    door = new Door(new Rectangle(350, 50, 60, 80));
    
    
    
	}

}
