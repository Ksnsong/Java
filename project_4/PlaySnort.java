/**
 * This class provides an interface and logic for playing a game of Snort. 
 * @author Ethan McQueen, Kei Ng
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

public class PlaySnort extends PlayGame {
	
	/** Debugging toggle. */
	private final boolean DEBUG = false; 
	
    /** The underlying game. */
    private Snort game;

    /** 
     * Class constructor. 
     */
    public PlaySnort() { 
        this.game = new Snort();
        this.currentPlayer = (int)Math.round(Math.random())+1;
    }
  
    /**
     * Class constructor for generic game.
     * @param game  Snort game to begin with.
     */
    public PlaySnort(Snort game) {
        this.game = game;
        this.currentPlayer = (int)Math.round(Math.random())+1;
    }
    
    /**
     * Creates a display for the game.
     */
    private void displayGame() {
        this.frame.setContentPane(new SnortDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
        
    /**
     * Moves to a new game
     * @param game  New game to make a move to.
     */
    private void move(Snort game) {
        this.game = game;
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
    	int clickY=e.getY();
    	int height = this.game.getHeight();
    	int width;
    	int currentBox = 1;
    	for (int j = 0; j < height; j++) {
    		width = this.game.getWidth(j);
    		for (int i = 0; i < width; i++) {
    			if (clickX >= 20 + (30 * i) && clickX <= 50 + (30 * i) &&
    					clickY >= 30 + (30 * j) && clickY <= 60 + (30 * j)) {
    				return currentBox;
    			} else {
    				currentBox++;
    			}
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
    		Snort nextPosition = null;
    		try {
    			nextPosition = this.game.getPosition(input, currentPlayer);
    		} catch (ArrayIndexOutOfBoundsException e) {
    			nextPosition = null;
    		}
    		if (nextPosition == null) {
    			System.out.println("Sorry, that is not a legal move for you.");
    		} else {
    			this.move(nextPosition);
    			if (combinationGame != null) combinationGame.moveMade();
    			if(this.game.hasMoves(currentPlayer)){
    				System.out.println("It is now player " + this.currentPlayer + "'s turn.");
        			System.out.println("Player " + this.currentPlayer + ", please choose a vertex. (Vertexes are numbered left-to-right)");
    			} else if (combinationGame == null || this.gameFinished == true)  {
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
        this.frame = new JFrame("Snort");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new SnortDisplay(this.game)).getPreferredSize());
        if(DEBUG)System.out.println("PlaySnort.displayGame(): framePositionX = " + framePositionX + ", framePositionY = " + framePositionY);
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
        Snort nextPosition;
        int input = 0;
        do {
             System.out.println();
             System.out.println("It is now player " + this.currentPlayer + "'s turn.");
             nextPosition = null;
             while (nextPosition == null) {
            	 System.out.println("Player " + this.currentPlayer + ", please choose a vertex. (Vertexes are numbered left-to-right)");
                 try {
                	 input = Integer.parseInt(in.readLine());      	 
                 } catch (IOException ioe) {
                     System.err.println(ioe.getStackTrace());
                     input = -1;
                 }
                 if (gameFinished) break;
                 nextPosition = this.game.getPosition(input, currentPlayer);
                 if (nextPosition == null) {
                     System.out.println("Sorry, that is not a legal move for you.");
                 } else {
                	System.out.println(nextPosition.toString());
                 }
             }
             if (gameFinished) break;
             this.move(nextPosition);
         } while (this.game.hasMoves(currentPlayer));
         if (!gameFinished) System.out.println("Sorry, player " + this.currentPlayer + ", you have no moves, so you lose!");
         if (!gameFinished) System.out.println("Close the game window to end this program.");
    }
   
    /**
     * Main method for testing.
     */
    public static void main(String[] args) { 
    	new PlaySnort().setupGame();
//    	playRandomGame();
    }
    
    /**
     * Provide a settings window for a set-up game. 
     */
    protected Runnable setupGame() {
    	this.settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.settingsFrame.setPreferredSize((new SnortSettings(this)).getPreferredSize());
    	settingsFrame.setContentPane(new SnortSettings(this));
        settingsFrame.pack();
        settingsFrame.repaint();
    	settingsFrame.setVisible(true);
    	return null;
    }
    
    /**
     * Play a game with indicated setup parameters. 
     * @param boardWidth	The number of spaces across the width of the game board. 
     * @param boardHeight	The number of spaces across the height of the game board. 
     */
    public void playSetGame(int boardWidth, int boardHeight) {
    	Snort game0 = new Snort(boardWidth, boardHeight);
    	PlaySnort pTAF = new PlaySnort(game0);
        pTAF.playGame();
    }
    
    /**
     * Play a game with random setup parameters. 
     */
    protected void playRandomGame() {
    	int minRowWidth = 3;
    	int maxRowWidth = 5;
    	int minBoardHeight = 4;
    	int maxBoardHeight = 5;
    	int height = (int)(Math.random() * (maxBoardHeight - minBoardHeight + 1)) + minBoardHeight;
    	ArrayList<Integer> width = new ArrayList<Integer>();
    	for (int i = 0; i < height; i++){
    		width.add((int)(Math.random() * (maxRowWidth - minRowWidth + 1)) + minRowWidth);
    	}
    	Snort game0;
    	if (height == 4)
    		game0 = new Snort(height,width.get(0),width.get(1),width.get(2),width.get(3));
    	else if (height == 5)
    		game0 = new Snort(height,width.get(0),width.get(1),width.get(2),width.get(3),width.get(4));
    	else 
    		game0 = new Snort(5,4,3,4,5,4);
    	this.game = game0;
    	playGame();
    }
}

/**
 * A Swing container that renders a graphical representation of a Snort window settings.
 */
class SnortSettings extends JPanel 
implements ActionListener,
WindowListener,
ChangeListener 
{
	private static final long serialVersionUID = 1L; // Annoying warning eliminator
	private JFrame settingsFrame;
	private static final int minBoardWidth = 2;
	private static final int maxBoardWidth = 30;
	private static final int initialBoardWidth = 5;
	private int boardWidth = initialBoardWidth;
	private static final int minBoardHeight = 2;
	private static final int maxBoardHeight = 30;
	private static final int initialBoardHeight = 5;
	private int boardHeight = initialBoardHeight;
    /**
     * Class constructor.
     */
    public SnortSettings(final PlaySnort playGame) {
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
    	boardWidthSlider.setMajorTickSpacing(2);
    	boardWidthSlider.setMinorTickSpacing(1);
    	boardWidthSlider.setPaintTicks(true);
    	boardWidthSlider.setPaintLabels(true);
    	boardWidthSlider.setBorder(BorderFactory.createEmptyBorder(0,40,0,40));
    	add(boardWidthSliderLabel);
    	add(boardWidthSlider);
    	
    	JLabel boardHeightSliderLabel = new JLabel("Board Height", JLabel.CENTER);
    	boardHeightSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	boardHeightSliderLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
    	JSlider boardHeightSlider = new JSlider(JSlider.HORIZONTAL, minBoardHeight, maxBoardHeight, initialBoardHeight);
    	boardHeightSlider.setName("boardHeightSlider");
    	boardHeightSlider.addChangeListener(this);
    	boardHeightSlider.setMajorTickSpacing(2);
    	boardHeightSlider.setMinorTickSpacing(1);
    	boardHeightSlider.setPaintTicks(true);
    	boardHeightSlider.setPaintLabels(true);
    	boardHeightSlider.setBorder(BorderFactory.createEmptyBorder(0,40,25,40));
    	add(boardHeightSliderLabel);
    	add(boardHeightSlider);
    	
    	JButton button1 = new JButton("Play Snort");
		add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {  
				settingsFrame.dispose();
				Thread t = new Thread(PlaySnort.giveSetGame(playGame, boardHeight, boardWidth));
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
        	else if (source.getName().equals("boardHeightSlider"))
        		boardHeight = (int)source.getValue();
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
 * A Swing container that renders a graphical representation of a Snort game.
 */
class SnortDisplay extends JPanel {
	
	private static final long serialVersionUID = 1L; // Annoying warning eliminator
	
	/**
     * The Snort game this will display.
     */
    Snort game;
    
    /**
     * Class constructor.
     *
     * @param game      The Snort instance this will display.
     */
    public SnortDisplay(Snort game) {
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
        int height = this.game.getHeight();
        int width;
        for (int j = 0; j < height; j++) {
        	width = this.game.getWidth(j);
        	for (int i = 0; i < width; i++) {
        		graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
            	if (i != width-1) 
            		graphics.drawLine(35 + (30 * i), 25 + (30 * j), 45 + (30 * i), 25 + (30 * j));
	            if (this.game.getValue(i, j) == 1) {
	            	graphics.setColor(Color.BLUE);
	            	graphics.fillOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            	graphics.setColor(Color.BLACK);
	            	graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	                //graphics.drawString("1", 22 + (30 * i), 30 + (30 * j));
	            } else if (this.game.getValue(i, j) == 2) {
	            	graphics.setColor(Color.RED);
	            	graphics.fillOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            	graphics.setColor(Color.BLACK);
	            	graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	                //graphics.drawString("2", 22 + (30 * i), 30 + (30 * j));
	            }
        	}
        }
    }
    
    /**
     * Returns the preferred size for this display.
     * @return  A Dimension object representing the smallest size needed to nicely display this game.
     */
    public Dimension getPreferredSize() {
    	int tempWidth = 1;
    	for (int i = 0; i < this.game.getHeight(); i++) {
    		if (tempWidth < this.game.getWidth(i)){
    			tempWidth = this.game.getWidth(i);
    		}
    	}
        int width = 30 + 31*tempWidth;
        int height = 53 + 31*this.game.getHeight();
        return new Dimension(width, height);
    }
    
}
        