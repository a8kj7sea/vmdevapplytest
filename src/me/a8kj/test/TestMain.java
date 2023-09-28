package me.a8kj.test;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.a8kj.test.command.ReloadCommand;
import me.a8kj.test.configuration.Configuration;
import me.a8kj.test.database.impl.SimpleConnectionManager;
import me.a8kj.test.listener.BlockBreakListener;
import me.a8kj.test.listener.BlockPlaceListener;
import me.a8kj.test.listener.PlayerJoinListener;
import me.a8kj.test.listener.PlayerQuitListener;
import me.a8kj.test.listener.ServerReloadListener;
import me.a8kj.test.manager.PlayerDataManager;

public class TestMain extends JavaPlugin implements Listener {

	private static TestMain instance;
	private static PlayerDataManager playerDataManager;
	private static Logger pluginLogger;
	private static Configuration configuration;
	private static SimpleConnectionManager simpleConnectionManager;

	void registerNiggs() {
		Listener[] listeners = { new ServerReloadListener(), new PlayerJoinListener(), new PlayerQuitListener(),
				new BlockPlaceListener(), new BlockBreakListener() };
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	@Override
	public void onEnable() {
		instance = this;
		playerDataManager = new PlayerDataManager();
		pluginLogger = this.getLogger();
		configuration = new Configuration("config.yml", this, true);
		simpleConnectionManager = new SimpleConnectionManager()
				.setHost(configuration.getConfig().getString("MySQL.host", "localhost"))
				.setUsername(configuration.getConfig().getString("MySQL.username", "root"))
				.setDatabase(configuration.getConfig().getString("MySQL.database", "devtest"))
				.setPassword(configuration.getConfig().getString("MySQL.password", "easypassword"));
		simpleConnectionManager.openConnection();
		registerNiggs();
		this.getCommand("testreload").setExecutor(new ReloadCommand(this));
	}

	@Override
	public void onDisable() {
		instance = null;
		playerDataManager = null;
		simpleConnectionManager.closeConnection();

	}

	public static TestMain getInstance() {
		return instance;
	}

	public static PlayerDataManager getPlayerDataManager() {
		return playerDataManager;
	}

	public static Logger getPluginLogger() {
		return pluginLogger;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static SimpleConnectionManager getSimpleConnectionManager() {
		return simpleConnectionManager;
	}

}
