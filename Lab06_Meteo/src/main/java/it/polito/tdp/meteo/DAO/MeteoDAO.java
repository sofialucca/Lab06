package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		String sql="SELECT DATA,Umidita "
				+ "FROM situazione "
				+ "WHERE DATA>=? AND DATA<=? AND Localita=?";
		List<Rilevamento> rilevamentiMese= new ArrayList<>();
		
		try {
			Connection conn =ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			
			st.setDate(1,Date.valueOf("2013-"+mese+"-01"));
			st.setDate(2,Date.valueOf("2013-"+mese+"-31"));
			st.setString(3, localita);
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				rilevamentiMese.add(new Rilevamento(localita,rs.getDate("Data"),rs.getInt("Umidita")));
			}
			
			st.close();
			rs.close();
			conn.close();
			return rilevamentiMese;
		}catch(SQLException sqle) {
			throw new RuntimeException("Errore caricamento database",sqle);
		}
	}

	public double getAvgRilevamentiLocalitaMese(int mese, String localita) {
		String sql="SELECT AVG(umidita) AS media "
				+ "FROM situazione "
				+ "WHERE DATA>=? AND DATA<=? AND Localita=?";
		
		double mediaUmidita=-1;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDate(1,Date.valueOf("2013-"+mese+"-01"));
			st.setDate(2,Date.valueOf("2013-"+mese+"-31"));
			st.setString(3, localita);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				mediaUmidita=rs.getDouble("media");
			}
			
			rs.close();
			st.close();
			conn.close();
		}catch(SQLException sqle) {
			throw new RuntimeException("Errore caricamento database",sqle);			
		}
		return mediaUmidita;
	}

}
