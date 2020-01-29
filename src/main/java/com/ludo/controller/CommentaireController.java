package com.ludo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.ludo.dao.CommentaireRepository;
import com.ludo.dao.SpotRepository;
import com.ludo.entities.Commentaire;
import com.ludo.entities.Spot;
import com.ludo.entities.Utilisateur;
import com.ludo.forms.CommentaireForm;
import com.ludo.metier.CommentaireService;

@Controller
public class CommentaireController {
	
	@Autowired
	SpotRepository spotRepository ;
	@Autowired
	CommentaireRepository commentaireRepository ;
	@Autowired
	CommentaireService commentaireService ;
	
	/////////////////////////DISPLAY COMMENTAIRE\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	//	La gestion de l'affichage des commentaire se passe dans SpotController
	//	Dans le controller qui permet d'afficher les détails du spot
	
	/////////////////////////AJOUT COMMENTAIRE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * Controller qui renvoie vers le formulaire d'ajout de commentaire
	 */
	@GetMapping("/ajoutCommentaire/{spotId}")
	public String ajoutCommentaire(Model model,
			@PathVariable("spotId")Long spotId) {
			Spot spot = spotRepository.findById(spotId).get();
			
			model.addAttribute("spotId", spot.getIdSiteEscalade());
			
			model.addAttribute("commentaire", new Commentaire());
			
		return "formCommentaire" ;
	}
	
	/*
	 * Controller pour l'action du bouton sauvegarder dans formulaire d'ajout de commentaire.
	 */
	@PostMapping("/saveCommentaire/{spotId}")
	public String saveCommentaire(
			@ModelAttribute("commentaireForm")CommentaireForm commentaireForm,
			@PathVariable("spotId")Long spotId,
			@Valid Commentaire commentaire,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "formCommentaire";			
		} else {
		
		commentaireService.saveCommentaire(commentaireForm, spotId);
		
		return "redirect:/spot/" +spotId ;
		}
	}
	
	/////////////////////////EDITION COMMENTAIRE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * Controller pour accéder à l'édition d'un commentaire
	 */
	@GetMapping("/spot/{spotId}/editCommentaire/{comId}")
	public String editCommentaire(
			Model model,
			@PathVariable("comId")Long comId,
			@PathVariable("spotId")Long spotId,
			HttpServletRequest request) {
		
		if (request.getRemoteUser() == null) {
			return "formConnexion";
		} else {

			UserDetails utilDet = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (utilDet.getAuthorities().toString().contains("ADMINISTRATOR")) {

				Commentaire commentaire = commentaireRepository.findById(comId).get();

				model.addAttribute("commentaire", commentaire);
				model.addAttribute("spotId", spotId);

				return "editCommentaire";
			} else
				return "redirect:/spot/" + spotId;
		}
	}
	
	/*
	 * Controller pour l'action du bouton sauvegarder du formulaire d'édition d'un commentaire
	 * Il renvoie sur le secteur du commentaire qui vient d'être édité
	 */
	@PostMapping("/spot/{spotId}/saveEditCommentaire/{comId}")
	public String saveEditCommentaire(
			@ModelAttribute("commentaireForm")CommentaireForm commentaireForm,
			@PathVariable("comId")Long comId,
			@PathVariable("spotId")Long spotId) {
		
		commentaireService.saveEditCommentaire(commentaireForm, comId);
		
		return "redirect:/spot/" +spotId ;
	}	
	
	/////////////////////////SUPPRESSION COMMENTAIRE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/*
	 * Cette méthode permet la suppression d'un commentaire. Elle execute une
	 * vérification de rôle. Seul le rôle ADMINISTRATOR peut supprimer un commentaire
	 * Le lien ne s'affiche que pour les ADMIN côté front, mais permet de protéger
	 * contre un anonyme qui taperait le PATH à la main dans son navigateur
	 */
	@GetMapping("/spot/{spotId}/deleteCommentaire/{comId}")
	public String deleteCommentaire(
			@PathVariable("comId")Long comId,
			@PathVariable("spotId")Long spotId,
			HttpServletRequest request){
		
		if (request.getRemoteUser() == null) {
			return "formConnexion";
		} else {

			UserDetails utilDet = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			
			if (utilDet.getAuthorities().toString().contains("ADMINISTRATOR")) {
				commentaireRepository.deleteById(comId);
				return "redirect:/spot/" +spotId ;
			}
			return "redirect:/spot/" +spotId ;
		}
	}
}
