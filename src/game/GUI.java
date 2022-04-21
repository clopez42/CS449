package game;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import game.GameEngine.GameState;
import game.GameEngine.Players;

public class GUI extends JFrame{
	public static final int GAME_BOARD_SIZE = 800;
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
    private final JButton replayButton = new JButton("Replay");
    private static JRadioButton simpleGameButton = new JRadioButton("Simple Game");
    private static JRadioButton generalGameButton = new JRadioButton("General Game");
    private JRadioButton redSButton = new JRadioButton("S");
    private JRadioButton redOButton = new JRadioButton("O");
    private JRadioButton blueSButton = new JRadioButton("S");
    private JRadioButton blueOButton = new JRadioButton("O");
    private JRadioButton blueHumanButton = new JRadioButton("Human");
    private JRadioButton redHumanButton = new JRadioButton("Human");
    private JRadioButton redComputerButton = new JRadioButton("Computer");
    private JRadioButton blueComputerButton = new JRadioButton("Computer");
    private JCheckBox recordCheckBox = new JCheckBox("Record Game");
    
    private GameEngine game;
	
    public GUI() {
    	this(new SimpleGame(8));
    	simpleGameButton.doClick();
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
		northPanel.add(Box.createHorizontalStrut(375));
		northPanel.add(simpleGameButton);
		northPanel.add(generalGameButton);
		northPanel.add(Box.createHorizontalStrut(325));
		northPanel.add(boardSizeLabel);
		northPanel.add(boardSizeField);
		
		southPanel.add(recordCheckBox);
		southPanel.add(Box.createHorizontalStrut(350));
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		southPanel.add(statusBar);
		southPanel.add(Box.createHorizontalStrut(350));
		newGameButton.setHorizontalAlignment(SwingConstants.RIGHT);
		southPanel.add(newGameButton);
		
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setPreferredSize(new Dimension(150, GAME_BOARD_SIZE));		
		eastPanel.add(Box.createVerticalStrut(150));
		redPlayerLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		eastPanel.add(redPlayerLbl);
		eastPanel.add(Box.createVerticalStrut(10));
		redHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		eastPanel.add(redHumanButton);
		eastPanel.add(Box.createVerticalStrut(3));
		redSButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		eastPanel.add(redSButton);
		redSButton.addActionListener(new RedSButtonListener());
		redOButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		eastPanel.add(redOButton);
		eastPanel.add(Box.createVerticalStrut(15));
		redComputerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		eastPanel.add(redComputerButton);
		eastPanel.add(Box.createVerticalStrut(480));
		replayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		eastPanel.add(replayButton);
		
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.setPreferredSize(new Dimension(150, GAME_BOARD_SIZE));	
		westPanel.add(Box.createVerticalStrut(150));
		bluePlayerLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		westPanel.add(bluePlayerLbl);
		westPanel.add(Box.createVerticalStrut(10));
		blueHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		westPanel.add(blueHumanButton);
		westPanel.add(Box.createVerticalStrut(3));
		blueSButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		westPanel.add(blueSButton);
		blueOButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		westPanel.add(blueOButton);
		westPanel.add(Box.createVerticalStrut(15));
		blueComputerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		westPanel.add(blueComputerButton);
		
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
        redOButton.addActionListener(new RedOButtonListener());
        blueSButton.addActionListener(new BlueSButtonListener());
        blueOButton.addActionListener(new BlueOButtonListener());
        blueHumanButton.addActionListener(new BlueHumanButtonListener());
        blueComputerButton.addActionListener(new BlueComputerButtonListener());
        redHumanButton.addActionListener(new RedHumanButtonListener());
        redComputerButton.addActionListener(new RedComputerButtonListener());
        recordCheckBox.addActionListener(new RecordListener());
        replayButton.addActionListener(new ReplayListener());
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
		blueHumanButton.doClick();
		redHumanButton.doClick();
		blueSButton.doClick();
		redSButton.doClick();
		replayButton.setEnabled(false);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		repaint();
	}
	
	public void setStatusBar() {
		if(game.getGameState() == GameState.PLAYING) {
			if((game.getPlayerTurn() == Players.BLUEHUMAN) || (game.getPlayerTurn() == Players.BLUECOMPUTER)) {
				statusBar.setText("Current Turn: Blue");
			}else if((game.getPlayerTurn() == Players.REDHUMAN) || (game.getPlayerTurn() == Players.BLUECOMPUTER)) {
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
	
	public void setBoardSizeTextField(String size) {
		boardSizeField.setText(size);
	}
	
	public String getStatus() {
		return statusBar.getText();
	}
	
	public void newGame() {
		newGameButton.doClick();
	}
	
	public void checkComputerVsComputer() {
		if(game.checkFullAutoPlay() == true) {
			game.computerMove();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
			setStatusBar();
		}
	}
	
	public void checkHumanVsComputer() {
		if(game.checkPartialAutoPlay() == true) {
			game.computerMove();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
			setStatusBar();
		}
	}
	
	public void checkGameOver() {
		if(game.checkGameOver() && game.wasRecorded()) {
			replayButton.setEnabled(true);
		}
	}
	
	public void checkReplay() {
		if((game.getGameState() == GameState.REPLAY) && (game.getTurnNumber() <= game.getNumberOfMovesMade())) {
			game.makeMove(game.getGameRecordCoordinates(game.getTurnNumber(), 0),
				game.getGameRecordCoordinates(game.getTurnNumber(), 1), game.getGameRecordLetter(game.getTurnNumber()));
		}
	}
	
	class GameBoardCanvas extends JPanel {
		GameBoardCanvas(){
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (game.getGameState() == GameState.PLAYING) {
						int row = e.getY() / cellSize;
						int col = e.getX() / cellSize;
						if(game.getPlayerTurn() == Players.BLUEHUMAN) {
							if(blueSButton.isSelected()) {
								game.makeMove(row, col, 'S');
							}else if(blueOButton.isSelected()) {
								game.makeMove(row, col, 'O');
							}
						}else if(game.getPlayerTurn() == Players.REDHUMAN) {
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
			checkComputerVsComputer();
			checkHumanVsComputer();
			checkGameOver();
			checkReplay();
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
				if((game.getSosMadeBy(i) == Players.BLUEHUMAN) || (game.getSosMadeBy(i) == Players.BLUECOMPUTER)) {
					g2d.setColor(Color.BLUE);
				}else if((game.getSosMadeBy(i) == Players.REDHUMAN) || (game.getSosMadeBy(i) == Players.REDCOMPUTER)) {
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
    			blueHumanButton.doClick();
    			redHumanButton.doClick();
    			if(recordCheckBox.isSelected()) {
    				recordCheckBox.doClick();
    			}
    			replayButton.setEnabled(false);
    			
    			if(simpleGameButton.isSelected()) {
    				game = new SimpleGame(Integer.parseInt(boardSizeField.getText()));
    			}else if(generalGameButton.isSelected()) {
    				game = new GeneralGame(Integer.parseInt(boardSizeField.getText()));
    			}
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
    
    private class RedHumanButtonListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		redSButton.setEnabled(true);
    		redOButton.setEnabled(true);
    		if(redHumanButton.isSelected() && !redComputerButton.isSelected()) {
    			redSButton.doClick();
    		}else if(!redHumanButton.isSelected() && !redComputerButton.isSelected()) {
    			redHumanButton.doClick();
    		}else if(redHumanButton.isSelected() && redComputerButton.isSelected()) {
    			redComputerButton.doClick();
    		}
    		
    		game.setRedHumanComputerToggle(Players.REDHUMAN);
    	}
    }
    
    private class RedComputerButtonListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(redHumanButton.isSelected() && redComputerButton.isSelected()) {
    			redHumanButton.doClick();
    			redSButton.setEnabled(false);
    			redOButton.setEnabled(false);
    		} else if(!redHumanButton.isSelected() && !redComputerButton.isSelected()) {
    			redComputerButton.doClick();
    		}
    		
    		game.setRedHumanComputerToggle(Players.REDCOMPUTER);
    		repaint();
    	}
    }
    
    private class BlueHumanButtonListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		blueSButton.setEnabled(true);
    		blueOButton.setEnabled(true);
    		if(blueHumanButton.isSelected() && !blueComputerButton.isSelected()) {
    			blueSButton.doClick();
    		}else if(!blueHumanButton.isSelected() && !blueComputerButton.isSelected()) {
    			blueHumanButton.doClick();
    		}else if(blueHumanButton.isSelected() && blueComputerButton.isSelected()) {
    			blueComputerButton.doClick();
    		}
    		
    		game.setBlueHumanComputerToggle(Players.BLUEHUMAN);
    	}
    }
    
    private class BlueComputerButtonListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(blueHumanButton.isSelected() && blueComputerButton.isSelected()) {
    			blueHumanButton.doClick();
    			blueSButton.setEnabled(false);
    			blueOButton.setEnabled(false);
    		} else if(!blueHumanButton.isSelected() && !blueComputerButton.isSelected()) {
    			blueComputerButton.doClick();
    		}
    		
    		game.setBlueHumanComputerToggle(Players.BLUECOMPUTER);
    		repaint();
    	}
    }
    
    public class RecordListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(recordCheckBox.isSelected()) {
    			game.setRecord(true);
    		}else if(!recordCheckBox.isSelected()) {
    			game.setRecord(false);
    		}
    	}
    }
    
    public class ReplayListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		game.setReplay(game.getBoardSize());
    		repaint();
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
