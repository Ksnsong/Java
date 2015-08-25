/**
 * ToadsAndFrogs.java describes a game of Toads and Frogs.  More information about this game is available here: http://en.wikipedia.org/wiki/Toads_and_Frogs_%28game%29
 * 
 * @author Kyle Burke 
 * @version 1.0 (a version number or a date)
 */

//imported packages 
import java.lang.*;
import java.io.*;
import java.util.*;


public class ToadsAndFrogs {

    // instance variables 
    
    /**
     * The number of boxes in the game.
     */
    public int size;
    
    /**
     * Contents of the boxes.  0 is empty, 1 is a Toad, -1 is a Frog.
     */
    public int[] boxes;

    /**
     * Constructor for a default basic game with five boxes, one toad and one frog.
     */
    public ToadsAndFrogs() {
        // initialise instance variables
        this.size = 5;
        this.boxes = new int[this.size];
        this.boxes[0] = 0;
        this.boxes[1] = 1;
        this.boxes[2] = 0;
        this.boxes[3] = -1;
        this.boxes[4] = 0;
    }

    /**
     * Constructor for generic game.
     * 
     * @param boxes        Contents of the boxes.
     */
    public ToadsAndFrogs(int size, int[] boxes) {
        this.size = size;
        //don't alias!  Create a new array!
        this.boxes = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.boxes[i] = boxes[i];
        }
    }  

    /**
     * Determines whether there are any positions for the Left player to move to.
     * 
     * @return     Whether this game has any moves for the Left player.
     */
    public boolean hasLeftPositions() {
        for (int i = 0; i < this.boxes.length; i++) {
            if (this.boxes[i] == 1 && i+1 < this.boxes.length) {
                if (this.boxes[i+1] == 0) {
                    return true;
                } else if (this.boxes[i+1] == -1 && i+2 < this.boxes.length && this.boxes[i+2] == 0) {
                    return true;
                }
            }
        }
        return false;
    } 

    /**
     * Determines whether there are any positions for the Right player to move to.
     * 
     * @return     Whether this game has any moves for the Right player.
     */
    public boolean hasRightPositions() {
        for (int i = 0; i < this.boxes.length; i++) {
            if (this.boxes[i] == -1 && i > 0) {
                if (this.boxes[i-1] == 0) {
                    return true;
                } else if (this.boxes[i-1] == 1 && i > 1 && this.boxes[i-2] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Get the different possible moves for the Left player.
     * 
     * @return  A Vector of ToadsAndFrogs consisting of all moves the Left player can legally make.
     */
    public Vector<ToadsAndFrogs> getLeftPositions() {
        Vector<ToadsAndFrogs> positions = new Vector<ToadsAndFrogs>();
        int[] positionBoxes;  //need another variable to prevent aliasing
        
        for (int i = 0; i < this.boxes.length; i++) {
            //re-initialize to prevent aliasing.
            positionBoxes = new int[this.boxes.length];
            if (this.boxes[i] == 1 && i+1 < this.boxes.length) {
                if (this.boxes[i+1] == 0) {
                //create the new position
                for (int j = 0; j < i; j++) {
                    positionBoxes[j] = this.boxes[j];
                }
                positionBoxes[i] = 0; //where the Toad moved from
                positionBoxes[i+1] = 1; //where the Toad moves to
                for (int j = i+2; j < this.boxes.length; j++) {
                    positionBoxes[j] = this.boxes[j];
                }
                positions.add(new ToadsAndFrogs(this.boxes.length, positionBoxes));
            } else if (this.boxes[i+1] == -1 && i+2 < this.boxes.length && this.boxes[i+2] == 0) {
                //create the new position
                for (int j = 0; j < i; j++) {
                    positionBoxes[j] = this.boxes[j];
                }
                positionBoxes[i] = 0; //where the Toad moved from
                positionBoxes[i+1] = this.boxes[i+1]; //the Frog it jumped (should be -1)
                positionBoxes[i+2] = 1; //where the Toad moved to
                for (int j = i+3; j < this.boxes.length; j++) {
                    positionBoxes[j] = this.boxes[j];
                }
                positions.add(new ToadsAndFrogs(this.boxes.length, positionBoxes));
                }
            }
        }
        return positions;
    } 
    
    /**
     * Get the different possible moves for the Right player.
     * 
     * @return  A Vector of ToadsAndFrogs consisting of all moves the Right player can legally make.
     */
    public Vector<ToadsAndFrogs> getRightPositions() {
        Vector<ToadsAndFrogs> positions = new Vector<ToadsAndFrogs>();
        int[] positionBoxes;  //need another variable to prevent aliasing
        
        for (int i = 0; i < this.boxes.length; i++) {
            //re-initialize to prevent aliasing.
            positionBoxes = new int[this.boxes.length];
            if (this.boxes[i] == -1 && i > 0) {
                if (this.boxes[i-1] == 0) {
                    //create the new position
                    for (int j = 0; j < i-1; j++) {
                        positionBoxes[j] = this.boxes[j];
                    }
                    positionBoxes[i-1] = -1; //where the Frog moved to
                    positionBoxes[i] = 0; //where the Frog moved from
                    for (int j = i+1; j < this.boxes.length; j++) {
                        positionBoxes[j] = this.boxes[j];
                    }
                    positions.add(new ToadsAndFrogs(this.boxes.length, positionBoxes));
                } else if (this.boxes[i-1] == 1 && i > 1 && this.boxes[i-2] == 0) {
                    //create the new position
                    for (int j = 0; j < i-2; j++) {
                        positionBoxes[j] = this.boxes[j];
                    }
                    positionBoxes[i-2] = -1; //where the Frog jumped to
                    positionBoxes[i-1] = this.boxes[i-1]; //the Toad the Frog jumped
                    positionBoxes[i] = 0; //where the Frog jumped from
                    for (int j = i+1; j < this.boxes.length; j++) {
                        positionBoxes[j] = this.boxes[j];
                    }
                    positions.add(new ToadsAndFrogs(this.boxes.length, positionBoxes));
                }
            }
        }
        return positions;
    }
    
    /**
     *  Gets a position from this game.
     *  
     *  @param boxIndex     Index of the amphibian that is going to move.
     *  @return             Either the position of this corresponding to the indicated move or null if that move is not legal.
     *  
     */
    public ToadsAndFrogs getPosition(int boxIndex) {
        if (boxIndex >= this.boxes.length) {
            System.err.println("Error in getPosition(): Index beyond number of boxes!");
            return null;
        }
        //now to create the child
        int [] positionBoxes = new int[this.boxes.length];
    
        //copy the other array
        for (int i = 0; i < positionBoxes.length; i++) {
            positionBoxes[i] = this.boxes[i];
        }
        
        if (this.boxes[boxIndex] == 0) {
            //can't jump from nothing!
            System.err.println("There is no amphibian to jump from box "+ boxIndex + ".");
            return null;
        } else if (this.boxes[boxIndex] == 1) {
            if (boxIndex == this.boxes.length-1) {
                //Toads at the end can't jump
                System.err.println("There is nowhere for the Toad at box "+ boxIndex + " to move to.");
                return null;
            }
            if (this.boxes[boxIndex + 1] == 0) {
                positionBoxes[boxIndex] = 0;
                positionBoxes[boxIndex+1] = 1;
                return new ToadsAndFrogs(positionBoxes.length, positionBoxes);
            } else if (boxIndex == this.boxes.length-2) {
                //Toads can't jump at this point
                System.err.println("There is no space for the Toad at box "+ boxIndex + " to jump to.");
                return null;
            } else if (this.boxes[boxIndex + 1] == -1 && this.boxes[boxIndex + 2] == 0) {
                positionBoxes[boxIndex] = 0;
                positionBoxes[boxIndex+2] = 1;
                return new ToadsAndFrogs(positionBoxes.length, positionBoxes);
            } else {
                //This toad cannot move.
                System.err.println("There is no space for the Toad at box "+ boxIndex + " to move or jump to.");
            }
        } else { //this.boxes[boxIndex] must equal -1
            if (boxIndex == 0) {
                //Frogs at the beginning have nowhere to jump to.
                System.err.println("There is no space for the Frog at box "+ boxIndex + " to move to.");
                return null;
            } else if (this.boxes[boxIndex-1]== 0) {
                positionBoxes[boxIndex] = 0;
                positionBoxes[boxIndex-1] = -1;
                return new ToadsAndFrogs(positionBoxes.length, positionBoxes);
            } else if (boxIndex == 1) {
                //Frogs can't jump at this point
                System.err.println("There is no space for the Frog at box "+ boxIndex + " to jump to.");
                return null;
            } else if (this.boxes[boxIndex - 1] == 1 && this.boxes[boxIndex - 2] == 0) {
                positionBoxes[boxIndex] = 0;
                positionBoxes[boxIndex-2] = -1;
                return new ToadsAndFrogs(positionBoxes.length, positionBoxes);
            } else {
                //this Frog cannot move
                System.err.println("There is no space for the Frog at box "+ boxIndex + " to jump or move to.");
            }
        }
        return null;
    }
    
    /**
     * Returns whether this equals another Toads and Frogs game.
     */
    public boolean equals(Object other) {
        if (!(other instanceof ToadsAndFrogs)) {
            return false;
        } else {
            ToadsAndFrogs otherGame = (ToadsAndFrogs) other;
            if (this.boxes.length != otherGame.boxes.length) {
                return false;
            }
            for (int i = 0; i < this.boxes.length; i++) {
                if (this.boxes[i] != otherGame.boxes[i]) {
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
        for (int i = 0; i < this.boxes.length; i++) {
            code += (this.boxes[i] + 1) * Math.pow(3, i);
        }
        return code;
    }
    
    //toString
    /**
     * Returns a string version of this.
     * 
     * @param indent    Indentation string.
     * 
     */
    public String toString(String indent) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(indent + "Toads and Frogs\n");
        stringBuffer.append(indent + "|");
        for (int i = 0; i < this.boxes.length; i++) {
            stringBuffer.append(" ");
            if (this.boxes[i] == -1) {
                stringBuffer.append("F");
            } else if (this.boxes[i] == 0) {
                stringBuffer.append(" ");
            } else if (this.boxes[i] == 1) {
                stringBuffer.append("T");
            } else {
                //this case should never happen.  If we reach this, something went logically wrong!
                stringBuffer.append(this.boxes[i]);
            }
            stringBuffer.append(" |");
        }
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
    
    /**
     * Returns a string version of this.
     * 
     * 
     */
    public String toString() {
        return this.toString("");
    }
    
    
    //private methods
    
    /**
     * Unit Test!
     */
    public static void main(String[] args) {
        ToadsAndFrogs game0 = new ToadsAndFrogs();
        System.out.println(game0);
        System.out.println(" has positions: ");
        System.out.println("Left:");
        for (ToadsAndFrogs position : game0.getLeftPositions()) {
            System.out.println(position);
        }
        System.out.println("Right:");
        for (ToadsAndFrogs position : game0.getRightPositions()) {
            System.out.println(position);
        }
        System.out.println("***********");
        ToadsAndFrogs game1 = new ToadsAndFrogs(10, new int[] {0, 1, -1, -1, 0, 1, 1, -1, 0, -1});
        System.out.println(game1);
        System.out.println("Left: " + game1.hasLeftPositions());
        for (ToadsAndFrogs position : game1.getLeftPositions()) {
            System.out.println(position);
        }
        System.out.println("Right: " + game1.hasRightPositions());
        for (ToadsAndFrogs position : game1.getRightPositions()) {
            System.out.println(position);
        }
    }
        
}
