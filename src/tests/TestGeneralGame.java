package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.GameEngine;
import game.GameEngine.GameState;
import game.GameEngine.Players;
import game.GUI;
import game.GeneralGame;

class TestGeneralGame {
	GameEngine game;
	GUI gui;

	@BeforeEach
	void setUp() throws Exception {
		game = new GeneralGame(3);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test //AC 6.3
	void testSOSFormation() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'O');
		game.makeMove(0, 2, 'S');
		assertEquals(1, game.getBluePlayerScore());
		assertEquals(Players.BLUEHUMAN, game.getPlayerTurn());
	}
	
	@Test //AC 7.2
	void testTieGame() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'S');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(1, 2, 'S');
		game.makeMove(2, 0, 'S');
		game.makeMove(2, 1, 'S');
		game.makeMove(2, 2, 'S');
		assertTrue(game.checkBoardFull());
		assertEquals(0, game.getBluePlayerScore());
		assertEquals(0, game.getRedPlayerScore());
		assertEquals(GameState.TIEGAME, game.getGameState());
	}
	
	@Test //AC 7.2
	void testTieGame2() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'O');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 2, 'O');
		game.makeMove(2, 2, 'S');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(2, 0, 'S');
		game.makeMove(2, 1, 'S');
		assertTrue(game.checkBoardFull());
		assertEquals(1, game.getBluePlayerScore());
		assertEquals(1, game.getRedPlayerScore());
		assertEquals(GameState.TIEGAME, game.getGameState());
	}
	
	@Test //AC 7.1
	void testBlueWinnerInGeneralGame() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'O');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(1, 2, 'S');
		game.makeMove(2, 0, 'S');
		game.makeMove(2, 1, 'S');
		game.makeMove(2, 2, 'S');
		assertTrue(game.checkBoardFull());
		assertEquals(1, game.getBluePlayerScore());
		assertEquals(0, game.getRedPlayerScore());
		assertEquals(GameState.BLUEWINNER, game.getGameState());
	}
	
	@Test //AC 7.1
	void testRedWinnerInGeneralGame() {
		game.makeMove(0, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(0, 2, 'S');
		game.makeMove(0, 1, 'O');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 2, 'S');
		game.makeMove(2, 0, 'S');
		game.makeMove(2, 1, 'S');
		game.makeMove(2, 2, 'S');
		assertTrue(game.checkBoardFull());
		assertEquals(0, game.getBluePlayerScore());
		assertEquals(1, game.getRedPlayerScore());
		assertEquals(GameState.REDWINNER, game.getGameState());
	}
	
	@Test //AC 6.3
	void testSOSTracking() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'O');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 2, 'O');
		game.makeMove(2, 2, 'S');
		assertEquals(0, game.getSosCoordinate(0,0));
		assertEquals(2, game.getSosCoordinate(0,1));
		assertEquals(0, game.getSosCoordinate(0,2));
		assertEquals(0, game.getSosCoordinate(0,3));
		assertEquals(2, game.getSosCoordinate(1,0));
		assertEquals(2, game.getSosCoordinate(1,1));
		assertEquals(0, game.getSosCoordinate(1,2));
		assertEquals(2, game.getSosCoordinate(1,3));
		assertEquals(Players.BLUEHUMAN, game.getSosMadeBy(0));
		assertEquals(Players.REDHUMAN, game.getSosMadeBy(1));
	}
}
