package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			// create the connection
			connection = DB.getConnection();
			// create the connection status with the bank
			statement = connection.createStatement();
			// receives the Sql query
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

}
