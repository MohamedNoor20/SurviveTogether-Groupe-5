package griffith;

import javax.swing.JFrame;

public class Main {
    
    private JFrame frame;        //main window
    private GamePanel gamePanel; //game screen
    private MenuPanel menuPanel; //menu screen
    
    //constructor to set up everything
    public Main() {
        //creates the main window
        frame = new JFrame("Fireboy & Watergirl");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //user wont be able to resize the window
        frame.setResizable(false);
        
        //create the menu screen
        menuPanel = new MenuPanel(this);
        
        //create the game screen
        gamePanel = new GamePanel();
        
        //show the menu first when program starts
        showMenu();
        
        //make the window visible
        frame.setVisible(true);
        }
    
    //switches from game to menu
    public void showMenu() {
    	//remove game screen if it is there
        frame.remove(gamePanel);
        //add menu screen
        frame.add(menuPanel);
        //refresh the window
        frame.revalidate();  
        //redraw everything
        frame.repaint();
        //menu gets keyboard focus
        menuPanel.requestFocusInWindow();
        }
    
    //starts a new game
    public void startGame() {
        frame.remove(menuPanel);
        
        //create a brand new game panel
        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
        
        //start the game loop in a separate thread
        Thread gameThread = new Thread(() -> {
            boolean gameRun = true;
            while (gameRun) {
            	  //update game logic
                boolean check = gamePanel.updateGame();
                
                if (check != false) {
                	//redraw graphics
                    gamePanel.repaint();
                    try {
                    	//wait 16ms
                        Thread.sleep(16);  
                        }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                    } 
                else {
                    gameRun = false;
                    gamePanel.repaint();
                    try {
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
    
    //restart the game
    public void restartGame() {
        startGame();
        }
    
    //main method
    public static void main(String[] args) {
    	//create the Main object
        new Main();
        }
    }