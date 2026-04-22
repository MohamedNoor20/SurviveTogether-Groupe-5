package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DifficultyPanel extends JPanel {
    
    private JButton easyButton;
    private JButton mediumButton;
    private JButton backButton;
    private Main mainFrame;
    private Image backgroundImage;
    
    public DifficultyPanel(Main main) {
        this.mainFrame = main;
        
        double scale = (main != null) ? main.scale : 1.0;
        int width = (int)(768 * scale);
        int height = (int)(768 * scale);
        this.setPreferredSize(new Dimension(width, height));
        
        setLayout(null);
        
        try {
            backgroundImage = new ImageIcon("src/static/image/background/MenuBackground.jpg").getImage();
        } catch (Exception e) {
            System.out.println("Background image not found");
        }
        
        easyButton = new JButton("EASY MODE");
        easyButton.setBounds(234, 250, 300, 70);
        easyButton.setFont(new Font("Arial", Font.BOLD, 28));
        easyButton.setBackground(new Color(255, 200, 50));
        easyButton.setForeground(Color.BLACK);
        easyButton.setFocusPainted(false);
        easyButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
        easyButton.setOpaque(true);
        easyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                easyButton.setBackground(new Color(255, 220, 80));
                easyButton.setBorder(new LineBorder(new Color(255, 180, 50), 3, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                easyButton.setBackground(new Color(255, 200, 50));
                easyButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
            }
        });
        easyButton.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.startGame("easy");
            }
        });
        add(easyButton);
        
        mediumButton = new JButton("MEDIUM MODE");
        mediumButton.setBounds(234, 350, 300, 70);
        mediumButton.setFont(new Font("Arial", Font.BOLD, 28));
        mediumButton.setBackground(new Color(255, 200, 50));
        mediumButton.setForeground(Color.BLACK);
        mediumButton.setFocusPainted(false);
        mediumButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
        mediumButton.setOpaque(true);
        mediumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mediumButton.setBackground(new Color(255, 220, 80));
                mediumButton.setBorder(new LineBorder(new Color(255, 180, 50), 3, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mediumButton.setBackground(new Color(255, 200, 50));
                mediumButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
            }
        });
        mediumButton.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.startGame("medium");
            }
        });
        add(mediumButton);
        
        backButton = new JButton("BACK");
        backButton.setBounds(284, 460, 200, 50);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(new Color(255, 200, 50));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
        backButton.setOpaque(true);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(255, 220, 80));
                backButton.setBorder(new LineBorder(new Color(255, 180, 50), 3, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(255, 200, 50));
                backButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
            }
        });
        backButton.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.showMainMenu();
            }
        });
        add(backButton);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(18, 18, 38));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}