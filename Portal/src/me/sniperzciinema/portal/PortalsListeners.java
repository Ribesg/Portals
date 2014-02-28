
package me.sniperzciinema.portal;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class PortalsListeners implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerUsePortal(final PlayerInteractEvent e) {

		// Make sure they have a portal item in their hand
		if (e.getItem() != null)
			if (e.getItem().getType().equals(Material.NETHER_STAR))
				if (e.getItem().getItemMeta().hasDisplayName())
					if (e.getPlayer().hasPermission("PortablePortals.Create"))
					{
						if (e.getItem().getItemMeta().getDisplayName().contains("Portal"))
						{

							final Player player = e.getPlayer();
							
							// Setting location
							if (e.getAction() == Action.LEFT_CLICK_BLOCK)
							{

								// Get Location, change into a string, set as
								// item lore
								Location loc = e.getClickedBlock().getLocation().clone().add(0.0, 1.0, 0.0);
								String locationString = PortalManager.getLocationToString(loc);
								
								ItemStack portal = e.getItem().clone();
								portal.setAmount(1);
								
								ItemMeta im = portal.getItemMeta();
								im.setDisplayName(ChatColor.DARK_GRAY + "Portal");
								ArrayList<String> lores = new ArrayList<String>();
								lores.add(ChatColor.DARK_AQUA + "Left Click To Set");
								lores.add(ChatColor.GOLD + "Right Click To Open");
								lores.add("" + ChatColor.WHITE + ChatColor.ITALIC + "------------");
								lores.add(ChatColor.YELLOW + "Target: " + locationString);
								im.setLore(lores);
								portal.setItemMeta(im);
								
								if (e.getItem().getAmount() != 1)
									e.getItem().setAmount(e.getItem().getAmount() - 1);
								else
									player.getInventory().remove(e.getItem());
								
								player.getInventory().addItem(portal);
								player.updateInventory();
								
								// Tell the player what just happened
								player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "The location of your portal has been set.");
							
							} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
							{
								// Save the location of the target
								if (player.hasPermission("PortablePortals.Create"))
								{
									Location target = PortalManager.getTargetFromItem(e.getItem());

									if (target == null)
										target = new Location(
												player.getWorld(), 0, 0, 0);

									// Remove portal from hand
									final Portal portal = PortalManager.addPortal(e.getClickedBlock().getLocation(), target, e.getItem(), player);

									if (e.getItem().getAmount() != 1)
										e.getItem().setAmount(e.getItem().getAmount() - 1);
									else
										player.getInventory().remove(e.getItem());
									
									player.updateInventory();

									player.getWorld().createExplosion(portal.getLocation(), 0.0F, false);

									if (!portal.canCreatePortal())
									{
										player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "The portal isn't balanced in this location.");

										player.getInventory().addItem(portal.getItem());

										player.updateInventory();
										PortalManager.delPortal(portal);

									} else
									{

										portal.createPortal();

										player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Your portal has been opened.");

										portal.playEffect();

										// Timer to give portal item back
										Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PortablePortals.me, new Runnable()
										{

											@Override
											public void run() {
												player.getInventory().addItem(portal.getItem());

												player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Your portal has been closed.");
												portal.removePortal();
												PortalManager.delPortal(portal);
											}
										}, 200);

									}
								}
							}
							e.setUseInteractedBlock(Result.DENY);
							e.setUseItemInHand(Result.DENY);
							e.setCancelled(true);
						}
					}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerAttemptToBreakPortal(BlockBreakEvent event) {
		for (Portal portal : PortalManager.getPortals())
			if (portal.isBlock(event.getBlock().getLocation()))
				event.setCancelled(true);
	}
}
