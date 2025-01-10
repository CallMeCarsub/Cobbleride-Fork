## Changelog

***

### v0.2.0 (Release TBD)

#### Updated controls system!

- Reworked the previous controls system to now anchor onto the keybinds provided by Cobblemon and vanilla Minecraft.
  This means there are no longer any separate keybinds for the mod itself, but this should allow for better
  out-of-the-box functionality without a reliance on any mods to resolve keybind conflicts! The updated controls are as
  follows:
    - Jump to ascend / fly (default Space)
    - Sneak to descend / dive (default LShift)
    - Sprint to... sprint! But with STYLE! (default LCtrl)
    - Throw Selected Pokemon from Cobblemon to dismount (default R). Kinda like when you want to get a shoulder-mounted
      Pokemon off your shoulder, only YOU'RE the one on THEIR shoulder!
- Reduced the number of packets sent between client and server! Which SHOULD mean servers have less data to juggle,
  which should keep TPS healthy. Haven't had any evidence to suggest that this could be a potential issue, but it never
  hurts to take some preventative measures!
- The above change did introduce a conflict, though, since the Throw Selected Pokemon key is also used for battles and
  interactions. The resulting side effects included being unable to dismount from your Ride Pokemon while involved in a
  battle. This has been fixed by prioritizing self-dismounting from any actively selected Ride Pokemon. You also
  prioritize dismounting from someone else's Pokemon. This should hopefully improve user-friendliness for the controls
  while still allowing you opportunities to ride your Pokemon into battle! (Just make sure it isn't your actively
  selected Pokemon when you start, it will work.)
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
- Set default underwater modifier to 1.0. Underwater Pokemon were a bit TOO fast.
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