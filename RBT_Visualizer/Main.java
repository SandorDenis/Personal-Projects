import javax.swing.JFrame;

/**
 * Main.java - implementeaza GUI.java (interfata grafica pentru vizualizarea unui arbore RB)
 * @author Sandor Denis
 * @version 1.0
 * @since 01.01.2021
 */

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Red Black Tree");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new GUI());
		frame.pack();
		frame.setVisible(true);
	}

}
