package com.worldbiomusic.minigameworldreward;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworld.minigameframes.SoloBattleMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.SoloMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.TeamBattleMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.TeamMiniGame;
import com.worldbiomusic.minigameworld.observer.MiniGameEventNotifier.MiniGameEvent;
import com.worldbiomusic.minigameworld.observer.MiniGameObserver;

public class RewardManager implements MiniGameObserver {
	private RewardDataManager rewardDataManager;

	public RewardManager(RewardDataManager rewardDataManager) {
		this.rewardDataManager = rewardDataManager;
	}

	@Override
	public void update(MiniGameEvent event, MiniGameAccessor minigame) {
		// give rewards when finish
		if (event == MiniGameEvent.FINISH) {
			// check type
			if (!this.checkMiniGameType(minigame)) {
				return;
			}

			// check participant percent
			if (!this.checkParticipantPercent(minigame)) {
				return;
			}

			List<Entry<Player, Integer>> entries = minigame.getScoreRank();

			for (int i = 0; i < entries.size(); i++) {
				if (this.existRankReward(i + 1)) {
					Player p = entries.get(i).getKey();

					// items
					this.giveRewardItems(p, i + 1);
					// xp
					this.giveRewardXp(p, i + 1);

					p.sendMessage("Got rewards");
				}
			}
		}

	}

	private boolean checkMiniGameType(MiniGameAccessor minigame) {
		@SuppressWarnings("unchecked")
		Map<String, Object> activeTypes = (Map<String, Object>) this.rewardDataManager.getData().get("active-types");

		Class<?> clazz = minigame.getClassType();
		if (SoloMiniGame.class.isAssignableFrom(clazz)) {
			return (boolean) activeTypes.get("solo");
		} else if (SoloBattleMiniGame.class.isAssignableFrom(clazz)) {
			return (boolean) activeTypes.get("solo-battle");
		} else if (TeamMiniGame.class.isAssignableFrom(clazz)) {
			return (boolean) activeTypes.get("team");
		} else if (TeamBattleMiniGame.class.isAssignableFrom(clazz)) {
			return (boolean) activeTypes.get("team-battle");
		}
		return false;
	}

	private boolean checkParticipantPercent(MiniGameAccessor minigame) {
		int maxPlayerCount = (int) minigame.getSettings().get("max-player-count");
		int leavingPlayerCount = minigame.getPlayers().size();
		double participantPercent = (double) leavingPlayerCount / maxPlayerCount;

		double minPercent = (int) this.rewardDataManager.getData().get("min-participant-percent") / 100.0;

		return minPercent <= participantPercent;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getRewardData(int rank) {
		Map<String, Object> rankReward = (Map<String, Object>) this.rewardDataManager.getData().get("rewards");
		return (Map<String, Object>) rankReward.get("" + rank);
	}

	private boolean existRankReward(int rank) {
		return this.getRewardData(rank) != null;
	}

	@SuppressWarnings("unchecked")
	private void giveRewardItems(Player p, int rank) {
		List<ItemStack> items = (List<ItemStack>) this.getRewardData(rank).get("items");
		items.forEach(item -> p.getInventory().addItem(item));
	}

	private void giveRewardXp(Player p, int rank) {
		int xp = (int) this.getRewardData(rank).get("xp");
		p.giveExp(xp);
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
//
//
//
