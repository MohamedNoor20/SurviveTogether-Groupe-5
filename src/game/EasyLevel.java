package game;

import java.awt.Rectangle;
import entities.*;

public class EasyLevel extends Level {
    public EasyLevel() {
        p1StartX = 50; p1StartY = 670;
        p2StartX = 130; p2StartY = 670;

        firePool = new Hazard(new Rectangle(210, 695, 80, 5), Type.FIRE);
        waterPool = new Hazard(new Rectangle(430, 695, 80, 5), Type.WATER);
        greenPool = new Hazard(new Rectangle(550, 365, 80, 5), Type.GREEN);

        door = new Door(new Rectangle(10, 40, 60, 80));
        bottom = new Bottom(new Rectangle(700, 375, 10, 10));

        coins.add(new Coin(new Rectangle(350, 680, 20, 20)));
        coins.add(new Coin(new Rectangle(150, 500, 20, 20)));
        coins.add(new Coin(new Rectangle(210, 160, 30, 30)));

        floors.add(new Floor(new Rectangle(0, 715, 768, 10)));
        floors.add(new Floor(new Rectangle(685, 630, 10, 95)));
        floors.add(new Floor(new Rectangle(685, 630, 83, 10)));
        floors.add(new Floor(new Rectangle(0, 540, 150, 10)));
        floors.add(new Floor(new Rectangle(83, 455, 10, 95)));
        floors.add(new Floor(new Rectangle(0, 455, 83, 10)));
        iceFloor.add(new Floor(new Rectangle(150, 540, 490, 10)));
        floors.add(new Floor(new Rectangle(123, 385, 645, 10)));
        floors.add(new Floor(new Rectangle(350, 300, 10, 95)));
        floors.add(new Floor(new Rectangle(350, 300, 83, 10)));
        floors.add(new Floor(new Rectangle(433, 300, 10, 95)));
        floors.add(new Floor(new Rectangle(123, 210, 187, 10)));
        openWall.add(new Floor(new Rectangle(123, 120, 10, 90)));
        openWall.add(new Floor(new Rectangle(300, 120, 10, 90)));
        floors.add(new Floor(new Rectangle(475, 210, 187, 10)));
        floors.add(new Floor(new Rectangle(0, 120, 475, 10)));
    }
}
