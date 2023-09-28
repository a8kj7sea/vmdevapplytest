package me.a8kj.test.manager;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import me.a8kj.test.enums.PlayerDataType;

public class PlayerDataManager {

	public LinkedHashMap<String, PlayerData> playersData;

	public PlayerDataManager() {
		this.playersData = new LinkedHashMap<>();
	}

	public LinkedHashMap<String, PlayerData> getPlayersData() {
		return playersData;
	}

	public boolean exits(String playerName) {
		return playersData.containsKey(playerName);
	}

	public void addPlayer(String playerName, PlayerData playerData) {
		if (!exits(playerName))
			playersData.put(playerName, playerData);
		else
			playersData.replace(playerName, playerData);
	}

	public void removePlayer(String playerName, PlayerData playerData) {
		if (!exits(playerName))
			return;
		else
			playersData.remove(playerName, playerData);
	}

	public void setPlayerData(String playerName, PlayerData playerData) {
		if (!exits(playerName))
			playersData.put(playerName, playerData);
		else
			playersData.replace(playerName, playerData);
	}

	public PlayerData getPlayerData(String playerName) {
		if (!exits(playerName))
			return null;
		return playersData.get(playerName);

	}

	// DataType methods

	public void addDataWithAnAmountToPlayer(Player player, PlayerDataType playerDataType, int amount) {
		if (!exits(player.getName())) {
			return;
		}

		PlayerData playerData = getPlayerData(player.getName());
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			playerData.setBrokenBlocksCount(playerData.getBrokenBlocksCount() + Math.abs(amount));
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			playerData.setPlacedBlocksCount(playerData.getPlacedBlocksCount() + Math.abs(amount));
		} else {
			throw new IllegalArgumentException("There is no PlayerDataType with this argument!");
		}

	}

	public void removeDataWithAnAmountFromPlayer(Player player, PlayerDataType playerDataType, int amount) {
		if (!exits(player.getName())) {
			return;
		}

		PlayerData playerData = getPlayerData(player.getName());
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			playerData.setBrokenBlocksCount(playerData.getBrokenBlocksCount() - Math.abs(amount));
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			playerData.setPlacedBlocksCount(playerData.getPlacedBlocksCount() - Math.abs(amount));
		} else {
			throw new IllegalArgumentException("There is no PlayerDataType with this argument!");
		}

	}

	public void setDataWithAnAmountForPlayer(Player player, PlayerDataType playerDataType, int amount) {
		if (!exits(player.getName())) {
			return;
		}

		PlayerData playerData = getPlayerData(player.getName());
		if (playerDataType.equals(PlayerDataType.BROKEN_BLOCKS)) {
			playerData.setBrokenBlocksCount(Math.abs(amount));
		} else if (playerDataType.equals(PlayerDataType.PLACED_BLOCKS)) {
			playerData.setPlacedBlocksCount(Math.abs(amount));
		} else {
			throw new IllegalArgumentException("There is no PlayerDataType with this argument!");
		}

	}

}
