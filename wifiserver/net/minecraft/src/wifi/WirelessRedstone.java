package net.minecraft.src.wifi;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.mod_WirelessRedstone;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wifi.network.NetworkConnection;
import net.minecraft.src.wifi.network.PacketHandlerRedstoneWireless;

public class WirelessRedstone
{
	public static Block blockWirelessR;
	public static Block blockWirelessT;

	public static int rxID=127;
	public static int txID=126;
	
	//public static int etherPackID=235;
	//public static int guiPackID=236;
	
	public static int manualUpdate = 10;
	public static int initUpdate = 2;
	public static int stateUpdate = 100;
	
	public static boolean guiOn = false;
	
	public static int spriteTopOn;
	public static int spriteTopOff;
	public static int spriteROn;
	public static int spriteROff;
	public static int spriteTOn;
	public static int spriteTOff;
	
	private static boolean loaded = false;

	public static void load()
	{
		if ( !loaded ) {
			loaded = true;
			loadConfig();
			blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F)).setBlockName("wifir");
			blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F)).setBlockName("wifit");
			ModLoader.registerBlock(blockWirelessR);
			ModLoader.registerTileEntity(TileEntityRedstoneWirelessR.class, "Wireless Receiver");
			ModLoader.registerBlock(blockWirelessT);
			ModLoader.registerTileEntity(TileEntityRedstoneWirelessT.class, "Wireless Transmitter");
			loadBlockTextures();
			addRecipes();
			
			Thread thr = new Thread(new Runnable() {			
				@Override
				public void run() {	
					if ( WirelessRedstone.manualUpdate < 1 ) return;
					
					while ( true ) {
						try {
							Thread.sleep(WirelessRedstone.manualUpdate*1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTilesToAll(0);
					}
				}
			});
			thr.setName("WirelessRedstoneUpdaterThread");
			thr.start();
		}
	}
	
	
/*	@Override
	public void handleLogin(EntityPlayerMP entityplayermp) {
		PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTilesTo(entityplayermp,initUpdate*1000);
	}
	
	
	@Override
	public void handlePacket(Packet230ModLoader packet, EntityPlayerMP player) {
		PacketHandlerRedstoneWireless.handlePacket(packet, player);
	}*/
	
	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
            "IRI", "RLR", "IRI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('L'), Block.lever
        });
		ModLoader.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
            "IRI", "RTR", "IRI", Character.valueOf('I'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('T'), Block.torchRedstoneActive
        });
	}
	
	public static void loadBlockTextures() {
		spriteTopOn = 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOn.png");
		spriteTopOff = 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOff.png");
		spriteROn = 	ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOn.png");
		spriteROff =	ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOff.png");
		spriteTOn =		ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOn.png");
		spriteTOff =	ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOff.png");
	}
	
	public static void addOverrideToReceiver(BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+blockWirelessR.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless)blockWirelessR).addOverride(override);
	}
	
	public static void addOverrideToTransmitter(BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+blockWirelessT.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless)blockWirelessT).addOverride(override);
	}
	
	private static void loadConfig() {
		rxID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Receiver.ID", Integer.class, new Integer(rxID));
		txID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Transmitter.ID", Integer.class, new Integer(txID));
		guiOn = (Boolean) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Gui", Boolean.class, new Boolean(guiOn));
		manualUpdate = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Update.Intervall", Integer.class, new Integer(manualUpdate));
		stateUpdate = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Update.StateDelay", Integer.class, new Integer(stateUpdate));
		initUpdate = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Update.LoginDelay", Integer.class, new Integer(initUpdate));
		//etherPackID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Packet.Ether.ID", Integer.class, new Integer(etherPackID));
		//guiPackID = (Integer) ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Packet.Gui.ID", Integer.class, new Integer(guiPackID));
	}
}
