package tw.momocraft.toolplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.toolplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        int length = args.length;
        if (length == 0) {
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.use")) {
                commands.add("help");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.reload")) {
                commands.add("reload");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.version")) {
                commands.add("version");
            }
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "toolplus.command.tool")) {
                commands.add("tool");
            }
        }
        switch (args[0]) {
            case "tool":
                if (length == 1) {
                    commands.add("on");
                    commands.add("off");
                } else if (length == 2) {
                    commands.addAll(ConfigHandler.getConfigPath().getVoidToolProp().keySet());
                    commands.addAll(CorePlusAPI.getPlayerManager().getOnlinePlayerNames());
                } else if (length == 3) {
                    commands.addAll(CorePlusAPI.getPlayerManager().getOnlinePlayerNames());
                }
                break;
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}