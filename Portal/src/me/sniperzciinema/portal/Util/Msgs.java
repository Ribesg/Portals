
package me.sniperzciinema.portal.Util;

import org.bukkit.ChatColor;


public enum Msgs
{

	Portals_Title("Portals.Title", ""),
	Portals_LeftClickTo("Portals.Left Click To", ""),
	Portals_RightClickTo("Portals.Right Click To", ""),
	Portals_Target("Portals.Target", "<target>"),
	Portals_NotEnoughRoom("Action.Not Enough Room", ""),
	Portals_PortalOpened("Action.Portal Opened", ""),
	Portals_PortalClosed("Action.Portal Closed", ""),
	Portals_TargetSet("Action.Target Set", ""), 
	Portals_NoTarget("Portals.No Target", "");

	private String string;
	private String replace;

	private Msgs(String s, String r)
	{
		string = s;
		replace = r;
	}

	public String getString(String... replacement) {
		try
		{
			String message = ChatColor.translateAlternateColorCodes('&', Files.getMessages().getString(string).replaceAll("&x", "&" + String.valueOf(RandomChatColor.getColor().getChar())).replaceAll("&y", "&" + String.valueOf(RandomChatColor.getFormat().getChar()))) + " ";

			if (replace != "")
				message = message.replaceAll(replace, replacement[0]);

			return message;
		} catch (NullPointerException npe)
		{
			return "Unable to find message: " + string;
		}
	}

};
