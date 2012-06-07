package net.minecraft.src.wirelessredstone.addon.powerc;

import net.minecraft.src.EntityPlayer;

public interface PowerConfiguratorOverride
{
	public boolean beforeOpenGui(EntityPlayer entityplayer, GuiRedstoneWirelessPowerDirector guiPowerC);
}
