/**
 * This class provides an interface and logic for playing a game of ToadsAndFrogs.
 * @author Kyle Burke, edited by Ethan McQueen and Kei Ng
 * @version 4.0
 */

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PlayToadsAndFrogs extends PlayGame {

    /** The underlying game. */
    private ToadsAndFrogs game;
    
    /** 
     * Class constructor. 
     */
    public PlayToadsAndFrogs() { 
        this.game = new ToadsAndFrogs();
        this.currentPlayer = 1; //1 is Left
    }
  
    /**
     * Class constructor for generic game.
     * @param game  ToadsAndFrogs game to begin with.
     */
    public PlayToadsAndFrogs(ToadsAndFrogs game) {
        this.game = game;
        this.currentPlayer = 1;
    }
    
    /** 
     * Creates a display for the game. 
     */
    private void displayGame() {
        this.frame.setContentPane(new ToadsAndFrogsDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
  
    /**
     * Moves to a new game
     * @param nextPosition  New game to make a move to.
     */
    private void move(ToadsAndFrogs nextPosition) {
        this.game = nextPosition;
        if (combinationGame == null) switchPlayer();
        this.displayGame();
    }
    
    /**
     * Indicates whether the current player has any legal moves.
     * @param currentplayer	The current player. 
     */
    public boolean hasMoves(int currentPlayer) {
    	return this.game.hasMoves(currentPlayer);
    }
    
    /**
     * Indicates the box clicked on. 
     * @return The box position clicked on.
     */
    private int afterClicked(MouseEvent e){
    	int clickX=e.getX();
    	int width;
    	int currentBox = 0;
    	width = this.game.getWidth();
    	for (int i = 0; i < width; i++) {
    		if (clickX >= 20 + (30 * i) && clickX <= 50 + (30 * i)) {
    			return currentBox;
    		} else {
    			currentBox++;
    		}
    	}
    	return 0;
    }
    
    /**
     * A method to make a move via a mouse click. 
     * @param int	Number of the input vertex. 
     */
    private void clickMove(int input) {
    	if (!gameFinished){
    		ToadsAndFrogs nextPosition = null;
    		try {
    			nextPosition = this.game.getPosition(input);
    			if (currentPlayer == 1){
    				if (!this.game.getLeftPositions().contains(nextPosition)) {
    					nextPosition = null;
    				}
    			} else if (currentPlayer == 2){
    				if (!this.game.getRightPositions().contains(nextPosition)) {
    					nextPosition = null;
    				}
    			}
    		} catch (ArrayIndexOutOfBoundsException e) {
    			nextPosition = null;
    		}
    		if (nextPosition == null) {
    			System.out.println("Sorry, that is not a legal move for you.");
    		} else {
    			this.move(nextPosition);
    			if (combinationGame != null) combinationGame.moveMade();
    			if(this.game.hasMoves(currentPlayer)) {
    				System.out.println("It is now player " + this.currentPlayer + "'s turn.");
        			System.out.println("Player " + this.currentPlayer + ", please choose an amphibian.");
    			} else if (combinationGame == null || this.gameFinished == true) {
    				this.gameFinished = true;
    				System.out.println("Sorry, player " + this.currentPlayer + ", you have no moves, so you lose!");
    				System.out.println("Close the game window to end this program.");
    			}
    		}
    	}
    }
    
    /**
     * Plays the game, alternating turns between the two players.
     */
    private void playGame() {
        this.frame = new JFrame("Toads and Frogs");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new ToadsAndFrogsDisplay(this.game)).getPreferredSize());
        this.frame.setLocation(this.framePositionX, this.framePositionY);
        this.displayGame();
        
        this.frame.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            	 clickMove(afterClicked(e));
            }
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
        });

        frame.pack();
        frame.setVisible(true);
    
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ToadsAndFrogs nextPosition;
        int box;
        while ((this.currentPlayer == 1 && this.game.hasLeftPositions()) || 
               (this.currentPlayer == 2 && this.game.hasRightPositions())) {
            System.out.println();
            System.out.println("It is now player " + this.currentPlayer + "'s turn.");
            nextPosition = null;
            while (nextPosition == null) {
                System.out.println("Player " + this.currentPlayer + ", please choose the box of the amphibian you would like to move. (From left-to-right; the first box is 0.)");
                try {
                    box = Integer.parseInt(in.readLine());
                } catch (IOException ioe) {
                    System.err.println(ioe.getStackTrace());
                    box = -1;
                }
                nextPosition = this.game.getPosition(box);
                if ((currentPlayer == 1 && 
                		!this.game.getLeftPositions().contains(nextPosition)) ||
                		(currentPlayer == 2 &&
                		!this.game.getRightPositions().contains(nextPosition))) {
                	System.out.println(nextPosition);
                	nextPosition = null;
                }
                if (nextPosition == null) {
                    System.out.println("Sorry, that is not a legal move for you.");
                }
            }
            this.move(nextPosition);
        }
        System.out.println("Sorry, player " + this.currentPlayer + ", there are no more moves for you, you lose.");
        System.out.println("Close the game window to end this program.");
    }
    
    /**
     * Main method for testing.
     */
    public static void main(String[] args) { 
//    	setupGame();
    	new PlayToadsAndFrogs().playRandomGame();
    }
    
    /**
     * Choose settings for a Toads and Frogs game. 
     */
    public Runnable setupGame() {
    	this.settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.settingsFrame.setPreferredSize((new ToadsAndFrogsSettings(this)).getPreferredSize());
    	settingsFrame.setContentPane(new ToadsAndFrogsSettings(this));
        settingsFrame.pack();
        settingsFrame.repaint();
    	settingsFrame.setVisible(true);
    	return null;
    }
    
    /**
     * Play a game with indicated setup parameters. 
     * @param boardWidth	The number of spaces across the width of the game board. 
     * @param fillChance	The chance that a space will contain an amphibian. 
     */
    protected void playSetGame(int boardWidth, int parameter2) {
    	ArrayList<Integer> randomBoard = new ArrayList<Integer>();
    	for (int i = 0; i < boardWidth; i++) {
    			if ((int)Math.round(Math.random()*100) <= parameter2) {
    				int value = (int)Math.round(Math.random());
    				if (value == 0) 
    					randomBoard.add(1);
    				else if (value == 1) 
    					randomBoard.add(-1);
    			} else 
    				randomBoard.add(0);
    	}
    	ToadsAndFrogs game0 = new ToadsAndFrogs(randomBoard);
    	PlayToadsAndFrogs pTAF = new PlayToadsAndFrogs(game0);
        pTAF.playGame();
    }
    
    /**
     * Play a game with random setup parameters. 
     */
    protected void playRandomGame() {
    	int minBoardWidth = 8;
    	int maxBoardWidth = 23;
    	int fillChance = 30;
    	int additionalSpaceChance = 88;
    	ArrayList<Integer> randomBoard = new ArrayList<Integer>();
    	while(true){
    		if (randomBoard.size() == 0){
    			randomBoard.add(1);
    		} else if (randomBoard.size() < minBoardWidth) {
    			if ((int)Math.round(Math.random()*100) <= fillChance) {
    				int value = (int)Math.round(Math.random());
    				if (value == 0) 
    					randomBoard.add(1);
    				else if (value == 1) 
    					randomBoard.add(-1);
    			} else 
    				randomBoard.add(0);
    		} else if (randomBoard.size() >= maxBoardWidth) {
    			randomBoard.add(-1);
    			break;
    		} else if (randomBoard.size() >= minBoardWidth) {
    			if ((int)Math.round(Math.random()*100) <= additionalSpaceChance) {
        			if ((int)Math.round(Math.random()*100) <= fillChance) {
        				int value = (int)Math.round(Math.random());
        				if (value == 0) 
        					randomBoard.add(1);
        				else if (value == 1) 
        					randomBoard.add(-1);
        			} else 
        				randomBoard.add(0);
    			} else {
    				randomBoard.add(-1);
    				break;
    			}
    		}
    	}
    	
        //ToadsAndFrogs game0 = new ToadsAndFrogs(10, new int[] {0, 1, -1, -1, 0, 1, 1, -1, 0, -1});
    	ToadsAndFrogs game0 = new ToadsAndFrogs(randomBoard);
    	this.game = game0;
    	playGame();
    }
}

/**
 * A Swing container that renders a graphical representation of a Toads and Frogs settings.
 */
class ToadsAndFrogsSettings extends JPanel 
implements ActionListener,
WindowListener,
ChangeListener 
{
	private static final long serialVersionUID = 1L; // Annoying warning eliminator
	private JFrame settingsFrame;
	private static final int minBoardWidth = 8;
	private static final int maxBoardWidth = 30;
	private static final int initialBoardWidth = 10;
	private int boardWidth = initialBoardWidth;
	private static final int minFillChance = 0;
	private static final int maxFillChance = 100;
	private static final int initialFillChance = 30;
	private int fillChance = initialFillChance;
    /**
     * Class constructor.
     */
    public ToadsAndFrogsSettings(final PlayToadsAndFrogs playGame) {
        super(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setOpaque(true);
        
        settingsFrame = playGame.settingsFrame;
        JLabel boardWidthSliderLabel = new JLabel("Board Width", JLabel.CENTER);
        boardWidthSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        boardWidthSliderLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
    	JSlider boardWidthSlider = new JSlider(JSlider.HORIZONTAL, minBoardWidth, maxBoardWidth, initialBoardWidth);
    	boardWidthSlider.setName("boardWidthSlider");
    	boardWidthSlider.addChangeListener(this);
    	boardWidthSlider.setMajorTickSpacing(1);
    	boardWidthSlider.setMinorTickSpacing(1);
    	boardWidthSlider.setPaintTicks(true);
    	boardWidthSlider.setPaintLabels(true);
    	boardWidthSlider.setBorder(BorderFactory.createEmptyBorder(0,40,0,40));
    	add(boardWidthSliderLabel);
    	add(boardWidthSlider);
    	
    	JLabel fillChanceSliderLabel = new JLabel("Fill Chance", JLabel.CENTER);
    	fillChanceSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	fillChanceSliderLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
    	JSlider fillChanceSlider = new JSlider(JSlider.HORIZONTAL, minFillChance, maxFillChance, initialFillChance);
    	fillChanceSlider.setName("fillChanceSlider");
    	fillChanceSlider.addChangeListener(this);
    	fillChanceSlider.setMajorTickSpacing(10);
    	fillChanceSlider.setMinorTickSpacing(1);
    	fillChanceSlider.setPaintTicks(true);
    	fillChanceSlider.setPaintLabels(true);
    	fillChanceSlider.setBorder(BorderFactory.createEmptyBorder(0,40,25,40));
    	add(fillChanceSliderLabel);
    	add(fillChanceSlider);
    	
    	JButton button1 = new JButton("Play Toads and Frogs"); 		// Button "Play Toads and Frogs"
		add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {  
				settingsFrame.dispose();
				Thread t = new Thread(PlayToadsAndFrogs.giveSetGame(playGame, boardWidth, fillChance));
				t.start();
			}
		});
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    /**
     * Returns the preferred size for this display.
     * @return  A Dimension object representing the smallest size needed to nicely display this game.
     */
    public Dimension getPreferredSize() {
        int width = 700;
        int height = 275;
        return new Dimension(width, height);
    }
	public void stateChanged(ChangeEvent event) {
		JSlider source = (JSlider)event.getSource();
        if (!source.getValueIsAdjusting()) {
        	if (source.getName().equals("boardWidthSlider"))
        		boardWidth = (int)source.getValue();
        	else if (source.getName().equals("fillChanceSlider"))
        		fillChance = (int)source.getValue();
        }
	}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void actionPerformed(ActionEvent arg0) {}
}

/**
 * A Swing container that renders a graphical representation of a Toads and Frogs game.
 */
class ToadsAndFrogsDisplay extends JPanel {
	
	private static final long serialVersionUID = 1L; // Annoying warning eliminator
	
	/** The Toads and Frogs game this will display. */
    private ToadsAndFrogs game;
    
    /**
     * Class constructor.
     * @param game	The ToadsAndFrogs instance this will display.
     */
    public ToadsAndFrogsDisplay(ToadsAndFrogs game) {
        super(new BorderLayout());
        this.game = game;
        this.setOpaque(true);
    }
    
    /**
     * Paints the game.  This is called whenever the window needs to redraw the game.
     * @param graphics      Graphics object used to draw things.  This is automatically supplied when the paint() method is called.
     */
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        int numberOfBoxes = this.game.getWidth();
        for (int i = 0; i < numberOfBoxes; i++) {
            graphics.drawRect(10 + (30 * i), 10, 30, 30);
            if (this.game.getValue(i) == -1) {
                graphics.drawString("F", 20 + (30 * i), 30);
            } else if (this.game.getValue(i) == 1) {
                graphics.drawString("T", 20 + (30 * i), 30);
            }
        }
    }
    
    /**
     * Returns the preferred size for this display.
     * @return  A Dimension object representing the smallest size needed to nicely display this game.
     */
    public Dimension getPreferredSize() {
        int width = 20 + 31*this.game.getWidth();
        int height = 80;
        return new Dimension(width, height);
    }
}
        