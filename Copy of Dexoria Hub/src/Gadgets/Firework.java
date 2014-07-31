package Gadgets;

import java.util.HashMap;

import me.lewys.com.Hub;
import me.lewys.com.Points;
import me.lewys.com.RandomFirework;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Firework implements Listener{
	
	 private HashMap<Player, Double> cooldownTime = new HashMap<Player, Double>();
	 private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<Player, BukkitRunnable>();

    
	@EventHandler
	public void onFireworkLaunch(final PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getItemInHand().getType() == Material.FIREWORK){
			
				e.setCancelled(true);
				
				if(cooldownTime.containsKey(e.getPlayer())){
					e.getPlayer().sendMessage("§2§lGadget" + ChatColor.WHITE + " > You must wait for " + ChatColor.RED + cooldownTime.get(e.getPlayer()) + ChatColor.WHITE + " seconds.");
					return;
				}	
				
				if(Points.hasEnough(e.getPlayer().getName(), 10)){
					Points.removePoints(e.getPlayer().getName(), 10);
					
					RandomFirework.getManager().launchRandomFirework(e.getPlayer().getLocation());
					
					final Player p = e.getPlayer();
					
					cooldownTime.put(p, 2.0);
                    cooldownTask.put(p, new BukkitRunnable() {
                            public void run() {
                                    cooldownTime.put(p, cooldownTime.get(p) - 0.5);
                                    if (cooldownTime.get(p) == 0) {
                                            cooldownTime.remove(p);
                                            cooldownTask.remove(p);
                                            cancel();
                                    }
                            }
                    });
                   
                    cooldownTask.get(p).runTaskTimer(Hub.getPluginInstance(), 10, 10);

						}else{
							e.getPlayer().sendMessage("§2§lGadget" + ChatColor.WHITE + " > " + ChatColor.GRAY + "You don't have enough points!");
						}
					}
			}
		}
}
