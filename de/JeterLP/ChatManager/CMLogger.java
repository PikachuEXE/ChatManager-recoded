package de.JeterLP.ChatManager;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

/**
* @author TheJeterLP
*/
public class CMLogger {
    
    private static final Logger logger = Bukkit.getLogger();
    
    public static void log(Level level, String message) {
        String prefix = "[ChatManager - recoded] ";
        message = prefix + message;
        logger.log(level, message);
    }

}
