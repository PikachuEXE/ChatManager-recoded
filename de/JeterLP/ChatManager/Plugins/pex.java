package de.JeterLP.ChatManager.Plugins;

import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * @author TheJeterLP
 */
public class pex implements PermissionsPlugin {

    @Override
    public String getPrefix(Player p, String world) {
        final PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user != null) {
            return user.getPrefix(world);
        } else {
            return "";
        }
    }

    @Override
    public String getSuffix(Player p, String world) {
        final PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user != null) {
            return user.getSuffix(world);
        } else {
            return "";
        }
    }

    @Override
    public String[] getGroupNames(Player p, String world) {
        final PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user != null) {
            return user.getGroupsNames();
        } else {
            String[] data = {""};
            return data;
        }
    }
}
