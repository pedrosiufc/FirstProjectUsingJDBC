package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbExcepition;
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {

		Connection connection = null;

		// listandoDepartamentosExistentesNoBD(connection);
		// listandoVendedoresExistentesNoBD(connection);
		// inserindoVendedoresNoBD(connection);
		// inserindoNovosDepartamentosNoBD(connection);
		// atualizandoInformacoesNoBD(connection);
		// deletandoUmDepartamentoDoDB(connection);
		// deletandoUmVendedorDoDB(connection);
	 	validandoUmaTransacaoNoBD(connection);
	}

	public static void listandoDepartamentosExistentesNoBD(Connection connection) {

		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from departament");

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("Id") + ", " + resultSet.getString("Name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

	public static void listandoVendedoresExistentesNoBD(Connection connection) {

		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from seller");

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("Id") + ", " + resultSet.getString("Name") + ", "
						+ resultSet.getString("email") + ", " + resultSet.getString("birthDate") + ", "
						+ resultSet.getDouble("baseSalary"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

	public static void inserindoVendedoresNoBD(Connection connection) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		PreparedStatement statement = null;

		try {
			connection = DB.getConnection();
			statement = connection.prepareStatement("INSERT INTO seller"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentID)" + "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, "Carl Purple");
			statement.setString(2, "carl@gmail.com");
			statement.setDate(3, new java.sql.Date(sdf.parse("12/04/1985").getTime()));
			statement.setDouble(4, 3000.0);
			statement.setInt(5, 4);

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				while (resultSet.next()) {
					int id = resultSet.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			} else {
				System.out.println("No rown affected!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

	public static void inserindoNovosDepartamentosNoBD(Connection connection) {

		// Inserindo novos departamentos e retornando seus Ids

		PreparedStatement statement = null;

		try {
			connection = DB.getConnection();
			statement = connection.prepareStatement("INSERT INTO departament (Name) values ('VESTUARIO'), ('ENTREGAS')",
					Statement.RETURN_GENERATED_KEYS);

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				while (resultSet.next()) {
					int id = resultSet.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			} else {
				System.out.println("No rown affected!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

	public static void atualizandoInformacoesNoBD(Connection connection) {

		// atualizando dados no banco de dados
		PreparedStatement statement = null;

		try {
			connection = DB.getConnection();
			statement = connection.prepareStatement(
					"UPDATE seller " + "SET BaseSalary = BaseSalary + ? " + "WHERE " + "(DepartmentId = ?)");

			statement.setDouble(1, 200.0);
			statement.setInt(2, 2);

			int rowsAffected = statement.executeUpdate();

			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

	public static void deletandoUmDepartamentoDoDB(Connection connection) {

		// atualizando dados no banco de dados
		PreparedStatement statement = null;

		try {
			connection = DB.getConnection();
			statement = connection.prepareStatement("DELETE FROM departament " + "WHERE " + "Id = ?");
			
			statement.setInt(1, 7);

			int rowsAffected = statement.executeUpdate();

			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}

		finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}

	public static void deletandoUmVendedorDoDB(Connection connection) {
		
		// atualizando dados no banco de dados
		PreparedStatement statement = null;

		try {
			connection = DB.getConnection();
			statement = connection.prepareStatement("DELETE FROM seller " + "WHERE " + "Id = ?");
			
			statement.setInt(1, 8);

			int rowsAffected = statement.executeUpdate();

			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}
	
	public static void validandoUmaTransacaoNoBD(Connection connection) {

				
		Statement statement = null;

		try {
			connection = DB.getConnection();
			
			connection.setAutoCommit(false);
			
			statement = connection.createStatement();
			
			int rows1=statement.executeUpdate("UPDATE seller SET BaseSalary = 2090 "
					+ "WHERE DepartmentId =1");
			
			/*
			 * int x=1; if(x<2) { throw new SQLException("Fake Error"); }
			 */
			
			int rows2=statement.executeUpdate("UPDATE seller SET BaseSalary = 3090 "
					+ "WHERE DepartmentId = 2");
			
			connection.commit();
			
			System.out.println("rows1 " + rows1);
			System.out.println("rows2 " + rows2);
			
		} catch (SQLException e) {
			try {
				connection.rollback();
				throw new DbExcepition("Transaction rolled Back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbExcepition("Error trying to rollvback! Cause by: " + e1.getMessage());
			}
		}

		finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}
	
	
}
