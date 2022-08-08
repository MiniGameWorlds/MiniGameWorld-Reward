package com.minigameworldreward;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.wbm.plugin.util.Metrics;
import com.wbm.plugin.util.data.yaml.YamlManager;
import com.minigameworld.util.Utils;
import com.minigameworldreward.cmd.Commands;
import com.minigameworldreward.giver.InDataRewardGiver;
import com.minigameworldreward.giver.InGameRewardGiver;
import com.minigameworldreward.manager.InDataRewardManager;
import com.minigameworldreward.manager.InGameRewardManager;

public class MiniGameWorldRewardMain extends JavaPlugin {
	private static MiniGameWorldRewardMain instance;
	private InGameRewardManager inGameRewardManager;
	private InDataRewardManager inDataRewardManager;
	private InGameRewardGiver inGameRewardGiver;
	private InDataRewardGiver inDataRewardGiver;
	private Commands commands;
	private YamlManager yamlManager;

	public static MiniGameWorldRewardMain getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;

		Utils.info(ChatColor.GREEN + "MiniGameWorld Reward ON");

		// bstats
		new Metrics(this, 14385);

		// data manager with config
		this.inGameRewardManager = new InGameRewardManager();
		this.inDataRewardManager = new InDataRewardManager();

		// giver
		this.inGameRewardGiver = new InGameRewardGiver(this.inGameRewardManager);
		this.inDataRewardGiver = new InDataRewardGiver(this.inDataRewardManager);

		// Yaml manager
		this.yamlManager = new YamlManager(getDataFolder());
		this.yamlManager.registerMember(inGameRewardManager);
		this.yamlManager.registerMember(inDataRewardManager);

		// register reward listener
		getServer().getPluginManager().registerEvents((this.inGameRewardGiver), this);
		getServer().getPluginManager().registerEvents((this.inDataRewardGiver), this);

		// command
		this.commands = new Commands(this.yamlManager);
		getCommand("reward").setExecutor(this.commands);

	}

	@Override
	public void onDisable() {
		super.onDisable();
		Utils.info(ChatColor.RED + "MiniGameWorld Reward OFF");

		this.yamlManager.saveAllData();
	}
}
