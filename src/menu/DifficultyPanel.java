package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import griffith.Main;

public class DifficultyPanel extends JPanel {
    
    private Main mainFrame;
    private Image backgroundImage;
    private Image easyImg, mediumImg, backImg;
    
    public DifficultyPanel(Main main) {
        this.mainFrame = main;
        
        double scale = (main != null) ? main.scale : 1.0;
        int width = (int)(768 * scale);
        int height = (int)(768 * scale);
        this.setPreferredSize(new Dimension(width, height));
        
        setLayout(null);
        
        backgroundImage = new ImageIcon("src/static/image/background/MenuBackground.jpg").getImage();

        easyImg   = new ImageIcon("src/static/image/text/Easy_mode.png").getImage();
        mediumImg = new ImageIcon("src/static/image/text/Medium_mode.png").getImage();
        backImg   = new ImageIcon("src/static/image/text/BackBtn.png").getImage();
        

        add(createImageButton(easyImg, 234, 250, 300, 70, () -> {
            if (mainFrame != null) mainFrame.startGame("easy");
        }));

        add(createImageButton(mediumImg, 234, 350, 300, 70, () -> {
            if (mainFrame != null) mainFrame.startGame("medium");
        }));

        add(createImageButton(backImg, 234, 450, 300, 70, () -> {
            if (mainFrame != null) mainFrame.showMainMenu();
        }));
    }

    private JButton createImageButton(Image img, int x, int y, int w, int h, Runnable action) {
        JButton btn = new JButton() {
            protected void paintComponent(Graphics g) {
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        btn.setBounds(x, y, w, h);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> action.run());

        return btn;
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