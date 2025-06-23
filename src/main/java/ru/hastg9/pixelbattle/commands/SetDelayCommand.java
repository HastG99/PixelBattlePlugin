package ru.hastg9.pixelbattle.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.hastg9.pixelbattle.PixelBattle;

public class SetDelayCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 1) {
            sender.sendMessage(PixelBattle.m("setdelay-error"));
            return true;
        }

        int delay;

        try {
            delay = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            sender.sendMessage(PixelBattle.m("not-a-number"));
            return true;
        }


        PixelBattle.setDelay(delay);
        sender.sendMessage(PixelBattle.m("setdelay-success"));

        return true;
    }
}
