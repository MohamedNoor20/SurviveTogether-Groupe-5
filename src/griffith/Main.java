package griffith;

import javax.swing.JFrame;

public class Main {
	
	private static JFrame frame;        // main window
	private static GamePanel gamePanel; // game screen
	private static MenuPanel menuPanel; // menu screen

	// Moahmed
	public static void main(String[] args) throws InterruptedException {

		frame = new JFrame("Fireboy & Watergirl Mini");

		 // create the menu screen
        menuPanel = new MenuPanel(this);
        
        // create the game screen (pass 'this' so it can call showMenu)
        gamePanel = new GamePanel(this);
        
        // show the menu first when program starts
        showMenu(false);

        frame.add(panel);
        frame.setSize(768, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Servive Together");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();

        gamePanel.requestFocusInWindow();
        
        boolean gameRun = true;
        
        gamePanel.startGame();

	}
	
		//switches from game to menu
		public void showMenu(boolean isGameOver) {
			//remove game screen if it is t   }
		    frame.add(menuPanel);
		    
		    if (isGameOver) {
		        menuPanel.showGameOverMenu();
	        }
		    //refresh the window
		    frame.revalidate();  
		    //redraw everything
		    frame.repaint();
		    // menu gets keyboard focus
		    menuPanel.requestFocusInWindow();
	    }
		
	    public void startGame() {
	        frame.remove(menuPanel);
	        
	        // create a brand new game panel (pass 'this')
	        gamePanel = new GamePanel(this);
	        frame.add(gamePanel);
	        frame.revalidate();
	        frame.repaint();
	        gamePanel.requestFocusInWindow();
	        
	        // start the game loop in a separate thread
	        Thread gameThread = new Thread(() -> {
	            boolean gameRun = true;
	            while (gameRun) {
	                // update game logic
	                boolean check = gamePanel.updateGame();
	                
	                if (check != false) {
	                    // redraw graphics
	                    gamePanel.repaint();
	                    try {
	                        // wait 16ms
	                        Thread.sleep(16);
	                        }
	                    catch (InterruptedException e) {
	                        e.printStackTrace();
	                        }
	                    } 
	                else {
	                    gameRun = false;
	                    gamePanel.repaint();
	                    try{
	                        Thread.sleep(16);
	                        }
	                    catch (InterruptedException e) {
	                        e.printStackTrace();
	                        }
	                    }
	                }
	            });
	        gameThread.start();
	        }
		
		// restart the game
	    public void restartGame() {
	        startGame();
	        }
		
		public static void main(String[] args) {
	        // create the Main object
	        new Main();
	        }
}

