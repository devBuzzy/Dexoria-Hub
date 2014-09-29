package LuckyDip;

import java.util.ArrayList;
import java.util.List;
import me.lewys.com.Currency;
import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class luckydip implements Listener{
	
	String direction;
	
	Location playerLocation;
	Player player;
	Location cauldron;
	Location cauldron_top;
	Location cauldron_inside;
	
	boolean canmove = true;
	
	int lava_task;
	int runnableTask;
	
	int totalitems = 0;
	
	List<PossibleItems> got = new ArrayList<>();
	List<Entity> bats = new ArrayList<>();
	List<Entity> items = new ArrayList<>();
	
	public luckydip(Location playerLoc, Player p) {
		playerLocation = playerLoc;
		player = p;
	}
	
	@SuppressWarnings("deprecation")
	public void doLuckyDip(){
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000,10));
		direction = getCardinalDirection(playerLocation);
		
		if(direction.equalsIgnoreCase("N") || direction.equalsIgnoreCase("NE")){
			cauldron = playerLocation.subtract(0,0,2).getBlock().getLocation();
		}
		
		if(direction.equalsIgnoreCase("E") || direction.equalsIgnoreCase("SE")){
			cauldron = playerLocation.add(2,0,0).getBlock().getLocation();
		}

		if(direction.equalsIgnoreCase("W") || direction.equalsIgnoreCase("SW")){
			cauldron = playerLocation.subtract(2,0,0).getBlock().getLocation();
		}

		if(direction.equalsIgnoreCase("N") || direction.equalsIgnoreCase("NW")){
			cauldron = playerLocation.add(0,0,2).getBlock().getLocation();
		}
		
		
		if(cauldron.getBlock().getType() != Material.AIR)
			return;
		
		cauldron_top = cauldron.add(0.5,1,0.5);
		cauldron_inside = cauldron_top.subtract(0,0.2,0);
		
		Location middle = playerLocation.subtract(0,1,0).getBlock().getLocation().add(0.5,1,0.5);
		player.teleport(middle);
		
		cauldron.getBlock().setType(Material.CAULDRON);
		
		runnableTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				
				if(totalitems == 3){
					stopLava();
				}
				
				if(totalitems == 4){
					end();
				}
				
				totalitems++;
				
				doRandomItemPopOut();
			}
			
		}, 20 * 2, 20 * 2);
		
		canmove = false;
	}
	
	@SuppressWarnings("deprecation")
	public void startLava(){
		lava_task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				ParticleEffect.LAVA.display(cauldron_inside, 0.1f, 0f, 0.1f, 0.2f, 2);
			}
			
		}, 5, 5);
	}
	
	public void stopLava(){
		Bukkit.getScheduler().cancelTask(lava_task);
	}
	
	public void end(){
		Bukkit.getScheduler().cancelTask(runnableTask);
		
		/*/
		 * Clear memory
		 */
		player = null;
		direction = null;
		cauldron = null;
		cauldron_top = null;
		cauldron_inside = null;
		playerLocation = null;
		canmove = true;		
		HandlerList.unregisterAll(this);
		
		player.removePotionEffect(PotionEffectType.SPEED);
		cauldron.getBlock().setType(Material.AIR);
		
	}
	
	public void doRandomItemPopOut() {
		
		
	}
	
	/*
	 * Thanks to sk89q for this method.
	 */
	public static String getCardinalDirection(Location loc) {
        double rotation = (loc.getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) {
            return "N";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
	}
	
	public void doubleGameCurrency(String UUID){
		Currency.addGC(UUID, Currency.getGC(UUID) * 2);
	}
	
	public void doubleCosmeticCurrency(String UUID){
		Currency.addCC(UUID, Currency.getCC(UUID) * 2);
	}
	
	public boolean hasBeenPicked(PossibleItems item){
		return got.contains(item);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(e.getPlayer() == player){
		Location from = e.getFrom();
		Location to = e.getTo();
		
		if(from.getX() != to.getX() || from.getZ() != to.getZ())
			e.getPlayer().teleport(from);
		}
	}
}