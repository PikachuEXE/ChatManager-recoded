package de.JeterLP.ChatManager;

import de.JeterLP.ChatManager.Plugins.EssentialsGroupManager;
import de.JeterLP.ChatManager.Plugins.PermissionFactory;
import de.JeterLP.ChatManager.Plugins.PermissionsPlugin;
import de.JeterLP.ChatManager.Plugins.bPermissions;
import de.JeterLP.ChatManager.Plugins.noPermPlugin;
import de.JeterLP.ChatManager.Plugins.pex;
import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author t3hk0d3, TheJeterLP
 */
public class ChatManager extends JavaPlugin {

    protected static Logger log;
    protected ChatListener listener;
    private static PermissionsPlugin plugin;

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        log = this.getLogger();
        if (!new File("plugins/ChatManager-recoded/config.yml").exists()) {
            this.initializeConfiguration(config);
        }
        if (!getConfig().getBoolean("enable")) {
            getServer().getPluginManager().disablePlugin(this);
            log.info("ChatManager - recoded disabled, check config!");
            return;
        }
        if (checkPex()) {
            PermissionFactory.setPlugin("PermissionsEx");
            plugin = new pex();
        /*} else if (checkGm()) {
            PermissionFactory.setPlugin("GroupManager");
            plugin = new EssentialsGroupManager();
        } else if (checkBperms()) {
            PermissionFactory.setPlugin("bPermissions");
            plugin = new bPermissions();*/
        } else {
            PermissionFactory.setPlugin("no");
            plugin = new noPermPlugin();
        }
        log.info("Successfully hooked into: " + PermissionFactory.getName());
        this.listener = new ChatListener(config, this);
        this.getServer().getPluginManager().registerEvents(listener, this);
        log.info("ChatManager enabled!");
        saveConfig();
    }

    @Override
    public void onDisable() {
        this.listener = null;
        log.info("ChatManager disabled!");
    }

    protected void initializeConfiguration(FileConfiguration config) {
        getConfig().set("enable", true);
        getConfig().set("message-format", ChatListener.MESSAGE_FORMAT);
        getConfig().set("global-message-format", ChatListener.GLOBAL_MESSAGE_FORMAT);
        getConfig().set("ranged-mode", ChatListener.RANGED_MODE);
        getConfig().set("chat-range", ChatListener.CHAT_RANGE);
        saveConfig();
    }

    public boolean checkPex() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");
        if (plugin == null) {
            return false;
        }
        return true;
    }

    /*public boolean checkGm() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GroupManager");
        if (plugin == null) {
            return false;
        }
        return true;
    }*/

    /*public boolean checkBperms() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("bPermissions");
        if (plugin == null) {
            return false;
        }
        return true;
    }*/

    public PermissionsPlugin getPlugin() {
        return plugin;
    }
}
