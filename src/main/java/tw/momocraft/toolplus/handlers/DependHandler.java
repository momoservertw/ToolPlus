package tw.momocraft.toolplus.handlers;

import tw.momocraft.toolplus.ToolPlus;
import tw.momocraft.toolplus.Commands;
import tw.momocraft.toolplus.TabComplete;
import tw.momocraft.toolplus.listeners.*;

public class DependHandler {

    public DependHandler() {
        registerEvents();
    }

    private void registerEvents() {
        ToolPlus.getInstance().getCommand("toolplus").setExecutor(new Commands());
        ToolPlus.getInstance().getCommand("toolplus").setTabCompleter(new TabComplete());

        ToolPlus.getInstance().getServer().getPluginManager().registerEvents(new VoidTool(), ToolPlus.getInstance());
    }
}
