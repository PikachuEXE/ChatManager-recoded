package de.JeterLP.ChatManager.Plugins;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author TheJeterLP
 *
 */
public class EssentialsGroupManager implements PermissionsPlugin {

    private GroupManager groupManager = null;

    public EssentialsGroupManager() {
        if (groupManager == null) {
            Plugin chat = Bukkit.getServer().getPluginManager().getPlugin("GroupManager");
            if (chat != null) {
                if (chat.isEnabled()) {
                    groupManager = (GroupManager) chat;
                }
            }
        }
    }

    @Override
    public String getPrefix(Player p, String world) {
        AnjoPermissionsHandler handler = null;
        if (world == null) {
            handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(p.getName());
        } else {
            handler = groupManager.getWorldsHolder().getWorldPermissions(world);
        }
        if (handler == null) {
            return "";
        }
        return handler.getUserPrefix(p.getName());
    }

    @Override
    public String getSuffix(Player p, String world) {
        AnjoPermissionsHandler handler;
        if (world == null) {
            handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(p.getName());
        } else {
            handler = groupManager.getWorldsHolder().getWorldPermissions(world);
        }
        if (handler == null) {
            return "";
        }
        return handler.getUserSuffix(p.getName());
    }

    @Override
    public String[] getGroups(Player p, String world) {
        AnjoPermissionsHandler handler;
        if (world == null) {
            handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(p.getName());
        } else {
            handler = groupManager.getWorldsHolder().getWorldPermissions(world);
        }
        if (handler == null) {
            String[] data = {""};
            return data;
        }
        return handler.getGroups(p.getName());
    }
}
