package tema2;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Clasa GUI extinde clasa JFrame
 * @author Denis
 * @version 1.0
 * @since 29.11.2020
 */

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel labelDispozitiv;
	JLabel labelTipDispozitiv;
	JLabel labelConsum;
	JLabel labelLoc;
	JLabel labelPerioada;
	JLabel labelZiLuna;
	JLabel labelCategorie;
	
	JTextField textDispozitiv;
	JTextField textTipDispozitiv;
	
	JSpinner spinnerConsum;
	JSpinner spinnerLoc;
	JSpinner spinnerPerioada;
	JSpinner spinnerZiLuna;
	JSpinner spinnerCategorie;
	
	JButton buttonZiLuna;
	JButton buttonGenerareRaportZi;
	JButton buttonAdd;
	JButton buttonNext;
	JButton buttonPrev;
	JButton buttonDelete;
	JButton buttonUpdate;
	JButton buttonGenerareRaportLuna;
	JButton buttonGenerareRaportAn;
	JButton buttonConsumCategorie;
	JButton buttonVizualizare;
	
	JFrame frame;
	
	List<Dispozitiv> listaDispozitive;
	int poz;
	static double pretKW;
	
	/**
	 * Constructor pentru clasa GUI
	 * @param f - fisier pentru serializarea/deserializarea obiectelor de tipul Dispozititv
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 * @param listaDispozitive - lista pentru obiectele de tip Dispozitiv
	 * @param poz - variabila pentru navigarea prin lista
	 * @param pretKW - variabila statica pentru pretul unui KW
	 * @param listaLoc - lista pentru locurile de consum
	 * @param listaZiLuna - lista pentru selectarea tipului de perioada 
	   (daca utilizatorul se refera la o perioada de timp din zi sau din luna)
	 * @param listaCategorie - lista de categorii ale dispozitivelor
	 * @param labelDispozitiv - obiect de tipul JLabel
	 * @param labelTipDispozitiv - obiect de tipul JLabel
	 * @param labelConsum - obiect de tipul JLabel
	 * @param labelLoc - obiect de tipul JLabel
	 * @param labelPerioada - obiect de tipul JLabel
	 * @param labeZiLuna - obiect de tipul JLabel
	 * @param labelCategorie - obiect de tipul JLabel
	 * @param textDispozitiv - obiect de tipul JTextField, pentru introducerea unui dispozitiv
	 * @param textTipDispozitiv - obiect de tipul JTextField, folosit pentru tipul dispozitivului
	 * @param spinnerCategorie - obiect de tipul JSpinner, folosit pentru selectarea categoriei obiectelor
	 * @param spinnerConsum - obiect de tipul JSpinner, folosit pentru selectarea consumului de curent
	 * @param spinnerLoc - obiect de tipul JSpinner, folosit pentru selectarea locului de consum
	 * @param spinnerZiLuna - obiect de tipul JSpinner, folosit pentru a selecta daca perioada se refera la zi sau la luna
	 * @param spinnerPerioada - obiect de tipul JSpinner, folosit pentru selectarea perioadei de consum,
	   are ca restrictii, pentru zi 24 (numarul maxim de ore dintr-o zi), iar pentru luna 744 (numarul maxim de ore dintr-o luna)
	 * @param buttonZiLuna - obiect de tipul JButton, folosit pentru a seta daca perioada se refera la zi/luna
	 * @param buttonAdd - obiect de tipul JButton, folosit pentru adaugarea dispozitiveor in lista de dispozitive
	 * @param buttonDelete - obiect de tipul JButton, folosit pentru stergerea unui dispozitiv din lista de dispozitive
	 * @param buttonNext - obiect de tipul JButton, folosit pentru afisarea urmatorului dispozitiv din lista de dispozitive
	 * @param buttonPrev - obiect de tipul JButton, folosit pentru afisarea dispozitivului precedent din lista de dispozititive
	 * @param buttonUpdate - obiect de tipul JButton, folosit pentru a actualiza datele unui dispozitiv din lista de dispozitive
	 * @param buttonGenerareRaportZi - obiect de tipul JButton, folosit pentru generarea raportului de consum electric in ultimele 24 de ore
	 * @param buttonGenerareRaportLuna - obiect de tipul JButton, folosit pentru generarea raportului de consum electric in ultima luna
	 * @param buttonGenerareRaportAn - obiect de tipul JButton, folosit pentru generarea raportului de consum electric in ultimul an
	 * @param buttonConsumCategorie - obiect de tipul JButton, folosit pentru vizualizarea consumui pe categorie, 
	   vizualizarea categoriei cu consumul cel mai ridicat si vizualizarea costului lunar/ bilunar
	 * @param buttonVizualizare - obiect de tipul JButton, folosit pentru vizualizarea tuturor elementelor de tipul dispozitiv
	 * @param top - obiect de tipul JPanel, folosit pentru incadrarea labelDispozitiv, textDispozitiv, labelTipDispozitiv, textTipDispozitiv
	 * @param mid - obiect de tipul JPanel, folosit pentru incadrarea labelConsum, labelLoc, spinnerLoc, labelPerioada, spinnerPerioada, labelZiLuna,
	   spinnerZiLuna, labelCategorie, spinnerCategorie
	 * @param bot - obiect de tipul JPanel, folosit pentru incadrarea buttonZiLuna, buttonAdd, buttonNext, buttonPrev, buttonDelete, buttonUpdate,
	   buttonGenerareRaportZi, buttonGenerareRaportLuna, buttonGenerareRaportAn, buttonConsumCategorie, buttonVizualizare
	   @param frame, de tipul JFrame, folosita pentru afisarea unor mesaje cu scop ajutator pentru utilizator
	 */
	
	public GUI(File f) throws IOException, ClassNotFoundException {
		
		super("Estimarea consumului de energie electrica");
		
		listaDispozitive = new ArrayList<>();
		poz = -1;
		pretKW = 1.8;
		
		if(!f.exists())
			f.createNewFile();
		
		boolean empty = f.exists() && f.length() == 0;
		if(empty == false)
			deserializare(f);
		
		List<String> listaLoc = new ArrayList<String>();
		listaLoc.add("Alegeti");
		listaLoc.add("Bucatarie");
		listaLoc.add("Baie");
		listaLoc.add("Dormitor");
		listaLoc.add("Hol");
		listaLoc.add("Living");
		
		List<String> listaZiLuna = new ArrayList<String>();
		listaZiLuna.add("Alegeti");
		listaZiLuna.add("Zi");
		listaZiLuna.add("Luna");
		
		List<String> listaCategorie = new ArrayList<String>();
		listaCategorie.add("Alegeti");
		listaCategorie.add("Electrocasnice mici");
		listaCategorie.add("Electrocasnice mari");
		listaCategorie.add("Alte dispozitive");
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setSize(770, 280);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		labelDispozitiv = new JLabel("Dispozitiv: ");
		labelDispozitiv.setPreferredSize(new Dimension(120, 30));
		labelDispozitiv.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTipDispozitiv = new JLabel("Tip dispozitiv: ");
		labelTipDispozitiv.setPreferredSize(new Dimension(120, 30));
		labelTipDispozitiv.setHorizontalAlignment(SwingConstants.RIGHT);
		labelConsum = new JLabel("Consum estimat: ");
		labelConsum.setPreferredSize(new Dimension(120, 30));
		labelConsum.setHorizontalAlignment(SwingConstants.RIGHT);
		labelLoc = new JLabel("Loc consum: ");
		labelLoc.setPreferredSize(new Dimension(120, 30));
		labelLoc.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPerioada = new JLabel("Perioada: ");
		labelPerioada.setPreferredSize(new Dimension(130, 30));
		labelPerioada.setHorizontalAlignment(SwingConstants.RIGHT);
		labelZiLuna = new JLabel("Zi/Luna: ");
		labelZiLuna.setPreferredSize(new Dimension(120, 30));
		labelZiLuna.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCategorie = new JLabel("Categorie dispozitiv: ");
		labelCategorie.setPreferredSize(new Dimension(347, 30));
		labelCategorie.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textDispozitiv = new JTextField(50);
		textTipDispozitiv = new JTextField(50);
		
		spinnerCategorie = new JSpinner(new SpinnerListModel(listaCategorie));
		spinnerCategorie.setPreferredSize(new Dimension(140, 20));
		spinnerConsum = new JSpinner(new SpinnerNumberModel(0, 0, 500000, 0.25));
		spinnerConsum.setPreferredSize(new Dimension(55, 20));
		spinnerLoc = new JSpinner(new SpinnerListModel(listaLoc));
		spinnerLoc.setPreferredSize(new Dimension(100, 20));
		spinnerZiLuna = new JSpinner(new SpinnerListModel(listaZiLuna));
		spinnerZiLuna.setPreferredSize(new Dimension(70, 20));
		spinnerPerioada = new JSpinner(new SpinnerNumberModel(0, 0, 0, 0));
		spinnerPerioada.setPreferredSize(new Dimension(70, 20));
		
		buttonZiLuna = new JButton("Set Zi/Luna");
		buttonGenerareRaportZi = new JButton("Raport Zi");
		buttonAdd = new JButton("Adaugare");
		buttonNext = new JButton("Urmator");
		buttonPrev = new JButton("Precedent");
		buttonDelete = new JButton("Delete");
		buttonUpdate = new JButton("Actualizare");
		buttonGenerareRaportLuna = new JButton("Raport luna");
		buttonGenerareRaportAn = new JButton("Raport an");
		buttonConsumCategorie = new JButton("Vizualizare consum categorie");
		buttonVizualizare = new JButton("Vizualizare dispozitive");
		
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		top.setPreferredSize(new Dimension(700, 70));
		
		top.add(labelDispozitiv);
		top.add(textDispozitiv);
		top.add(labelTipDispozitiv);
		top.add(textTipDispozitiv);
		
		this.add(top);
		
		JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mid.setPreferredSize(new Dimension(700, 70));
		
		mid.add(labelConsum);
		mid.add(spinnerConsum);
		mid.add(labelLoc);
		mid.add(spinnerLoc);
		mid.add(labelPerioada);
		mid.add(spinnerPerioada);
		mid.add(labelZiLuna);
		mid.add(spinnerZiLuna);
		mid.add(labelCategorie);
		mid.add(spinnerCategorie);
		
		this.add(mid);
		
		JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bot.setPreferredSize(new Dimension(700, 70));
		
		bot.add(buttonZiLuna);
		bot.add(buttonAdd);
		bot.add(buttonNext);
		bot.add(buttonPrev);
		bot.add(buttonDelete);
		bot.add(buttonUpdate);
		bot.add(buttonGenerareRaportZi);
		bot.add(buttonGenerareRaportLuna);
		bot.add(buttonGenerareRaportAn);
		bot.add(buttonConsumCategorie);
		bot.add(buttonVizualizare);
		
		this.add(bot);
		
		buttonZiLuna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(spinnerZiLuna.getValue().equals("Alegeti"))
					spinnerPerioada.setModel(new SpinnerNumberModel(0, 0, 0, 0));
				else if(spinnerZiLuna.getValue().equals("Zi"))
					spinnerPerioada.setModel(new SpinnerNumberModel(0.5, 0, 24, 0.5));	
				else
					spinnerPerioada.setModel(new SpinnerNumberModel(0.5, 0, 744, 0.5));
			}
		});
		
		poz = 0;
		buttonAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textDispozitiv.getText().isBlank())
					JOptionPane.showMessageDialog(frame, "Campul 'Dispozitiv' nu poate fi gol.");
				else if (textTipDispozitiv.getText().isBlank())
					JOptionPane.showMessageDialog(frame, "Campul 'Tip Dispozitiv' nu poate fi gol.");
				else  if(spinnerLoc.getValue().equals("Alegeti"))
					JOptionPane.showMessageDialog(frame, "Alegeti o optiune pentru locul de consum.");
				else if(spinnerCategorie.getValue().equals("Alegeti"))
					JOptionPane.showMessageDialog(frame, "Alegeti o optiune pentru categorie.");
				else if(spinnerZiLuna.getValue().equals("Alegeti"))
					JOptionPane.showMessageDialog(frame, "Selectati daca perioada la care faceti referire este aferenta unei zi/luni.");
				else {
				listaDispozitive.add(new Dispozitiv(textDispozitiv.getText(), textTipDispozitiv.getText(),
						(Double) spinnerConsum.getValue(), (String) spinnerLoc.getValue(),
						(Double) spinnerPerioada.getValue(), (String) spinnerZiLuna.getValue(), (String) spinnerCategorie.getValue()));
				textDispozitiv.setText(null);
				textTipDispozitiv.setText(null);
				spinnerConsum.setValue(0);
				spinnerPerioada.setValue(0.5);
				}
			}
		});
		
		poz = 0;
		buttonNext.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					if(poz == listaDispozitive.size() - 1)
						poz = -1;
					poz += 1;
					textDispozitiv.setText(listaDispozitive.get(poz).getDispozitiv());
					textTipDispozitiv.setText(listaDispozitive.get(poz).getTipDispozitiv());
					spinnerConsum.setValue(listaDispozitive.get(poz).getConsum());
					spinnerLoc.setValue(listaDispozitive.get(poz).getLoc());
					spinnerPerioada.setValue(listaDispozitive.get(poz).getPerioada());
					spinnerZiLuna.setValue(listaDispozitive.get(poz).getZiLuna());
					spinnerCategorie.setValue(listaDispozitive.get(poz).getCategorie());
				}
			}
		});
		
		poz = 0;
		buttonPrev.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					if(poz == 0)
						poz = listaDispozitive.size();
					poz -= 1;
					textDispozitiv.setText(listaDispozitive.get(poz).getDispozitiv());
					textTipDispozitiv.setText(listaDispozitive.get(poz).getTipDispozitiv());
					spinnerConsum.setValue(listaDispozitive.get(poz).getConsum());
					spinnerLoc.setValue(listaDispozitive.get(poz).getLoc());
					spinnerPerioada.setValue(listaDispozitive.get(poz).getPerioada());
					spinnerZiLuna.setValue(listaDispozitive.get(poz).getZiLuna());
					spinnerCategorie.setValue(listaDispozitive.get(poz).getCategorie());
				}
			}
		});
		
		buttonUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					if(poz != -1) {
						listaDispozitive.get(poz).setDispozitiv(textDispozitiv.getText());
						listaDispozitive.get(poz).setTipDispozitiv(textTipDispozitiv.getText());
						listaDispozitive.get(poz).setConsum((Double) spinnerConsum.getValue());
						listaDispozitive.get(poz).setLoc((String) spinnerLoc.getValue());
						listaDispozitive.get(poz).setPerioada((Double) spinnerPerioada.getValue());
						listaDispozitive.get(poz).setZiLuna((String) spinnerZiLuna.getValue());
						listaDispozitive.get(poz).setCategorie((String) spinnerCategorie.getValue());
					}
					else
						JOptionPane.showMessageDialog(frame, "Apasati pe butonul 'Urmator' sau 'Precedent' pentru a selecta un dispozitiv pe care doriti sa il actualizati.");
				}				
			}
		});
		
		buttonDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					listaDispozitive.remove(poz);
					poz--;
					textDispozitiv.setText(null);
					textTipDispozitiv.setText(null);
					spinnerConsum.setValue(0);
					spinnerLoc.setValue("Alegeti");
					spinnerPerioada.setValue(0);
					spinnerZiLuna.setValue("Alegeti");
					spinnerCategorie.setValue("Alegeti");
				}
			}
		});
		
		buttonGenerareRaportZi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Double suma = 0.0;
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					for(Dispozitiv el: listaDispozitive) {
						if(el.getZiLuna().equals("Luna"))
							suma += el.getConsum()/30;
						suma += el.getConsum();
					}
					JOptionPane.showMessageDialog(frame, "In ultima zi s-au consumat " + suma + " kW");
				}
			}
			
		});
		
		buttonGenerareRaportLuna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Double suma = 0.0;
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					for(Dispozitiv el: listaDispozitive) {
						if(el.getZiLuna().equals("Zi"))
							suma += el.getConsum()*30;
						suma += el.getConsum();
					}
					JOptionPane.showMessageDialog(frame, "In ultima luna s-au consumat " + suma + " kW");
				}
			}
			
		});
		
		buttonGenerareRaportAn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Double suma = 0.0;
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
					for(Dispozitiv el: listaDispozitive) {
						if(el.getZiLuna().equals("Luna"))
							suma += el.getConsum()*12;
						if(el.getZiLuna().equals("Zi"))
							suma += el.getConsum()*365;
						suma += el.getConsum();
					}
					JOptionPane.showMessageDialog(frame, "In ultimul an s-au consumat " + suma + " kW");
				}
			}
			
		});
		
		buttonConsumCategorie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				double sumaElectrocasniceMici = 0.0;
				double sumaElectrocasniceMari = 0.0;
				double sumaAlteDispozitive = 0.0;
				double sumaBucatarie = 0.0;
				double sumaDormitor = 0.0;
				double sumaBaie = 0.0;
				double sumaHol = 0.0;
				double sumaLiving = 0.0;
				String str = "Mai mult curent consuma electrocasnicele mici.";
				if(listaDispozitive.isEmpty())
					JOptionPane.showMessageDialog(frame, "Lista este goala.");
				else {
				for(Dispozitiv el: listaDispozitive) {
					if(el.getCategorie().equals("Electrocasnice mici"))
						sumaElectrocasniceMici += el.getConsum();
					if(el.getCategorie().equals("Electrocasnice mari"))
						sumaElectrocasniceMari += el.getConsum();
					if(el.getCategorie().equals("Alte dispozitive"))
						sumaAlteDispozitive += el.getConsum();
					if(el.getLoc().equals("Bucatarie") && el.getZiLuna().equals("Zi"))
						sumaBucatarie += el.getConsum()*30*pretKW;
					if(el.getLoc().equals("Bucatarie") && el.getZiLuna().equals("Luna"))
						sumaBucatarie += el.getConsum()*pretKW;
					if(el.getLoc().equals("Hol") && el.getZiLuna().equals("Zi"))
						sumaHol += el.getConsum()*30*pretKW;
					if(el.getLoc().equals("Hol") && el.getZiLuna().equals("Luna"))
						sumaHol += el.getConsum()*pretKW;
					if(el.getLoc().equals("Dormitor") && el.getZiLuna().equals("Zi"))
						sumaDormitor += el.getConsum()*30*pretKW;
					if(el.getLoc().equals("Dormitor") && el.getZiLuna().equals("Luna"))
						sumaDormitor += el.getConsum()*pretKW;
					if(el.getLoc().equals("Baie") && el.getZiLuna().equals("Zi"))
						sumaBaie += el.getConsum()*30*pretKW;
					if(el.getLoc().equals("Baie") && el.getZiLuna().equals("Luna"))
						sumaBaie += el.getConsum()*pretKW;
					if(el.getLoc().equals("Living") && el.getZiLuna().equals("Zi"))
						sumaLiving += el.getConsum()*30*pretKW;
					if(el.getLoc().equals("Living") && el.getZiLuna().equals("Luna"))
						sumaLiving += el.getConsum()*pretKW;
				}
				if(sumaElectrocasniceMari > sumaElectrocasniceMici) {
					str = "Mai mult curent consuma electrocasnicele mari.";
					if(sumaAlteDispozitive > sumaElectrocasniceMari)
						str = "Mai mult curent consuma alte dispozitive";
				}
				JOptionPane.showMessageDialog(frame, "Electrocasnicele mici au consumat " + sumaElectrocasniceMici 
						+ " ,electrocasnicele mari au consumat " + sumaElectrocasniceMari + ", iar alte dispozitive au consumat " + sumaAlteDispozitive + ". " + str + "\n"
						+ "Costul per KW este de 1.8 unitati monetare. " + "\n"
						+ "Costul LUNAR pentru aparatele din bucatarie este " + sumaBucatarie + "\n" + ", din living este " 
						+ sumaLiving + "\n" + " , din dormitor " + sumaDormitor + "\n" + " , din baie " + sumaBaie + "\n" + " , iar din hol " + sumaHol
						+ "\n" + " Costul BILUNAR pentru aparatele din bucatarie este " + sumaBucatarie/2 + "\n" + ", din living este " 
						+ sumaLiving/2 + "\n" + " , din dormitor " + sumaDormitor/2 + "\n" + " , din baie " + sumaBaie/2 + "\n" + " , iar din hol " + sumaHol/2);
				}
			}
			
		});
		
		buttonVizualizare.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String str = "";
				for (Dispozitiv el: listaDispozitive) {
					str += el;
					str += "\n";
				}
				JOptionPane.showMessageDialog(frame, str);
			}
		});
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				try {
					serializare(f);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 
	 * @param f - fisier folosit pentru serializare
	 * @throws IOException
	 */
	
	void serializare(File f) throws IOException {
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(listaDispozitive);
		oos.close();
		fos.close();
	}
	
	/**
	 * 
	 * @param f - fisier folosit pentru deserializare
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	void deserializare(File f) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		List<?> l = (List<?>) ois.readObject();
		
		for(Object o: l)
			listaDispozitive.add((Dispozitiv) o);
		
		ois.close();
		fis.close();
	}	
}
