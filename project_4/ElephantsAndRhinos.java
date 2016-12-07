/**
 * ElephantsAndRhinos.java describes a game of Elephants and Rhinos.
 * @author Kyle Burke, edited by Ethan McQueen and Kei Ng
 * @version 4.0
 */

import java.util.*;

public class ElephantsAndRhinos {

    /** Contents of the boxes.  0 is empty, 1 is a Elephant, -1 is a Rhino. */
    private ArrayList<Integer> boxes;

    /**
     * Constructor for a default basic game with five boxes, one elephant and one rhino.
     */
    public ElephantsAndRhinos() {
        this.boxes = new ArrayList<Integer>();
        this.boxes.add(0);
        this.boxes.add(1);
        this.boxes.add(0);
        this.boxes.add(-1);
        this.boxes.add(0);
    }
    
    /**
     * Constructor for a custom game position.
     * @param boxes        Contents of the boxes.
     */
    public ElephantsAndRhinos(int width, int[]values) {
    	ArrayList<Integer> board = new ArrayList<Integer>();
    	for (int i=0; i<width; i++) {
    		board.add(values[i]);
    	}
        this.boxes = board;
    }  

    /**
     * Constructor for any game position.
     * @param boxes        Contents of the boxes.
     */
    public ElephantsAndRhinos(ArrayList<Integer> boxesInput) {
        this.boxes = boxesInput;
    }  
    
	/**
	 * A method to get the width of the game board.
	 * @return	The width of the game board.
	 */
	public int getWidth() {
		return this.boxes.size();
	}
	
	/**
	 * A method to get a vertex value.
	 * @param	i	The horizontal index.
	 * @return	The value at the vertex with the input coordinates. 
	 */
	public int getValue(int i) {
		return this.boxes.get(i);
	}
	
    /**
     * Determines whether there are any positions for the Left player to move to.
     * @return     Whether this game has any moves for the Left player.
     */
    public boolean hasLeftPositions() {
        for (int i = 0; i < this.getWidth(); i++) {
            if (this.getValue(i) == 1 && i+1 < this.getWidth()) {
                if (this.getValue(i+1) == 0) {
                    return true;
                } 
//                else if (this.getValue(i+1) == -1 && i+2 < this.getWidth() && this.getValue(i+2) == 0) {
//                    return true;
//                }
            }
        }
        return false;
    } 

    /**
     * Determines whether there are any positions for the Right player to move to.
     * @return     Whether this game has any moves for the Right player.
     */
    public boolean hasRightPositions() {
        for (int i = 0; i < this.getWidth(); i++) {
            if (this.getValue(i) == -1 && i > 0) {
                if (this.getValue(i-1) == 0) {
                    return true;
                } 
//                else if (this.getValue(i-1) == 1 && i > 1 && this.getValue(i-2) == 0) {
//                    return true;
//                }
            }
        }
        return false;
    }
    
    /**
     * Determines if there are any moves that the indicated player can make. 
     * @return	True if the indicated player has a legal move. 
     */
    public boolean hasMoves(int currentPlayer) {
    	if (currentPlayer == 1 && this.hasLeftPositions()){
    		return true;
    	} else if (currentPlayer == 2 && this.hasRightPositions()){
    		return true;
    	}
    	return false;
    }
    
    /**
     * Get the different possible moves for the Left player.
     * @return  An ArrayList of ElephantsAndRhinos consisting of all moves the Left player can legally make.
     */
    public ArrayList<ElephantsAndRhinos> getLeftPositions() {
    	ArrayList<ElephantsAndRhinos> positions = new ArrayList<ElephantsAndRhinos>();
        ArrayList<Integer> positionBoxes;  //need another variable to prevent aliasing
        
        for (int i = 0; i < this.getWidth(); i++) {
            //re-initialize to prevent aliasing.
            positionBoxes = new ArrayList<Integer>();
            if (this.getValue(i) == 1 && i+1 < this.getWidth()) {
                if (this.getValue(i+1) == 0) {
                //create the new position
                for (int j = 0; j < this.getWidth(); j++) {
                    positionBoxes.add(this.getValue(j));
                }
                positionBoxes.set(i, 0); //where the Elephant moved from
                positionBoxes.set(i+1, 1); //where the Elephant moves to
//                for (int j = i+2; j < this.getWidth(); j++) {
//                    positionBoxes.add(this.getValue(j));
//                }
                positions.add(new ElephantsAndRhinos(positionBoxes));
            } 
//                else if (this.getValue(i+1) == -1 && i+2 < this.getWidth() && this.getValue(i+2) == 0) {
//                //create the new position
//                for (int j = 0; j < this.getWidth(); j++) {
//                    positionBoxes.add(this.getValue(j));
//                }
//                positionBoxes.set(i, 0); //where the Elephant moved from
//                positionBoxes.set(i+1, this.getValue(i+1)); //the Rhino it jumped (should be -1)
//                positionBoxes.set(i+2, 1); //where the Elephant moved to
////                for (int j = i+3; j < this.getWidth(); j++) {
////                    positionBoxes.add(this.getValue(j));
////                }
//                positions.add(new ElephantsAndRhinos(positionBoxes));
//                }
            }
        }
        return positions;
    } 
    
    /**
     * Get the different possible moves for the Right player.
     * @return  An ArrayList of ElephantsAndRhinos consisting of all moves the Right player can legally make.
     */
    public ArrayList<ElephantsAndRhinos> getRightPositions() {
    	ArrayList<ElephantsAndRhinos> positions = new ArrayList<ElephantsAndRhinos>();
        ArrayList<Integer> positionBoxes;  //need another variable to prevent aliasing
        
        for (int i = 0; i < this.getWidth(); i++) {
            //re-initialize to prevent aliasing.
            positionBoxes = new ArrayList<Integer>();
            if (this.getValue(i) == -1 && i > 0) {
                if (this.getValue(i-1) == 0) {
                    //create the new position
                    for (int j = 0; j < this.getWidth(); j++) {
                        positionBoxes.add(this.getValue(j));
                    }
                    positionBoxes.set(i-1, -1); //where the Rhino moved to
                    positionBoxes.set(i, 0); //where the Rhino moved from
//                    for (int j = i+1; j < this.getWidth(); j++) {
//                        positionBoxes.add(this.getValue(j));
//                    }
                    positions.add(new ElephantsAndRhinos(positionBoxes));
                } 
//                else if (this.getValue(i-1) == 1 && i > 1 && this.getValue(i-2) == 0) {
//                    //create the new position
//                    for (int j = 0; j < this.getWidth(); j++) {
//                        positionBoxes.add(this.getValue(j));
//                    }
//                    positionBoxes.set(i-2, -1); //where the Rhino jumped to
//                    positionBoxes.set(i-1, this.getValue(i-1)); //the Elephant the Rhino jumped
//                    positionBoxes.set(i, 0); //where the Rhino jumped from
////                    for (int j = i+1; j < this.getWidth(); j++) {
////                        positionBoxes.add(this.getValue(j));
////                    }
//                    positions.add(new ElephantsAndRhinos(positionBoxes));
//                }
            }
        }
        return positions;
    }
    
    /**
     *  Gets a position from this game.
     *  @param boxIndex     Index of the amphibian that is going to move.
     *  @return             Either the position of this corresponding to the indicated move or null if that move is not legal.
     */
    public ElephantsAndRhinos getPosition(int boxIndex) {
        if (boxIndex >= this.getWidth()) {
            System.err.println("Error in getPosition(): Index beyond number of boxes!");
            return null;
        }
        //now to create the child
        ArrayList<Integer> positionBoxes = new ArrayList<Integer>();
    
        //copy the other array
        for (int i = 0; i < this.getWidth(); i++) {
            positionBoxes.add(this.getValue(i));
        }
        
        if (this.getValue(boxIndex) == 0) {
            //can't jump from nothing!
            System.err.println("There is no amphibian to jump from box "+ boxIndex + ".");
            return null;
        } else if (this.getValue(boxIndex) == 1) {
            if (boxIndex == this.getWidth()-1) {
                //Elephants at the end can't jump
                System.err.println("There is nowhere for the Elephant at box "+ boxIndex + " to move to.");
                return null;
            }
            if (this.getValue(boxIndex + 1) == 0) {
                positionBoxes.set(boxIndex, 0);
                positionBoxes.set(boxIndex+1, 1);
                return new ElephantsAndRhinos(positionBoxes);
            } else if (boxIndex == this.getWidth()-2) {
                //Elephants can't jump at this point
                System.err.println("There is no space for the Elephant at box "+ boxIndex + " to jump to.");
                return null;
            } else if (this.getValue(boxIndex + 1) == -1 && this.getValue(boxIndex + 2) == 0) {
                positionBoxes.set(boxIndex, 0);
                positionBoxes.set(boxIndex+2, 1);
                return new ElephantsAndRhinos(positionBoxes);
            } else {
                //This elephant cannot move.
                System.err.println("There is no space for the Elephant at box "+ boxIndex + " to move or jump to.");
            }
        } else { //this.getValue(boxIndex) must equal -1
            if (boxIndex == 0) {
                //Rhinos at the beginning have nowhere to jump to.
                System.err.println("There is no space for the Rhino at box "+ boxIndex + " to move to.");
                return null;
            } else if (this.getValue(boxIndex - 1)== 0) {
                positionBoxes.set(boxIndex, 0);
                positionBoxes.set(boxIndex-1, -1);
                return new ElephantsAndRhinos(positionBoxes);
            } else if (boxIndex == 1) {
                //Rhinos can't jump at this point
                System.err.println("There is no space for the Rhino at box "+ boxIndex + " to jump to.");
                return null;
            } else if (this.getValue(boxIndex - 1) == 1 && this.getValue(boxIndex - 2) == 0) {
                positionBoxes.set(boxIndex, 0);
                positionBoxes.set(boxIndex-2, -1);
                return new ElephantsAndRhinos(positionBoxes);
            } else {
                //this Rhino cannot move
                System.err.println("There is no space for the Rhino at box "+ boxIndex + " to jump or move to.");
            }
        }
        return null;
    }
    
    /**
     * Returns whether this equals another Elephants and Rhinos game.
     */
    public boolean equals(Object other) {
        if (!(other instanceof ElephantsAndRhinos)) {
            return false;
        } else {
            ElephantsAndRhinos otherGame = (ElephantsAndRhinos) other;
            if (this.getWidth() != otherGame.getWidth()) {
                return false;
            }
            for (int i = 0; i < this.getWidth(); i++) {
                if (this.getValue(i) != otherGame.getValue(i)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns a hash code for this game.  
     */
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < this.getWidth(); i++) {
            code += (this.getValue(i) + 1) * Math.pow(3, i);
        }
        return code;
    }
    
    /**
     * Returns a string version of this.
     * @param indent    Indentation string.
     */
    public String toString(String indent) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(indent + "Elephants and Rhinos\n");
        stringBuffer.append(indent + "|");
        for (int i = 0; i < this.getWidth(); i++) {
            stringBuffer.append(" ");
            if (this.getValue(i) == -1) {
                stringBuffer.append("F");
            } else if (this.getValue(i) == 0) {
                stringBuffer.append(" ");
            } else if (this.getValue(i) == 1) {
                stringBuffer.append("T");
            } else {
                //this case should never happen.  If we reach this, something went logically wrong!
                stringBuffer.append(this.getValue(i));
            }
            stringBuffer.append(" |");
        }
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
    
    /**
     * Returns a string version of this.
     */
    public String toString() {
        return this.toString("");
    }

    /**
     * Unit Test!
     */
    public static void main(String[] args) {
        ElephantsAndRhinos game0 = new ElephantsAndRhinos();
        System.out.println(game0);
        System.out.println(" has positions: ");
        System.out.println("Left:");
        for (ElephantsAndRhinos position : game0.getLeftPositions()) {
            System.out.println(position);
        }
        System.out.println("Right:");
        for (ElephantsAndRhinos position : game0.getRightPositions()) {
            System.out.println(position);
        }
        System.out.println("***********");
        ElephantsAndRhinos game1 = new ElephantsAndRhinos(10, new int[] {0, 1, -1, -1, 0, 1, 1, -1, 0, -1});
        System.out.println(game1);
        System.out.println("Left: " + game1.hasLeftPositions());
        for (ElephantsAndRhinos position : game1.getLeftPositions()) {
            System.out.println(position);
        }
        System.out.println("Right: " + game1.hasRightPositions());
        for (ElephantsAndRhinos position : game1.getRightPositions()) {
            System.out.println(position);
        }
    } 
}