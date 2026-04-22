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

public class NewMainMenuPanel extends JPanel {
    private JButton playButton;
    private JButton exitButton;
    private Main mainFrame;
    private Image backgroundImage;
    private Image titleImage;
    
    public NewMainMenuPanel(Main main) {
        this.mainFrame = main;
        
        double scale = (main != null) ? main.scale : 1.0;
        int width = (int)(768 * scale);
        int height = (int)(768 * scale);
        this.setPreferredSize(new Dimension(width, height));
        
        setLayout(null);
        try {
            backgroundImage = new ImageIcon("src/static/image/background/MenuBackground.jpg").getImage();
        } catch (Exception e) {
            System.out.println("Background image not found, using fallback");
        }
        try {
            titleImage = new ImageIcon("src/static/image/text/GameTitleBackground.png").getImage();
        } catch (Exception e) {
            System.out.println("Title image not found");
        }
        
        playButton = new JButton("PLAY");
        playButton.setBounds(284, 450, 200, 60);
        playButton.setFont(new Font("Arial", Font.BOLD, 28));
        playButton.setBackground(new Color(255, 200, 50));
        playButton.setForeground(Color.BLACK);
        playButton.setFocusPainted(false);
        playButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
        playButton.setOpaque(true);
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playButton.setBackground(new Color(255, 220, 80));
                playButton.setBorder(new LineBorder(new Color(255, 180, 50), 3, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playButton.setBackground(new Color(255, 200, 50));
                playButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
            }
        });
        playButton.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.showDifficultyMenu();
            }
        });
        add(playButton);
        
        exitButton = new JButton("EXIT");
        exitButton.setBounds(284, 530, 200, 60);
        exitButton.setFont(new Font("Arial", Font.BOLD, 28));
        exitButton.setBackground(new Color(255, 200, 50));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
        exitButton.setOpaque(true);
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(255, 220, 80));
                exitButton.setBorder(new LineBorder(new Color(255, 180, 50), 3, true));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(255, 200, 50));
                exitButton.setBorder(new LineBorder(new Color(200, 150, 30), 2, true));
            }
        });
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
        
        if (titleImage != null) {
            int titleWidth = 500;
            int titleHeight = 200;
            int titleX = (getWidth() - titleWidth) / 2;
            int titleY = 80;
            g.drawImage(titleImage, titleX, titleY, titleWidth, titleHeight, this);
        }
    }
}