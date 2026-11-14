# Minecraft Plugin: Ruby of the Nether

Ruby of the Nether is a Minecraft plugin that adds a custom Ruby Ore block, a custom Weak Ruby item, persistent placed-ore tracking, and a player command for obtaining Ruby Ore. The Ruby Ore is stronger than ordinary materials but weaker than Netherite in the plugin's progression concept.

## Install

1. Build the plugin with Java 21 and Maven:
   ```bash
   mvn clean package
   ```
2. Copy the generated `plugin.jar` file from the `target` directory into your Paper server's `plugins` directory.
3. Start or restart the server.
4. Confirm that the plugin is enabled in the server console.

Requirements:

- Paper 1.21.11 or a compatible server version
- Java 21
- A server with permission management configured if you want to customize command access

## Use

1. Join the server as an operator or as a player with the `rubyore.give` permission.
2. Run:
   ```text
   /rubyore
   ```
   This gives you one Ruby Ore block.
3. Give yourself a specific amount:
   ```text
   /rubyore 16
   ```
   The supported amount is between 1 and 64.
4. Place the Ruby Ore block.
5. Break the placed Ruby Ore block to receive one Weak Ruby.
6. Ruby Ore locations are saved in the plugin configuration so tracked blocks remain recognized after a server restart.

Only Ruby Ore items created by this plugin are tracked. Ordinary Netherite Blocks are not treated as Ruby Ore.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/56ca51c6-183f-44e1-a520-ee0a53d8986c?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=56ca51c6-183f-44e1-a520-ee0a53d8986c
