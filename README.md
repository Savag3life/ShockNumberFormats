# Shock Number Formats
A simple but life altering change to the way large numbers can be handled on your server. Converting numbers in the suffixed format, for example:
- `1.1B` to `1100000000`
- `1.5M` to `1500000`
- `6.8k` to `6800`

## Compatibility
This project should be compatible with any server running Paper, Spigot, or Bukkit. On any version (theoretically) from 1.8 to 1.20.2. However, it has only been tested on 1.20.2 and 1.20.1. ALL plugins which do not use a packet-level command framework should be supported automatically.

## Safety
Given the nature of this type of plugin, some may be concerned about the safety of their server. This plugin does not modify any core server files, and does not require any special permissions to run. It is a simple plugin that modifies the way numbers are displayed in chat, and does not affect any other functionality of the server. If a number is too large, or not valid, the plugin ultimately handling the command will still validate & fail as normal. The worse case scenario is that the plugin will not be able to convert the number, and the command will fail as its unable to parse the suffixed number into an actual number. This has no effect on permission checks, balances, limits, or anything else.

## Installation
1. Install Shock Number Formats
2. Install PacketEvents
3. Restart, dont reload.
4. Enjoy!