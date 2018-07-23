package co.stashcat.types;

import co.stashcat.Navigation;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Waypoint {
    Location loc;
    int destinationRadius;
    boolean ignoreHeight;
    String id;
    String name;
    String desc;
    ItemStack item;

    public Waypoint(Location loc, int destinationRadius, boolean ignoreHeight) {
        setLocation(loc);
        setDestinationRadius(destinationRadius);
        setIgnoreHeight(ignoreHeight);
    }

    public Waypoint(Location loc, int destinationRadius, boolean ignoreHeight, String id, String name, String desc, ItemStack item) {
        setId(id);
        setName(name);
        setDescription(desc);
        setLocation(loc);
        setDestinationRadius(destinationRadius);
        setIgnoreHeight(ignoreHeight);
        setItem(item);
    }

    public void save(Navigation p) {
        ConfigurationSection wp = p.getConfig().getConfigurationSection("waypoints." + id);
        wp.set("id", id);
        wp.set("name", name);
        wp.set("description", desc);
        wp.set("location", loc);
        wp.set("destinationRadius", destinationRadius);
        wp.set("ignoreHeight", ignoreHeight);
        wp.set("item", item);
    }

    public void setId(String id) throws IllegalArgumentException {
        if (StringUtils.isAlphanumeric(id))
            this.id = id;
        else
            throw new IllegalArgumentException("IDs can only be alphanumeric.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public void setDestinationRadius(int radius) {
        this.destinationRadius = radius;
    }

    public void setIgnoreHeight(boolean ignoreHeight) {
        this.ignoreHeight = ignoreHeight;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Location getLocation() {
        return loc;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public int getDestinationRadius() {
        return destinationRadius;
    }

    public boolean isHeightIgnored() {
        return ignoreHeight;
    }
}
