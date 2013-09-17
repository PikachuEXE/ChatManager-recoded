package de.JeterLP.ChatManager.Plugins;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 *
 */
public class EssentialsGroupManager implements PermissionsPlugin {

    private GroupManager groupManager = new GroupManager();

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
    public String getPrefix(String player, String world) {
        AnjoPermissionsHandler handler;
        if (world == null) {
            handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player);
        } else {
            handler = groupManager.getWorldsHolder().getWorldPermissions(world);
        }
        if (handler == null) {
            return "";
        }
        return handler.getUserPrefix(player);
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
    public String getSuffix(String player, String world) {
        AnjoPermissionsHandler handler;
        if (world == null) {
            handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player);
        } else {
            handler = groupManager.getWorldsHolder().getWorldPermissions(world);
        }
        if (handler == null) {
            return "";
        }
        return handler.getUserSuffix(player);
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
            return null;
        }
        return handler.getGroups(p.getName());
    }

    @Override
    public String[] getGroups(String player, String world) {
        AnjoPermissionsHandler handler;
        if (world == null) {
            handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player);
        } else {
            handler = groupManager.getWorldsHolder().getWorldPermissions(world);
        }
        if (handler == null) {
            return null;
        }
        return handler.getGroups(player);
    }
}
