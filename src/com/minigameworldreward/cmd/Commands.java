package com.minigameworldreward.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.wbm.plugin.util.data.yaml.YamlManager;

public class Commands implements CommandExecutor {
	private YamlManager yamlManager;

	public Commands(YamlManager yamlManager) {
		this.yamlManager = yamlManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.isOp()) {
			return true;
		}

		String menu = args[0];
		switch (menu) {
		case "reload":
			reload(sender);
			break;
		case "save":
			save(sender);
			break;
		}

		return true;
	}

	private void reload(CommandSender sender) {
		this.yamlManager.reloadAllData();
		sender.sendMessage("Reload complete");
	}

	private void save(CommandSender sender) {
		this.yamlManager.saveAllData();
		sender.sendMessage("Saved");
	}
}
