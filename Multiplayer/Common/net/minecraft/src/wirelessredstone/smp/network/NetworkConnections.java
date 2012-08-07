package net.minecraft.src.wirelessredstone.smp.network;

import net.minecraft.src.BaseMod;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.INetworkConnections;

public abstract class NetworkConnections implements INetworkConnections {
	public String channel;
	public EntityPlayer entityplayer;
	public NetworkManager netManager;

	private NetworkConnections(String channel) {
		this.channel = channel;
	}
	public NetworkConnections(EntityPlayer entityplayer, String channel) {
		this(channel);
		this.entityplayer = entityplayer;
	}
	public NetworkConnections(NetworkManager netManager, String channel) {
		this(channel);
		this.netManager = netManager;
	}

	@Override
	public void onLogin(NetworkManager network, EntityPlayer entityplayer, BaseMod mod) {
		if (entityplayer != null) {
			this.entityplayer = entityplayer;
		} else {
			this.entityplayer = WirelessRedstone.getPlayer(network);
		}
		ModLoader.registerPacketChannel(mod, this.channel);
		if (network != null) {
			ModLoader.getLogger().fine(
					"Wireless Redstone : Registered channel [" + this.channel
							+ "] for "
							+ this.entityplayer.username);
		}
	}
}