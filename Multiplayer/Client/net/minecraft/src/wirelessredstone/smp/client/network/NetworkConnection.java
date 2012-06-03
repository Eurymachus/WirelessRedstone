package net.minecraft.src.wirelessredstone.smp.client.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.smp.INetworkConnections;
import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketOpenWindowRedstoneWireless;
import net.minecraft.src.wirelessredstone.smp.packet.PacketRedstoneEther;
import net.minecraft.src.wirelessredstone.smp.packet.PacketWirelessTile;

public class NetworkConnection implements INetworkConnections
{

	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] bytes) 
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
		try
		{
			NetClientHandler net = (NetClientHandler)network.getNetHandler();
			int packetID = data.read();
			switch (packetID)
			{
			case PacketIds.ETHER:
				PacketRedstoneEther pRE = new PacketRedstoneEther();
				pRE.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pRE, ModLoader.getMinecraftInstance().thePlayer);
				break;
			case PacketIds.GUI:
				PacketOpenWindowRedstoneWireless pORW = new PacketOpenWindowRedstoneWireless();
				pORW.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pORW, ModLoader.getMinecraftInstance().thePlayer);
				break;
			case PacketIds.TILE:
				PacketWirelessTile pWT = new PacketWirelessTile();
				pWT.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pWT, ModLoader.getMinecraftInstance().thePlayer);
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
		MessageManager.getInstance().registerChannel(network, this, "WIFI");
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args) 
	{
	}
}