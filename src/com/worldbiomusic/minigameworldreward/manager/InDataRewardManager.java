package com.worldbiomusic.minigameworldreward.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.IntIterable;
import org.bukkit.inventory.ItemStack;

import com.wbm.plugin.util.data.yaml.YamlHelper;
import com.wbm.plugin.util.data.yaml.YamlManager;
import com.wbm.plugin.util.data.yaml.YamlMember;

public class InDataRewardManager implements YamlMember {
	private YamlManager yamlManager;
	private Map<String, Object> data;

	public InDataRewardManager() {
		this.data = new LinkedHashMap<>();
		
		initData();
	}
	
	public Map<String, Object> getData() {
		return this.data;
	}

	private void initData() {
		this.data.put("min-saved-data-count", 10);

		initRewardData();
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

	@Override
	public String getFileName() {
		return "in-data-reward.yml";
	}

	@Override
	public void reload() {
	}

	@Override
	public void setData(YamlManager yaml, FileConfiguration config) {
		this.yamlManager = yaml;
		
		if (config.isSet("data")) {
			this.data = YamlHelper.ObjectToMap(config.getConfigurationSection("data"));
		}

		config.set("data", this.data);

	}

}
