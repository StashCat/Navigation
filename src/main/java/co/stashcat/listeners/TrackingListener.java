package co.stashcat.listeners;

import co.stashcat.Navigation;
import co.stashcat.Navigator;
import co.stashcat.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class TrackingListener implements Listener {
    Map<Player, Long> lastCheck = new HashMap<>();
    Navigation pl;

    public TrackingListener(Navigation p) {
        Bukkit.getPluginManager().registerEvents(this, p);
        pl = p;
    }

    @EventHandler
    public void trackPlayer(PlayerMoveEvent e) {
        if (Tracker.isBeingTracked(e.getPlayer()) && (!lastCheck.containsKey(e.getPlayer()) || System.currentTimeMillis() - lastCheck.get(e.getPlayer()) > pl.getConfig().getDouble("checkInterval") * 1000)) {
            Player p = Tracker.getTracker(e.getPlayer());
            Navigator.getDestination(p).setLocation(e.getPlayer().getLocation());
            lastCheck.put(p, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void disconnectListener(PlayerQuitEvent e) {
        if (e.getPlayer() != null && Tracker.isBeingTracked(e.getPlayer())) {
            Player p = Tracker.getTracker(e.getPlayer());
            Navigation.sendMsg(p, "&cTracking target \"&a%s&c\" has disconnected, stopping tracking.", e.getPlayer().getDisplayName());
            Tracker.stopTracking(p);
        }
    }
}
