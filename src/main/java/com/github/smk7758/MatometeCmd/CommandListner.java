package com.github.smk7758.MatometeCmd;

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
		if (!main.hasCommand(event.getMessage())) return;
		event.setCancelled(true);
		main.callCommand(event.getMessage(), event.getPlayer());
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		if (!main.hasCommand(event.getCommand())) return;
		event.setCancelled(true);
		main.callCommand(event.getCommand(), event.getSender());
	}
}