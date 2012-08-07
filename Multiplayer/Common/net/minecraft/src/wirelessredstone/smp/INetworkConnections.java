package net.minecraft.src.wirelessredstone.smp;

import net.minecraft.src.BaseMod;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.forge.IConnectionHandler;
import net.minecraft.src.forge.IPacketHandler;

public abstract interface INetworkConnections {

	public void onConnect(NetworkManager network);

	public void onLogin(NetworkManager network, EntityPlayer entityplayer, BaseMod mod);
	
	public void onDisconnect(NetworkManager network, String message,
			Object[] args);

	public void onPacketData(Packet250CustomPayload packet);
}
