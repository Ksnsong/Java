/**
 * This class provides a framework for interface and logic for playing various games. 
 * @author Ethan McQueen, Kei Ng
 * @version 4.0
 */

import javax.swing.JFrame;

public abstract class PlayGame {
	
	/** Current Player. */
    protected int currentPlayer;
    
    /** Number of players */
    protected final int NUM_PLAYERS = 2;
    
    /** Is the game finished? */
	protected boolean gameFinished = false;
	
    /** The combined game this game is a part of. */
    protected PlayCombinationGameMenu combinationGame;
    
    /** JFrame we are displaying the game in. */
    protected JFrame frame;
    
    /** JFrame we are displaying the game settings in. */
    protected final JFrame settingsFrame = new JFrame("Settings");
    
    /** Window frame horizontal position. */
    protected int framePositionX;
    
    /** Window frame vertical position. */
    protected int framePositionY;
	
    /**
     * Returns the game window height. 
     * @return The height of our window named "frame".
     */
    public int getFrameHeight() {
    	return this.frame.getHeight();
    }
    
    /**
     * Returns the game window width. 
     * @return The width of our window named "frame".
     */
    public int getFrameWidth() {
    	return this.frame.getWidth();
    }
    
    /**
     * Switches the current player. 
     */
    public void switchPlayer() {
    	this.currentPlayer++;
        if (this.currentPlayer > NUM_PLAYERS) {
            currentPlayer = 1;
        }
    }
    
    /**
     * Indicates whether the current player has any legal moves.
     * @param currentplayer	The current player. 
     */
    public boolean hasMoves(int currentPlayer) {
    	// Game-specific method.
    	System.out.println("PlayGame.hasMoves(): This should not show.");
    	return false;
    }
    
    /**
     * Set the game's completion status to true. 
     */
    public void setGameFinished() {
    	this.gameFinished = true;
    }
    
    /**
     * Provide a random game for a combination game. 
     */
    public Runnable giveRandomGame(int positionX, int positionY, PlayCombinationGameMenu combinationGame) {
    	this.framePositionX = positionX;
    	this.framePositionY = positionY;
    	this.combinationGame = combinationGame;
    	if (combinationGame != null) this.currentPlayer = 1;
    	return new PlayRandomGame(this);
    }
    
    /**
     * A class to create random games as runnable objects. 
     */
    private class PlayRandomGame implements Runnable {
    	private PlayGame playGame;
    	private PlayRandomGame(PlayGame playGame) {
    		this.playGame = playGame;
    	}
        /** Implement the runnable interface. */
        public void run() {
        	playGame.playRandomGame();
        }
    }

    /**
     * Play a game with random setup parameters. 
     */
	protected void playRandomGame() {
		// Game-specific method.
		System.out.println("PlayGame.playRandomGame(): This should not show.");
	}
	
    /**
     * Provide a runnable set-up game. 
     * @return A new runnable PlaySetGame object, which runs a game with the indicated parameters. 
     */
    protected static Runnable giveSetGame(PlayGame playGame, int boardWidth, int parameter2) {
    	return new PlaySetGame(playGame, boardWidth, parameter2);
    }
    
    /**
     * A class to create set-up games as runnable objects. 
     */
    private static class PlaySetGame implements Runnable {
    	private PlayGame playGame;
    	private int boardWidth;
    	private int parameter2; // Board height or fill chance, depending on game type.
    	private PlaySetGame(PlayGame playGame, int boardWidth, int parameter2) {
    		this.playGame = playGame;
    		this.boardWidth = boardWidth;
    		this.parameter2 = parameter2;
    	}
        /** Implement the runnable interface. */
        public void run() {
        	playGame.playSetGame(boardWidth, parameter2);
        }
    }
    
    /**
     * Play a game with indicated setup parameters. 
     * @param boardWidth	The number of spaces across the width of the game board. 
     * @param paramter2		Board height or fill chance, depending on game type. 
     */
	protected void playSetGame(int boardWidth, int parameter2) {
		// Game-specific method.
		System.out.println("PlayGame.playSetGame(): This should not show.");
	}
	
    /**
     * Provide a settings window for a set-up game. 
     */
	protected Runnable setupGame() {
		// Game-specific method.
		System.out.println("PlayGame.setupGame(): This should not show.");
		return null;
	}
	
}