package com.start2sparkle.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.start2sparkle.dao.ProduitRepository;
import com.start2sparkle.entities.Produit;


@Controller			// -> Un controller est une classe qui utilise l'annotation Controller
					//    et à besoin de Produitrepository.
public class ProduitController {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	//@RequestMapping(value="/index", method=RequestMethod.GET)
	@GetMapping("/index")
	public String index(Model model,
			@RequestParam(name="page", defaultValue ="0") int page, 		// -> par default la valeur est 0.
			@RequestParam(name="motCle", defaultValue ="") String mc){     // -> par default la valeur est une chaine vide.
		
		// On recupere une list ou des pages de produits
		
		//List<Produit> produits = produitRepository.findAll();
		// avoir une page > Page<Produit> pageProduits = produitRepository.findAll(PageRequest.of(page, 5));
		// Pour une recherche find by :
		Page<Produit> pageProduits = produitRepository.findByDesignationContains(mc, PageRequest.of(page, 5));
		
		// On enregistre les produits trouvés dans le model. 
		model.addAttribute("listProduits",pageProduits.getContent());			// Une page est un objet et on utilise getContent pour avoir la liste.
		model.addAttribute("pages", new int[pageProduits.getTotalPages()]);   // La dimension du tableau c'est le nombre de pages.
		
		// On memorise la page courante pour la mettre en surbrillance en appliquant une classe css context.
		// <li th:class="${currentPage==status.index}" -> On applique une classe css si une condition est vérifiée.
		// Si c'est le numéro de la page active on applique la classe active.
		model.addAttribute("currentPage", page);
		
		model.addAttribute("motCle", mc);
		
		return "produits";			// retourne une vue qui s'appelle produit.
									// dans le html on fait une boulbe sur le tableau pour afficher les différentes pages.
	}
	
	@GetMapping("/delete")
	public String delete(Long id, int page, String motCle) {
		produitRepository.deleteById(id);
		return "redirect:/index?page=" + page + "&motCle=" + motCle;
	}

}
