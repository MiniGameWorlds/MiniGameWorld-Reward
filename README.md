# Description
Give rewards to players when the minigame has finished

# Features
- Depend on [MiniGameWorld] (always use LATEST API)
- Also can use with [MiniGameWorld-Rank] to give reward by saved rank data
- Can customize rewards with config

# Rewards
- Item
- Xp

# Commands
- `/reward reload`: Reload all config from file
- `/reward save`: Save all data to file

# Soft depend on MiniGameWorldRank
- If use with [MiniGameWorld-Rank], can reward to players with saved rank data
- `in-data-reward.yml` config only works with [MiniGameWorld-Rank]

# Config
- `in-game-reward.yml` is only applied between players who played together in the minigame
- `in-data-reward.yml` is only applied between all saved rank data in the minigame config
- `reward.percent.<n>` percent(`<n>`) must be sorted in ascending order
- Can add, remove `rank` or `percent` to `reward.rank`, `reward.percent` list
- Can add, remove reward `item`, `xp`

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
- `reward.rank.<n>`: Give rewards to `<n>` rank (n >= 1)
- `reward.percent.<n>`: Give rewards if in `<n>` percent rank (1 <= n <= 100) (e.g. `4` rank of `10` rank = `40%` = not in `25%`, but in `50%`)
- `min-participant-percent`: Give reward if participants exist more than `min-participant-percent` percent of `max-player-count`
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
- `min-saved-data-count`: Give rewards if saved data count in the config is equal or bigger than the value
- `reward.rank.<n>`: Give rewards to `<n>` rank (n >= 1)
- `reward.percent.<n>`: Give rewards if in `<n>` percent rank (1 <= n <= 100)






[MiniGameWorld]: https://github.com/MiniGameWorlds/MiniGameWorld
[MiniGameWorld-Rank]: https://github.com/MiniGameWorlds/MiniGameWorld-Rank



