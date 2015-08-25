/**
 * PlayToadsAndFrogs.java defines a "referee" for the game ToadsAndFrogs.  It keeps track of the current players' turn, prompts them for their move and determines when the game has finished.
 * 
 * @author Kyle Burke
 * @version 1.0
 */
//package something;
 
import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class PlayToadsAndFrogs {


    //instance variables
    
    /**
     * The underlying game.
     */
    public ToadsAndFrogs game;
    
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
     * Class constructor.
     *
     */
    public PlayToadsAndFrogs() { 
        this.game = new ToadsAndFrogs();
        this.currentPlayer = 1; //1 is Left
    }
  
    /**
     * Class constructor for generic game.
     * 
     * @param game  ToadsAndFrogs game to begin with.
     */
    public PlayToadsAndFrogs(ToadsAndFrogs game) {
        this.game = game;
        this.currentPlayer = 1;
    }
    
    /**
     * Creates a display for the game.
     */
    public void displayGame() {
        this.frame.setContentPane(new ToadsAndFrogsDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
        
  
    /**
     * Moves to a new game
     *
     * @param game  New game to make a move to.
     */
    public void move(ToadsAndFrogs game) {
        this.game = game;
        this.currentPlayer++;
        if (this.currentPlayer > NUM_PLAYERS) {
            currentPlayer = 1;
        }
        this.displayGame();
    }

    /**
     * Plays the game, alternating turns between the two players.
     */
    public void playGame() {
        //Create and set up the window.
        this.frame = new JFrame("Toads and Frogs");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new ToadsAndFrogsDisplay(this.game)).getPreferredSize());
        this.displayGame();

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
        ToadsAndFrogs game0 = new ToadsAndFrogs(10, new int[] {0, 1, -1, -1, 0, 1, 1, -1, 0, -1});
        PlayToadsAndFrogs pTAF = new PlayToadsAndFrogs(game0);
        pTAF.playGame();
    }
   
} //end of PlayToadsAndFrogs

/**
 * A Swing container that renders a graphical representation of a Toads and Frogs game.
 */
class ToadsAndFrogsDisplay extends JPanel {

    //instance variables
    
    /**
     * The Toads and Frogs game this will display.
     */
    ToadsAndFrogs game;
    
    /**
     * Class constructor.
     *
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
        int numberOfBoxes = this.game.boxes.length;
        for (int i = 0; i < numberOfBoxes; i++) {
            graphics.drawRect(10 + (30 * i), 10, 30, 30);
            if (this.game.boxes[i] == -1) {
                graphics.drawString("F", 20 + (30 * i), 30);
            } else if (this.game.boxes[i] == 1) {
                graphics.drawString("T", 20 + (30 * i), 30);
            }
        }
    }
    
    /**
     * Returns the preferred size for this display.
     *
     * @return  A Dimension object representing the smallest size needed to nicely display this game.
     */
    public Dimension getPreferredSize() {
        int width = 20 + 30*this.game.boxes.length;
        int height = 80;
        return new Dimension(width, height);
    }
    
} //end of ToadsAndFrogsDisplay
        
