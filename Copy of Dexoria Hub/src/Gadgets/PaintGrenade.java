package Gadgets;

import java.util.HashMap;
import java.util.Random;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PaintGrenade implements Listener{
	
	public HashMap<String, BukkitRunnable> cooldowntask = new HashMap<String, BukkitRunnable>();
	public HashMap<String, Double> timeleft = new HashMap<String, Double>();
	
	
	@EventHandler
	public void onXPHit(ExpBottleEvent e){
		
		e.setExperience(0);
		
		Location startloc = e.getEntity().getLocation();
		
		Location corner1 = startloc.add(2,1,2);
		
		Location corner2 = startloc.subtract(2,1,2);
		
		loopThrough(corner1, corner2);
	}
	
	
	@SuppressWarnings("deprecation")
	private void loopThrough(Location loc1, Location loc2)
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
	            	final BlockState state = loc.getBlock().getState();
	            	
	            	Random r = new Random(); 
	        		DyeColor c = DyeColor.values()[r.nextInt(DyeColor.values().length)];
	        		
	        		if(r.nextBoolean() == false)
	        			continue;
	        		
	        		loc.getBlock().setType(Material.WOOL);
	        		loc.getBlock().setData(c.getData());
	        		ParticleEffect.INSTANT_SPELL.display(loc.add(0,1,0), 0.5f, 0.5f, 0.5f, 0.05f, 5);
	        		
	        		Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.instance, new Runnable(){

						@Override
						public void run() {
							state.update();
						}
	        		}, 3 * 20);
	            }
	         }
	        }
	    }
	}
}
