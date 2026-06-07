package eva.luca.soccorso_Web.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import eva.luca.soccorso_Web.data.db.ConnectionFactory;
import eva.luca.soccorso_Web.models.Operator;

public class OperatorDao implements IDaoRead<Operator>, IDaoWrite<Operator>{

	@Override
	public boolean insert(Operator o) {
		
		try {
			Connection con = ConnectionFactory.getConnection();
			
			String query = "INSERT INTO operatori(nome, cognome, eta, email, passkey) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, o.getName());
			ps.setString(2, o.getSurname());
			ps.setDate(3, java.sql.Date.valueOf(o.getAge())); //setDate non accetta un LocalDate, ma un java.sql.Date
			ps.setString(4, o.getEmail());
			ps.setString(5, o.getPasskey());
			
			int affRows = ps.executeUpdate();
			
			if(affRows > 0) {
				return true;
			}
			
		} catch (Exception e) {
			throw new RuntimeException("JDBC error", e);
		}
		
		return false;
	}

	@Override
	public boolean delete(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Operator t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean updateStatusBySquadraId(int squadraId, String stato) {
		try {
			Connection con = ConnectionFactory.getConnection();
			
			String query = "UPDATE operatori o " +
                    "JOIN appartenenza a " +
                    "ON o.operatoreID = a.operatoreRIF " +
                    "SET o.stato = ? " +
                    "WHERE a.squadraRIF = ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, stato);
			ps.setInt(2, squadraId);
			
			int affRows = ps.executeUpdate();
			
			if(affRows > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("JDBC error", e);
		}
		return false;
	}

	@Override
	public ArrayList<Operator> findAll() {
			
			ArrayList<Operator> list = new ArrayList<Operator>();
			
			try {
				Connection con = ConnectionFactory.getConnection();
				
				String query = "SELECT operatoreID, nome, cognome, eta, email, stato FROM operatori";
				
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery(); //comunque un tipo di lista
				
				while(rs.next()) {
					Operator op = new Operator();
					op.setName(rs.getString("nome"));
					op.setSurname(rs.getString("cognome"));
					op.setAge(rs.getDate("eta").toLocalDate());
					op.setEmail(rs.getString("email"));
					op.setId(rs.getInt("operatoreID"));
					op.setStatus(rs.getString("stato"));
					
					list.add(op);
				}
				
			} catch (SQLException e) {
				throw new RuntimeException("JDBC error", e);
			}
			return list;
		}

	@Override
	public Operator findById(int id) {
		
		try {
			Connection con = ConnectionFactory.getConnection();
			
			String query = "SELECT operatoreID, nome, cognome, eta, email, stato FROM operatori WHERE operatoreID = ?";
			
			PreparedStatement ps =con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			Operator op = new Operator();
			
			while(rs.next()) {
				
				op.setName(rs.getString("nome"));
				op.setSurname(rs.getString("cognome"));
				op.setAge(rs.getDate("eta").toLocalDate());
				op.setEmail(rs.getString("email"));
				op.setId(rs.getInt("operatoreID"));
				op.setStatus(rs.getString("stato"));
						
			}
			
			return op;
			
			} catch (SQLException e) {
			throw new RuntimeException("JDBC error", e);
		}
	}
	
	
	public Operator findByMail(String mail) {
		
		try {
			Connection con = ConnectionFactory.getConnection();
			
			String query = "SELECT operatoreID, nome, cognome, eta, email FROM operatori WHERE email = ?";
			
			PreparedStatement ps =con.prepareStatement(query);
			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();
			
			Operator op = new Operator();
			
			while(rs.next()) {
				
				op.setName(rs.getString("nome"));
				op.setSurname(rs.getString("cognome"));
				op.setAge(rs.getDate("eta").toLocalDate());
				op.setEmail(rs.getString("email"));
				op.setId(rs.getInt("operatoreID"));
				op.setStatus(rs.getString("stato"));
						
			}
			
			return op;
			
			} catch (SQLException e) {
			throw new RuntimeException("JDBC error", e);
		}
	}

}
