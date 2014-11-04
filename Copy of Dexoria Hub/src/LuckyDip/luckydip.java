package LuckyDip;

import java.util.ArrayList;
import java.util.List;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dexoria.core.DexCore;

public class luckydip implements Listener{
	
	LuckyDipManager ldm = new LuckyDipManager();
	
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
		startLava();
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000,10));
		
		
		if(player.getLocation().add(2,0,0).getBlock().getType() == Material.AIR){
			cauldron = player.getLocation().add(2,0,0).getBlock().getLocation();
			player.teleport(new Location(player.getLocation().getWorld(), cauldron.getX() -2, player.getLocation().getY(), player.getLocation().getZ(), -90, player.getLocation().getPitch()));
			
		}else if(player.getLocation().subtract(2,0,0).getBlock().getType() == Material.AIR){
			cauldron = player.getLocation().subtract(2,0,0).getBlock().getLocation();
			player.teleport(new Location(player.getLocation().getWorld(), cauldron.getX() + 2, player.getLocation().getY(), player.getLocation().getZ(), 90, player.getLocation().getPitch()));
			
		}else if(player.getLocation().add(0,0,2).getBlock().getType() == Material.AIR){
			cauldron = player.getLocation().add(0,0,2).getBlock().getLocation();
			player.teleport(new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY(), cauldron.getZ() - 2, 0, player.getLocation().getPitch()));
			
		}else if(player.getLocation().subtract(0,0,2).getBlock().getType() == Material.AIR){
			cauldron = player.getLocation().subtract(0,0,2).getBlock().getLocation();
			player.teleport(new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY(), cauldron.getZ() + 2, 180, player.getLocation().getPitch()));
		
		}else{
			return;
		}
		
		if(cauldron.getBlock().getType() != Material.AIR)
			return;
		
		cauldron_top = cauldron.add(0.5,1,0.5);
		cauldron_inside = cauldron_top.subtract(0,0.2,0);
		
		ldm.addActivePlayer(player.getName(), this);
		cauldron.getBlock().setType(Material.CAULDRON);
		canmove = false;
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
				
			}
			
		}, 20 * 4, 20 * 4);
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
		cauldron.getBlock().setType(Material.AIR);
		player.removePotionEffect(PotionEffectType.SPEED);
		player = null;
		cauldron = null;
		cauldron_top = null;
		cauldron_inside = null;
		playerLocation = null;
		canmove = true;		
	}
	
	public void doRandomItemPopOut() {
		Location loc = null;
        
	}
	
	public void doubleGameCurrency(String UUID){
		DexCore.getCurrencySystem().addGC(UUID, DexCore.getCurrencySystem().getGC(UUID) * 2);
	}
	
	public void doubleCosmeticCurrency(String UUID){
		DexCore.getCurrencySystem().addCC(UUID, DexCore.getCurrencySystem().getCC(UUID) * 2);
	}
	
	public boolean hasBeenPicked(PossibleItems item){
		return got.contains(item);
	}
}