package tests;

import game.*;
import game.GameEngine.GameMode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGameSetUp {
	GUI gui;
	GameEngine game;

	@BeforeEach
	void setUp() throws Exception {
		gui = new GUI();
		game = new GameEngine(8, GameMode.SIMPLEGAME);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test	//AC 1.1
	void testInvalidInput() {
		assertFalse(gui.checkValidBoardSize("A"));
	}
	
	@Test	//AC 1.2
	void testGridSizeTooSmall() {
		assertFalse(gui.checkValidBoardSize("2"));
	}
	
	@Test
	void testValidInput() {
		assertTrue(gui.checkValidBoardSize("3"));
	}
	
	@Test //AC 3.2
	void testImproperNewGameSetup() {
		gui.setBoardSizeTextField("A");
		gui.newGame();
		assertEquals("Invalid Board Size Input", gui.getStatus());
	}
	
	@Test //AC 3.2
	void testImproperNewGameSetup2() {
		gui.setBoardSizeTextField("1");
		gui.newGame();
		assertEquals("Board Size Must Be 3 Or Greater.", gui.getStatus());
	}
	
	@Test //AC 3.1
	void testClearBoard() {
		game.makeMove(0, 0, 'S');
		game.makeMove(1, 1, 'O');
		game.makeMove(3, 0, 'S');
		game.resetGame(6, GameMode.SIMPLEGAME);		
		assertTrue(game.boardEmpty());
	}
}
