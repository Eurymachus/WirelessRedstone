package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessRemote;
import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.addon.remote.network.PacketHandlerWirelessRemote;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWireless;

import org.lwjgl.input.Mouse;

public class WirelessProcessRemote
{
	public static boolean mouseDown, wasMouseDown;
	public static Remote remote;

	public static void initialize()
	{
		mouseDown = false;
        wasMouseDown = false;
	}

	public static void activateRemote(World world, EntityPlayer entityplayer)
	{
		if (remote != null)
        {
        	if (remote.isBeingHeld())
            {
            	return;
            }

            deactivateRemote(world, entityplayer);
        }
        remote = new Remote(entityplayer, world);
        remote.remoteOn();

        transmitRemote("activateRemote", world, remote);
	}

	public static boolean deactivateRemote(World world, EntityPlayer entityplayer)
	{
    	if (remote == null)
        {
        	return false;
        }
        else
        {
        	remote.remoteOff();

            transmitRemote("deactivateRemote", world, remote);

            remote = null;
            return true;
        }
	}

	public static boolean isRemoteOn(EntityPlayer entityplayer, String freq)
	{
		return remote == null ? false : remote.getFreq() == freq;
	}

	public static void checkClicks()
	{
    	wasMouseDown = mouseDown;
        mouseDown = Mouse.isButtonDown(1);
	}

	public static boolean mouseClicked()
	{
		return mouseDown && !wasMouseDown;
	}

	public static void processRemote(World world, EntityPlayer entityplayer, GuiScreen gui, MovingObjectPosition mop)
	{
    	if (remote != null && !mouseDown)
        {
        	deactivateRemote(world, entityplayer);
        }

        if (mouseClicked() && remote == null && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem() == WirelessRemote.itemRemote && gui != null && gui instanceof GuiRedstoneWireless && !entityplayer.isSneaking() && WirelessRemote.ticksInGui > 0)
        {
        	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            int var6 = itemstack.getItemDamage();
            activateRemote(world, entityplayer);
        }
	}
    
	public static void transmitRemote(String command, World world, Remote remote)
	{
    	if (world.isRemote && mod_WirelessRemote.wirelessRemoteSMP)
    	{
    		PacketHandlerWirelessRemote.PacketHandlerOutput.sendWirelessRemotePacket(command, remote);
    	}
    	else
    	{
    		if (command.equals("deactivateRemote"))
    			RedstoneEther.getInstance().remTransmitter(world, remote.coords.getX(), remote.coords.getY(), remote.coords.getZ(), remote.getFreq());
    		else
    		{
				RedstoneEther.getInstance().addTransmitter(world, remote.coords.getX(), remote.coords.getY(), remote.coords.getZ(), remote.getFreq());
    			RedstoneEther.getInstance().setTransmitterState(world, remote.coords.getX(), remote.coords.getY(), remote.coords.getZ(), remote.getFreq(), true);
			}
    	}
	}
}
