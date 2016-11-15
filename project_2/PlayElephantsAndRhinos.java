/**
 * PlayElephantsAndRhinos.java defines a "referee" for the game ElephantsAndRhinos.  It keeps track of the current players' turn, prompts them for their move and determines when the game has finished.
 * @author Kyle Burke, edited by Ethan McQueen and Kei Ng
 * @version 1.0
 */

//import java.lang.*;
import java.io.*;
import javax.imageio.ImageIO;
//import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class PlayElephantsAndRhinos implements Runnable{

    /** The underlying game. */
    private ElephantsAndRhinos game;
    
    /** Current Player. */
    private int currentPlayer;
	
    /** Is the game finished? */
	private boolean gameFinished;

    /** JFrame we are displaying the game in. */
    private JFrame frame;
    
    /** Number of players */
    private final int NUM_PLAYERS = 2;
    
    /** Implement the runnable interface. */
    public void run() {
    	main(null);
    }

    /** Class constructor. */
    public PlayElephantsAndRhinos() { 
        this.game = new ElephantsAndRhinos();
        this.currentPlayer = 1; //1 is Left
    }
  
    /**
     * Class constructor for generic game.
     * @param game  ElephantsAndRhinos game to begin with.
     */
    public PlayElephantsAndRhinos(ElephantsAndRhinos game) {
        this.game = game;
        this.currentPlayer = 1;
    }
    
    /** 
     * Creates a display for the game. 
     */
    private void displayGame() {
        this.frame.setContentPane(new ElephantsAndRhinosDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
        
  
    /**
     * Moves to a new game
     * @param game  New game to make a move to.
     */
    private void move(ElephantsAndRhinos game) {
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
    		ElephantsAndRhinos nextPosition = null;
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
        			System.out.println("Player " + this.currentPlayer + ", please choose a pachyderm.");
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
        this.frame = new JFrame("Elephants and Rhinos");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new ElephantsAndRhinosDisplay(this.game)).getPreferredSize());
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
        ElephantsAndRhinos nextPosition;
        int box;
        while ((this.currentPlayer == 1 && this.game.hasLeftPositions()) || 
               (this.currentPlayer == 2 && this.game.hasRightPositions())) {
            System.out.println();
            System.out.println("It is now player " + this.currentPlayer + "'s turn.");
            nextPosition = null;
            while (nextPosition == null) {
                System.out.println("Player " + this.currentPlayer + ", please choose the box of the pachyderm you would like to move. (From left-to-right; the first box is 0.)");
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
        //ElephantsAndRhinos game0 = new ElephantsAndRhinos(10, new int[] {0, 1, -1, -1, 0, 1, 1, -1, 0, -1});
        ElephantsAndRhinos game0 = new ElephantsAndRhinos(10, new int[] {0, -1, 1, 0, 0, -1, 0, 1, 0, 0});
        PlayElephantsAndRhinos pTAF = new PlayElephantsAndRhinos(game0);
        pTAF.playGame();
    }
}

/**
 * A Swing container that renders a graphical representation of a Elephants and Rhinos game.
 */
class ElephantsAndRhinosDisplay extends JPanel {
	
	private static final long serialVersionUID = 1L;//annoying warning-ridder
	private BufferedImage elephantImage;
	private BufferedImage rhinoImage;
	
	/** The Elephants and Rhinos game this will display. */
    private ElephantsAndRhinos game;
    
    /**
     * Class constructor.
     * @param game      The ElephantsAndRhinos instance this will display.
     */
    public ElephantsAndRhinosDisplay(ElephantsAndRhinos game) {
        super(new BorderLayout());
        this.game = game;
        try {                
        	// Elephant image found at: http://icons.iconarchive.com/icons/fasticon/animal-toys/256/Elephant-icon.png
        	elephantImage = ImageIO.read(new File("Elephant.png"));
        	// Rhino image found at: http://365psd.com/images/premium/thumbs/485/cartoon-head-of-a-rhinoceros-cute-animal-illustration-vector-207630.jpg
        	rhinoImage = ImageIO.read(new File("Rhino.png"));
         } catch (IOException ex) {
        	 System.out.println("Image failed to load...");
         }
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
                //graphics.drawString("R", 20 + (30 * i), 30);
            	graphics.drawImage(rhinoImage, 11 + (30 * i), 11, null);
            } else if (this.game.getValue(i) == 1) {
                //graphics.drawString("E", 20 + (30 * i), 30);
                graphics.drawImage(elephantImage, 11 + (30 * i), 11, null);
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
        
