/**
 * PlayToadsAndFrogs.java defines a "referee" for the game ToadsAndFrogs.  It keeps track of the current players' turn, prompts them for their move and determines when the game has finished.
 * 
 * @author Kyle Burke
 * @version 1.0
 */
//package something;
 
//import java.lang.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
//import java.util.*;
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

public class PlayToadsAndFrogs {

    /** The underlying game. */
    private ToadsAndFrogs game;
    
    /** Current Player. */
    private int currentPlayer;
	
    /** Is the game finished? */
	private boolean gameFinished;

    /** JFrame we are displaying the game in. */
    private JFrame frame;
    
    /** JFrame we are displaying the game settings in. */
    private final JFrame settingsFrame = new JFrame("Toads and Frogs settings");
    
    /** Number of players */
    private final int NUM_PLAYERS = 2;

    /** Class constructor. */
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
     * Returns the settings setup window frame. 
     * @return settingsFrame
     */
    public JFrame getSettingsFrame() {
    	return this.settingsFrame;
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
     * Creates a display for the game settings. 
     */
    private void displayGameSettings() {
        settingsFrame.setContentPane(new ToadsAndFrogsSettings(this));
        settingsFrame.pack();
        settingsFrame.repaint();
    }
  
    /**
     * Moves to a new game
     * @param game  New game to make a move to.
     */
    private void move(ToadsAndFrogs game) {
        this.game = game;
        this.currentPlayer++;
        if (this.currentPlayer > NUM_PLAYERS) {
            currentPlayer = 1;
        }
        this.displayGame();
    }
    
    /**
     * Moves to a new game
     * @param get position after clicked.
     */
    private int afterClicked(MouseEvent e){
    	int clickX=e.getX();
    	int width;
    	//System.out.println(clickX+","+clickY);
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
    			if(this.game.hasMoves(currentPlayer)){
    				System.out.println("It is now player " + this.currentPlayer + "'s turn.");
        			System.out.println("Player " + this.currentPlayer + ", please choose an amphibian.");
    			} else {
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
        //Create and set up the window.
        this.frame = new JFrame("Toads and Frogs");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new ToadsAndFrogsDisplay(this.game)).getPreferredSize());
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

        //Display the window.
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
                	//in the above cases, this was not a legal move for that player!
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
     * Returns a string version of this.
     * @param indent  Indentation string.
     */
    public String toString(String indent){
        String string = "";
        return string;
    }
  
    /**
     * Returns a string version of this.
     */
    public String toString() {
        return this.toString("");
    }
    
    /**
     * Main method for testing.
     */
    public static void main(String[] args) { 
    	setupGame();
//    	playRandomGame();
    }
    public static Runnable setupGame() {
    	ToadsAndFrogs game0 = new ToadsAndFrogs();
    	PlayToadsAndFrogs pTAF = new PlayToadsAndFrogs(game0);
        pTAF.changeSettings();
		return null;
    }
    private void changeSettings() {
    	//      this.settingsFrame = new JFrame("Toads and Frogs settings");
    	this.settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.settingsFrame.setPreferredSize((new ToadsAndFrogsSettings(this)).getPreferredSize());
    	this.displayGameSettings();
    	settingsFrame.pack();
    	settingsFrame.setVisible(true);
    }
    public static void playSetGame(int boardWidth, int fillChance) {
    	ArrayList<Integer> randomBoard = new ArrayList<Integer>();
    	for (int i = 0; i < boardWidth; i++) {
    			if ((int)Math.round(Math.random()*100) <= fillChance) {
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
    private static void playRandomGame() {
    	int minBoardWidth = 8;
    	int maxBoardWidth = 23;
    	int fillChance = 30;
    	int additionalSpaceChance = 88;
    	ArrayList<Integer> randomBoard = new ArrayList<Integer>();
    	while(true){
    		//System.out.println(randomBoard.size());
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
        PlayToadsAndFrogs pTAF = new PlayToadsAndFrogs(game0);
        pTAF.playGame();
    }
    public static Runnable giveRandomGame() {
    	return new PlayRandomGame();
    }
    private static class PlayRandomGame implements Runnable {
        /** Implement the runnable interface. */
        public void run() {
        	PlayToadsAndFrogs.playRandomGame();
        }
    }
    public static Runnable giveSetGame(int boardWidth, int fillChance) {
    	return new PlaySetGame(boardWidth, fillChance);
    }
    private static class PlaySetGame implements Runnable {
    	private int boardWidth;
    	private int fillChance;
    	private PlaySetGame(int boardWidth, int fillChance) {
    		this.boardWidth = boardWidth;
    		this.fillChance = fillChance;
    	}
        /** Implement the runnable interface. */
        public void run() {
        	PlayToadsAndFrogs.playSetGame(boardWidth, fillChance);
        }
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
	private static final long serialVersionUID = 1L;//annoying warning-ridder
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
        
        settingsFrame = playGame.getSettingsFrame();
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
				Thread t = new Thread(PlayToadsAndFrogs.giveSetGame(boardWidth, fillChance));
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
	
	private static final long serialVersionUID = 1L;//annoying warning-ridder
	
	/** The Toads and Frogs game this will display. */
    private ToadsAndFrogs game;
    
    /**
     * Class constructor.
     * @param game      The ToadsAndFrogs instance this will display.
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
        