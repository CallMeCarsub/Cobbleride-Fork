## Changelog

***

### v0.2.0 (Release TBD)

#### Updated controls system!

- Reworked the previous controls system to now anchor onto the keybinds provided by Cobblemon and vanilla Minecraft.
  This means there are no longer any separate keybinds for the mod itself, but this should allow for better
  out-of-the-box functionality without a reliance on any mods to resolve keybind conflicts! The updated controls are as
  follows:
    - "Jump" to ascend, fly... and jump! (default Space)
    - "Sneak" to descend and dive (default LShift)
    - "Sprint" to... sprint! But with STYLE! (default LCtrl)
    - "Throw Selected Pokemon" while sneaking to dismount (default LShift + R) if your Ride Pokemon is selected. Kinda
      like when you want to get a shoulder-mounted Pokemon off your shoulder, only YOU'RE the one on THEIR shoulder!
      Except that you also have to be sneaking. This is mainly to ensure that dismounting is a very deliberate action,
      while also freeing up the "Throw Selected Pokemon" key for other actions such as starting battles or interacting
      with other players while mounted. Note that you also cannot recall your Pokemon unless you are dismounted from it.
- Reduced the number of packets sent between client and server as a result of the changes above. Which SHOULD mean
  servers have less data to juggle, which should keep TPS healthy. Haven't had any evidence to suggest that this could
  be a potential issue, but it never hurts to take some preventative measures!
- Adjusted raytracing for Cobblemon such that it will ignore mounts when drawing a line between you and a target for
  certain interactions. This works for vanilla mounts as well! So now even the largest of Pokemon will not block your
  ability to start a battle!
- Adjusted Ride Pokemon interaction to now let you mount even while holding an item! This only applies to items that do
  not have some special effect on the Pokemon when used, such as potions or evolution items.
- Fixed an issue where server configs were not being correctly synced to connecting clients.
- Added new movement-type option for diving and flying, where you will move in the direction your camera is facing in,
  not unlike creative flight. Ascending and descending via key presses will still work, but this mode allows for
  vertical movement without needing to use them beyond initializing flying or diving.
- Dimension blacklisting! All dimensions are allowed by default, but if you wish to restrict riding in specific
  dimensions, just add their resource locations (e.g. "minecraft:the_nether") to the list to prevent riding in those
  dimensions!
- Added safety check to make sure you can't mount a Ride Pokemon that belongs to an NPC entity.
- Added checks to make sure that Ride Pokemon are not dismounted if swapped out in battle (unless they faint). Ride
  Pokemon will still be recalled, however, at the start and end of any level adjusted trainer battle. This is for safety
  reasons, so I have no plans of changing this.
- Added checks to make sure that Ride Pokemon cannot be evolved while mounted, nor can players mount Pokemon while they
  are evolving.
- Reduced default underwater modifier from 2.0 to 1.0. Underwater Pokemon were a bit TOO fast.
- Increased maximum limit for all speed-related modifiers from 5.0 to 100.0. Because some folks WANT to go too fast.
- Increased maximum limit for ride speed limit from 120.0 to 420.0. Because you'll be blazing at those speeds.
- Added option to toggle off sprinting on land, so that sprinting can be more selectively toggled for each medium.
- Set default state for sprinting in air to true. In hindsight, it's more reasonable to make this consistent across all
  mediums and allowing servers to decide if any of these need to be selectively disabled.
- Fixed Torterra's offset. Added Rhyhorn. More planned, just wanted to square these two away since they were on my mind.
- Added compatibility with Pet Your Cobblemon. Right-clicking to mount will work normally while the mod's interaction
  mode is disabled, and will be disabled while interaction mode is enabled.
- Minor adjustments throughout the code, to make things either more readable or a touch more standardized.

***

### v0.1.0 (Released January 7, 2025)

#### Initial release!

- Base feature set for mounting and dismounting, with initial support for 140 Ride Pokemon!
- Icons in the summary, PC and Pokedex screens mark which Pokemon can be ridden, while the Rideable subset in the
  Pokedex shows all available mounts!
- Movement speed that scales with Speed, with config options!
- Sprint with your Ride Pokemon, with config options!
- Toggleable shared water breathing and midair dismount!
- Global and individual modifiers for movement speed, whether in general or across specific mediums!
- Config for speed limiting, for server safety!
- Data registry and addon support for adding and updating Ride Pokemon!