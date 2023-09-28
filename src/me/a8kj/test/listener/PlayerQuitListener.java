package me.a8kj.test.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.a8kj.test.TestMain;
import me.a8kj.test.manager.PlayerDataManager;
import me.a8kj.test.manager.PlayerDataMySQLManager;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlayerDataManager playerDataManager = TestMain.getPlayerDataManager();
		PlayerDataMySQLManager playerDataMySQLManager = new PlayerDataMySQLManager();

		if (playerDataManager.exits(player.getName())) {
			playerDataMySQLManager.saveAndStoreDataToPlayer(player);
			playerDataManager.playersData.remove(player.getName());
		}
	}
}
