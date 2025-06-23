package ru.hastg9.pixelbattle.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CooldownUtil<T> {

    private final Map<T, Long> cooldowns = new ConcurrentHashMap<>();

    public long updateCooldown(T player, long time) {
        if(cooldowns.containsKey(player)) {
            long left = cooldowns.get(player) - System.nanoTime();

            if(left >= 0) return left;
        }

        cooldowns.put(player, System.nanoTime() + TimeUnit.SECONDS.toNanos(time));

        return 0;
    }

    public long getCooldown(T player) {
        if(cooldowns.containsKey(player)) {
            long left = cooldowns.get(player) - System.nanoTime();
            if(left >= 0) return left;
        }

        return 0;
    }

    public void reset() {
        cooldowns.clear();
    }

    public void reset(T player) {
        cooldowns.remove(player);
    }

}
