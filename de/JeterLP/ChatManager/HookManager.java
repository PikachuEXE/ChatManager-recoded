package de.JeterLP.ChatManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
* @author TheJeterLP
*/
public class HookManager {
    
    public boolean checkBperms() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("bPermissions");
        if (plugin == null) {
            return false;
        }
        return true;
    }
    
    public boolean checkGm() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GroupManager");
        if (plugin == null) {
            return false;
        }
        return true;
    }
    
    public boolean checkPex() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");
        if (plugin == null) {
            return false;
        }
        return true;
    }
    
    public boolean checkVault() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (plugin == null) {
            return false;
        }
        return true;
    }
}
