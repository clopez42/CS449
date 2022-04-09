package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GUI;
import game.GameEngine;
import game.GameEngine.GameMode;
import game.GameEngine.Players;
import game.GameEngine.GameState;

class TestSimpleGame {
	GameEngine game;
	GUI gui;

	@BeforeEach
	void setUp() throws Exception {
		game = new GameEngine(3, GameMode.SIMPLEGAME);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test //AC 2.1
	void testSimpleGameMode() {
		assertEquals(game.getGameMode(), GameMode.SIMPLEGAME);
	}
	
	@Test //AC 2.1
	void testSimpleGameModeOnGUI() {
		gui = new GUI();
		gui.setGameModeButton(GameMode.SIMPLEGAME);
		assertEquals(gui.getGameMode(), GameMode.SIMPLEGAME);
	}

	@Test //AC 5.1
	void testSOSFormation() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'O');
		game.makeMove(0, 2, 'S');
		assertEquals(GameState.BLUEWINNER, game.getGameState());
	}
	
	@Test //AC 5.2
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
		assertTrue(game.boardFull());
		assertEquals(GameState.TIEGAME, game.getGameState());
	}
}
