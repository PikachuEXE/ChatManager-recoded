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
        PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user != null) {
            return user.getPrefix(world);
        } else {
            return null;
        }
    }

    @Override
    public String getSuffix(Player p, String world) {
        PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user != null) {
            return user.getSuffix(world);
        } else {
            return null;
        }
    }

    @Override
    public String[] getGroups(Player p, String world) {
        return PermissionsEx.getPermissionManager().getUser(p).getGroupsNames();
    }

}
