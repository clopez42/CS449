package game;

import game.GameEngine.Players;

public class GeneralGame extends GameEngine{
	public GeneralGame(int boardSize) {
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
			
			if(checkSOSFormed(row, col) == false) {
				switchTurn();
			}
			
			if(checkBoardFull() == true) {
				if(getBluePlayerScore() > getRedPlayerScore()) {
					gameState = GameState.BLUEWINNER;
				}else if(getRedPlayerScore() > getBluePlayerScore()) {
					gameState = GameState.REDWINNER;
				}else if(getBluePlayerScore() == getRedPlayerScore()) {
					gameState = GameState.TIEGAME;
				}
				recordToFile();
			}
		}
	}
}
