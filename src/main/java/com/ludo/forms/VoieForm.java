package com.ludo.forms;

public class VoieForm {

	private String nomVoie ;
	private String cotation ;
	private int nombrePointVoie ;
	private Double longueurMin ;
	private Double longueurMax ;
	private String equipee ;
	public VoieForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VoieForm(String nomVoie, String cotation, int nombrePointVoie, Double longueurMin, Double longueurMax,
			String equipee) {
		super();
		this.nomVoie = nomVoie;
		this.cotation = cotation;
		this.nombrePointVoie = nombrePointVoie;
		this.longueurMin = longueurMin;
		this.longueurMax = longueurMax;
		this.equipee = equipee;
	}
	public String getNomVoie() {
		return nomVoie;
	}
	public void setNomVoie(String nomVoie) {
		this.nomVoie = nomVoie;
	}
	public String getCotation() {
		return cotation;
	}
	public void setCotation(String cotation) {
		this.cotation = cotation;
	}
	public int getNombrePointVoie() {
		return nombrePointVoie;
	}
	public void setNombrePointVoie(int nombrePointVoie) {
		this.nombrePointVoie = nombrePointVoie;
	}
	public Double getLongueurMin() {
		return longueurMin;
	}
	public void setLongueurMin(Double longueurMin) {
		this.longueurMin = longueurMin;
	}
	public Double getLongueurMax() {
		return longueurMax;
	}
	public void setLongueurMax(Double longueurMax) {
		this.longueurMax = longueurMax;
	}
	public String getEquipee() {
		return equipee;
	}
	public void setEquipee(String equipee) {
		this.equipee = equipee;
	}
	
	
	
}