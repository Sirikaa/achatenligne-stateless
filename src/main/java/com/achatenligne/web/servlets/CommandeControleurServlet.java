package com.achatenligne.web.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.achatenligne.model.Commande;
import com.achatenligne.model.exceptions.CommandeVideException;
import com.achatenligne.model.services.CommandeService;
import com.achatenligne.model.services.ProduitService;

@WebServlet("/commande")
public class CommandeControleurServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Commande commande = getCommande(req);
			req.setAttribute("commande", commande);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/commande.jsp").forward(req, resp);
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Commande commande = getCommande(req);
			req.setAttribute("commande", commande);
			try {
				CommandeService commandeService = new CommandeService();
				commandeService.valider(commande);
				getServletContext().getRequestDispatcher("/WEB-INF/jsp/commandeValidee.jsp").forward(req, resp);
			} catch (CommandeVideException e) {
				doGet(req, resp);
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Commande getCommande(HttpServletRequest req) throws SQLException {
		ProduitService produitService = new ProduitService();
		int[] productIds = toIntArray(req.getParameterValues("produitId"));
		return produitService.creerCommande(productIds);
	}

	private static int[] toIntArray(String[] stringArray) {
		if (stringArray == null) {
			return new int[0];
		}
		int[] intArray = new int[stringArray.length];
		for (int i = 0 ; i < stringArray.length; ++i) {
			intArray[i] = Integer.valueOf(stringArray[i]);
		}
		return intArray;
	}
	
}
