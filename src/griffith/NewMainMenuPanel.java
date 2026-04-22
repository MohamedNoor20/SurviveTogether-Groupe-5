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
}
	    
}
