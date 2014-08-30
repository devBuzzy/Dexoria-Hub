package customEntitys;

import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntityItem;
import net.minecraft.server.v1_7_R1.EntityPig;
import net.minecraft.server.v1_7_R1.GenericAttributes;
import net.minecraft.server.v1_7_R1.Item;
import net.minecraft.server.v1_7_R1.Items;
import net.minecraft.server.v1_7_R1.NBTTagCompound;
import net.minecraft.server.v1_7_R1.PathfinderGoalAvoidPlayer;
import net.minecraft.server.v1_7_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_7_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R1.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftBat;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPig;
import org.bukkit.entity.Pig;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class crazyPig extends EntityPig {

    private int fuseTicks;
    private int maxFuseTicks = 30;
    private int explosionRadius = 3;
    public crazyPig(World world) {
		super(world);
		
			this.goalSelector.a(1, new PathfinderGoalFloat(this));
	        this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityItem.class, 6.0F, 1.0D, 1.2D));
	        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
	        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
	        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
	        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
	        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
	    }
	@Override
	 protected void aD() {
	        super.aD();
	        this.getAttributeInstance(GenericAttributes.d).setValue(0.25D);
	    }
	 @Override
	    public boolean bk() {
	        return true;
	    }
	    @Override
	    public int ax() {
	        return this.getGoalTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	    }
	    @Override
	    protected void b(float f) {
	        super.b(f);
	        this.fuseTicks = (int) ((float) this.fuseTicks + f * 1.5F);
	        if (this.fuseTicks > this.maxFuseTicks - 5) {
	            this.fuseTicks = this.maxFuseTicks - 5;
	        }
	    }
	    @Override
	    protected void c() {
	        super.c();
	        this.datawatcher.a(16, Byte.valueOf((byte) -1));
	        this.datawatcher.a(17, Byte.valueOf((byte) 0));
	        this.datawatcher.a(18, Byte.valueOf((byte) 0));
	    }
	    @Override
	    public void b(NBTTagCompound nbttagcompound) {
	        super.b(nbttagcompound);
	        if (this.datawatcher.getByte(17) == 1) {
	            nbttagcompound.setBoolean("powered", true);
	        }

	        nbttagcompound.setShort("Fuse", (short) this.maxFuseTicks);
	        nbttagcompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
	        nbttagcompound.setBoolean("ignited", this.cc());
	    }
	    @Override
	    public void a(NBTTagCompound nbttagcompound) {
	        super.a(nbttagcompound);
	        this.datawatcher.watch(17, Byte.valueOf((byte) (nbttagcompound.getBoolean("powered") ? 1 : 0)));
	        if (nbttagcompound.hasKeyOfType("Fuse", 99)) {
	            this.maxFuseTicks = nbttagcompound.getShort("Fuse");
	        }

	        if (nbttagcompound.hasKeyOfType("ExplosionRadius", 99)) {
	            this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
	        }

	        if (nbttagcompound.getBoolean("ignited")) {
	            this.cd();
	        }
	    }
	    @Override
	    protected String aT() {
	        return "mob.creeper.say";
	    }
	    @Override
	    protected String aU() {
	        return "mob.creeper.death";
	    }

	    // CraftBukkit end

	    @Override
	    protected Item getLoot() {
	        return Items.SULPHUR;
	    }

	    @Override
	    public void a(int i) {
	        this.datawatcher.watch(16, Byte.valueOf((byte) i));
	    }
	    @Override
	    public boolean cc() {
	        return this.datawatcher.getByte(18) != 0;
	    }
	    @Override
	    public void cd() {
	        this.datawatcher.watch(18, Byte.valueOf((byte) 1));
	    }
	    
		public static Pig spawn(Location location) {
			World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
			final crazyPig customEntity = new crazyPig(
					mcWorld);
			customEntity.setLocation(location.getX(), location.getY(),
					location.getZ(), location.getYaw(), location.getPitch());
			((CraftLivingEntity) customEntity.getBukkitEntity())
					.setRemoveWhenFarAway(false);
			mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
			return (CraftPig) customEntity.getBukkitEntity();
		}
	}

