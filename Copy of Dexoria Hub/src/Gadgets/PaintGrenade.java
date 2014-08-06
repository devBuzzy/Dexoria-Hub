package Gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.lewys.com.Hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PaintGrenade implements Listener{
	
	public HashMap<Player, BukkitRunnable> cooldowntask = new HashMap<>();
	public HashMap<Player, Integer> timeleft = new HashMap<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if((e.getPlayer().getItemInHand().getType() != null) && 
				(e.getPlayer().getItemInHand().getType() == Material.EXP_BOTTLE)){
			if(cooldowntask.containsKey(e.getPlayer())){
				e.getPlayer().sendMessage("§2§lGadget" + ChatColor.WHITE + " > You must wait for " + ChatColor.RED + timeleft.get(e.getPlayer()) + ChatColor.WHITE + " seconds.");
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onXPHit(ExpBottleEvent e){
		final Player p = (Player) e.getEntity().getShooter();
		
		if(!cooldowntask.containsKey(p)){
		
		e.setExperience(0);
		p.playSound(p.getLocation(), Sound.SPLASH, 2.0f, 2.0f);
		
		Location startloc = e.getEntity().getLocation();
		
		Location corner1 = startloc.add(2,1,2);
		
		Location corner2 = startloc.subtract(2,1,2);
		
		loopThrough(corner1, corner2);
		timeleft.put(p, 10);
		cooldowntask.put(p, new BukkitRunnable(){

			@Override
			public void run() {
				if(timeleft.get(p) >= 0){
					timeleft.put(p, timeleft.get(p) -1);
				}else{
					timeleft.remove(p);
					cooldowntask.remove(p);
					cancel();
				}
			}
		});
		
		Bukkit.getScheduler().runTaskTimer(Hub.instance, cooldowntask.get(p), 20, 20);
	}
}
	
	
	@SuppressWarnings("deprecation")
	private void loopThrough(Location loc1, Location loc2)
	{
		final List<BlockState> state = new ArrayList<BlockState>();
		
	    int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
	        miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
	        minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
	        maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
	        maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
	        maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
	    
	    for(int x = minx; x<=maxx;x++)
	    {
	        for(int y = miny; y<=maxy;y++)
	        {
	            for(int z = minz; z<=maxz;z++)
	            {
	            	
	            	Location loc = new Location(loc1.getWorld(), x,y,z);
	            	
	            	if(loc.getBlock().getType() != Material.AIR){
	            	state.add(loc.getBlock().getState());
	            	
	            	Random r = new Random(); 
	        		DyeColor c = DyeColor.values()[r.nextInt(DyeColor.values().length)];
	        		
	        		loc.getBlock().setType(Material.WOOL);
	        		loc.getBlock().setData(c.getData());
	        		
	        		Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.instance, new Runnable(){

						@Override
						public void run() {
							for(BlockState states : state){
								states.update();
								state.remove(states);
							}
						}
	        		}, 3 * 20);
	            }
	         }
	        }
	    }
	}
}
