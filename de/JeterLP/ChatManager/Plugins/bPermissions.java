package de.JeterLP.ChatManager.Plugins;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 *
 */
public class bPermissions implements PermissionsPlugin {

    @Override
    public String getPrefix(Player p, String world) {
        return ApiLayer.getValue(world, CalculableType.USER, p.getName(), "prefix");
    }

    @Override
    public String getSuffix(Player p, String world) {
        return ApiLayer.getValue(world, CalculableType.USER, p.getName(), "suffix");
    }

    @Override
    public String[] getGroups(Player p, String world) {
        return ApiLayer.getGroups(world, CalculableType.USER, p.getName());
    }
}
