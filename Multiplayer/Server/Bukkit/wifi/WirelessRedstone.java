package wifi;

import forge.MinecraftForge;
import java.awt.GraphicsEnvironment;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;
import wifi.network.NetworkConnection;
import wifi.network.PacketHandlerRedstoneWireless;

public class WirelessRedstone
{
    public static Block blockWirelessR;
    public static Block blockWirelessT;
    public static int rxID = 127;
    public static int txID = 126;
    public static int manualUpdate = 10;
    public static int stateUpdate = 100;
    public static int initUpdate = 2;
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
        if (!loaded)
        {
            loaded = true;
            loadConfig();
            MinecraftForge.registerConnectionHandler(new NetworkConnection());
            blockWirelessR = (new BlockRedstoneWirelessR(rxID, 1.0F, 8.0F)).a("wifir");
            blockWirelessT = (new BlockRedstoneWirelessT(txID, 1.0F, 8.0F)).a("wifit");
            ModLoader.registerBlock(blockWirelessR);
            ModLoader.registerTileEntity(TileEntityRedstoneWirelessR.class, "Wireless Receiver");
            ModLoader.registerBlock(blockWirelessT);
            ModLoader.registerTileEntity(TileEntityRedstoneWirelessT.class, "Wireless Transmitter");
            loadBlockTextures();
            addRecipes();

            Thread var1 = new Thread(new Runnable()
            {
                public void run()
                {
                    if (WirelessRedstone.manualUpdate >= 1)
                    {
                        while (true)
                        {
                            try
                            {
                                Thread.sleep((long)(WirelessRedstone.manualUpdate * 1000));
                            }
                            catch (InterruptedException var2)
                            {
                                var2.printStackTrace();
                            }

                            PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTilesToAll(0);
                        }
                    }
                }
            });
            var1.setName("WirelessRedstoneUpdaterThread");
            var1.start();
        }
    }

    public static void addRecipes()
    {
        ModLoader.addRecipe(new ItemStack(blockWirelessR, 1), new Object[] {"IRI", "RLR", "IRI", 'I', Item.IRON_INGOT, 'R', Item.REDSTONE, 'L', Block.LEVER});
        ModLoader.addRecipe(new ItemStack(blockWirelessT, 1), new Object[] {"IRI", "RTR", "IRI", 'I', Item.IRON_INGOT, 'R', Item.REDSTONE, 'T', Block.REDSTONE_TORCH_ON});
    }

    public static void loadBlockTextures()
    {
        spriteTopOn = ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOn.png");
        spriteTopOff = ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOff.png");
        spriteROn = ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOn.png");
        spriteROff = ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOff.png");
        spriteTOn = ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOn.png");
        spriteTOff = ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOff.png");
    }

    public static void addOverrideToReceiver(BlockRedstoneWirelessOverride var0)
    {
        LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to " + blockWirelessR.getClass().toString() + ": " + var0.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
        ((BlockRedstoneWireless)blockWirelessR).addOverride(var0);
    }

    public static void addOverrideToTransmitter(BlockRedstoneWirelessOverride var0)
    {
        LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to " + blockWirelessT.getClass().toString() + ": " + var0.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
        ((BlockRedstoneWireless)blockWirelessT).addOverride(var0);
    }

    private static void loadConfig()
    {
        rxID = ((Integer)ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Receiver.ID", Integer.class, new Integer(rxID))).intValue();
        txID = ((Integer)ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Transmitter.ID", Integer.class, new Integer(txID))).intValue();
        guiOn = ((Boolean)ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Gui", Boolean.class, new Boolean(guiOn))).booleanValue();
        manualUpdate = ((Integer)ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Update.Intervall", Integer.class, new Integer(manualUpdate))).intValue();
        stateUpdate = ((Integer)ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Update.StateDelay", Integer.class, new Integer(stateUpdate))).intValue();
        initUpdate = ((Integer)ConfigStoreRedstoneWireless.getInstance("WirelessRedstone").get("Ether.Update.LoginDelay", Integer.class, new Integer(initUpdate))).intValue();
    }
}
