package griffith;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JButton;

public class MyLevelPanel extends JPanel implements Runnable, KeyListener {
    
    private static final long serialVersionUID = 1L;
    
    private final int WIDTH = 768;
    private final int HEIGHT = 768;
    private final int IMG_WIDTH = 48;
    private final int IMG_HEIGHT = 59;
    private final int RUN_IMG_WIDTH = 68;
    private final int RUN_IMG_HEIGHT = 61;
    
    private ArrayList<Level2Block> blocks = new ArrayList<>();
    private ArrayList<Level2Item> items = new ArrayList<>();
    private ArrayList<Level2Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Level2Door> doors = new ArrayList<>();
    private ArrayList<Level2SwitchBlock> switchBlocks = new ArrayList<>();
    private ArrayList<Level2Switch> switchBtns = new ArrayList<>();
    
    private int delay = 17;
    private long pretime;
    private Thread gameThread;
    private long startTime;
    private int elapsedSeconds = 0;
    
    private int resetTotalDistance = 90;
    private int jumpingDist = 6;
    private int fallingDist = 6;
    private int xmovingDist = 6;
    
    private int fireboyX = 50;
    private int fireboyY = 650;
    private Rectangle fireboyRect;
    
    private boolean fireboyLeft = false;
    private boolean fireboyRight = false;
    private boolean fireboyUp = false;
    private boolean fireboyJumping = false;
    private boolean fireboyFalling = false;
    private int fireboyJumpDist = 0;
    private String fireboyState = "FRONT";
    
    private int watergirlX = 100;
    private int watergirlY = 650;
    private Rectangle watergirlRect;
    
    private boolean watergirlLeft = false;
    private boolean watergirlRight = false;
    private boolean watergirlUp = false;
    private boolean watergirlJumping = false;
    private boolean watergirlFalling = false;
    private int watergirlJumpDist = 0;
    private String watergirlState = "FRONT";
    
    private boolean isDie = false;
    private boolean isGameClear = false;
    private Toolkit imageTool = Toolkit.getDefaultToolkit();
    
    private Image fireboyFront;
    private Image fireboyLeftImg;
    private Image fireboyRightImg;
    private Image watergirlFront;
    private Image watergirlLeftImg;
    private Image watergirlRightImg;
    private Image mapImg;
    private Main mainFrame;
    
    public MyLevelPanel() {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);
        
        mapImg = imageTool.getImage("src/static/image/background/game_play_background.png");
        
        fireboyFront = imageTool.getImage("src/static/image/character/fire_boy_character.png");
        fireboyLeftImg = imageTool.getImage("src/static/image/character/fire boy_run left.gif");
        fireboyRightImg = imageTool.getImage("src/static/image/character/fire boy_run right.gif");
        
        watergirlFront = imageTool.getImage("src/static/image/character/water_girl_character.png");
        watergirlLeftImg = imageTool.getImage("src/static/image/character/water girl_run left.gif");
        watergirlRightImg = imageTool.getImage("src/static/image/character/water girl_run right.gif");
        
        JButton menuButton = new JButton("MENU");
        menuButton.setBounds(WIDTH - 90, 10, 80, 30);
        menuButton.setFont(new Font("Arial", Font.BOLD, 14));
        menuButton.setBackground(new Color(200, 200, 200));
        menuButton.setForeground(Color.BLACK);
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(e -> {
            if (gameThread != null) {
                gameThread = null;
            }
            if (mainFrame != null) {
                mainFrame.showMainMenu();
            }
        });
        add(menuButton);
        
        loadLevel();
        
        startTime = System.currentTimeMillis();
        
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void setMainFrame(Main main) {
        this.mainFrame = main;
    }
    
    private void loadLevel() {
        for (int i = 0; i < 25; i++) {
            blocks.add(new Level2Block(i * 31, 700, 31, 32));
        }
        
        blocks.add(new Level2Block(200, 600, 31, 32));
        blocks.add(new Level2Block(231, 600, 31, 32));
        blocks.add(new Level2Block(262, 600, 31, 32));
        
        blocks.add(new Level2Block(500, 500, 31, 32));
        blocks.add(new Level2Block(531, 500, 31, 32));
        blocks.add(new Level2Block(562, 500, 31, 32));
        
        blocks.add(new Level2Block(100, 400, 31, 32));
        blocks.add(new Level2Block(131, 400, 31, 32));
        
        items.add(new Level2Item(300, 650, 1));
        items.add(new Level2Item(350, 650, 2));
        items.add(new Level2Item(550, 450, 1));
        items.add(new Level2Item(150, 350, 2));
        
        obstacles.add(new Level2Obstacle(400, 650, 0));
        obstacles.add(new Level2Obstacle(600, 450, 1));
        obstacles.add(new Level2Obstacle(250, 350, -1));
        
        doors.add(new Level2Door(650, 630, 1));
        doors.add(new Level2Door(700, 630, 2));
        
        Level2SwitchBlock switchBlock = new Level2SwitchBlock(300, 550);
        switchBlocks.add(switchBlock);
        
        Level2Switch switchBtn = new Level2Switch(300, 580);
        switchBtn.setManageBlock(switchBlock);
        switchBtns.add(switchBtn);
        
        fireboyX = 50;
        fireboyY = 650;
        watergirlX = 100;
        watergirlY = 650;
    }
    
    public void startGame() {
        if (gameThread == null) {
            startTime = System.currentTimeMillis();
            gameThread = new Thread(this);
            gameThread.start();
        }
    }
    
    @Override
    public void run() {
        while (gameThread != null) {
            pretime = System.currentTimeMillis();
            updateGame();
            repaint();
            
            long sleepTime = delay - (System.currentTimeMillis() - pretime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void updateGame() {
        if (!isDie && !isGameClear) {
            updateFireboy();
            updateWatergirl();
            checkItemCollisions();
            checkObstacleCollisions();
            checkSwitchCollisions();
            checkArrival();
            
            long currentTime = System.currentTimeMillis();
            elapsedSeconds = (int)((currentTime - startTime) / 1000);
        }
    }
    
    private void updateFireboy() {
        if (fireboyLeft) {
            fireboyState = "LEFT";
            fireboyX -= xmovingDist;
            if (!canMoveFireboy(fireboyX, fireboyY)) fireboyX += xmovingDist;
        }
        if (fireboyRight) {
            fireboyState = "RIGHT";
            fireboyX += xmovingDist;
            if (!canMoveFireboy(fireboyX, fireboyY)) fireboyX -= xmovingDist;
        }
        if (!fireboyLeft && !fireboyRight) {
            fireboyState = "FRONT";
        }
        
        if (fireboyUp && !fireboyJumping && !fireboyFalling) {
            fireboyJumping = true;
            fireboyJumpDist = resetTotalDistance;
        }
        
        if (fireboyJumping) {
            if (fireboyJumpDist <= 0) {
                fireboyJumping = false;
                fireboyFalling = true;
            } else {
                if (canMoveFireboy(fireboyX, fireboyY - jumpingDist)) {
                    fireboyY -= jumpingDist;
                    fireboyJumpDist -= jumpingDist;
                } else {
                    fireboyJumping = false;
                    fireboyFalling = true;
                }
            }
        } else if (fireboyFalling) {
            if (canMoveFireboy(fireboyX, fireboyY + fallingDist)) {
                fireboyY += fallingDist;
            } else {
                fireboyFalling = false;
            }
        }
        
        if (fireboyY > HEIGHT) {
            isDie = true;
        }
    }
    
    private void updateWatergirl() {
        if (watergirlLeft) {
            watergirlState = "LEFT";
            watergirlX -= xmovingDist;
            if (!canMoveWatergirl(watergirlX, watergirlY)) watergirlX += xmovingDist;
        }
        if (watergirlRight) {
            watergirlState = "RIGHT";
            watergirlX += xmovingDist;
            if (!canMoveWatergirl(watergirlX, watergirlY)) watergirlX -= xmovingDist;
        }
        if (!watergirlLeft && !watergirlRight) {
            watergirlState = "FRONT";
        }
        
        if (watergirlUp && !watergirlJumping && !watergirlFalling) {
            watergirlJumping = true;
            watergirlJumpDist = resetTotalDistance;
        }
        
        if (watergirlJumping) {
            if (watergirlJumpDist <= 0) {
                watergirlJumping = false;
                watergirlFalling = true;
            } else {
                if (canMoveWatergirl(watergirlX, watergirlY - jumpingDist)) {
                    watergirlY -= jumpingDist;
                    watergirlJumpDist -= jumpingDist;
                } else {
                    watergirlJumping = false;
                    watergirlFalling = true;
                }
            }
        } else if (watergirlFalling) {
            if (canMoveWatergirl(watergirlX, watergirlY + fallingDist)) {
                watergirlY += fallingDist;
            } else {
                watergirlFalling = false;
            }
        }
        
        if (watergirlY > HEIGHT) {
            isDie = true;
        }
    }
    
    private boolean canMoveFireboy(int x, int y) {
        fireboyRect = new Rectangle(x + 8, y + 8, IMG_WIDTH - 30, IMG_HEIGHT - 14);
        
        for (Level2Block block : blocks) {
            if (fireboyRect.intersects(block.getRectangle())) {
                return false;
            }
        }
        for (Level2SwitchBlock switchBlock : switchBlocks) {
            if (switchBlock.isVisible() && fireboyRect.intersects(switchBlock.getRectangle())) {
                return false;
            }
        }
        return true;
    }
    
    private boolean canMoveWatergirl(int x, int y) {
        watergirlRect = new Rectangle(x + 8, y + 8, IMG_WIDTH - 30, IMG_HEIGHT - 14);
        
        for (Level2Block block : blocks) {
            if (watergirlRect.intersects(block.getRectangle())) {
                return false;
            }
        }
        for (Level2SwitchBlock switchBlock : switchBlocks) {
            if (switchBlock.isVisible() && watergirlRect.intersects(switchBlock.getRectangle())) {
                return false;
            }
        }
        return true;
    }
    
    private void checkItemCollisions() {
        for (int i = 0; i < items.size(); i++) {
            Level2Item item = items.get(i);
            Rectangle itemRect = new Rectangle(item.getX(), item.getY(), item.getWidth(), item.getHeight());
            
            if (fireboyRect != null && fireboyRect.intersects(itemRect) && item.getState() == 1) {
                items.remove(i);
                i--;
            } else if (watergirlRect != null && watergirlRect.intersects(itemRect) && item.getState() == 2) {
                items.remove(i);
                i--;
            }
        }
    }
    
    private void checkObstacleCollisions() {
        for (Level2Obstacle obstacle : obstacles) {
            Rectangle obsRect = new Rectangle(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
            
            if (fireboyRect != null && fireboyRect.intersects(obsRect)) {
                if (obstacle.getState() == 1 || obstacle.getState() == -1) {
                    isDie = true;
                }
            }
            if (watergirlRect != null && watergirlRect.intersects(obsRect)) {
                if (obstacle.getState() == 0 || obstacle.getState() == -1) {
                    isDie = true;
                }
            }
        }
    }
    
    private void checkSwitchCollisions() {
        for (Level2Switch switchBtn : switchBtns) {
            Rectangle switchRect = switchBtn.getRectangle();
            
            if (fireboyRect != null && fireboyRect.intersects(switchRect)) {
                switchBtn.setSwitchState(true);
            }
            if (watergirlRect != null && watergirlRect.intersects(switchRect)) {
                switchBtn.setSwitchState(true);
            }
        }
    }
    
    private void checkArrival() {
        boolean fireboyAtDoor = false;
        boolean watergirlAtDoor = false;
        
        for (Level2Door door : doors) {
            Rectangle doorRect = new Rectangle(door.getX(), door.getY(), door.getWidth(), door.getHeight());
            
            if (fireboyRect != null && fireboyRect.intersects(doorRect) && door.getState() == 1) {
                fireboyAtDoor = true;
            }
            if (watergirlRect != null && watergirlRect.intersects(doorRect) && door.getState() == 2) {
                watergirlAtDoor = true;
            }
        }
        
        if (fireboyAtDoor && watergirlAtDoor) {
            isGameClear = true;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_ESCAPE) {
            if (gameThread != null) {
                gameThread = null;
            }
            if (mainFrame != null) {
                mainFrame.showMainMenu();
            }
        }
        
        switch(key) {
            case KeyEvent.VK_LEFT: fireboyLeft = true; break;
            case KeyEvent.VK_RIGHT: fireboyRight = true; break;
            case KeyEvent.VK_UP: fireboyUp = true; break;
            
            case KeyEvent.VK_A: watergirlLeft = true; break;
            case KeyEvent.VK_D: watergirlRight = true; break;
            case KeyEvent.VK_W: watergirlUp = true; break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch(key) {
            case KeyEvent.VK_LEFT: fireboyLeft = false; break;
            case KeyEvent.VK_RIGHT: fireboyRight = false; break;
            case KeyEvent.VK_UP: fireboyUp = false; break;
            
            case KeyEvent.VK_A: watergirlLeft = false; break;
            case KeyEvent.VK_D: watergirlRight = false; break;
            case KeyEvent.VK_W: watergirlUp = false; break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    private Image getFireboyImage() {
        if (fireboyState.equals("LEFT")) {
            return fireboyLeftImg;
        } else if (fireboyState.equals("RIGHT")) {
            return fireboyRightImg;
        } else {
            return fireboyFront;
        }
    }
    
    private Image getWatergirlImage() {
        if (watergirlState.equals("LEFT")) {
            return watergirlLeftImg;
        } else if (watergirlState.equals("RIGHT")) {
            return watergirlRightImg;
        } else {
            return watergirlFront;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(mapImg, 0, 0, WIDTH, HEIGHT, this);
        
        for (Level2Block block : blocks) {
            g.drawImage(block.getImage(), block.getX(), block.getY(), this);
        }
        
        for (Level2Item item : items) {
            g.drawImage(item.getImage(), item.getX(), item.getY(), this);
        }
        
        for (Level2Obstacle obstacle : obstacles) {
            g.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
        }
        
        for (Level2Door door : doors) {
            g.drawImage(door.getImage(), door.getX(), door.getY(), this);
        }
        
        for (Level2SwitchBlock switchBlock : switchBlocks) {
            if (switchBlock.isVisible()) {
                g.drawImage(switchBlock.getImage(), switchBlock.getX(), switchBlock.getY(), this);
            }
        }
        
        for (Level2Switch switchBtn : switchBtns) {
            g.drawImage(switchBtn.getImage(), switchBtn.getX(), switchBtn.getY(), this);
        }
        
        g.drawImage(getFireboyImage(), fireboyX, fireboyY, this);
        g.drawImage(getWatergirlImage(), watergirlX, watergirlY, this);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Time: " + elapsedSeconds + "s", 20, 30);
        
        if (isDie) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH / 2 - 120, HEIGHT / 2);
        }
        
        if (isGameClear) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("STAGE CLEAR!", WIDTH / 2 - 120, HEIGHT / 2);
        }
    }
}