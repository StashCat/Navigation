package co.stashcat.navigation.commands;

import co.stashcat.navigation.Main;
import co.stashcat.navigation.Navigator;
import co.stashcat.navigation.WaypointManager;
import co.stashcat.navigation.types.Waypoint;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.naming.NoPermissionException;

public class NavigateCommand implements CommandExecutor {
    Main pl;

    public NavigateCommand(Main pl) {
        this.pl = pl;
        pl.getCommand("navigate").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {
            Main.sendMsg(s, "&cThis command can only be used by players.");
            return true;
        }
        Player p = (Player) s;
        if (args.length == 1) {
            Waypoint w = WaypointManager.getWaypoint(args[0]);
            if (w != null) {
                try {
                    Navigator.navigate(p, w);
                } catch (NoPermissionException e) {
                    Main.sendMsg(s, "&cPermission required to navigate to %s.", w.getId());
                    return true;
                }
                Main.sendMsg(p, "&aNavigating to %s...", w.getName());
                Main.sendMsg(p, "Type /%s to stop.", label);
            } else {
                Main.sendMsg(p, "&cWaypoint %s does not exist.", args[0]);
            }
            return true;
        } else if (args.length == 2 || args.length == 3 && s.hasPermission("navigation.coordinates")) {
            int x;
            int y = 0;
            int z;
            try {
                x = Integer.parseInt(args[0]);
                if (args.length == 3) {
                    y = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                } else {
                    z = Integer.parseInt(args[1]);
                }
            } catch (NumberFormatException e) {
                Main.sendMsg(p, "&cCoordinates contain a non-numeric character.");
                return true;
            }
            Waypoint w = new Waypoint(new Location(p.getWorld(), x, y, z), 5, args.length != 3);
            try {
                Navigator.navigate(p, w);
            } catch (NoPermissionException e) {
                Main.sendMsg(s, "&cPermission required to navigate to %s.", w.getId());
                return true;
            }
            Main.sendMsg(p, "&aNavigating to %d, %d, %d...", x, y, z);
            Main.sendMsg(p, "Type /%s to stop.", label);
            return true;
        } else if (args.length == 0 && Navigator.isNavigating(p)) {
            Navigator.stopNavigation(p);
            Main.sendMsg(p, "&aNavigation stopped.");
            return true;
        }
        Main.sendMsg(s, "&cInvalid arguments.");
        return false;
    }
}
