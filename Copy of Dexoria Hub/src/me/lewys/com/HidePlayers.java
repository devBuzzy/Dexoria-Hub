package me.lewys.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class HidePlayers implements Listener{
	
	public List<String> enabled = new ArrayList<>();
	public HashMap<String, BukkitRunnable> task = new HashMap<>();
	public HashMap<String, Double> cooldown = new HashMap<>();
	
	public ItemStack player_enabled(){
		ItemStack is = new ItemStack(Material.TORCH);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Players Enabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle player visibility");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack player_disabled(){
		ItemStack is = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Players Disabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle player visibility");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		if(e.getPlayer().getItemInHand().getType() != null){
			if(e.getPlayer().getItemInHand().getType() == Material.TORCH){
				e.setCancelled(true);
				if(cooldown.containsKey(p.getName())){
					p.sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY
							+ "You must wait " + ChatColor.RED + cooldown.get(p.getName()
							) + ChatColor.GRAY + " before toggling players again.");
					
					return;
				}
				
				p.sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY 
						+ "Players are now: " + ChatColor.RED + "Disabled");
				
				for(Player hide : Bukkit.getOnlinePlayers()){
					p.hidePlayer(hide);
				}
				
				cooldown.put(p.getName(), 10.0);
				
				task.put(p.getName(), new BukkitRunnable() {

					@Override
					public void run() {
						if(cooldown.get(p.getName()) != 0.0){
							cooldown.put(p.getName(), cooldown.get(p.getName()) - 0.5);
						}else{
							cooldown.remove(p.getName());
							task.remove(p.getName());
							this.cancel();
						}
					}
				});
				
				task.get(p.getName()).runTaskTimer(Hub.instance, 10, 10);
				
				p.getInventory().setItem(8, new ItemStack(Material.AIR));
				p.getInventory().setItem(8, player_disabled());
				p.updateInventory();
				
				return;
			}else
			
			if(e.getPlayer().getItemInHand().getType() == Material.REDSTONE_TORCH_ON){
				e.setCancelled(true);
				if(cooldown.containsKey(p.getName())){
					p.sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY
							+ "You must wait " + ChatColor.RED + cooldown.get(p.getName()
							) + ChatColor.GRAY + " before toggling players again.");
					
					return;
				}
				
				p.sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY 
						+ "Players are now: " + ChatColor.GREEN + "Enabled");
				
				for(Player hide : Bukkit.getOnlinePlayers()){
					p.showPlayer(hide);
				}
				
				cooldown.put(p.getName(), 10.0);
				
				task.put(p.getName(), new BukkitRunnable() {

					@Override
					public void run() {
						if(cooldown.get(p.getName()) != 0.0){
							cooldown.put(p.getName(), cooldown.get(p.getName()) - 0.5);
						}else{
							cooldown.remove(p.getName());
							task.remove(p.getName());
							this.cancel();
						}
					}
				});
				
				task.get(p.getName()).runTaskTimer(Hub.instance, 10, 10);
				
				p.getInventory().setItem(8, new ItemStack(Material.AIR));
				p.getInventory().setItem(8, player_enabled());
				p.updateInventory();
			}
		}
	}

	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		if(enabled.contains(e.getPlayer().getName())){
			enabled.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e){
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()){
					e.getPlayer().showPlayer(p);
				}
				
				for(String shid : enabled){
					Player hhid = Bukkit.getPlayer(shid);
					
					hhid.hidePlayer(e.getPlayer());
				}
			}
			
		}, 20);
	}
}
