package Gadgets;

import java.util.HashMap;
import java.util.Random;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dexoria.core.DexCore;


public class PaintGrenade implements Listener{
	
	public HashMap<String, BukkitRunnable> cooldowntask = new HashMap<String, BukkitRunnable>();
	public HashMap<String, Double> timeleft = new HashMap<String, Double>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getPlayer().getItemInHand().getType() != null){
			if(e.getPlayer().getItemInHand().getType() == Material.EXP_BOTTLE){
				
				e.setCancelled(true);
				e.getPlayer().updateInventory();
				if(DexCore.getCurrencySystem().hasEnoughGC(e.getPlayer().getUniqueId().toString(), 30)){
					DexCore.getCurrencySystem().removeGC(e.getPlayer().getUniqueId().toString(), 30);
				}else{
					e.getPlayer().sendMessage(ChatColor.BLUE + "Gadget > " + ChatColor.GRAY + "You don't have enough points!");
					return;
				}
				
				
				if(!cooldowntask.containsKey(e.getPlayer().getName())){
					final Player p = e.getPlayer();
					p.launchProjectile(ThrownExpBottle.class, 
							p.getLocation().getDirection().multiply(1));
					
					timeleft.put(p.getName(), 10.0);
					cooldowntask.put(p.getName(), new BukkitRunnable() {

						@Override
						public void run() {
							if(timeleft.get(p.getName()) == 0.0){
								cooldowntask.remove(p.getName());
								timeleft.remove(p.getName());
								cancel();
							}else{
								timeleft.put(p.getName(), timeleft.get(p.getName()) - 0.5);
							}
						}
					});
					
					cooldowntask.get(p.getName()).runTaskTimer(Hub.instance, 10, 10);
					
				}else{
					e.getPlayer().sendMessage(ChatColor.BLUE + "Gadget > " + ChatColor.GRAY + " You must wait for " + ChatColor.RED + timeleft.get(e.getPlayer().getName()) + ChatColor.GRAY + " seconds.");
				}
			}
		}
	}
	
	
	@EventHandler
	public void onXPHit(ProjectileHitEvent e){
		
		if(e.getEntity() instanceof ThrownExpBottle){
		final Location startloc = e.getEntity().getLocation();
		
		Location corner1 = startloc.add(2,1,2);
		
		Location corner2 = startloc.subtract(2,1,2);
		
		loopThrough(corner1, corner2, (Player) e.getEntity().getShooter());
		}
	}
	
	
	@SuppressWarnings("deprecation")
	private void loopThrough(Location loc1, Location loc2, final Player p)
	{
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
	            		continue;
	            	}
	            	
	            	final BlockState state = loc.getBlock().getState();
	            	
	            	Random r = new Random(); 
	        		DyeColor c = DyeColor.values()[r.nextInt(DyeColor.values().length)];
	        		
	        		loc.getBlock().setType(Material.WOOL);
	        		loc.getBlock().setData(c.getData());
	        		ParticleEffect.INSTANT_SPELL.display(loc.add(0,1,0), 0.5f, 0.5f, 0.5f, 0.05f, 5);
	        		
	        		 Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.instance, new Runnable(){

	        				@Override
	        				public void run() {
	        					state.update(true);
	        				}
	        			}, 4 * 20);
	            }
	        }
	    }
	}
}
