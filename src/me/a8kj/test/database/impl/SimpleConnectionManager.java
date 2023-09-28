package me.a8kj.test.database.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.a8kj.test.TestMain;
//import me.a8kj.test.annotation.Comment; that for my soon lib Stay Tuned if u care (lmao)
import me.a8kj.test.database.ISimpleConnection;

public class SimpleConnectionManager implements ISimpleConnection {

	private String host, username, database, password;
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public SimpleConnectionManager setHost(String host) {
		this.host = host;
		return this;
	}

	public SimpleConnectionManager setUsername(String username) {
		this.username = username;
		return this;
	}

	public SimpleConnectionManager setDatabase(String database) {
		this.database = database;
		return this;
	}

	public SimpleConnectionManager setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getDatabase() {
		return database;
	}

	public String getPassword() {
		return password;
	}


	public SimpleConnectionManager() {
	}

	public SimpleConnectionManager(String host, String username, String database, String password) {
		setHost(host);
		setUsername(username);
		setDatabase(database);
		setPassword(password);
	}

	@Override
	public void openConnection() {
		String jdbcUrl = "jdbc:mysql://" + host + ":" + "3306" + "/" + database + "?characterEncoding=latin1";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
			TestMain.getPluginLogger().info(SUCCESSFULLY_CONNECTED_MESSAGE);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeConnection() {
		if (isConnected()) {
			try {
				connection.close();
				TestMain.getPluginLogger().info(CLOESD_CONNECTION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isConnected() {
		return connection != null;
	}

	@Override
	public ResultSet query(String sql) {
		if (!isConnected()) {
			TestMain.getPluginLogger().warning(NOT_CONNECTED_MESSAGE);
			return null;
		}

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultSet getResultSet(String sqlQuery) {
		if (!isConnected()) {
			TestMain.getPluginLogger().warning(NOT_CONNECTED_MESSAGE);
			return null;
		}

		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	@Override
	public int update(String sql) {
		if (!isConnected()) {
			TestMain.getPluginLogger().warning(NOT_CONNECTED_MESSAGE);
			return -1;
		}

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void createTable(String tableLabel, String tableStructure) {
		if (!isConnected()) {
			TestMain.getPluginLogger().warning(NOT_CONNECTED_MESSAGE);
			return;
		}

		String createTableQuery = "create table if not exists " + tableLabel + " (" + tableStructure + ");";

		try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
