package me.a8kj.test.command;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.a8kj.test.TestMain;
import me.a8kj.test.listener.custom.ServerReloadEvent;

import static me.a8kj.test.util.StringUtils.colorize;;

public class ReloadCommand implements CommandExecutor {

	final String RELOAD_COMMAND_PERMISSION = "test.commands.reloadcommand";

	private final TestMain main;

	public TestMain getMain() {
		return main;
	}

	public ReloadCommand(TestMain main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			TestMain.getPluginLogger().log(Level.WARNING,
					"You Cannot perform this command from console , this command especialy created to player usage !");
			return false;
		}

		Player player = (Player) sender;

		if (!player.hasPermission(RELOAD_COMMAND_PERMISSION)) {
			player.sendMessage(colorize("&c&lSorry &cYou don't have enough permission to perform this command !"));
			return false;
		}

		if (args.length != 0) {
			player.sendMessage(colorize("&cWrong usage please try to type /vmtrl"));
			return false;
		}

		
		TestMain.getConfiguration().load();
		Bukkit.getServer().getPluginManager().callEvent(new ServerReloadEvent(main.getServer()));
		Bukkit.getPluginManager().disablePlugin(getMain());
		Bukkit.getPluginManager().enablePlugin(getMain());
		player.sendMessage(colorize("&aWoooooosh you have been reloaded the configuration file successfully !"));

		return true;
	}
}
