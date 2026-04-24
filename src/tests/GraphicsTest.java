package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import game.GamePanel;

import java.awt.Color;

public class GraphicsTest {

    GamePanel game = new GamePanel();

    //testing colors 
    @Test
    void testFireboyColor() {
        //fireboy should be red
        assertEquals(new Color(200, 50, 10), game.getFireboyColor());
        }

    @Test
    void testWatergirlColor() {
        //watergirl must be blue
        assertEquals(new Color(20, 80, 200), game.getWatergirlColor());
        }

    //testing alive status
    @Test
    void testFireboyAlive() {
        assertTrue(game.isFireboyAlive());
        }

    @Test
    void testWatergirlAlive() {
        assertTrue(game.isWatergirlAlive());
        }
    
    
    //dynamic movement test
    
    @Test
    void testFireboyMovesRight() {
        int startX = game.getFireboyX();
        game.player1.moveRight();
        game.updateGame(); 
        int newX = game.getFireboyX();
        assertTrue(newX > startX, "Fireboy should move right");
        }
    
    @Test
    void testFireboyMovesLeft() {
        int startX = game.getFireboyX();
        game.player1.moveLeft();
        game.updateGame(); 
        int newX = game.getFireboyX();
        assertTrue(newX < startX, "Fireboy should move left");
        }
    
    @Test
    void testWatergirlMovesRight() {
        int startX = game.getWatergirlX();
        game.player2.moveRight();
        game.updateGame(); 
        int newX = game.getWatergirlX();
        assertTrue(newX > startX, "Watergirl should move right");
        }
    
    @Test
    void testWatergirlMovesLeft() {
        int startX = game.getWatergirlX();
        game.player2.moveLeft();
        game.updateGame(); 
        int newX = game.getWatergirlX();
        assertTrue(newX < startX, "Watergirl should move left");
        }
    
    @Test
    void testFireboyJumps() {
        int startY = game.getFireboyY();
        game.player1.moveUp();
        game.player1.gravity();
        int newY = game.getFireboyY();
        assertTrue(newY < startY, "Fireboy should jump up (Y decreases)");
        }
    
    @Test
    void testWatergirlJumps() {
        int startY = game.getWatergirlY();
        game.player2.moveUp();
        game.player2.gravity();
        int newY = game.getWatergirlY();
        assertTrue(newY < startY, "Watergirl should jump up (Y decreases)");
        }
    
    @Test
    void testPlayerCannotMoveThroughLeftWall() {
        for (int i = 0; i < 20; i++) {
            game.player1.moveLeft();
            }
        
        int x = game.getFireboyX();
        assertTrue(x >= 0, "Player should not go beyond left wall");
        }
    
    @Test
    void testPlayerCannotMoveThroughRightWall() {
        for (int i = 0; i < 20; i++) {
            game.player1.moveRight();
            }
        
        int x = game.getFireboyX();
        assertTrue(x + 40 <= 800, "Player should not go beyond right wall");
        }
}