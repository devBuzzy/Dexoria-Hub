package me.lewys.com;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Gadgets.GadgetManager;

public class GameToggler implements Listener{
	
	public ItemStack games_enabled(){
		ItemStack is = new ItemStack(Material.SLIME_BALL);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Hub Games Enabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle being effected by gadgets");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack games_disabled(){
		ItemStack is = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Hub Games Disabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle being effected by gadgets");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		
		if(p.getItemInHand().getType() != null){
			if(p.getItemInHand().getType() == Material.SLIME_BALL){
				GadgetManager.addImmunity(p.getName());
				p.sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY 
						+ "Hub games are now: " + ChatColor.GREEN + "Enabled");
				p.getInventory().setItem(7, games_disabled());
			}
			if(p.getItemInHand().getType() == Material.MAGMA_CREAM){
				GadgetManager.removeImmunity(p.getName());
				
				p.sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY 
						+ "Hub games are now: " + ChatColor.RED + "Disabled");
				
				p.getInventory().setItem(7, games_enabled());
			}
		}
	}
}
