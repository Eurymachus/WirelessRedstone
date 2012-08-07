package net.minecraft.src.wirelessredstone.smp;

import net.minecraft.src.BaseMod;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.forge.IConnectionHandler;
import net.minecraft.src.forge.IPacketHandler;

public abstract interface INetworkConnections extends IConnectionHandler, IPacketHandler {

	public void onPacketData(NetworkManager network, String channel,
			byte[] bytes);

	public void onConnect(NetworkManager network);

	public void onLogin(NetworkManager network, Packet1Login login, BaseMod mod);
	
    public void onLogin(NetworkManager network, Packet1Login login);

	public void onDisconnect(NetworkManager network, String message,
			Object[] args);

	public void onPacketData(EntityPlayer entityplayer,
			Packet250CustomPayload packet);

	//public void onPacketData(Packet250CustomPayload packet);
}
