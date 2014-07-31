package me.lewys.com;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Protection implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if((e.getPlayer().isOp()  || (e.getPlayer().getGameMode() == GameMode.CREATIVE)))
			return;
		
		e.setCancelled(true);		
		
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e){
		if(e.getSpawnReason() == SpawnReason.CUSTOM)
			return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if((e.getPlayer().isOp() || (e.getPlayer().getGameMode() == GameMode.CREATIVE)))
			return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e){
		e.setFoodLevel(20);
		e.setCancelled(true);
	}
}
