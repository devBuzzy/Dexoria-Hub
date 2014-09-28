package particles;

	import java.util.Random;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

	public class ThunderCloud implements Listener{
		
		/*
		 * just a test, will work on user in the real thing.
		 */
		
		boolean changed = false;
		int lightningstrike = 20;
		double lastX;
		double lastY;
		double lastZ;
		
		@EventHandler
		public void onMove(final PlayerInteractEvent e){
			Random r = new Random();
			lightningstrike = r.nextInt((20*10 - 60) + 1) + 60;
			
			lastX = e.getPlayer().getLocation().getX();
			lastY = e.getPlayer().getLocation().getY();
			lastZ = e.getPlayer().getLocation().getZ();
			
			final Player player = e.getPlayer();


			Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

				@Override
				public void run() {
					
					if(isSame(e.getPlayer().getLocation(), new Location(e.getPlayer().getWorld(), lastX, lastY, lastZ)) == false){
						
						changed = true; 
						ParticleEffect.SPLASH.display(e.getPlayer().getLocation(), 0.1f, 0.1f, 0.1f, 0f, 2);
						lastX = e.getPlayer().getLocation().getX();
						lastY = e.getPlayer().getLocation().getY();
						lastZ = e.getPlayer().getLocation().getZ();
						return;
						
					}else if(isSame(e.getPlayer().getLocation(), new Location(e.getPlayer().getWorld(), lastX, lastY, lastZ)) == true){
						
						if(changed == true){
							changed = false;
							return;
						}else{
							lightningstrike--;
							if(lightningstrike == 0){
								doStrike(player.getLocation().add(0,2.5,0));
								Location abovehead = player.getLocation().add(0,2.5,0);
								ParticleEffect.EXPLODE.display(abovehead, 0.4f, 0.1f, 0.4f, 0f, 40);
							}else{
							
							Location abovehead = player.getLocation().add(0,2.5,0);
							
							ParticleEffect.SMOKE.display(abovehead, 0.4f, 0.1f, 0.4f, 0f, 40);
							ParticleEffect.SPLASH.display(abovehead, 0.3f, 0.1f, 0.4f, 0f, 1);
							ParticleEffect.DRIP_WATER.display(abovehead, 0.3f, 0.1f, 0.4f, 0f, 1);
							}
						}
				}
			}
		}, 1, 1);
	}
		
		public boolean isSame(Location one, Location two){
			if((one.getX() == two.getX()) && (one.getY() == two.getY() && (one.getZ() == two.getZ())))
					return true;
			else return false;
		}
		
		public void doStrike(Location start){	
			Random r = new Random();
			lightningstrike = r.nextInt((20*10 - 60) + 1) + 60;
			start.getWorld().playSound(start, Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
	}
}
