package net.minecraft.src.wirelessredstone;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessT;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.overrides.BaseModOverride;
import net.minecraft.src.wirelessredstone.overrides.RedstoneEtherOverrideServer;
import net.minecraft.src.wirelessredstone.smp.network.NetworkConnection;
import net.minecraft.src.wirelessredstone.smp.network.PacketHandlerRedstoneWireless;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessT;

public class WirelessRedstone {
	public static boolean isLoaded = false;
	public static Block blockWirelessR;
	public static Block blockWirelessT;

	public static int rxID = 127;
	public static int txID = 126;

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
	public static int spriteTItem;
	public static int spriteRItem;
	public static int maxEtherFrequencies = 10000;

	private static List<BaseModOverride> overrides;

	public static boolean initialize() {
		try {
			overrides = new ArrayList();
			MinecraftForge.registerConnectionHandler(new NetworkConnection());

			loadConfig();
			addEtherOverrides();

			blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F))
					.setBlockName("wirelessredstone.receiver");
			blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F))
					.setBlockName("wirelessredstone.transmitter");
			ModLoader.registerBlock(blockWirelessR);
			ModLoader.registerTileEntity(TileEntityRedstoneWirelessR.class,
					"Wireless Receiver");
			ModLoader.registerBlock(blockWirelessT);
			ModLoader.registerTileEntity(TileEntityRedstoneWirelessT.class,
					"Wireless Transmitter");
			loadBlockTextures();
			addRecipes();

			Thread thr = new Thread(new Runnable() {
				@Override
				public void run() {
					if (WirelessRedstone.manualUpdate < 1)
						return;

					while (true) {
						try {
							Thread.sleep(WirelessRedstone.manualUpdate * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (ModLoader.getMinecraftServerInstance()
								.playersOnline() > 0)
							PacketHandlerRedstoneWireless.PacketHandlerOutput
									.sendEtherTilesToAll(0);
					}
				}
			});
			thr.setName("WirelessRedstoneUpdaterThread");
			thr.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static void addEtherOverrides() {
		RedstoneEtherOverrideServer override = new RedstoneEtherOverrideServer();
		RedstoneEther.getInstance().addOverride(override);
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
				"IRI", "RLR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('L'),
				Block.lever });
		ModLoader.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
				"IRI", "RTR", "IRI", Character.valueOf('I'), Item.ingotIron,
				Character.valueOf('R'), Item.redstone, Character.valueOf('T'),
				Block.torchRedstoneActive });
	}

	public static void loadBlockTextures() {
		spriteTopOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/topOn.png");
		spriteTopOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/topOff.png");
		spriteROn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/rxOn.png");
		spriteROff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/rxOff.png");
		spriteTOn = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/txOn.png");
		spriteTOff = ModLoader.addOverride("/terrain.png",
				"/WirelessSprites/txOff.png");
	}

	private static void loadConfig() {
		rxID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Receiver.ID", Integer.class,
				new Integer(rxID));
		txID = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Transmitter.ID", Integer.class,
				new Integer(txID));
		guiOn = (Boolean) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Ether.Gui", Boolean.class,
				new Boolean(guiOn));
		manualUpdate = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Ether.Update.Intervall",
				Integer.class, new Integer(manualUpdate));
		stateUpdate = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Ether.Update.StateDelay",
				Integer.class, new Integer(stateUpdate));
		initUpdate = (Integer) ConfigStoreRedstoneWireless.getInstance(
				"WirelessRedstone").get("Ether.Update.LoginDelay",
				Integer.class, new Integer(initUpdate));
	}

	public static void addOverrideToReceiver(
			BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ WirelessRedstone.blockWirelessR.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);

		((BlockRedstoneWireless) WirelessRedstone.blockWirelessR)
				.addOverride(override);
	}

	public static void addOverrideToTransmitter(
			BlockRedstoneWirelessOverride override) {
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write(
				"Override added to "
						+ WirelessRedstone.blockWirelessT.getClass().toString()
						+ ": " + override.getClass().toString(),
				LoggerRedstoneWireless.LogLevel.DEBUG);

		((BlockRedstoneWireless) WirelessRedstone.blockWirelessT)
				.addOverride(override);
	}

	public static World getWorld(NetworkManager network) {
		NetServerHandler net = (NetServerHandler) network.getNetHandler();
		return net.getPlayerEntity().worldObj;
	}

	public static EntityPlayer getPlayer(NetworkManager network) {
		NetServerHandler net = (NetServerHandler) network.getNetHandler();
		return net.getPlayerEntity();
	}

	public static void addOverride(BaseModOverride override) {
		overrides.add(override);
	}

	public static void openGUI(World world, EntityPlayer entityplayer,
			TileEntity tileentity) {
		boolean prematureExit = false;

		for (BaseModOverride override : overrides) {
			if (override.beforeOpenGui(world, entityplayer, tileentity))
				prematureExit = true;
		}

		if (!prematureExit) {
			if (tileentity != null) {
				if (tileentity instanceof TileEntityRedstoneWirelessT) {
					TileEntityRedstoneWirelessT tileentityredstonewirelesst = (TileEntityRedstoneWirelessT) tileentity;
					PacketHandlerRedstoneWireless.PacketHandlerOutput
							.sendGuiPacketTo((EntityPlayerMP) entityplayer,
									tileentityredstonewirelesst);
				}
				if (tileentity instanceof TileEntityRedstoneWirelessR) {
					TileEntityRedstoneWirelessR tileentityredstonewirelesst = (TileEntityRedstoneWirelessR) tileentity;
					PacketHandlerRedstoneWireless.PacketHandlerOutput
							.sendGuiPacketTo((EntityPlayerMP) entityplayer,
									tileentityredstonewirelesst);
				}
			}
		}
	}
	
	public static Entity getEntityByID(World world, EntityPlayer entityplayer, int entityId) {
		if (entityId == entityplayer.entityId) {
			return WirelessRedstone.getPlayer();
		} else {
			for (int i = 0; i < world.loadedEntityList
					.size(); ++i) {
				Entity entity = (Entity) world.loadedEntityList
						.get(i);

				if (entity == null) {
					return null;
				}

				if (entity.entityId == entityId) {
					return entity;
				}
			}
			return null;
		}
	}

	private static Entity getPlayer() {
		return null;
	}
}
