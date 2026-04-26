package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
/*
 * OLD MENU SYSTEM 
 * 
 * This class was used in the original version of the game.
 * It has been replaced by:
 *   - NewMainMenuPanel
 *   - DifficultyPanel
 *
 * This file is kept for reference to show project upgrade (.
 * (susan ogozi)
 */

/*
public class MenuPanelTest {
    
    private MenuPanel menuPanel;
    private Main main;
    
    //this runs before each test
    @BeforeEach
    void setUp() {
        main = new Main();
        menuPanel = new MenuPanel(main);
        }
    
    //this verifies Start button exists and has correct text
    @Test
    void testStartButtonExists() {
        JButton startButton = menuPanel.getStartButton();
        assertNotNull(startButton);
        assertEquals("START GAME", startButton.getText());
        }
    
    //this verifies Restart button exists and has correct text
    @Test
    void testRestartButtonExists() {
        JButton restartButton = menuPanel.getRestartButton();
        assertNotNull(restartButton);
        assertEquals("RESTART GAME", restartButton.getText());
        }
    
    //this verifies Exit button exists and has correct text
    @Test
    void testExitButtonExists() {
        JButton exitButton = menuPanel.getExitButton();
        assertNotNull(exitButton);
        assertEquals("EXIT", exitButton.getText());
        }
    
    //this verifies all three buttons are visible on normal menu
    @Test
    void testFullMenuShowsAllButtons() {
        menuPanel.showFullMenu();
        assertTrue(menuPanel.getStartButton().isVisible());
        assertTrue(menuPanel.getRestartButton().isVisible());
        assertTrue(menuPanel.getExitButton().isVisible());
        }
    
    //this verifies only Restart button shows during game over
    @Test
    void testGameOverMenuShowsOnlyRestartButton() {
        menuPanel.showGameOverMenu();
        assertFalse(menuPanel.getStartButton().isVisible());
        assertTrue(menuPanel.getRestartButton().isVisible());
        assertFalse(menuPanel.getExitButton().isVisible());
        }
        */
    