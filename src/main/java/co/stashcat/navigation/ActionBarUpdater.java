package co.stashcat.navigation;

import co.stashcat.navigation.types.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarUpdater extends BukkitRunnable {
    public void run() {
        for (Player p : Navigator.getNavigatingList()) {
            Waypoint w = Navigator.getDestination(p);
            String info;
            if (p.getWorld().equals(w.getLocation().getWorld())) {
                Location ploc = p.getLocation();
                Location dest = w.getLocation();
                String hStr = "";
                if (w.isHeightIgnored()) {
                    ploc.setY(0);
                    dest.setY(0);
                } else {
                    int hDiff = w.getLocation().getBlockY() - p.getLocation().getBlockY();
                    if (hDiff > 0)
                        hStr = ", " + hDiff + " blocks above";
                    else if (hDiff < 0)
                        hStr = ", " + -hDiff + " blocks below";
                }
                info = String.format("&a%s&r: %d blocks away%s", w.getName(), (int) ploc.distance(dest), hStr);
            } else {
                info = String.format("&cWaypoint is in world &b%s&c!", w.getLocation().getWorld().getName());
            }
            info = ChatColor.translateAlternateColorCodes('&', info);
            ActionBar.sendActionBar(p, info);
        }
    }
}