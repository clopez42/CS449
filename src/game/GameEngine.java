package game;

public class GameEngine {
	private int bluePlayerScore, redPlayerScore;
	private char[][] board;
	private int[][] sosCoordinates;
	private Players[] sosMadeBy;
	private int row1, col1, row2, col2;
	
	public enum GameMode{
		SIMPLEGAME, GENERALGAME
	}
	
	public enum GameState{
		PLAYING, BLUEWINNER, REDWINNER, TIEGAME, REPLAY
	}
	public static enum Players {
		BLUEPLAYER, REDPLAYER
	}
	
	GameMode gameMode;
	GameState gameState;
	Players playerTurn;
	
	public GameEngine(int boardSize, GameMode mode) {
		initGame(boardSize, mode);
	}
	
	public void initGame(int boardSize, GameMode mode) {
		setBoard(boardSize);
		setGameMode(mode);
		setGameState(GameState.PLAYING);
		bluePlayerScore = 0;
		redPlayerScore = 0;
		setPlayerTurn(Players.BLUEPLAYER);
		
		if(boardEmpty() == false) {
			System.out.println("Board is not empty");
		}
	}

	public void makeMove(int row, int col, char letter) {				
		if((board[row][col] != 'S') && (board[row][col] != 'O')) {
			board[row][col] = letter;
			if(gameMode == GameMode.SIMPLEGAME) {
				simpleGameMode(row, col);
			}else if(gameMode == GameMode.GENERALGAME) {
				generalGameMode(row, col);				
			}
		}
	}
	
	public void simpleGameMode(int row, int col) {
		if(checkSOSFormed(row, col)) {
			setSosCoordinates(row1, col1, row2, col2);
			setSosMadeBy(getPlayerTurn());
			if(playerTurn == Players.BLUEPLAYER) {
				gameState = GameState.BLUEWINNER;
			}else if(playerTurn == Players.REDPLAYER) {
				gameState = GameState.REDWINNER;
			}
		}else if(!checkSOSFormed(row, col)) {
			if(boardFull()) {
				gameState = GameState.TIEGAME;
			}else {
				switchTurn();
			}
		}
	}
	
	public void generalGameMode(int row, int col) {
		if(checkSOSFormed(row, col)) {
			setSosCoordinates(row1, col1, row2, col2);
			setSosMadeBy(getPlayerTurn());
			if(playerTurn == Players.BLUEPLAYER) {
				increaseBluePlayerScore();
			}else if(playerTurn == Players.REDPLAYER) {
				increaseRedPlayerScore();
			}
		}else if(checkSOSFormed(row, col) == false) {
			switchTurn();
		}
		
		if(boardFull()) {
			if(getBluePlayerScore() > getRedPlayerScore()) {
				gameState = GameState.BLUEWINNER;
			}else if(getRedPlayerScore() > getBluePlayerScore()) {
				gameState = GameState.REDWINNER;
			}else if(getBluePlayerScore() == getRedPlayerScore()) {
				gameState = GameState.TIEGAME;
			}
		}
	}
	
	public void switchTurn() {
		if(playerTurn == Players.BLUEPLAYER) {
			playerTurn = Players.REDPLAYER;
		}else {
			playerTurn = Players.BLUEPLAYER;
		}
	}
	
	public void resetGame(int boardSize, GameMode mode) {
		initGame(boardSize, mode);
	}
	
	public void setBoard(int size) {
		board = null;
		sosCoordinates = null;
		sosMadeBy = null;
		board = new char[size][size];
		sosCoordinates = new int[size*size/3][4];
		sosMadeBy = new Players[size*size/3];
	}
	
	public void setGameMode(GameMode mode) {
		gameMode = mode;
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

	public GameMode getGameMode() {
		return gameMode;
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
	
	public void increaseBluePlayerScore() {
		bluePlayerScore++;
	}
	
	public void increaseRedPlayerScore() {
		redPlayerScore++;
	}
	
	public boolean boardEmpty() {
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
	
	public boolean boardFull() {
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j<board.length; j++) {
				if(!(board[i][j] == 'S') && !(board[i][j] == 'O')) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkSOSFormed(int row, int col) {
		char initChar = board[row][col];
		char nextChar = ' ';
		char finalChar = 'S';
		
		if(initChar == 'S') {
			nextChar = 'O';
		}else if(initChar == 'O') {
			nextChar = 'S';
		}
		
		if(initChar == 'S') {
			row1 = row;
			col1 = col;
			for(int i = row - 1; i <= row + 1; i++) {
				for(int j = col - 1; j <= col + 1; j++) {					
					if((i >= 0) && (j >= 0) && (i < board.length) && (j < board.length) && (board[i][j] == nextChar)) {						
						if((i<row) && (j<col) && (i-1 >= 0) && (j-1 >= 0) && (board[i-1][j-1] == finalChar)) {
							row2 = i-1;
							col2 = j-1;
							return true;
						}else if((i<row) && (j==col) && (i-1 >= 0) && (board[i-1][j] == finalChar)) {
							row2 = i-1;
							col2 = j;
							return true;
						}else if((i<row) && (j>col) && (i-1 >= 0) && (j+1 < board.length) && (board[i+1][j+1] == finalChar)) {
							row2 = i+1;
							col2 = j+1;
							return true;
						}else if((i==row) && (j<col) && (j-1 >= 0) && (board[i][j-1] == finalChar)) {
							row2 = i;
							col2 = j-1;
							return true;
						}else if((i==row) && (j>col) && (j+1 < board.length) && (board[i][j+1] == finalChar)) {
							row2 = i-1;
							col2 = j-1;
							return true;
						}else if((i>row) && (j<col) && (i+1 < board.length) && (j-1 >= 0) && (board[i+1][j-1] == finalChar)) {
							row2 = i+1;
							col2 = j-1;
							return true;
						}else if((i>row) && (j==col) && (i+1 < board.length) && (board[i+1][j] == finalChar)) {
							row2 = i+1;
							col2 = j;
							return true;
						}else if((i>row) && (j>col) && (i+1 < board.length) && (j+1 < board.length) && (board[i+1][j+1] == finalChar)) {
							row2 = i+1;
							col2 = j+1;
							return true;
						}
					}
				}
			}
		}else if(initChar == 'O') {
			for(int i = row - 1; i <= row + 1; i++) {
				for(int j = col - 1; j <= col + 1; j++) {
					if((i >= 0) && (j >= 0) && (i < board.length) && (j < board.length) && (board[i][j] == nextChar)) {
						row1 = i;
						col1 = j;
						if((i<row) && (j<col) && (row+1 < board.length) && (col+1 < board.length) && (board[row+1][col+1] == finalChar)) {
							row2 = row+1;
							col2 = col+1;
							return true;
						}else if((i<row) && (j==col) && (row+1 < board.length) && (board[row+1][col] == finalChar)) {
							row2 = row+1;
							col2 = col;
							return true;
						}else if((i<row) && (j>col) && (row+1 < board.length) && (col-1 >= 0) && (board[row+1][col-1] == finalChar)) {
							row2 = row+1;
							col2 = col-1;
							return true;
						}else if((i==row) && (j<col) && (col+1 < board.length) && (board[row][col+1] == finalChar)) {
							row2 = row;
							col2 = col+1;
							return true;
						}							
					}
				}
			}
		}
		return false;
	}
}
