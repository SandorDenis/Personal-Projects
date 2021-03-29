import java.io.File;
import java.io.IOException;
import java.awt.HeadlessException;

/**
 * Main.java - clasa care contine functia main
 * @author Denis
 * @version 1.0
 * @since 29.11.2020
 */

public class Main {

	public static void main(String[] args) throws HeadlessException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		File f = new File("Dispozitive");		
		GUI userInterface = new GUI(f);
		userInterface.setVisible(true);
		userInterface.setLocationRelativeTo(null);
	}

}
