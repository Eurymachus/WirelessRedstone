package net.minecraft.src.wirelessredstone.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneWirelessOpenGui;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWirelessTile;

public class RedstoneWirelessConnection extends NetworkConnections {

	public RedstoneWirelessConnection(EntityPlayer entityplayer, String channel) {
		super(entityplayer, channel);
	}

	public RedstoneWirelessConnection(NetworkManager netManager,
			EntityPlayer entityplayer, String channel) {
		this(entityplayer, channel);
		this.netManager = netManager;
	}

	@Override
	public void onConnect(NetworkManager network) {
	}

	@Override
	public void onDisconnect(NetworkManager network, String message,
			Object[] args) {
	}

	public void onPacketData(Packet250CustomPayload packet) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		if (this.entityplayer == null) {
			this.entityplayer = WirelessRedstone.getPlayer();
		}
		try {
			World world = this.entityplayer.worldObj;
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.ETHER:
				PacketRedstoneEther pRE = new PacketRedstoneEther();
				pRE.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pRE, world,
						this.entityplayer);
				break;
			case PacketIds.GUI:
				PacketRedstoneWirelessOpenGui pORW = new PacketRedstoneWirelessOpenGui();
				pORW.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pORW, world,
						this.entityplayer);
				break;
			case PacketIds.TILE:
				PacketWirelessTile pWT = new PacketWirelessTile();
				pWT.readData(data);
				PacketHandlerRedstoneWireless.handlePacket(pWT, world,
						this.entityplayer);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}