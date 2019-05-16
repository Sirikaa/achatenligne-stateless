package com.achatenligne.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.achatenligne.model.services.ProduitService;

@WebServlet("/produits")
public class ListeProduitsControleurServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProduitService service = new ProduitService();
		try {
			req.setAttribute("produits", service.getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/jsp/produits.jsp").forward(req, resp);
	}

}
