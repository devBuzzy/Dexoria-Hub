package customEntitys;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_7_R3.EntityInsentient;
import net.minecraft.server.v1_7_R3.EntityVillager;
import net.minecraft.server.v1_7_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R3.World;

public class HubVillager extends EntityVillager{
	
	public HubVillager(World world, int paramInt) {
		super(world);
		
	    setProfession(paramInt);
	    a(0.6F, 1.8F);

	    getNavigation().b(true);
	    getNavigation().a(true);

	    this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.0D));
	    this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 16.0F));
	}

	public static Villager spawn(Location location) {
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final HubVillager customEntity = new HubVillager(
				mcWorld,1);
		customEntity.setLocation(location.getX(), location.getY(),
				location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity())
				.setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (CraftVillager) customEntity.getBukkitEntity();
	}
	
	@Override
	public void move(double d0, double d1, double d2) {
		return;
	}
	
	@Override
	 protected String t(){
		 if (cc()) {
		      return "";
		    }
		    return "";
		 }
	
	@Override
	public void g(double d0, double d1, double d2){
		return;
	}
}
