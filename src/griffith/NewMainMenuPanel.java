package griffith;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewMainMenuPanel extends JPanel {

    private Main mainFrame;
    private Image backgroundImage;
    private Image titleImage;
    private Image startBtnImg;
    private Image startBtnClickedImg;
    private Image exitBtnImg;
    private Image exitBtnClickedImg;

    private boolean startPressed = false;
    private boolean exitPressed  = false;

    public NewMainMenuPanel(Main main) {
        this.mainFrame = main;
        setPreferredSize(new Dimension(768, 768));
        setLayout(null);

        // Load images safely
        backgroundImage      = tryLoad("src/static/image/background/MenuBackground.jpg");
        titleImage           = tryLoad("src/static/image/text/GameTitleBackground.png");
        startBtnImg          = tryLoad("src/static/image/elements/start_button.png");
        startBtnClickedImg   = tryLoad("src/static/image/elements/start_button_clicked.png");
        exitBtnImg           = tryLoad("src/static/image/elements/exit_button.png");
        exitBtnClickedImg    = tryLoad("src/static/image/elements/exit_button_clicked.png");

        // PLAY button
        JButton playButton = new JButton("PLAY") {
            @Override
            protected void paintComponent(Graphics g) {
                Image img = startPressed ? startBtnClickedImg : startBtnImg;
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // solid fallback so it's always visible
                    g.setColor(startPressed ? new Color(200, 150, 20) : new Color(255, 200, 50));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                    g.setColor(new Color(80, 50, 0));
                    g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 26));
                    FontMetrics fm = g.getFontMetrics();
                    int tx = (getWidth() - fm.stringWidth("PLAY")) / 2;
                    int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g.drawString("PLAY", tx, ty);
                }
            }
        };
        playButton.setBounds(284, 520, 200, 65);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        playButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e)  { startPressed = true;  repaint(); }
            public void mouseReleased(MouseEvent e) { startPressed = false; repaint(); }
        });
        playButton.addActionListener(e -> { if (mainFrame != null) mainFrame.showDifficultyMenu(); });
        add(playButton);

        // EXIT button
        JButton exitButton = new JButton("EXIT") {
            @Override
            protected void paintComponent(Graphics g) {
                Image img = exitPressed ? exitBtnClickedImg : exitBtnImg;
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // solid fallback so it's always visible
                    g.setColor(exitPressed ? new Color(200, 150, 20) : new Color(255, 200, 50));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                    g.setColor(new Color(80, 50, 0));
                    g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 26));
                    FontMetrics fm = g.getFontMetrics();
                    int tx = (getWidth() - fm.stringWidth("EXIT")) / 2;
                    int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g.drawString("EXIT", tx, ty);
                }
            }
        };
        exitButton.setBounds(284, 605, 200, 65);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e)  { exitPressed = true;  repaint(); }
            public void mouseReleased(MouseEvent e) { exitPressed = false; repaint(); }
        });
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private Image tryLoad(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            // check image actually loaded (width > 0 means success)
            if (icon.getIconWidth() > 0) return icon.getImage();
        } catch (Exception e) {
            System.out.println("Could not load: " + path);
        }
        return null;
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
            int tw = 500, th = 200;
            g.drawImage(titleImage, (getWidth() - tw) / 2, 80, tw, th, this);
        }
    }
}