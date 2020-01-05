package com.start2sparkle.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/* Une entité JPA est un java bean. Un java bean est une classe serialisable.
 * Dans laquelle on déclare les attributs avec des getters / setters.
 * 
 * Pour spécifier que c'est une entité JPA, on ajoute l'annotation @Entity. 
 * Cela signifie que la classe Produit corresponds à une table Produit.
 * L'annotation @Id sert à préciser que c'est l'identifiant.
 * L'annotation @GeneratedValue sert à ce que l'identifiant soit généré automatiquement.
 */


@Entity
public class Produit implements Serializable{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String designation;
	private double prix;
	private int quantite;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Produit(Long id, String designation, double prix, int quantite) {
		super();
		this.id = id;
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
	}
}
