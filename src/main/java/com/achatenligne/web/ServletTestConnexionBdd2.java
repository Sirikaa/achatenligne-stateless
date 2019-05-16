package com.achatenligne.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//Ne fonctionne QUE dans une servlet
@WebServlet("/bdd2")
public class ServletTestConnexionBdd2 extends HttpServlet{
	@Resource(name="bdd/produits")
	private DataSource dataSource;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Connection connection = dataSource.getConnection();
			Statement stmt = connection.createStatement();){
				try(ResultSet rs = stmt.executeQuery("select * from produit")) {
					while(rs.next()) {
						int idProduit = rs.getInt(1);
						String libelleProduit = rs.getString(2);
						double prixProduit = rs.getDouble(3);
						resp.getWriter().write("id : "+idProduit+", libellé : "+libelleProduit+", prix : "+prixProduit+"\n");
					}
					stmt.close();
				}
			}catch(SQLException e) {
				log("Problème pour récupérer la datasource", e);
				throw new ServletException(e);
			}
	}
}