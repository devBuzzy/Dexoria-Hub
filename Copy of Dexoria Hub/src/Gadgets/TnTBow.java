package Gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.lewys.com.Hub;
import me.lewys.com.Points;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TnTBow implements Listener{

	 private HashMap<Player, Double> cooldownTime = new HashMap<Player, Double>();
	 private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<Player, BukkitRunnable>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void bowFire(ProjectileLaunchEvent e){
		if((e.getEntity().getShooter() instanceof Player) 
				&& (e.getEntity() instanceof Arrow)){
			e.setCancelled(true);
			
			final Player p = (Player) e.getEntity().getShooter();
			
			if(Points.hasEnough(p.getName(), 100)){
				
			if(cooldownTime.containsKey(p)){
				p.sendMessage("§2§lGadget" + ChatColor.WHITE + " > You must wait for " + ChatColor.RED + cooldownTime.get(p) + ChatColor.WHITE + " seconds.");
				return;
			}	
			
			Points.removePoints(p.getName(), 100);
			
			Entity tnt = p.getWorld().spawnEntity(p.getLocation().add(0,1,0), EntityType.PRIMED_TNT);
			tnt.setVelocity(p.getLocation().getDirection().multiply(2));
			
			cooldownTime.put(p, 10.0);
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
			p.sendMessage("§2§lGadget" + ChatColor.WHITE + " > " + ChatColor.GRAY + "You don't have enough points!");
		}
	}
}	
	@EventHandler
	public void tntEcplode(final EntityExplodeEvent e){
		
		List<Entity> nearby = new ArrayList<>();
		e.setCancelled(true);

		ParticleEffect.EXPLODE.display(e.getLocation(), 1.5f, 0.5f, 1.5f, 0.2f, 7);
		ParticleEffect.LARGE_EXPLODE.display(e.getLocation(), 2f, 1f, 2f, 4f, 2);

		for(Entity p : e.getEntity().getNearbyEntities(5d, 5d, 5d)){
			if(!(p instanceof Player))
				continue;
			
			nearby.add(p);
		}
		
		for(final Entity p : nearby){
			p.setVelocity(p.getLocation().getDirection().multiply(-1.5d).add(new Vector(0,1.5,0)));
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.getPluginInstance(), new Runnable(){

				@Override
				public void run() {
					Firework f = (Firework) p.getWorld().spawn(p.getLocation(), Firework.class);
					FireworkMeta fm = f.getFireworkMeta();
					fm.addEffect(FireworkEffect.builder().flicker(true).trail(false).with(Type.BALL).withColor(Color.RED).build());
					f.setFireworkMeta(fm);
					f.detonate();
				}			
			}, 20);		
		}
	}
}
