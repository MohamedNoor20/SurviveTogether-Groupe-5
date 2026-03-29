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

     
    //return the fireboy x position from player1 object
    public int getFireboyX() {
        return player1.x;
        }

    //return the fireboy y position from player1 object
    public int getFireboyY() {
        return player1.y;
        }

    //returns watergirl x position from player2 object
    public int getWatergirlX() {
        return player2.x;
        }

    //returns watergirl y position from player2 object
    public int getWatergirlY() {
        return player2.y;
        }

    //returns whether fireboy is alive
    public boolean isFireboyAlive() {
        return player1.alive;
        }

    //returns whether watergirl is alive
    public boolean isWatergirlAlive() {
        return player2.alive;
        }

    //returns fireboy color
    public java.awt.Color getFireboyColor() {
        return java.awt.Color.RED;
        }

    //returns watergirl color
      public java.awt.Color getWatergirlColor() {
          return java.awt.Color.BLUE;
          }      

    
    
    
      //implementing draw method stubs one by one
      
      //fireboy
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
      

      //watergirl
      public void drawWatergirl(java.awt.Graphics g) {
    	  //this will stop from drawing if the player is dead
    	  if (!player2.alive) return;
    	  //fire girl position
    	  int x = player2.x;
    	  int y = player2.y;
    	  
    	  //firegirl body
    	  g.setColor(java.awt.Color.BLUE);
    	  g.fillRect(x, y, 40, 40);
    	  
    	  //firegirl outline
    	  g.setColor(java.awt.Color.CYAN);
    	  g.drawRect(x, y, 40, 40);
    	  
    	  // label
    	  g.setColor(java.awt.Color.WHITE);
    	  g.drawString("W", x + 15, y + 25);
    	  
      }

      public void drawHazards(java.awt.Graphics g) {
    	    //placeholder for hazard drawing
    	  }

      public void drawDoor(java.awt.Graphics g) {
    	    //placeholder for door drawing
    	  }
      
      
      @Override
      protected void paintComponent(java.awt.Graphics g) {
    	  //it clears the screen and prepares the canvas
          super.paintComponent(g);
          //rendering
          drawFireboy(g);
          drawWatergirl(g);
          drawHazards(g);
          drawDoor(g);
          }
      }