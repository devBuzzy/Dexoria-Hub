package EventManager;

import java.util.ArrayList;
import java.util.List;

import me.lewys.com.Hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Join implements Listener{
	
	public ItemStack gadgetmenu(){
		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Gadgets");
		
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack compass(){
		ItemStack is = new ItemStack(Material.COMPASS);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Game Selector");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Chose a game to play!");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
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
		ItemStack is = new ItemStack(Material.REDSTONE_TORCH_OFF);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Players Disabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle player visibility");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Location hubloc = new Location(Bukkit.getWorld("hub"), -843.5, 12, 22);
		final Player p = e.getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				p.teleport(hubloc);
			}
		}, 3);
		
		p.getInventory().clear();
		
		p.getInventory().getHelmet().setType(null);
		p.getInventory().getChestplate().setType(null);
		p.getInventory().getLeggings().setType(null);
		p.getInventory().getBoots().setType(null);
		
		p.getInventory().setItem(0, compass());
		p.getInventory().setItem(4, gadgetmenu());
		p.getInventory().setItem(7, games_enabled());
		p.getInventory().setItem(8, player_enabled());
		
		
		Bukkit.broadcastMessage(ChatColor.BLUE + "Join >" + ChatColor.GRAY + " Player " +
		e.getPlayer().getName() + " Has joined the dexoria network");
	}
}
