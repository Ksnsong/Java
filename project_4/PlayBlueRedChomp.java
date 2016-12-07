/**
 * This class provides an interface and logic for playing a game of BlueRedChomp. 
 * @author Ethan McQueen, Kei Ng
 * @version 4.0
 */

import java.io.*;
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

public class PlayBlueRedChomp extends PlayGame {
    
    /** The underlying game. */
    private BlueRedChomp game;

    /**
     * Class constructor.
     */
    public PlayBlueRedChomp() { 
        this.game = new BlueRedChomp();
        this.currentPlayer = (int)Math.round(Math.random())+1;
    }
  
    /**
     * Class constructor for generic game.
     * @param game  BlueRedChomp game to begin with.
     */
    public PlayBlueRedChomp(BlueRedChomp game) {
        this.game = game;
        this.currentPlayer = (int)Math.round(Math.random())+1;
    }
    
    /**
     * Gets the name of the color of the current player. 
     * @param currentPlayer	The current player. 
     * @param capitalize	Whether to return the word capitalized or not. 
     */
    private String getPlayerColor(int currentPlayer, boolean capitalize) {
    	if (currentPlayer == 1 && !capitalize)
    		return "blue";
    	else if (currentPlayer == 2 && !capitalize)
    		return "red";
    	else if (currentPlayer == 1 && capitalize)
    		return "Blue";
    	else if (currentPlayer == 2 && capitalize)
    		return "Red";
    	return null;
    }
    
    /**
     * Creates a display for the game.
     */
    private void displayGame() {
        this.frame.setContentPane(new BlueRedChompDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
  
    /**
     * Moves to a new game
     * @param game  New game to make a move to.
     */
    private void move(BlueRedChomp game) {
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
    	final int width = this.game.getWidth();
    	final int height = this.game.getHeight();
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
    private void clickMove(int input) {
    	if (!gameFinished){
    		BlueRedChomp nextPosition = null;
    		nextPosition = this.game.getPosition(input, currentPlayer);
    		if (nextPosition == null) {
    			System.out.println("Sorry, that is not a legal move for you.");
    		} else {
    	    	this.move(nextPosition);
    	    	if (combinationGame != null) combinationGame.moveMade();
    	    	if(this.game.hasMoves(currentPlayer)) {
    	    		System.out.println("It is now " + getPlayerColor(this.currentPlayer, false) + " player's turn.");
        			System.out.println(getPlayerColor(this.currentPlayer, true) + " player, please choose a cookie.");
    	    	} else if (combinationGame == null || this.gameFinished == true) {
    	    		
    				System.out.println("Sorry, " + getPlayerColor(this.currentPlayer, false) + " player, you have no moves left, you lose.");
    				System.out.println("Close the game window to end this program.");
    			} 
    		}
    	}

    }

    /**
     * Plays the game, alternating turns between the two players.
     */
    public void playGame() {
        this.frame = new JFrame("BlueRedChomp");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new BlueRedChompDisplay(this.game)).getPreferredSize());
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
        BlueRedChomp nextPosition;
        int input = 0;
        while ((this.currentPlayer == 1) || (this.currentPlayer == 2)) {
             System.out.println();
             System.out.println("It is now the " + getPlayerColor(this.currentPlayer, false) + " player's turn.");
             nextPosition = null;
             while (nextPosition == null) {
                 System.out.println(getPlayerColor(this.currentPlayer, true) + " player, please choose a cookie. (Boxes are numbered 1-25, left-to-right)");
                 try {
                	 input = Integer.parseInt(in.readLine());      	 
                 } catch (IOException ioe) {
                     System.err.println(ioe.getStackTrace());
                     input = -1;
                 }
                 nextPosition = this.game.getPosition(input, currentPlayer);
                 if (input == game.getPoisonCookie()) break; 
                 else if (nextPosition == null) {
                     System.out.println("Sorry, that is not a legal move for you.");
                 } else {
                	System.out.println(nextPosition.toString());
                 }
             }
             if (!game.hasMoves(currentPlayer)) break;
             this.move(nextPosition);
         }
         System.out.println("Sorry, " + getPlayerColor(this.currentPlayer, false) + " player, you ate the poison cookie, you lose.");
         System.out.println("Close the game window to end this program.");
    }
   
    /**
     * Main method for testing.
     */
    public static void main(String[] args) { 
        new PlayBlueRedChomp().setupGame();
//    	playRandomGame();
    }
    
    /**
     * Provide a settings window for a set-up game. 
     */
    protected Runnable setupGame() {
    	this.settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.settingsFrame.setPreferredSize((new BlueRedChompSettings(this)).getPreferredSize());
    	settingsFrame.setContentPane(new BlueRedChompSettings(this));
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
    protected void playSetGame(int boardWidth, int boardHeight) {
    	BlueRedChomp game0 = new BlueRedChomp(boardWidth, boardHeight);
    	PlayBlueRedChomp pTAF = new PlayBlueRedChomp(game0);
        pTAF.playGame();
    }
    
    /**
     * Play a game with random setup parameters. 
     */
    protected void playRandomGame() {
    	int minBoardWidth = 4;
    	int maxBoardWidth = 10;
    	int minBoardHeight = 4;
    	int maxBoardHeight = 8;
    	int width = (int)(Math.random() * (maxBoardWidth - minBoardWidth + 1)) + minBoardWidth;
    	int height = (int)(Math.random() * (maxBoardHeight - minBoardHeight + 1)) + minBoardHeight;
        BlueRedChomp game0 = new BlueRedChomp(width,height);
        this.game = game0;
    	playGame();
    }
}

/**
 * A Swing container that renders a graphical representation of a Blue Red Chomp settings window.
 */
class BlueRedChompSettings extends JPanel 
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
    public BlueRedChompSettings(final PlayBlueRedChomp playGame) {
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
    	
    	JButton button1 = new JButton("Play BlueRedChomp");
		add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {  
				settingsFrame.dispose();
				Thread t = new Thread(PlayBlueRedChomp.giveSetGame(playGame, boardWidth, boardHeight));
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
 * A Swing container that renders a graphical representation of a BlueRedChomp game.
 */
class BlueRedChompDisplay extends JPanel {

    //instance variables
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; // Annoying warning eliminator
	/**
     * The BlueRedChomp game this will display.
     */
    BlueRedChomp game;
    
    /**
     * Class constructor.
     *
     * @param game      The BlueRedChomp instance this will display.
     */
    public BlueRedChompDisplay(BlueRedChomp game) {
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
	            if (this.game.getValue(i, j) == 1) {
	            	graphics.setColor(Color.BLUE);
	            	graphics.fillOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            	graphics.setColor(Color.BLACK);
	            	graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            } else if (this.game.getValue(i, j) == 2) {
	            	graphics.setColor(Color.PINK);
	            	graphics.fillOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            	graphics.setColor(Color.BLACK);
	            	graphics.drawOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            } else if (this.game.getValue(i, j) == 3) {
	            	graphics.setColor(Color.RED);
	            	graphics.fillOval(15 + (30 * i), 15 + (30 * j), 20, 20);
	            	graphics.setColor(Color.BLACK);
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
    
}
        