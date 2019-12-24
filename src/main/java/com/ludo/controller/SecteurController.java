package com.ludo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ludo.dao.SecteurRepository;
import com.ludo.dao.SpotRepository;
import com.ludo.dao.VoieRepository;
import com.ludo.entities.Secteur;
import com.ludo.entities.Spot;
import com.ludo.entities.Utilisateur;
import com.ludo.entities.Voie;
import com.ludo.forms.SecteurForm;
import com.ludo.metier.SecteurService;

@Controller
public class SecteurController {
	
	@Autowired
	private SpotRepository spotRepository ;
	@Autowired
	private SecteurRepository secteurRepository ;
	@Autowired
	private VoieRepository voieRepository ;
	@Autowired
	private SecteurService secteurService ;
	
	/*
	 * Controller qui permet d'afficher les détails du spot et du secteur
	 * Affiche une liste de voie lié au secteur choisi
	 */
	@GetMapping("/spot/{spotId}/secteur/{secteurId}")
	public String afficherSecteur(
			Model model, 
			@PathVariable("spotId")Long spotId, 
			@PathVariable("secteurId")Long secteurId) {
		
		Spot spot = spotRepository.findById(spotId).get();
		Secteur secteur = secteurRepository.findById(secteurId).get();
		
		model.addAttribute("spotInfo", spot);
		model.addAttribute("secteurInfo", secteur);
		
		List <Voie> listeVoie = voieRepository.findBySecteur(secteurId);
		model.addAttribute("listeVoie", listeVoie);
		return "secteur";
	}
	
	/*
	 * Controller pour accéder au formulaire d'ajout de secteur lié à un spot 
	 */
	@GetMapping("/spot/{spotId}/ajouterSecteur")
	public String formSecteur(
			Model model,
			@PathVariable("spotId") Long spotId) {
		
		Spot spot = spotRepository.findById(spotId).get();
		
		return "formSecteur";
	}
	
	/*
	 * Controller pour l'action du bouton sauvegarder pour un nouveau secteur
	 */
	@PostMapping("/spot/{spotId}/ajouterSecteur/save")
	public String saveSecteur(
			Model model,
			@ModelAttribute("secteurForm") SecteurForm secteurForm,
			@PathVariable("spotId") Long spotId,
			BindingResult result) {
		
		secteurService.saveSecteur(spotId, secteurForm, result);
		
		return "redirect:/spot/" + spotId ;
	}
	
	/*
	 * Cette méthode permet la suppression d'un secteur. Elle execute une
	 * vérification de rôle. Seul le rôle ADMINISTRATOR peut supprimer une longueur
	 * Le lien ne s'affiche que pour les ADMIN côté front, mais permet de protéger
	 * contre un anonyme qui taperait le PATH à la main dans son naviguateur
	 */
	@GetMapping("/spot/{spotId}/deleteSecteur/{secteurId}")
	public String deleteSecteur(
			@PathVariable("secteurId") Long secteurId, 
			@PathVariable("spotId") Long spotId,
			final RedirectAttributes redirect,
			HttpServletRequest request) {
		
		if (request.getRemoteUser() == null) {
			return "formConnexion" ;
		} else {
			UserDetails utilDet = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (utilDet.getAuthorities().toString().contains("ADMINISTRATOR")) {
				secteurRepository.deleteById(secteurId);
				return "/spot";
			} else {
				return "/spot";
			}
		}
	}
	
	/*
	 * Controller pour accéder à l'edition d'un secteur
	 */
	@GetMapping("/spot/{spotId}/editSecteur/{secteurId}")
	public String editSpot(
			Model model, 
			@PathVariable("spotId") Long spotId, 
			@PathVariable("secteurId")Long secteurId){
		return "editSecteur" ;
	}
	
	/*
	 * Controller pour l'action du bouton sauvegarder dans le formulaire d'étion d'un secteur
	 * Il renvoie vers le secteur qui vient d'être édité
	 */
	@PostMapping("/spot/{spotId}/saveEditSecteur/{secteurId}")
	public String saveEditSpot(
			Model model, 
			@PathVariable("spotId")Long spotId,
			@PathVariable("secteuId")Long secteurId) {
		return "redirect:/spot/"+ spotId +"/secteur/" +secteurId;
	}
}