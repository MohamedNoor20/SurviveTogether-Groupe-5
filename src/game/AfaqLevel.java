package game;

import java.awt.Rectangle;
import entities.*;

public class AfaqLevel extends Level {
    
    public AfaqLevel() {
        
        //players position
        p1StartX = 50;
        p1StartY = 600;
        p2StartX = 120;
        p2StartY = 600;
        
        //door position
        door = new Door(new Rectangle(350, 100, 60, 80));
        
        //main path
        // Ground floor
        floors.add(new Floor(new Rectangle(0, 700, 768, 20)));
        
        //starting
        floors.add(new Floor(new Rectangle(0, 600, 200, 20)));
        floors.add(new Floor(new Rectangle(250, 600, 180, 20)));
        floors.add(new Floor(new Rectangle(480, 600, 150, 20)));
        floors.add(new Floor(new Rectangle(680, 600, 88, 20)));
        
        //mid level
        floors.add(new Floor(new Rectangle(50, 500, 120, 20)));
        floors.add(new Floor(new Rectangle(300, 500, 120, 20)));
        floors.add(new Floor(new Rectangle(550, 500, 120, 20)));
        
        //upper level
        floors.add(new Floor(new Rectangle(100, 380, 120, 20)));
        floors.add(new Floor(new Rectangle(350, 380, 120, 20)));
        floors.add(new Floor(new Rectangle(600, 380, 120, 20)));
        
        //door level
        floors.add(new Floor(new Rectangle(200, 250, 120, 20)));
        floors.add(new Floor(new Rectangle(450, 250, 120, 20)));
        floors.add(new Floor(new Rectangle(300, 150, 150, 20)));
        
        //path for coins
        floors.add(new Floor(new Rectangle(650, 200, 80, 20)));
        floors.add(new Floor(new Rectangle(30, 300, 80, 20)));
        

       
        //slippry ice
        iceFloor.add(new Floor(new Rectangle(250, 600, 60, 20)));
        iceFloor.add(new Floor(new Rectangle(550, 500, 60, 20)));
        iceFloor.add(new Floor(new Rectangle(600, 380, 60, 20)));
        
        
        //hazzards
        //Fire hazard
        firePool = new Hazard(new Rectangle(300, 586, 60, 14), Type.FIRE);
        firePool2 = new Hazard(new Rectangle(350, 486, 60, 14), Type.FIRE);
        firePool3 = new Hazard(new Rectangle(630, 366, 60, 14), Type.FIRE);
        
        //Water hazards
        waterPool = new Hazard(new Rectangle(520, 586, 60, 14), Type.WATER);
        waterPool2 = new Hazard(new Rectangle(580, 486, 60, 14), Type.WATER);
        waterPool3 = new Hazard(new Rectangle(250, 366, 60, 14), Type.WATER);
        
        //Green hazard
        greenPool = new Hazard(new Rectangle(680, 586, 60, 14), Type.GREEN);
        greenPool2 = new Hazard(new Rectangle(450, 366, 60, 14), Type.GREEN);
        
        //bounce pads
        bouncePad.add(new BouncePad(new Rectangle(200, 580, 40, 15)));
        bouncePad.add(new BouncePad(new Rectangle(550, 480, 40, 15)));
        bouncePad.add(new BouncePad(new Rectangle(150, 360, 40, 15)));
        bouncePad.add(new BouncePad(new Rectangle(450, 230, 40, 15)));
        
        //portals
        portal.add(new Portal(new Rectangle(680, 580, 40, 20), 50, 480));
        portal.add(new Portal(new Rectangle(30, 480, 40, 20), 650, 580));
        
        
        
        //coins
        //starting
        coins.add(new Coin(new Rectangle(80, 560, 20, 20)));
        coins.add(new Coin(new Rectangle(400, 560, 20, 20)));
        coins.add(new Coin(new Rectangle(600, 560, 20, 20)));
        
        //Mid level coins
        coins.add(new Coin(new Rectangle(100, 460, 20, 20)));
        coins.add(new Coin(new Rectangle(350, 460, 20, 20)));
        coins.add(new Coin(new Rectangle(600, 460, 20, 20)));
        
        //Upper level coins
        coins.add(new Coin(new Rectangle(150, 340, 20, 20)));
        coins.add(new Coin(new Rectangle(400, 340, 20, 20)));
        coins.add(new Coin(new Rectangle(650, 340, 20, 20)));
        
        //left and right area coins
        coins.add(new Coin(new Rectangle(680, 160, 20, 20)));
        coins.add(new Coin(new Rectangle(60, 260, 20, 20)));
        
        // Door area coins
        coins.add(new Coin(new Rectangle(250, 210, 20, 20)));
        coins.add(new Coin(new Rectangle(500, 210, 20, 20)));
        coins.add(new Coin(new Rectangle(350, 110, 20, 20)));
        }
    
    //Additional hazard variables
    public Hazard firePool2;
    public Hazard firePool3;
    public Hazard waterPool2;
    public Hazard waterPool3;
    public Hazard greenPool2;
    }