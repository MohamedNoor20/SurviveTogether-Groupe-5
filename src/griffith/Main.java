package griffith;

import javax.swing.JFrame;

public class Main {
	// Moahmed
	public static void main(String[] args) throws InterruptedException {

        JFrame frame = new JFrame("Fireboy & Watergirl Mini");

        GamePanel panel = new GamePanel(); 

        frame.add(panel);
        frame.setSize(768, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Servive Together");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();

        panel.requestFocusInWindow();
        
        boolean gameRun = true;
        
        panel.startGame();

    }
}