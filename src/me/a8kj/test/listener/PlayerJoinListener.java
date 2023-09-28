package me.a8kj.test.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.a8kj.test.TestMain;
import me.a8kj.test.manager.PlayerData;
import me.a8kj.test.manager.PlayerDataManager;
import me.a8kj.test.manager.PlayerDataMySQLManager;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		PlayerDataManager playerDataManager = TestMain.getPlayerDataManager();

		if (playerDataManager.exits(player.getName()))
			return;

		PlayerDataMySQLManager playerDataMySQLManager = new PlayerDataMySQLManager();

		if (playerDataMySQLManager.exits(player)) {
			playerDataManager.addPlayer(player.getName(),
					playerDataMySQLManager.loadDataAndConvertToPlayerData(player));
		} else {
			playerDataMySQLManager.setupNewPlayer(player);
			playerDataManager.addPlayer(player.getName(), new PlayerData());
		}

	}
}
