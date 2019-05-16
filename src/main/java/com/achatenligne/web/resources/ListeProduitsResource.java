package com.achatenligne.web.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.achatenligne.model.Produit;
import com.achatenligne.model.services.ProduitService;

@Path("/produits")
public class ListeProduitsResource {

	@GET
	@Produces({"application/json", "application/xml"})
	public List<Produit> getAll() {
		ProduitService produitService = new ProduitService();
		return produitService.getAll();
	}
}
