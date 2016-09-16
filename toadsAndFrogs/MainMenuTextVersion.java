import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenuTextVersion {

	public static void main (String[] args){
		System.out.println("Main Menu");
		System.out.println("To play Toads and Frogs, enter \"1\"");
		System.out.println("To play Chomp, enter \"2\"");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Integer choice;
		try {
            choice = Integer.parseInt(in.readLine());
        } catch (IOException ioe) {
            System.err.println(ioe.getStackTrace());
            choice = 0;
        }
		if (choice == 1) {
			PlayToadsAndFrogs.main(null);
		} else if (choice == 2) {
			PlayChomp.main(null);
		}
	}
}