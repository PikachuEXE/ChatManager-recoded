package de.JeterLP.ChatManager.Plugins;

/**
 * @author TheJeterLP
 */
public class PermissionFactory {

    private static PermissionsPlugin plugin;
    private static String name;

    public static PermissionsPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(String pl) {
        if (pl.equalsIgnoreCase("permissionsex") || pl.equalsIgnoreCase("pex")) {
            plugin = new pex();
            name = pl;
        } else if (pl.equalsIgnoreCase("bpermissions")) {
            plugin = new bPermissions();
            name = pl;
        } else if (pl.equalsIgnoreCase("groupmanager")) {
            plugin = new EssentialsGroupManager();
            name = pl;
        } else {
            plugin = new noPermPlugin();
            name = "No Permissions plugin found!";
        }
    }
    
    public static String getName() {
        return name;
    }
}
