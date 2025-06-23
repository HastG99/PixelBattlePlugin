package ru.hastg9.pixelbattle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hastg9.pixelbattle.commands.SetDelayCommand;
import ru.hastg9.pixelbattle.hook.CoreProtectHook;
import ru.hastg9.pixelbattle.util.RGBUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class PixelBattle extends JavaPlugin implements Listener {

    private static final Set<String> hiddenPlayers = new HashSet<>();

    public static PixelBattle INSTANCE;

    private static ItemStack[] contents;
    private static final Set<Material> paints = new HashSet<>();
    private static DataStorage storage;

    private static final Map<String, Integer> players = new HashMap<>();
    public static int TOTAL_PLACED = 0;

    private static boolean supportCoreProtect = false;

    private static int delay ;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        saveDefaultConfig();

        storage = new DataStorage("data.yml");

        TOTAL_PLACED = storage.getTotal();
        storage.loadPlayers(PixelBattle::updateBlocks);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PBExpansion().register();
        }

        if(Bukkit.getPluginManager().getPlugin("CoreProtect") != null) {
            CoreProtectHook.init(this);
            supportCoreProtect = true;
        }

        delay = INSTANCE.getConfig().getInt("delay");

        loadPaints();

        getCommand("setdelay").setExecutor(new SetDelayCommand());

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> storage.save(), 600, 600);
    }

    public static String m(String path) {
        return RGBUtil.color(INSTANCE.getConfig().getString(path));
    }

    public void loadPaints() {
        paints.add(Material.RED_WOOL);
        paints.add(Material.ORANGE_WOOL);
        paints.add(Material.YELLOW_WOOL);
        paints.add(Material.LIME_WOOL);
        paints.add(Material.GREEN_WOOL);
        paints.add(Material.BLUE_WOOL);
        paints.add(Material.LIGHT_BLUE_WOOL);
        paints.add(Material.WHITE_WOOL);
        paints.add(Material.BLACK_WOOL);

        paints.add(Material.GRAY_WOOL);
        paints.add(Material.LIGHT_GRAY_WOOL);
        paints.add(Material.CYAN_WOOL);
        paints.add(Material.PURPLE_WOOL);
        paints.add(Material.BROWN_WOOL);
        paints.add(Material.PINK_WOOL);

        contents = new ItemStack[paints.size()];

        int i = 0;

        for (Material material : paints)
            contents[i++] = new ItemStack(material);
    }


    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setInvulnerable(true);

        if(player.hasPermission("pb.bypass")) return;

        //hide(event.getPlayer());

        player.setInvisible(true);
        player.setCollidable(false);
        player.setFlySpeed(0.3f);

        Inventory inventory = player.getInventory();
        inventory.clear();

        for (int i = 0; i < contents.length; i++) {
            inventory.setItem(i, contents[i]);
        }
    }

    @EventHandler()
    public void onQuit(PlayerJoinEvent event) {
        hiddenPlayers.remove(event.getPlayer().getName());
    }

    public static int getDelay() {
        return delay;
    }

    public static void setDelay(int delay) {
        PixelBattle.delay = delay;
    }

    public static void blockPlaced(String player) {
        TOTAL_PLACED++;
        storage.updateTotal(TOTAL_PLACED);

        if(players.containsKey(player)) {
            updateBlocks(player, players.get(player) + 1);
            return;
        }

        updateBlocks(player, 1);
    }

    public static boolean isSupportCoreProtect() {
        return supportCoreProtect;
    }

    private static void updateBlocks(String player, int blocks) {
        players.put(player, blocks);
        Leaderboard.update(player, blocks);
        storage.updateStats(player, blocks);
    }

    public static int getBlocks(String player) {
        return players.getOrDefault(player, 0);
    }

    public static boolean isPaint(Material type) {
        return paints.contains(type);
    }

    public static Set<Material> getPaints() {
        return paints;
    }

    @Override
    public void onDisable() {
        storage.save();
    }

}
