import java.util.ArrayList;

public class Snort {
	
	/** The contents of the game board. */
	private ArrayList<ArrayList<Integer>> boxes;
	
	/**
	 * Constructor for a default, blank 5x5 game board.
	 */
	public Snort() {
		this.boxes = new ArrayList<ArrayList<Integer>>();
		int height = 5;
		int width = 5;
		int fillChance = 25;
		for (int j = 0; j < height; j++) {
			this.boxes.add(new ArrayList<Integer>());
			for (int i = 0; i < width; i++) {
				if ((int)Math.round(Math.random()*100) <= fillChance) {
    				int value = (int)Math.round(Math.random());
    				if (value == 0) 
    					this.boxes.get(j).add(1);
    				else if (value == 1) 
    					this.boxes.get(j).add(2);
    			} else 
    				this.boxes.get(j).add(0);
			}
		}
	}

	/**
	 * Constructor for a blank, square game board.
	 * @param	width	Width of the game board.
	 * @param	height	Height of the game board.
	 */
	public Snort(int height, int width) {
		this.boxes = new ArrayList<ArrayList<Integer>>();
		int fillChance = 25;
		for (int j = 0; j < height; j++) {
			this.boxes.add(new ArrayList<Integer>());
			for (int i = 0; i < width; i++) {
				if ((int)Math.round(Math.random()*100) <= fillChance) {
    				int value = (int)Math.round(Math.random());
    				if (value == 0) 
    					this.boxes.get(j).add(1);
    				else if (value == 1) 
    					this.boxes.get(j).add(2);
    			} else 
    				this.boxes.get(j).add(0);
			}
		}
	}
	
	/**
	 * Constructor for a blank game board with varying-length rows.
	 * @param	height	Height of the game board.
	 * @param	width	Width of the row. Supply as many of these as there are rows (the height value). 
	 */
	public Snort(int height, int... width) {
		this.boxes = new ArrayList<ArrayList<Integer>>();
		int fillChance = 25;
		for (int j = 0; j < height; j++) {
			this.boxes.add(new ArrayList<Integer>());
			for (int i = 0; i < width[j]; i++) {
				if ((int)Math.round(Math.random()*100) <= fillChance) {
    				int value = (int)Math.round(Math.random());
    				if (value == 0) 
    					this.boxes.get(j).add(1);
    				else if (value == 1) 
    					this.boxes.get(j).add(2);
    			} else 
    				this.boxes.get(j).add(0);
			}
		}
	}

	/**
	 * Constructor for any game position based on a given board.
	 * @param	boxes	Contents of the boxes.
	 */
	public Snort(ArrayList<ArrayList<Integer>> boxesInput) {
		this.boxes = boxesInput;
	}
	
	/**
	 * A method to get the width of the game board at the current height.
	 * @param	currentHeight	The height at which the width is being measured. 
	 * @return	The width of the game board.
	 */
	public int getWidth(int currentHeight) {
		return this.boxes.get(currentHeight).size();
	}
	
	/**
	 * A method to get the height of the game board.
	 * @return	The height of the game board.
	 */
	public int getHeight() {
		return this.boxes.size();
	}
	
//    /**
//     * Gets the value of an indicated vertex. 
//     * @param	moveSelection	The left-to-right numbering of the indicated vertex. 
//     * @return	The value stored at the indicated vertex. 
//     */
//    public int getValue(int moveSelection) {
//    	return boxes.get(getXY(1, moveSelection)).get(getXY(2, moveSelection));
//    }
    
	/**
	 * A method to get a vertex value.
	 * @param	i	The horizontal index.
	 * @param	j	The vertical index.
	 * @return	The value at the vertex with the input coordinates. 
	 */
	public int getValue(int i, int j) {
		return this.boxes.get(j).get(i);
	}
	
    /**
     * Gets the current game position. 
     * @param	moveSelection	The left-to-right numbering of the indicated vertex. 
     * @param	currentPlayer	The number of the currently active player
     * @return	A new Snort game of the next game position, or null if an illegal move. 
     */
    public Snort getPosition(int moveSelection, int currentPlayer) {
        if (this.isLegalMove(moveSelection, currentPlayer)){
        	boxes.get(this.getXY(2, moveSelection)).set(this.getXY(1, moveSelection), currentPlayer);
        	return new Snort(boxes);
        } else
        	return null;
    }
    
    
//     For an indicated vertex number, returns the corresponding X or Y index for that vertex. 
//     @param	moveSelection	The left-to-right numbering of the indicated vertex. 
//     @return	The chosen coordinate of the indicated vertex. 
     
    private int getXY(int axis, int moveSelection) {
    	int currentBox = 1;
        for (int j = 0; j < this.getHeight(); j++) {
        	for (int i = 0; i < this.getWidth(j); i++) {
        		if (moveSelection == currentBox) {
        			if (axis == 1)
        				return i;
        			else if (axis == 2)
        				return j;
        		} else 
        			currentBox++;
        	}
        }
		return -1;
    }
    
    /**
     * Determines if an indicated move is legal for the indicated player. 
     * @return	True if the indicated move is legal for the indicated player. 
     */
    public boolean isLegalMove(int moveSelection, int currentPlayer) {
    	int i = this.getXY(1, moveSelection);
    	int j = this.getXY(2, moveSelection);
        if (getValue(i, j) == 0 && (
	    		(i != 0 && i != this.getWidth(j)-1 && (
			    	((getValue(i-1, j) != 2 && currentPlayer == 1) ||
					(getValue(i-1, j) != 1 && currentPlayer == 2)) &&
				    ((getValue(i+1, j) != 2 && currentPlayer == 1) ||
					(getValue(i+1, j) != 1 && currentPlayer == 2))
				)) ||
	    		(i == 0 && (
			    	(getValue(i+1, j) != 2 && currentPlayer == 1) ||
			    	(getValue(i+1, j) != 1 && currentPlayer == 2)
		    	)) ||
	    		(i == this.getWidth(j)-1 &&	(
	    			(getValue(i-1, j) != 2 && currentPlayer == 1) ||
			    	(getValue(i-1, j) != 1 && currentPlayer == 2)
		    	))
    		)
    	) {
     			return true;
        }
    	return false;
    }
    
    /**
     * Determines if there are any moves that the indicated player can make. 
     * @return	True if the indicated player has a legal move. 
     */
    public boolean hasMoves(int currentPlayer) {
    	int currentBox = 1;
    	for (int j = 0; j < this.getHeight(); j++) {
    		for (int i = 0; i < this.getWidth(j); i++) {
    			if (this.isLegalMove(currentBox, currentPlayer))
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
        stringBuffer.append(indent + "Snort\n");
        //stringBuffer.append(indent + "|");
        int width = this.boxes.size();
        int height = this.boxes.get(0).size();
        for (int j = 0; j < height; j++) {
        	for (int i = 0; i < width; i++) {
        		stringBuffer.append(" ");
        		if (this.boxes.get(j).get(i) == 0) {
        			stringBuffer.append("O");
        		} else if (this.boxes.get(j).get(i) == 1) {
        			stringBuffer.append("1");
        		} else if (this.boxes.get(j).get(i) == 2) {
        			stringBuffer.append("2");
        		} else {
        			//this case should never happen.  If we reach this, something went logically wrong!
        			stringBuffer.append(this.boxes.get(j).get(i));
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
		
	}

}
