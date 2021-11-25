package com.worldbiomusic.minigameworldreward;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.wbm.plugin.util.data.yaml.YamlManager;
import com.worldbiomusic.minigameworld.api.MiniGameWorld;
import com.worldbiomusic.minigameworld.util.Utils;

public class MiniGameWorldReward extends JavaPlugin {
	private YamlManager yamlManager;

	@Override
	public void onEnable() {
		super.onEnable();
		Utils.info(ChatColor.GREEN + "MiniGameWorld Reward ON");

		// data manager with config
		RewardDataManager rewardDataManager = new RewardDataManager();

		// reward manager
		RewardManager rewardManager = new RewardManager(rewardDataManager);

		// Yaml manager
		this.yamlManager = new YamlManager(getDataFolder());
		this.yamlManager.registerMember(rewardDataManager);

		// register observer to MiniGameWorld
		MiniGameWorld mw = MiniGameWorld.create(MiniGameWorld.API_VERSION);
		mw.registerMiniGameObserver(rewardManager);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		Utils.info(ChatColor.RED + "MiniGameWorld Reward OFF");

		this.yamlManager.saveAllData();
	}
}
