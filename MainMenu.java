import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {

public static void main (String[] args){    
  JFrame frame = new JFrame("Main Menu");
  frame.setVisible(true);
  frame.setSize(500,200);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  JPanel panel = new JPanel();
  frame.add(panel);
  
  
//button for "Play Toads and Frogs"
  JButton button = new JButton("Play Toads and Frogs");
  panel.add(button);
  button.addActionListener (new GoPlayToadsAndFrogs());
  
  //chomp button
  JButton button2 = new JButton("Play Chomp");
  panel.add(button2);
  button.addActionListener (new GoPlayChomp()); 
}


static class GoPlayToadsAndFrogs implements ActionListener {        
  public void actionPerformed (ActionEvent e) {     
	  PlayToadsAndFrogs.main(null);
  }
}   
static class GoPlayChomp implements ActionListener {        
  public void actionPerformed (ActionEvent e) {     
   
  }
}   
}