package com.github.smk7758.MatometeCmd;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public String chat_prefix = ChatColor.RED + "[" + ChatColor.RESET + "MatometeCmd" + ChatColor.RED +"] " + ChatColor.RESET;
	CommandListner command_listner = new CommandListner(this);

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(command_listner, this);
		saveDefaultConfig();
		reloadConfig();
	}

	@Override
	public void onDisable() {
	}
}
