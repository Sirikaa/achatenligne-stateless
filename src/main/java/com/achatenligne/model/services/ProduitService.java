package com.achatenligne.model.services;

import java.util.List;
import java.util.Optional;

import com.achatenligne.dao.ProduitDAO;
import com.achatenligne.model.Commande;
import com.achatenligne.model.Produit;
import com.achatenligne.model.exceptions.InexistantException;
import com.achatenligne.model.exceptions.RequeteInvalideException;


public class ProduitService {
	
	private ProduitDAO produitDao = new ProduitDAO();
	
	//Sans BDD, génération des produits en dur
	/*public List<Produit> getAll() {
		List<Produit> all = new ArrayList<Produit>();
		all.add(new Produit(1, "XTV-12", "Vélo", 250.00));
		all.add(new Produit(2, "CTK-55", "Bouillotte", 12.40));
		all.add(new Produit(3, "XGK-80", "Souris PC", 3.23));
		return all;
	}*/
	
	public List<Produit> getAll() {
		return produitDao.getAll();
	}
	
	public Optional<Produit> getOne(int id) throws InexistantException{
		Optional<Produit> p = produitDao.getProduit(id);
		//On s'assure que le produit existe, n'est pas vide
		if(p.isPresent()) {
			return p;
		}else {
			throw new InexistantException();
		}
	}
	
	public Commande creerCommande(int...idProduits) {
		Commande commande = new Commande();
		for (int idProduit : idProduits) {
			ajouter(commande, idProduit);
		}
		return commande;
	}

	private void ajouter(Commande commande, int idProduit) {
		//Utilisation de Optional pour voir si le produit existe en base.
		Optional<Produit> produit = produitDao.getProduit(idProduit);
		if(produit.isPresent()) {
			commande.add(produit.get());
		}	
	}
	
	public void supprimer(int id) throws InexistantException {
		if(!produitDao.delete(id)) {
			throw new InexistantException("Produit inexistant en base");
		}
	}
	
	public void update(Produit produit) throws RequeteInvalideException, InexistantException{
		if(isProduitBienRempli(produit)) {
			if(!produitDao.update(produit)) {
				throw new InexistantException("Le produit n'existe pas en base");
			}
		}else {
			throw new RequeteInvalideException("Il manque un champ");
		}
	}
	
	public void create(Produit produit) throws RequeteInvalideException{
		if(isProduitBienRempli(produit)) {
			produitDao.create(produit);
		}else {
			throw new RequeteInvalideException("Il manque un champ");
		}
	}
	
	private boolean isProduitBienRempli(Produit produit){
		boolean estBienRempli;
		if(produit.getCode() != null && produit.getLibelle() != null && produit.getPrix() != null) {
			estBienRempli = true;
		}else {
			estBienRempli = false;
		}
		return estBienRempli;
	}
}
