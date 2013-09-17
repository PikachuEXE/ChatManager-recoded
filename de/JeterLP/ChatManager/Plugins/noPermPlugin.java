package de.JeterLP.ChatManager.Plugins;

import org.bukkit.entity.Player;

/**
* @author TheJeterLP
*/
public class noPermPlugin implements PermissionsPlugin{

    @Override
    public String getPrefix(Player p, String world) {
        return "";
    }

    @Override
    public String getPrefix(String player, String world) {
        return "";
    }

    @Override
    public String getSuffix(Player p, String world) {
        return "";
    }

    @Override
    public String getSuffix(String player, String world) {
        return "";
    }

    @Override
    public String[] getGroups(Player p, String world) {
        String[] data={""};
        return data;
    }

    @Override
    public String[] getGroups(String player, String world) {
        String[] data={""};
        return data;
    }

}
