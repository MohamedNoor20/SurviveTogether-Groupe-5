package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
public class NewMainMenuPanel extends JPanel {
	 private JButton playButton;
	    private JButton exitButton;
	    private Main mainFrame;
	    private Image backgroundImage;
	    
	    public NewMainMenuPanel(Main main) {
	        this.mainFrame = main;
	        
	        double scale = (main != null) ? main.scale : 1.0;
	        int width = (int)(768 * scale);
	        int height = (int)(768 * scale);
	        this.setPreferredSize(new Dimension(width, height));
	        
	        setLayout(null);
	        try {
	            backgroundImage = new ImageIcon("src/static/images/MenuBackground.jpg").getImage();
	        } catch (Exception e) {
	            System.out.println("Background image not found, using fallback");
	        }
	        playButton = new JButton("PLAY");
	        playButton.setBounds(284, 450, 200, 60);
	        playButton.setFont(new Font("Arial", Font.BOLD, 28));
	        playButton.setBackground(new Color(255, 100, 50));
	        playButton.setForeground(Color.WHITE);
	        playButton.setFocusPainted(false);
	        playButton.addActionListener(e -> {
	            if (mainFrame != null) {
	                mainFrame.showDifficultyMenu();
	            }
	        });
	        add(playButton);
	        
	        exitButton = new JButton("EXIT");
	        exitButton.setBounds(284, 530, 200, 60);
	        exitButton.setFont(new Font("Arial", Font.BOLD, 28));
	        exitButton.setBackground(new Color(80, 80, 80));
	        exitButton.setForeground(Color.WHITE);
	        exitButton.setFocusPainted(false);
	        exitButton.addActionListener(e -> System.exit(0));
	        add(exitButton);
}
	    
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        
	      
	        if (backgroundImage != null) {
	            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	        } else {
	            
	            g.setColor(new Color(20, 30, 60));
	            g.fillRect(0, 0, getWidth(), getHeight());
	        }
	        
	       
	        g.setFont(new Font("Arial", Font.BOLD, 52));
	        g.setColor(new Color(255, 200, 50));
	        g.drawString("FIREBOY", 200, 150);
	        
	        g.setFont(new Font("Arial", Font.BOLD, 52));
	        g.setColor(new Color(50, 200, 255));
	        g.drawString("&", 420, 150);
	        
	        g.setFont(new Font("Arial", Font.BOLD, 52));
	        g.setColor(new Color(50, 200, 100));
	        g.drawString("WATERGIRL", 460, 150);
	        
	        g.setFont(new Font("Arial", Font.ITALIC, 24));
	        g.setColor(new Color(200, 200, 200));
	        g.drawString("ELEMENTS", 340, 220);
	    }
}
