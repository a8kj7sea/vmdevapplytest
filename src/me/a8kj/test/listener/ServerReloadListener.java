package me.a8kj.test.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import me.a8kj.test.listener.custom.ServerReloadEvent;

public class ServerReloadListener implements Listener {

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		Bukkit.getServer().getPluginManager().callEvent(new ServerReloadEvent(event.getPlugin().getServer()));
	}

	@EventHandler
	public void onServerReload(ServerReloadEvent event) {
		for (Player player : event.getServer().getOnlinePlayers()) {
			player.kickPlayer("Server is reloading .. please rejoin !");
		}
	}

}
