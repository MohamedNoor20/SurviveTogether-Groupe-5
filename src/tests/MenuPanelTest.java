package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.*;

import menu.MenuPanel;

public class MenuPanelTest {

    private MenuPanel menuPanel;
    @BeforeEach
    void setUp() {
    	// can't use real mean
        menuPanel = new MenuPanel(null); 
        
        menuPanel.setSize(768, 768);
        menuPanel.doLayout();
    }

    private JButton findButtonByText(String text) {
        for (Component comp : menuPanel.getComponents()) {
            if (comp instanceof JButton btn && text.equals(btn.getText())) {
                return btn;
            }
        }
        return null;
    }

    @Test
    void testPlayButtonExists() {
        JButton playButton = findButtonByText("PLAY");
        assertNotNull(playButton);
    }

    @Test
    void testExitButtonExists() {
        JButton exitButton = findButtonByText("EXIT");
        assertNotNull(exitButton);
    }

    @Test
    void testButtonsHaveValidBounds() {
        JButton playButton = findButtonByText("PLAY");
        JButton exitButton = findButtonByText("EXIT");

        assertNotNull(playButton);
        assertNotNull(exitButton);

        // Checking if they are positioned and sized correctly 
        assertTrue(playButton.getWidth() > 0);
        assertTrue(playButton.getHeight() > 0);

        assertTrue(exitButton.getWidth() > 0);
        assertTrue(exitButton.getHeight() > 0);

        // Check vertical order 
        assertTrue(exitButton.getY() < playButton.getY());
    }

    @Test
    void testPanelLayoutIsNull() {
        assertNull(menuPanel.getLayout());
    }
}