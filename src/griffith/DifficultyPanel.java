package griffith;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;
public class DifficultyPanel {
	private JButton easyButton;
    private JButton mediumButton;
    private JButton backButton;
    private Main mainFrame;
    
    
    public DifficultyPanel(Main main) {
        this.mainFrame = main;
        
        double scale = (main != null) ? main.scale : 1.0;
        int width = (int)(768 * scale);
        int height = (int)(768 * scale);
        this.setPreferredSize(new Dimension(width, height));
        
        setLayout(null);
        setBackground(new Color(18, 18, 38));
}
}