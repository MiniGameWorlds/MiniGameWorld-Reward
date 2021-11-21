package com.worldbiomusic.minigameworldreward;

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

public class RewardDataManager implements YamlMember {
	private YamlManager yamlManager;
	private Map<String, Object> data;

	public RewardDataManager() {
		this.data = new LinkedHashMap<>();

		this.initData();
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	private void initData() {
		// rewards
		this.initRewards();

		// participant percent
		this.data.put("min-participant-percent", 50);

		// active type
		this.initActiveTypes();
	}

	private void initRewards() {
		/*
		 * first Rank
		 */

		Map<String, Object> rewardData = new LinkedHashMap<>();

		Map<String, Object> firstRank = new LinkedHashMap<>();

		// items
		List<ItemStack> firItems = new ArrayList<>();
		firItems.add(new ItemStack(Material.OAK_WOOD, 5));
		firItems.add(new ItemStack(Material.COAL, 3));
		firstRank.put("items", firItems);

		// xp
		firstRank.put("xp", 100);

		rewardData.put("1", firstRank);

		/*
		 *  second Rank
		 */
		Map<String, Object> secondRank = new LinkedHashMap<>();

		// items
		List<ItemStack> secItems = new ArrayList<>();
		secItems.add(new ItemStack(Material.OAK_WOOD, 3));
		secItems.add(new ItemStack(Material.COAL, 2));
		secondRank.put("items", secItems);

		// xp
		secondRank.put("xp", 50);

		rewardData.put("2", secondRank);

		/*
		 *  third Rank
		 */
		Map<String, Object> thirdRank = new LinkedHashMap<>();

		// items
		List<ItemStack> thiItems = new ArrayList<>();
		thiItems.add(new ItemStack(Material.OAK_WOOD, 1));
		thiItems.add(new ItemStack(Material.COAL, 1));
		thirdRank.put("items", thiItems);

		// xp
		thirdRank.put("xp", 30);

		rewardData.put("3", thirdRank);

		this.data.put("rewards", rewardData);
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
		return "rewards.yml";
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
