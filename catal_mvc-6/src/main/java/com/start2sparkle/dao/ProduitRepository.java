package com.start2sparkle.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.start2sparkle.entities.Produit;

/**
 * Pour utiliser JPa avec Spring Data, il faut une interface qui hérite de JpaRepository.
 * On spécifie deux paramètre : l'entité et le type de l'Id.
 */

public interface ProduitRepository extends JpaRepository<Produit, Long>{
	
	// On ajoute une méthode pour chercher un produit par mot clé mc (premier parametre).
	// Cette méthode retourne une page de produit (pas une liste parcequ'il y a une pagination.
	// Comme elle retourne une page il faut un deuxième paramètre : Pageable car il y a une pagination.
	public Page<Produit> findByDesignationContains(String mc, Pageable pageable);

}
