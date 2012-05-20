package net.minecraft.src.wirelessredstone.addon.remote;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.data.ConfigStoreRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWireless;

public class WirelessRemote {	
	public static Item itemRemote;
	public static int remoteon, remoteoff;
	public static int remoteID=6245;
	
	public static long pulseTime=2500;
	public static boolean duraTogg=true;
	public static int maxPulseThreads=1;
    static int ticksInGui;
	
	public static boolean initialize()
	{
		try
		{
			loadConfig();
			WirelessProcessRemote.initialize();
			itemRemote = (new ItemRedstoneWirelessRemote(remoteID - 256)).setItemName("remote");
			loadItemTextures();
			addRecipes();
			addNames();
		} catch (Exception e)
		{
			LoggerRedstoneWireless.getInstance(LoggerRedstoneWireless.filterClassName(WirelessRemote.class.toString())).write("Initialization failed.", LoggerRedstoneWireless.LogLevel.WARNING);
		}
		return true;
	}
	
	public static void loadItemTextures() {
		remoteoff = ModLoader.addOverride("/gui/items.png", "/WirelessSprites/remoteoff.png");
		remoteon = ModLoader.addOverride("/gui/items.png", "/WirelessSprites/remote.png");
		itemRemote.setIconIndex(remoteoff);
	}

	public static void addRecipes() {
		ModLoader.addRecipe(new ItemStack(itemRemote, 1), new Object[] {
            "i", "#", Character.valueOf('i'), Block.torchRedstoneActive, Character.valueOf('#'), WirelessRedstone.blockWirelessT
        });
	}
	
	public static void addNames() {
		ModLoader.addName(itemRemote, "Wireless Remote");
	}

	private static void loadConfig() {
		remoteID = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("ID", Integer.class, new Integer(remoteID));
		duraTogg = (Boolean) ConfigStoreRedstoneWireless.getInstance("Remote").get("Durability", Boolean.class, new Boolean(duraTogg));
		pulseTime = (Long) ConfigStoreRedstoneWireless.getInstance("Remote").get("PulseDurration", Long.class, new Long(pulseTime));
		maxPulseThreads = (Integer) ConfigStoreRedstoneWireless.getInstance("Remote").get("MaxPulseThreads", Integer.class, new Integer(maxPulseThreads));
	}

	public static void openGUI(EntityPlayer entityplayer, World world) {
		ModLoader.openGUI(entityplayer, new GuiRedstoneWirelessRemote(world, entityplayer));
	}

	public static void tick(Minecraft mc) {
        WirelessProcessRemote.checkClicks();
        WirelessProcessRemote.processRemote(mc.theWorld, mc.thePlayer, mc.currentScreen, mc.objectMouseOver);
        
        if (mc.currentScreen == null)
        {
            ticksInGui = 0;
        }
        else
        {
            ++ticksInGui;
        }
	}
}
