package griffith;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    
    //buttons for the menu
    private JButton startButton;
    private JButton restartButton;
    private JButton exitButton;
    //reference to main to switch screens
    private Main mainFrame;
    private boolean showGameOver = false;
    
    //constructor for the menu
    public MenuPanel(Main main) {
        this.mainFrame = main;
        setLayout(null);
        setBackground(new Color(18, 18, 38));
        
        //START BUTTON
        startButton = new JButton("START GAME");
        startButton.setBounds(300, 280, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(new Color(80, 200, 80));
        startButton.setForeground(Color.BLACK);
        //when clicked
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showFullMenu();
            	//telling main to start the game
                mainFrame.startGame();
                }
            });
        add(startButton);
        
        //RESTART BUTTON
        restartButton = new JButton("RESTART GAME");
        restartButton.setBounds(300, 350, 200, 50);
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBackground(new Color(200, 200, 80));
        restartButton.setForeground(Color.BLACK);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showFullMenu();
            	//telling Main to restart
                mainFrame.restartGame();
                }
            });
        add(restartButton);
        
        //EXIT BUTTON
        exitButton = new JButton("EXIT");
        exitButton.setBounds(300, 420, 200, 50);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setBackground(new Color(200, 80, 80));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//close the game
                System.exit(0);
                }
            });
        add(exitButton);
        }
    
    //this shows only restart button when player dies
    public void showGameOverMenu() {
        startButton.setVisible(false);
        exitButton.setVisible(false);
        restartButton.setBounds(300, 320, 200, 50);
        showGameOver = true;
        repaint();
        }
    
    //this will draw the title every time the screen refreshes
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //add background first
        drawBackground(g);
        //then draw title on top
        drawTitle(g);
        }
    
    //draws the title text
    private void drawTitle(Graphics g) {
        //shadow first
        g.setColor(new Color(0, 0, 0, 100));
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("FIREBOY & WATERGIRL", 165, 155);
        
        //main title text
        g.setColor(new Color(255, 200, 50));
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("FIREBOY & WATERGIRL", 160, 150);
        
        /*//small subtitle below the title
        g.setColor(new Color(180, 180, 220));
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Adventure Game", 290, 210);
        }*/
        }
    
    //adding same background as the game
    //draws background with brick pattern
    public void drawBackground(java.awt.Graphics g) {
        //back wall
        g.setColor(new Color(28, 28, 52));
        g.fillRect(0, 0, 800, 510);
        
        //brick pattern
        g.setColor(new Color(38, 38, 65));
        for (int row = 0; row < 510; row += 28) {
            int offset = (row / 28 % 2 == 0) ? 0 : 40;
            for (int col = -40 + offset; col < 800; col += 80) {
                g.drawRoundRect(col + 2, row + 2, 76, 24, 3, 3);
            }
        }
        
        //floor line
        g.setColor(new Color(55, 42, 25));
        g.fillRect(0, 510, 800, 4);
    }
    

    //this will show 3 buttons when menu button is clicked not only restart button
    public void showFullMenu() {
        startButton.setVisible(true);
        exitButton.setVisible(true);
        //back to the original position
        restartButton.setBounds(300, 350, 200, 50);
        showGameOver = false;
        repaint();
        }
    }