package de.JeterLP.ChatManager.Plugins;

import org.bukkit.entity.Player;

/**
 *
 * @author TheJeterLP
 */
public interface PermissionsPlugin {

    public String getPrefix(Player p, String world);

    public String getPrefix(String player, String world);

    public String getSuffix(Player p, String world);

    public String getSuffix(String player, String world);
    
    public String[] getGroups(Player p, String world);
    
    public String[] getGroups(String player, String world);
}