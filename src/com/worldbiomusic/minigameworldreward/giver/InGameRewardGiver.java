package com.worldbiomusic.minigameworldreward.giver;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.worldbiomusic.minigameworld.api.MiniGameAccessor;
import com.worldbiomusic.minigameworld.minigameframes.SoloBattleMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.SoloMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.TeamBattleMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.TeamMiniGame;
import com.worldbiomusic.minigameworld.minigameframes.helpers.MiniGameRankComparable;
import com.worldbiomusic.minigameworld.observer.MiniGameEventNotifier.MiniGameEvent;
import com.worldbiomusic.minigameworld.observer.MiniGameObserver;
import com.worldbiomusic.minigameworld.util.Setting;
import com.worldbiomusic.minigameworldreward.manager.InGameRewardManager;

/**
 * Give rewards by played players rank
 *
 */
public class InGameRewardGiver implements MiniGameObserver {
	private InGameRewardManager inGameRewardManager;

	public InGameRewardGiver(InGameRewardManager inGameRewardManager) {
		this.inGameRewardManager = inGameRewardManager;
	}

	@Override
	public void update(MiniGameAccessor minigame, MiniGameEvent event) {
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

			giveRankReward(minigame);

			givePercentReward(minigame);
		}
	}

	private void giveRankReward(MiniGameAccessor minigame) {
		List<? extends MiniGameRankComparable> rankList = minigame.getRank();

		for (int i = 0; i < rankList.size(); i++) {
			if (this.existRankReward(i + 1)) {
				for (Player p : rankList.get(i).getPlayers()) {
					// give rewards
					this.giveRankRewards(p, i + 1);

					p.sendMessage("Got rewards");
				}
			}
		}
	}

	private void givePercentReward(MiniGameAccessor minigame) {
		List<? extends MiniGameRankComparable> rankList = minigame.getRank();

		for (int i = 0; i < rankList.size(); i++) {
			int rankPercent = (int) (((i + 1) / (double) rankList.size()) * 100);

			for (String percentStr : getPercentList()) {
				int dataPercent = Integer.parseInt(percentStr);

				// inner percent
				if (rankPercent <= dataPercent) {
					// give rewards
					for (Player p : rankList.get(i).getPlayers()) {
						this.givePercentRewards(p, dataPercent);

						p.sendMessage("Got rewards: " + rankPercent);
					}

					break;
				}
			}
		}
	}

	private boolean checkMiniGameType(MiniGameAccessor minigame) {
		@SuppressWarnings("unchecked")
		Map<String, Object> activeTypes = (Map<String, Object>) this.inGameRewardManager.getData().get("active-types");

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
		int maxPlayerCount = (int) minigame.getSettings().get(Setting.MINIGAMES_MAX_PLAYER_COUNT);
		int leavingPlayerCount = minigame.getPlayers().size();
		double participantPercent = (double) leavingPlayerCount / maxPlayerCount;

		double minPercent = (int) this.inGameRewardManager.getData().get("min-participant-percent") / 100.0;

		minigame.getPlayers().forEach(p -> p.sendMessage("participant check: " + (minPercent <= participantPercent)));

		return minPercent <= participantPercent;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getRewardData() {
		return (Map<String, Object>) this.inGameRewardManager.getData().get("reward");
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
