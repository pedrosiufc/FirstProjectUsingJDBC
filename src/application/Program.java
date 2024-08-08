package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {

		/*
		 * Connection connection = null; Statement statement = null; ResultSet resultSet
		 * = null;
		 * 
		 * try {
		 * 
		 * // create the connection connection = DB.getConnection(); // create the
		 * connection status with the bank statement = connection.createStatement(); //
		 * receives the Sql query resultSet =
		 * statement.executeQuery("select * from departament");
		 * 
		 * while (resultSet.next()) { System.out.println(resultSet.getInt("Id") + ", " +
		 * resultSet.getString("Name"));
		 * 
		 * }
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); }
		 * 
		 * finally { DB.closeResultSet(resultSet); DB.closeStatement(statement);
		 * DB.closeConnection(); }
		 */

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Connection connection = null;
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
			
			if(rowsAffected > 0) {
				ResultSet resultSet=statement.getGeneratedKeys();
				while(resultSet.next()) {
					int id=resultSet.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			}else {
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

}
