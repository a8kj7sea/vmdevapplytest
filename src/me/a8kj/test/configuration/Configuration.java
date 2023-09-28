package me.a8kj.test.configuration;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {

	private File file;
	private FileConfiguration config;

	private void init(boolean defaultSave, String configName, JavaPlugin plugin) throws IOException {
		file.getParentFile().mkdirs();
		if (!file.exists()) {
			if (defaultSave) {
				plugin.saveResource(configName, true);
			} else {
				file.createNewFile();
			}
		}
	}

	public Configuration(String fileName, JavaPlugin plugin, boolean saveDefault) {
		setFile(new File(plugin.getDataFolder(), fileName));
		try {
			init(saveDefault, fileName, plugin);
		} catch (IOException e) {
			e.printStackTrace();
		}
		load();
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException var1) {
			var1.printStackTrace();
		}
	}

	public void load() {
		setConfig((FileConfiguration) YamlConfiguration.loadConfiguration(file));
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}

	public FileConfiguration getConfig() {
		return config;
	}

}
