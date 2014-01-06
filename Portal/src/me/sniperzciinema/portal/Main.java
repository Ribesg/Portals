
package me.sniperzciinema.portal;

import java.util.ArrayList;

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
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	public static Plugin me;
	private Listeners listeners = new Listeners(this);

	public void onEnable() {
		me = this;

		// Register the event listener
		getServer().getPluginManager().registerEvents(listeners, this);

		// Portal Item

		ItemStack portal = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta im = portal.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Portal");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GREEN + "Left Click To Set");
		lore.add(ChatColor.RED + "Right Click To Open");
		lore.add(ChatColor.GRAY + "------------");
		lore.add(ChatColor.YELLOW + "Target: None");
		im.setLore(lore);
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
					Location loc = PortalManager.getRoundedLocation(player.getLocation());
					if (player.hasPermission("Portals.Use"))
						for (Portal portal : PortalManager.getPortals())
							if (loc.getBlockX() == portal.getLocation().getBlockX() && loc.getBlockY() == portal.getLocation().getBlockY() && loc.getBlockZ() == portal.getLocation().getBlockZ())
								player.teleport(portal.getTarget());
				}
			}
		}, 100L, 20);

	}

	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("Portals"))
		{
			if (sender instanceof Player && sender.hasPermission("Portals.Spawn"))
			{
				// Define "player" as the one who sent the command
				Player player = (Player) sender;

				ItemStack portal = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta im = portal.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Portal");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GREEN + "Left Click To Set");
				lore.add(ChatColor.RED + "Right Click To Open");
				lore.add("" + ChatColor.GRAY + RandomChatColor.getFormat() + "------------");
				lore.add(RandomChatColor.getColor() + "Target: None");
				im.setLore(lore);
				portal.setItemMeta(im);
				player.getInventory().addItem(portal);
			}

		}
		return true;
	}

}
