package ru.hastg9.pixelbattle;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataStorage {

    private final File file;
    private final FileConfiguration config;

    public DataStorage(String name) {
        this.file =  new File(PixelBattle.INSTANCE.getDataFolder(), name);;
        this.config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void loadPlayers(DataCallback callback) {
        ConfigurationSection section = config.getConfigurationSection("stats");

        if(section == null) return;

        for (String player : section.getKeys(false)) {
            int blocks = config.getInt("stats." + player);

            callback.execute(player, blocks);
        }
    }

    public void updateTotal(int total) {
        config.set("total", total);
    }

    public int getTotal() {
        return config.getInt("total");
    }

    public void updateStats(String player, int blocks) {
        config.set("stats." + player, blocks);
    }

    public synchronized void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    interface DataCallback {
        void execute(String player, int blocks);
    }



}
