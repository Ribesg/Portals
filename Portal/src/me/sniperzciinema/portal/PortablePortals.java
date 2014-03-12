
package me.sniperzciinema.portal;

import java.io.IOException;
import java.util.ArrayList;

import me.sniperzciinema.portal.PortalHandlers.Portal;
import me.sniperzciinema.portal.PortalsListeners;
import me.sniperzciinema.portal.PortalHandlers.PortalManager;
import me.sniperzciinema.portal.Util.Files;
import me.sniperzciinema.portal.Util.Metrics;
import me.sniperzciinema.portal.Util.Msgs;
import me.sniperzciinema.portal.Util.Settings;
import me.sniperzciinema.portal.Util.Updater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class PortablePortals extends JavaPlugin {

	public static Plugin me;
	public ItemStack portal;
	public boolean update = false;
	public String name = "";

	public void onEnable() {
		me = this;
		Updater updater;
		getConfig().options().copyDefaults(true);
		Files.getMessages().options().copyDefaults(true);
		Files.saveMessages();
		saveConfig();

		PluginManager pm = getServer().getPluginManager();
		pm = getServer().getPluginManager();
		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
			System.out.println("Metrics was started!");
		} catch (IOException e)
		{
			System.out.println("Metrics was unable to start...");
		}
		// Register the event listener
		pm.registerEvents(new PortalsListeners(this), this);

		if (getConfig().getBoolean("Check for new updates"))
		{
			try
			{
				updater = new Updater(this, 65787, getFile(),
						Updater.UpdateType.NO_DOWNLOAD, false);

				update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
				name = updater.getLatestName();

			} catch (Exception ex)
			{
				System.out.println("The auto-updater tried to contact dev.bukkit.org, but was unsuccessful.");
			}
		}
		portal = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = portal.getItemMeta();
		im.setDisplayName(Msgs.Portals_Title.getString());
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(Msgs.Portals_LeftClickTo.getString());
		lores.add(Msgs.Portals_RightClickTo.getString());
		lores.add("" + ChatColor.WHITE + ChatColor.ITALIC + "------------");
		lores.add(Msgs.Portals_Target.getString("None"));
		im.setLore(lores);

		portal.setItemMeta(im);

		ShapedRecipe portalCube = new ShapedRecipe(portal).shape(new String[] { "*#*", "#%#", "*#*" }).setIngredient('#', Material.EMERALD).setIngredient('*', Material.OBSIDIAN).setIngredient('%', Material.NETHER_STAR);

		Bukkit.getServer().addRecipe(portalCube);
		// =======================================================================================

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers())
				{
					if (player.hasPermission("PortablePortals.Use"))
					{
						if (!PortalManager.getPortals().isEmpty())
						{
							Location loc = PortalManager.getRoundedLocation(player.getLocation());
							for (Portal portal : PortalManager.getPortals())
								if (loc.getBlockX() == portal.getLocation().getBlockX() && loc.getBlockY() == portal.getLocation().getBlockY() && loc.getBlockZ() == portal.getLocation().getBlockZ())
									player.teleport(portal.getTarget());
						}
					}
				}
			}
		}, Settings.portalRefreshTime(), 20);

	}

	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("PPortals"))
		{
			if (sender instanceof Player && sender.hasPermission("PortablePortals.Spawn"))
			{
				// Define "player" as the one who sent the command
				Player player = (Player) sender;
				player.getInventory().addItem(portal);
			}

		}else if (cmd.getName().equalsIgnoreCase("PReload"))
		{
			if (sender instanceof Player && ((Player)sender).isOp())
				Files.reloadAll();
			

		}
		return true;
	}

}
