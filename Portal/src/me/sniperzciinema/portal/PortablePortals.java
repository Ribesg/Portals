
package me.sniperzciinema.portal;

import java.io.IOException;
import java.util.ArrayList;

import me.sniperzciinema.portal.PortalHandlers.Portal;
import me.sniperzciinema.portal.PortalHandlers.PortalManager;
import me.sniperzciinema.portal.Util.Metrics;
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

	public void onEnable() {
		me = this;
		boolean update = false;
		@SuppressWarnings("unused")
		String name = "";
		Updater updater;
		getConfig().options().copyDefaults(true);
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
		pm.registerEvents(new PortalsListeners(), this);


		if (getConfig().getBoolean("Download new updates"))
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
			if (update)
				updater = new Updater(this, 75241, getFile(),
						Updater.UpdateType.NO_VERSION_CHECK, false);
		}
		portal = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = portal.getItemMeta();
		im.setDisplayName(ChatColor.DARK_GRAY + "Portal");
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(ChatColor.DARK_AQUA + "Left Click To Set");
		lores.add(ChatColor.GOLD + "Right Click To Open");
		lores.add("" + ChatColor.WHITE + ChatColor.ITALIC + "------------");
		lores.add(ChatColor.YELLOW + "Target: None");
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
		}, getConfig().getInt("Check If In Portal Refresh Time")*20, 20);

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

		}
		return true;
	}

}
