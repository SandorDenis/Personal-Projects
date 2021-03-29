package tema2;

import java.io.Serializable;

/**
 * Dispozitiv.java - implementeaza interfata Serializable
 * @author Denis
 * @version 1.0
 * @since 29.11.2020
 */

public class Dispozitiv implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String dispozitiv;
	String tipDispozitiv;
	Double consum;
	String loc;
	Double perioada;
	String ziLuna;
	String categorie;
	
	/**
	 * 	constructor implicit pentru clasa Dispozitiv
	 */
	
	public Dispozitiv() {
		super();
	}
	
	/**
	 * Constructor explicit pentru clasa Dispozitiv.java
	 * @param dispozitiv - de tipul String
	 * @param tipDispozitiv - de tipul String
	 * @param consum - de tipul Double
	 * @param loc - de tipul String
	 * @param perioada - de tipul Double
	 * @param ziLuna - de tipul String
	 * @param categorie -  de tipul String
	 */

	public Dispozitiv(String dispozitiv, String tipDispozitiv, Double consum, String loc, Double perioada, String ziLuna,
			String categorie) {
		super();
		this.dispozitiv = dispozitiv;
		this.tipDispozitiv = tipDispozitiv;
		this.consum = consum;
		this.loc = loc;
		this.perioada = perioada;
		this.ziLuna = ziLuna;
		this.categorie = categorie;
	}
	
	/**
	 * suprascrierea metodei de afisare pentru clasa Dispozitiv.java
	 */

	@Override
	public String toString() {
		return "Dispozitiv [dispozitiv=" + dispozitiv + ", tipDispozitiv=" + tipDispozitiv + ", consum=" + consum
				+ ", loc=" + loc + ", perioada=" + perioada + ", ziLuna=" + ziLuna + ", categorie=" + categorie + "]";
	}
	
	/**
	 * getter pentru dispozitiv
	 * @return dispozitiv, de tipul String, returneaza numele dispozitivului
	 */

	public String getDispozitiv() {
		return dispozitiv;
	}
	/**
	 * getter pentru tipDispozitiv
	 * @return tipDispozitiv, de tipul String, returneaza tipul dispozitivului
	 */

	public String getTipDispozitiv() {
		return tipDispozitiv;
	}
	
	/**
	 * getter pentru consum
	 * @return consum, de tipul Double, returneaza consumul de curent
	 */

	public Double getConsum() {
		return consum;
	}
	
	/**
	 * getter pentru loc
	 * @return loc, de tipul String, returneaza locul de consum
	 */

	public String getLoc() {
		return loc;
	}
	
	/**
	 * getter pentru perioada
	 * @return perioada, de tipul Double, returneaza perioada de consum
	 */

	public Double getPerioada() {
		return perioada;
	}
	
	/**
	 * getter pentru ziLuna
	 * @return ziLuna, de tipul String, returneaza zi/luna
	 */

	public String getZiLuna() {
		return ziLuna;
	}
	
	/**
	 * getter pentru categorie
	 * @return categorie, de tipul String, returneaza categoria 
	 */

	public String getCategorie() {
		return categorie;
	}
	
	/**
	 * setter pentru dispozitiv
	 * @param dispozitiv
	 */

	public void setDispozitiv(String dispozitiv) {
		this.dispozitiv = dispozitiv;
	}
	
	/**
	 * setter pentru tipDispozitiv
	 * @param tipDispozitiv
	 */

	public void setTipDispozitiv(String tipDispozitiv) {
		this.tipDispozitiv = tipDispozitiv;
	}
	
	/**
	 * setter pentru consum
	 * @param consum
	 */

	public void setConsum(Double consum) {
		this.consum = consum;
	}
	
	/**
	 * setter pentru loc
	 * @param loc
	 */

	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	/**
	 * setter pemntru perioada
	 * @param perioada
	 */

	public void setPerioada(Double perioada) {
		this.perioada = perioada;
	}
	
	/**
	 * setter pentru ziLuna
	 * @param ziLuna
	 */

	public void setZiLuna(String ziLuna) {
		this.ziLuna = ziLuna;
	}
	
	/**
	 * setter pentru categorie
	 * @param categorie
	 */

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
}
