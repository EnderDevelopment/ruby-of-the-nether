package com.enderdevelopment.plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private RubyOreManager rubyOreManager;

    @Override
    public void onEnable() {
        rubyOreManager = new RubyOreManager(this);
        getServer().getPluginManager().registerEvents(rubyOreManager, this);
        RubyOreCommand command = new RubyOreCommand(rubyOreManager);
        if (getCommand("rubyore") != null) {
            getCommand("rubyore").setExecutor(command);
            getCommand("rubyore").setTabCompleter(command);
        }
    }

    @Override
    public void onDisable() {
        if (rubyOreManager != null) {
            rubyOreManager.saveLocations();
        }
    }

    public NamespacedKey key(String value) {
        return new NamespacedKey(this, value);
    }
}
