import java.util.ArrayList;

public class Chomp {
	
	public ArrayList<ArrayList<Integer>> boxes;
	
    /**
     * Constructor for a default 5x5 game board.
     */
    public Chomp() {
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
    public Chomp(int width, int height) {
        this.boxes = new ArrayList<ArrayList<Integer>>();
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
     * Constructor for any game position.
     * @param boxes        Contents of the boxes.
     */
    public Chomp(ArrayList<ArrayList<Integer>> boxesInput) {
        this.boxes = boxesInput;
    }  
    
    public int getPoisonCookie() {
    	int width = this.boxes.size();
        int height = this.boxes.get(0).size();
        return width * height - (width - 1);
    }
    
    /**
     * Gets the current game position. 
     * @param boxes        Contents of the boxes.
     */
    public Chomp getPosition(int boxNumber) {
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
        			} else if (boxes.get(i).get(j) == 1) {
        				// Regular move
        				System.out.println("Regular move branch.");//debug
        				for (int x = i; x < width; x++) {			// Set blanks from current x index rightward
        		        	for (int y = 0; y <= j; y++) {			// Set blanks from top to current y index
        		        		boxes.get(x).set(y, 0);
        		        	}
        				}        					
        				return new Chomp(boxes);
        			} else if (boxes.get(i).get(j) == 2) {
        				// Losing move
        				System.out.println("Losing move branch.");//debug
        				return new Chomp(boxes);
        			} 
        		} else {
        			currentBox++;
        		}
        	}
    	}
    	return null;
    }
    
    /**
     * Returns a string version of this.
     * @param indent    Indentation string.
     */
    public String toString(String indent) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(indent + "Chomp\n");
        //stringBuffer.append(indent + "|");
        int width = this.boxes.size();
        int height = this.boxes.get(0).size();
        for (int j = 0; j < height; j++) {
        	for (int i = 0; i < width; i++) {
        		stringBuffer.append(" ");
        		if (this.boxes.get(i).get(j) == 0) {
        			stringBuffer.append("X");
        		} else if (this.boxes.get(i).get(j) == 1) {
        			stringBuffer.append("C");
        		} else if (this.boxes.get(i).get(j) == 2) {
        			stringBuffer.append("P");
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
