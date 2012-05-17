package net.minecraft.src.wirelessredstone.addon.triangulator.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.addon.remote.network.packet.PacketWirelessRemoteGui;
import net.minecraft.src.wirelessredstone.addon.remote.network.packet.PacketWirelessRemoteSettings;
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
			NetClientHandler net = (NetClientHandler)network.getNetHandler();
			int packetID = data.read();
			switch (packetID)
			{
			case PacketIds.WIFI_TRIANGULATOR:
				PacketWirelessRemoteSettings pWR = new PacketWirelessRemoteSettings();
				pWR.readData(data);
				PacketHandlerWirelessTriangulator.handlePacket(pWR);
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
		MessageManager.getInstance().registerChannel(network, this, "WIFI-TRI");
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args) 
	{
	}
}