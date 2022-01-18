package com.worldbiomusic.minigameworldreward;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.wbm.plugin.util.data.yaml.YamlManager;
import com.worldbiomusic.minigameworld.util.Utils;
import com.worldbiomusic.minigameworldreward.cmd.Commands;
import com.worldbiomusic.minigameworldreward.giver.InDataRewardGiver;
import com.worldbiomusic.minigameworldreward.giver.InGameRewardGiver;
import com.worldbiomusic.minigameworldreward.manager.InDataRewardManager;
import com.worldbiomusic.minigameworldreward.manager.InGameRewardManager;

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
