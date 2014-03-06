
package me.sniperzciinema.portal;

import java.util.ArrayList;

import me.sniperzciinema.portal.PortalHandlers.Portal;
import me.sniperzciinema.portal.PortalHandlers.PortalManager;
import me.sniperzciinema.portal.Util.Msgs;
import me.sniperzciinema.portal.Util.Settings;

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
						if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.stripColor(Msgs.Portals_Title.getString())))
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
								im.setDisplayName(Msgs.Portals_Title.getString());
								ArrayList<String> lores = new ArrayList<String>();
								lores.add(Msgs.Portals_LeftClickTo.getString());
								lores.add(Msgs.Portals_RightClickTo.getString());
								lores.add("" + ChatColor.WHITE + ChatColor.ITALIC + "------------");
								lores.add(Msgs.Portals_Target.getString(locationString));
								im.setLore(lores);
								portal.setItemMeta(im);

								if (e.getItem().getAmount() != 1)
									e.getItem().setAmount(e.getItem().getAmount() - 1);
								else
									player.getInventory().remove(e.getItem());

								player.getInventory().addItem(portal);
								player.updateInventory();

								// Tell the player what just happened
								player.sendMessage(Msgs.Portals_TargetSet.getString());

							} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
							{
								// Save the location of the target
								if (player.hasPermission("PortablePortals.Create"))
								{
									Location target = PortalManager.getTargetFromItem(e.getItem());

									if (target == null)
										if (Settings.doesPortalWithoutTargetKill())
											target = new Location(
													player.getWorld(), 0, 0, 0);
										else
											target = e.getPlayer().getWorld().getSpawnLocation();

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
										player.sendMessage(Msgs.Portals_NotEnoughRoom.getString());

										player.getInventory().addItem(portal.getItem());

										player.updateInventory();
										PortalManager.delPortal(portal);

									} else
									{

										portal.createPortal();

										player.sendMessage(Msgs.Portals_PortalOpened.getString());

										portal.playEffect();

										// Timer to give portal item back
										Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PortablePortals.me, new Runnable()
										{

											@Override
											public void run() {
												player.getInventory().addItem(portal.getItem());

												player.sendMessage(Msgs.Portals_PortalClosed.getString());
												portal.removePortal();
												PortalManager.delPortal(portal);
											}
										}, Settings.stayOpenTime());

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
