package me.a8kj.test.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import me.a8kj.test.TestMain;
import me.a8kj.test.enums.PlayerDataType;
import me.a8kj.test.manager.PlayerData;
import me.a8kj.test.manager.PlayerDataManager;

public class BlockBreakListener implements Listener {

	final int SECOND = 20;

	List<Map<Location, Material>> brokenBlocks = new ArrayList<Map<Location, Material>>();
	List<Map<Location, MaterialData>> brokenBlocksData = new ArrayList<Map<Location, MaterialData>>();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Map<Location, Material> brokenBlocksEventMaterialMap = new HashMap<>();
		Map<Location, MaterialData> brokenBlocksDataMap = new HashMap<>();
		Block blockEvent = event.getBlock();

		// brokenBlocksEventMaterialMap
		brokenBlocksEventMaterialMap.put(blockEvent.getLocation(), blockEvent.getType());
		brokenBlocks.add((Map<Location, Material>) brokenBlocksEventMaterialMap);

		// brokenBlocksDataMap
		brokenBlocksDataMap.put(blockEvent.getLocation(), blockEvent.getState().getData());
		brokenBlocksData.add((Map<Location, MaterialData>) brokenBlocksDataMap);

		PlayerDataManager playerDataManager = TestMain.getPlayerDataManager();
		if (playerDataManager.exits(event.getPlayer().getName())) {
			playerDataManager.addDataWithAnAmountToPlayer(event.getPlayer(), PlayerDataType.BROKEN_BLOCKS, 1);
		} else {
			playerDataManager.addPlayer(event.getPlayer().getName(),
					new PlayerData().setBrokenBlocksCount(1).setPlacedBlocksCount(0));
		}

		new BukkitRunnable() {

			@Override
			public void run() {

				try {
					if (!brokenBlocks.isEmpty()) {
						for (Map<Location, Material> typeMap : brokenBlocks) {
							typeMap.entrySet().stream().forEach(entry -> {

								Location loc = entry.getKey();
								loc.getBlock().setType(entry.getValue());
							});
						}
						if (!brokenBlocksData.isEmpty()) {
							for (Map<Location, MaterialData> dataMap : brokenBlocksData) {
								dataMap.entrySet().stream().forEach(entry -> {

									Location loc = entry.getKey();
									if (entry.getValue() != null) {
										BlockState blockState = loc.getBlock().getState();
										blockState.setData((MaterialData) entry.getValue());
										blockState.update();
									}
								});
							}
						}

					} else {
						Location blockLocation = blockEvent.getLocation();
						blockLocation.getBlock().setType(blockEvent.getType());
					}
				} catch (java.lang.IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(TestMain.getInstance(), SECOND * 5);
	}

}
