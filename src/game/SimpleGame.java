package game;

import game.GameEngine.GameState;
import game.GameEngine.Players;

public class SimpleGame extends GameEngine{
	public SimpleGame(int boardSize) {
		super(boardSize);
	}

	@Override
	public void makeMove(int row, int col, char letter) {
		if((board[row][col] != 'S') && (board[row][col] != 'O')) {
			board[row][col] = letter;
			if(getGameState() == GameState.PLAYING) {
				recordMove(row, col, letter);
				increaseNumberOfMovesMade();
			}
			if(getGameState() == GameState.REPLAY) {
				increaseTurnNumber();
				if(getTurnNumber() == getNumberOfMovesMade()) {
					resetTurnNumber();
				}
			}
			
			if(checkSOSFormed(row, col)) {
				if((playerTurn == Players.BLUEHUMAN) || (playerTurn == Players.BLUECOMPUTER)) {
					gameState = GameState.BLUEWINNER;
				}else if((playerTurn == Players.REDHUMAN) || (playerTurn == Players.REDCOMPUTER)) {
					gameState = GameState.REDWINNER;
				}
				recordToFile();
			}else if(!checkSOSFormed(row, col)) {
				if(checkBoardFull()) {
					gameState = GameState.TIEGAME;
				}else {
					switchTurn();
				}
			}
		}		
	}
}
