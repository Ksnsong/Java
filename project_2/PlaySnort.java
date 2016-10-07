/**
 * This class provides an interface and logic for playing a game of Snort. 
 * 
 * @author Ethan McQueen, Kei Ng
 * @version 1.0
 */
//import java.lang.*;
import java.io.*;
//import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlaySnort implements Runnable {
	
    /** The underlying game. */
    private Snort game;
    
    /** Current Player. */
    private int currentPlayer;
    
	/** Is the game finished? */
	private boolean gameFinished = false;
    
    /** JFrame we are displaying the game in. */
    private JFrame frame;
    
    /** Number of players */
    private final int NUM_PLAYERS = 2;
    
    /** Implement the runnable interface. */
    public void run() {
    	main(null);
    }

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
        this.currentPlayer++;
        if (this.currentPlayer > NUM_PLAYERS) {
            currentPlayer = 1;
        }
        this.displayGame();
    }
    
    private int afterClicked(MouseEvent e){
    	int clickX=e.getX();
    	int clickY=e.getY();
    	int height = this.game.getHeight();
    	int width;
    	//System.out.println(clickX+","+clickY);
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
    			if(this.game.hasMoves(currentPlayer)){
    				System.out.println("It is now player " + this.currentPlayer + "'s turn.");
        			System.out.println("Player " + this.currentPlayer + ", please choose a vertex. (Vertexes are numbered left-to-right)");
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
        this.frame = new JFrame("Snort");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new SnortDisplay(this.game)).getPreferredSize());
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
    
    //toString
    
    /**
     * Returns a string version of this.
     *
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
  
    //private methods
    
    
   
    /**
     * Main method for testing.
     */
    public static void main(String[] args) { 
        Snort game0 = new Snort(5,4,3,4,5,4);
        PlaySnort pS = new PlaySnort(game0);
        pS.playGame();
    }
   
} //end of PlaySnort

/**
 * A Swing container that renders a graphical representation of a Snort game.
 */
class SnortDisplay extends JPanel {

    //instance variables
	
	private static final long serialVersionUID = 1L;//pesky warning-ridder
	
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
     *
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
    
} //end of SnortDisplay
        