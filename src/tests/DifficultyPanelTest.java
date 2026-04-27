package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import menu.DifficultyPanel;
import griffith.Main;

class DifficultyPanelTest {

	// Fake Main to track calls // cant use real main
	static class FakeMain extends Main {
		String startedLevel = null;
		boolean mainMenuShown = false;

		@Override
		public void startGame(String difficulty) {
			startedLevel = difficulty;
		}

		@Override
		public void showMainMenu() {
			mainMenuShown = true;
		}
	}

	@Test
	void testEasyButtonStartsGame() {
		FakeMain main = new FakeMain();
		DifficultyPanel panel = new DifficultyPanel(main);

		panel.handleClick(new Point(250, 160)); // inside EASY

		assertEquals("easy", main.startedLevel);
	}
	// inside MEDIUM
	
	@Test
	void testMediumButtonStartsGame() {
		FakeMain main = new FakeMain();
		DifficultyPanel panel = new DifficultyPanel(main);

		panel.handleClick(new Point(250, 260));

		assertEquals("medium", main.startedLevel);
	}
	// same idea for the other levels

	@Test
	void testClickOutsideDoesNothing() {
		FakeMain main = new FakeMain();
		DifficultyPanel panel = new DifficultyPanel(main);

		panel.handleClick(new Point(50, 50)); // outside buttons

		assertNull(main.startedLevel);
		assertFalse(main.mainMenuShown);
	}
}