package tw.momocraft.toolplus.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.toolplus.handlers.ConfigHandler;
import tw.momocraft.toolplus.utils.VoidToolMap;

import java.util.HashMap;
import java.util.Map;

public class VoidTool implements Listener {

    private static final Map<String, String> playersMap = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onClickBlock(PlayerInteractEvent e) {
        if (!ConfigHandler.getConfigPath().isVoidTool()) {
            return;
        }
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        Block block = e.getClickedBlock();
        // Left click a block.
        String action = e.getAction().name();
        if (action.equals("LEFT_CLICK_BLOCK")) {
            String blockType;
            try {
                blockType = block.getType().name();
            } catch (Exception ex) {
                return;
            }
            // Holding menu.
            if (!CorePlusAPI.getConditionManager().isMenu(itemStack)) {
                return;
            }
            // Enabled mode.
            String mode = getMode(player.getName());
            if (mode == null) {
                return;
            }
            remove(player, mode, block.getLocation(), blockType);
        }
    }


    private void remove(Player player, String mode, Location blockLoc, String blockType) {
        VoidToolMap voidToolMap = ConfigHandler.getConfigPath().getVoidToolProp().get(mode);
        if (voidToolMap == null) {
            return;
        }
        // Location
        if (!CorePlusAPI.getConditionManager().checkLocation(ConfigHandler.getPluginName(), blockLoc,
                voidToolMap.getLocList(), true)) {
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(),
                    "Void-Tool", blockType, "location", "return",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Residence flag
        if (!CorePlusAPI.getConditionManager().checkFlag(player, blockLoc, "destroy", true, true)) {
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[13] = "destroy"; // %flag%
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    "Message.noFlagPerm", player, placeHolders);
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(),
                    "Void-Tool", blockType, "residence", "return", "destroy",
                    new Throwable().getStackTrace()[0]);
            return;
        }
        // Drop permission
        if (CorePlusAPI.getPlayerManager().hasPerm(player, "toolplus.drop." + blockType) ||
                CorePlusAPI.getPlayerManager().hasPerm(player, "toolplus.drop.*")) {
            try {
                player.getWorld().dropItem(blockLoc, new ItemStack(Material.getMaterial(blockType)));
                CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(),
                        "Void-Tool", blockType, "Drop", "return",
                        new Throwable().getStackTrace()[0]);
            } catch (Exception ex) {
                CorePlusAPI.getLangManager().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), ex);
            }
            return;
        }
        // Remove the block.
        blockLoc.getBlock().setType(Material.AIR);
        CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(),
                "Void-Tool", blockType, "final", "return",
                new Throwable().getStackTrace()[0]);
    }

    public static void toggle(CommandSender sender, Player target, String status, String mode) {
        Player player;
        if (target != null) {
            player = target;
        } else {
            player = (Player) sender;
        }
        if (ConfigHandler.getConfigPath().getVoidToolProp().get(mode) == null) {
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVersion(), sender);
            return;
        }
        if (status.equals("on")) {
            putMode(player.getName(), mode);
            String[] placeHolders = CorePlusAPI.getLangManager().newString();
            placeHolders[5] = mode;
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgToolOn(), player, placeHolders);
            if (target != null) {
                placeHolders[1] = target.getName(); // %targetplayer%
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgToolOnTarget(), sender, placeHolders);
            }
        } else if (status.equals("off")) {
            removeMode(player.getName());
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgToolOff(), sender);
            if (target != null) {
                String[] placeHolders = CorePlusAPI.getLangManager().newString();
                placeHolders[1] = target.getName(); // %targetplayer%
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                        ConfigHandler.getConfigPath().getMsgToolOffTarget(), sender);
            }
        } else {
            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                    ConfigHandler.getConfigPath().getMsgVersion(), sender);
        }
    }

    public static String getMode(String playerName) {
        return playersMap.get(playerName);
    }

    public static void putMode(String playerName, String mode) {
        playersMap.put(playerName, mode);
    }

    public static void removeMode(String playerName) {
        playersMap.remove(playerName);
    }
}