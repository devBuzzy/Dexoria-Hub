package Gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.lewys.com.Hub;
import me.lewys.com.Points;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.kumpelblase2.remoteentities.EntityManager;
import de.kumpelblase2.remoteentities.RemoteEntities;
import de.kumpelblase2.remoteentities.api.EntitySound;
import de.kumpelblase2.remoteentities.api.RemoteEntity;
import de.kumpelblase2.remoteentities.api.RemoteEntityType;
import de.kumpelblase2.remoteentities.api.thinking.goals.DesireFollowSpecific;

public class GadgetMenu implements Listener{

	HashMap<String, Entity> activepet = new HashMap<String, Entity>();
	HashMap<Player, RemoteEntity> re = new HashMap<Player, RemoteEntity>();
	HashMap<Player, Entity> mount = new HashMap<Player, Entity>();
	
	String prefix = "§2§lGadget §f> ";
	
	public ItemStack sword(){

		ItemStack is = new ItemStack(Material.LEASH);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Gadgets");
		
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack firework(){
		ItemStack is = new ItemStack(Material.FIREWORK);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Firework");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 20 Points" + ChatColor.RED + "" + ChatColor.BOLD + " Per use");
		lore.add(ChatColor.GREEN + "Shoot random fireworks!");
		lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Usage:");
		lore.add(ChatColor.GREEN + "Right click the ground");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack snowgun(){
		ItemStack is = new ItemStack(Material.IRON_BARDING);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Snow Shooter");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 2 Point" + ChatColor.RED + "" + ChatColor.BOLD + " Per shot");
		lore.add(ChatColor.GREEN + "Shoot snow at people making them cold!");
		lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Usage:");
		lore.add(ChatColor.GREEN + "Right click air to shoot!");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack tntbow(){
		ItemStack is = new ItemStack(Material.BOW);
		is.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TnT Bow");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 100 Points" + ChatColor.RED + "" + ChatColor.BOLD + " Per use");
		lore.add(ChatColor.GREEN + "Shoot tnt that sends players flying!");
		lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Usage:");
		lore.add(ChatColor.GREEN + "Shoot an arrow normaly");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	
	public ItemStack rail(){
		ItemStack is = new ItemStack(Material.RAILS);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Railway");
		
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack minecart_np(){


		ItemStack is = new ItemStack(Material.MINECART);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "MineCart");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.LIGHT_PURPLE + " PLATNIM +");
		lore.add(ChatColor.GREEN + "Ride on the railway!");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack minecart_unlocked(){


		ItemStack is = new ItemStack(Material.MINECART);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "MineCart");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Click to activate!");
		lore.add(ChatColor.GREEN + "Ride on the railway!");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack mount(){

		
		ItemStack is = new ItemStack(Material.HAY_BLOCK);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Pets");
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack dead_np(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 100);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Horse");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.AQUA + " ULTIMATE +"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack dead_unlocked(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 100);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Horse");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD +"Click to activate!"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack dog_np(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 95);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Dog");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 3,500 Points" ));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack dog_unlocked(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 95);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Dog");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Click to activate"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack cat_np(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 98);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Cat");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 3,500 Points" ));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack cat_unlocked(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 98);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Cat");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Click to activate"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack slime_unlocked(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 55);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Slime");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Click to activate"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack slime_np(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 55);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Slime");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 3,500 Points" ));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	
	public ItemStack blaze_np(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 61);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Blaze");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.LIGHT_PURPLE + " PLATINUM +"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack blaze_unlocked(){

		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1, (short) 61);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Blaze");
		
		List<String> lore = new ArrayList<String>();
		lore.add((ChatColor.RED + "" + ChatColor.BOLD + "Click to activate!"));
		
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)){
			if(e.getPlayer().getItemInHand().getType() == Material.CHEST){
				e.setCancelled(true);
				
				Inventory gadget = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Gadget Menu");
				
					gadget.setItem(0, sword());
					gadget.setItem(2, firework());
					gadget.setItem(4, snowgun());
					gadget.setItem(6, tntbow());
					gadget.setItem(18, rail());
					
					if(e.getPlayer().hasPermission("hub.platinum")){
					gadget.setItem(20, minecart_unlocked());
					}else{
					gadget.setItem(20, minecart_np());
					}
					
					gadget.setItem(36, mount());				
					
					if(e.getPlayer().hasPermission("hub.ultimate") || (e.getPlayer().hasPermission("hub.platinum"))){
					gadget.setItem(41, dead_unlocked());		
					}else{
					gadget.setItem(41, dead_np());	
					}
					if(Hub.getpet_dogFile().contains(e.getPlayer().getName())){
					gadget.setItem(38, dog_unlocked());
					}else{
					gadget.setItem(38, dog_np());
					}
					
					if(Hub.getpet_catFile().contains(e.getPlayer().getName())){
					gadget.setItem(39, cat_unlocked());
					}else{
					gadget.setItem(39, cat_np());
					}	
					
					if(Hub.getpet_slimeFile().contains(e.getPlayer().getName())){
					gadget.setItem(40, slime_unlocked());
					}else{
					gadget.setItem(40, slime_np());
					}
					
					if(e.getPlayer().hasPermission("hub.platinum")){
					gadget.setItem(42, blaze_unlocked());
					}else{
					gadget.setItem(42, blaze_np());
					}
					
					e.getPlayer().openInventory(gadget);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getName().equals(ChatColor.BLUE + "Gadget Menu")){
			
			if(e.getCurrentItem() == null || (e.getCurrentItem().getType() == Material.AIR))
				return;
			
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();
			if(e.getCurrentItem().getType() == Material.MINECART){
			e.getWhoClicked().getInventory().setItem(3, new ItemStack(Material.AIR));	
			e.getWhoClicked().getInventory().setItem(5, new ItemStack(Material.AIR));	
			if(e.getWhoClicked().hasPermission("hub.platinum")){
			for(Entity cart : Bukkit.getWorld("flatroom").getEntities()){
				if(cart.getType() == EntityType.MINECART){
					
					if(cart.getPassenger() == null){
						cart.setPassenger(e.getWhoClicked());
						return;
							}
						}
					}
				}
			}
			
			if(e.getCurrentItem().getType() == Material.FIREWORK){
				if(e.getWhoClicked().getInventory().contains(Material.FIREWORK)){
					e.getWhoClicked().getInventory().setItem(3, new ItemStack(Material.AIR));
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Firework!");
				}else{		
				e.getWhoClicked().getInventory().setItem(3, firework());
				e.getWhoClicked().getInventory().setItem(5, new ItemStack(Material.AIR));
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Firework!");
				}
			}
		
			
			if(e.getCurrentItem().getType() == Material.BOW){
				
				if(e.getWhoClicked().getInventory().contains(Material.BOW)){
					
					e.getWhoClicked().getInventory().setItem(5, new ItemStack(Material.AIR));
					e.getWhoClicked().getInventory().setItem(3, new ItemStack(Material.AIR));
					
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " TnT Bow!");
					
				}else{
				e.getWhoClicked().getInventory().setItem(3, tntbow());
				e.getWhoClicked().getInventory().setItem(5, new ItemStack(Material.ARROW));
				
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " TnT Bow!");
				}
			}
			
		if(e.getCurrentItem().getType() == Material.IRON_BARDING){
			if(e.getWhoClicked().getInventory().contains(Material.IRON_BARDING)){
				e.getWhoClicked().getInventory().setItem(3, new ItemStack(Material.AIR));
				
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Snow Gun!");
				
				}else{
					e.getWhoClicked().getInventory().setItem(3, snowgun());
					e.getWhoClicked().getInventory().setItem(5, new ItemStack(Material.AIR));
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Snow Gun!");
				}
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Horse")){
			if(p.hasPermission("hub.ultimate") || (p.hasPermission("hub.platinum"))){
				
			if(activepet.containsKey(p.getName())){
				Entity ent = activepet.get(p.getName());
				ent.remove();
				mount.remove(p);
				activepet.remove(p.getName());
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Your last pet!");
			}else{
				
			EntityManager manager = RemoteEntities.createManager(Hub.getPluginInstance());
			RemoteEntity entity = manager.createEntity(RemoteEntityType.Horse, e.getWhoClicked().getLocation(), false);
			entity.getMind().addMovementDesire(new DesireFollowSpecific(entity, (LivingEntity) e.getWhoClicked(), 3F,8F), 1);
			Horse h = (Horse) entity.getBukkitEntity();
			entity.setName(p.getName() + " pet!");
			h.getInventory().setSaddle(new ItemStack(Material.SADDLE));
			h.setTamed(true);
			h.setStyle(Horse.Style.BLACK_DOTS);
			activepet.put(p.getName(), entity.getBukkitEntity());
			re.put(p, entity);
			mount.put(p, entity.getBukkitEntity());
			p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Horse Pet!");
			
			}
		}
	}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Dog")){
				if(e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 3,500 Points")){
					if(Points.hasEnough(p.getName(), 3500)){
					Points.removePoints(p.getName(), 3500);
					p.sendMessage(prefix + ChatColor.GRAY + "You purchased " + ChatColor.GREEN + "Dog Pet!");
					Hub.getpet_dogFile().set(p.getName(), "");
					return;
				}else{
					p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Gadgets" + ChatColor.WHITE + " > " + ChatColor.GRAY + "You don't have enough points!");
					return;
				}
			}
				if(activepet.containsKey(p.getName())){
					Entity ent = activepet.get(p.getName());
					ent.remove();
					activepet.remove(p.getName());
					re.remove(p);
					mount.remove(p);
					
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Your last pet!");
				}else{
				
				EntityManager manager = RemoteEntities.createManager(Hub.getPluginInstance());
				RemoteEntity entity = manager.createEntity(RemoteEntityType.Wolf, e.getWhoClicked().getLocation(), false);
				entity.getMind().addMovementDesire(new DesireFollowSpecific(entity, (LivingEntity) e.getWhoClicked(), 3F,8F), 1);
				entity.setName(p.getName() + " pet!");
				entity.setSound(EntitySound.RANDOM, "");
				activepet.put(p.getName(), entity.getBukkitEntity());
				re.put(p, entity);
				mount.put(p, entity.getBukkitEntity());
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Wolf pet!");
				}
		}
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Cat")){
				if(e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 3,500 Points")){
					if(Points.hasEnough(p.getName(), 3500)){
					Points.removePoints(p.getName(), 3500);
					p.sendMessage(prefix + ChatColor.GRAY + "You purchased " + ChatColor.GREEN + "Cat Pet!");
					Hub.getpet_catFile().set(p.getName(), "");
					return;
				}else{
					p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Gadgets" + ChatColor.WHITE + " > " + ChatColor.GRAY + "You don't have enough points!");
					return;
				}
			}
				if(activepet.containsKey(p.getName())){
					Entity ent = activepet.get(p.getName());
					ent.remove();
					re.remove(p);
					mount.remove(p);
					activepet.remove(p.getName());
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Your last pet!");
				}else{
				
				EntityManager manager = RemoteEntities.createManager(Hub.getPluginInstance());
				RemoteEntity entity = manager.createEntity(RemoteEntityType.Ocelot, e.getWhoClicked().getLocation(), false);
				entity.getMind().addMovementDesire(new DesireFollowSpecific(entity, (LivingEntity) e.getWhoClicked(), 3F,8F), 1);
				entity.setName(p.getName() + " pet!");
				entity.setSound(EntitySound.RANDOM, "");
				activepet.put(p.getName(), entity.getBukkitEntity());
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Cat pet!");
				}
			}
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Slime")){
				if(e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.BOLD + "Cost:" + ChatColor.YELLOW + " 3,500 Points")){
					if(Points.hasEnough(p.getName(), 3500)){
					Points.removePoints(p.getName(), 3500);
					p.sendMessage(prefix + ChatColor.GRAY + "You purchased " + ChatColor.GREEN + "Slime Pet!");
					Hub.getpet_slimeFile().set(p.getName(), "");
					return;
				}else{
					p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Gadgets" + ChatColor.WHITE + " > " + ChatColor.GRAY + "You don't have enough points!");
					return;
				}
			}
				if(activepet.containsKey(p.getName())){
					Entity ent = activepet.get(p.getName());
					ent.remove();
					re.remove(p);
					mount.remove(p);
					activepet.remove(p.getName());
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Your last pet!");
				}else{
				
				EntityManager manager = RemoteEntities.createManager(Hub.getPluginInstance());
				RemoteEntity entity = manager.createEntity(RemoteEntityType.Slime, e.getWhoClicked().getLocation(), false);
				Slime s = (Slime) entity.getBukkitEntity();
				s.setSize(2);
				entity.getMind().addMovementDesire(new DesireFollowSpecific(entity, (LivingEntity) e.getWhoClicked(), 3F,8F), 1);
				entity.setSound(EntitySound.RANDOM, "");
				entity.setName(p.getName() + " pet!");
				activepet.put(p.getName(), entity.getBukkitEntity());
				p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Slime pet!");
				}
			}
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Blaze")){
				if(p.hasPermission("hub.platinum")){
				if(activepet.containsKey(p.getName())){
					Entity ent = activepet.get(p.getName());
					ent.remove();
					re.remove(p);
					mount.remove(p);
					activepet.remove(p.getName());
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.RED + "deactivated" + ChatColor.GRAY + " Your last pet!");
				}else{
					EntityManager manager = RemoteEntities.createManager(Hub.getPluginInstance());
					RemoteEntity entity = manager.createEntity(RemoteEntityType.Blaze, e.getWhoClicked().getLocation(), false);
					entity.getMind().addMovementDesire(new DesireFollowSpecific(entity, (LivingEntity) e.getWhoClicked(), 3F,8F), 1);
					entity.setSound(EntitySound.RANDOM, "");
					entity.setName(p.getName() + " pet!");
					activepet.put(p.getName(), entity.getBukkitEntity());
					p.sendMessage(prefix + ChatColor.GRAY + "You " + ChatColor.GREEN + "activated" + ChatColor.GRAY + " Blaze Pet!");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Horse){
				
				if(e.getRightClicked().equals(mount.get(e.getPlayer()))){
					RemoteEntity entity = re.get(e.getPlayer());
					entity.setSpeed(0.3d);
					}else{
						e.getPlayer().sendMessage(prefix + ChatColor.GRAY + "This is not your mount!");
						e.setCancelled(true);
					}
			}
		}
	
	@EventHandler
	public void onDismount(VehicleExitEvent e){
		if(e.getVehicle() instanceof Horse){
			RemoteEntity ent1 = re.get(e.getExited());		
			ent1.setSpeed(0.7d);
		}
	}
	
	@EventHandler
	public void onPlayerExit(PlayerQuitEvent e){
		
		if(activepet.containsKey(e.getPlayer().getName())){
			Entity ent = activepet.get(e.getPlayer().getName());
			
			ent.remove();
			
			activepet.remove(e.getPlayer().getName());
		}
		
	   re.remove(e.getPlayer());
	   
	   mount.remove(e.getPlayer());
	}
}
