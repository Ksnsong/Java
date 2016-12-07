/**
 * This class provides an interface and logic for playing a game of Chomp. 
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

public class PlayChomp extends PlayGame {

    /** The underlying game. */
    private Chomp game;
    
    /** 
     * Class constructor. 
     */
    public PlayChomp() { 
        this.game = new Chomp();
        this.currentPlayer = 1; //1 is Left
    }
  
    /** 
     * Class constructor for generic game.
     * @param game  Chomp game to begin with. 
     * */
    public PlayChomp(Chomp game) {
        this.game = game;
        this.currentPlayer = 1;
    }
    
    /**
     * Creates a display for the game.
     */
    private void displayGame() {
        this.frame.setContentPane(new ChompDisplay(this.game));
        this.frame.pack();
        this.frame.repaint();
    }
  
    /**
     * Moves to a new game
     * @param nextPosition  New game to make a move to.
     */
    private void move(Chomp nextPosition) {
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
    		Chomp nextPosition = null;
    		nextPosition = this.game.getPosition(input);
    		if (nextPosition == null) {
    			System.out.println("Sorry, that is not a legal move for you.");
    		} else {
    			this.move(nextPosition);
    			if (combinationGame != null) combinationGame.moveMade();
    			if (game.hasMoves(currentPlayer)) {
    				System.out.println("It is now player " + this.currentPlayer + "'s turn.");
    				System.out.println("Player " + this.currentPlayer + ", please choose a cookie.");
    			} else if (combinationGame == null || gameFinished) {
    				this.gameFinished = true;
    				System.out.println("Sorry, player " + this.currentPlayer + ", you have no moves left, you lose.");
    				System.out.println("Close the game window to end this program.");
    			} 
    		}	
    	}
    }
    
    /**
     * Plays the game, alternating turns between the two players.
     */
    public void playGame() {
        this.frame = new JFrame("Chomp");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize((new ChompDisplay(this.game)).getPreferredSize());
        if(PlayCombinationGameMenu.POSITIONING_DEBUG)System.out.println("PlayChomp.playGame(): framePositionX = " + framePositionX + ", framePositionY = " + framePositionY);
        this.frame.setLocation(this.framePositionX, this.framePositionY);
        this.displayGame();
        this.frame.addMouseListener(new MouseListener() {
        	public void mouseClicked(MouseEvent e) {
        		if(PlayCombinationGameMenu.CLICK_DEBUG)System.out.println("PlayChomp.playGame(): main thread ID: " + Thread.currentThread().getId());
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
    
    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
    	new PlayChomp().setupGame();
//    	playRandomGame();
    }
    
    /**
     * Provide a settings window for a set-up game. 
     */
    protected Runnable setupGame() {
    	this.settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.settingsFrame.setPreferredSize((new ChompSettings(this)).getPreferredSize());
    	settingsFrame.setContentPane(new ChompSettings(this));
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
    	Chomp game0 = new Chomp(boardWidth, boardHeight);
    	PlayChomp playGame = new PlayChomp(game0);
    	playGame.playGame();
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
        Chomp game0 = new Chomp(width,height);
        this.game = game0;
    	this.currentPlayer = 1;
    	playGame();
    }
}

/**
 * A Swing container that renders a graphical representation of a Chomp window settings.
 */
class ChompSettings extends JPanel 
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
    public ChompSettings(final PlayChomp playGame) {
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
    	
    	JButton button1 = new JButton("Play Chomp");
		add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {  
				settingsFrame.dispose();
				Thread t = new Thread(PlayChomp.giveSetGame(playGame, boardWidth, boardHeight));
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
 * A Swing container that renders a graphical representation of a Chomp game.
 */
class ChompDisplay extends JPanel {

	private static final long serialVersionUID = 1L; // Annoying warning eliminator
	
	/**
     * The Chomp game this will display.
     */
    private Chomp game;
    
    /**
     * Class constructor.
     * @param game2      The Chomp instance this will display.
     */
    public ChompDisplay(Chomp game2) {
        super(new BorderLayout());
        this.game = game2;
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
     * @return  A Dimension object representing the smallest size needed to nicely display this game.
     */
    public Dimension getPreferredSize() {
        int width = 30 + 31*this.game.getWidth();
        int height = 55 + 31*this.game.getHeight();
        return new Dimension(width, height);
    }
    
}
        