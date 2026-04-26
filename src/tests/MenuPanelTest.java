package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;

import java.awt.*;

import griffith.Main;
import menu.MenuPanel;

public class MenuPanelTest {
    
    private MenuPanel menuPanel;
    private Main main;
    

    @BeforeEach
    void setUp() {
        main = new Main();
        menuPanel = new MenuPanel(main);
        }
 
    
    @Test
    void testPlayButtonExists() {
        Component[] components = menuPanel.getComponents();
        JButton playButton = null;
        for (Component comp : components) {
            if (comp instanceof JButton && comp.getY() == 520) {
                playButton = (JButton) comp;
                break;
            }
        }
        assertNotNull(playButton);
        assertEquals("PLAY", playButton.getText());
    }
    
    @Test
    void testExitButtonExists() {
        Component[] components = menuPanel.getComponents();
        JButton exitButton = null;
        for (Component comp : components) {
            if (comp instanceof JButton && comp.getY() == 380) {
                exitButton = (JButton) comp;
                break;
            }
        }
        assertNotNull(exitButton);
        assertEquals("EXIT", exitButton.getText());
    }
    
    @Test
    void testPlayButtonBounds() {
        Component[] components = menuPanel.getComponents();
        JButton playButton = null;
        for (Component comp : components) {
            if (comp instanceof JButton && comp.getY() == 520) {
                playButton = (JButton) comp;
                break;
            }
        }
        assertNotNull(playButton);
        assertEquals(284, playButton.getX());
        assertEquals(520, playButton.getY());
        assertEquals(200, playButton.getWidth());
        assertEquals(65, playButton.getHeight());
    }
    
    @Test
    void testExitButtonBounds() {
        Component[] components = menuPanel.getComponents();
        JButton exitButton = null;
        for (Component comp : components) {
            if (comp instanceof JButton && comp.getY() == 380) {
                exitButton = (JButton) comp;
                break;
            }
        }
        assertNotNull(exitButton);
        assertEquals(284, exitButton.getX());
        assertEquals(380, exitButton.getY());
        assertEquals(200, exitButton.getWidth());
        assertEquals(65, exitButton.getHeight());
    }
    
    @Test
    void testPanelLayoutIsNull() {
        assertNull(menuPanel.getLayout());
    }
}
