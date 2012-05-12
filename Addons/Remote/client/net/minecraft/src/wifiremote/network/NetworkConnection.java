package net.minecraft.src.wifiremote.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wifi.network.INetworkConnections;
import net.minecraft.src.wifi.network.PacketIds;

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
			case PacketIds.WIFI_REMOTE:
				PacketWirelessRemoteSettings pPC = new PacketWirelessRemoteSettings();
				pPC.readData(data);
				PacketHandlerWirelessRemote.handlePacket(pPC);
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
		MessageManager.getInstance().registerChannel(network, this, "WIFI-POWERC");
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args) 
	{
	}
}