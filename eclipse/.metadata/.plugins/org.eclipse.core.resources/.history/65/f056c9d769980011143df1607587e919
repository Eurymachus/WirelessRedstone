package wifi.network;

import forge.DimensionManager;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ModLoader;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;
import wifi.LoggerRedstoneWireless;
import wifi.RedstoneEther;
import wifi.RedstoneEtherFrequency;
import wifi.RedstoneEtherNode;
import wifi.TileEntityRedstoneWireless;
import wifi.TileEntityRedstoneWirelessR;
import wifi.TileEntityRedstoneWirelessT;

public class PacketHandlerRedstoneWireless
{
    public static void handlePacket(PacketUpdate var0, EntityPlayer var1)
    {
        if (var0 instanceof PacketRedstoneEther)
        {
            PacketHandlerRedstoneWireless.PacketHandlerInput.handleEther((PacketRedstoneEther)var0, var1.world, var1);
        }
    }

    private static PacketRedstoneEther prepareTileEntityPacket(TileEntityRedstoneWireless var0, World var1)
    {
        PacketRedstoneEther var2 = new PacketRedstoneEther(var0, var1);
        return var2;
    }

    private static class PacketHandlerInput
    {
        public static void handleEther(PacketRedstoneEther var0, World var1, EntityPlayer var2)
        {
            LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:" + var2.name + ":" + var0.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
            TileEntity var3;

            if (var0.getCommand().equals("changeFreq"))
            {
                var3 = var1.getTileEntity(var0.xPosition, var0.yPosition, var0.zPosition);

                if (var3 instanceof TileEntityRedstoneWireless)
                {
                    int var4 = Integer.parseInt(var0.getFreq().toString());
                    int var5 = Integer.parseInt(((TileEntityRedstoneWireless)var3).getFreq().toString());
                    ((TileEntityRedstoneWireless)var3).setFreq(Integer.toString(var5 + var4));
                    var3.update();
                    var1.notify(var0.xPosition, var0.yPosition, var0.zPosition);
                    PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWireless)var3, var1, 0);
                }
            }
            else if (var0.getCommand().equals("fetchTile"))
            {
                var3 = var1.getTileEntity(var0.xPosition, var0.yPosition, var0.zPosition);

                if (var3 instanceof TileEntityRedstoneWireless)
                {
                    PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTileTo(var2, (TileEntityRedstoneWireless)var3, var1, 0);
                }
            }
            else
            {
                LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:" + var2.name + ":" + var0.toString() + "UNKNOWN COMMAND", LoggerRedstoneWireless.LogLevel.WARNING);
            }
        }
    }

    public static class PacketHandlerOutput
    {
        public static void sendGuiPacketTo(EntityPlayer var0, TileEntityRedstoneWireless var1, int var2)
        {
            PacketOpenWindowRedstoneWireless var3 = new PacketOpenWindowRedstoneWireless(var1);
            LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendGuiPacketTo:" + var0.name, LoggerRedstoneWireless.LogLevel.DEBUG);
            (new PacketHandlerRedstoneWireless.PacketHandlerOutput.PacketHandlerOutputSender(var0, var3, var2)).send();
        }

        public static void sendEtherTileToAll(TileEntityRedstoneWireless var0, World var1, int var2)
        {
            PacketRedstoneEther var3 = PacketHandlerRedstoneWireless.prepareTileEntityPacket(var0, var1);
            LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTileToAll:" + var3.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
            (new PacketHandlerRedstoneWireless.PacketHandlerOutput.PacketHandlerOutputSender(var3, var2)).send();
        }

        public static void sendEtherTileTo(EntityPlayer var0, TileEntityRedstoneWireless var1, World var2, int var3)
        {
            PacketRedstoneEther var4 = PacketHandlerRedstoneWireless.prepareTileEntityPacket(var1, var2);
            LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTileTo:" + var0.name + ":" + var4.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
            (new PacketHandlerRedstoneWireless.PacketHandlerOutput.PacketHandlerOutputSender(var0, var4, var3)).send();
        }

        public static void sendEtherNodeTileToAll(RedstoneEtherNode var0, int var1)
        {
            WorldServer var2 = ModLoader.getMinecraftServerInstance().getWorldServer(0);
            TileEntity var3 = var2.getTileEntity(var0.i, var0.j, var0.k);

            if (var3 instanceof TileEntityRedstoneWireless)
            {
                sendEtherTileToAll((TileEntityRedstoneWireless)var3, var2, var1);
            }
        }

        public static void sendEtherTilesTo(EntityPlayer var0, int var1)
        {
            LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTilesTo" + var0.name, LoggerRedstoneWireless.LogLevel.DEBUG);
            List var2 = RedstoneEther.getInstance().getRXNodes();
            WorldServer var4 = ModLoader.getMinecraftServerInstance().getWorldServer(0);
            Iterator var5 = var2.iterator();
            RedstoneEtherNode var6;
            TileEntity var7;

            while (var5.hasNext())
            {
                var6 = (RedstoneEtherNode)var5.next();
                var7 = var4.getTileEntity(var6.i, var6.j, var6.k);

                if (var7 instanceof TileEntityRedstoneWirelessR)
                {
                    sendEtherTileTo(var0, (TileEntityRedstoneWirelessR)var7, var4, var1);
                }
            }

            var2 = RedstoneEther.getInstance().getTXNodes();
            var5 = var2.iterator();

            while (var5.hasNext())
            {
                var6 = (RedstoneEtherNode)var5.next();
                var7 = var4.getTileEntity(var6.i, var6.j, var6.k);

                if (var7 instanceof TileEntityRedstoneWirelessT)
                {
                    sendEtherTileTo(var0, (TileEntityRedstoneWirelessT)var7, var4, var1);
                }
            }
        }

        public static void sendEtherTilesToAll(int var0)
        {
            LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTilesToAll", LoggerRedstoneWireless.LogLevel.DEBUG);
            List var1 = RedstoneEther.getInstance().getRXNodes();
            WorldServer var3 = ModLoader.getMinecraftServerInstance().getWorldServer(0);
            Iterator var4 = var1.iterator();
            RedstoneEtherNode var5;
            TileEntity var6;

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();
                var6 = var3.getTileEntity(var5.i, var5.j, var5.k);

                if (var6 instanceof TileEntityRedstoneWirelessR)
                {
                    sendEtherTileToAll((TileEntityRedstoneWirelessR)var6, var3, var0);
                }
            }

            var1 = RedstoneEther.getInstance().getTXNodes();
            var4 = var1.iterator();

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();
                var6 = var3.getTileEntity(var5.i, var5.j, var5.k);

                if (var6 instanceof TileEntityRedstoneWirelessT)
                {
                    sendEtherTileToAll((TileEntityRedstoneWirelessT)var6, var3, var0);
                }
            }
        }

        public static void sendEtherFrequencyTilesToAll(RedstoneEtherFrequency var0, int var1)
        {
            LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherFrequencyTilesToAll:" + var0.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
            WorldServer var3 = ModLoader.getMinecraftServerInstance().getWorldServer(0);
            Iterator var4 = var0.rxs.values().iterator();
            RedstoneEtherNode var5;
            TileEntity var6;

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();
                var6 = var3.getTileEntity(var5.i, var5.j, var5.k);

                if (var6 instanceof TileEntityRedstoneWirelessR)
                {
                    sendEtherTileToAll((TileEntityRedstoneWirelessR)var6, var3, var1);
                }
            }

            var4 = var0.txs.values().iterator();

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();
                var6 = var3.getTileEntity(var5.i, var5.j, var5.k);

                if (var6 instanceof TileEntityRedstoneWirelessT)
                {
                    sendEtherTileToAll((TileEntityRedstoneWirelessT)var6, var3, var1);
                }
            }
        }

        private static class PacketHandlerOutputSender implements Runnable
        {
            private int delay;
            private EntityPlayer player;
            private PacketUpdate packet;

            public PacketHandlerOutputSender(EntityPlayer var1, PacketUpdate var2, int var3)
            {
                this(var2, var3);
                this.player = var1;
            }

            public PacketHandlerOutputSender(PacketUpdate var1, int var2)
            {
                this.delay = var2;
                this.packet = var1;
            }

            public void send()
            {
                Thread var1 = new Thread(this);
                var1.setName("WirelessRedstonePacketSender." + this.packet.toString());
                var1.start();
            }

            public void run()
            {
                if (this.delay > 0)
                {
                    try
                    {
                        Thread.sleep((long)this.delay);
                    }
                    catch (InterruptedException var5)
                    {
                        var5.printStackTrace();
                    }
                }

                if (this.player == null)
                {
                    World[] var1 = DimensionManager.getWorlds();

                    for (int var2 = 0; var2 < var1.length; ++var2)
                    {
                        for (int var3 = 0; var3 < var1[var2].players.size(); ++var3)
                        {
                            EntityPlayer var4 = (EntityPlayer)var1[var2].players.get(var3);

                            if (Math.abs(var4.locX) <= 16.0D && Math.abs(var4.locY) <= 16.0D && Math.abs(var4.locZ) <= 16.0D)
                            {
                                var4.netServerHandler.networkManager.queue(this.packet.getPacket());
                            }
                        }
                    }
                }
                else
                {
                    this.player.netServerHandler.networkManager.queue(this.packet.getPacket());
                }
            }
        }
    }
}
