/**
 * This class provides a GUI menu for playing a combination game where multiple games are played together. 
 * @author Ethan McQueen, Kei Ng
 * @version 4.0
 */

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayCombinationGameMenu extends MainMenu implements Runnable {
	
	/** Debugging toggles. */
	public static final boolean POSITIONING_DEBUG = false;
	public static final boolean CLICK_DEBUG = false;
	public static final boolean COMBINATION_DEBUG = false;
	/** A collection of games to play that were indicated by the user. */
	private ArrayList<PlayGame> gameList = new ArrayList<PlayGame>();
	
    /** Current Player. */
    private int currentPlayer = 1;
	
    /** 
     *  Empty constructor.
     */
	public PlayCombinationGameMenu() {
		
	}
	
	/** 
     *  Implement the runnable interface. 
     */
	public void run() {
		chooseGames();
	}
	
	/** 
     *  Main method for testing. 
     */
	public static void main(String[] args) {
		new PlayCombinationGameMenu().chooseGames();
	}
	
	/** 
     *  Create the menu GUI for choosing games for a combination game. 
     */
	private void chooseGames() {
		final JFrame frame = new JFrame("Combination Game Menu");
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // Center combination game menu window on the screen
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1,20,20));
		frame.getContentPane().add(panel);
		makeButton(true, frame, panel, "Add a Toads and Frogs game", "PlayToadsAndFrogs");
		makeButton(true, frame, panel, "Add a Chomp game", "PlayChomp");
		makeButton(true, frame, panel, "Add a Snort game", "PlaySnort");
		makeButton(true, frame, panel, "Add an Elephants and Rhinos game", "PlayElephantsAndRhinos");
		makeButton(true, frame, panel, "Add a Blue-Red Chomp game", "PlayBlueRedChomp");
		makeButton(false, frame, panel, "Start the combination game", null);
		frame.setVisible(true);
	}
	
	/** 
     *  Make a button for the combination game GUI. 
     */
	private void makeButton(final boolean addGame, final JFrame frame, JPanel panel, String buttonText, final String gameType) {
		JButton button = new JButton(buttonText);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (addGame) {
					gameList.add(gameFactory(gameType));
				}
				else {
					frame.dispose();
					Thread t = new Thread(giveCombinedGame(gameList));
					t.start();
				}
			}
		});
	}
	
	/** 
     *  Launch all the games in the game list. 
     */
    public Runnable giveCombinedGame(ArrayList<PlayGame> gameList) {
    	ArrayList<Integer> frameHeights = new ArrayList<Integer>();
    	ArrayList<Integer> frameWidths = new ArrayList<Integer>();
		for (int i = 0; i < gameList.size(); i++) {
			Thread t = new Thread(gameList.get(i).giveRandomGame(getFramePosition(true, frameWidths, frameHeights), getFramePosition(false, frameWidths, frameHeights), this));
			t.start();
			if(COMBINATION_DEBUG)System.out.println("PlayGame.playGame(): new thread ID: " + t.getId());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while(true) {
				try {
					if (gameList.get(i).getFrameHeight() != 0 && gameList.get(i).getFrameWidth() != 0) {
						frameHeights.add(gameList.get(i).getFrameHeight());
						frameWidths.add(gameList.get(i).getFrameWidth());
						break;
					}
				} catch (NullPointerException e) {}
			}
		}
		return null;
    }
    
    /** 
     *  Games in a combination game report when a legal move is made using this method. 
     */
    public void moveMade() {
    	this.switchPlayer();
    	for (int i = 0; i < gameList.size(); i++) {
    		if(COMBINATION_DEBUG)System.out.println("PlayCombinationGame.moveMade(): switchplayer: i = " + i);
    		gameList.get(i).switchPlayer();
    	}
    	for (int i = 0; i < gameList.size(); i++) {
        	if (gameList.get(i).hasMoves(currentPlayer)) {
        		if(COMBINATION_DEBUG)System.out.println("PlayCombinationGame.moveMade(): break");
        		break;
        	}
        	if (i == gameList.size()-1) {
        		if(COMBINATION_DEBUG)System.out.println("PlayCombinationGame.moveMade(): gameFinished");
        		for (int j = 0; j < gameList.size(); j++) {
            		gameList.get(j).setGameFinished();
            	}
        	}
    	}
    }
    
    /**
     * Switches the current player. 
     */
    private void switchPlayer() {
    	this.currentPlayer++;
        if (this.currentPlayer > 2) {
            currentPlayer = 1;
        }
    }
    
    /** 
     *  Place windows on-screen such that they don't overlap or go off-screen. (Within reasonable limits) 
     */
    private static int getFramePosition(boolean width, ArrayList<Integer> frameWidths, ArrayList<Integer> frameHeights) {
    	ArrayList<Integer> rowHeights = new ArrayList<Integer>();
    	rowHeights.add(0);
    	ArrayList<Integer> rowWidths = new ArrayList<Integer>();
    	rowWidths.add(0);
    	int currentRow = 0;
    	int maxHeight = 0;
    	int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    	if(POSITIONING_DEBUG)System.out.println("PlayCombinationGame.getFramePosition(): screenWidth = " + screenWidth);
    	for (int i = 0; i < frameWidths.size(); i++) {
    		rowWidths.set(currentRow, rowWidths.get(currentRow) + frameWidths.get(i));
    		if (rowHeights.get(currentRow) < frameHeights.get(i)) {
    			rowHeights.set(currentRow, frameHeights.get(i));
    		}
    		if (rowWidths.get(currentRow) + frameWidths.get(i) > screenWidth) {
    			rowWidths.add(0);
    			rowHeights.add(0);
    			currentRow ++;
    		}
    		maxHeight = 0;
    		for (int j = 0; j < currentRow; j++) {
    			maxHeight += rowHeights.get(j);
    		}
    		if(POSITIONING_DEBUG)System.out.println("PlayCombinationGame.getFramePosition(): loop end: currentRow = " + currentRow + ", rowHeights.get(currentRow) = " + rowHeights.get(currentRow) + ", maxHeight = " + maxHeight);
    	}
    	if (width) {
    		if(POSITIONING_DEBUG)System.out.println("PlayCombinationGame.getFramePosition(): rowWidths.get(currentRow) = " + rowWidths.get(currentRow));
    		return rowWidths.get(currentRow);
    	} else if (!width) {
    		if (currentRow != 0) {
    			if(POSITIONING_DEBUG)System.out.println("PlayCombinationGame.getFramePosition(): maxHeight = " + maxHeight);
    			return maxHeight;
    		}
    		return 0;
    	}
    	return 0;
    }
}
