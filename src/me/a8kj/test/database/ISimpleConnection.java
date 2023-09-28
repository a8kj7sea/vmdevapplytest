package me.a8kj.test.database;

import java.sql.ResultSet;

public interface ISimpleConnection {

	final String NOT_CONNECTED_MESSAGE = "Not connected to the database.";
	final String SUCCESSFULLY_CONNECTED_MESSAGE = "Connected to MySQL database successfully !";
	final String FAILED_CONNECTED_MESSAGE = "Failed to connected to database !";
	final String CLOESD_CONNECTION_MESSAGE = "Database connection has been closed !";

	void openConnection();

	void closeConnection();

	boolean isConnected();

	ResultSet query(String sql);

	ResultSet getResultSet(String sql);

	int update(String sql);
	
	void createTable(String tableLabel , String tableStructure);

}
