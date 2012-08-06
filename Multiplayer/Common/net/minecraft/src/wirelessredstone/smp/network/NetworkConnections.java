package net.minecraft.src.wirelessredstone.smp.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.World;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.INetworkConnections;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneEther;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketRedstoneWirelessOpenGui;
import net.minecraft.src.wirelessredstone.smp.network.packet.PacketWirelessTile;

public abstract class NetworkConnections implements INetworkConnections {
	private static String channel;
	
	public NetworkConnections(String channel) {
		super();
		this.channel = channel;
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login) {
		MessageManager.getInstance().registerChannel(network, this,
				this.channel);
		ModLoader.getLogger().fine(
				"Wireless Redstone : Registered channel [" + this.channel +"] for "
						+ WirelessRedstone.getPlayer(network).username);
	}
}