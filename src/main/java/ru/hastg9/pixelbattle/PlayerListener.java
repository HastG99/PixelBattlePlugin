package ru.hastg9.pixelbattle;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.hastg9.pixelbattle.hook.CoreProtectHook;
import ru.hastg9.pixelbattle.util.CooldownUtil;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {

    public static final CooldownUtil<UUID> cooldownUtil = new CooldownUtil<>();


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(event.getPlayer().hasPermission("pb.bypass")) return;

        event.setCancelled(true);
    }




    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(player.hasPermission("pb.bypass") || block == null) return;

        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if(!PixelBattle.isPaint(block.getType()))
            return;

        event.setCancelled(true);

        Material itemType = item.getType();

        if(!PixelBattle.isPaint(itemType) || block.getType() == itemType)
            return;

        long delay = cooldownUtil.updateCooldown(player.getUniqueId(), PixelBattle.getDelay());

        if(delay > 0) {
            player.sendActionBar(String.format(PixelBattle.m("delay-message"), TimeUnit.NANOSECONDS.toSeconds(delay)));
            return;
        }

        block.setType(itemType);

        for (Material material : PixelBattle.getPaints()) {
            player.setCooldown(material, PixelBattle.getDelay() * 20);
        }

        if(PixelBattle.isSupportCoreProtect())
            CoreProtectHook.logPlace(player.getName(), block.getLocation(), itemType, block.getBlockData());

        PixelBattle.blockPlaced(player.getName());
        player.sendMessage(PixelBattle.m("placed"));
    }

}
