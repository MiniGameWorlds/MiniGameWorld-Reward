# Description
- Example `third-party` plugin of `MiniGameWorld`
- Can give rewards to participants when minigame has finished

# Rewards
- Items
- Xp

# Config
```yaml
data:
  rewards:
    '1':
      items:
      - ==: org.bukkit.inventory.ItemStack
        v: 2730
        type: OAK_WOOD
        amount: 5
      - ==: org.bukkit.inventory.ItemStack
        v: 2730
        type: COAL
        amount: 3
      xp: 100
    '2':
      items:
      - ==: org.bukkit.inventory.ItemStack
        v: 2730
        type: OAK_WOOD
        amount: 3
      - ==: org.bukkit.inventory.ItemStack
        v: 2730
        type: COAL
        amount: 2
      xp: 50
    '3':
      items:
      - ==: org.bukkit.inventory.ItemStack
        v: 2730
        type: OAK_WOOD
      - ==: org.bukkit.inventory.ItemStack
        v: 2730
        type: COAL
      xp: 30
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











