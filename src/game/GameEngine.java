package game;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import game.GameEngine.Players;

public class GameEngine {
	private int[][] sosCoordinates, gameRecordCoordinates;
	private Players[] sosMadeBy;
	protected char[][] board;
	private char[] gameRecordLetters;
	private int bluePlayerScore, redPlayerScore, numberOfMovesMade, turnNumber;
	private boolean record;
	private Random rand = new Random();
	private PrintWriter recordFile;
	
	public enum GameState{
		PLAYING, BLUEWINNER, REDWINNER, TIEGAME, REPLAY
	}
	public static enum Players {
		BLUEHUMAN, REDHUMAN, BLUECOMPUTER, REDCOMPUTER
	}
	
	GameState gameState;
	Players playerTurn;
	Players bluePlayer;
	Players redPlayer;
	
	public GameEngine(int boardSize) {
		initGame(boardSize);
	}
	
	public void initGame(int boardSize) {
		board = new char[boardSize][boardSize];
		sosCoordinates = new int[boardSize*boardSize][4];
		sosMadeBy = new Players[boardSize*boardSize];
		gameRecordCoordinates = new int[boardSize*boardSize][2];
		gameRecordLetters = new char[boardSize*boardSize];
		try {
			recordFile = new PrintWriter("record.txt");
		} catch (FileNotFoundException e) {
			System.out.println("fail");
			e.printStackTrace();
		}
		bluePlayerScore = 0;
		redPlayerScore = 0;
		numberOfMovesMade = 0;
		
		setBlueHumanComputerToggle(Players.BLUEHUMAN);
		setRedHumanComputerToggle(Players.REDHUMAN);
		playerTurn = bluePlayer;
		
		record = false;
		
		setGameState(GameState.PLAYING);
		setPlayerTurn(bluePlayer);
	}

	public void makeMove(int row, int col, char letter) {				
		if((board[row][col] != 'S') && (board[row][col] != 'O')) {
			board[row][col] = letter;
		}
	}
	
	public void switchTurn() {
		if(playerTurn == bluePlayer) {
			playerTurn = redPlayer;
		}else {
			playerTurn = bluePlayer;
		}
	}
	
	public void computerAutoPlay() {
		int target = rand.nextInt(getNumberOfEmptyCells());
		int index = 0;
		
		for (int i = 0; i < getBoardSize(); i++) {
			for (int j = 0; j < getBoardSize(); j++) {
				if ((getCell(i,j) != 'S') && (getCell(i,j) != 'O')) {
					if (target == index) {
						makeMove(i, j, getAutoPlayLetter());
						return;
					} else {
						index++;
					}
				}
			}
		}
	}
	
	public void computerMove() {
		if(getGameState() == GameState.PLAYING) {
			computerAutoPlay();
		}
	}
	
	public void recordMove(int row, int col, char letter) {
		gameRecordCoordinates[numberOfMovesMade][0] = row;
		gameRecordCoordinates[numberOfMovesMade][1] = col;
		gameRecordLetters[numberOfMovesMade] = letter;
	}
	
	public void recordToFile() {
		if(wasRecorded()) {
			for(int i=0; i<getNumberOfMovesMade(); i++) {
				int move = i + 1;
				recordFile.println("Move" + move + ": row= " + getGameRecordCoordinates(i,0) + ", column= " + getGameRecordCoordinates(i,1) 
				+ ", letter= '" + getCell(getGameRecordCoordinates(i,0),getGameRecordCoordinates(i,1)) + "'\n");
			}
			if(getGameState() == GameState.BLUEWINNER) {
				recordFile.println("Blue Player Wins!!");
			}else if(getGameState() == GameState.REDWINNER) {
				recordFile.println("Red Player Wins!!");
			}else if(getGameState() == GameState.TIEGAME) {
				recordFile.println("Tie Game!!");
			}
		}
		recordFile.close();
	}
	
	public void setPlayerTurn(Players player) {
		playerTurn = player;
		
	}
	
	public void setGameState(GameState state) {
		gameState = state;
	}
	
	public void setSosCoordinates(int row1, int col1, int row2, int col2) {
		int index = getCombinedScore();
		sosCoordinates[index][0] = row1;
		sosCoordinates[index][1] = col1;
		sosCoordinates[index][2] = row2;
		sosCoordinates[index][3] = col2;
	}
	
	public void setSosMadeBy(Players player) {
		sosMadeBy[getBluePlayerScore()+getRedPlayerScore()] = player;
	}
	
	public void setBlueHumanComputerToggle(Players mode) {
		bluePlayer = mode;
		if((getPlayerTurn() == Players.BLUEHUMAN) && (getBluePlayer() == Players.BLUECOMPUTER) && (getGameState() == GameState.PLAYING)) {
			playerTurn = bluePlayer;
			computerAutoPlay();
		}		
	}
	
	public void setRedHumanComputerToggle(Players mode) {
		redPlayer = mode;
		if((getPlayerTurn() == Players.REDHUMAN) && (getRedPlayer() == Players.REDCOMPUTER) && (getGameState() == GameState.PLAYING)) {
			playerTurn = redPlayer;
			computerAutoPlay();
		}
	}
	
	public void setRecord(boolean record) {
		this.record = record;
	}
	
	public void setReplay(int boardSize) {
		gameState = GameState.REPLAY;
		board = new char[boardSize][boardSize];
		sosCoordinates = new int[boardSize*boardSize][4];
		sosMadeBy = new Players[boardSize*boardSize];
		bluePlayerScore = 0;
		redPlayerScore = 0;
		setBlueHumanComputerToggle(Players.BLUECOMPUTER);
		setRedHumanComputerToggle(Players.REDCOMPUTER);
		setPlayerTurn(getBluePlayer());
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public Players getPlayerTurn() {
		return playerTurn;
	}

	public char getCell(int row, int col) {
		return board[row][col];
	}
	
	public int getBluePlayerScore() {
		return bluePlayerScore;
	}
	
	public int getRedPlayerScore() {
		return redPlayerScore;
	}
	
	public int getCombinedScore() {
		return getBluePlayerScore() + getRedPlayerScore();
	}
	
	public int getBoardSize() {
		return board.length;
	}
	
	public int getSosCoordinate(int row, int col) {
		return sosCoordinates[row][col];
	}
	
	public Players getSosMadeBy(int i) {
		return sosMadeBy[i];
	}
	
	public Players getBluePlayer() {
		return bluePlayer;
	}
	
	public Players getRedPlayer() {
		return redPlayer;
	}
	
	public char getAutoPlayLetter() {
		int coin = rand.nextInt(2);
    	
    	if(coin == 0) {
    		return 'S';
    	}else if(coin == 1){
    		return 'O';
    	}else {
    		return 'S';
    	}
	}
	
	private int getNumberOfEmptyCells() {
		int numberOfEmptyCells = 0;
		for (int i = 0; i < getBoardSize(); i++) {
			for (int j = 0; j < getBoardSize(); j++) {
				if ((getCell(i,j) != 'S') && (getCell(i,j) != 'O')) {
					numberOfEmptyCells++;
				}
			}
		}
		return numberOfEmptyCells;
	}
	
	public int getGameRecordCoordinates(int move, int coordinate) {
		return gameRecordCoordinates[move][coordinate];
	}
	
	public char getGameRecordLetter(int move) {
		return gameRecordLetters[move];
	}
	
	public int getNumberOfMovesMade() {
		return numberOfMovesMade;
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}
	
	public boolean wasRecorded() {
		return record;
	}
	
	public void increaseScore() {
		if((getPlayerTurn() == Players.BLUEHUMAN) || (getPlayerTurn() == Players.BLUECOMPUTER)) {
			bluePlayerScore++;
		}else if((getPlayerTurn() == Players.REDHUMAN) || (getPlayerTurn() == Players.REDCOMPUTER)) {
			redPlayerScore++;
		}
	}
	
	public void increaseBluePlayerScore() {
		bluePlayerScore++;
	}
	
	public void increaseRedPlayerScore() {
		redPlayerScore++;
	}
	
	public void increaseNumberOfMovesMade() {
		numberOfMovesMade++;
	}
	
	public void increaseTurnNumber() {
		turnNumber++;
	}
	
	public void resetTurnNumber() {
		turnNumber = 0;
	}
	
	public boolean checkFullAutoPlay() {
		if((bluePlayer == Players.BLUECOMPUTER) && (redPlayer == Players.REDCOMPUTER)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkPartialAutoPlay() {
		if((bluePlayer == Players.BLUEHUMAN) && (redPlayer == Players.REDCOMPUTER) && (playerTurn == redPlayer)) {
			return true;
		}else if((bluePlayer == Players.BLUECOMPUTER) && (redPlayer == Players.REDHUMAN) && (playerTurn == bluePlayer)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkBoardEmpty() {
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j<board.length; j++) {
				if((board[i][j] == 'S') || (board[i][j] == 'O')) {
					return false;
				}else {
					return true; 
				}
			}
		}
		return false;
	}
	
	public boolean checkBoardFull() {
		int count = 0;
		int target = getBoardSize() * getBoardSize();
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j<board.length; j++) {				
				if((board[i][j] == 'S') || (board[i][j] == 'O')) {
					count++;
				}
			}
		}
		if(count == target) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean checkGameOver() {
		if(getGameState() == GameState.PLAYING) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean checkSOSFormed(int row, int col) {
		char initChar = board[row][col];
		char nextChar = ' ';
		char finalChar = 'S';
		boolean sosMade = false;
		
		if(initChar == 'S') {
			nextChar = 'O';
		}else if(initChar == 'O') {
			nextChar = 'S';
		}
		
		if(initChar == 'S') {
			for(int i = row - 1; i <= row + 1; i++) {
				for(int j = col - 1; j <= col + 1; j++) {					
					if((i >= 0) && (j >= 0) && (i < board.length) && (j < board.length) && (board[i][j] == nextChar)) {						
						if((i<row) && (j<col) && (i-1 >= 0) && (j-1 >= 0) && (board[i-1][j-1] == finalChar)) {
							setSosCoordinates(row, col, i-1, j-1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i<row) && (j==col) && (i-1 >= 0) && (board[i-1][j] == finalChar)) {
							setSosCoordinates(row, col, i-1, j);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i<row) && (j>col) && (i-1 >= 0) && (j+1 < board.length) && (board[i-1][j+1] == finalChar)) {
							setSosCoordinates(row, col, i-1, j+1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i==row) && (j<col) && (j-1 >= 0) && (board[i][j-1] == finalChar)) {
							setSosCoordinates(row, col, i, j-1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i==row) && (j>col) && (j+1 < board.length) && (board[i][j+1] == finalChar)) {
							setSosCoordinates(row, col, i, j+1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i>row) && (j<col) && (i+1 < board.length) && (j-1 >= 0) && (board[i+1][j-1] == finalChar)) {
							setSosCoordinates(row, col, i+1, j-1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i>row) && (j==col) && (i+1 < board.length) && (board[i+1][j] == finalChar)) {
							setSosCoordinates(row, col, i+1, j);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i>row) && (j>col) && (i+1 < board.length) && (j+1 < board.length) && (board[i+1][j+1] == finalChar)) {
							setSosCoordinates(row, col, i+1, j+1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}
					}
				}
			}
			return sosMade;
		}else if(initChar == 'O') {
			for(int i = row - 1; i <= row + 1; i++) {
				for(int j = col - 1; j <= col + 1; j++) {
					if((i >= 0) && (j >= 0) && (i < board.length) && (j < board.length) && (board[i][j] == nextChar)) {
						if((i<row) && (j<col) && (row+1 < board.length) && (col+1 < board.length) && (board[row+1][col+1] == finalChar)) {
							setSosCoordinates(i, j, row+1, col+1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i<row) && (j==col) && (row+1 < board.length) && (board[row+1][col] == finalChar)) {
							setSosCoordinates(i, j, row+1, col);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i<row) && (j>col) && (row+1 < board.length) && (col-1 >= 0) && (board[row+1][col-1] == finalChar)) {
							setSosCoordinates(i, j, row+1, col-1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}else if((i==row) && (j<col) && (col+1 < board.length) && (board[row][col+1] == finalChar)) {
							setSosCoordinates(i, j, row, col+1);
							setSosMadeBy(getPlayerTurn());
							increaseScore();
							sosMade = true;
						}							
					}
				}
			}
			return sosMade;
		}
		return sosMade;
	}
}
