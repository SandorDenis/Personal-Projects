package proiectRBT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * TreePanel.java - extinde JPanel, implementeaza un panel pentru vizualizarea arboerelui
 * @author Sandor Denis
 * @version 1.0
 * @since 01.01.2021
 */

public class TreePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RedBlackTree<?> tree;
	private static int raza = 20;
	private static int distY = 50;
	private Color textColor = Color.WHITE;

	private Comparable<?> search;

	/**
	 * constructor explicit
	 * @param tree - de tipul RedBlackTree
	 */
	public TreePanel(RedBlackTree<?> tree) {
		this.tree = tree;
	}

	/**
	 * functie care utilizeaza Comparable pentru cautarea unui nod din arbore
	 * @param c
	 */
	public void searchComparable(Comparable<?> c) {
		search = c;
	}

	/**
	 * functie suport pentru Graphics2D, este auto-apelata atunci cand este nevoie
	 */
	@Override
	protected void paintComponent(Graphics g2d) {
		super.paintComponent(g2d);

		if (tree.isEmpty())
			return;

		Graphics2D graphics2d = (Graphics2D) g2d;

		drawTree(graphics2d, (Node<?>) tree.getRoot(), getWidth() / 2, 30, getDistantaNoduri());

	}

	/**
	 * functie pentru display arbore
	 * @param g2d - de tipul Graphics2D
	 * @param root - radacina
	 * @param x - de tipul int
	 * @param y - de tipul int
	 * @param distX - distanta pe axa x
	 */
	private void drawTree(Graphics2D g2d, Node<?> root, int x, int y, int distX) {

		if (x < 0)
			setPreferredSize(new Dimension(2 * getWidth(), getHeight()));

		if (search != null && Node.compare(root, search) == 0)
			drawSearchCircle(g2d, x, y);

		drawNode(g2d, root, x, y);

		if (root.getLeft() != null) {
			line(g2d, x - distX, y + distY, x, y);
			drawTree(g2d, (Node<?>) root.getLeft(), x - distX, y + distY, distX / 2);
		}

		if (root.getRight() != null) {
			line(g2d, x + distX, y + distY, x, y);
			drawTree(g2d, (Node<?>) root.getRight(), x + distX, y + distY, distX / 2);
		}
	}

	/**
	 * functie care deseneaza un cerc in jurul nodului cautat, daca acesta este gasit
	 * @param g2d - de tipul Graphics2D
	 * @param x - de tipul int
	 * @param y - de tipul int
	 */
	private void drawSearchCircle(Graphics2D g2d, int x, int y) {
		g2d.setColor(Color.YELLOW);
		raza += 7;
		g2d.fillOval(x - raza, y - raza, 2 * raza, 2 * raza);
		raza -= 7;
	}
	
	/**
	 * functie care deseneaza un nod, ii da culoare si text
	 * @param g2d - de tipul Graphics2D
	 * @param node - de tipul genericului Node
	 * @param x - de tipul int
	 * @param y - de tipul int
	 */

	private void drawNode(Graphics2D g2d, Node<?> node, int x, int y) {
		g2d.setColor(node.getRedBlack());
		g2d.fillOval(x - raza, y - raza, 2 * raza, 2 * raza);
		g2d.setColor(textColor);

		String text = node.toString();
		drawCentreText(g2d, text, x, y);
		g2d.setColor(Color.BLACK);
	}
	
	/**
	 * functie care asigura ca textul va fi generat in mijlocul nodului
	 * @param g2d - de tipul Graphics2D
	 * @param text - de tipul String
	 * @param x - de tipul int
	 * @param y - de tipul int
	 */

	private void drawCentreText(Graphics2D g2d, String text, int x, int y) {
		FontMetrics fm = g2d.getFontMetrics();
		double latimeText = fm.getStringBounds(text, g2d).getWidth();
		g2d.drawString(text, (int) (x - latimeText / 2), (int) (y + fm.getMaxAscent() / 2));
	}
	
	/**
	 * functie care conecteaza, dupa caz, doua noduri din arbore
	 * @param g2D -  de tipul graphics2D
	 * @param x1 - de tipul int
	 * @param y1 - de tipul int
	 * @param x2 - de tipul int
	 * @param y2 - de tipul int
	 */

    private void line(Graphics2D g2D, int x1, int y1, int x2, int y2) {
        double hypot = Math.hypot(distY, x2 - x1);
        int x11 = (int) (x1 + raza * (x2 - x1) / hypot);
        int y11 = (int) (y1 - raza * distY / hypot);
        int x21 = (int) (x2 - raza * (x2 - x1) / hypot);
        int y21 = (int) (y2 + raza * distY / hypot);
        g2D.drawLine(x11, y11, x21, y21);
    }

    /**
     * functie care seteaza distanta intre noduri pe axa X
     * @return - int
     */
    
    private int getDistantaNoduri() {
        int depth = tree.getDepth();
        return (int) Math.pow(depth, 1.5) * 20;
    }

}
