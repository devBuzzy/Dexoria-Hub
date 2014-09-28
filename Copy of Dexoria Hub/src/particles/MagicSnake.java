package particles;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicSnake implements Listener{

		
		/*
		 * just a test, will work on user in the real thing.
		 */
		
		double sin = 0;
		boolean change = false;
		@EventHandler
		public void onMove(final PlayerInteractEvent e){
			
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

				@Override
				public void run() {
					if(change == false)
					sin+=0.1;
					
					if(change == true)
						sin-=0.1;
					
					if(sin == -0.7){
						change = false;
					}
					if(sin == 0.7){
						change = true;
					}
					
					Location newloc = e.getPlayer().getLocation();
					double amout = Math.sin(sin);
					
					if(sin >= 0){
						newloc.add(0,amout,0);
					}else if(sin < 0){
						newloc.subtract(0,amout,0);
					}
					
					ParticleEffect.MAGIC_CRIT.display(newloc.add(0,0.2,0), 0f, 0f, 0f, 0f, 2);
			}
		}, 0, 2);
	}
}
