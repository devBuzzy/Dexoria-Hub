package EventManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Interact implements Listener{

	@EventHandler
	public void i(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		
		if(!(ent instanceof Villager)){
			return;
		}
		e.setCancelled(true);
		Villager v = (Villager) ent;
		
		if(v.getCustomName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + "Mineageddon")){
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "--------" + ChatColor.RED + "" + ChatColor.BOLD + "Mineageddon" + ChatColor.AQUA + "" + ChatColor.BOLD + "--------");
			p.sendMessage(ChatColor.BLUE + "Description: " + ChatColor.GRAY + "Use our amazing custom items to kill other players whilst destorying everything!");
			p.sendMessage(ChatColor.BLUE + "Category: " + ChatColor.GRAY + "Kit PvP");
			p.sendMessage(ChatColor.BLUE + "Coded by: " + ChatColor.GRAY + "nxsupert");
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "------------------" + ChatColor.AQUA + "" + ChatColor.BOLD + "--------");
		}	
	}
}
