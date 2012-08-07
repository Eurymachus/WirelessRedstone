package net.minecraft.src.wirelessredstone.smp.network;

import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.smp.INetworkConnections;

public abstract class NetworkConnections implements INetworkConnections {
	public String channel;

	public NetworkConnections(String channel) {
		this.channel = channel;
	}

	@Override
	public void onLogin(NetworkManager network, Packet1Login login, BaseMod mod) {
		ModLoader.registerPacketChannel(mod, this.channel);
		/*
		 * MessageManager.getInstance().registerChannel(network, this,
		 * this.channel);
		 */
		if (network != null) {
			ModLoader.getLogger().fine(
					"Wireless Redstone : Registered channel [" + this.channel
							+ "] for "
							+ WirelessRedstone.getPlayer(network).username);
		}
	}
}