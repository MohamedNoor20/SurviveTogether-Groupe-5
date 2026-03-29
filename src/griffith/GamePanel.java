package griffith;

import java.awt.Rectangle;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    Player player1;
    Player player2;

    Hazard firePool;
    Hazard waterPool;

    Door door;
    // Mohamed
    public GamePanel() {
        setFocusable(true);

        // Create players
        player1 = new Player(50, 50, Type.FIRE);
        player2 = new Player(50, 150, Type.WATER);

        // Create hazards
        firePool = new Hazard(new Rectangle(300, 100, 100, 50), Type.FIRE);
        waterPool = new Hazard(new Rectangle(300, 100, 100, 50), Type.WATER);

        // Create door
        door = new Door(new Rectangle(600, 150, 50, 80));
    }
    // Mohamed
    // This runs the frame
    public void updateGame() {

        // This will call Susan's logic
        firePool.check(player1);
        firePool.check(player2);

        waterPool.check(player1);
        waterPool.check(player2);

        // Win condition
        if (door.isInside(player1) && door.isInside(player2)) {
            System.out.println("YOU WIN!");
        }

        // Lose condition
        if (!player1.alive || !player2.alive) {
            System.out.println("GAME OVER");
        }
    }
      //Afaq Ahmed
      //Graphics methods stubs

      //this returns the fireboy x-axis position
      public int getFireboyX() {
          return 50;
          }

      //this returns the fireboy y-axis position
      public int getFireboyY() {
          return 50;
          }

      //this returns the watergirl x-axis position
      public int getWatergirlX() {
          return 50;
          }

      //this returns the watergirl y-axis position
      public int getWatergirlY() {
          return 150;
          }

      //this will return if fireboy is alive
      public boolean isFireboyAlive() {
          return true;
          }

      //this will return if watergirl is alive
      public boolean isWatergirlAlive() {
          return true;
          }

      //this will return fireboy color
      public java.awt.Color getFireboyColor() {
          return java.awt.Color.RED;
          }

      //this will return watergirl color
      public java.awt.Color getWatergirlColor() {
          return java.awt.Color.BLUE;
          }
      
      
      
      
      //adding draw method stubs
      
      public void drawFireboy(java.awt.Graphics g) {
    	  //this will stop from drawing if the player is dead
    	  if (!player1.alive) return;
    	  //fire boy positions
    	  int x = player1.x;
    	  int y = player1.y;
    	  
    	  //body of fireboy
    	  g.setColor(java.awt.Color.RED);
    	  g.fillRect(x, y, 40, 40);
    	  
    	  //outline of fireboy
    	  g.setColor(java.awt.Color.ORANGE);
    	  g.drawRect(x, y, 40, 40);
    	  //label 
    	  g.setColor(java.awt.Color.WHITE);
    	  g.drawString("F", x + 15, y + 25);
    	  
      }

      public void drawWatergirl(java.awt.Graphics g) {
    	    //draws watergirl
    	  }

      public void drawHazards(java.awt.Graphics g) {
    	    //placeholder for hazard drawing
    	  }

      public void drawDoor(java.awt.Graphics g) {
    	    //placeholder for door drawing
    	  }
      }