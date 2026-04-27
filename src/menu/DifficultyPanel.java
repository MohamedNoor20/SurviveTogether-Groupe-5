package menu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import griffith.Main;

public class DifficultyPanel extends JPanel {

	private Main mainFrame;
	private Image backgroundImage;
	private Image easyImg, mediumImg, backImg;
	private JButton easyBtn, mediumBtn, muadBtn, backBtn;

	public DifficultyPanel(Main main) {
		this.mainFrame = main;
		this.setPreferredSize(new Dimension(Main.BaseSize, Main.BaseSize));
		setLayout(null);

		backgroundImage = tryLoad("src/static/image/background/MenuBackground.jpg");
		easyImg = tryLoad("src/static/image/text/Easy_mode.png");
		mediumImg = tryLoad("src/static/image/text/Medium_mode.png");
		backImg = tryLoad("src/static/image/text/BackBtn.png");
		// creates button using image
		easyBtn = createImageButton(easyImg, () -> {
			if (mainFrame != null)
				mainFrame.startGame("easy");
		});
		mediumBtn = createImageButton(mediumImg, () -> {
			if (mainFrame != null)
				mainFrame.startGame("medium");
		});
		backBtn = createImageButton(backImg, () -> {
			if (mainFrame != null)
				mainFrame.showMainMenu();
		});
		//creates button
		muadBtn = new JButton("Muad Level");
		muadBtn.setFont(new Font("Arial", Font.BOLD, 22));
		muadBtn.setBackground(new Color(150, 50, 200));
		muadBtn.setForeground(Color.WHITE);
		muadBtn.setFocusPainted(false);
		muadBtn.addActionListener(e -> {
			if (mainFrame != null)
				mainFrame.startGame("muad");
		});

		add(easyBtn);
		add(mediumBtn);
		add(muadBtn);
		add(backBtn);

	}
	// this does the layouts
    @Override
    public void doLayout() {
        super.doLayout();
        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0) {
        	return;
        }

        int btnW = (int)(w * 0.39); 
        int btnH = (int)(h * 0.091);
        int cx   = (w - btnW) / 2;

        // Spread buttons evenly down the panel
        easyBtn  .setBounds(cx, (int)(h * 0.195), btnW, btnH);
        mediumBtn.setBounds(cx, (int)(h * 0.325), btnW, btnH);
        muadBtn  .setBounds(cx, (int)(h * 0.456), btnW, btnH);
        backBtn  .setBounds(cx, (int)(h * 0.586), btnW, btnH);
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
	// this makes the image button and clickable
	private JButton createImageButton(Image img, Runnable action) {
		JButton btn = new JButton() {
			@Override
			protected void paintComponent(Graphics g) {
				if (img != null) {
					g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
				} else {
					// Solid block for fallback if image is missing
					g.setColor(new Color(255, 200, 50));
					g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
					g.setColor(Color.BLACK);
					g.setFont(new Font("Arial", Font.BOLD, 20));
					g.drawString("?", getWidth() / 2, getHeight() / 2);
				}
			}
		};
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.addActionListener(e -> action.run());
		return btn;
	}
	// tjis loads the images
	private Image tryLoad(String path) {
		try {
			ImageIcon icon = new ImageIcon(path);
			if (icon.getIconWidth() > 0)
				return icon.getImage();
		} catch (Exception e) {
			System.out.println("Could not load: " + path);
		}
		return null;
	}
}