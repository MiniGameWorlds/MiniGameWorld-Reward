# Description
- Example `third-party` plugin of `MiniGameWorld`
- Can give rewards to participants when minigame has finished
- [Release](https://github.com/MiniGameWorlds/MiniGameWorld/discussions/6)

# Rewards
- Items
- Xp

# Features
- Softdepends [MiniGameWorldRank]()

# With MiniGameWorldRank

# Config
## in-game-reward.yml
```yaml
data:
  reward:
    rank:
      '1':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 10
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 10
        xp: 100
      '2':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 7
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 7
        xp: 50
      '3':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 5
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 5
        xp: 30
    percent:
      '25':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 4
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 4
        xp: 20
      '50':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 3
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 3
        xp: 15
      '75':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 2
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 2
        xp: 10
      '100':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
        xp: 5
  min-participant-percent: 50
  active-types:
    solo: false
    solo-battle: true
    team: false
    team-battle: true

```
- `rewards`: Reward items (can add more rank)
- `min-participant-percent`: Give reward if participants more than `min-participant-percent` percent of `max-player-count`
- `active-types.<game-type>`: Only give reward to minigame which is set to true


## in-data-reward.yml
```yaml
data:
  min-saved-data-count: 10
  reward:
    rank:
      '1':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 10
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 10
        xp: 100
      '2':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 7
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 7
        xp: 50
      '3':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 5
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 5
        xp: 30
    percent:
      '25':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 4
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 4
        xp: 20
      '50':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 3
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 3
        xp: 15
      '75':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
          amount: 2
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
          amount: 2
        xp: 10
      '100':
        items:
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: OAK_WOOD
        - ==: org.bukkit.inventory.ItemStack
          v: 2730
          type: COAL
        xp: 5
```












