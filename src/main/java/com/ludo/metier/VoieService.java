package com.ludo.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.ludo.dao.SecteurRepository;
import com.ludo.dao.VoieRepository;
import com.ludo.entities.Secteur;
import com.ludo.entities.Voie;
import com.ludo.forms.VoieForm;

@Service
public class VoieService {

	@Autowired
	private SecteurRepository secteurRepository ;
	@Autowired
	private VoieRepository voieRepository ;
	
	public void saveVoie(Long secteurId, VoieForm voieForm, BindingResult result) {
		
		Voie newVoie = new Voie();
		
		newVoie.setNomVoie(voieForm.getNomVoie());
		newVoie.setCotation(voieForm.getCotation());
		newVoie.setNombrePointVoie(voieForm.getNombrePointVoie());
		newVoie.setLongeurMin(voieForm.getLongueurMin());
		newVoie.setLongueurMax(voieForm.getLongueurMax());
		newVoie.setEquipee(voieForm.getEquipee());
		
		Secteur secteurVoie = secteurRepository.findById(secteurId).get();
		newVoie.setSecteur(secteurVoie);
		voieRepository.save(newVoie);
	}

}
