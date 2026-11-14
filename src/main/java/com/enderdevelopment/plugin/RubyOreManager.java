package com.enderdevelopment.plugin;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class RubyOreManager implements Listener {
    private final Main plugin;
    private final NamespacedKey rubyOreKey;
    private final NamespacedKey weakRubyKey;
    private final Set<String> placedRubyOre = new HashSet<>();

    public RubyOreManager(Main plugin) {
        this.plugin = plugin;
        rubyOreKey = plugin.key("ruby_ore");
        weakRubyKey = plugin.key("weak_ruby");
        loadLocations();
    }

    public ItemStack createRubyOre(int amount) {
        ItemStack item = new ItemStack(Material.NETHERITE_BLOCK, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_RED + "Ruby Ore");
            meta.setLore(java.util.List.of(ChatColor.GRAY + "A powerful ore forged from Netherite.",
                    ChatColor.RED + "Mine it to uncover a Weak Ruby."));
            meta.getPersistentDataContainer().set(rubyOreKey, PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack createWeakRuby() {
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "Weak Ruby");
            meta.setLore(java.util.List.of(ChatColor.GRAY + "A small ruby crystal recovered from Ruby Ore."));
            meta.getPersistentDataContainer().set(weakRubyKey, PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onRubyOrePlace(BlockPlaceEvent event) {
        if (isRubyOre(event.getItemInHand())) {
            placedRubyOre.add(locationKey(event.getBlock().getLocation()));
            saveLocations();
        }
    }

    @EventHandler
    public void onRubyOreBreak(BlockBreakEvent event) {
        String locationKey = locationKey(event.getBlock().getLocation());
        if (!placedRubyOre.remove(locationKey)) {
            return;
        }

        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), createWeakRuby());
        saveLocations();
    }

    public boolean isRubyOre(ItemStack item) {
        if (item == null || item.getType() != Material.NETHERITE_BLOCK) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(rubyOreKey, PersistentDataType.BYTE);
    }

    public void saveLocations() {
        plugin.getConfig().set("placed-ruby-ore", placedRubyOre.stream().toList());
        plugin.saveConfig();
    }

    private void loadLocations() {
        placedRubyOre.addAll(plugin.getConfig().getStringList("placed-ruby-ore"));
    }

    private String locationKey(Location location) {
        return location.getWorld().getUID() + ":" + location.getBlockX() + ":"
                + location.getBlockY() + ":" + location.getBlockZ();
    }
}
