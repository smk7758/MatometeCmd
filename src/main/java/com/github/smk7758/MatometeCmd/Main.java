package com.github.smk7758.MatometeCmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static final String plugin_name = "MatometeCmd";
	public static boolean debug_mode = false;
	private CommandListner command_listner = new CommandListner(this);
	private CommandExecuter command_executer = new CommandExecuter(this);

	@Override
	public void onEnable() {
		if (!Main.plugin_name.equals(getDescription().getName())) {
			getPluginLoader().disablePlugin(this);
		}
		getServer().getPluginManager().registerEvents(command_listner, this);
		getCommand("MatometeCmd").setExecutor(command_executer);
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	@Override
	public void reloadConfig() {
		super.reloadConfig();
		debug_mode = getConfig().getBoolean("DebugMode");
	}

	public boolean hasCommand(String cmd) {
		if (getConfig().contains("Commands." + cmd)) return true;
		else return false;
	}

	public boolean callCommand(String cmd, CommandSender sender) {
		List<String> commands = getConfig().getStringList("Commands." + cmd);
		for (String command : commands) {
			convertText(command, sender);
			Bukkit.dispatchCommand(sender, command);
		}
		SendLog.debug(cmd, sender);
		return true;
	}

	public String convertText(String text, CommandSender sender) {
		text = text.replaceFirst("/", "");
		text = text.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player) {
			Player player = (Player) sender;
			text = text.replaceAll("%World%", player.getWorld().getName());
		}
		text = ChatColor.translateAlternateColorCodes('&', text);
		return text;
	}

	public Map<String, List<String>> getCommandsContents() {
		Map<String, List<String>> contents = new HashMap<>();
		getCommands().forEach(command ->  contents.put(command, getConfig().getStringList("Commands." + command)));
//		for (String command : getCommands()) {
//			contents.put(command, getConfig().getStringList("Commands." + command));
//		}
		return contents;
	}

	public Set<String> getCommands() {
		return getConfig().getConfigurationSection("Commands").getKeys(true);
	}
}
