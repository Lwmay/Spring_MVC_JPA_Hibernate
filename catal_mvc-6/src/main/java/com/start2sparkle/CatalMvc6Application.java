package com.start2sparkle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.start2sparkle.dao.ProduitRepository;
import com.start2sparkle.entities.Produit;

/**
 * Le moyen le plus simple pour faire des tests est de faire un héritage de CommandLineRunner.
 * On doit redéfinir la méthode run. spring va automatiquement faire appel à la méthode run.
 * On fait le test à l'intérieur de la méthode run.
 */


@SpringBootApplication
public class CatalMvc6Application implements CommandLineRunner{
	
	@Autowired        // -> permet à Spring de faire l'injection des dépendances.
	private ProduitRepository produitRepository;

	public static void main(String[] args) {
		SpringApplication.run(CatalMvc6Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		produitRepository.save(new Produit(null, "Pc 5648", 6000, 12));
		produitRepository.save(new Produit(null, "Imprimante 1258", 300, 64));
		produitRepository.save(new Produit(null, "Pc 048", 450, 12));
		produitRepository.save(new Produit(null, "souris Corsair", 80, 120));
		produitRepository.findAll().forEach(p->{
			System.out.println(p.getDesignation());
		});
	}

}
