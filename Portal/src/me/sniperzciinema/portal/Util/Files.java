
package me.sniperzciinema.portal.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import me.sniperzciinema.portal.PortablePortals;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Files {

	public static YamlConfiguration	messages		= null;
	public static File				messagesFile	= null;

	public static FileConfiguration getConfig() {
		return PortablePortals.me.getConfig();
	}

	// Get Arenas File
	public static FileConfiguration getMessages() {
		if (Files.messages == null)
			reloadMessages();
		return Files.messages;
	}

	public static void reloadAll() {
		reloadConfig();
		reloadMessages();
	}

	public static void reloadConfig() {
		PortablePortals.me.reloadConfig();
	}

	// Reload Arenas File
	public static void reloadMessages() {
		if (Files.messages == null)
			Files.messagesFile = new File(
					Bukkit.getPluginManager().getPlugin("Portable-Portals").getDataFolder(),
					"Messages.yml");
		Files.messages = YamlConfiguration.loadConfiguration(Files.messagesFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Portable-Portals").getResource("Messages.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			Files.messages.setDefaults(defConfig);
		}
	}

	// //////////////////////////////////////////////////////////////////////////////
	// MESSAGES

	public static void saveAll() {
		saveConfig();
		saveMessages();
	}

	public static void saveConfig() {
		PortablePortals.me.saveConfig();
	}

	// Safe Arenas File
	public static void saveMessages() {
		if ((Files.messages == null) || (Files.messagesFile == null))
			return;
		try
		{
			getMessages().save(Files.messagesFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + Files.messagesFile, ex);
		}
	}

}
