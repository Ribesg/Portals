
package me.sniperzciinema.portal;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.sniperzciinema.portal.Direction;


public class Portal {

	private Location location;
	private ItemStack item;
	private Location target;
	private Player thrower;

	private int effectTimer;
	private int effect;
	private boolean direction = false;

	public Portal(Location location, Location target, ItemStack item,
			Player thrower)
	{
		this.location = location;
		this.target = target;
		this.item = item;
		this.thrower = thrower;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the item
	 */
	public ItemStack getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemStack item) {
		this.item = item;
	}

	/**
	 * @return the target
	 */
	public Location getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(Location target) {
		this.target = target;
	}

	/**
	 * @return the thrower
	 */
	public Player getThrower() {
		return thrower;
	}

	/**
	 * @param thrower
	 *            the thrower to set
	 */
	public void setThrower(Player thrower) {
		this.thrower = thrower;
	}

	public boolean isBlock(Location loc) {
		loc = PortalManager.getRoundedLocation(loc);
		Location location2 = location.clone();
		int i = 0;
		Location locs = location2.add(-1, 0, 0);
		for (i = 0; i != 3; i++)
		{
			if (locs == location2.add(0, 1, 0))
				return true;
			if (locs == location2.add(0, 2, 0))
				return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void createPortal() {
		Location location2 = location.clone();
		String d = Direction.getCardinalDirection(thrower);
		if (d.contains("East") || d.contains("West"))
		{
			location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY(), (int) location2.getZ()).setType(Material.COBBLE_WALL);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()).setType(Material.getMaterial(36));
			location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY(), (int) location2.getZ()).setType(Material.COBBLE_WALL);

			location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.COBBLE_WALL);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.getMaterial(36));
			location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.COBBLE_WALL);

			location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.STEP);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.STEP);
			location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.STEP);
		} else
		{
			direction = true;
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ() - 1).setType(Material.COBBLE_WALL);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()).setType(Material.getMaterial(36));
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ() + 1).setType(Material.COBBLE_WALL);

			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ() - 1).setType(Material.COBBLE_WALL);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.getMaterial(36));
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ() + 1).setType(Material.COBBLE_WALL);

			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ() - 1).setType(Material.STEP);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.STEP);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ() + 1).setType(Material.STEP);

		}
	}

	public void removePortal() {
		Location location2 = location.clone();
		if (!direction)
		{
			location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY(), (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY(), (int) location2.getZ()).setType(Material.AIR);

			location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.AIR);

			location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.AIR);
		} else
		{
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ() - 1).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ() + 1).setType(Material.AIR);

			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ() - 1).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ() + 1).setType(Material.AIR);

			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ() - 1).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()).setType(Material.AIR);
			location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ() + 1).setType(Material.AIR);

		}
	}

	public boolean canCreatePortal() {
		Location location2 = location.clone();

		String d = Direction.getCardinalDirection(thrower);
		if (d.contains("East") || d.contains("West"))
		{
			if (location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY(), (int) location2.getZ()).getType() == Material.AIR)
				if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()).getType() == Material.AIR)
					if (location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY(), (int) location2.getZ()).getType() == Material.AIR)

						if (location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY() + 1, (int) location2.getZ()).getType() == Material.AIR)
							if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()).getType() == Material.AIR)
								if (location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY() + 1, (int) location2.getZ()).getType() == Material.AIR)

									if (location2.getWorld().getBlockAt((int) location2.getX() - 1, (int) location2.getY() + 2, (int) location2.getZ()).getType() == Material.AIR)
										if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()).getType() == Material.AIR)
											if (location2.getWorld().getBlockAt((int) location2.getX() + 1, (int) location2.getY() + 2, (int) location2.getZ()).getType() == Material.AIR)
												return true;
		}
		else{
			if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()-1).getType() == Material.AIR)
				if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()).getType() == Material.AIR)
					if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY(), (int) location2.getZ()+1).getType() == Material.AIR)

						if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()-1).getType() == Material.AIR)
							if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()).getType() == Material.AIR)
								if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 1, (int) location2.getZ()+1).getType() == Material.AIR)

									if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()-1).getType() == Material.AIR)
										if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()).getType() == Material.AIR)
											if (location2.getWorld().getBlockAt((int) location2.getX(), (int) location2.getY() + 2, (int) location2.getZ()+1).getType() == Material.AIR)
												return true;
					
		}
		return false;
	}

	public void playEffect() {
		final Location location2 = location.clone();
		effectTimer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PortablePortals.me, new Runnable()
		{

			@Override
			public void run() {
				if (effect != 10)
				{
					location2.getWorld().playEffect(location2.clone().add(0, 1, 0), Effect.ENDER_SIGNAL, 5);
					location2.getWorld().playEffect(location2.clone(), Effect.ENDER_SIGNAL, 5);
					effect++;
				} else
					Bukkit.getScheduler().cancelTask(effectTimer);
			}
		}, 0, 20);
	}
}
