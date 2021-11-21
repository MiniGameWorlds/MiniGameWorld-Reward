package com.worldbiomusic.minigameworldreward;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.wbm.plugin.util.data.yaml.YamlManager;
import com.worldbiomusic.minigameworld.api.MiniGameWorld;

public class MiniGameWorldReward extends JavaPlugin {
	private YamlManager yamlManager;

	@Override
	public void onEnable() {
		super.onEnable();
		getLogger().info(ChatColor.GREEN + "MiniGameWorld Reward ON");

		// data manager with config
		RewardDataManager rewardDataManager = new RewardDataManager();

		// reward manager
		RewardManager rewardManager = new RewardManager(rewardDataManager);

		// Yaml manager
		this.yamlManager = new YamlManager(getDataFolder());
		yamlManager.registerMember(rewardDataManager);

		// register observer to MiniGameWorld
		MiniGameWorld mw = MiniGameWorld.create(MiniGameWorld.API_VERSION);
		mw.registerMiniGameObserver(rewardManager);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		this.yamlManager.saveAllData();
	}
}
