package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.GameEngine;
import game.GameEngine.GameState;
import game.GameEngine.Players;
import game.GeneralGame;
import game.SimpleGame;

class TestAutoPlay {
	GameEngine game;
	GameEngine game2;

	@BeforeEach
	void setUp() throws Exception {
		game = new SimpleGame(8);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test //AC 9.1 & 10.1
	void testComputerMode() {
		assertEquals(Players.BLUEHUMAN, game.getPlayerTurn());
		game.setBlueHumanComputerToggle(Players.BLUECOMPUTER);
		assertEquals(Players.REDHUMAN, game.getPlayerTurn());
	}
	
	@Test //AC 9.1 & 10.1
	void testAutoPlayMoves() {
		game2 = new GeneralGame(3);
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		game2.computerAutoPlay();
		assertEquals(GameState.PLAYING, game.getGameState());
		game2.computerAutoPlay();
		assertNotEquals(GameState.PLAYING, game2.getGameState());
	}
	
	@Test //AC 9.2
	void testToggleHumanComputerIntermittently() {
		game.setRedHumanComputerToggle(Players.REDCOMPUTER);
		game.makeMove(0,0,'S');
		game.makeMove(0,1,'S');
		game.setRedHumanComputerToggle(Players.REDHUMAN);
		game.makeMove(0,2,'S');
		assertEquals(Players.REDHUMAN, game.getPlayerTurn());
		game.makeMove(1,2,'S');
		assertEquals(Players.BLUEHUMAN, game.getPlayerTurn());
	}

}
