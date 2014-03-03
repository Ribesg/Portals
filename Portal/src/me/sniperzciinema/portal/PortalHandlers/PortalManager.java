
package me.sniperzciinema.portal.PortalHandlers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class PortalManager {

	private static ArrayList<Portal> portals = new ArrayList<Portal>();

	public static Portal addPortal(Location loc, Location target, ItemStack im, Player p) {
		Portal portal = new Portal(getRoundedLocation(loc.add(0, 1, 0)),
				target, im, p);
		portals.add(portal);
		return portal;
	}

	public static Location getRoundedLocation(Location loc) {
		return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(),
				loc.getBlockZ(), loc.getPitch(), loc.getYaw());
	}

	public static Portal getPortal(Location loc) {
		for (Portal portal : portals)
			if (portal.getLocation() == loc)
				return portal;
		return null;
	}

	public static ArrayList<Portal> getPortals() {
		return portals;
	}

	public static Location getTargetFromItem(ItemStack im) {
		Location loc = null;
		try
		{
			String[] floc = ChatColor.stripColor(im.getItemMeta().getLore().get(3).replaceAll("Target: ", "")).split(",");
			World world = Bukkit.getServer().getWorld(floc[0]);
			loc = new Location(world, Double.valueOf(floc[1]),
					Double.valueOf(floc[2]), Double.valueOf(floc[3]));
		} catch (Exception e)
		{
		}
		return loc;
	}

	public static Location getObjectLocation(String loc) {
		Location Loc = null;
		if (loc != null)
		{
			String[] floc = loc.split(",");
			World world = Bukkit.getServer().getWorld(floc[0]);
			Loc = new Location(world, Double.valueOf(floc[1]),
					Double.valueOf(floc[2]), Double.valueOf(floc[3]));
		}
		return Loc;
	}

	public static boolean isPortal(ItemStack im) {
		for (Portal portal : portals)
			if (portal.getItem() == im)
				return true;

		return false;
	}

	public static String getLocationToString(Location loc) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World world = loc.getWorld();
		String s = world.getName() + "," + x + "," + y + "," + z + ",";
		return s;
	}

	public static void delPortal(Portal portal) {
		portals.remove(portal);
	}

}
