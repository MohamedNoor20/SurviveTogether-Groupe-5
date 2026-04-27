package menu;

//updated menu panel 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import griffith.Main;

public class MenuPanel extends JPanel {

	private Main mainFrame;
	private Image backgroundImage;
	private Image titleImage;
	private Image startBtnImg;
	private Image startBtnClickedImg;
	private Image exitBtnImg;
	private Image exitBtnClickedImg;

	private boolean startPressed = false;
	private boolean exitPressed = false;

	private JButton playButton;
	private JButton exitButton;

	public MenuPanel(Main main) {
		this.mainFrame = main;
		setPreferredSize(new Dimension(Main.BaseSize, Main.BaseSize));
		setLayout(null);

		// Load images safely
		backgroundImage = tryLoad("src/static/image/background/MenuBackground.jpg");
		titleImage = tryLoad("src/static/image/text/GameTitleBackground.png");
		startBtnImg = tryLoad("src/static/image/elements/unclickedStartBtn.png");
		startBtnClickedImg = tryLoad("src/static/image/elements/clickedStartBtn.png");
		exitBtnImg = tryLoad("src/static/image/elements/unclickedExitBtn.png");
		exitBtnClickedImg = tryLoad("src/static/image/elements/clickedExitBtn.png");

		// PLAY button
		playButton = new JButton("PLAY") {
			@Override
			protected void paintComponent(Graphics g) {
				Image img = startPressed ? startBtnClickedImg : startBtnImg;

				if (img != null) {
					g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
				} else {
					// fallback
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

		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.setFocusPainted(false);
		
		playButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startPressed = true;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				startPressed = false;
				repaint();
			}
		});
		playButton.addActionListener(e -> {
			if (mainFrame != null)
				mainFrame.showDifficultyMenu();
		});
		add(playButton);

		// EXIT button
		exitButton = new JButton("EXIT") {
			@Override
			protected void paintComponent(Graphics g) {
				Image img = exitPressed ? exitBtnClickedImg : exitBtnImg;

				if (img != null) {
					g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
				} else {
					// fallback
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
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				exitPressed = true;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				exitPressed = false;
				repaint();
			}
		});
		exitButton.addActionListener(e -> System.exit(0));
		add(exitButton);
	}
	// this does the layout
	@Override
	public void doLayout() {
		super.doLayout();
		int w = getWidth();
		int h = getHeight();
		if (w == 0 || h == 0) {
			return;
		}

		int btnW = (int) (w * 0.26);
		int btnH = (int) (h * 0.085);
		int cx = (w - btnW) / 2;

		exitButton.setBounds(cx, (int) (h * 0.495), btnW, btnH);
		playButton.setBounds(cx, (int) (h * 0.676), btnW, btnH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getWidth();
		int h = getHeight();

		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, w, h, this);
		} else {
			g.setColor(new Color(20, 30, 60));
			g.fillRect(0, 0, w, h);
		}
		if (titleImage != null) {
			int tw = (int) (w * 0.65);
			int th = (int) (h * 0.26);
			g.drawImage(titleImage, (w - tw) / 2, (int) (h * 0.1), tw, th, this);
		}
	}
	// this gets the images 
	private Image tryLoad(String path) {
		try {
			ImageIcon icon = new ImageIcon(path);
			// check image actually loaded (width > 0 means success)
			if (icon.getIconWidth() > 0)
				return icon.getImage();
		} catch (Exception e) {
			System.out.println("Could not load: " + path);
		}
		return null;
	}
}