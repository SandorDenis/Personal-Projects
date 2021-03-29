package proiectRBT;

import javax.swing.JOptionPane;

/**
 * RedBlackTree.java - generica, extinde Comparable, implementeaza operatii de baza pentru RBTree
 * @author Sandor Denis
 * @version 1.0
 * @since 01.01.2021
 */

public class RedBlackTree<T extends Comparable<T>> {
    protected Node<T> root;
    protected int size = 0;
    String str = "";
    String str2 = "";
    String str3 = "";

    /**
     * getter pentru radacina
     * @return returneaza radacina arborelui
     */
    public Node<T> getRoot() {
        return this.root;
    }

    /**
     * functie pentru inserarea unui nod nou in arbore
     * @param val - valoarea numerica din nod
     */
    public void insert(T val) {
        if (this.isEmpty()) {
            this.root = new Node<T>(val);
        } else {
            this.insert(this.root, val);
        }
        this.root.setColor(Node.BLACK);
        ++this.size;
    }

    /**
     * functie pentru inserarea unui nod in arbore, dupa un nod specificat
     * @param node - nodul dupa care se doreste sa se faca inserarea
     * @param val - valoarea numerica din nod
     */
    private void insert(Node<T> node, T val) {
        if (this.contains(val)) {
            return;
        }
        if (node.getData().compareTo(val) > 0) {
            if (node.hasLeft()) {
                this.insert(node.getLeft(), val);
            } else {
                Node<T> valInsert = new Node<T>(val);
                node.setLeft(valInsert);
                this.balansareInsert(valInsert);
            }
        } else if (node.hasRight()) {
            this.insert(node.getRight(), val);
        } else {
            Node<T> valInsert = new Node<T>(val);
            node.setRight(valInsert);
            this.balansareInsert(valInsert);
        }
    }

    /**
     * functie pentru stergerea unui nod din arbore
     * @param val - valoarea care urmeaza sa fie stearsa
     */
    public void remove(T val) {
        if (!this.contains(val)) {
            return;
        }
        
        Node<T> node = this.find(val);
        if (node.getLeft() != null && node.getRight() != null) {
            Node<T> successor = this.getSuccessor(node);
            node.setData(successor.getData());
            node = successor;
        }
        
        Node<T> fixUp = node.getLeft() == null ? node.getRight() : node.getLeft();
        if (fixUp != null) {
            if (node == this.root) {
                node.removeParent();
                this.root = node;
            } else if (Node.getLeft(node.getParent()) == node) {
                node.getParent().setLeft(fixUp);
            } else {
                node.getParent().setRight(fixUp);
            }
            if (Node.isBlack(node)) {
                this.balansareDelete(fixUp);
            }
        } else if (node == this.root) {
            this.root = null;
        } else {
            if (Node.isBlack(node)) {
                this.balansareDelete(node);
            }
            node.removeParent();
        }
    }
    
    /**
     * functie care verifica daca arborele nu contine niciun nod
     * @return - returneazaz boolean
     */

    public boolean isEmpty() {
        if (this.root == null) {
            return true;
        }
        return false;
    }
    
    /**
     * functie care seteaza radacina pe null
     */

    public void clear() {
        this.root = null;
    }
    
    /**
     * getter pentru dimensiunea arborelui
     * @return - returneaza dimensiunea arborelui
     */

    public int getSize() {
        return this.size;
    }

    /**
     * @param val - valoarea specificata
     * @return - boolean 
     */

    public boolean contains(T val) {
        return this.contains(this.root, val);
    }
    
    /**
     * functie care verifica daca exista o valoare in arbore
     * @param root - radacina arborelui
     * @param val  - valoarea specificata
     * @return - boolean
     */

    private boolean contains(Node<T> root, T val) {
        if (root == null) {
            return false;
        }
        if (root.getData().compareTo(val) > 0) {
            return this.contains(root.getLeft(), val);
        }
        if (root.getData().compareTo(val) < 0) {
            return this.contains(root.getRight(), val);
        }
        return true;
    }
    

    public Node<T> find(T val) {
        return this.find(this.root, val);
    }
    
    /**
     * functie care cauta o valoare in arbore
     * @param root - radacina arborelui
     * @param val - valoarea specificata
     * @return - returneaza valoarea gasita
     */

    private Node<T> find(Node<T> root, T val) {
        if (root == null) {
            return null;
        }
        if (root.getData().compareTo(val) > 0) {
            return this.find(root.getLeft(), val);
        }
        if (root.getData().compareTo(val) < 0) {
            return this.find(root.getRight(), val);
        }
        return root;
    }

    public int getDepth() {
        return this.getDepth(this.root);
    }
    
    /**
     * getter pentru adancimea arborelui
     * @param node - nodul specificat din arbore
     * @return - returneaza adancimea arborelui (integer)
     */

    private int getDepth(Node<T> node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.getLeft());
            return left_depth > (right_depth = this.getDepth(node.getRight())) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    }
    
    /**
     * getter pentru succesor
     * @param val - valoare nod
     * @return - returneaza succesorului unui nod
     */

    private Node<T> getSuccessor(Node<T> val) {
        Node<T> leftTree = val.getLeft();
        if (leftTree != null) {
            while (leftTree.getRight() != null) {
                leftTree = leftTree.getRight();
            }
        }
        return leftTree;
    }
    
    /**
     * functie pentru autobalansarea arborelui dupa efectuarea unei inserari,
     * ia in calcul cazurile aferente RBTree
     * @param node - nodul inserat
     */

    private void balansareInsert(Node<T> node) {
        if (node == null || node == this.root || Node.isBlack(node.getParent())) {
            return;
        }
        if (Node.isRed(node.getUncle())) {
            Node.switchColor(node.getParent());
            Node.switchColor(node.getUncle());
            Node.switchColor(node.getGrandparent());
            this.balansareInsert(node.getGrandparent());
        } else if (this.hasLeftParent(node)) {
            if (this.isRightChild(node)) {
                node = node.getParent();
                this.rotateLeft(node);
            }
            Node.setColor(node.getParent(), Node.BLACK);
            Node.setColor(node.getGrandparent(), Node.RED);
            this.rotateRight(node.getGrandparent());
        } else if (this.hasRightParent(node)) {
            if (this.isLeftChild(node)) {
                node = node.getParent();
                this.rotateRight(node);
            }
            Node.setColor(node.getParent(), Node.BLACK);
            Node.setColor(node.getGrandparent(), Node.RED);
            this.rotateLeft(node.getGrandparent());
        }
    }
    
    /**
     * functie pentru balansarea arborelui dupa efectuarea unei stergeri din arbore
     * @param node - nodul sters
     */

    @SuppressWarnings("unchecked")
	private void balansareDelete(Node<T> node) {
        while (node != this.root && node.isBlack()) {
            Node<T> sibling = node.getSibling();
            if (node == Node.getLeft(node.getParent())) {
                if (Node.isRed(sibling)) {
                    Node.setColor(sibling, Node.BLACK);
                    Node.setColor(node.getParent(), Node.RED);
                    this.rotateLeft(node.getParent());
                    sibling = (Node<T>) Node.getRight(node.getParent());
                }
                if (Node.isBlack(Node.getLeft(sibling)) && Node.isBlack(Node.getRight(sibling))) {
                    Node.setColor(sibling, Node.RED);
                    node = node.getParent();
                    continue;
                }
                if (Node.isBlack(Node.getRight(sibling))) {
                    Node.setColor(Node.getLeft(sibling), Node.BLACK);
                    Node.setColor(sibling, Node.RED);
                    this.rotateRight(sibling);
                    sibling = (Node<T>) Node.getRight(node.getParent());
                }
                Node.setColor(sibling, Node.getColor(node.getParent()));
                Node.setColor(node.getParent(), Node.BLACK);
                Node.setColor(Node.getRight(sibling), Node.BLACK);
                this.rotateLeft(node.getParent());
                node = this.root;
                continue;
            }
            if (Node.isRed(sibling)) {
                Node.setColor(sibling, Node.BLACK);
                Node.setColor(node.getParent(), Node.RED);
                this.rotateRight(node.getParent());
                sibling = (Node<T>) Node.getLeft(node.getParent());
            }
            if (Node.isBlack(Node.getLeft(sibling)) && Node.isBlack(Node.getRight(sibling))) {
                Node.setColor(sibling, Node.RED);
                node = node.getParent();
                continue;
            }
            if (Node.isBlack(Node.getLeft(sibling))) {
                Node.setColor(Node.getRight(sibling), Node.BLACK);
                Node.setColor(sibling, Node.RED);
                this.rotateLeft(sibling);
                sibling = (Node<T>) Node.getLeft(node.getParent());
            }
            Node.setColor(sibling, Node.getColor(node.getParent()));
            Node.setColor(node.getParent(), Node.BLACK);
            Node.setColor(Node.getLeft(sibling), Node.BLACK);
            this.rotateRight(node.getParent());
            node = this.root;
        }
        Node.setColor(node, Node.BLACK);
    }
    
    /**
     * functie recurenta pentru parcurgerea in postordine
     * @param node 
     */
    
    private void postOrderHelper(Node<T> node) {
	    if (node != null) {
	      postOrderHelper(node.getLeft());
	      postOrderHelper(node.getRight());
	      str3 += node.getData() + " ";
	    }
	  }
    
  /**
   * functie recurenta pentru parcurgerea in preordine
   * @param node
   */
    
  private void preOrderHelper(Node<T> node) {
	    if (node != null) {
	      str += node.getData() + " ";
	      preOrderHelper(node.getLeft());
	      preOrderHelper(node.getRight());
	    }
	    
	  }
  
  /**
   * functie recurenta pentru parcurgerea in inordine
   * @param node
   */
	  private void inOrderHelper(Node<T> node) {
	    if (node != null) {
	      inOrderHelper(node.getLeft());
	      str2 += node.getData() + " ";
	      inOrderHelper(node.getRight());
	    }
	  }
  
	  /**
	   * functie pentru afisarea rezultatului obtinut in urma parcurgerii
	   * in preordine
	   */
  public void preorder() {
	    preOrderHelper(this.root);
	    JOptionPane.showMessageDialog(null, str);
	    str = "";
	  }

  /**
   * functie pentru afisarea rezultatului obtinut in urma parcurgerii
   * in inordine
   */
	  public void inorder() {
	    inOrderHelper(this.root);
	    JOptionPane.showMessageDialog(null, str2);
	    str2 = "";
	  }

	  /**
	   * functie pentru afisarea rezultatului obtinut in urma parcurgerii
	   * in postordine
	   */
	  public void postorder() {
	    postOrderHelper(this.root);
	    JOptionPane.showMessageDialog(null, str3);
	    str3 = "";
	  }
	  
	  /**
	   * functie pentru efectuarea unei rotari la dreapta
	   * @param node - nodul de la care se face rotarea
	   */

    private void rotateRight(Node<T> node) {
        if (node.getLeft() == null) {
            return;
        }
        Node<T> leftTree = node.getLeft();
        node.setLeft(leftTree.getRight());
        if (node.getParent() == null) {
            this.root = leftTree;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(leftTree);
        } else {
            node.getParent().setRight(leftTree);
        }
        leftTree.setRight(node);
    }
    
    /**
     * functie pentru efectuarea unei rotari la stanga
     * @param node - nodul de la care se face rotarea
     */

    private void rotateLeft(Node<T> node) {
        if (node.getRight() == null) {
            return;
        }
        Node<T> rightTree = node.getRight();
        node.setRight(rightTree.getLeft());
        if (node.getParent() == null) {
            this.root = rightTree;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(rightTree);
        } else {
            node.getParent().setRight(rightTree);
        }
        rightTree.setLeft(node);
    }

    /**
     * functie care verifica daca un nod are parinte in dreapta sa
     * @param node
     * @return - boolean
     */
    
    private boolean hasRightParent(Node<T> node) {
        if (Node.getRight(node.getGrandparent()) == node.getParent()) {
            return true;
        }
        return false;
    }
    
    /**
     * functie care verifica daca un nod are parinte in staga sa
     * @param node
     * @return - boolean
     */

    private boolean hasLeftParent(Node<T> node) {
        if (Node.getLeft(node.getGrandparent()) == node.getParent()) {
            return true;
        }
        return false;
    }
    
    /**
     * functie care verifica daca un nod este copilul drept
     * @param node
     * @return - boolean
     */

    private boolean isRightChild(Node<T> node) {
        if (Node.getRight(node.getParent()) == node) {
            return true;
        }
        return false;
    }
    
    /**
     * functie care verifica daca un nod este copilul stang
     * @param node
     * @return - boolean
     */

    private boolean isLeftChild(Node<T> node) {
        if (Node.getLeft(node.getParent()) == node) {
            return true;
        }
        return false;
    }
}