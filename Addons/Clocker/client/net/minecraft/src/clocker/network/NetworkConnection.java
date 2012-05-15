package net.minecraft.src.clocker.network;

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
	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
		try
		{
			NetClientHandler net = (NetClientHandler)network.getNetHandler();
			int packetID = data.read();
			switch (packetID)
			{
			case PacketIds.WIFI_CLOCKER:
				PacketWirelessClockerSettings pWCB = new PacketWirelessClockerSettings();
				pWCB.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCB, ModLoader.getMinecraftInstance().thePlayer);
				break;
			case PacketIds.WIFI_CLOCKERGUI:
				PacketWirelessClockerGui pWCG = new PacketWirelessClockerGui();
				pWCG.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCG, ModLoader.getMinecraftInstance().thePlayer);
				break;
			case PacketIds.WIFI_CLOCKERTILE:
				PacketWirelessClockerTile pWCT = new PacketWirelessClockerTile();
				pWCT.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCT, ModLoader.getMinecraftInstance().thePlayer);
				break;
			} 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnect(NetworkManager network) {
		
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login) {
		MessageManager.getInstance().registerChannel(network, this, "WIFI-CLOCKER");
		ModLoader.getLogger().warning("Channel Registered");
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {
	}
}
