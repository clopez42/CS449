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
			
			if(checkSOSFormed(row, col)) {
				//System.out.println("sos made");
				if((playerTurn == Players.BLUEHUMAN) || (playerTurn == Players.BLUECOMPUTER)) {
					gameState = GameState.BLUEWINNER;
				}else if((playerTurn == Players.REDHUMAN) || (playerTurn == Players.REDCOMPUTER)) {
					gameState = GameState.REDWINNER;
				}
			}else if(!checkSOSFormed(row, col)) {
				if(checkBoardFull()) {
					gameState = GameState.TIEGAME;
				}else {
					switchTurn();
				}
			}
		}
		
		
		
		//System.out.println(getSosCoordinate(0,0) + ", " +  getSosCoordinate(0,1) + ", " + getSosCoordinate(0,2) + ", " + getSosCoordinate(0,3));
	}
}
