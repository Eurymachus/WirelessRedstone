package net.minecraft.src.wirelessredstone.addon.remote.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.forge.MessageManager;
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
			NetServerHandler net = (NetServerHandler)network.getNetHandler();
			int packetID = data.read();
			switch (packetID)
			{
			case PacketIds.WIFI_REMOTE:
				PacketWirelessRemoteSettings pWR = new PacketWirelessRemoteSettings();
				pWR.readData(data);
				PacketHandlerWirelessRemote.handlePacket(pWR, net.getPlayerEntity());
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
		MessageManager.getInstance().registerChannel(network, this, "WIFI-REMOTE");
		ModLoader.getLogger().warning("Channel Registered");
	}

	@Override
	public void onDisconnect(NetworkManager network, String message, Object[] args) 
	{
	}
}