package com.achatenligne.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.achatenligne.model.Produit;
import com.achatenligne.model.exceptions.InexistantException;
import com.achatenligne.model.exceptions.RequeteInvalideException;
import com.achatenligne.model.services.ProduitService;

@Path("/produit/{id}")
public class ProduitResource {
	private ProduitService produitService = new ProduitService();

	@GET
	@Produces({ "application/json", "application/xml" })
	public Produit get(@PathParam("id") int id) throws InexistantException {
		return produitService.getOne(id).get();
	}

	@DELETE
	public void delete(@PathParam("id") int id) throws InexistantException {
		produitService.supprimer(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") int id, Produit produit) throws RequeteInvalideException, InexistantException {
		produit.setId(id);
		produitService.update(produit);
	}

}
