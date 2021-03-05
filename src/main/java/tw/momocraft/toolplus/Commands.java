package tw.momocraft.toolplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.toolplus.handlers.ConfigHandler;
import tw.momocraft.toolplus.listeners.VoidTool;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        int length = args.length;
        if (length == 0) {
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.use")) {
                CorePlusAPI.getLangManager().sendMsg("", sender, "");
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                        ConfigHandler.getConfigPath().getMsgTitle(), sender);
                CorePlusAPI.getLangManager().sendMsg("", sender,
                        "&f " + ToolPlus.getInstance().getDescription().getName()
                                + " &ev" + ToolPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                        ConfigHandler.getConfigPath().getMsgHelp(), sender);
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                        "Message.noPermission", sender);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.use")) {
                    CorePlusAPI.getLangManager().sendMsg("", sender, "");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                            ConfigHandler.getConfigPath().getMsgTitle(), sender);
                    CorePlusAPI.getLangManager().sendMsg("", sender,
                            "&f " + ToolPlus.getInstance().getDescription().getName()
                                    + " &ev" + ToolPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                            ConfigHandler.getConfigPath().getMsgHelp(), sender);
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.reload")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                                ConfigHandler.getConfigPath().getMsgReload(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.version")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                                ConfigHandler.getConfigPath().getMsgVersion(), sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), "",
                                ConfigHandler.getConfigPath().getMsgVersion(), sender);
                    }
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "reload":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.reload")) {
                    ConfigHandler.generateData(true);
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.configReload", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "version":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.version")) {
                    CorePlusAPI.getLangManager().sendMsg("", sender,
                            "&f " + ToolPlus.getInstance().getDescription().getName()
                                    + " &ev" + ToolPlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getUpdateManager().check(ConfigHandler.getPluginName(), "", sender,
                            ToolPlus.getInstance().getName(), ToolPlus.getInstance().getDescription().getVersion(), true);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "tool":
                // tlp tool <on/off> [mode] [player]
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool")) {
                    // tlp tool <on/off> [mode] [player]
                    if (length == 4) {
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[3]);
                        if (player == null) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[3]; // %targetplayer%
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool." + args[2]) ||
                                CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool.*")) {
                            VoidTool.toggle(sender, player, args[1], args[2]);
                        } else {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.noPermission", sender);
                        }
                        return true;
                    } else if (length == 3) {
                        // tlp tool <on/off> [player]
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[2]);
                        if (player != null) {
                            VoidTool.toggle(sender, player, args[1], null);
                        } else {
                            // tlp tool <on/off> [mode]
                            if (sender instanceof ConsoleCommandSender) {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                        "Message.onlyPlayer", sender);
                                return true;
                            }
                            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool." + args[2]) ||
                                    CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool.*")) {
                                VoidTool.toggle(sender, null, args[1], args[2]);
                            } else {
                                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                        "Message.noPermission", sender);
                            }
                        }
                        return true;
                        // tlp tool <on/off>
                    } else if (length == 2) {
                        if (sender instanceof ConsoleCommandSender) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.onlyPlayer", sender);
                            return true;
                        }
                        VoidTool.toggle(sender, null, args[1], null);
                        return true;
                    }
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            ConfigHandler.getConfigPath().getMsgVersion(), sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
        }
        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                "Message.unknownCommand", sender);
        return true;
    }
}

