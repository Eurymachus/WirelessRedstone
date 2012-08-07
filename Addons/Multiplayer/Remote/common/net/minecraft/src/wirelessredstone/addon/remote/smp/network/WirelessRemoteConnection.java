package net.minecraft.src.wirelessredstone.addon.remote.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet.PacketWirelessRemoteOpenGui;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.packet.PacketWirelessRemoteSettings;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;

public class WirelessRemoteConnection extends NetworkConnections {

	public WirelessRemoteConnection(EntityPlayer entityplayer, String channel) {
		super(entityplayer, channel);
	}

	@Override
	public void onConnect(NetworkManager network) {
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {
	}

	@Override
	public void onPacketData(Packet250CustomPayload packet) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			World world = entityplayer.worldObj;
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ADDON:
				PacketWirelessRemoteSettings pWR = new PacketWirelessRemoteSettings();
				pWR.readData(data);
				PacketHandlerWirelessRemote.handlePacket(pWR, world,
						entityplayer);
				break;
			case PacketIds.GUI:
				PacketWirelessRemoteOpenGui pRG = new PacketWirelessRemoteOpenGui();
				pRG.readData(data);
				PacketHandlerWirelessRemote.handlePacket(pRG, world,
						entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}