package com.enderdevelopment.plugin;

import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public final class RubyOreCommand implements CommandExecutor, TabCompleter {
    private final RubyOreManager manager;

    public RubyOreCommand(RubyOreManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can receive Ruby Ore.");
            return true;
        }

        int amount = 1;
        if (args.length > 1) {
            player.sendMessage("Usage: /rubyore [amount]");
            return true;
        }
        if (args.length == 1) {
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                player.sendMessage("Amount must be a whole number between 1 and 64.");
                return true;
            }
            if (amount < 1 || amount > 64) {
                player.sendMessage("Amount must be a whole number between 1 and 64.");
                return true;
            }
        }

        player.getInventory().addItem(manager.createRubyOre(amount)).values()
                .forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
        player.sendMessage("You received " + amount + " Ruby Ore.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("1", "8", "16", "32", "64");
        }
        return Collections.emptyList();
    }
}
