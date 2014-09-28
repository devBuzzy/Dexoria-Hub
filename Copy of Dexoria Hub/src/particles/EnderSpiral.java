package particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderSpiral implements Listener{
	
	public EnderSpiral(){
	}
	
	int wich = 1;
	boolean changed = false;
	double lastX;
	double lastY;
	double lastZ;
	double y = 0.0;
	
	int task;
	
	boolean delete;
	int locamount;
	
	List<Location> locs = new ArrayList<Location>();
	
	public void start(final Player p){
		locamount = 0;
		
		lastX = p.getLocation().getX();
		lastY = p.getLocation().getY();
		lastZ = p.getLocation().getZ();
		
		y=0;
		for (double t = 0; t < 2*Math.PI; t+=Math.toRadians(6)){
			y +=0.02;
			  Location l = p.getLocation().add(Math.cos(t), y, Math.sin(t));
			  
			  if(delete == true){
			  locs.add(l);
			  delete = false;
			  }else{
				  delete = true;
			  }
			}
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				if(isSame(p.getLocation(), new Location(p.getWorld(), lastX, lastY, lastZ)) == false){
					
					changed = true; 
					ParticleEffect.PORTAL.display(p.getLocation(), 0.5f, 0.5f, 0.5f, 0f, 2);
					lastX = p.getLocation().getX();
					lastY = p.getLocation().getY();
					lastZ = p.getLocation().getZ();
					locs.clear();
					changed = true;
					return;
					
				}else if(isSame(p.getLocation(), new Location(p.getWorld(), lastX, lastY, lastZ)) == true){
					
					if(changed == true){
						locs.clear();
						y = 0;
						for (double t = 0; t < 2*Math.PI; t+=Math.toRadians(6)){
								y +=0.02;
							  Location l = p.getLocation().add(Math.cos(t), y, Math.sin(t));
							  
							  if(delete == true){
								  locs.add(l);
								  delete = false;
								  }else{
									  delete = true;
								  }
							}
						
					}
					
					if(locs.isEmpty())
						return;
					
					if(wich < locamount){
						Location finalloc = locs.get(wich);
						ParticleEffect.PORTAL.display(finalloc, 0f, 0f, 0f, 0f, 3);
						wich++;
					}else{
						wich=1;	
				}
			}
		}
	}, 1, 1);
}
	public void stop(){
		Bukkit.getScheduler().cancelTask(task);
	}
	
	public boolean isSame(Location one, Location two){
		if((one.getX() == two.getX()) && (one.getY() == two.getY() && (one.getZ() == two.getZ())))
				return true;
		else return false;
	}
}
