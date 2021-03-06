package com.achatenligne.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.achatenligne.model.Produit;

public class ProduitDAO extends AbstractDAO{
	
	public List<Produit> getAll() {
		try(Connection connection = ProduitsDataSource.getSingleton().getConnection();
			Statement stmt = connection.createStatement()){
			List<Produit> produits = new ArrayList<Produit>();
			try(ResultSet rs = stmt.executeQuery("select id, code, libelle, prix from produit")){
				while(rs.next()) {
					Produit p = new Produit(rs.getInt("id"), rs.getString("code"), rs.getString("libelle"), rs.getBigDecimal("prix"));
					produits.add(p);
				}
			}
			return produits;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//Optional <> pour gérer que ça peut ne rien retourner. Alternative à une exception ou un return null.
	public Optional<Produit> getProduit(int id) {
		try(Connection connection = ProduitsDataSource.getSingleton().getConnection();
				Statement stmt = connection.createStatement()){
			try(ResultSet rs = stmt.executeQuery("select * from produit where id ="+id)){
				if(rs.next()) {
					Produit p = new Produit(rs.getInt("id"), rs.getString("code"), rs.getString("libelle"), rs.getBigDecimal("prix"));
					return Optional.of(p);
				}else {
					return Optional.empty();
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void create(Produit produit) {
		String sql = "insert into produit (code, libelle, prix) values (?, ?, ?)";
		try {
			boolean transactionOk = false;
			Connection connection = ProduitsDataSource.getSingleton().getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, produit.getCode());
				stmt.setString(2, produit.getLibelle());
				stmt.setBigDecimal(3, produit.getPrix());
				stmt.executeUpdate();
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						produit.setId(rs.getInt(1));
					}
				}
				transactionOk = true;
			} finally {
				checkTransactionAndClose(connection, transactionOk);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean update(Produit produit) {
		String sql = "update produit set code = ?, libelle = ?, prix = ? where id = ?";
		boolean haveWeUpdateSomething = false;
		try {
			boolean transactionOk = false;
			Connection connection = ProduitsDataSource.getSingleton().getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, produit.getCode());
				stmt.setString(2, produit.getLibelle());
				stmt.setBigDecimal(3, produit.getPrix());
				stmt.setInt(4, produit.getId());
				if(stmt.executeUpdate() > 0) {
					haveWeUpdateSomething = true;
				}else {
					haveWeUpdateSomething = false;
				}
				transactionOk = true;
			} finally {
				checkTransactionAndClose(connection, transactionOk);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return haveWeUpdateSomething;
	}

	public boolean delete(int id) {
		String sql = "delete from produit where id = ?";
		boolean haveWeDeleteSomething = false;
		try {
			boolean transactionOk = false;
			Connection connection = ProduitsDataSource.getSingleton().getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, id);
				if(stmt.executeUpdate() > 0) {
					haveWeDeleteSomething = true;
				}else {
					haveWeDeleteSomething = false;
				}
				transactionOk = true;
			} finally {
				checkTransactionAndClose(connection, transactionOk);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return haveWeDeleteSomething;
	}
}
