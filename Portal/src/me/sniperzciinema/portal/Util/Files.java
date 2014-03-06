
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

	public static YamlConfiguration messages = null;
	public static File messagesFile = null;

	public static FileConfiguration getConfig() {
		return PortablePortals.me.getConfig();
	}

	public static void saveConfig() {
		PortablePortals.me.saveConfig();
	}

	public static void reloadConfig() {
		PortablePortals.me.reloadConfig();
	}

	public static void saveAll() {
		saveConfig();
		saveMessages();
	}

	public static void reloadAll() {
		reloadConfig();
		reloadMessages();
	}

	// //////////////////////////////////////////////////////////////////////////////
	// MESSAGES

	// Reload Arenas File
	public static void reloadMessages() {
		if (messages == null)
			messagesFile = new File(
					Bukkit.getPluginManager().getPlugin("Portable-Portals").getDataFolder(),
					"Messages.yml");
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Portable-Portals").getResource("Messages.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			messages.setDefaults(defConfig);
		}
	}

	// Get Arenas File
	public static FileConfiguration getMessages() {
		if (messages == null)
			reloadMessages();
		return messages;
	}

	// Safe Arenas File
	public static void saveMessages() {
		if (messages == null || messagesFile == null)
			return;
		try
		{
			getMessages().save(messagesFile);
		} catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + messagesFile, ex);
		}
	}

}