package com.worldbiomusic.minigameworldreward.giver;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.wbm.plugin.util.Utils;
import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworld.minigameframes.helpers.MiniGameRankResult;
import com.worldbiomusic.minigameworld.observer.MiniGameEventNotifier.MiniGameEvent;
import com.worldbiomusic.minigameworld.observer.MiniGameObserver;
import com.worldbiomusic.minigameworldrank.api.MiniGameWorldRank;
import com.worldbiomusic.minigameworldrank.data.RankData;
import com.worldbiomusic.minigameworldreward.MiniGameWorldRewardMain;
import com.worldbiomusic.minigameworldreward.manager.InDataRewardManager;

/**
 * Give rewards by rank data
 *
 */
public class InDataRewardGiver implements MiniGameObserver {
	private InDataRewardManager inDataRewardManager;
	private MiniGameWorldRank minigameRank;

	public InDataRewardGiver(InDataRewardManager inDataRewardManager) {
		this.inDataRewardManager = inDataRewardManager;

		// load MiniGameWorldRank if exist (softdepend)
		if (isRankDataExist()) {
			Utils.info("MiniGameWorld-Reward loaded with MiniGameWorld-Rank");
			this.minigameRank = MiniGameWorldRank.create();
		} else {
			Utils.info("MiniGameWorld-Reward loaded without MiniGameWorld-Rank");
		}
	}

	private boolean isRankDataExist() {
		return MiniGameWorldRewardMain.getInstance().getServer().getPluginManager()
				.isPluginEnabled("MiniGameWorld-Rank");
	}

	@Override
	public void update(MiniGameAccessor minigame, MiniGameEvent event) {
		if (!isRankDataExist()) {
			return;
		}

		if (event == MiniGameEvent.FINISH) {
			if (!checkMinSavedDataCount(minigame)) {
				return;
			}

			List<? extends MiniGameRankResult> compList = minigame.getRank();
			for (MiniGameRankResult comp : compList) {
				List<Player> players = comp.getPlayers();
				int rank = getRankFromData(minigame, players);

				// give rank reward
				giveRankReward(players, rank);

				// give percent reward
				givePercentReward(players, rank, compList.size());
			}
		}
	}

	private void giveRankReward(List<Player> players, int rank) {
		if (existRankReward(rank)) {
			players.forEach(p -> {
				giveRankRewards(p, rank);
			});
		}
	}

	private void givePercentReward(List<Player> players, int rank, int allDataCount) {
		int rankPercent = (int) ((rank / (double) allDataCount) * 100);
		for (String percentStr : getPercentList()) {
			int dataPercent = Integer.parseInt(percentStr);
			// inner percent
			if (rankPercent <= dataPercent) {
				players.forEach(p -> {
					givePercentRewards(p, rankPercent);
				});

				break;
			}
		}
	}

	private boolean checkMinSavedDataCount(MiniGameAccessor minigame) {
		int dataCount = this.minigameRank.getRankDataList(minigame).size();
		int minCount = (int) this.inDataRewardManager.getData().get("min-saved-data-count");
		return dataCount >= minCount;
	}

	private int getRankFromData(MiniGameAccessor minigame, List<Player> players) {
		List<RankData> rankDataList = this.minigameRank.getRankDataList(minigame);
		for (RankData rankData : rankDataList) {
			if (rankData.isSamePlayers(players)) {
				return rankData.getRank();
			}
		}

		// will never return -1, because called after rank data saved to config
		return -1;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getRewardData() {
		return (Map<String, Object>) this.inDataRewardManager.getData().get("reward");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getRankReward(int rank) {
		Map<String, Object> rankReward = (Map<String, Object>) getRewardData().get("rank");
		return (Map<String, Object>) rankReward.get("" + rank);
	}

	private boolean existRankReward(int rank) {
		return this.getRankReward(rank) != null;
	}

	private void giveRankRewards(Player p, int rank) {
		// items
		@SuppressWarnings("unchecked")
		List<ItemStack> items = (List<ItemStack>) this.getRankReward(rank).get("items");
		items.forEach(item -> p.getInventory().addItem(item));

		// xp
		int xp = (int) this.getRankReward(rank).get("xp");
		p.giveExp(xp);
	}

	@SuppressWarnings("unchecked")
	private Set<String> getPercentList() {
		return ((Map<String, Object>) getRewardData().get("percent")).keySet();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getPercentReward(int percent) {
		Map<String, Object> percentReward = (Map<String, Object>) getRewardData().get("percent");
		return (Map<String, Object>) percentReward.get("" + percent);
	}

	private void givePercentRewards(Player p, int percent) {
		// items
		@SuppressWarnings("unchecked")
		List<ItemStack> items = (List<ItemStack>) this.getPercentReward(percent).get("items");
		items.forEach(item -> p.getInventory().addItem(item));

		// xp
		int xp = (int) this.getPercentReward(percent).get("xp");
		p.giveExp(xp);
	}

}
