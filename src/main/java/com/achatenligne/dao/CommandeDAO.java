package com.achatenligne.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.achatenligne.model.Commande;
import com.achatenligne.model.Produit;

public class CommandeDAO {
	
	public void addCommande(Commande commande){
		List<Produit> produits = commande.getProduits();
		boolean transactionOk = false;
		if(!produits.isEmpty()) {	
			try(Connection connection = ProduitsDataSource.getSingleton().getConnection();
					Statement stmt = connection.createStatement()){
				insertIntoCommande(stmt);
				//Solution JDBC pour récupérer l'id de la commande :
				//try(ResultSet rs = stmt.getGeneratedKeys()){
					//if(rs.next()){
						//int commandeId = rs.getInt(1);
						//insertMultipleIntoLigneCommande(connection, produits, commandeId);
				insertMultipleIntoLigneCommande(connection, produits);
				transactionOk = true;
				if(transactionOk) {
					connection.commit();
				}else {
					connection.rollback();
				}
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private void insertIntoCommande(Statement stmt) throws SQLException {
		stmt.executeUpdate("INSERT INTO commande() VALUES()");
		//stmt.executeUpdate("INSERT INTO commande() VALUES()", stmt.RETURN_GENERATED_KEYS);
	}
	
	private void insertMultipleIntoLigneCommande(Connection conn, List<Produit> produits) throws SQLException {
		try(PreparedStatement pStmt = conn.prepareStatement("INSERT INTO ligne_commande(commande_id, produit_id)"
															+ "VALUES(last_Insert_Id(), ?)")){
																	// ?, ?
			for(Produit p : produits) {
				pStmt.setInt(1, p.getId());
				pStmt.executeUpdate();
			}
		}
	}
}
