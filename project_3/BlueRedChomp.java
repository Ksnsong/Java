/**
 * This class describes a game position of BlueRedChomp. 
 * 
 * @author Ethan McQueen, Kei Ng
 * @version 1.0
 */

import java.util.ArrayList;

public class BlueRedChomp {
	
	private ArrayList<ArrayList<Integer>> boxes;
	
	/**
	 * A method to get the width of the game board.
	 * @return	The width of the game board.
	 */
	public int getWidth() {
		return this.boxes.size();
	}
	
	/**
	 * A method to get the height of the game board.
	 * @return	The height of the game board.
	 */
	public int getHeight() {
		return this.boxes.get(0).size();
	}
	
	/**
	 * A method to get a vertex value.
	 * @param	i	The horizontal index.
	 * @param	j	The vertical index.
	 * @return	The value at the vertex with the input coordinates. 
	 */
	public int getValue(int i, int j) {
		return this.boxes.get(i).get(j);
	}
	
    /**
     * Constructor for a default 5x5 game board.
     */
    public BlueRedChomp() {
       this.boxes = new ArrayList<ArrayList<Integer>>();
        int width = 5;
        int height = 5;
        for (int i = 0; i < width; i++) {
        	this.boxes.add(new ArrayList<Integer>());
            for (int j = 0; j < height; j++) {
            	if (i == 0 && j == height-1) {
            		this.boxes.get(i).add(2);
            	} else {
            		this.boxes.get(i).add(1);
            	}
            }
        }
    }
	
    /**
     * Constructor for generic game.
     * @param width        Width of the game board.
     * @param height       Height of the game board.
     */
    public BlueRedChomp(int width, int height) {
        this.boxes = new ArrayList<ArrayList<Integer>>();
        boolean fairnessCheckBlue = false;
        for (int i = 0; i < width; i++) {
        	this.boxes.add(new ArrayList<Integer>());
            for (int j = 0; j < height; j++) {
            	if (i == 0 && j == height-1) {
            		this.boxes.get(i).add(2);
            	} else if (i == 0 && j == height-2) {
            		if (Math.random()>.5){
            			this.boxes.get(i).add(1);
            			fairnessCheckBlue = true;
            		} else {
            			this.boxes.get(i).add(3);
            		}
            	} else if (i == 1 && j == height-1) {
            		if (fairnessCheckBlue){
            			this.boxes.get(i).add(3);
            		} else {
            			this.boxes.get(i).add(1);
            		}
            	} else {
            		if (Math.random()>.5){
            			this.boxes.get(i).add(1);
            		} else {
            			this.boxes.get(i).add(3);
            		}
            	}
            }
        }
    }  
    
    /**
     * Constructor for any game position.
     * @param boxes        Contents of the boxes.
     */
    public BlueRedChomp(ArrayList<ArrayList<Integer>> boxesInput) {
        this.boxes = boxesInput;
    }  
    
    /**
     * Get the board position number of the poison cookie (the bottom-left corner). 
     */
    public int getPoisonCookie() {
    	int width = this.boxes.size();
        int height = this.boxes.get(0).size();
        return width * height - (width - 1);
    }
    
    /**
     * Gets the current game position. 
     * @param boxes        Contents of the boxes.
     */
    public BlueRedChomp getPosition(int boxNumber, int currentPlayer) {
        int width = this.boxes.size();
        int height = this.boxes.get(0).size();
        int currentBox = 1;
        for (int j = 0; j < height; j++) {							// From top-to-bottom...
        	for (int i = 0; i < width; i++) {						// and left-to-right, number the boxes... 
        		if (boxNumber == currentBox) {						// and if the chosen box is found, find its indexes 
        			if (boxes.get(i).get(j) == 0) {
        				// Illegal move
        				System.out.println("Illegal move branch.");//debug
        				return null;
        			} else if (boxes.get(i).get(j) == 1 && currentPlayer == 1) {
        				// Regular move
        				System.out.println("Regular move branch.");//debug
        				for (int x = i; x < width; x++) {			// Set blanks from current x index rightward
        		        	for (int y = 0; y <= j; y++) {			// Set blanks from top to current y index
        		        		boxes.get(x).set(y, 0);
        		        	}
        				}        					
        				return new BlueRedChomp(boxes);
        			} else if (boxes.get(i).get(j) == 3 && currentPlayer == 2) {
        				// Regular move
        				System.out.println("Regular move branch.");//debug
        				for (int x = i; x < width; x++) {			// Set blanks from current x index rightward
        		        	for (int y = 0; y <= j; y++) {			// Set blanks from top to current y index
        		        		boxes.get(x).set(y, 0);
        		        	}
        				}        					
        				return new BlueRedChomp(boxes);
        			} else {
        				System.out.println("Illegal move branch (end version).");//debug
        				return null;
        			}
        		} else {
        			currentBox++;
        		}
        	}
    	}
    	return null;
    }
    
    /**
     * Determines if there are any moves that the indicated player can make. 
     * @return	True if the indicated player has a legal move. 
     */
    public boolean hasMoves(int currentPlayer) {
    	int currentBox = 1;
    	for (int j = 0; j < this.getHeight(); j++) {
    		for (int i = 0; i < this.getWidth(); i++) {
    			if (this.boxes.get(i).get(j) == 1 && currentPlayer == 1)
    				return true;
    			if (this.boxes.get(i).get(j) == 3 && currentPlayer == 2)
    				return true;
    			currentBox++;
    		}
    	}
    	return false;
    }
    
    /**
     * Returns a string version of this.
     * @param indent    Indentation string.
     */
    public String toString(String indent) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(indent + "BlueRedChomp\n");
        //stringBuffer.append(indent + "|");
        int width = this.boxes.size();
        int height = this.boxes.get(0).size();
        for (int j = 0; j < height; j++) {
        	for (int i = 0; i < width; i++) {
        		stringBuffer.append(" ");
        		if (this.boxes.get(i).get(j) == 0) {
        			stringBuffer.append("X");
        		} else if (this.boxes.get(i).get(j) == 1) {
        			stringBuffer.append("B");
        		} else if (this.boxes.get(i).get(j) == 2) {
        			stringBuffer.append("P");
        		} else if (this.boxes.get(i).get(j) == 3) {
        			stringBuffer.append("R");
        		} else {
        			//this case should never happen.  If we reach this, something went logically wrong!
        			stringBuffer.append(this.boxes.get(i).get(j));
        		}
        	}
        	//stringBuffer.append(" |");
        	stringBuffer.append("\n");
        }
        //stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    /**
     * Returns a string version of this.
     */
    public String toString() {
        return this.toString("");
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
