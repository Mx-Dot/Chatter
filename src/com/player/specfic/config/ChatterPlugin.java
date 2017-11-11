package com.player.specfic.config;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatterPlugin extends JavaPlugin implements Listener
{
	boolean isLocked = false;

	Logger myPluginLogger = Bukkit.getLogger();

	@Override
	public void onEnable() 
	{
		loadConfig();
		PluginDescriptionFile pdfFile = getDescription();
		myPluginLogger.info("Chatter is enabled Version: " + pdfFile.getVersion());
		myPluginLogger.info("Getting necessary resources");

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() 
	{
		PluginDescriptionFile pdfFile = getDescription();
		myPluginLogger.info("Chatter is disabled");
		{

		}
	}
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player) sender;
		if(sender.hasPermission("chatter.chat.clear"))
			if(cmd.getName().equalsIgnoreCase("clearchat"))
				
			{
				for (int x = 0; x < 150; x++) {
					Bukkit.broadcastMessage(" ");
				}
				Bukkit.broadcastMessage(ChatColor.GOLD + "|-----------------+====+-----------------|");
				Bukkit.broadcastMessage(ChatColor.GRAY + "Chat has been cleared by: " + ChatColor.WHITE+ p.getName());
				Bukkit.broadcastMessage(ChatColor.GOLD + "|-----------------+====+-----------------|"); }


			else if (cmd.getName().equalsIgnoreCase("chatlock")) {
				if (p.hasPermission("chatter.chat.lock")) {
					if (isLocked != true) {
						isLocked = true;
						Bukkit.broadcastMessage(ChatColor.RED
								+ "Chat has been muted by: " + ChatColor.WHITE + p.getName());

					} else {
						p.sendMessage(ChatColor.RED + "Chat is already Muted");
					}

				} else {
					p.sendMessage(ChatColor.RED
							+ "You do Not have Permission to do this Command");
				}
			}

			else if (cmd.getName().equalsIgnoreCase("chatunlock")) {
				if (p.hasPermission("chatter.chat.unlock")) {
					if (isLocked != false) {
						isLocked = false;
						Bukkit.broadcastMessage(ChatColor.GREEN
								+ "Chat has been Un-Muted by: " + ChatColor.WHITE + p.getName());
					} else {
						p.sendMessage(ChatColor.RED
								+ "Chat is currently not Muted");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You do Not have Permission to do this Command");
				}
			}
		return true;
	}

	@EventHandler
	public void onType(AsyncPlayerChatEvent event) {
		if (isLocked != false
				&& !event.getPlayer().hasPermission("chatter.chat.bypass.lock")) {
			event.setCancelled(true);
			event.getPlayer()
			.sendMessage(
					ChatColor.RED + "Chat is currently muted");
		}
	}
}


