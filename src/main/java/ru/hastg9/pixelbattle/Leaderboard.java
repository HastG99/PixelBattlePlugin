package ru.hastg9.pixelbattle;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Leaderboard {

    private static final List<Entry> entries = new ArrayList<>();
    private static final Map<String, Entry> entryMap = new HashMap<>();

    public static void update(String player, int blocks) {

        if(!entryMap.containsKey(player)) {
            Entry entry = new Entry(player, blocks);

            entries.add(entry);
            Collections.sort(entries);

            entryMap.put(player, entry);
            return;
        }

        Entry entry = entryMap.get(player);
        entry.setBlocks(blocks);
        Collections.sort(entries);
    }

    public static List<Entry> getTop(int places) {
        return entries.subList(0, places);
    }

    public static Entry get(int place) {
        if(place >= entries.size())
            return new Entry("Н/Д", 0);

        return entries.get(place);
    }


    static class Entry implements Comparable<Entry> {
        public final String player;
        public int blocks;


        public Entry(String player, int blocks) {
            this.player = player;
            this.blocks = blocks;
        }

        public void setBlocks(int blocks) {
            this.blocks = blocks;
        }

        @Override
        public int compareTo(@NotNull Leaderboard.Entry o) {
            return o.blocks - blocks;
        }
    }

}
