/**
 * This class provides an interface and logic for playing a game of Chomp. 
 * 
 * @author Ethan McQueen, Kei Ng
 * @version 1.0
 */
import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayChomp implements Runnable {


    //instance variables
    
    /**
     * Is the game over?
     */
    public boolean gameFinished = false;
    
    /**
     * The underlying game.
     */
    public Chomp game;
    
    /**
     * Current Player.
     */
    public int currentPlayer;
    
    /**
     * JFrame we are displaying the game in.
     */
    public JFrame frame;
    
    //constants
    
    /**
     * Number of players
     */
    private final int NUM_PLAYERS = 2;
    
    //public methods
  
    /**
     * Implement the runnable interface.
     */
    public void run() {
    	main(null);
    }

    /**
     * Class constructor.
     *
     */
    public PlayChomp() { 
        this.game = new Chomp();
        this.currentPlayer = 1; //1 is Left
    }
  
    /**
     * Class constructor for generic game.
     * 
     * @param game  Chomp game to begin with.
     */
    public PlayChomp(Chomp game) {
        this.game = game;
        this.currentPlayer = 1;
    }
    
    
    /**
     * Creates a display for the game.
     */
    public void displayGame() {
        this.frame.setContentPane(new ChompDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
        
  
    /**
     * Moves to a new game
     *
     * @param game  New game to make a move to.
     */
    public void move(Chomp game) {
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
    public int afterClicked(MouseEvent e){
    	int clickX=e.getX();
    	int clickY=e.getY();
    	final int width = this.game.getWidth();
    	final int height = this.game.getHeight();
    	//System.out.println(clickX+","+clickY);
    	int currentBox = 1;
    	for (int j = 0; j < height; j++) {
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
     * @param int	Number of the input cookie. 
     */
    public void clickMove(int input) {
    	if (!gameFinished){
    		Chomp nextPosition = null;
    		nextPosition = this.game.getPosition(input);
    		if (nextPosition == null) {
    			System.out.println("Sorry, that is not a legal move for you.");
    		} else if (input == game.getPoisonCookie()) {
    			System.out.println("Sorry, player " + this.currentPlayer + ", you ate the poison cookie, you lose.");
    			System.out.println("Close the game window to end this program.");
    			gameFinished = true;
    		} else {
    			this.move(nextPosition);
    			System.out.println("It is now player " + this.currentPlayer + "'s turn.");
    			System.out.println("Player " + this.currentPlayer + ", please choose a cookie. (Boxes are numbered 1-25, left-to-right)");
    		}
    	}
    }

    /**
     * Plays the game, alternating turns between the two players.
     */
    public void playGame() {
        //Create and set up the window.
        this.frame = new JFrame("Chomp");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new ChompDisplay(this.game)).getPreferredSize());
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
        Chomp nextPosition;
        int input = 0;
        while ((this.currentPlayer == 1) || (this.currentPlayer == 2)) {
             System.out.println();
             System.out.println("It is now player " + this.currentPlayer + "'s turn.");
             nextPosition = null;
             while (nextPosition == null) {
                 System.out.println("Player " + this.currentPlayer + ", please choose a cookie. (Boxes are numbered 1-25, left-to-right)");
                 try {
                	 input = Integer.parseInt(in.readLine());      	 
                 } catch (IOException ioe) {
                     System.err.println(ioe.getStackTrace());
                     input = -1;
                 }
                 nextPosition = this.game.getPosition(input);
                 if (input == game.getPoisonCookie()) break; 
                 else if (nextPosition == null) {
                     System.out.println("Sorry, that is not a legal move for you.");
                 } else {
                	System.out.println(nextPosition.toString());
                 }
             }
             if (input == game.getPoisonCookie()) break;
             this.move(nextPosition);
         }
         System.out.println("Sorry, player " + this.currentPlayer + ", you ate the poison cookie, you lose.");
         System.out.println("Close the game window to end this program.");
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
        Chomp game0 = new Chomp(5,4);
        PlayChomp pC = new PlayChomp(game0);
        pC.playGame();
    }
   
} //end of PlayChomp

/**
 * A Swing container that renders a graphical representation of a Chomp game.
 */
class ChompDisplay extends JPanel {

    //instance variables
    
    /**
     * The Chomp game this will display.
     */
    Chomp game;
    
    /**
     * Class constructor.
     *
     * @param game      The Chomp instance this will display.
     */
    public ChompDisplay(Chomp game) {
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
        int width = this.game.getWidth();
        int height = this.game.getHeight();
        for (int i = 0; i < width; i++) {
        	for (int j = 0; j < height; j++) {
	            //graphics.drawRect(10 + (30 * i), 10 + (30 * j), 30, 30);
	            if (this.game.getValue(i, j) == 1) {
	                //graphics.drawString("C", 20 + (30 * i), 30 + (30 * j));
	            	graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            } else if (this.game.getValue(i, j) == 2) {
	                graphics.drawString("P", 20 + (30 * i), 30 + (30 * j));
	            	graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
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
        int width = 30 + 31*this.game.getWidth();
        int height = 55 + 31*this.game.getHeight();
        return new Dimension(width, height);
    }
    
} //end of ChompDisplay
        