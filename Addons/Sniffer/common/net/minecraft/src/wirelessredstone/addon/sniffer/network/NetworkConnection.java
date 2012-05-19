package net.minecraft.src.wirelessredstone.addon.sniffer.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferEtherCopy;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferOpenGui;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.packet.PacketWirelessSnifferSettings;
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
			case PacketIds.WIFI_SNIFFER:
				PacketWirelessSnifferSettings pWS = new PacketWirelessSnifferSettings();
				pWS.readData(data);
				PacketHandlerWirelessSniffer.handlePacket(pWS, world, player);
				break;
			case PacketIds.WIFI_SNIFFERETHER:
				PacketWirelessSnifferEtherCopy pWSEC = new PacketWirelessSnifferEtherCopy();
				pWSEC.readData(data);
				PacketHandlerWirelessSniffer.handlePacket(pWSEC, world, player);
				break;
			case PacketIds.WIFI_SNIFFERGUI:
				PacketWirelessSnifferOpenGui pWSG = new PacketWirelessSnifferOpenGui();
				pWSG.readData(data);
				PacketHandlerWirelessSniffer.handlePacket(pWSG, world, player);
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
		MessageManager.getInstance().registerChannel(network, this, "WIFI-SNIFFER");
		ModLoader.getLogger().warning("Wireless Redstone : Sniffer Registered for - " + WirelessRedstone.getPlayer(network).username);
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args) 
	{
	}
}
