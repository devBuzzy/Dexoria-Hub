package LuckyDip;


import org.bukkit.Material;

public class PIMat {

	public static Material getCorrospondingItem(PossibleItems item){
		
	if(item.equals(PossibleItems.COSMETIC_CURRENCY)){
			return Material.GOLD_NUGGET;
	}
	if(item.equals(PossibleItems.DISCOSUITE)){
		return Material.LEATHER_HELMET;
	}
	if(item.equals(PossibleItems.DOUBLE_CC)){
		return Material.GOLD_INGOT;
	}
	if(item.equals(PossibleItems.DOUBLE_GC)){
		return Material.EMERALD_BLOCK;
	}
	if(item.equals(PossibleItems.FIREWORK_SHOW)){
		return Material.FIREWORK;
	}
	if(item.equals(PossibleItems.GAME_CURRENCY)){
		return Material.EMERALD;
	}
	if(item.equals(PossibleItems.PARTICLE_ENDERSPIRAL)){
		return Material.ENDER_PEARL;
	}
	if(item.equals(PossibleItems.PARTICLE_FLAMERING)){
		return Material.FIRE;
	}
	if(item.equals(PossibleItems.PARTICLE_LOVE)){
		return Material.RED_ROSE;
	}
	if(item.equals(PossibleItems.PARTICLE_MAGICSNAKE)){
		return Material.LEATHER_BOOTS;
	}
	if(item.equals(PossibleItems.PARTICLE_THUNDERCLOUD)){
		return Material.WATER;
	}
	if(item.equals(PossibleItems.PET_CAT)){
		return Material.MONSTER_EGG;
	}
	if(item.equals(PossibleItems.PET_DOG)){
		return Material.MONSTER_EGG;
	}
	if(item.equals(PossibleItems.PET_SLIME)){
		return Material.MONSTER_EGG;
	}
	if(item.equals(PossibleItems.SPACESUITE)){
		return Material.IRON_CHESTPLATE;
		}
	return null;
	}
	
}
