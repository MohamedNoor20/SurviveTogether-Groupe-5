package menu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import griffith.Main;

public class DifficultyPanel extends JPanel {

	private Main mainFrame;
	private Image backgroundImage;
	private Image easyImg, mediumImg, backImg;

	private boolean easyPressed = false;
	private boolean mediumPressed = false;
	private boolean muadPressed = false;
	private boolean afaqPressed = false;
	private boolean mohamedPressed = false;
	private boolean backPressed = false;

	// All button positions
	private final Rectangle easyBtnBase = new Rectangle(234, 150, 300, 70);
	private final Rectangle mediumBtnBase = new Rectangle(234, 250, 300, 70);
	private final Rectangle muadBtnBase = new Rectangle(234, 350, 300, 70);
	private final Rectangle afaqBtnBase = new Rectangle(234, 450, 300, 70);
	private final Rectangle mohamedBtnBase = new Rectangle(234, 550, 300, 70);
	private final Rectangle backBtnBase = new Rectangle(234, 650, 300, 70);

	public DifficultyPanel(Main main) {
		this.mainFrame = main;
		setPreferredSize(new Dimension(Main.BaseSize, Main.BaseSize));
		setLayout(null);

		backgroundImage = tryLoad("src/static/image/background/MenuBackground.jpg");
		easyImg = tryLoad("src/static/image/text/Easy_mode.png");
		mediumImg = tryLoad("src/static/image/text/Medium_mode.png");
		backImg = tryLoad("src/static/image/text/BackBtn.png");

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point base = toBase(e.getPoint());
				if (easyBtnBase.contains(base)) {
					easyPressed = true;
					repaint();
				}
				if (mediumBtnBase.contains(base)) {
					mediumPressed = true;
					repaint();
				}
				if (muadBtnBase.contains(base)) {
					muadPressed = true;
					repaint();
				}
				if (afaqBtnBase.contains(base)) {
					afaqPressed = true;
					repaint();
				}
				if (mohamedBtnBase.contains(base)) {
					mohamedPressed = true;
					repaint();
				}
				if (backBtnBase.contains(base)) {
					backPressed = true;
					repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				easyPressed = false;
				mediumPressed = false;
				muadPressed = false;
				afaqPressed = false;
				mohamedPressed = false;
				backPressed = false;
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Point base = toBase(e.getPoint());
				if (easyBtnBase.contains(base) && mainFrame != null)
					mainFrame.startGame("easy");
				if (mediumBtnBase.contains(base) && mainFrame != null)
					mainFrame.startGame("medium");
				if (muadBtnBase.contains(base) && mainFrame != null)
					mainFrame.startGame("muad");
				if (afaqBtnBase.contains(base) && mainFrame != null)
					mainFrame.startGame("afaq");
				if (mohamedBtnBase.contains(base) && mainFrame != null)
					mainFrame.startGame("mohamed");
				if (backBtnBase.contains(base) && mainFrame != null)
					mainFrame.showMainMenu();
			}
		});

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

	// Convert physical mouse point to base coordinate 
	private Point toBase(Point p) {
		if (mainFrame == null)
			return p;
		double s = mainFrame.scale;
		int physicalSize = (int) (Main.BaseSize * s);
		int offsetX = (getWidth() - physicalSize) / 2;
		int offsetY = (getHeight() - physicalSize) / 2;
		int bx = (int) ((p.x - offsetX) / s);
		int by = (int) ((p.y - offsetY) / s);
		return new Point(bx, by);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Fill entire panel black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		Graphics2D g2 = (Graphics2D) g.create();

		// Center the square and apply scale
		if (mainFrame != null) {
			double s = mainFrame.scale;
			int physicalSize = (int) (Main.BaseSize * s);
			int offsetX = (getWidth() - physicalSize) / 2;
			int offsetY = (getHeight() - physicalSize) / 2;
			g2.translate(offsetX, offsetY);
			g2.scale(s, s);
		}

		// Clip to Base square so nothing draws outside
		g2.setClip(0, 0, Main.BaseSize, Main.BaseSize);

		// Draw background
		if (backgroundImage != null) {
			g2.drawImage(backgroundImage, 0, 0, Main.BaseSize, Main.BaseSize, this);
		} else {
			g2.setColor(new Color(18, 18, 38));
			g2.fillRect(0, 0, Main.BaseSize, Main.BaseSize);
		}

		Point mouse = getMousePosition();
		Point baseMouse = toBase(mouse != null ? mouse : new Point(-1, -1));

		// Draw all buttons
		drawImageButton(g2, easyBtnBase, easyImg, easyPressed, "EASY", baseMouse);
		drawImageButton(g2, mediumBtnBase, mediumImg, mediumPressed, "MEDIUM", baseMouse);
		drawSolidButton(g2, muadBtnBase, muadPressed, "Muad Level", new Color(150, 50, 200), baseMouse);
		drawSolidButton(g2, afaqBtnBase, afaqPressed, "AFAQ LEVEL", new Color(255, 100, 50), baseMouse);
		drawSolidButton(g2, mohamedBtnBase, mohamedPressed, "Mohamed LEVEL", new Color(200, 185, 80), baseMouse);
		drawImageButton(g2, backBtnBase, backImg, backPressed, "BACK", baseMouse);

		g2.dispose();
	}

	// Draws a button using an image, falls back to solid if image is null
	private void drawImageButton(Graphics2D g2, Rectangle r, Image img, boolean pressed, String label,
			Point baseMouse) {
		if (img != null) {
			// Slight darken when pressed
			g2.drawImage(img, r.x, r.y, r.width, r.height, this);
			if (pressed) {
				g2.setColor(new Color(0, 0, 0, 60));
				g2.fillRoundRect(r.x, r.y, r.width, r.height, 12, 12);
			}
		} else {
			// Solid box for fallback with hover and press states
			boolean hover = r.contains(baseMouse);
			if (pressed) {
				g2.setColor(new Color(200, 150, 20));
			} else if (hover) {
				g2.setColor(new Color(255, 220, 80));
			} else {
				g2.setColor(new Color(255, 200, 50));
			}
			g2.fillRoundRect(r.x, r.y, r.width, r.height, 16, 16);
			g2.setColor(new Color(80, 50, 0));
			g2.drawRoundRect(r.x, r.y, r.width - 1, r.height - 1, 16, 16);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Arial", Font.BOLD, 26));
			FontMetrics fm = g2.getFontMetrics();
			int tx = r.x + (r.width - fm.stringWidth(label)) / 2;
			int ty = r.y + (r.height + fm.getAscent() - fm.getDescent()) / 2;
			g2.drawString(label, tx, ty);
		}
	}

	// Draws a solid colored button
	private void drawSolidButton(Graphics2D g2, Rectangle r, boolean pressed, String label, Color baseColor,
			Point baseMouse) {
		boolean hover = r.contains(baseMouse);

		// Darken when presses, lighten when hover over it
		Color displayColor;
		if (pressed) {
			displayColor = baseColor.darker();
		} else if (hover) {
			displayColor = baseColor.brighter();
		} else {
			displayColor = baseColor;
		}

		g2.setColor(displayColor);
		g2.fillRoundRect(r.x, r.y, r.width, r.height, 16, 16);

		// Border
		g2.setColor(baseColor.darker().darker());
		g2.drawRoundRect(r.x, r.y, r.width - 1, r.height - 1, 16, 16);

		// Label
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 26));
		FontMetrics fm = g2.getFontMetrics();
		int tx = r.x + (r.width - fm.stringWidth(label)) / 2;
		int ty = r.y + (r.height + fm.getAscent() - fm.getDescent()) / 2;
		g2.drawString(label, tx, ty);
	}

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