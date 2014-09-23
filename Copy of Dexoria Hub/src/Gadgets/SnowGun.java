package Gadgets;

import java.util.ArrayList;
import java.util.List;

import me.lewys.com.Currency;
import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


public class SnowGun implements Listener{

	List<String> hit = new ArrayList<String>();
	
	@EventHandler
	public void onShovelClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR){
			if(e.getPlayer().getItemInHand().getType() == Material.IRON_BARDING){
				if(GadgetManager.isImmune(e.getPlayer().getName())){
					e.getPlayer().sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY
							+ "You are not playing hub games.");
					return;
				}
				
				if(Currency.hasEnoughGC(e.getPlayer().getUniqueId().toString(), 2)){
					Currency.removeGC(e.getPlayer().getUniqueId().toString(), 2);
					e.getPlayer().launchProjectile(Snowball.class, e.getPlayer().getLocation().getDirection().multiply(2));
				}
			}
		}
	}
	
	@EventHandler
	public void projectilehit(EntityDamageByEntityEvent e){
		if((e.getEntity() instanceof Player) && (e.getDamager() instanceof Snowball)){
			Player hitp = (Player) e.getEntity();
			
			if(GadgetManager.isImmune(hitp.getName())){
				Snowball s = (Snowball) e.getDamager();
				Player shooter = (Player) s.getShooter();
				
				shooter.sendMessage(ChatColor.BLUE + "Hub > " 
				+ ChatColor.GRAY + "Player " + ChatColor.RED + hitp.getName() + ChatColor.GRAY
				+ " is not playing hub games!");
				return;
			}
			if(hit.contains(hitp.getName()))
					return;
			
			doSnowBallHit(hitp);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void doSnowBallHit(final Player p){
		
		hit.add(p.getName());
		p.playSound(p.getLocation(), Sound.DIG_SNOW, 2.0f, 2.0f);
		
		ItemStack hel = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta meta = (LeatherArmorMeta) hel.getItemMeta();
		meta.setColor(Color.WHITE);
		hel.setItemMeta(meta);
		
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) chest.getItemMeta();
		meta2.setColor(Color.WHITE);
		chest.setItemMeta(meta2);
		
		ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta meta3 = (LeatherArmorMeta) leg.getItemMeta();
		meta3.setColor(Color.WHITE);
		leg.setItemMeta(meta3);
		
		ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta meta4 = (LeatherArmorMeta) boot.getItemMeta();
		meta4.setColor(Color.WHITE);
		boot.setItemMeta(meta4);
		
		p.getInventory().setHelmet(hel);
		p.getInventory().setChestplate(chest);
		p.getInventory().setLeggings(leg);
		p.getInventory().setBoots(boot);
		
		p.updateInventory();
		
		ParticleEffect.SNOW_SHOVEL.display(p.getLocation().add(0,1,0), 0.2f, 0.5f, 0.2f, 0.05f, 20);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.getPluginInstance(), new Runnable(){

			public void run() {
				hit.remove(p.getName());
				p.getInventory().setHelmet(new ItemStack(Material.AIR));
				p.getInventory().setChestplate(new ItemStack(Material.AIR));
				p.getInventory().setLeggings(new ItemStack(Material.AIR));
				p.getInventory().setBoots(new ItemStack(Material.AIR));
				
				p.updateInventory();
			}
			
		}, 35);
	}
	
}
