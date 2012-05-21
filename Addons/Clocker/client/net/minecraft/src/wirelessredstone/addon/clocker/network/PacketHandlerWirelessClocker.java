package net.minecraft.src.wirelessredstone.addon.clocker.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.clocker.TileEntityRedstoneWirelessClocker;
import net.minecraft.src.wirelessredstone.addon.clocker.WirelessClocker;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerGui;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerSettings;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerTile;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.packet.PacketUpdate;

public class PacketHandlerWirelessClocker {
	
	public static void handlePacket(PacketUpdate packet, World world, EntityPlayer player)
	{
		if ( packet instanceof PacketWirelessClockerSettings )
			PacketHandlerInput.handleWirelessClockerSettings(
					(PacketWirelessClockerSettings)packet,
					world, 
					player
			);
		else if ( packet instanceof PacketWirelessClockerGui )
			PacketHandlerInput.handleWirelessClockerGui(
					(PacketWirelessClockerGui)packet, 
					world, 
					player
			);
		else if ( packet instanceof PacketWirelessClockerTile )
			PacketHandlerInput.handleWirelessClockerTile(
					(PacketWirelessClockerTile)packet, 
					world, 
					player
			);
	}
	

	private static class PacketHandlerInput 
	{
		private static void handleWirelessClockerSettings(PacketWirelessClockerSettings packet, World world, EntityPlayer player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerSettingsPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		}

		private static void handleWirelessClockerGui(PacketWirelessClockerGui packet, World world, EntityPlayer player) 
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerGuiPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity tileentity = packet.getTarget(world);
			if (tileentity != null && tileentity instanceof TileEntityRedstoneWirelessClocker) 
			{
				WirelessClocker.guiClock.assTileEntity((TileEntityRedstoneWirelessClocker)tileentity);
				ModLoader.openGUI(player, WirelessClocker.guiClock);
			}
		}

		public static void handleWirelessClockerTile(PacketWirelessClockerTile packet, World world, EntityPlayer player) 
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleWirelessClockerTilePacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);

			TileEntity tileentity = packet.getTarget(world);
			if (tileentity != null && tileentity instanceof TileEntityRedstoneWirelessClocker) 
			{
				((TileEntityRedstoneWirelessClocker)tileentity).setClockFreq(Integer.parseInt(packet.getClockFreq()));
				((TileEntityRedstoneWirelessClocker)tileentity).setFreq(packet.getFreq());
				tileentity.onInventoryChanged();
				player.worldObj.markBlockAsNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
			}
		}
	}

	public static class PacketHandlerOutput
	{
		public static void sendWirelessClockerPacket(EntityPlayer player, String clockFreq, int i, int j, int k) 
		{
			PacketWirelessClockerSettings packet = new PacketWirelessClockerSettings(clockFreq, i, j, k);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendWirelessClockerPacket:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			ModLoader.getMinecraftInstance().getSendQueue().addToSendQueue(packet.getPacket());
		}
	}

}
