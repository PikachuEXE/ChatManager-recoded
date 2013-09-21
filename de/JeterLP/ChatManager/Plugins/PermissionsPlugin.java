package de.JeterLP.ChatManager.Plugins;

import org.bukkit.entity.Player;

/**
 *
 * @author TheJeterLP
 */
public interface PermissionsPlugin {

    public String getPrefix(Player p, String world);

    public String getSuffix(Player p, String world);

    public String[] getGroupNames(Player p, String world);
}
