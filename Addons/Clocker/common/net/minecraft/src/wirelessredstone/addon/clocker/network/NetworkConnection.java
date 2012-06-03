package net.minecraft.src.wirelessredstone.addon.clocker.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerGui;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerSettings;
import net.minecraft.src.wirelessredstone.addon.clocker.network.packet.PacketWirelessClockerTile;
import net.minecraft.src.wirelessredstone.smp.INetworkConnections;
import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;

public class NetworkConnection implements INetworkConnections
{

	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] bytes) 
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
		try
		{
			World world = WirelessRedstone.getWorld(network);
			EntityPlayer player = WirelessRedstone.getPlayer(network);
			int packetID = data.read();
			
			switch (packetID)
			{
			case PacketIds.ADDON:
				PacketWirelessClockerSettings pWCB = new PacketWirelessClockerSettings();
				pWCB.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCB, world, player);
				break;
			case PacketIds.GUI:
				PacketWirelessClockerGui pWCG = new PacketWirelessClockerGui();
				pWCG.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCG, world, player);
				break;
			case PacketIds.TILE:
				PacketWirelessClockerTile pWCT = new PacketWirelessClockerTile();
				pWCT.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCT, world, player);
				break;
			} 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnect(NetworkManager network) 
	{	
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login) 
	{
		MessageManager.getInstance().registerChannel(network, this, "WIFI-CLOCKER");
		ModLoader.getLogger().warning("Wireless Redstone : Clocker Registered for - " + WirelessRedstone.getPlayer(network).username);
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args) 
	{
	}
}
