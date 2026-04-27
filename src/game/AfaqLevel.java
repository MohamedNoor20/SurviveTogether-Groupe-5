package game;

import entities.*;
import java.awt.Rectangle;

import entities.*;

public class AfaqLevel extends Level{
	
	public AfaqLevel(){

    p1StartX = 50; p1StartY = 670;
    p2StartX = 130; p2StartY = 670;
    
    
    floors.add(new Floor(new Rectangle(0, 715, 768, 10)));
	}

}
