package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameEngine;
import game.GameEngine.Players;

class TestMoves {
	GameEngine game;

	@BeforeEach
	void setUp() throws Exception {
		game = new GameEngine(4);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test //AC 4.1 & 6.1
	void testSMove() {
		game.makeMove(0, 0, 'S');
		assertEquals('S', game.getCell(0,0));
	}
	
	@Test //AC 4.1 & 6.1
	void testOMove() {
		game.makeMove(3, 3, 'O');
		assertEquals( 'O', game.getCell(3,3));
	}
	
	@Test //AC 8.1 
	void testSwitchTurn() {
		game.setPlayerTurn(Players.BLUEHUMAN);
		game.switchTurn();
		assertEquals(Players.REDHUMAN, game.getPlayerTurn());
	}
	
	@Test //AC 4.2 & 6.2
	void testSMoveNonVacantCell() {
		game.makeMove(0, 0, 'O');
		game.makeMove(0, 0, 'S');
		assertEquals('O', game.getCell(0, 0));
	}
	
	@Test //AC 4.2 & 6.2
	void testOMoveNonVacantCell() {
		game.makeMove(1, 1, 'S');
		game.makeMove(1, 1, 'O');
		assertEquals('S', game.getCell(1, 1));
	}
}
