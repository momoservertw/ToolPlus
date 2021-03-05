package tw.momocraft.toolplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.toolplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.api.CorePlusAPI;

public class ToolPlus extends JavaPlugin {
    private static ToolPlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&fhas been Disabled.");
    }

    public static ToolPlus getInstance() {
        return instance;
    }
}