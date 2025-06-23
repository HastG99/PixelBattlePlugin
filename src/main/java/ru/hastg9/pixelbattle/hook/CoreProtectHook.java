package ru.hastg9.pixelbattle.hook;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;
import ru.hastg9.pixelbattle.PixelBattle;

public class CoreProtectHook {

    private static CoreProtectAPI api;

    public static void init(PixelBattle instance) {
        api = initCoreProtect(instance);

        if (api != null){ // Ensure we have access to the API
            api.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }
    }


    public static void logPlace(String user, Location location, Material material, BlockData data) {
        api.logPlacement(user, location, material, data);
    }

    private static CoreProtectAPI initCoreProtect(PixelBattle instance) {
        Plugin plugin = instance.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            return null;
        }

        return CoreProtect;
    }

    public static CoreProtectAPI getAPI() {
        return api;
    }


}
