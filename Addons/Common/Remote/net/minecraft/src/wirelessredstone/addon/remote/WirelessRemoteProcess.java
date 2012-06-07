package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class WirelessRemoteProcess
{
	public static Remote remote;

	public static void activateRemote(World world, EntityPlayer entityplayer)
	{
		if (remote != null)
        {
        	if (remote.isBeingHeld())
            {
            	return;
            }

            deactivateRemote(entityplayer, world);
        }
        remote = new Remote(entityplayer, world);
        remote.activate();
	}

	public static boolean deactivateRemote(EntityPlayer entityplayer, World world)
	{
    	if (remote == null)
        {
        	return false;
        }
        else
        {
        	remote.deactivate();
            remote = null;
            return true;
        }
	}

	public static boolean isRemoteOn(EntityPlayer entityplayer, String freq)
	{
		return remote == null ? false : remote.getFreq() == freq;
	}
}
