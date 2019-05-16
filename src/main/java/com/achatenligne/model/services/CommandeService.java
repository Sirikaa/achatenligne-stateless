package com.achatenligne.model.services;


import com.achatenligne.dao.CommandeDAO;
import com.achatenligne.model.Commande;
import com.achatenligne.model.exceptions.CommandeVideException;

public class CommandeService {

	public void valider(Commande commande) throws CommandeVideException{
		if (commande == null || commande.getProduits().isEmpty()) {
			throw new CommandeVideException("Votre commande est vide");
		}
		//Insertion en BDD ?
		CommandeDAO commandeDao = new CommandeDAO();
		commandeDao.addCommande(commande);
		// pour l'instant on vide la commande
		commande.getProduits().clear();	
	}

}
