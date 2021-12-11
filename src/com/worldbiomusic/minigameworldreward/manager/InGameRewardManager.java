package com.worldbiomusic.minigameworldreward.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.wbm.plugin.util.data.yaml.YamlHelper;
import com.wbm.plugin.util.data.yaml.YamlManager;
import com.wbm.plugin.util.data.yaml.YamlMember;

public class InGameRewardManager implements YamlMember {
	private YamlManager yamlManager;
	private Map<String, Object> data;

	public InGameRewardManager() {
		this.data = new LinkedHashMap<>();

		this.initData();
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	private void initData() {
		// rewards
		this.initRewardData();

		// participant percent
		this.data.put("min-participant-percent", 50);

		// active type
		this.initActiveTypes();
	}

	private void initRewardData() {
		Map<String, Object> reward = new LinkedHashMap<>();

		this.data.put("reward", reward);

		Map<String, Object> rank = new LinkedHashMap<>();
		Map<String, Object> percent = new LinkedHashMap<>();
		reward.put("rank", rank);
		reward.put("percent", percent);

		rank.put("1", getRewardDataObject(10, 100));
		rank.put("2", getRewardDataObject(7, 50));
		rank.put("3", getRewardDataObject(5, 30));

		percent.put("25", getRewardDataObject(4, 20));
		percent.put("50", getRewardDataObject(3, 15));
		percent.put("75", getRewardDataObject(2, 10));
		percent.put("100", getRewardDataObject(1, 5));

	}

	private Object getRewardDataObject(int itemAmount, int xpAmount) {
		Map<String, Object> template = new LinkedHashMap<>();

		// items
		List<ItemStack> firItems = new ArrayList<>();
		firItems.add(new ItemStack(Material.OAK_WOOD, itemAmount));
		firItems.add(new ItemStack(Material.COAL, itemAmount));
		template.put("items", firItems);

		// xp
		template.put("xp", xpAmount);

		return template;
	}

	private void initActiveTypes() {
		Map<String, Object> activeTypes = new LinkedHashMap<>();

		activeTypes.put("solo", false);
		activeTypes.put("solo-battle", true);
		activeTypes.put("team", false);
		activeTypes.put("team-battle", true);

		this.data.put("active-types", activeTypes);
	}

	@Override
	public void reload() {
		this.yamlManager.reload(this);
	}

	@Override
	public void setData(YamlManager yamlManager, FileConfiguration config) {
		this.yamlManager = yamlManager;

		if (config.isSet("data")) {
			this.data = YamlHelper.ObjectToMap(config.getConfigurationSection("data"));
		}

		config.set("data", this.data);
	}

	@Override
	public String getFileName() {
		return "in-game-reward.yml";
	}
}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
