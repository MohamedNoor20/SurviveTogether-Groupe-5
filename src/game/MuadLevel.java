package game;

import entities.Door;
import entities.Floor;
import java.awt.Rectangle;

public class MuadLevel extends Level{


public MuadLevel() {
    p1StartX = 50;
    p1StartY = 650;
    p2StartX = 120;
    p2StartY = 650;
    
    
    floors.add(new Floor(new Rectangle(0, 715, 768, 40))); 

	}
}