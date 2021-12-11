package com.worldbiomusic.minigameworldreward.giver;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworld.minigameframes.helpers.MiniGameRankComparable;
import com.worldbiomusic.minigameworldrank.api.MiniGameWorldRank;
import com.worldbiomusic.minigameworldrank.data.RankData;
import com.worldbiomusic.minigameworldrank.observer.MiniGameRankNotifier.RankEvent;
import com.worldbiomusic.minigameworldrank.observer.MiniGameRankObserver;
import com.worldbiomusic.minigameworldreward.MiniGameWorldRewardMain;
import com.worldbiomusic.minigameworldreward.manager.InDataRewardManager;

/**
 * Give rewards by rank data
 *
 */
public class InDataRewardGiver implements MiniGameRankObserver {
	private InDataRewardManager inDataRewardManager;
	private MiniGameWorldRank minigameRank;

	public InDataRewardGiver(InDataRewardManager inDataRewardManager) {
		this.inDataRewardManager = inDataRewardManager;

		// load MiniGameWorldRank if exist (softdepend)
		if (isRankDataExist()) {
			System.out.println("@@@@@@@@@@@@@@ YES @@@@@@@@@@@@@@@@");
			this.minigameRank = MiniGameWorldRank.create();
			// register this to rank observer
			this.minigameRank.registerRankObserver(this);
		} else {
			System.out.println("@@@@@@@@@@@@@@ NO @@@@@@@@@@@@@@@@");
		}
	}

	private boolean isRankDataExist() {
		return MiniGameWorldRewardMain.getInstance().getServer().getPluginManager()
				.isPluginEnabled("MiniGameWorld-Rank");
	}

	@Override
	public void update(MiniGameAccessor minigame, RankEvent event) {
		if (event == RankEvent.AFTER_DATA_SAVED) {
			if (!checkMinSavedDataCount(minigame)) {
				return;
			}

			List<? extends MiniGameRankComparable> compList = minigame.getRank();
			for (MiniGameRankComparable comp : compList) {
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
				p.sendMessage("Got rewards from rank data");
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
					p.sendMessage("Got rewards from percent data");
				});
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
	private Map<String, Object> getRankReward(int rank) {
		Map<String, Object> rankReward = (Map<String, Object>) this.inDataRewardManager.getData().get("reward.rank");
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
		return ((Map<String, Object>) this.inDataRewardManager.getData().get("reward.percent")).keySet();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getPercentReward(int percent) {
		Map<String, Object> percentReward = (Map<String, Object>) this.inDataRewardManager.getData()
				.get("reward.percent");
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
