package net.minecraft.src;

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.RedstoneEtherOverrideServer;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnection;

public class mod_WirelessRedstoneSMP extends NetworkMod
{
	public static NetworkMod instance;
	
	public mod_WirelessRedstoneSMP()
	{
		instance = this;
		
    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
    	
		RedstoneEtherOverrideServer etherOverride = new RedstoneEtherOverrideServer();
		RedstoneEther.getInstance().addOverride(etherOverride);
		
		WirelessRedstone.load();
	}
	
	public static void addOverrideToReceiver(BlockRedstoneWirelessOverride override)
	{
		LoggerRedstoneWireless.getInstance("Wireless Redstone")
		.write("Override added to "+WirelessRedstone.blockWirelessR.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		
		((BlockRedstoneWireless)WirelessRedstone.blockWirelessR).addOverride(override);
	}
	
	public static void addOverrideToTransmitter(BlockRedstoneWirelessOverride override)
	{
		LoggerRedstoneWireless.getInstance("Wireless Redstone")
		.write("Override added to "+WirelessRedstone.blockWirelessT.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		
		((BlockRedstoneWireless)WirelessRedstone.blockWirelessT).addOverride(override);
	}
	
	@Override
	public void load()
	{
	}
	
	@Override
	public String getVersion()
	{
		return "1.6";
	}

	@Override
	public boolean clientSideRequired()
	{
		return true;
	}

	@Override
	public boolean serverSideRequired()
	{
		return true;
	}
}