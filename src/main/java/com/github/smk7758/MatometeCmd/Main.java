package com.github.smk7758.MatometeCmd;

import java.util.ArrayList;
import java.util.Arrays;
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

	/**
	 * Don't forget to run saveConfig().
	 * @param cmd
	 * @param list
	 */
	public void addCommand(String cmd, List<String> list) {
		getConfig().set("Commands." + cmd, list);
	}

	/**
	 * Don't forget to run saveConfig().
	 * @param cmd
	 * @param list
	 */
	public void removeCommand(String cmd) {
		getConfig().set("Commands." + cmd, null);
	}

	/**
	 * removes items from front, and returns with List.
	 * @param array
	 * @param length
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IndexOutOfBoundsException
	 */
	public List<String> removeFrontItemsOfArray(String[] array, int length) throws IllegalArgumentException, IndexOutOfBoundsException {
		if (array == null) throw new IllegalArgumentException();
		if (array.length < length) throw new IndexOutOfBoundsException("array length: " + array.length + "remove item length:" + length);
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		for (int i = 0; i < length; i++) {
			list.remove(0);
		}
		return list;
	}

	public List<String> slimItems(List<String> items) {
		if (items == null) throw new IllegalArgumentException();
		List<String> list = new ArrayList<String>();
		for (String item : items) {
			item = item.replaceAll("_", " ");
			list.add(item);
		}
		return list;
	}
//	public List<String> removeAfterItemsOfArray(String[] array, int length) {
//		List<String> list = new ArrayList<String>(Arrays.asList(array));
//		for (int i = array.length; i > length; i--) {
//			list.remove(i - 1);
//		}
//		return list;
//	}

	public CommandExecuter getCommandExecuter() {
		return command_executer;
	}

	public CommandListner getCommandListner() {
		return command_listner;
	}
}
