package models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	// variaveis para setar o banco de dados
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/assistencia";
	private String user = "root";
	private String password = "";

	// objeto jdbc usado para conectar o banco
	private Connection con;
	/**
	 * Conexão
	 * @return con
	 */

	public Connection conectar() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;										
		}
	}
}
