package game;

import entities.*;
import java.util.ArrayList;

public class Level {
	
	//player starting point
	public int p1StartX, p1StartY;
    public int p2StartX, p2StartY;
    
    public Door door;
    public Bottom bottom;
    public Hazard firePool;
    public Hazard waterPool;
    public Hazard greenPool;
    
    public ArrayList<Floor> floors = new ArrayList<>();
    public ArrayList<Floor> iceFloor = new ArrayList<>();
    public ArrayList<Floor> openWall = new ArrayList<>();
    public ArrayList<Coin> coins = new ArrayList<>();
 // if you want to add coin you need to use this coins.add(new Coin(new
 		// Rectangle(x, y, width, height)));
	public ArrayList<Portal> portal = new ArrayList<>();
	public ArrayList<BouncePad> bouncePad = new ArrayList<>(); 

}
