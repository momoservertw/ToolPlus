package tw.momocraft.toolplus.utils;

import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.toolplus.ToolPlus;
import tw.momocraft.toolplus.handlers.ConfigHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigPath {
    public ConfigPath() {
        setUp();
    }

    //  ============================================== //
    //         Message Variables                       //
    //  ============================================== //
    private String msgTitle;
    private String msgHelp;
    private String msgReload;
    private String msgVersion;

    private String msgCmdTool;

    private String msgToolOn;
    private String msgToolOnTarget;
    private String msgToolOff;
    private String msgToolOffTarget;

    //  ============================================== //
    //         Void Tool Variables                     //
    //  ============================================== //
    private boolean voidTool;
    private Map<String, VoidToolMap> voidToolProp = new HashMap<>();

    //  ============================================== //
    //         Setup all configuration                 //
    //  ============================================== //
    private void setUp() {
        setupMsg();
        setVoidTool();

        sendSetupMsg();
    }

    private void sendSetupMsg() {
        List<String> list = new ArrayList<>(ToolPlus.getInstance().getDescription().getDepend());
        list.addAll(ToolPlus.getInstance().getDescription().getSoftDepend());
        UtilsHandler.getLang().sendHookMsg(ConfigHandler.getPluginPrefix(), "plugins", list);

        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "Loaded commands.yml: " + voidToolProp.keySet());

        /*
        list = Arrays.asList((
                "climb" + ","
                        + "crawl" + ","
                        + "mobkick" + ","
                        + "wallkick"
        ).split(","));
        CorePlusAPI.getLangManager().sendHookMsg(ConfigHandler.getPluginPrefix(), "Residence flags", list);

         */
    }

    //  ============================================== //
    //         Message Setter                          //
    //  ============================================== //
    private void setupMsg() {
        msgTitle = ConfigHandler.getConfig("config.yml").getString("Message.Commands.title");
        msgHelp = ConfigHandler.getConfig("config.yml").getString("Message.Commands.help");
        msgReload = ConfigHandler.getConfig("config.yml").getString("Message.Commands.reload");
        msgVersion = ConfigHandler.getConfig("config.yml").getString("Message.Commands.version");
        msgCmdTool = ConfigHandler.getConfig("config.yml").getString("Message.Commands.toggle");

        msgToolOn = ConfigHandler.getConfig("config.yml").getString("Message.toggleOn");
        msgToolOnTarget = ConfigHandler.getConfig("config.yml").getString("Message.toggleOnTarget");
        msgToolOff = ConfigHandler.getConfig("config.yml").getString("Message.toggleOff");
        msgToolOffTarget = ConfigHandler.getConfig("config.yml").getString("Message.toggleOffTarget");
    }

    //  ============================================== //
    //         Void Tool Setter                        //
    //  ============================================== //
    private void setVoidTool() {
        voidTool = ConfigHandler.getConfig("config.yml").getBoolean("Void-Tool.Enable");
        if (!voidTool) {
            return;
        }
        ConfigurationSection config = ConfigHandler.getConfig("config.yml").getConfigurationSection("Void-Tool.Groups");
        if (config == null) {
            return;
        }
        VoidToolMap voidToolMap;
        for (String group : config.getKeys(false)) {
            if (group.equals("Enable")) {
                continue;
            }
            voidToolMap = new VoidToolMap();
            voidToolMap.setTypes(CorePlusAPI.getConfigManager().getTypeList(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfig("config.yml").getStringList("Void-Tool.Groups." + group + ".List"),
                    "Materials"));
            voidToolMap.setIgnoreTypes(CorePlusAPI.getConfigManager().getTypeList(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfig("config.yml").getStringList("Void-Tool.Groups." + group + ".Ignore-List"),
                    "Materials"));
            voidToolMap.setLocList(CorePlusAPI.getConfigManager().getTypeList(ConfigHandler.getPrefix(),
                    ConfigHandler.getConfig("config.yml").getStringList("Void-Tool.Groups." + group + ".Location"),
                    "Materials"));
            voidToolProp.put(group, voidToolMap);
        }
    }

    //  ============================================== //
    //         Message Getter                          //
    //  ============================================== //
    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgHelp() {
        return msgHelp;
    }

    public String getMsgReload() {
        return msgReload;
    }

    public String getMsgVersion() {
        return msgVersion;
    }

    public String getMsgCmdTool() {
        return msgCmdTool;
    }

    public String getMsgToolOn() {
        return msgToolOn;
    }

    public String getMsgToolOnTarget() {
        return msgToolOnTarget;
    }

    public String getMsgToolOff() {
        return msgToolOff;
    }

    public String getMsgToolOffTarget() {
        return msgToolOffTarget;
    }

    //  ============================================== //
    //         Destroy Getter                          //
    //  ============================================== //
    public boolean isVoidTool() {
        return voidTool;
    }

    public Map<String, VoidToolMap> getVoidToolProp() {
        return voidToolProp;
    }
}
