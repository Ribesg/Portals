
package me.sniperzciinema.portal.Util;

import me.sniperzciinema.portal.PortablePortals;

import org.bukkit.Material;


public class Settings {

	public static boolean checkForUpdate() {
		return PortablePortals.me.getConfig().getBoolean("Check for new update");
	}

	@SuppressWarnings("deprecation")
	public static Material getItemRequired() {
		return Material.getMaterial(PortablePortals.me.getConfig().getInt("Require Item To Use.ItemId"));
	}

	public static boolean isItemRequired() {
		return PortablePortals.me.getConfig().getBoolean("Require Item To Use.Enabled");
	}

	public static int portalRefreshTime() {
		return PortablePortals.me.getConfig().getInt("Portals.Check If In Portal Refresh Time") * 20;
	}

	public static int stayOpenTime() {
		return PortablePortals.me.getConfig().getInt("Portals.Stay Open Time") * 20;
	}
}
