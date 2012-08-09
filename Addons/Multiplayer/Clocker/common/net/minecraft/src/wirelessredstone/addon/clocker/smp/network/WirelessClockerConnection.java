package net.minecraft.src.wirelessredstone.addon.clocker.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.packet.PacketWirelessClockerOpenGui;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.packet.PacketWirelessClockerSettings;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.packet.PacketWirelessClockerTile;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;

public class WirelessClockerConnection extends NetworkConnections {

	public WirelessClockerConnection(EntityPlayer entityplayer, String channel) {
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
				PacketWirelessClockerSettings pWCB = new PacketWirelessClockerSettings();
				pWCB.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCB, world,
						entityplayer);
				break;
			case PacketIds.GUI:
				PacketWirelessClockerOpenGui pWCG = new PacketWirelessClockerOpenGui();
				pWCG.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCG, world,
						entityplayer);
				break;
			case PacketIds.TILE:
				PacketWirelessClockerTile pWCT = new PacketWirelessClockerTile();
				pWCT.readData(data);
				PacketHandlerWirelessClocker.handlePacket(pWCT, world,
						entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}