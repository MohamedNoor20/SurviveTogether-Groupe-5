package griffith;

import javax.swing.JFrame;

public class Main {
	// Moahmed
	public static void main(String[] args) throws InterruptedException {

        JFrame frame = new JFrame("Fireboy & Watergirl Mini");

        GamePanel panel = new GamePanel(); 

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        panel.requestFocusInWindow();
        
        boolean gameRun = true;

        // GAME LOOP
        while (gameRun) {
           boolean check =  panel.updateGame();  
                       
            if(check != false) {
            	panel.repaint();    // Afaq's drawing 
                Thread.sleep(16);   // ~60 FPS
            }
            
            else {
            	gameRun = false;
            	panel.repaint();    // Afaq's drawing 
                Thread.sleep(16); 
            }
        }
    }
}