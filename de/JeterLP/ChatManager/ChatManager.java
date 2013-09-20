package de.JeterLP.ChatManager;

import de.JeterLP.ChatManager.Plugins.PermissionFactory;
import de.JeterLP.ChatManager.Plugins.PermissionsPlugin;
import java.io.File;
import java.util.logging.Level;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author t3hk0d3, TheJeterLP
 */
public class ChatManager extends JavaPlugin {

    private ChatListener listener;
    private Utils utils;
    private HookManager hook = new HookManager();
    private static Chat chat = null;
    private static ChatManager instance;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        ChatManager.instance = new ChatManager();
        FileConfiguration config = this.getConfig();
        utils = new Utils(this);
        initializeConfiguration();
        if (!getConfig().getBoolean("enable")) {
            getServer().getPluginManager().disablePlugin(this);
            CMLogger.log(Level.INFO, "disabled, check config!");
            return;
        }
        if (hook.checkPex()) {
            PermissionFactory.setPlugin("PermissionsEx");
        } else if (hook.checkBperms()) {
            PermissionFactory.setPlugin("bPermissions");
        } else if (hook.checkGm()) {
            PermissionFactory.setPlugin("GroupManager");
        } else if (hook.checkVault()) {
            setupChat();
            PermissionFactory.setPlugin("Vault");
        } else {
            PermissionFactory.setPlugin("no");
        }
        if (hook.checkVault()) {
            setupEconomy();
        }
        CMLogger.log(Level.INFO, "Successfully hooked into: " + PermissionFactory.getName());
        listener = new ChatListener(config, this);
        getServer().getPluginManager().registerEvents(listener, this);
        CMLogger.log(Level.INFO, "ChatManager enabled!");
        saveConfig();
    }

    @Override
    public void onDisable() {
        listener = null;
        CMLogger.log(Level.INFO, "is now disabled!");
    }

    private void initializeConfiguration() {
        if (!new File("plugins/ChatEx/config.yml").exists()) {
            getConfig().set("enable", true);
            getConfig().set("message-format", ChatListener.MESSAGE_FORMAT);
            getConfig().set("global-message-format", ChatListener.GLOBAL_MESSAGE_FORMAT);
            getConfig().set("ranged-mode", ChatListener.RANGED_MODE);
            getConfig().set("chat-range", ChatListener.CHAT_RANGE);
            saveConfig();
        }
    }

    public PermissionsPlugin getPlugin() {
        return PermissionFactory.getPlugin();
    }

    public ChatListener getListener() {
        return listener;
    }

    public Utils getUtils() {
        return utils;
    }

    public Chat getChat() {
        return chat;
    }

    public HookManager getHook() {
        return hook;
    }

    public Economy getEconomy() {
        return econ;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        chat = chatProvider.getProvider();
        return chat != null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
        return econ != null;
    }

    public static ChatManager getInstance() {
        return instance;
    }
}
