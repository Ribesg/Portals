
package me.sniperzciinema.portal.Util;

import me.sniperzciinema.portal.PortablePortals;


public class Settings {

	public static boolean doesPortalWithoutTargetKill() {
		return PortablePortals.me.getConfig().getBoolean("Portals.Without Target Kill Player");
	}

	public static int stayOpenTime() {
		return PortablePortals.me.getConfig().getInt("Portals.Stay Open Time") * 20;
	}

	public static int portalRefreshTime() {
		return PortablePortals.me.getConfig().getInt("Portals.Check If In Portal Refresh Time") * 20;
	}
}
