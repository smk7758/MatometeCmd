package com.github.smk7758.MatometeCmd;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListner implements Listener {
	public Main main = null;

	public CommandListner(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (!hasCommand(event.getMessage())) return;
		event.setCancelled(true);
		callCommand(event.getMessage(), event.getPlayer());
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		if (!hasCommand(event.getCommand())) return;
		event.setCancelled(true);
		callCommand(event.getCommand(), event.getSender());
	}

	public boolean hasCommand(String cmd) {
		if (main.getConfig().contains("Commands." + cmd)) return true;
		else return false;
	}

	public boolean callCommand(String cmd, CommandSender sender) {
		List<String> config_commands = main.getConfig().getStringList("Commands." + cmd);
		for (String config_command : config_commands) {
			convertText(config_command, sender);
			Bukkit.dispatchCommand(sender, config_command);
		}
		if (main.getConfig().getBoolean("DebugMode")) sender.sendMessage(main.chat_prefix + cmd);
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
}
