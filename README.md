# Shock Number Formats
A simple but life altering change to the way large numbers can be handled on your server. 

Allows players to enter suffixed numbers like 1m, 1.1k, and 56.7b in any command for any plugin regardless of support. Simply add the jar, and all plugins will now support these formats in commands.
- `/eco give Savage 1.1B` to `1100000000`
- `/points give Notch 1.5M` to `1500000`
- `/pay cra 6.8k` to `6800`

The Default configuration includes the following formats:
- Thousands, `K` or `k`
- Millions, `M` or `m`
- Billions, `B` or `b`
- Trillions, `T` or `t`
- Quadrillions, `Q` or `q`
- Sextillion, `S` or `s`
- Octillions, `O` or `o`
- Nonillions, `N` or `n`
- Decillions, `D` or `d`
- Undecillions, `U` or `u`p

## Compatibility
This project should be compatible with any server running Paper, Spigot, or Bukkit. On any version (theoretically) from 1.8 to 1.21.5. However, it has only been tested on 1.21.4 and 1.21.5. ALL plugins which do not use a packet-level command framework should be supported automatically.

## Safety
It is a simple plugin that modifies the way numbers are displayed in chat, and does not affect any other functionality of the server. If a number is too large, or not valid, the plugin ultimately handling the command will still validate & fail as if the player entered all the zeros required for their input number. This has no effect on permission checks, balances, limits, or anything else.
## Installation
1. Install Shock Number Formats plugin by downloading the latest release from the [GitHub Releases](https://github.com/Savag3life/ShockNumberFormats/releases)
3. **Restart**, don't reload! This plugin requires a full server restart to function properly.
4. Enjoy! Make sure you configure any blacklisted commands in the `config.yml` file.
