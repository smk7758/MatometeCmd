package com.github.smk7758.MatometeCmd;

import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecuter implements CommandExecutor {
	public Main main = null;

	public CommandExecuter(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("MatometeCmd")) {
			if ((sender instanceof Player) && (!sender.hasPermission("MatometeCmd.cmds"))) {
				SendLog.sendPermissionErrorMessage("MatometeCmd.cmds", sender);
				return false;
			}
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("help")) {
					sendCommandList(sender);
				} else if (args[0].equalsIgnoreCase("reload")) {
					main.reloadConfig();
					SendLog.send("config.yml has been reloaded.", sender);
					return true;
				} else if (args[0].equalsIgnoreCase("debug")) {
						Main.debug_mode = !Main.debug_mode;
						SendLog.send("DebugMode has been swiched.", sender);
						main.getConfig().set("DebugMode", Main.debug_mode);
						SendLog.debug("test DebugMode", sender);
						SendLog.send("DebugMode: " + Main.debug_mode, sender);
						return true;
				} else if (args[0].equalsIgnoreCase("commands")) {
					if (args.length > 1 && args[1].equalsIgnoreCase("false")) {
						main.getCommandsContents().forEach((content_cmd, call_cmds) -> SendLog.send(content_cmd, sender));
						return true;
					} else {
						Map<String, List<String>> contents = main.getCommandsContents();
						contents.forEach((content_cmd, call_cmds) -> {
							SendLog.send(content_cmd + ": ", sender);
							call_cmds.forEach(call_cmd -> SendLog.send("- " + call_cmd, sender));
						});
						return true;
					}
				}
			} else {
				sendCommandList(sender);
				return true;
			}
		}
		return false;
	}

	public void sendCommandList(CommandSender sender) {
		SendLog.send("Command Arguments List:", sender);
		SendLog.send("help", sender);
		SendLog.send("-- shows you this.", sender);
		SendLog.send("reload", sender);
		SendLog.send("-- reloads config.yml file.", sender);
		SendLog.send("debug", sender);
		SendLog.send("-- switches DebugMode.", sender);
		SendLog.send("commands", sender);
		SendLog.send("-- shows commands with call commands.", sender);
		SendLog.send("commands false", sender);
		SendLog.send("-- shows just commands.", sender);
		return;
	}
}
