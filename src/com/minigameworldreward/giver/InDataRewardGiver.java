package com.minigameworldreward.giver;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.minigameworld.api.MiniGameAccessor;
import com.minigameworld.events.minigame.MiniGameFinishEvent;
import com.minigameworld.frames.helpers.MiniGameRank;
import com.minigameworld.util.Utils;
import com.minigameworldrank.api.MiniGameWorldRank;
import com.minigameworldrank.data.RankData;
import com.minigameworldreward.MiniGameWorldRewardMain;
import com.minigameworldreward.manager.InDataRewardManager;


/**
 * Give rewards by rank data
 *
 */
public class InDataRewardGiver implements Listener {
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

	/**
	 * Give rewards when finish
	 * 
	 * @param e Event when a minigame has finished
	 */
	@EventHandler
	public void onMiniGameFinish(MiniGameFinishEvent e) {
		MiniGameAccessor minigame = e.getMiniGame();

		if (!isRankDataExist()) {
			return;
		}

		if (!checkMinSavedDataCount(minigame)) {
			return;
		}

		List<? extends MiniGameRank> compList = minigame.rank();
		for (MiniGameRank comp : compList) {
			List<Player> players = comp.getPlayers();
			int rank = getRankFromData(minigame, players);

			// give rank reward
			giveRankReward(players, rank);

			// give percent reward
			givePercentReward(players, rank, compList.size());
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
					givePercentRewards(p, dataPercent);
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
