package me.a8kj.test.manager;

import static java.lang.Math.abs;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.a8kj.test.TestMain;
//import me.a8kj.test.annotation.Comment;
import me.a8kj.test.enums.PlayerDataType;

public class PlayerDataMySQLManager {

	// set / remove / add / setup

	// load / save

	public PlayerDataMySQLManager() {
		createTable();
	}

	private void createTable() {
		TestMain.getSimpleConnectionManager().createTable("players_data", "player_name varchar(18) not null ,"
				+ "broken_blocks int unsigned default 0 , " + "placed_blocks int unsigned default 0");
	}

	public boolean exits(Player player) {
		String sqlStatement = "select * from players_data where player_name=\"" + player.getName() + "\"";
		ResultSet resultSet = TestMain.getSimpleConnectionManager().getResultSet(sqlStatement);
		try {
			if (resultSet.next()) {
				return resultSet.getString("player_name") != null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setupNewPlayer(Player player) {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (!exits(player)) {
					TestMain.getSimpleConnectionManager().update(String.format(
							"insert into players_data (player_name , broken_blocks , placed_blocks) values ('%s' , 0 , 0)",
							player.getName()));
				}
			}
		}.runTaskAsynchronously(TestMain.getInstance());
	}

	public void setDataToPlayer(PlayerDataType playerDataType, int amount, Player player) {
		if (!exits(player))
			return;
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			TestMain.getSimpleConnectionManager().update("update players_data set broken_blocks=" + abs(amount)
					+ " where player_name=\"" + player.getName() + "\"");
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			TestMain.getSimpleConnectionManager().update("update players_data set placed_blocks=" + abs(amount)
					+ " where player_name=\"" + player.getName() + "\"");
		} else {
			throw new IllegalArgumentException("There is no PlayerDataType with this argument !");
		}

	}

	public void addDataToPlayer(PlayerDataType playerDataType, int amount, Player player) {
		if (!exits(player))
			return;
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			TestMain.getSimpleConnectionManager().update("update players_data set broken_blocks= broken_blocks + "
					+ abs(amount) + " where player_name=\"" + player.getName() + "\"");
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			TestMain.getSimpleConnectionManager().update("update players_data set placed_blocks= placed_blocks + "
					+ abs(amount) + " where player_name=\"" + player.getName() + "\"");
		} else {
			throw new IllegalArgumentException("There is no PlayerDataType with this argument !");
		}
	}

	public void removeData(PlayerDataType playerDataType, int amount, Player player) {
		if (!exits(player))
			return;
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			TestMain.getSimpleConnectionManager().update("update players_data set broken_blocks= broken_blocks - "
					+ abs(amount) + " where player_name=\"" + player.getName() + "\"");
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			TestMain.getSimpleConnectionManager().update("update players_data set placed_blocks= placed_blocks - "
					+ abs(amount) + " where player_name=\"" + player.getName() + "\"");
		} else {
			throw new IllegalArgumentException("There is no PlayerDataType with this argument !");
		}
	}

	public int getData(PlayerDataType playerDataType, Player player) {
		if (!exits(player))
			return -1;
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			return getBrokenBlocksToPlayer(player);
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			return getPlacedBlocksToPlayer(player);
		} else {
			throw new IllegalArgumentException("There is no PlayerData with this argument!");
		}
	}

	public int getBrokenBlocksToPlayer(Player player) {
		if (!exits(player))
			return -1;
		String sqlStatement = String
				.format("select distinct broken_blocks from players_data where player_name = \"%s\"", player.getName());

		ResultSet resultSet = TestMain.getSimpleConnectionManager().getResultSet(sqlStatement);
		try {
			if (resultSet.next()) {
				return resultSet.getInt("broken_blocks");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	public int getPlacedBlocksToPlayer(Player player) {
		if (!exits(player))
			return -1;
		String sqlStatement = String
				.format("select distinct placed_blocks from players_data where player_name = \"%s\"", player.getName());

		ResultSet resultSet = TestMain.getSimpleConnectionManager().getResultSet(sqlStatement);
		try {
			if (resultSet.next()) {
				return resultSet.getInt("placed_blocks");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	// used to store player data to database !
	public void storePlayerDataToMySQL(PlayerData playerData, Player player) {
		new BukkitRunnable() {

			@Override
			public void run() {
				setDataToPlayer(PlayerDataType.BROKEN_BLOCKS, playerData.getBrokenBlocksCount(), player);
				setDataToPlayer(PlayerDataType.PLACED_BLOCKS, playerData.getPlacedBlocksCount(), player);
			}
		}.runTaskAsynchronously(TestMain.getInstance());
	}

	// used when player login to server
	public PlayerData loadDataAndConvertToPlayerData(Player player) {
		PlayerData playerData = new PlayerData();
		new BukkitRunnable() {

			@Override
			public void run() {
				playerData.setBrokenBlocksCount(getBrokenBlocksToPlayer(player));
				playerData.setPlacedBlocksCount(getPlacedBlocksToPlayer(player));
			}
		}.runTaskAsynchronously(TestMain.getInstance());
		return playerData;
	}

	// used when player quit from server to save already data in his class and store
	// that in database
	public void saveAndStoreDataToPlayer(Player player) {
		PlayerDataManager playerDataManager = TestMain.getPlayerDataManager();
		if (!exits(player)) {
			setupNewPlayer(player);
			return;
		}
		if (!playerDataManager.exits(player.getName())) {
			return;
		}
		storePlayerDataToMySQL(playerDataManager.getPlayerData(player.getName()), player);
	}

}
