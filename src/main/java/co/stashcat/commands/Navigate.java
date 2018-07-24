package co.stashcat.commands;

import co.stashcat.Navigation;
import co.stashcat.Navigator;
import co.stashcat.WaypointManager;
import co.stashcat.types.Waypoint;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Navigate implements CommandExecutor {
    Navigation pl;

    public Navigate(Navigation pl) {
        this.pl = pl;
        pl.getCommand("navigate").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
        if (!(s instanceof Player)) {
            Navigation.sendMsg(s, "&cThis command can only be used by players.");
            return true;
        }
        Player p = (Player) s;
        if (args.length == 1) {
            Waypoint w = WaypointManager.getWaypoint(args[0]);
            if (w != null) {
                Navigator.navigate(p, w);
                Navigation.sendMsg(p, "&aNavigating to %s...", w.getName());
            } else {
                Navigation.sendMsg(p, "&cWaypoint %s does not exist.", args[0]);
            }
            return true;
        } else if (args.length == 2 || args.length == 3) {
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
                Navigation.sendMsg(p, "&cCoordinates contain a non-numeric character.");
                return true;
            }
            Waypoint w = new Waypoint(new Location(p.getWorld(), x, y, z), 5, args.length == 3);
            Navigator.navigate(p, w);
            Navigation.sendMsg(p, "&aNavigating to %d, %d, %d...", x, y, z);
            return true;
        } else if (args.length == 0 && Navigator.isNavigating(p)) {
            Navigator.stopNavigation(p);
            Navigation.sendMsg(p, "&aNavigation stopped.");
            return true;
        }
        Navigation.sendMsg(s, "&cInvalid arguments.");
        return false;
    }
}
