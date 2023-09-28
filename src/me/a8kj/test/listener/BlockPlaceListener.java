package me.a8kj.test.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.a8kj.test.TestMain;
import me.a8kj.test.enums.PlayerDataType;
import me.a8kj.test.manager.PlayerData;
import me.a8kj.test.manager.PlayerDataManager;

public class BlockPlaceListener implements Listener {
	final int SECOND = 20;

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block blockEvent = event.getBlock();
		PlayerDataManager playerDataManager = TestMain.getPlayerDataManager();
		if (playerDataManager.exits(event.getPlayer().getName())) {
			playerDataManager.addDataWithAnAmountToPlayer(event.getPlayer(), PlayerDataType.PLACED_BLOCKS, 1);
		} else {
			playerDataManager.addPlayer(event.getPlayer().getName(),
					new PlayerData().setBrokenBlocksCount(0).setPlacedBlocksCount(1));
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				blockEvent.setType(Material.AIR);
			}
		}.runTaskLater(TestMain.getInstance(), SECOND * 5);
	}
}
