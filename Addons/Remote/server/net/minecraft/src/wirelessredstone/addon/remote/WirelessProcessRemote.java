package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class WirelessProcessRemote
{
	public static Remote remote;

	public static void activateRemote(World world, EntityPlayer entityplayer) 
	{
        remote = new Remote(entityplayer, world);
	}

    public static boolean deactivateRemote(World world, EntityPlayer entityplayer)
    {
        if (remote == null)
        {
            return false;
        }
        else
        {
            remote = null;
            return true;
        }
	}
}
