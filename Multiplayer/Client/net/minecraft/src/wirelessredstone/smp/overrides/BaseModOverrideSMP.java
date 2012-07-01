package net.minecraft.src.wirelessredstone.smp.overrides;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;

public class BaseModOverrideSMP implements BaseModOverride {

	@Override
	public boolean beforeOpenGui(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		return (world.isRemote);
	}
}