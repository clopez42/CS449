package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameEngine;
import game.SimpleGame;

class TestRecordGame {
	GameEngine game;

	@BeforeEach
	void setUp() throws Exception {
		game = new SimpleGame(3);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test //AC 11.1
	void testOrderOfMovesRecordedToArray() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'S');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(1, 2, 'S');
		game.makeMove(2, 0, 'S');
		game.makeMove(2, 1, 'S');
		game.makeMove(2, 2, 'S');
		assertEquals(0, game.getGameRecordCoordinates(6,1));
		assertEquals(2, game.getGameRecordCoordinates(7,0));
		assertEquals(1, game.getGameRecordCoordinates(7,1));
		assertEquals('S', game.getGameRecordLetter(7));
	}
	
	@Test //AC 11.1 & 11.2
	void testRecordSelected() {
		game.setRecord(true);
		assertTrue(game.wasRecorded());
	}

	@Test //AC 11.3
	void testGameNotRecorded() {
		game.setRecord(true);
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'S');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(1, 2, 'S');
		game.makeMove(2, 0, 'S');
		game.setRecord(false);
		game.makeMove(2, 1, 'S');
		game.makeMove(2, 2, 'S');
		assertFalse(game.wasRecorded());
	}
	
	@Test
	void testGameOver() {
		game.makeMove(0, 0, 'S');
		game.makeMove(0, 1, 'S');
		game.makeMove(0, 2, 'S');
		game.makeMove(1, 0, 'S');
		game.makeMove(1, 1, 'S');
		game.makeMove(1, 2, 'S');
		game.makeMove(2, 0, 'S');
		game.makeMove(2, 1, 'S');
		game.makeMove(2, 2, 'S');
		assertTrue(game.checkGameOver());
	}
}
