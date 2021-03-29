package proiectRBT;

import java.awt.Color;

/**
 * Node.java - generica, extinde Comparable
 * @author Sandor Denis
 * @version 1.0
 * @since 01.01.2021
 */

public class Node<T extends Comparable<T>> {
	
	public static boolean RED = false;
	public static boolean BLACK = true;

	private boolean color = RED;
	private Node<T> left;
	private Node<T> right;
	private Node<T> p;
	private T val;


	/**
	 * constructor implicit al clasei
	 */
	public Node() {
	}

	/**
	 * constructor explicit al clasei
	 * @param val - valoarea din nod
	 */
	public Node(T val) {
		this.val = val;
	}
	
	/**
	 * setter pentru valoarea din nod
	 * @param val
	 */

	public void setData(T val) {
		this.val = val;
	}
	
	/**
	 * functie pentru stergerea parintelui
	 */

	public void removeParent() {
		if (getParent() == null)
			return;

		if (p.getLeft() == this)
			p.setLeft(null);
		else if (p.getRight() == this)
			p.setRight(null);

		this.p = null;
	}
	
	/**
	 * setter pentru parintele stang
	 * @param val
	 */

	public void setLeft(Node<T> val) {

		if (getLeft() != null)
			getLeft().setParent(null);

		if (val != null) {
			val.removeParent();
			val.setParent(this);
		}

		this.left = val;
	}
	
	/**
	 * setter pentru parintele drept
	 * @param val
	 */

	public void setRight(Node<T> val) {

		if (getRight() != null) {
			getRight().setParent(null);
		}

		if (val != null) {
			val.removeParent();
			val.setParent(this);
		}

		this.right = val;
	}
	
	/**
	 * getter pentru valoare
	 * @return
	 */

	public T getData() {
		return val;
	}
	
	/**
	 * getter pentru nodul stang
	 * @return
	 */

	public Node<T> getLeft() {
		return left;
	}

	public static Node<?> getLeft(Node<?> node) {
		return node == null ? null : node.getLeft();
	}

	/**
	 * getter pentru nodul drept
	 * @return
	 */
	public Node<T> getRight() {
		return right;
	}

	public static Node<?> getRight(Node<?> node) {
		return node == null ? null : node.getRight();
	}

	/**
	 * functie care verifica daca exista nod in partea stanga
	 * @return
	 */
	public boolean hasLeft() {
		return left != null;
	}

	/**
	 * functie care verifica daca exista nod in partea dreapta
	 * @return
	 */
	public boolean hasRight() {
		return right != null;
	}

	/**
	 * suprasctrierea functiei de afisare
	 */
	@Override
	public String toString() {
		return val.toString();
	}

	/**
	 * comparator
	 * @param node
	 * @param c
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int compare(Node<?> node, Comparable<?> c) {
		return ((Comparable) node.getData()).compareTo(c);
	}
	
	/**
	 * functie care verifica daca un nod este rosu
	 * @return - boolean
	 */

	public boolean isRed() {
		return getColor() == RED;
	}

	/**
	 * functie care verifica daca un nod este negru
	 * @return - boolean
	 */

	public boolean isBlack() {
		return !isRed();
	}

	/**
	 * functie care verifica daca un nod este rosu
	 * @return - boolean
	 */
	public static boolean isRed(Node<?> node) {
		return getColor(node) == RED;
	}

	/**
	 * functie care verifica daca un nod este negru
	 * @return - boolean
	 */
	public static boolean isBlack(Node<?> node) {
		return !isRed(node);
	}

	/**
	 * setter pentru culoarea nodului
	 * @param color
	 */
	public void setColor(boolean color) {
		this.color = color;
	}

	public static void setColor(Node<?> node, boolean color) {
		if (node == null)
			return;
		node.setColor(color);
	}

	/**
	 * getter pentru culoarea nodului
	 * @return
	 */
	public boolean getColor() {
		return color;
	}

	public static boolean getColor(Node<?> node) {
		
		return node == null ? BLACK : node.getColor();
	}

	/**
	 * getter pentru culoarea curenta a nodului
	 * @return
	 */
	public Color getRedBlack() {
		if (isRed())
			return Color.RED;
		else
			return Color.BLACK;

	}

	/**
	 * functie pentru schimbarea culorii unui nod
	 */
	public void switchColor() {
		color = !color;
	}

	public static void switchColor(Node<?> node) {
		if (node == null)
			return;

		node.setColor(!node.getColor());
	}

	/**
	 * stter pentru parinte
	 * @param p
	 */
	public void setParent(Node<T> p) {
		this.p = p;
	}

	/**
	 * getter pentru parinte
	 * @return
	 */
	public Node<T> getParent() {
		return p;
	}


	/**
	 * getter pentru bunic
	 * @return
	 */
	public Node<T> getGrandparent() {
		Node<T> p = getParent();
		return (p == null) ? null : p.getParent();
	}

	/**
	 * getter pentru frate
	 * @return
	 */
	public Node<T> getSibling() {
		Node<T> p = getParent();
		if (p != null) {
			if (this == p.getRight())
				return (Node<T>) p.getLeft();
			else
				return (Node<T>) p.getRight();
		}
		return null;
	}

	/** 
	 * getter pentru unchi
	 * @return
	 */
	public Node<T> getUncle() {
		Node<T> p = getParent();
		if (p != null) {
			return p.getSibling();
		}
		return null;
	}

}
