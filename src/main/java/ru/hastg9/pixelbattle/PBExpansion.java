package ru.hastg9.pixelbattle;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public class PBExpansion extends PlaceholderExpansion {

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("delay")) {
            return String.valueOf(TimeUnit.NANOSECONDS.toSeconds(PlayerListener.cooldownUtil.getCooldown(player.getUniqueId())));
        }

        if(params.equalsIgnoreCase("total")) {
            return String.valueOf(PixelBattle.TOTAL_PLACED);
        }

        if(params.equalsIgnoreCase("totalk")) {
            return String.valueOf(PixelBattle.TOTAL_PLACED / 1000);
        }

        if(params.equalsIgnoreCase("count")) {
            return String.valueOf(PixelBattle.getBlocks(player.getName()));
        }

        try {
            int place = Integer.parseInt(params);
            Leaderboard.Entry entry = Leaderboard.get(place);

            return String.format(PixelBattle.m("entry-format"), entry.player, entry.blocks);
        } catch (NumberFormatException ex) {
            return null;
        }

        // Placeholder is unknown by the Expansion
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pixelbattle";
    }

    @Override
    public @NotNull String getAuthor() {
        return "hastg99";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }
}
