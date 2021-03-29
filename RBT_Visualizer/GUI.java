package proiectRBT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * GUI.java - extinde JPanel
 * @author Sandor Denis
 * @version 1.0
 * @since 01.01.2021
 *
 */

public class GUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
	private TreePanel treePanel = new TreePanel(tree);

	/**
	 * constructor explicit al clasei
	 * @param text - de tipul JTextField, reprezinta valoarea cu care doreste sa se faca o operatie in arbore
	 * @param buttonInsert - de tipul JButton, cu listener pentru inserarea unei valori in arbore
	 * @param buttonDelete - de tipul JButton, cu listener pentru stergerea unei valori din arbore
	 * @param buttonReset - de tipul JButton, cu listener pentru a sterge toate valorile din arbore
	 * @param buttonCautare - de tipul JButton, cu listener pentru a cauta o valoare din arbore
	 * @param buttonInordine - de tipul JButton, cu listener pentru a parcurge in inordine nodurile introduse in arbore
	 * @param buttonPreordine - de tipul JButton, cu listener pentru a parcurge in preordine nodurile introduse in arbore
	 * @param buttonPostordine - de tipul JButton, cu listener pentru a parcurge in postordine nodurile introduse in arbore
	 * @param panel - panou ccu butoane si JTextField
	 * @param pane - panou pentru vizualizarea arborelui
	 */
	public GUI() {
		super.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(treePanel);
		pane.setPreferredSize(new Dimension(850, 700));
		add(pane, BorderLayout.CENTER);
		
		JTextField text = new JTextField(15);
		JButton buttonInsert = new JButton("Inserare");
		JButton buttonDelete = new JButton("Stergere");
		JButton buttonReset = new JButton("Reset");
		JButton buttonCautare = new JButton("Cautare");
        JButton buttonInordine = new JButton("Inordine");
        JButton buttonPreordine = new JButton("Preordine");
        JButton buttonPostordine = new JButton("Postordine");

		JPanel panel = new JPanel();
		panel.add(text);
		panel.add(buttonCautare);
		panel.add(buttonInsert);
		panel.add(buttonDelete);
        panel.add(buttonInordine);
        panel.add(buttonPreordine);
        panel.add(buttonPostordine);
		panel.add(buttonReset);
		
		add(panel, BorderLayout.SOUTH);

		buttonInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (text.getText().equals("") || text.getText().chars().allMatch(Character::isLetter)) {
					JOptionPane.showMessageDialog(null, "Inserati doar numere!");
					return;
				}
				Integer val = Integer.parseInt(text.getText());
				if (tree.contains(val)) {
					JOptionPane.showMessageDialog(null, "Elementul a fost deja inserat!");
				} else {
					tree.insert(val);
					treePanel.repaint();
				}
			}
		});

		buttonDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (text.getText().equals(""))
					return;

				Integer val = Integer.parseInt(text.getText());
				if (!tree.contains(val)) {
					JOptionPane.showMessageDialog(null,
							"Elementul nu exista in arbore!");
				} else {
					tree.remove(val);
					treePanel.repaint();
				}
			}
		});

		buttonReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				tree.clear();
				treePanel.searchComparable(null);
				treePanel.repaint();
			}
		});

		buttonCautare.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {

				if (text.getText().equals(""))
					return;

				Integer search = Integer.parseInt(text.getText());
				if (!tree.contains(search)) {
					JOptionPane.showMessageDialog(null, "Elementul nu exista in arbore!");
				} else {
					treePanel.searchComparable(search);
					treePanel.repaint();
				}
			}
		});
		
		buttonInordine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	if (tree.getRoot() == null) {
            		JOptionPane.showMessageDialog(null, "Arborele este gol!");
                  }
                  else {
                	  tree.inorder();
                  }
            	treePanel.repaint();
            }
        });
        
        buttonPreordine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	if (tree.getRoot() == null) {
            		JOptionPane.showMessageDialog(null, "Arborele este gol!");
                  }
                  else {
                	  tree.preorder();
                  }
            	treePanel.repaint();
            }
        });
        
        buttonPostordine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	if (tree.getRoot() == null) {
            		JOptionPane.showMessageDialog(null, "Arborele este gol!");
                  }
                  else {
                	  tree.postorder();
                  }
            	treePanel.repaint();
            }
        });
	}
}