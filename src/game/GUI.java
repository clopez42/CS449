package game;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import game.GameEngine.GameMode;
import game.GameEngine.GameState;
import game.GameEngine.Players;

public class GUI extends JFrame{
	public static final int GAME_BOARD_SIZE = 1000;
	public static final int GRID_LINE_WIDTH = 8;
	public static final int GRID_LINE_WIDHT_HALF = GRID_LINE_WIDTH / 2;
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	private int cellSize;
	private int cellPadding;
	private int letterAdjustment;
	private int letterSize;
	
	private GameBoardCanvas gameBoardCanvas;
	private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    
    private JTextField boardSizeField = new JTextField(2);
    
	private JLabel boardSizeLabel = new JLabel ("Board Size");
	private JLabel statusBar = new JLabel("Status");
	private JLabel bluePlayerLbl = new JLabel("Blue player");
    private JLabel redPlayerLbl = new JLabel("Red player");
    
    private JButton newGameButton = new JButton("New Game");
	
    private static JRadioButton simpleGameButton = new JRadioButton("Simple Game");
    private static JRadioButton generalGameButton = new JRadioButton("General Game");
    private JRadioButton redSButton = new JRadioButton("S");
    private JRadioButton redOButton = new JRadioButton("O");
    private JRadioButton blueSButton = new JRadioButton("S");
    private JRadioButton blueOButton = new JRadioButton("O");
    
    private GameEngine game;
	
    public GUI() {
    	this(new GameEngine(8, getGameMode()));
    }
    
    public GUI(GameEngine game) {
    	this.game = game;
		setContentPane();
		setTitle("S-O-S");
		setBoard();
		setStatusBar();
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	private void setContentPane() {
		
		northPanel.add(simpleGameButton);
		northPanel.add(generalGameButton);
		northPanel.add(boardSizeLabel);
		northPanel.add(boardSizeField);
		
		southPanel.add(statusBar);
		southPanel.add(newGameButton);
		
		eastPanel.add(redPlayerLbl);
		eastPanel.add(redSButton);
		eastPanel.add(redOButton);
		
		westPanel.add(bluePlayerLbl);
		westPanel.add(blueSButton);
		westPanel.add(blueOButton);
		
		gameBoardCanvas = new GameBoardCanvas();
		gameBoardCanvas.setPreferredSize(new Dimension(GAME_BOARD_SIZE, GAME_BOARD_SIZE));
		
		Container contentPane = getContentPane();
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		contentPane.add(eastPanel, BorderLayout.EAST);
		contentPane.add(westPanel, BorderLayout.WEST);
		contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
		
		simpleGameButton.addActionListener(new SimpleGameButtonListener());
        generalGameButton.addActionListener(new GeneralGameButtonListener());
        newGameButton.addActionListener(new NewGameButtonListener());
        redSButton.addActionListener(new RedSButtonListener());
        redOButton.addActionListener(new RedOButtonListener());
        blueSButton.addActionListener(new BlueSButtonListener());
        blueOButton.addActionListener(new BlueOButtonListener());
	}

	public boolean checkValidBoardSize(String input) {
		try { 
	        Integer.parseInt(input); 
	        if(Integer.parseInt(input) >= 3) {
				return true;
			}else {
				statusBar.setText("Board Size Must Be 3 Or Greater.");
				return false;
			}
	    } catch(NumberFormatException e) { 
	    	statusBar.setText("Invalid Board Size Input");
	        return false; 
	    } catch(NullPointerException e) {
	    	statusBar.setText("Invalid Input");
	        return false;
	    }
	}
	
	public void setBoard() {
		cellSize = GAME_BOARD_SIZE / game.getBoardSize();
		cellPadding = cellSize / 2;
		letterAdjustment = cellSize / 4;
		letterSize = cellSize;
		
		boardSizeField.setText(Integer.toString(game.getBoardSize()));
		blueSButton.doClick();
		redSButton.doClick();
		
		repaint();
	}
	
	public void setStatusBar() {
		if(game.getGameState() == GameState.PLAYING) {
			if(game.getPlayerTurn() == Players.BLUEPLAYER) {
				statusBar.setText("Current Turn: Blue");
			}else if(game.getPlayerTurn() == Players.REDPLAYER) {
				statusBar.setText("Current Turn: Red");
			}
		}else if(game.getGameState() == GameState.BLUEWINNER) {
			statusBar.setText("Blue Player Wins!!");
		}else if(game.getGameState() == GameState.REDWINNER) {
			statusBar.setText("Red Player Wins!!");
		}else if(game.getGameState() == GameState.TIEGAME) {
			statusBar.setText("Tie Game!!");
		}
	}
	
	public void setGameModeButton(GameMode mode) {
		if(mode == GameMode.SIMPLEGAME) {
			simpleGameButton.doClick();
		}else if(mode == GameMode.GENERALGAME) {
			generalGameButton.doClick();
		}
	}
	
	public void setBoardSizeTextField(String size) {
		boardSizeField.setText(size);
	}
	
	public static GameMode getGameMode() {
		if(simpleGameButton.isSelected()) {
			return GameMode.SIMPLEGAME;
		}else if(generalGameButton.isSelected()) {
			return GameMode.GENERALGAME;
		}else {
			simpleGameButton.doClick();
			return GameMode.SIMPLEGAME;
		}
	}
	
	public String getStatus() {
		return statusBar.getText();
	}
	
	public void newGame() {
		newGameButton.doClick();
	}
	
	class GameBoardCanvas extends JPanel {
		GameBoardCanvas(){
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (game.getGameState() == GameState.PLAYING) {
						int row = e.getY() / cellSize;
						int col = e.getX() / cellSize;
						if(game.getPlayerTurn() == Players.BLUEPLAYER) {
							if(blueSButton.isSelected()) {
								game.makeMove(row, col, 'S');
							}else if(blueOButton.isSelected()) {
								game.makeMove(row, col, 'O');
							}
						}else if(game.getPlayerTurn() == Players.REDPLAYER) {
							if(redSButton.isSelected()) {
								game.makeMove(row, col, 'S');
							}else if(redOButton.isSelected()) {
								game.makeMove(row, col, 'O');
							}
						}
					}
					repaint();
					setStatusBar();
				}
			});
		}
			
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			drawGridLines(g);
			drawBoard(g);
		}
		
		private void drawGridLines(Graphics g) {			
			g.setColor(Color.GRAY);
			for (int row = 1; row < game.getBoardSize(); ++row) {
				g.fillRoundRect(0, cellSize * row - GRID_LINE_WIDHT_HALF, GAME_BOARD_SIZE, 
						GRID_LINE_WIDTH, GRID_LINE_WIDTH, GRID_LINE_WIDTH);
			}
			for (int col = 1; col < game.getBoardSize(); ++col) {
				g.fillRoundRect(cellSize * col - GRID_LINE_WIDHT_HALF, 0, GRID_LINE_WIDTH,
						GAME_BOARD_SIZE, GRID_LINE_WIDTH, GRID_LINE_WIDTH);
			}

		}
		
		private void drawBoard(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, letterSize - letterAdjustment));
			
			for (int row = 0; row < game.getBoardSize(); row++) {
				for (int col = 0; col < game.getBoardSize(); col++) {					
					int x1 = col * cellSize + cellPadding - letterAdjustment;
					int y1 = row * cellSize + cellPadding + letterAdjustment;
					if(game.getCell(row, col) == 'S') {
						g2d.drawString("S", x1, y1);
					}else if(game.getCell(row, col) == 'O') {
						g2d.drawString("O", x1, y1);
					}
				}
			}
			
			for(int i = 0; i < game.getCombinedScore(); i++) {
				if(game.getSosMadeBy(i) == Players.BLUEPLAYER) {
					g2d.setColor(Color.BLUE);
				}else if(game.getSosMadeBy(i) == Players.REDPLAYER) {
					g2d.setColor(Color.RED);
				}
				
				int row1 = game.getSosCoordinate(i,0) * cellSize + cellPadding;
				int col1 = game.getSosCoordinate(i,1) * cellSize + cellPadding;
				int row2 = game.getSosCoordinate(i,2) * cellSize + cellPadding;
				int col2 = game.getSosCoordinate(i,3) * cellSize + cellPadding;
				
				g2d.drawLine(col1, row1, col2 ,row2);
			}
		}
	}
	
	public class SimpleGameButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {    
    		if(!simpleGameButton.isSelected() && !generalGameButton.isSelected()) {
    			simpleGameButton.doClick();
    		}
    		else if(simpleGameButton.isSelected() && generalGameButton.isSelected()) {
    			generalGameButton.doClick();
    		}
    	}
    }
    
    public class GeneralGameButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(!generalGameButton.isSelected() && !simpleGameButton.isSelected()) {
    			generalGameButton.doClick();
    		}
    		else if(generalGameButton.isSelected() && simpleGameButton.isSelected()) {
    			simpleGameButton.doClick();
    		}
    	}
    }
    
    public class NewGameButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(checkValidBoardSize(boardSizeField.getText()) == true) {
    			game.resetGame(Integer.parseInt(boardSizeField.getText()), getGameMode());
    			setBoard();
    			setStatusBar();
    		}		
    	}
    }
    
    private class RedSButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {    
    		if(redSButton.isSelected() && redOButton.isSelected()) {
    			redOButton.doClick();
    		}else if(!redSButton.isSelected() && !redOButton.isSelected()) {
    			redSButton.doClick();
    		}
    	}
    }
    
    public class RedOButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(redSButton.isSelected() && redOButton.isSelected()) {
    			redSButton.doClick();
    		}else if(!redSButton.isSelected() && !redOButton.isSelected()) {
    			redOButton.doClick();
    		}    		
    	}
    }
    
    public class BlueSButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(blueSButton.isSelected() && blueOButton.isSelected()) {
    			blueOButton.doClick();
    		}else if(!blueSButton.isSelected() && !blueOButton.isSelected()) {
    			blueSButton.doClick();
    		}
    	}
    }
    
    public class BlueOButtonListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(blueSButton.isSelected() && blueOButton.isSelected()) {
    			blueSButton.doClick();
    		}else if(!blueSButton.isSelected() && !blueOButton.isSelected()) {
    			blueOButton.doClick();
    		}  
    	}

    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}
}
