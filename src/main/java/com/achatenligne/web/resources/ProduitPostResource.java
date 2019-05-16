package com.achatenligne.web.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.achatenligne.model.Produit;
import com.achatenligne.model.exceptions.RequeteInvalideException;
import com.achatenligne.model.services.ProduitService;

@Path("/produit")
public class ProduitPostResource {
	
	private ProduitService produitService = new ProduitService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//Pour afficher le produit créé
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Produit produit, @Context UriInfo uriInfo) throws RequeteInvalideException {
		produitService.create(produit);
		URI uri = uriInfo.getRequestUriBuilder().build();
		return Response.created(uri)
				       .entity(produit)
				       .build();
	}
}
