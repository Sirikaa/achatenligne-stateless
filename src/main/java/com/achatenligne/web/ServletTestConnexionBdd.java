package com.achatenligne.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet Pour se connecter à la base de données qui peut fonctionner partout 
 * @author Sirika
 *
 */
@WebServlet("/bdd")
public class ServletTestConnexionBdd extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Context envContext = InitialContext.doLookup("java:/comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("bdd/produits"); //On choisit le nom qu'on veut pourvu d'être d'accord avec l'admin BDD
			try(Connection connection = dataSource.getConnection();
					Statement stmt = connection.createStatement();){ //Statement pour faire des requêtes, query
				
				try(ResultSet rs = stmt.executeQuery("select 'hello world'")) { //On select la chaine de caractère hello world
					while(rs.next()) {
						String valeur = rs.getString(1); //En SQL on commence à compter à 1
						resp.getWriter().write(valeur);
					}
				}
				stmt.close();
			}catch(SQLException exception) {
				log("Problème pour récupérer la datasource", exception);
				throw new ServletException(exception);
			}
		}catch(NamingException ne) {
			//Ecrire dans les fichiers de logs du serveur
			log("Problème pour récupérer le contexte JNDI", ne);
			//On ne peut pas rajouter NamingException à la suite de ServletException et IOException donc on fait comme ça...
			throw new ServletException(ne);
		}
		//resp.getWriter().write("ça fonctionne"); 
	}
}
